package com.github.wukap.uno_soft.model.group.condition;

import com.github.wukap.uno_soft.model.group.stringList.SubGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ConditionsMap {
    private final ArrayList<HashMap<String, SubGroup>> groupConditions;

    private ConditionsMap(final ArrayList<HashMap<String, SubGroup>> conditions) {
        this.groupConditions = conditions;
    }

    public ConditionsMap() {
        this.groupConditions = new ArrayList<>();
    }

    public static ConditionsMap ofSubGroup(SubGroup subGroup) {
        if (subGroup == null || subGroup.getLength() == 0) {
            return new ConditionsMap(new ArrayList<>());
        }
        int columnsCount = subGroup.getMaxItemLength();
        ArrayList<HashMap<String, SubGroup>> conditions = new ArrayList<>();
        for (int i = 0; i < columnsCount; i++) {
            HashMap<String, SubGroup> condMap = new HashMap<>();
            for (ArrayList<String> item : subGroup.getItems()) {
                String key = item.get(i);
                put(condMap, key, subGroup);
            }
            conditions.add(condMap);
        }
        return new ConditionsMap(conditions);
    }

    public static ConditionsMap ofLine(List<String> line, SubGroup subGroup) {
        if (line == null) {
            return new ConditionsMap(new ArrayList<>());
        }
        ArrayList<HashMap<String, SubGroup>> conditions = new ArrayList<>();
        for (String string : line) {
            HashMap<String, SubGroup> condMap = new HashMap<>();
            put(condMap, string, subGroup);
            conditions.add(condMap);
        }
        return new ConditionsMap(conditions);
    }

    public void addConditions(final List<String> line, final SubGroup subGroup) {
        for (int i = 0; i < line.size(); i++) {
            if (groupConditions.size() <= i) {
                groupConditions.add(new HashMap<>());
            }
            put(groupConditions.get(i), line.get(i), subGroup);
        }
    }

    public HashSet<SubGroup> getMatchedGroups(final List<String> line) {
        HashSet<SubGroup> matchedGroups = new HashSet<>();
        for (int i = 0; i < line.size(); i++) {
            if (isMatch(line.get(i), i)) {
                matchedGroups.add(groupConditions.get(i).get(line.get(i)));
            }
        }
        return matchedGroups;
    }

    private boolean isMatch(final String item, final int columnIndex) {
        if (groupConditions.size() <= columnIndex) {
            return false;
        }
        return groupConditions.get(columnIndex).containsKey(item);
    }

    private static void put(final HashMap<String, SubGroup> map, final String key, final SubGroup subGroup) {
        if (key == null || key.isEmpty()) {
            return;
        }
        map.put(key, subGroup);
    }
}
