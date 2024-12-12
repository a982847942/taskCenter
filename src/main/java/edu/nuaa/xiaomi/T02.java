package edu.nuaa.xiaomi;

import java.util.Scanner;

/**
 * @author brain
 * @version 1.0
 * @date 2024/9/19 16:45
 */
public class T02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int i = 0; i < T; i++) {
            int n = sc.nextInt();
            int[] a = new int[n];
            int[] b = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = sc.nextInt();
            }
            for (int j = 0; j < n; j++) {
                b[j] = sc.nextInt();
            }
            process(a, b);
        }
    }

    public static void process(int[] a, int[] b) {
        int len = a.length;
        int[] help = new int[len];
        help[0] = a[0] <= b[0] ? a[0] :b[0];
        for (int i = 1; i < len; i++) {
            int min = a[i] <= b[i] ? a[i] : b[i];
            if (min >= a[i - 1]){
                help[i] = min;
            }else {
                help[i] = min == a[i] ? b[i] : a[i];
            }
        }
        boolean flag1 = true;
        for (int i = 1; i < len; i++) {
            if (help[i] < help[i - 1]) {
                flag1 = false;
               break;
            }
        }

        help[0] = a[0] <= b[0] ? b[0] : a[0];
        for (int i = 1; i < len; i++) {
            int max = a[i] <= b[i] ? b[i] : a[i];
            if (max <= a[i - 1]){
                help[i] = max;
            }else {
                help[i] = max == a[i] ? b[i] : a[i];
            }
        }
        boolean flag2 = true;
        for (int i = 1; i < len; i++) {
            if (help[i] > help[i - 1]) {
                flag2 = false;
                break;
            }
        }
       if (flag1 || flag2){
           System.out.println("YES");
       }else {
           System.out.println("NO");
       }
    }
}
