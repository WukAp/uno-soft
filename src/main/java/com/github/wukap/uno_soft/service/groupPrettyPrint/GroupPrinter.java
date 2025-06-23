package com.github.wukap.uno_soft.service.groupPrettyPrint;

import com.github.wukap.uno_soft.model.group.Group;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class GroupPrinter {
    public void print(List<Group> groups) {
        for (int i = 0; i < groups.size(); i++) {
            System.out.printf("Group %d [size: %d]:%n", i + 1, groups.get(i).getLength());
            groups.get(i).getSubGroupList().forEach(subGroup -> {
                subGroup.getItems().forEach(System.out::println);
            });
            System.out.println();
        }
    }

    public void printNonSingleSortedGroups(List<Group> groups) {
        List<Group> nonSingleSortedGroups = getNonSingleSortedGroups(groups);
        print(nonSingleSortedGroups);
    }

    public List<Group> getNonSingleSortedGroups(List<Group> groups) {
        return groups.stream()
                .filter(group -> group.getLength() > 1)
                .sorted(Comparator.comparing(Group::getLength, Comparator.reverseOrder()))
                .toList();
    }
}