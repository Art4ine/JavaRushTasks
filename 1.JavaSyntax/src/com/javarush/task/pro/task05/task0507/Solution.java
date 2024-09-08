package com.javarush.task.pro.task05.task0507;

import java.util.Arrays;
import java.util.Scanner;

/* 
Максимальное из N чисел
*/

public class Solution {
    public static int[] array;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        array = new int[scanner.nextInt()];
        int MaxNumb = Integer.MIN_VALUE;

        for (int i = 0; i < array.length; i++) {
            array[i] = scanner.nextInt();
        }

        for (int j = 0; j < array.length; j++) {
            if (array[j] > MaxNumb) {
                MaxNumb = array[j];
            }
        }

        System.out.println(MaxNumb);
    }
}
