package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Analysis {
    public void unavailable(String source, String target) {
        List<String> resultList = new ArrayList<>();
        List<String> list = null;
        StringBuilder sb = null;
        boolean flag = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            list = reader.lines().toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s : list) {
            if ((s.startsWith("400") || s.startsWith("500")) && !flag) {
                sb = new StringBuilder();
                sb.append(s.split(" ")[1]);
                sb.append(";");
                flag = true;
            }
            if (flag && (s.startsWith("200") || s.startsWith("300"))) {
                sb.append(s.split(" ")[1]);
                sb.append(";");
                resultList.add(sb.toString());
                flag = false;
            }
        }
        try (PrintWriter writer = new PrintWriter(target)) {
            writer.println(target.split("\\.")[1]);
            for (String s : resultList) {
                writer.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
    }
}
