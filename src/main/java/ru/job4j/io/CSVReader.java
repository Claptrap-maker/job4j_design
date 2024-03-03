package ru.job4j.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CSVReader {

    public static ArrayList<String> array = new ArrayList<>();

    public static void handle(ArgsName argsName) throws Exception {
        array.addAll(Arrays.asList(argsName.get("filter").split(",")));
        boolean isFirstString = true;
        int[] indexes = new int[array.size()];
        StringBuilder builder = new StringBuilder();
        try (var scanner = new Scanner(new FileInputStream(argsName.get("path")))) {
            while (scanner.hasNext()) {
                String value = scanner.nextLine();
                ArrayList<String> values = new ArrayList<>(Arrays.asList(value.split(argsName.get("delimiter"))));
                if (isFirstString) {
                    for (int i = 0; i < array.size(); i++) {
                        if (values.contains(array.get(i))) {
                            indexes[i] = values.indexOf(array.get(i));
                        }
                    }
                    isFirstString = false;
                }
                for (int i = 0; i < indexes.length; i++) {
                    builder.append(values.get(indexes[i]));
                    if (i != indexes.length - 1) {
                        builder.append(argsName.get("delimiter"));
                    }
                }
                builder.append(System.lineSeparator());
            }
        }
        if ("stdout".equals(argsName.get("out"))) {
            System.out.print(builder);
        } else {
            try (PrintStream writer = new PrintStream(new FileOutputStream(argsName.get("out")))) {
                writer.print(builder);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ArgsName argsName = ArgsName.of(args);
        validate("path", argsName.get("path"));
        handle(argsName);
    }

    private static void validate(String key, String value) {
        if ("path".equals(key)) {
            if (Files.notExists(Path.of(value))) {
                throw new IllegalArgumentException("This file does not exist");
            }
            if (!value.endsWith(".csv")) {
                throw new IllegalArgumentException("Wrong extension format");
            }
        }
    }
}