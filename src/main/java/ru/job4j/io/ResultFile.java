package ru.job4j.io;

import java.io.*;

public class ResultFile {
    public static void main(String[] args) {
        try (PrintWriter output = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream("data/result.txt")))) {
            output.println("Hello, world!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
