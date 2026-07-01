package com.awinas.learning.leetcode.medium;

/*
 * Traverse only the boundary cells. Whenever a boundary cell contains 'O',
 * perform DFS (or BFS) to mark all connected 'O' cells as safe (e.g., mark them as 'T').
 * After all boundary cells have been processed, traverse the entire board and convert the remaining 'O' cells to 'X',
 * then change all 'T' cells back to 'O'.
 *
 *
1. Traverse all boundary cells.
2. If boundary cell == 'O'
      DFS/BFS
      Mark all connected O's as 'T' (Safe)
3. Traverse the entire board
      O -> X    // Surrounded region
      T -> O    // Restore safe region
 *
 */
public class S130_SurroundedRegions {

    public static void solve(char[][] board) {

        if (board == null || board.length == 0)
            return;

        int rows = board.length;
        int cols = board[0].length;

        // Traverse left and right boundaries
        for (int row = 0; row < rows; row++) {
            dfs(board, row, 0, rows, cols);
            dfs(board, row, cols - 1, rows, cols);
        }

        // Traverse top and bottom boundaries
        for (int col = 0; col < cols; col++) {
            dfs(board, 0, col, rows, cols);
            dfs(board, rows - 1, col, rows, cols);
        }

        // Flip surrounded regions
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == 'O') {
                    board[row][col] = 'X';
                } else if (board[row][col] == 'T') {
                    board[row][col] = 'O';
                }
            }
        }
    }

    private static void dfs(char[][] board, int row, int col, int rows, int cols) {

        // Boundary check
        if (row < 0 || row >= rows || col < 0 || col >= cols)
            return;

        // Stop if not O
        if (board[row][col] != 'O')
            return;

        // Mark as safe
        board[row][col] = 'T';

        dfs(board, row + 1, col, rows, cols);
        dfs(board, row - 1, col, rows, cols);
        dfs(board, row, col + 1, rows, cols);
        dfs(board, row, col - 1, rows, cols);
    }

    private static void print(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        // Test Case 1 (1x1)
        char[][] board1 = {
                {'O'}
        };

        System.out.println("Test Case 1");
        solve(board1);
        print(board1);


        // Test Case 2 (2x2)
        char[][] board2 = {
                {'X', 'X'},
                {'X', 'O'}
        };

        System.out.println("\nTest Case 2");
        solve(board2);
        print(board2);


        // Test Case 3 (3x3)
        char[][] board3 = {
                {'X', 'X', 'X'},
                {'X', 'O', 'X'},
                {'X', 'X', 'X'}
        };

        System.out.println("\nTest Case 3");
        solve(board3);
        print(board3);


        // Test Case 4 (4x4) - LeetCode Example
        char[][] board4 = {
                {'X', 'X', 'X', 'X'},
                {'X', 'O', 'O', 'X'},
                {'X', 'X', 'O', 'X'},
                {'X', 'O', 'X', 'X'}
        };

        System.out.println("\nTest Case 4");
        solve(board4);
        print(board4);


        // Test Case 5 (5x5)
        char[][] board5 = {
                {'O', 'X', 'X', 'O', 'X'},
                {'X', 'O', 'O', 'X', 'O'},
                {'X', 'X', 'O', 'O', 'X'},
                {'O', 'X', 'X', 'O', 'O'},
                {'X', 'O', 'X', 'X', 'X'}
        };

        System.out.println("\nTest Case 5");
        solve(board5);
        print(board5);


        // Test Case 6 (6x6)
        char[][] board6 = {
                {'X', 'X', 'X', 'X', 'X', 'X'},
                {'X', 'O', 'O', 'X', 'O', 'X'},
                {'X', 'O', 'X', 'O', 'O', 'X'},
                {'X', 'X', 'O', 'X', 'X', 'X'},
                {'O', 'O', 'X', 'O', 'O', 'X'},
                {'X', 'X', 'X', 'X', 'X', 'O'}
        };

        System.out.println("\nTest Case 6");
        solve(board6);
        print(board6);


        // Test Case 7 (7x7)
        char[][] board7 = {
                {'X', 'O', 'X', 'X', 'X', 'X', 'X'},
                {'X', 'O', 'O', 'X', 'O', 'O', 'X'},
                {'X', 'O', 'X', 'X', 'X', 'O', 'X'},
                {'X', 'O', 'O', 'O', 'X', 'O', 'X'},
                {'X', 'X', 'X', 'O', 'X', 'X', 'X'},
                {'O', 'O', 'X', 'O', 'O', 'O', 'X'},
                {'X', 'X', 'X', 'X', 'X', 'X', 'O'}
        };

        System.out.println("\nTest Case 7");
        print(board7);
        solve(board7);
        System.out.println("--------------");
        print(board7);
    }
}
