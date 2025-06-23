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
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class GroupBuilderTest {

    private ParseService parseService;
    private GroupBuilder groupBuilder;

    @BeforeEach
    void setUp() {
        EmptyHandler emptyHandler = new EmptyHandler();
        QuotedEmptyHandler quotedEmptyHandler = new QuotedEmptyHandler();
        PlainNumberHandler plainHandler = new PlainNumberHandler();
        QuotedNumberHandler quotedHandler = new QuotedNumberHandler();
        parseService = new ParseService(emptyHandler, quotedEmptyHandler, plainHandler, quotedHandler);
        groupBuilder = new GroupBuilder(parseService);
    }

    @Test
    void singleGroup1(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("test1.txt");
        Files.write(testFile, new ArrayList<>(List.of(
                "111;123;222"
        )));

        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        assertEquals(1, groups.size());
        assertEquals(1, groups.get(0).getLength());
    }

    @Test
    void singleGroup2(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("test2.txt");
        Files.write(testFile, new ArrayList<>(List.of(
                "111;123;222",// Group 1
                "200;123;100",// Group 1 (via 123)
                "300;;100"   // Group 1 (via 100)
        )));
        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        assertEquals(1, groups.size());
        assertEquals(3, groups.get(0).getLength());
    }

    @Test
    void severalGroups1(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("merge.txt");
        Files.write(testFile, new ArrayList<>(List.of(
                "111;123;222",  // Group 1
                "200;123;100",   // Group 1 (via 123)
                "300;;100",      // Group 1 (via 100)
                "400;500;600",   // Group 2
                "700;500;800"    // Group 2 (via 500)
        )));

        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        assertEquals(2, groups.size());
        assertTrue(groups.stream().anyMatch(g -> g.getLength() == 3));
        assertTrue(groups.stream().anyMatch(g -> g.getLength() == 2));
    }

    @Test
    void severalGroups2(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("test3.txt");
        Files.write(testFile, new ArrayList<>(List.of(
                "100;200;300",
                "200;300;100"
        )));

        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        assertEquals(2, groups.size());
        assertEquals(1, groups.get(0).getLength());
        assertEquals(1, groups.get(1).getLength());
    }

    @Test
    void quotedValues1(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("test4.txt");
        Files.write(testFile, new ArrayList<>(List.of(
                "\"111\";\"123\";\"222\"",
                "\"200\";\"123\";\"100\"",
                "\"300\";;\"100\""
        )));

        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        assertEquals(1, groups.size());
        assertEquals(3, groups.get(0).getLength());
    }

    @Test
    void skipEmptyConnectors(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("test4.txt");
        Files.write(testFile, new ArrayList<>(List.of(
                "\"111\";\"\";\"222\"",
                "\"200\";\"\";\"100\"",
                "\"300\";;\"100\""
        )));

        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        assertEquals(2, groups.size());
        assertEquals(1, groups.get(0).getLength());
        assertEquals(2, groups.get(1).getLength());
    }

    @Test
    void nonEqualsLinesSizes1(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("invalid.txt");
        Files.write(testFile, new ArrayList<>(List.of(
                "111;123;222",// Group 1
                "2", // Group 2
                "111;;100",// Group 1
                "111;;;;100",// Group 1
                "2;;;;2",// Group 2
                "1;1;1;1;1;1;1"// Group 3
        )));

        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        assertEquals(3, groups.size());
        assertTrue(groups.stream().anyMatch(g -> g.getLength() == 3));
        assertTrue(groups.stream().anyMatch(g -> g.getLength() == 2));
        assertTrue(groups.stream().anyMatch(g -> g.getLength() == 1));
    }

    @Test
    void nonEqualsLinesSizes2(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("invalid.txt");
        Files.write(testFile, new ArrayList<>(List.of(
                "111;123;222",// Group 1
                "2", // Group 1 (via 2)
                "111;;100",// Group 1
                "111;;;;100",// Group 1
                "2;;;;100",// Group 1 (via 100)
                "1;1;1;1;1;1;1"// Group 3
        )));

        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        assertEquals(2, groups.size());
        assertTrue(groups.stream().anyMatch(g -> g.getLength() == 5));
        assertTrue(groups.stream().anyMatch(g -> g.getLength() == 1));
    }

    @Test
    void skipInvalidLines1(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("invalid.txt");
        Files.write(testFile, new ArrayList<>(List.of(
                "111;123;222",// Group 1
                "\"8383\"200000741652251\"", // invalid
                "111;;100"// Group 1
        )));

        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        assertEquals(1, groups.size());
        assertEquals(2, groups.get(0).getLength());
    }

    @Test
    void skipInvalidLines2(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("invalid.txt");
        Files.write(testFile, new ArrayList<>(List.of(
                "111;123;222",// Group 1
                "\"838aaaaaaa00000741652251\"", // invalid
                "\"83bbbb0000741652251\"", // invalid
                "\"83   0000741652251\"", // invalid
                "111;;100"// Group 1
        )));

        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        assertEquals(1, groups.size());
        assertEquals(2, groups.get(0).getLength());
    }

    @Test
    void emptyFile(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("empty.txt");
        Files.write(testFile, new ArrayList<>(List.of()));

        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        assertTrue(groups.isEmpty());
    }

    @Test
    void removeDuplicateLines(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("duplicates.txt");
        Files.write(testFile, new ArrayList<>(List.of(
                "100;200;300",
                "100;200;300", // duplicate
                "200;300;100"
        )));

        List<Group> groups = groupBuilder.getGroups(testFile.toString());
        assertEquals(2, groups.size());
        assertEquals(1, groups.get(0).getLength());
        assertEquals(1, groups.get(1).getLength());
    }
}