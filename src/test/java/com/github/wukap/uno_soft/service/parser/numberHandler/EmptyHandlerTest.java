package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmptyHandlerTest {
    @Test
    public void emptyString() {
        EmptyHandler handler = new EmptyHandler();
        assertEquals("", handler.handle(""));
    }

    @Test
    public void whitespaceString() {
        EmptyHandler handler = new EmptyHandler();
        assertEquals("", handler.handle("   "));
    }
}