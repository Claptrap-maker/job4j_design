package ru.job4j.io;

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
            values.put(s.substring(1, index), s.substring(index + 1));
        }
    }

    public static ArgsName of(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments not passed to program");
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