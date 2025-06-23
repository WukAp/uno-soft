package com.github.wukap.uno_soft.service.parser.numberHandler;

public interface NumberHandler {
    /**
     * Пытается обработать строку и извлечь число.
     * @param input Входная строка (может быть в кавычках или без)
     * @return Число в виде строки (без кавычек), или null, если обработка не удалась
     */
    String handle(String input);
    NumberHandler setNext(NumberHandler next);
}