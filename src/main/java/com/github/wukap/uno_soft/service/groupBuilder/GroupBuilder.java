package com.github.wukap.uno_soft.service.groupBuilder;

import com.github.wukap.uno_soft.model.group.Group;
import com.github.wukap.uno_soft.model.group.condition.ConditionsMap;
import com.github.wukap.uno_soft.service.parser.ParseService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

@Service
public class GroupBuilder {
    private final ParseService parseService;

    public GroupBuilder(final ParseService parseService) {
        this.parseService = parseService;
    }

    public List<Group> getGroups(String stringPath) {
        Path path = Path.of(stringPath);
        if (!Files.exists(path)) {
            System.err.println("File " + path + " not found");
            throw new IllegalArgumentException("File " + path + " not found");
        }
        return getGroups(path);
    }

    public List<Group> getGroups(Path path) {
        Map<Integer, Group> groupsMap = new HashMap<>();
        ;
        ConditionsMap conditionsMap = new ConditionsMap();
        try (Stream<String> lines = Files.lines(path)) {
            lines.map(parseService::parse)
                    .filter(Objects::nonNull)
                    .forEach(line -> {
                        applyLineToGroup(line, conditionsMap, groupsMap);
                    });
        } catch (IOException e) {
            System.err.println("Error while processing file: " + e.getMessage());
            throw new UncheckedIOException("Failed to process file: " + path, e);
        }
        return groupsMap.values().stream().toList();
    }

    private void applyLineToGroup(List<String> line, ConditionsMap conditionsMap, Map<Integer, Group> groupsMap) {
        List<Integer> containedGroupIds = new ArrayList<>(conditionsMap.getMatchedGroupsId(line));
        if (containedGroupIds.isEmpty()) {
            Group group = Group.ofLine(line);
            groupsMap.put(group.getGroupId(), group);
            conditionsMap.addConditions(line, group);
            return;
        }
        Group mainGroup = groupsMap.get(containedGroupIds.getFirst());
        mainGroup.add(line);
        conditionsMap.addConditions(line, mainGroup);
        if (containedGroupIds.size() > 1) {
            List<Integer> toBeRemoved = containedGroupIds.subList(1, containedGroupIds.size());
            toBeRemoved.forEach(groupId -> mainGroup.join(groupsMap.get(groupId)));
            groupsMap.entrySet().removeIf(entry -> toBeRemoved.contains(entry.getKey()));
        }
    }
}