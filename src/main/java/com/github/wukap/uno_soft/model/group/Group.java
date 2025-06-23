package com.github.wukap.uno_soft.model.group;

import com.github.wukap.uno_soft.model.group.stringList.SubGroup;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public final class Group {
    private final ArrayList<SubGroup> subGroupList;
    private final int groupId;
    private static int groupIdCounter = 0;

    public Group(ArrayList<SubGroup> subGroupList) {
        this.subGroupList = subGroupList;
        this.groupId = groupIdCounter++;
    }

    public static Group ofLine(ArrayList<String> line) {
        ArrayList<SubGroup> subGroupList = new ArrayList<>(){{
            add(SubGroup.ofLine(line, groupIdCounter));
        }};
        return new Group(subGroupList);
    }

    public void join(final Group group) {
        group.subGroupList.forEach(subGroup -> subGroup.setGroupId(groupId));
        subGroupList.addAll(group.subGroupList);
    }

    public void add(final ArrayList<String> item) {
        if (subGroupList.isEmpty()) {
            subGroupList.add(SubGroup.ofLine(item, groupId));
            return;
        }
        subGroupList.getFirst().add(item);
    }

    public int getLength() {
        return subGroupList.stream().map(SubGroup::getLength).reduce(0, Integer::sum);
    }

    public SubGroup getSubGroup() {
        if (subGroupList.isEmpty()) {
            throw new IllegalStateException("subGroupList is empty");
        } else {
            return subGroupList.getFirst();
        }
    }
}





