package com.github.wukap.uno_soft.service.parser;

import com.github.wukap.uno_soft.service.parser.numberHandler.EmptyHandler;
import com.github.wukap.uno_soft.service.parser.numberHandler.PlainNumberHandler;
import com.github.wukap.uno_soft.service.parser.numberHandler.QuotedEmptyHandler;
import com.github.wukap.uno_soft.service.parser.numberHandler.QuotedNumberHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ParseServiceTest {

    private ParseService parseService;

    @BeforeEach
    void setUp() {
        QuotedEmptyHandler quotedEmptyHandler = new QuotedEmptyHandler();
        QuotedNumberHandler quotedHandler = new QuotedNumberHandler();
        PlainNumberHandler plainHandler = new PlainNumberHandler();
        EmptyHandler emptyHandler = new EmptyHandler();
        parseService = new ParseService(emptyHandler, quotedEmptyHandler, plainHandler, quotedHandler);
    }

    static Stream<Object[]> validInputProvider() {
        return Stream.of(
                new Object[]{"111", List.of("111")},
                new Object[]{"\"222\"", List.of("222")},
                new Object[]{"111;123;222", List.of("111", "123", "222")},
                new Object[]{"\"111\";\"123\";\"222\"", List.of("111", "123", "222")},
                new Object[]{"111;\"222\"", List.of("111", "222")},
                new Object[]{"\"111\";222", List.of("111", "222")},
                new Object[]{"", List.of("")},
                new Object[]{";;;", List.of("", "", "", "")},
                new Object[]{"\"\"", List.of("")},
                new Object[]{"\"\";\"\";\"\"", List.of("", "", "")},
                new Object[]{"300;;100", List.of("300", "", "100")},
                new Object[]{"200;123;100", List.of("200", "123", "100")},
                new Object[]{"\"200\";\"123\";\"100\"", List.of("200", "123", "100")},
                new Object[]{"\"\";\"79076513686\";\"79499289445\";\"79895211259\";\"79970144607\";\"79460148141\";\"79124811542\";\"79660572200\";\"79245307223\";\"79220239511\"",
                        List.of("", "79076513686", "79499289445", "79895211259", "79970144607", "79460148141", "79124811542", "79660572200", "79245307223", "79220239511")},
                new Object[]{"\"79270937799\";\"79978621736\";\"79060904919\";\"79038244103\";\"\";\"79689733676\";\"79638068069\";\"\";\"79860010266\";\"79225375201\"",
                        List.of("79270937799", "79978621736", "79060904919", "79038244103", "", "79689733676", "79638068069", "", "79860010266", "79225375201")}
        );
    }

    @ParameterizedTest
    @MethodSource("validInputProvider")
    void validInputs(String input, List<String> expected) {
        assertEquals(expected, parseService.parse(input));
    }

    static Stream<String> invalidInputProvider() {
        return Stream.of(
                " \"8383\"200000741652251\"",
                "\"79855053897\"83100000580443402\";\"200000133000191\"",
                "\"123",
                "123\"",
                "\"\"123\"",
                "12\"3",
                "abc",
                "123abc",
                "12 34",
                "\"12 34\"",
                "123;abc",
                "\"123\";abc"
        );
    }

    @ParameterizedTest
    @MethodSource("invalidInputProvider")
    void invalidInputs(String input) {
        assertNull(parseService.parse(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void nullOrWhitespaceInputs(String input) {
        if (input == null) {
            assertNull(parseService.parse(null));
        } else {
            assertEquals(List.of(""), parseService.parse(input));
        }
    }
}