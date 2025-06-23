package com.github.wukap.uno_soft.model.group.stringList;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GroupLines {
    @Getter
    private final HashSet<ArrayList<String>> items;
    @Getter
    private int maxItemLength;

    public GroupLines() {
        maxItemLength = 0;
        items = new HashSet<>();
    }

    public GroupLines(final HashSet<ArrayList<String>> items) {
        if (items == null) {
            throw new IllegalArgumentException("items cannot be null");
        }
        this.items = items;
        items.forEach(this::updateMaxItemLength);
    }

    public static GroupLines ofLine(final ArrayList<String> line) {
        if (line == null) {
            return new GroupLines(new HashSet<>());
        }
        return new GroupLines(new HashSet<>() {{add(line);}});
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
}