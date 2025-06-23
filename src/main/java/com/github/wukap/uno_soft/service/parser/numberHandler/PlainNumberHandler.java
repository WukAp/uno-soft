package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(4)
public class PlainNumberHandler extends BaseNumberHandler {
    private static final Pattern PLAIN_NUMBER_PATTERN = Pattern.compile("^(\\d+)$");

    @Override
    public String handle(String input) {
        Matcher matcher = PLAIN_NUMBER_PATTERN.matcher(input.trim());
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return toNext(input);
    }
}
