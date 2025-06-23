package com.github.wukap.uno_soft.service.groupBuilder;

import com.github.wukap.uno_soft.model.group.Group;
import com.github.wukap.uno_soft.model.group.condition.ConditionsMap;
import com.github.wukap.uno_soft.model.group.stringList.SubGroup;
import com.github.wukap.uno_soft.service.parser.ParseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class GroupBuilder {
    private final ParseService parseService;

    public GroupBuilder(final ParseService parseService) {
        this.parseService = parseService;
    }

    public List<Group> getGroups(String stringPath) {
        Path path = Path.of(stringPath);
        if (!Files.exists(path)) {
            log.error("File{} not found", path);
            throw new IllegalArgumentException("File " + path + " not found");
        }
        return getGroups(path);
    }

    public List<Group> getGroups(Path path) {
        List<Group> groups = new ArrayList<>();
        ConditionsMap conditionsMap = new ConditionsMap();
        try (Stream<String> lines = Files.lines(path)) {
            lines.map(parseService::parse)
                    .filter(Objects::nonNull)
                    .forEach(line -> {
                        log.info("Processing line: {}", line);
                        applyLineToGroup(line, conditionsMap, groups);
                    });
        } catch (IOException e) {
            log.error("Error while processing file: {}", e.getMessage());
            throw new RuntimeException("File processing failed", e);
        }
        return groups;
    }

    private void applyLineToGroup(ArrayList<String> line, ConditionsMap conditionsMap, List<Group> groups) {
        List<SubGroup> containedSubGroups = conditionsMap.getMatchedGroups(line).stream().toList();
        if (containedSubGroups.isEmpty()) {
            Group group = Group.ofLine(line);
            groups.add(group);
            conditionsMap.addConditions(line, group.getSubGroup());
        } else if (containedSubGroups.size() == 1) {
            SubGroup subGroup = containedSubGroups.getFirst();
            subGroup.add(line);
            conditionsMap.addConditions(line, subGroup);
        } else {
            HashSet<Integer> containedGroupIds = containedSubGroups.stream().map(SubGroup::getGroupId).collect(Collectors.toCollection(HashSet::new));
            List<Group> containedGroups = groups.stream().filter(group -> containedGroupIds.contains(group.getGroupId())).toList();
            List<Group> toBeRemoved = containedGroups.subList(1, containedGroups.size());
            Group mainGroup = containedGroups.getFirst();
            groups.removeAll(toBeRemoved);
            mainGroup.add(line);
            conditionsMap.addConditions(line, mainGroup.getSubGroup());
            toBeRemoved.forEach(mainGroup::join);
        }
    }
}