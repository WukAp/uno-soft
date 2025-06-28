package com.github.wukap.uno_soft.service.groupBuilder;

import com.github.wukap.uno_soft.model.group.Group;
import com.github.wukap.uno_soft.service.parser.ParseService;
import com.github.wukap.uno_soft.service.parser.numberHandler.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class GroupBuilderTimeTest {

    private ParseService parseService;
    private GroupBuilder groupBuilder;

    @Test
    void bigFile1() {

        NumberHandler emptyHandler = new EmptyHandler();
        NumberHandler quotedEmptyHandler = new QuotedEmptyHandler();
        NumberHandler quotedFloatHandler = new QuotedFloatHandler();
        NumberHandler quotedHandler = new QuotedNumberHandler();
        parseService = new ParseService(List.of(emptyHandler, quotedEmptyHandler, quotedHandler, quotedFloatHandler));
        groupBuilder = new GroupBuilder(parseService);

        Path testFile = Paths.get("src/test/resources/testFiles/big_test.txt");
        Long startTime = System.currentTimeMillis();
        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        Long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
        log.info("Time: {} sec, Groups: {}", duration, groups.size());
    }

    @Test
    void bigFile2() {
        NumberHandler emptyHandler = new EmptyHandler();
        NumberHandler quotedEmptyHandler = new QuotedEmptyHandler();
        NumberHandler quotedFloatHandler = new QuotedFloatHandler();
        parseService = new ParseService(List.of(emptyHandler, quotedEmptyHandler, quotedFloatHandler));
        groupBuilder = new GroupBuilder(parseService);

        Path testFile = Paths.get("src/test/resources/testFiles/lng-big.csv");
        Long startTime = System.currentTimeMillis();
        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        Long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
        log.info("Time: {} sec, Groups: {}", duration, groups.size());
    }
}