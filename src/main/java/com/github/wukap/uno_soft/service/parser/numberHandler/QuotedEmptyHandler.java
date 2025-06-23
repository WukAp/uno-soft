package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(2)
public class QuotedEmptyHandler extends BaseNumberHandler {
    private static final Pattern QUOTED_EMPTY_PATTERN = Pattern.compile("^\"(\\s*)\"$");

    @Override
    public String handle(String input) {
        Matcher matcher = QUOTED_EMPTY_PATTERN.matcher(input.trim());
        if (matcher.matches()) {
            return "";
        }
        return toNext(input);
    }
}