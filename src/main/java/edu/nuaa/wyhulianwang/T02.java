package edu.nuaa.wyhulianwang;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author brain
 * @version 1.0
 * @date 2024/9/21 19:21
 */
public class T02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] str = sc.next().split(",");
        int[] arr = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            arr[i] = str[i].charAt(0) - '0';
        }
        int maxPos = 0;
        boolean flag = true;
        for (int i = 0; i < arr.length; i++) {
            if (maxPos < i){
//                System.out.println("false");
                flag = false;
                break;
            }
            maxPos = Math.max(maxPos, arr[i] + i);
        }
        if (!flag){
            System.out.println("false");
        }else {
            System.out.println(maxPos >= arr.length - 1 ? "true" : "false");
        }
    }
}
