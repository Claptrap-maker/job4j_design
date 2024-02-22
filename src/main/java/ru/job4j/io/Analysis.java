package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.BiFunction;

public class Analysis {
    public void unavailable(String source, String target) {
        StringBuilder builder = new StringBuilder();
        BiFunction<String, StringBuilder, StringBuilder> function = (s, sb) -> {
            StringBuilder result = new StringBuilder();
            String[] buff = s.split(" ");
            int size = sb.toString().split(";").length;
            if (size % 2 != 0 && size != 1) {
                sb.setLength(0);
            }
            if (("400".equals(buff[0]) || "500".equals(buff[0])) && "".equals(sb.toString())) {
                sb.append(buff[1]);
                sb.append(";");
            }
            if (!"".equals(sb.toString()) && ("200".equals(buff[0]) || "300".equals(buff[0]))) {
                sb.append(buff[1]);
                sb.append(";\n");
                result = sb;
            }
            return result;
        };
        try (BufferedReader reader = new BufferedReader(new FileReader(source));
             PrintWriter writer = new PrintWriter(target)) {
            writer.println(target.split("\\.")[1]);
            reader.lines()
                    .forEachOrdered(line -> writer.print(function.apply(line, builder)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
    }
}
