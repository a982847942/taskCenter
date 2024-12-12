package edu.nuaa.wangyi;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author brain
 * @version 1.0
 * @date 2024/9/21 9:54
 */
public class T03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] arr = new int[n][4];
        for (int i = 0; i < n; i++) {
            arr[i][0] =sc.nextInt();
            arr[i][1] = sc.nextInt();
            arr[i][2] = sc.nextInt();
            arr[i][3] = sc.nextInt();
        }
        long total = 0;
        Arrays.sort(arr, (o1, o2) -> o1[0] - o2[0]);
        int curIndex = 0;
//        while(curIndex < n){
//            int curLeft = arr[curIndex][0];
//            int curMaxRight = arr[curIndex][1];
//            int curTotal = 0;
//            while (curLeft <= total && curIndex < n){
//                int curRight = arr[curIndex][1];
//                if (curRight == curMaxRight){
//                    curTotal += arr[curIndex][]
//                }
//            }
//        }
    }
}
