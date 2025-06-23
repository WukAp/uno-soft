package com.github.wukap.uno_soft.model.group;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public final class Group {
    private final ArrayList<SubGroup> subGroupList;
    private final int groupId;
    private static final AtomicInteger groupIdCounter = new AtomicInteger(0);

    public Group(ArrayList<SubGroup> subGroupList) {
        this.subGroupList = subGroupList;
        this.groupId = groupIdCounter.getAndIncrement();
    }

    public static Group ofLine(List<String> line) {
        ArrayList<SubGroup> subGroupList = new ArrayList<>();
        subGroupList.add(SubGroup.ofLine(line, groupIdCounter.get()));
        return new Group(subGroupList);
    }

    public void join(final Group group) {
        group.subGroupList.forEach(subGroup -> subGroup.setGroupId(groupId));
        subGroupList.addAll(group.subGroupList);
    }

    public void add(final List<String> item) {
        if (subGroupList.isEmpty()) {
            subGroupList.add(SubGroup.ofLine(item, groupId));
            return;
        }
        subGroupList.getFirst().add(item);
    }

    public int getLength() {
        return subGroupList.stream().map(SubGroup::getLength).reduce(0, Integer::sum);
    }

    public SubGroup getAnySubGroup() {
        if (subGroupList.isEmpty()) {
            throw new IllegalStateException("subGroupList is empty");
        } else {
            return subGroupList.getFirst();
        }
    }
}