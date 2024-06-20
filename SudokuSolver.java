import java.util.Scanner;

public class SudokuSolver {
    public static final int SIZE = 9;

    public static void main(String[] args) {
        int[][] board = new int[SIZE][SIZE];
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Sudoku solver!");
        System.out.println("Please enter the Sudoku puzzle row by row.");
        System.out.println("Enter the numbers from 0 to 9, where 0 represents an empty cell.");
        System.out.println("Separate the numbers with spaces.");

        for (int i = 0; i < SIZE; i++) {
            while (true) {
                System.out.printf("Enter row %d: ", i + 1);
                String input = sc.nextLine().trim();
                String[] tokens = input.split("\\s+");

                if (tokens.length != SIZE) {
                    System.out.println("Invalid input. Please enter exactly 9 numbers separated by spaces.");
                    continue;
                }

                boolean valid = true;
                for (int j = 0; j < SIZE; j++) {
                    try {
                        board[i][j] = Integer.parseInt(tokens[j]);
                        if (board[i][j] < 0 || board[i][j] > 9) {
                            valid = false;
                            break;
                        }
                    } catch (NumberFormatException e) {
                        valid = false;
                        break;
                    }
                }

                if (valid) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter numbers between 0 and 9.");
                }
            }
        }

        System.out.println("Here is the puzzle you entered:");
        printBoard(board);

        if (solveSudoku(board)) {
            System.out.println("Solved Sudoku puzzle:");
            printBoard(board);
        } else {
            System.out.println("No solution exists.");
        }

        sc.close();
    }

    private static boolean solveSudoku(int[][] board) {
        int[] emptySpot = findEmptySpot(board);
        if (emptySpot == null) {
            return true; // puzzle solved
        }

        int row = emptySpot[0];
        int col = emptySpot[1];

        for (int num = 1; num <= SIZE; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;

                if (solveSudoku(board)) {
                    return true;
                }

                board[row][col] = 0; // backtrack
            }
        }

        return false; // trigger backtracking
    }

    private static int[] findEmptySpot(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    private static boolean isValid(int[][] board, int row, int col, int num) {
        for (int x = 0; x < SIZE; x++) {
            if (board[row][x] == num || board[x][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r + startRow][c + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void printBoard(int[][] board) {
        for (int r = 0; r < SIZE; r++) {
            for (int d = 0; d < SIZE; d++) {
                System.out.print(board[r][d] + " ");
            }
            System.out.println();
        }
    }
}
