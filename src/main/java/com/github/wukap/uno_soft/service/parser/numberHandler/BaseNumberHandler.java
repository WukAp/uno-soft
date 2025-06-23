package com.github.wukap.uno_soft.service.parser.numberHandler;

public abstract class BaseNumberHandler implements NumberHandler {

    protected NumberHandler next;

    public NumberHandler setNext(NumberHandler next) {
        this.next = next;
        return next;
    }

    protected String toNext(String input) {
        if (next != null) {
            return next.handle(input);
        }
        return null;
    }
}