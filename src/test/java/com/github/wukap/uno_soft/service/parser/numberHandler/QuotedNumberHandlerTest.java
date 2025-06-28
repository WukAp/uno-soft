package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class QuotedNumberHandlerTest {
    private final NumberHandler handler = new QuotedNumberHandler();

    @Test
    public void validQuotedNumber() {
        assertEquals("42", handler.handle("\"42\""));
    }

    @Test
    public void illegalQuotedNumberWithWhitespace() {
        assertNull(handler.handle("\"42"));
        assertNull(handler.handle("  \"42  "));
    }
}