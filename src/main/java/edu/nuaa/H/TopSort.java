package edu.nuaa.H;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/16 11:07
 */
public class TopSort {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adjacency = new ArrayList<>();
        int[] indegress = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            adjacency.add(new ArrayList<>());
        }
        for (int[] cur : prerequisites) {
            indegress[cur[0]]++;
            adjacency.get(cur[1]).add(cur[0]);
        }
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegress[i] == 0)deque.addLast(i);
        }
        while (!deque.isEmpty()){
            numCourses--;
            Integer cur = deque.pollLast();
            for (Integer temp : adjacency.get(cur)) {
                indegress[temp]--;
                if (indegress[temp] == 0)deque.addLast(temp);
            }
        }
        return numCourses == 0;
    }
}
