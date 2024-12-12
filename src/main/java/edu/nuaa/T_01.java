package edu.nuaa;

import java.util.Scanner;

/**
 * @author brain
 * @version 1.0
 * @date 2024/5/18 19:06
 */
public class T_01 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int size = input.nextInt();
        long sum = input.nextLong();
        int[] arr = new int[size];
        long curSum = 0;
        int index  = 0;
        for (int i = 0; i < size; i++) {
            arr[i] = input.nextInt();
            if (arr[i] == -1){
                index = i;
            }else {
                curSum += arr[i];
            }
        }
        arr[index]  = (int)(sum - curSum);
        for (int i = 0; i < size - 1; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println(arr[size - 1]);
    }
}
