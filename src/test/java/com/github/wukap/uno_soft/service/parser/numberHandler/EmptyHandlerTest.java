package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EmptyHandlerTest {
    NumberHandler handler = new EmptyHandler();

    @Test
    public void emptyString() {
        assertEquals("", handler.handle(""));
    }

    @Test
    public void nonEmpty() {
        assertNull(handler.handle("42"));
        assertNull(handler.handle("111.1"));
    }
}