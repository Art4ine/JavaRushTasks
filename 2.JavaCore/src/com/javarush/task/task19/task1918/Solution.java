package com.javarush.task.task19.task1918;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/* 
Знакомство с тегами
*/

public class Solution {
//    public static void main(String[] args) throws IOException {
//        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader bufferedReader = new BufferedReader((new FileReader(console.readLine())))) {
//            StringBuilder stringBuilder = new StringBuilder();
//
//            while (bufferedReader.ready()) {
//                stringBuilder.append(bufferedReader.readLine());
//            }
//
//            Document document = Jsoup.parse(stringBuilder.toString(), "", Parser.xmlParser());
//            Elements elements = document.select(args[0]);
//
//            elements.forEach(System.out::println);
//        }
//    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader bufferedReader = new BufferedReader((new FileReader(console.readLine())))) {
            StringBuilder stringBuilder = new StringBuilder();

            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.readLine());
            }

            String line = stringBuilder.toString();

            int tagIndex = line.indexOf("<" + args[0]);
            ArrayList<Integer> tagList = new ArrayList<>();

            while (tagIndex != -1) {
                tagList.add(tagIndex);

                tagIndex = line.indexOf("<"+args[0], tagIndex+1);
            }

            for (int startIndex : tagList) {
                int lastOpenTagIndex = startIndex;
                int lastCloseTagIndex = line.indexOf("</"+args[0], lastOpenTagIndex);

                while (true) {
                    int next = line.indexOf("<"+args[0], lastOpenTagIndex+1);
                    if (next < lastCloseTagIndex && lastOpenTagIndex < next) {
                        lastOpenTagIndex = line.indexOf("<"+args[0], lastOpenTagIndex+1);
                        lastCloseTagIndex = line.indexOf("</"+args[0], lastCloseTagIndex+1);
                    } else {
                        System.out.println(line.substring(startIndex, lastCloseTagIndex+3+args[0].length()));
                        break;
                    }
                }
            }
        }
    }
}
