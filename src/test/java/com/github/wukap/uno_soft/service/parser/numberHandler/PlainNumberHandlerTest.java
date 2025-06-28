package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlainNumberHandlerTest {
    NumberHandler handler = new PlainNumberHandler();

    @Test
    public void validNumber() {
        assertEquals("42", handler.handle("42"));
    }
}