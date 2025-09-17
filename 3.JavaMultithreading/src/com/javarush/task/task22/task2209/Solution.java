package com.javarush.task.task22.task2209;

import org.hibernate.engine.jdbc.ReaderInputStream;

import javax.swing.text.StyledEditorKit;
import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/* 
Составить цепочку слов
*/

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(scanner.nextLine()))) {
            while (reader.ready()) {
                lines.addAll(Arrays.stream(reader.readLine().split(" ")).toList());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        StringBuilder result = getLine(lines.toArray(String[]::new));
        System.out.println(result.toString());
    }

    public static StringBuilder getLine(String... words) {
        List<StringBuilder> builderList = new ArrayList<>();

        for (String word : words) {
            StringBuilder builder = new StringBuilder(word);

            List<String> list = new ArrayList<>(Arrays.asList(words));
            list.remove(word);
            int count = 0;
            while (!list.isEmpty()) {
                String firstStart = builder.substring(0, 1);
                String firstEnd = builder.substring(builder.length() - 1);
                String secondFirst = list.get(0).substring(0, 1);
                String secondEnd = list.get(0).substring(list.get(0).length() - 1);

                if (firstEnd.equalsIgnoreCase(secondFirst)) {
                    builder.append(" ").append(list.get(0));
                } else if (secondEnd.equalsIgnoreCase(firstStart)) {
                    builder.insert(0, list.get(0) + " ");
                } else {
                    count++;
                    String string = list.get(0);
                    list.add(string);

                    if (count >= words.length * words.length) {
                        break;
                    }
                }

                list.remove(0);
            }

            builderList.add(builder);
        }
        return builderList.stream().max(Comparator.comparingInt(StringBuilder::length)).get();
    }
}
