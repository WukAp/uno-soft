package com.github.wukap.uno_soft.model.group.condition;

import java.util.ArrayList;
import java.util.HashSet;

public class ConditionsMap {
    private final ArrayList<HashSet<GroupConditions.Condition>> conditions;

    public ConditionsMap(final ArrayList<HashSet<GroupConditions.Condition>> conditions) {
        this.conditions = conditions;
    }
}
