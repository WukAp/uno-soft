package com.github.wukap.uno_soft.model.group;

import com.github.wukap.uno_soft.model.group.condition.GroupConditions;
import com.github.wukap.uno_soft.model.group.stringList.GroupLines;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public final class Group {
    private final GroupConditions groupConditions;
    private final List<GroupLines> ItemList;

    public Group(GroupConditions groupConditions, List<GroupLines> ItemList) {
        this.groupConditions = groupConditions;
        this.ItemList = ItemList;
    }

    public Group(GroupLines stringItemList) {
        this(GroupConditions.ofGroupLines(stringItemList), List.of(stringItemList));
    }

    public Group(ArrayList<String> line) {
        this(GroupConditions.ofLine(line), List.of(GroupLines.ofLine(line)));
    }

    public void join(final Group group) {
        groupConditions.join(group.groupConditions);
        ItemList.addAll(group.ItemList);
    }

    public void add(final ArrayList<String> item) {
        ItemList.getFirst().add(item);
        groupConditions.join(GroupConditions.ofLine(item));
    }

    public boolean isMatch(final String item, final int columnIndex) {
        return groupConditions.isMatch(item, columnIndex);
    }

    public boolean isMatch(final List<String> line) {
        for (int i = 0; i < line.size(); i++) {
            if (isMatch(line.get(i), i)) {
                return true;
            }
        }
        return false;
    }
    public int getLength() {
        return ItemList.stream().map(GroupLines::getLength).reduce(0, Integer::sum);
    }
}





