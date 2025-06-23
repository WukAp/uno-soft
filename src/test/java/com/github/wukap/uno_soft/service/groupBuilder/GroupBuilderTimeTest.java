package com.github.wukap.uno_soft.service.groupBuilder;

import com.github.wukap.uno_soft.model.group.Group;
import com.github.wukap.uno_soft.service.parser.ParseService;
import com.github.wukap.uno_soft.service.parser.numberHandler.EmptyHandler;
import com.github.wukap.uno_soft.service.parser.numberHandler.PlainNumberHandler;
import com.github.wukap.uno_soft.service.parser.numberHandler.QuotedEmptyHandler;
import com.github.wukap.uno_soft.service.parser.numberHandler.QuotedNumberHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class GroupBuilderTimeTest {

    private ParseService parseService;
    private GroupBuilder groupBuilder;

    @BeforeEach
    void setUp() {
        EmptyHandler emptyHandler = new EmptyHandler();
        QuotedEmptyHandler quotedEmptyHandler = new QuotedEmptyHandler();
        PlainNumberHandler plainHandler = new PlainNumberHandler();
        QuotedNumberHandler quotedHandler = new QuotedNumberHandler();
        parseService = new ParseService(List.of(emptyHandler, quotedEmptyHandler, plainHandler, quotedHandler));
        groupBuilder = new GroupBuilder(parseService);
    }

    @Test
    void bigFile() throws IOException {
        Path testFile = Paths.get("src/test/resources/testFiles/big_test.txt");
        Long startTime = System.currentTimeMillis();
        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        Long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
        log.info("Time: {} sec, Groups: {}", duration, groups.size());
        assertTrue(duration < 30);
    }
}