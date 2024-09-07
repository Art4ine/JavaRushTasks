package com.javarush.task.pro.task05.task0509;

/* 
Таблица умножения
*/

import java.nio.channels.MulticastChannel;

public class Solution {

    public static int[][] MULTIPLICATION_TABLE;

    public static void main(String[] args) {
        MULTIPLICATION_TABLE = new int[10][10];

        for (int y = 1; y < 11; y++) {
            for (int x = 1; x < 11; x++) {
                MULTIPLICATION_TABLE[y-1][x-1] = y*x;

                System.out.print(y*x+" ");
            }

            System.out.println("");
        }
    }
}
