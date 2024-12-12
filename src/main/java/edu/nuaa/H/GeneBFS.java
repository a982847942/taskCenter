package edu.nuaa.H;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/16 11:10
 */
public class GeneBFS {
    char[] cArr = {'A', 'C', 'G', 'T'};
    public int minMutation(String startGene, String endGene, String[] bank) {
        Set<String> bankSet = new HashSet<>();
        for (String s : bank) {
            bankSet.add(s);
        }
        Deque<String> deque = new ArrayDeque<>();
        deque.addLast(startGene);
        Set<String> visited = new HashSet<>();
        visited.add(startGene);
        int step = 0;
        while (!deque.isEmpty()){
            int size = deque.size();
            step++;
            for (int i = 0; i < size; i++) {
                String s = deque.pollFirst();
                char[] cutStr = s.toCharArray();
                for (char c : cArr) {
                    for (int j = 0; j < cutStr.length; j++) {
                        char oldChar = cutStr[j];
                        if (oldChar == c)continue;
                        cutStr[j] = c;
                        String str = new String(cutStr);
                        if (bankSet.contains(str)){
                            if (str.equals(endGene)){
                                return step;
                            }
                            if (!visited.contains(str)){
                                visited.add(str);
                                deque.addLast(str);
                            }
                        }
                        cutStr[j] = oldChar;
                    }
                }
            }
        }
        return -1;
    }
}
