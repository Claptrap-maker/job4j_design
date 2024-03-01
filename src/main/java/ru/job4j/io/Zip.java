package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public void packFiles(List<Path> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (Path source : sources) {
                zip.putNextEntry(new ZipEntry(source.toString()));
                try (BufferedInputStream output = new BufferedInputStream(new FileInputStream(source.toFile()))) {
                    zip.write(output.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            zip.putNextEntry(new ZipEntry(source.getPath()));
            try (BufferedInputStream output = new BufferedInputStream(new FileInputStream(source))) {
                zip.write(output.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArgsName argsName = ArgsName.of(args);
        validate("d", argsName.get("d"));
        validate("e", argsName.get("e"));
        validate("o", argsName.get("o"));
        List<Path> paths = new ArrayList<>();
        try {
            paths = Search.search(Path.of(argsName.get("d")),
                    (k -> !k.getFileName().toString().contains(argsName.get("e"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Zip zip = new Zip();
        zip.packFiles(paths, new File(argsName.get("o")));
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
}
