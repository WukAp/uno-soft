package com.github.wukap.uno_soft.model.group.stringList;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class SubGroup {
    @Getter
    private final HashSet<ArrayList<String>> items;
    @Getter
    private int maxItemLength;
    @Getter
    @Setter
    private int groupId;

    public SubGroup(int groupId) {
        maxItemLength = 0;
        items = new HashSet<>();
    }

    public SubGroup(final HashSet<ArrayList<String>> items, int groupId) {
        if (items == null) {
            throw new IllegalArgumentException("items cannot be null");
        }
        this.items = items;
        items.forEach(this::updateMaxItemLength);
        this.groupId = groupId;
    }

    public static SubGroup ofLine(final ArrayList<String> line, int groupId) {
        if (line == null) {
            return new SubGroup(new HashSet<>(), groupId);
        }
        return new SubGroup(new HashSet<>() {{add(line);}}, groupId);
    }

    public int getLength() {
        return items.size();
    }

    public void add(final ArrayList<String> item) {
        items.add(item);
        updateMaxItemLength(item);
    }

    public ArrayList<String> getItemsByColumn(int column) {
        return items.stream()
                .map(item -> item.get(column))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private void updateMaxItemLength(ArrayList<String> item) {
        if (item.size() > maxItemLength) {
            maxItemLength = item.size();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SubGroup subGroup = (SubGroup) o;
        return groupId == subGroup.groupId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(groupId);
    }
}