package com.javarush.task.pro.task11.task1104;

/* 
Конвертер строки
*/

public class Solution {
    public static void main(String[] args) {
        String string = "12.84";
        double some = Double.parseDouble(string);

        System.out.println((int) (Math.round(some)));
    }
}
