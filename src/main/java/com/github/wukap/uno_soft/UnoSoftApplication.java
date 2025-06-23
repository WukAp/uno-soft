package com.github.wukap.uno_soft;

import com.github.wukap.uno_soft.model.group.Group;
import com.github.wukap.uno_soft.service.groupBuilder.GroupBuilder;
import com.github.wukap.uno_soft.service.groupPrettyPrint.GroupPrinter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class UnoSoftApplication {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java -jar {название проекта}.jar {Полный путь к входному файлу}");
            System.exit(1);
        }
        String path = args[0];

        var context = SpringApplication.run(UnoSoftApplication.class, args);

        GroupBuilder groupBuilder = context.getBean(GroupBuilder.class);
        GroupPrinter groupPrinter = context.getBean(GroupPrinter.class);

        printGroups(groupBuilder, groupPrinter, path);
    }

    private static void printGroups(GroupBuilder groupBuilder, GroupPrinter groupPrinter, String path) {
        List<Group> groups = groupBuilder.getGroups(path);
        groupPrinter.printNonSingleSortedGroups(groups);
    }
}