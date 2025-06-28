package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(3)
public class QuotedNumberHandler extends BaseNumberHandler {
    private static final Pattern QUOTED_NUMBER_PATTERN = Pattern.compile("^\"(\\d+)\"$");

    @Override
    public String handle(String input) {
        Matcher matcher = QUOTED_NUMBER_PATTERN.matcher(input);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return toNext(input);
    }
}