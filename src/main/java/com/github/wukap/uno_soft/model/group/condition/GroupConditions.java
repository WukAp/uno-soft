package com.github.wukap.uno_soft.model.group.condition;

import com.github.wukap.uno_soft.model.group.stringList.GroupLines;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class GroupConditions {
    private final ArrayList<Condition> conditions;

    private GroupConditions(final ArrayList<Condition> conditions) {
        this.conditions = conditions == null ? new ArrayList<>() : conditions;
    }

    public static GroupConditions ofGroupLines(GroupLines items) {
        if (items == null || items.getLength() == 0) {
            return new GroupConditions(new ArrayList<>());
        }
        int columnsCount = items.getMaxItemLength();
        ArrayList<Condition> conditions = IntStream.range(0, columnsCount)
                .mapToObj(col -> {
                    HashSet<String> columnItems = items.getItems().stream()
                            .map(item -> item.get(col))
                            .collect(HashSet::new, HashSet::add, HashSet::addAll);
                    return new Condition(columnItems);
                })
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return new GroupConditions(conditions);
    }

    public static  GroupConditions ofLine(List<String> line) {
        if (line == null) {
            return new GroupConditions(new ArrayList<>());
        }
        ArrayList<Condition> conditions = line.stream()
                .map(t -> new Condition(new HashSet<>() {{add(t);}}))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return new GroupConditions(conditions);
    }

    public int getLength() {
        return conditions.size();
    }

    public boolean isMatch(final String item, final int columnIndex) {
        if(conditions.size() <= columnIndex) {
            return false;
        }
        return conditions.get(columnIndex).isMatch(item);
    }

    public Condition get(final int columnIndex) {
        return conditions.get(columnIndex);
    }

    public void join(final GroupConditions groupConditions) {
        for (int i = 0; i < this.getLength(); i++) {
            if(groupConditions.getLength() <= i) {
                break;
            }
            conditions.get(i).addAll(groupConditions.get(i));
        }
        for (int i = this.getLength(); i < groupConditions.getLength(); i++) {
            conditions.add(groupConditions.get(i));
        }
    }

    public record Condition(HashSet<String> columnPossibleItems) {
        public Condition(final HashSet<String> columnPossibleItems) {
            this.columnPossibleItems = columnPossibleItems == null ? new HashSet<>() : columnPossibleItems.stream()
                    .filter(this::isNotEmptyCondition)
                    .collect(HashSet::new, HashSet::add, HashSet::addAll);
        }

        public boolean isMatch(final String item) {
            return columnPossibleItems.contains(item);
        }

        public void addAll(final Condition condition) {
            columnPossibleItems.addAll(condition.columnPossibleItems);
        }

        private boolean isNotEmptyCondition(String item) {
            return !item.isEmpty();
        }
    }
}