package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

public class Analysis {
    public void unavailable(String source, String target) {
        AtomicBoolean flag = new AtomicBoolean(false);
        try (BufferedReader reader = new BufferedReader(new FileReader(source));
             PrintWriter writer = new PrintWriter(target)) {
            writer.println(target.split("\\.")[1]);
            reader.lines()
                    .forEachOrdered(line -> {
                        String[] buff = line.split(" ");
                        if (("400".equals(buff[0]) || "500".equals(buff[0])) && !flag.get()) {
                            writer.print(buff[1] + ";");
                            flag.set(true);
                        } else if (("200".equals(buff[0]) || "300".equals(buff[0])) && flag.get()) {
                            writer.print(buff[1] + ";\n");
                            flag.set(false);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
    }
}
