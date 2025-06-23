package com.github.wukap.uno_soft;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ListSetBehaviorTest {

    @Test
    void shouldDetectDuplicateLists() {
        Set<List<String>> set = new HashSet<>();

        List<String> list1 = Arrays.asList("A", "B", "C");
        List<String> list2 = Arrays.asList("A", "B", "C");

        assertTrue(set.add(list1));
        assertFalse(set.add(list2));
        assertEquals(1, set.size());
    }

    @Test
    void shouldTreatDifferentOrderAsUnique() {
        Set<List<String>> set = new HashSet<>();

        List<String> list1 = Arrays.asList("A", "B", "C");
        List<String> list2 = Arrays.asList("C", "B", "A");

        assertTrue(set.add(list1));
        assertTrue(set.add(list2));
        assertEquals(2, set.size());
    }

    @Test
    void shouldHandleDifferentListImplementations() {
        Set<List<String>> set = new HashSet<>();

        List<String> arrayList = new ArrayList<>(Arrays.asList("X", "Y"));
        List<String> immutableList = List.of("X", "Y");

        assertTrue(set.add(arrayList));
        assertFalse(set.add(immutableList));
    }
}