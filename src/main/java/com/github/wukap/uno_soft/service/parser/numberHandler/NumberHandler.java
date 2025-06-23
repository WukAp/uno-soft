package com.github.wukap.uno_soft.service.parser.numberHandler;

public interface NumberHandler {
    String handle(String input);

    NumberHandler setNext(NumberHandler next);
}