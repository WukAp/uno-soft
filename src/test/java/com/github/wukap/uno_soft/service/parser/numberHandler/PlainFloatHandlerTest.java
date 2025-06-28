package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlainFloatHandlerTest {
    NumberHandler handler = new PlainFloatHandler();

    @Test
    public void validNumber() {
        assertEquals("42.42", handler.handle("42.42"));
        assertEquals("42.2", handler.handle("42.2"));
        assertEquals("111.1", handler.handle("111.1"));
        assertEquals("42", handler.handle("42.0"));
    }

    @Test
    public void validNumberWithTrailingZero() {
        assertEquals("42.42", handler.handle("42.4200"));
        assertEquals("42.42", handler.handle("42.420"));
        assertEquals("1", handler.handle("1.0000"));
        assertEquals("1", handler.handle("1."));
    }

    @Test
    public void illegalFloat() {
        assertNull(handler.handle("42"));
        assertNull(handler.handle("\"42\""));
    }
}