package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class QuotedEmptyHandlerTest {
    private final QuotedEmptyHandler handler = new QuotedEmptyHandler();

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
}