package com.github.wukap.uno_soft.service.parser.numberHandler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(6)
public class PlainFloatHandler extends BaseNumberHandler {
    private static final Pattern PLAIN_FLOAT_PATTERN = Pattern.compile("^(\\d+\\.\\d*[1-9])(0)*$");
    private static final Pattern PLAIN_INTEGER_PATTERN = Pattern.compile("^(\\d+)\\.(0)*$");

    @Override
    public String handle(String input) {
        if (!input.contains(".")) {
            return toNext(input);
        }
        {
            Matcher matcher = PLAIN_FLOAT_PATTERN.matcher(input);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }
        {
            Matcher matcher = PLAIN_INTEGER_PATTERN.matcher(input);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }
        return toNext(input);
    }
}
