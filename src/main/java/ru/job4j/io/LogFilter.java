package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogFilter {
    private final String file;

    public LogFilter(String file) {
        this.file = file;
    }

    public List<String> filter() {
        List<String> resultList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            resultList = reader.lines().filter(s -> s.contains("\" 404 ")).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public void saveTo(String out) {
        var data = filter();
        try (PrintWriter writer = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(out)))) {
            for (String s : data) {
                writer.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LogFilter("data/log.txt").saveTo("data/404.txt");
    }
}
