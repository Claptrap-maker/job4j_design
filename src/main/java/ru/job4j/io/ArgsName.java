package ru.job4j.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ArgsName {
    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("This key: '" + key + "' is missing");
        }
        return values.get(key);
    }

    private void parse(String[] args) {
        for (String s : args) {
            int index = s.indexOf('=');
            String key = s.substring(1, index);
            String value = s.substring(index + 1);
            validate(key, value);
            values.put(key, value);
        }
    }

    private static void validate(String key, String value) {
        switch (key) {
            case "d":
                if (Files.notExists(Path.of(value))) {
                    throw new IllegalArgumentException("The directory to be archived does not exist");
                }
                break;
            case "e":
                if (!value.startsWith(".")) {
                    throw new IllegalArgumentException("The extension has wrong format");
                }
                break;
            case "o":
                if (!value.contains(".zip")) {
                    throw new IllegalArgumentException("The archive name does not have '.zip' extension");
                }
                break;
            default:
                break;
        }
    }

    public static ArgsName of(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Wrong number of arguments");
        }
        for (String s : args) {
            if (!s.startsWith("-")) {
                throw new IllegalArgumentException("Error: This argument '" + s + "' does not start with a '-' character");
            }
            if (!s.contains("=")) {
                throw new IllegalArgumentException("Error: This argument '" + s + "' does not contain an equal sign");
            }
            if (s.startsWith("-=")) {
                throw new IllegalArgumentException("Error: This argument '" + s + "' does not contain a key");
            }
            if (s.indexOf('=') == s.length() - 1) {
                throw new IllegalArgumentException("Error: This argument '" + s + "' does not contain a value");
            }
        }
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[] {"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[] {"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }
}
