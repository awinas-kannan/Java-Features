package com.awinas.learning.leetcode.easy;

import java.util.LinkedList;
import java.util.Queue;

public class S733_FloodFill {

    public static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {

        int rows = image.length;
        int cols = image[0].length;

        int originalColor = image[sr][sc];

        if (originalColor == newColor) {
            return image;
        }

        dfs(image, sr, sc, rows, cols, originalColor, newColor);

        return image;
    }

    private static void dfs(int[][] image, int row, int col, int rows, int cols, int originalColor, int newColor) {

        // Boundary Check
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return;
        }

        // Different color
        if (image[row][col] != originalColor) {
            return;
        }

        // Paint current cell
        image[row][col] = newColor;
        print(image);
        System.out.println("----");
        // Explore 4 directions
        dfs(image, row + 1, col, rows, cols, originalColor, newColor);
        dfs(image, row - 1, col, rows, cols, originalColor, newColor);
        dfs(image, row, col + 1, rows, cols, originalColor, newColor);
        dfs(image, row, col - 1, rows, cols, originalColor, newColor);
    }

//    // Test Case 6 (5 x 5)
//    int[][] image6 = {
//            {1, 1, 1, 0, 0},
//            {1, 0, 1, 0, 2},
//            {1, 1, 1, 2, 2},
//            {0, 0, 2, 2, 2},
//            {3, 3, 3, 2, 1}
//    };


    public static int[][] floodFillBFS(int[][] image, int sr, int sc, int newColor) {

        int rows = image.length;
        int cols = image[0].length;

        int originalColor = image[sr][sc];

        if (originalColor == newColor) {
            return image;
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});
        image[sr][sc] = newColor;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            visit(image, queue, row + 1, col, rows, cols, originalColor, newColor); // Down
            visit(image, queue, row - 1, col, rows, cols, originalColor, newColor); // Up
            visit(image, queue, row, col + 1, rows, cols, originalColor, newColor); // Right
            visit(image, queue, row, col - 1, rows, cols, originalColor, newColor); // Left
        }

        return image;
    }

    private static void visit(int[][] image, Queue<int[]> queue, int row, int col, int rows, int cols, int originalColor, int newColor) {
        // Boundary Check
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return;
        }
        // Different color
        if (image[row][col] != originalColor) {
            return;
        }
        // Paint
        image[row][col] = newColor;

        print(image);
        System.out.println("----");
        // Add to queue
        queue.offer(new int[]{row, col});
    }

    private static void print(int[][] image) {
        for (int[] row : image) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

//        // Test Case 1
//        int[][] image1 = {
//                {1, 1, 1},
//                {1, 1, 0},
//                {1, 0, 1}
//        };
//
//        System.out.println("Test Case 1");
//        print(floodFill(image1, 1, 1, 2));
//        // Expected:
//        // 2 2 2
//        // 2 2 0
//        // 2 0 1
//
//
//        // Test Case 2
//        int[][] image2 = {
//                {0, 0, 0},
//                {0, 1, 1}
//        };
//
//        System.out.println("\nTest Case 2");
//        print(floodFill(image2, 1, 1, 1));
//        // Expected:
//        // 0 0 0
//        // 0 1 1
//
//
//        // Test Case 3
//        int[][] image3 = {
//                {0, 0, 0},
//                {0, 0, 0}
//        };
//
//        System.out.println("\nTest Case 3");
//        print(floodFill(image3, 0, 0, 2));
//        // Expected:
//        // 2 2 2
//        // 2 2 2
//
//
//        // Test Case 4
//        int[][] image4 = {
//                {1}
//        };
//
//        System.out.println("\nTest Case 4");
//        print(floodFill(image4, 0, 0, 5));
//        // Expected:
//        // 5
//
//
//        // Test Case 5
//        int[][] image5 = {
//                {1, 2, 2},
//                {2, 2, 2},
//                {1, 2, 1}
//        };
//
//        System.out.println("\nTest Case 5");
//        print(floodFill(image5, 0, 1, 9));
//        // Expected:
//        // 1 9 9
//        // 9 9 9
//        // 1 9 1

        // Test Case 6 (5 x 5)
        int[][] image6 = {
                {1, 1, 1, 0, 0},
                {1, 0, 1, 0, 2},
                {1, 1, 1, 2, 2},
                {0, 0, 2, 2, 2},
                {3, 3, 3, 2, 1}
        };

        int[][] image7 = {
                {1, 1, 1, 0, 0},
                {1, 0, 1, 0, 2},
                {1, 1, 1, 2, 2},
                {0, 0, 2, 2, 2},
                {3, 3, 3, 2, 1}
        };

        System.out.println("\nTest Case 6");
        System.out.println("---- DFS  -----");
        print(image6);
        System.out.println("---- DFS SOLN START  -----");
        print(floodFill(image6, 0, 0, 9));

        System.out.println("---- BFS -----");
        print(image7);
        System.out.println("---- BFS SOLN START -----");
        print(floodFillBFS(image7, 0, 0, 9));
    }
}
