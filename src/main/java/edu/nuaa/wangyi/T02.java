package edu.nuaa.wangyi;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author brain
 * @version 1.0
 * @date 2024/9/21 9:33
 */
public class T02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int q = sc.nextInt();
        int[] row = new int[n];
        Map<Integer, int[]> col = new HashMap<>();
//        int[][] matrix = new int[n][m];
//        int index = 1;
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < m; j++) {
//                matrix[i][j] = index++;
//            }
//        }
        for (int i = 0; i < n; i++) {
            row[i] = i + 1;
        }
        for (int i = 0; i < q; i++) {
            int operationType = sc.nextInt();
            int x = sc.nextInt();
            int y = sc.nextInt();
            if (operationType == 1) {
                // 交换 x y行
                swap(x, y, row);
            } else if (operationType == 2) {
                // 整体平移
                reverse(x, y, col, m);
            } else {
                // 3 要输出
                if (col.containsKey(x)){
                    System.out.println((row[x - 1] - 1) * m + col.get(x)[y - 1]);
                }else {
                    System.out.println((row[x - 1] - 1) * m + y);
                }
            }
        }
    }

    public static void swap(int x, int y, int[] row) {
        int temp = row[x - 1];
        row[x - 1] = row[y - 1];
        row[y - 1]= temp;
    }

    public static void reverse(int x, int y,  Map<Integer, int[]> column, int col) {
        y %= col;
        int[] help = new int[y];
        if (!column.containsKey(x)){
            int[] temp = new int[col];
            for (int i = 0; i < col; i++) {
                temp[i] = i + 1;
            }
            column.put(x,temp);
        }
        for (int i = 0; i < col - y; i++) {
            int temp = column.get(x)[i];
            column.get(x)[i] = temp + y;
        }
        int index = 1;
        for(int i = col - y; i < col; i++){
            column.get(x)[i] = index++;
        }
    }
}
