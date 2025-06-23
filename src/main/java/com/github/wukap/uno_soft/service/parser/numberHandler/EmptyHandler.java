package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.springframework.stereotype.Component;

@Component
public class EmptyHandler extends BaseNumberHandler {
    @Override
    public String handle(String input) {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        return toNext(input);
    }
}