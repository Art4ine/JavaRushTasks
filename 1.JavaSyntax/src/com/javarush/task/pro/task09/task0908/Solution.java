package com.javarush.task.pro.task09.task0908;

import java.util.Arrays;
import java.util.regex.Pattern;

/* 
Двоично-шестнадцатеричный конвертер
*/

public class Solution {
    public  static final String HEX = "0123456789abcdef";
    public static final String[] BINARY = {
            "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
            "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"
    };

    public static void main(String[] args) {
        String binaryNumber = "100111010000";
        System.out.println("Двоичное число " + binaryNumber + " равно шестнадцатеричному числу " + toHex(binaryNumber));
        String hexNumber = "9d0";
        System.out.println("Шестнадцатеричное число " + hexNumber + " равно двоичному числу " + toBinary(hexNumber));
    }

    public static String toHex(String binaryNumber) {
        if (binaryNumber == null || binaryNumber.isEmpty() || !binaryNumber.matches("[01]+")) {
            return "";
        }

        while (binaryNumber.length() % 4 != 0) {
            binaryNumber = "0" + binaryNumber;
        }

        StringBuilder hexNumber = new StringBuilder();
        for (int i = 0; i < binaryNumber.length(); i += 4) {
            String fourBit = binaryNumber.substring(i, i + 4);

            int index = Arrays.binarySearch(BINARY, fourBit);
            hexNumber.append(HEX.charAt(index));
        }

        return hexNumber.toString();
    }

    public static String toBinary(String hexNumber) {
        if (hexNumber == null || hexNumber.isEmpty() || !hexNumber.matches("[0-9a-f]+")) {
            return "";
        }

        StringBuilder binaryNumber = new StringBuilder();
        for (char aChar : hexNumber.toCharArray()) {
            int index = HEX.indexOf(aChar);

            binaryNumber.append(BINARY[index]);
        }

        return binaryNumber.toString();
    }
}