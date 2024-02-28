package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {

    private Map<FileProperty, Set<String>> map = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attributes) throws IOException {
        if (Files.isRegularFile(file)) {
            FileProperty fileProperty = new FileProperty(Files.size(file), file.getFileName().toString());
            map.computeIfAbsent(fileProperty, path -> new HashSet<>())
                    .add(file.toAbsolutePath().toString());
        }
        return super.visitFile(file, attributes);
    }

    public void printMap() {
        for (Map.Entry<FileProperty, Set<String>> entry : map.entrySet()) {
            Set<String> set = entry.getValue();
            if (set.size() >= 2) {
                System.out.println(entry.getKey().getName() + ", " + entry.getKey().getSize());
                set.forEach(System.out::println);
            }
        }
    }
}
