package edu.nuaa.ry;

import java.util.*;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/8 18:34
 */
public class T03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input + "|" + " ";
        String[] instructions = input.split("\\|");
        int rows = 1;
        Set<Character> set = new HashSet<>();
        set.add('a');
        set.add('i');
        set.add('r');
        set.add('d');
        List<String> res = new ArrayList<>();
        boolean isError = false;
        for (String instruction : instructions) {
            if (instruction.equals(" ")) {
                break;
            }
            int curRow = instruction.charAt(0) - '0';
            char operation = instruction.charAt(2);
            String content = "";
            if (operation != 'd') {
                content = instruction.substring(4);
            }
            if (rows == 1 && operation != 'i') {
                isError = true;
                System.out.println("error");
                break;
            }
            if (curRow > rows) {
                isError = true;
                System.out.println("error");
                break;
            }
            if (!set.contains(operation)) {
                isError = true;
                System.out.println("error");
                break;
            }
            //校验结束
            if (rows == 1) {
                res.add(content);
                rows++;
            } else if (operation == 'i') {
                res.add(content);
                for (int i = rows - 1; i >= curRow; i--) {
                    res.set(i, res.get(i - 1));
                }
                res.set(curRow - 1, content);
                rows++;
            } else if (operation == 'a') {
                res.add(content);
                for (int i = rows - 1; i > curRow; i--) {
                    res.set(i, res.get(i - 1));
                }
                res.set(curRow, content);
                rows++;
            } else if (operation == 'r') {
                res.set(curRow - 1, content);
            } else if (operation == 'd') {
                if (curRow >= rows) {
                    isError = true;
                    System.out.println("error");
                    break;
                }
                for (int i = curRow; i < rows - 1; i++) {
                    if(res.size() > i + 1){
                        res.set(i - 1, res.get(i + 1));
                    }
                }
                res.remove(curRow - 1);
                rows--;
            }

        }
        if (!isError) {
            for (String s : res) {
                System.out.println(s);
            }
        }
    }
}
