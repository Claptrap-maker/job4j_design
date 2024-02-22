package ru.job4j.io;

import java.io.File;

public class Dir {
    public static void main(String[] args) {
        File file = new File("src\\main\\java\\ru\\job4j\\io\\files\\file.txt");
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("Not exist %s", file.getAbsoluteFile()));
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException(String.format("Not file %s", file.getAbsoluteFile()));
        }
        System.out.println(String.format("size : %d", file.length()));
        System.out.println(String.format("name : %s", file.getName()));
    }
}
