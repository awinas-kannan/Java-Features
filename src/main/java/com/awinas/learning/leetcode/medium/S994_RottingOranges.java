package com.awinas.learning.leetcode.medium;

import java.util.LinkedList;
import java.util.Queue;


// https://algo.monster/liteproblems/994

public class S994_RottingOranges {
    private static final int[] ROW_DIR = {-1, 1, 0, 0};
    private static final int[] COL_DIR = {0, 0, -1, 1};

    public static int orangesRotting(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();
        int fresh = 0;

        // Find all rotten oranges and count fresh oranges
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 2) {
                    queue.offer(new int[]{row, col});
                } else if (grid[row][col] == 1) {
                    fresh++;
                }
            }
        }

        // No fresh oranges
        if (fresh == 0) {
            return 0;
        }

        // BFS - One level = One minute
        for (int minutes = 1; !queue.isEmpty(); minutes++) {

            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {

                int[] current = queue.poll();

                int row = current[0];
                int col = current[1];

                // Visit all 4 directions
                for (int d = 0; d < 4; d++) {

                    int newRow = row + ROW_DIR[d];
                    int newCol = col + COL_DIR[d];

                    if (newRow >= 0 && newRow < rows &&
                            newCol >= 0 && newCol < cols &&
                            grid[newRow][newCol] == 1) {

                        // Rot the fresh orange
                        grid[newRow][newCol] = 2;
                        fresh--;

                        // Add newly rotten orange to queue
                        queue.offer(new int[]{newRow, newCol});

                        // Last fresh orange has rotted
                        if (fresh == 0) {
                            return minutes;
                        }
                    }
                }
            }
        }

        // Some fresh oranges could never be reached
        return -1;
    }

    public static void main(String[] args) {

        int[][] grid1 = {
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}
        };
        System.out.println(orangesRotting(grid1)); // 4


        int[][] grid2 = {
                {2, 1, 1},
                {0, 1, 1},
                {1, 0, 1}
        };
        System.out.println(orangesRotting(grid2)); // -1


        int[][] grid3 = {
                {0, 2}
        };
        System.out.println(orangesRotting(grid3)); // 0


        int[][] grid4 = {
                {2, 2, 2},
                {2, 2, 2}
        };
        System.out.println(orangesRotting(grid4)); // 0


        int[][] grid5 = {
                {2, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}
        };
        System.out.println(orangesRotting(grid5)); // 8


        int[][] grid6 = {
                {2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 2, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2}
        };
        System.out.println(orangesRotting(grid6)); // Multiple sources


        int[][] grid7 = {
                {2, 0, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 1, 0, 1},
                {1, 1, 1, 0, 1, 1, 1},
                {1, 0, 1, 1, 1, 0, 0},
                {1, 1, 1, 1, 1, 1, 1}
        };
        System.out.println(orangesRotting(grid7)); // -1 (Unreachable fresh oranges)
    }
}
