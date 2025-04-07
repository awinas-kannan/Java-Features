package com.awinas.learning.leetcode;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/*
 * Snake & Snake Positions
 * Food & Food Positions
 * 
 * Declare a Variable for Snake / Empty Space / Food and Init it with a Character
 *  
 * Intialize the Rows and columns
 * 
 * Initialize and Fill a board of 2 * 2 matrix with Empty Spaces
 * 
 * Print the board a see if it looks good
 * 
 * Place the Snake in the Center of the board 
 * Have a separate LinkedList for Add the head and removing the Tail
 * 
 * Track the Food Pos in Int Array
 * 
 * Place the Food only when the New X Y is having EMPTY SPACE
 * 
 * Track a Variable for Game OVer 
 * 
 * Trace the Direction of a Snake using a variable. Init it with R
 * 
 * How the Move Logic should work
 * 
 * -> Get the First Position of the snake from the linked list
 * -> Create new X and new Y based on the Direction
 * 
 * -> Check if the new X and Y cause collition with board and snake...
 *    -> If yes -> Game Over
 *    -> Else add the new Position in the board and add the First in the snake
 * 
 * -> Not check when the Snake ate the food
 *   -> If Food Pos = new X & Y , 
 *        then name has eaten the food,, Place a new Food
 *   
 *   -> Else remove the tail from Snake List and keep emplyt int he board 
 *   
 *  
 */
public class SnakeGame {
	private static final int ROWS = 10; // Board height
	private static final int COLS = 10; // Board width
	private static final char EMPTY = '.';
	private static final char SNAKE = 'O';
	private static final char FOOD = 'X';

	private char[][] board;
	private LinkedList<int[]> snake;
	private int[] food;
	private boolean gameOver = false;
	private char direction = 'R'; // Initial direction (R=Right, L=Left, U=Up, D=Down)

	public SnakeGame() {
		board = new char[ROWS][COLS];
		snake = new LinkedList<>();
		initializeGame();
	}

	private void initializeGame() {
		// Initialize empty board
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				board[i][j] = EMPTY;
			}
		}

		// Initialize snake at the center
		int startX = ROWS / 2;
		int startY = COLS / 2;
		snake.add(new int[] { startX, startY });
		board[startX][startY] = SNAKE;

		// Place initial food
		placeFood();
	}

	private void placeFood() {
		Random rand = new Random();
		int x, y;
		do {
			x = rand.nextInt(ROWS);
			y = rand.nextInt(COLS);
		} while (board[x][y] != EMPTY); // Ensure food is placed in an empty cell

		food = new int[] { x, y };
		board[x][y] = FOOD;
	}

	public void printBoard() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void move() {
		if (gameOver) {
			System.out.println("Game Over!");
			return;
		}

		// Get current head position
		int[] head = snake.getFirst();
		int newX = head[0], newY = head[1];

		// Determine new head position based on direction
		switch (direction) {
		case 'U':
			newX--;
			break;
		case 'D':
			newX++;
			break;
		case 'L':
			newY--;
			break;
		case 'R':
			newY++;
			break;
		}

		// Check for collisions (wall or self)
		if (newX < 0 || newX >= ROWS || newY < 0 || newY >= COLS || board[newX][newY] == SNAKE) {
			gameOver = true;
			return;
		}

		// Move the snake
		snake.addFirst(new int[] { newX, newY });
		board[newX][newY] = SNAKE;

		// Check if snake eats food
		boolean eatFood = (newX == food[0] && newY == food[1]);
		
		if (!eatFood) {
			// Remove tail if no food eaten
			int[] tail = snake.removeLast();
			board[tail[0]][tail[1]] = EMPTY;
		} else {
			// Place new food
			placeFood();
		}
	}

	public void setDirection(char newDirection) {
		// Prevent reversing direction
		if ((direction == 'U' && newDirection == 'D') || (direction == 'D' && newDirection == 'U')
				|| (direction == 'L' && newDirection == 'R') || (direction == 'R' && newDirection == 'L')) {
			return;
		}
		direction = newDirection;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public static void main(String[] args) {
		SnakeGame game = new SnakeGame();
		Scanner scanner = new Scanner(System.in);

		// Game loop
		while (!game.isGameOver()) {
			game.printBoard();
			System.out.println("Enter direction (W=Up, S=Down, A=Left, D=Right): ");
			char input = scanner.next().charAt(0);

			switch (input) {
			case 'W':
				game.setDirection('U');
				break;
			case 'S':
				game.setDirection('D');
				break;
			case 'A':
				game.setDirection('L');
				break;
			case 'D':
				game.setDirection('R');
				break;
			}

			game.move();
		}

		System.out.println("Game Over! Final Board:");
		game.printBoard();
		scanner.close();
	}
}
