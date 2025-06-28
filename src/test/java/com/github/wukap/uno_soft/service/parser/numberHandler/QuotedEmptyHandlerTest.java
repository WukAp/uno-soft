package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class QuotedEmptyHandlerTest {
    private final NumberHandler handler = new QuotedEmptyHandler();

    @Test
    public void quotedEmptyString() {
        assertEquals("", handler.handle("\"\""));
    }

    @Test
    public void quotedWhitespace() {
        assertEquals("", handler.handle("\"  \""));
    }

    @Test
    public void illegalQuoted() {
        assertNull(handler.handle("\""));
        assertNull(handler.handle("\"  "));
    }

    @Test
    public void nonEmpty() {
        assertNull(handler.handle("42"));
        assertNull(handler.handle("111.1"));
    }
}