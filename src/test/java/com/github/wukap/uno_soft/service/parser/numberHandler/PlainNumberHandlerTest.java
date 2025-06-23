package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlainNumberHandlerTest {
    @Test
    public void validNumber() {
        PlainNumberHandler handler = new PlainNumberHandler();
        assertEquals("42", handler.handle("42"));
    }

    @Test
    public void numberWithWhitespace() {
        PlainNumberHandler handler = new PlainNumberHandler();
        assertEquals("42", handler.handle("  42  "));
    }
}