package edu.nuaa.wyhulianwang;

import java.util.Scanner;
import java.util.Stack;

/**
 * @author brain
 * @version 1.0
 * @date 2024/9/21 19:16
 */
public class T01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        Stack<Character> stack = new Stack<>();
        boolean flag = true;
        for (int i = 0; i < str.length(); i++) {
            char temp = str.charAt(i);
            if (temp == '('){
                stack.push(')');
            }else if (temp == '['){
                stack.push(']');
            }else if (temp == '{'){
                stack.push('}');
            }else if (stack.isEmpty() || stack.pop() != temp){
                flag = false;
                break;
            }
        }
        if (!flag){
            System.out.println("0");
        }else {
            System.out.println(stack.isEmpty() ? "0" : "1");
        }
    }
}
