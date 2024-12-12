package edu.nuaa.H;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/16 11:09
 */
public class MiGong {
    int[][] direction = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

    public int nearestExit(char[][] maze, int[] entrance) {
        Deque<int[]> deque = new ArrayDeque<>();
        deque.add(new int[]{entrance[0], entrance[1]});
        int step = 0;
        int row = maze.length;
        int col = maze[0].length;
        maze[entrance[0]][entrance[1]] = '+';
        while (!deque.isEmpty()) {
            int size = deque.size();
            step++;
            for (int i = 0; i < size; i++) {
                int[] cur = deque.pollFirst();
                for (int[] dir : direction) {
                    int nextX = cur[0] + dir[0];
                    int nextY = cur[1] + dir[1];
                    if (nextX >= 0 && nextX < row && nextY >= 0 && nextY < col && maze[nextX][nextY] == '.'){
                        if (nextX == 0 || nextX == row - 1 || nextY == 0 || nextY == col - 1){
                            return step;
                        }
                        deque.addLast(new int[]{nextX, nextY});
                        maze[nextX][nextY] = '+';
                    }
                }
            }
        }
        return -1;
    }
}
