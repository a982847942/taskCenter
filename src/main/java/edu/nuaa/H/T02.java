package edu.nuaa.H;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/16 21:15
 */
public class T02 {
    // ()(())((()(())))
    // (((())()))(())()

    /**
     * ((()(())))  11 10 1100 00
     * (((())()))  11 1100 10 00
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        System.out.println(handle(s));
    }

    public static String handle(String s) {
        if (s.length() <= 2) {
            return s;
        }
        // 拆
        int begin = 0;
        int lnum = 0;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                lnum++;
            } else if (s.charAt(i) == ')') {
                lnum--;
                if (lnum == 0) {
                    list.add(s.substring(begin, i + 1));
                    begin = i + 1;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        if (list.size() == 1) {
            int left = 0, right = s.length() - 1;
            while (left < right && s.charAt(left) == '(' && s.charAt(right) == ')') {
                left++;
                right--;
            }
            int pad = left - 1;
            for (int i = 0; i < pad; i++) {
                sb.append("(");
            }
            if (left != right) {
                sb.append(handle(s.substring(left - 1, right + 2)));
            }
            for (int i = 0; i < pad; i++) {
                sb.append(")");
            }
        } else {
            PriorityQueue<String> pq = new PriorityQueue<>((o1, o2) -> {
                return (o1 + o2).compareTo(o2 + o1);
            });
            for (int i = 0; i < list.size(); i++) {
                pq.offer(handle(list.get(i)));
            }
            while (!pq.isEmpty()) {
                sb.append(pq.poll());
            }
        }
        return sb.toString();
    }
}
