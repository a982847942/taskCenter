package edu.nuaa;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author brain
 * @version 1.0
 * @date 2024/5/18 19:49
 */
public class T_03 {
    static int res = 0;
    static StringBuilder temp;
    static Set<String> set = new HashSet<>();
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String s = input.next();
        StringBuilder sb = new StringBuilder();
        String FuYin="aeiou";
        for (int i = 0; i < s.length();i++)
        {
            if(FuYin.indexOf(s.charAt(i)) == -1)
            {
                sb.append(s.charAt(i));
            }
        }
        s= sb.toString();
        temp = new StringBuilder();
        dfs(s,0);
        System.out.println(res);
    }

    public static void dfs(String s, int index){
        if (temp.length() > 3)return;
        String cur = temp.toString();
        if (temp.length() == 3 && !set.contains(cur)){
            set.add(cur);
            res++;
            return;
        }
        if (temp.length() == 2 && !set.contains(cur)){
            set.add(cur);
            res++;
        }
        if (index >= s.length())return;
        dfs(s,index + 1);
        temp.append(s.charAt(index));
        dfs(s,index + 1);
        temp.deleteCharAt(temp.length() - 1);
    }
}
