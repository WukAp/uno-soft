package com.github.wukap.uno_soft.service.parser;

import com.github.wukap.uno_soft.service.parser.numberHandler.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParseService {
    private final NumberHandler chain;

    public ParseService(EmptyHandler emptyHandler, QuotedEmptyHandler quotedEmptyHandler, PlainNumberHandler plainHandler, QuotedNumberHandler quotedHandler) {
        this.chain = emptyHandler;
        emptyHandler.setNext(quotedEmptyHandler).setNext(plainHandler).setNext(quotedHandler);
    }

    public ArrayList<String> parse(String string) {
        if (string == null) {
            return null;
        }
        String[] parts = string.split(";", -1);
        ArrayList<String> result = new ArrayList<>(parts.length);

        for (String part : parts) {
            String parsed = chain.handle(part);
            if (parsed == null) {
                return null;
            }
            result.add(parsed);
        }
        return result;
    }
}
