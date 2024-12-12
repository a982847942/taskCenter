package edu.nuaa;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author brain
 * @version 1.0
 * @date 2024/5/18 19:13
 */
public class T_02 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int k = (int)(Math.sqrt(2 * n + 0.25) - 0.5);
        System.out.println(k);
        if (k * (k + 1) != 2 * n ){
            System.out.println(-1);
            return;
        }
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int temp = input.nextInt();
            map.put(temp,map.getOrDefault(temp,0) + 1);
        }
        boolean[] counts = new boolean[k + 1];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int count = entry.getValue();
            if (count > k){
                System.out.println(-1);
                return;
            }
            counts[count] = true;
        }
        for (int i = 1; i <= k; i++) {
            if (!counts[i]){
                System.out.println(-1);
                return;
            }
        }
        System.out.println(k);
    }
}
