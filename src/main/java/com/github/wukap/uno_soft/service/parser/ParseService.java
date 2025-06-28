package com.github.wukap.uno_soft.service.parser;

import com.github.wukap.uno_soft.service.parser.numberHandler.NumberHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParseService {
    private final NumberHandler chain;

    public ParseService(List<NumberHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            throw new IllegalArgumentException("Handlers chain cannot be empty");
        }

        NumberHandler current = handlers.getFirst();
        for (int i = handlers.size() - 1; i > 0; i--) {
            current = current.setNext(handlers.get(i));
        }
        this.chain = handlers.getFirst();
    }

    public List<String> parse(String string) {
        if (string == null || string.trim().isEmpty()) {
            return null;
        }
        String[] parts = string.split(";", -1);
        List<String> result = new ArrayList<>(parts.length);

        for (String part : parts) {
            String parsed = chain.handle(part.trim());
            if (parsed == null) {
                return null;
            }
            result.add(parsed);
        }
        if (result.isEmpty() || result.stream().allMatch(String::isEmpty)) {
            return null;
        }
        return result;
    }
}