package edu.nuaa.ry;

import java.util.Scanner;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/8 18:02
 */
public class T01 {
    public static void main(String[] args) {
        int mod = 1000000007;
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int N = sc.nextInt();
            int L = sc.nextInt();
            if (N == 0 && L == 0) {
                break;
            }
            int ans = 0;
            for (int i = 1; i <= L; i++) {
                int temp = ((int) Math.pow(N, i)) % mod;
                ans = (ans + temp) % mod;
            }
            System.out.println(ans);
        }
    }
}
