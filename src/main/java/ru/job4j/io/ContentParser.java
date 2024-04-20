package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ContentParser {
    private final File file;

    public ContentParser(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (InputStream input = new FileInputStream(file)) {
            int data;
            char ch;
            while ((data = input.read()) > 0) {
                ch = (char) data;
                if (filter.test(ch)) {
                    output.append(ch);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
