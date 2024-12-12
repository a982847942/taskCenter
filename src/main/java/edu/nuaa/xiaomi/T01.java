package edu.nuaa.xiaomi;

import java.util.Scanner;

/**
 * @author brain
 * @version 1.0
 * @date 2024/9/19 16:29
 */
public class T01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int i = 0; i < T; i++) {
            int N = sc.nextInt();
            int n = sc.nextInt();
            int c = sc.nextInt();
            int[] v = new int[n];
            int sum = 0;
            for (int j = 0; j < n; j++) {
                v[j] = sc.nextInt();
                sum += v[j];
            }
            if (sum + c < N){
                System.out.println("NO");
            }else {
                // 背包处理
                int temp = process(v, N);
                if (temp + c < N){
                    System.out.println("NO");
                }else {
                    System.out.println("YES");
                }
            }
        }
    }
    public static int process(int[] v, int capacity){
        int len = v.length;
        int[][] dp = new int[len + 1][capacity + 1];
        for (int i = 1; i <= len; i++) {
            for (int j = 0; j <= capacity; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= v[i]){
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - v[i]] + v[i]);
                }
            }
        }
        return dp[len][capacity];
    }
}
