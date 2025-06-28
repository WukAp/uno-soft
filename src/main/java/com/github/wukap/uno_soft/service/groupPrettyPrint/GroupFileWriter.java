package com.github.wukap.uno_soft.service.groupPrettyPrint;

import com.github.wukap.uno_soft.model.group.Group;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;

@Service
public class GroupFileWriter {
    private final Path path;

    public GroupFileWriter(@Value("${resultFileName}") String fileName) {
        this.path = Path.of(fileName);
    }

    public void write(List<Group> groups) throws IOException {
        Files.writeString(path, "", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        try (var writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            writer.write("Groups amount: " + groups.size() + "\n");
            for (int i = 0; i < groups.size(); i++) {
                Group group = groups.get(i);
                writer.write(String.format("Group %d [size: %d]:%n", i + 1, group.getLength()));

                group.getSubGroupList().forEach(subGroup -> {
                    subGroup.getItems().forEach(item -> {
                        try {
                            writer.write(item.toString() + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to write item to file", e);
                        }
                    });
                });
                writer.write("\n");
            }
        }
    }

    public void writeNonSingleSortedGroups(List<Group> groups) throws IOException {
        List<Group> nonSingleSortedGroups = getNonSingleSortedGroups(groups);
        write(nonSingleSortedGroups);
    }

    public List<Group> getNonSingleSortedGroups(List<Group> groups) {
        return groups.stream()
                .filter(group -> group.getLength() > 1)
                .sorted(Comparator.comparing(Group::getLength, Comparator.reverseOrder()))
                .toList();
    }
}