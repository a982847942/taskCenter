package edu.nuaa.ry;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/8 18:21
 */
public class T02 {
    public static void main(String[] args) {
        int mod = (int)1e9 + 7;
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int N = sc.nextInt();
            int L = sc.nextInt();
            if (N == 0 && L == 0){
                break;
            }
            long ans = 0;
            long temp = 1;
            for (int i = 1; i <= L; i++) {
                temp = (temp * N) % mod;
                ans = (ans + temp) % mod;
            }
            System.out.println(ans);
        }
    }
}

