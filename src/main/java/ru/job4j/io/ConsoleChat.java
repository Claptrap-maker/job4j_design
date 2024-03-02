package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    private String randomPhrase(List<String> answers) {
        Random random = new Random();
        int randomNumber = random.nextInt(answers.size());
        return answers.get(randomNumber);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        List<String> log = new ArrayList<>();
        List<String> phrases = readPhrases();
        String userWord = "";
        boolean isContinue = true;
        while (!OUT.equals(userWord)) {
            userWord = scanner.nextLine();
            log.add(userWord);
            if (CONTINUE.equals(userWord)) {
                isContinue = true;
                continue;
            }
            if (STOP.equals(userWord)) {
                isContinue = false;
                continue;
            }
            if (OUT.equals(userWord)) {
                saveLog(log);
                continue;
            }
            if (isContinue) {
                String botAnswer = randomPhrase(phrases);
                System.out.println(botAnswer);
                log.add(botAnswer);
            }
        }
    }

    private List<String> readPhrases() {
        List<String> phrases = new ArrayList<>();
        try (BufferedReader reader =
                new BufferedReader(new FileReader(botAnswers, StandardCharsets.UTF_8))) {
            reader.lines()
                    .forEach(phrases::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return phrases;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(path, StandardCharsets.UTF_8))) {
            log.forEach(writer::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsoleChat consoleChat = new ConsoleChat("data/consoleLog.txt", "data/botAnswers.txt");
        consoleChat.run();
    }
}