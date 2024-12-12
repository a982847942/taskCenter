package edu.nuaa.wangyi;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author brain
 * @version 1.0
 * @date 2024/9/21 9:17
 */
public class T01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        int[] c = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            b[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            c[i] = sc.nextInt();
        }
        int[][] diff = new int[n][2];
        for (int i = 0; i < n; i++) {
            // 差值数组
            diff[i][0] = b[i] - a[i];
            diff[i][1] = i;
        }
        Arrays.sort(diff, (o1, o2) -> o2[0] - o1[0]);
        int curTotalUsed = 0;
        long totalValue = 0;
        for (int i = 0; i < n; i++) {
            int curUsed = 0;
            int curMaxDiff = diff[i][0];
            while (curMaxDiff > 0 && curUsed < c[diff[i][1]] && curTotalUsed < k){
                totalValue += curMaxDiff;
                curUsed++;
                curTotalUsed++;
            }
        }
        System.out.println(totalValue);
    }
}
