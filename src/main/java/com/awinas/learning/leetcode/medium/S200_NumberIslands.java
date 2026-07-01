package com.awinas.learning.leetcode.medium;

// Matrix Traversal
public class S200_NumberIslands {


    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;

        int islandCount = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == '1') {
                    islandCount++;
                    dfs(grid, row, col, rows, cols);
                }
            }
        }

        return islandCount;
    }

    private static void dfs(char[][] grid, int row, int col, int rows, int cols) {

        // Boundary Check
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return;
        }

        // Water or already visited
        if (grid[row][col] == '0') {
            return;
        }

        // Mark as visited
        grid[row][col] = '0';

        dfs(grid, row + 1, col, rows, cols); // Down
        dfs(grid, row - 1, col, rows, cols); // Up
        dfs(grid, row, col + 1, rows, cols); // Right
        dfs(grid, row, col - 1, rows, cols);  // Left
    }

    public static int numIslandsVisited(char[][] grid) {

        if (grid == null || grid.length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;

        boolean[][] visited = new boolean[rows][cols];

        int islandCount = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == '1' && !visited[row][col]) {
                    islandCount++;
                    dfs(grid, visited, row, col, rows, cols);
                }
            }
        }

        return islandCount;
    }

    private static void dfs(char[][] grid, boolean[][] visited, int row, int col, int rows, int cols) {

        // Boundary Check
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return;
        }

        // Water cell
        if (grid[row][col] == '0') {
            return;
        }

        // Already visited
        if (visited[row][col]) {
            return;
        }

        // Mark current cell as visited
        visited[row][col] = true;

        // Explore all 4 directions
        dfs(grid, visited, row + 1, col, rows, cols); // Down
        dfs(grid, visited, row - 1, col, rows, cols); // Up
        dfs(grid, visited, row, col + 1, rows, cols); // Right
        dfs(grid, visited, row, col - 1, rows, cols); // Left
    }


    public static void main(String[] args) {

        // Test Case 1
        char[][] grid1 = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };

        System.out.println("Test Case 1: " + numIslands(grid1));
        // Expected Output: 1


        // Test Case 2
        char[][] grid2 = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };

        System.out.println("Test Case 2: " + numIslands(grid2));
        // Expected Output: 3


        // Test Case 3
        char[][] grid3 = {
                {'1'}
        };

        System.out.println("Test Case 3: " + numIslands(grid3));
        // Expected Output: 1


        // Test Case 4
        char[][] grid4 = {
                {'0'}
        };

        System.out.println("Test Case 4: " + numIslands(grid4));
        // Expected Output: 0


        // Test Case 5
        char[][] grid5 = {
                {'1', '0', '1'},
                {'0', '1', '0'},
                {'1', '0', '1'}
        };

        System.out.println("Test Case 5: " + numIslands(grid5));
        // Expected Output: 5
    }
}
