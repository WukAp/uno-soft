package com.github.wukap.uno_soft.service.groupBuilder;

import com.github.wukap.uno_soft.model.group.Group;
import com.github.wukap.uno_soft.service.parser.ParseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Slf4j
public class GroupBuilder {
    private final ParseService parseService;
    private int i = 0;

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
        try (Stream<String> lines = Files.lines(path)) {
            lines.map(parseService::parse)
                    .filter(Objects::nonNull)
                    .forEach(line -> {
                        log.info("Processing line: {}", line);
                        applyLineToGroup(line, groups);
                        sout();
                    });
        } catch (IOException e) {
            log.error("Error while processing file: {}", e.getMessage());
            throw new RuntimeException("File processing failed", e);
        }
        return groups;
    }

    private void applyLineToGroup(ArrayList<String> line, List<Group> groups) {
        List<Group> containedGroups = groups.stream()
                .filter(group -> group.isMatch(line))
                .toList();
        if (containedGroups.isEmpty()) {
            groups.add(new Group(line));
        } else if (containedGroups.size() == 1) {
            containedGroups.getFirst().add(line);
        } else {
            groups.removeAll(containedGroups.subList(1, containedGroups.size()));
            Group mainGroup = containedGroups.getFirst();
            mainGroup.add(line);
            for (int i = 1; i < containedGroups.size(); i++) {
                mainGroup.join(containedGroups.get(i));
            }
        }
    }

    private void sout() {
        log.info("Processing line: {}", i++);
    }
}