package com.github.wukap.uno_soft.model.group.condition;

import com.github.wukap.uno_soft.model.group.Group;
import com.github.wukap.uno_soft.model.group.SubGroup;

import java.util.*;

public class ConditionsMap {
    private final ArrayList<HashMap<String, SubGroup>> groupConditions;

    public ConditionsMap() {
        this.groupConditions = new ArrayList<>();
    }

    public void addConditions(final List<String> line, final Group group) {
        for (int i = 0; i < line.size(); i++) {
            if (groupConditions.size() <= i) {
                groupConditions.add(new HashMap<>());
            }
            put(groupConditions.get(i), line.get(i), group.getAnySubGroup());
        }
    }

    public Set<Integer> getMatchedGroupsId(final List<String> line) {
        Set<Integer> matchedGroups = new HashSet<>();
        for (int i = 0; i < line.size() && i < groupConditions.size(); i++) {
            if (isMatch(line.get(i), i)) {
                matchedGroups.add(groupConditions.get(i).get(line.get(i)).getGroupId());
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