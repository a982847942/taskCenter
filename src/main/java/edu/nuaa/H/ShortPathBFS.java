package edu.nuaa.H;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/16 11:06
 */
public class ShortPathBFS {
    static int n, m;
    static int[][] grid;
    static int[][] dist;
    static int[] dx = {0, 0, 1, -1}; // 移动方向，分别为右、左、下、上
    static int[] dy = {1, -1, 0, 0}; // 移动方向，分别为右、左、下、上

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        m = sc.nextInt();
        int sx = sc.nextInt();
        int sy = sc.nextInt();
        int ex = sc.nextInt();
        int ey = sc.nextInt();
        grid = new int[n][m];
        dist = new int[n][m];

        // 初始化距离数组为-1，表示尚未访问
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], -1);
        }

        int k = sc.nextInt(); // 不可通过的障碍点数量
        for (int i = 0; i < k; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            grid[x][y] = 1; // 设置障碍点
        }

        // 调用BFS寻找最短路径
        bfs(sx, sy);

        // 输出终点到起点的最短距离
        System.out.println(dist[ex][ey]);
    }

    // BFS算法实现
    static void bfs(int sx, int sy) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{sx, sy});
        dist[sx][sy] = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            // 遍历四个方向
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                // 判断新的位置是否在矩阵范围内，且未访问且不是障碍
                if (nx >= 0 && nx < n && ny >= 0 && ny < m && dist[nx][ny] == -1 && grid[nx][ny] == 0) {
                    queue.add(new int[]{nx, ny});
                    dist[nx][ny] = dist[x][y] + 1; // 更新距离
                }
            }
        }
    }
}
