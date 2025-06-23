package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
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