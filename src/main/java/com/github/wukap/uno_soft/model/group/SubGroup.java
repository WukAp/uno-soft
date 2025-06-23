package com.github.wukap.uno_soft.model.group;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SubGroup {
    @Getter
    private final HashSet<List<String>> items;
    @Getter
    private int maxItemLength;
    @Getter
    @Setter
    private int groupId;

    public SubGroup(int groupId) {
        maxItemLength = 0;
        items = new HashSet<>();
    }

    public SubGroup(final Set<List<String>> items, int groupId) {
        this.items = new HashSet<>(Objects.requireNonNull(items, "items cannot be null"));
        items.forEach(this::updateMaxItemLength);
        this.groupId = groupId;
    }

    public static SubGroup ofLine(final List<String> line, int groupId) {
        if (line == null) {
            return new SubGroup(new HashSet<>(), groupId);
        }
        return new SubGroup(new HashSet<>() {{add(line);}}, groupId);
    }

    public int getLength() {
        return items.size();
    }

    public void add(final List<String> item) {
        items.add(item);
        updateMaxItemLength(item);
    }

    private void updateMaxItemLength(List<String> item) {
        if (item.size() > maxItemLength) {
            maxItemLength = item.size();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SubGroup subGroup)) return false;
        return groupId == subGroup.groupId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(groupId);
    }
}