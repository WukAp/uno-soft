package com.github.wukap.uno_soft;

import com.github.wukap.uno_soft.model.group.Group;
import com.github.wukap.uno_soft.service.groupBuilder.GroupBuilder;
import com.github.wukap.uno_soft.service.groupPrettyPrint.GroupFileWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class UnoSoftApplication {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Usage: java -jar {название проекта}.jar {Полный путь к входному файлу}");
            System.exit(1);
        }
        String path = args[0];
        var context = SpringApplication.run(UnoSoftApplication.class, args);

        GroupBuilder groupBuilder = context.getBean(GroupBuilder.class);
        GroupFileWriter groupPrinter = context.getBean(GroupFileWriter.class);

        printGroups(groupBuilder, groupPrinter, path);

    }

    private static void printGroups(GroupBuilder groupBuilder, GroupFileWriter groupPrinter, String path) throws IOException {
        List<Group> groups = groupBuilder.getGroups(path);
        groupPrinter.writeNonSingleSortedGroups(groups);
    }
}