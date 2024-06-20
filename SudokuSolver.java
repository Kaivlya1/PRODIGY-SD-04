import java.util.Scanner;
public class SudokuSolver {
    @SuppressWarnings("resource")
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Sudoku solver!");
        System.out.print("Please enter the size of the Sudoku puzzle (e.g., 9 for 9x9, 4 for 4x4): ");
        int size = sc.nextInt();
        sc.nextLine();  // Consume the newline character
        if (Math.sqrt(size) % 1 != 0) 
        {
            System.out.println("Invalid size. The size must be a perfect square (e.g., 4, 9, 16).");
            return;
        }
        int[][] board = new int[size][size];
        System.out.println("Please enter the Sudoku puzzle row by row.");
        System.out.println("Enter the numbers from 0 to " + size + ", where 0 represents an empty cell.");
        System.out.println("Separate the numbers with spaces.");
        for (int i = 0; i < size; i++) 
        {
            while (true) 
            {
                System.out.printf("Enter row %d: ", i + 1);
                String input = sc.nextLine().trim();
                String[] tokens = input.split("\\s+");
                if (tokens.length != size) 
                {
                    System.out.println("Invalid input. Please enter exactly " + size + " numbers separated by spaces.");
                    continue;
                }
                boolean valid = true;
                for (int j = 0; j < size; j++) 
                {
                    try 
                    {
                        board[i][j] = Integer.parseInt(tokens[j]);
                        if (board[i][j] < 0 || board[i][j] > size) 
                        {
                            valid = false;
                            break;
                        }
                    } catch (NumberFormatException e) 
                    {
                        valid = false;
                        break;
                    }
                }

                if (valid) 
                {
                    break;
                } else 
                {
                    System.out.println("Invalid input. Please enter numbers between 0 and " + size + ".");
                }
            }
        }
        System.out.println("Here is the puzzle you entered:");
        printBoard(board, size);

        if (solveSudoku(board, size)) 
        {
            System.out.println("Solved Sudoku puzzle:");
            printBoard(board, size);
        } else 
        {
            System.out.println("No solution exists.");
        }

        sc.close();
    }
    private static boolean solveSudoku(int[][] board, int size) 
    {
        int[] emptySpot = findEmptySpot(board, size);
        if (emptySpot == null) 
        {
            return true; // puzzle solved
        }
        int row = emptySpot[0];
        int col = emptySpot[1];

        for (int num = 1; num <= size; num++) {
            if (isValid(board, row, col, num, size)) 
            {
                board[row][col] = num;

                if (solveSudoku(board, size)) 
                {
                    return true;
                }

                board[row][col] = 0; // backtrack
            }
        }
        return false; // trigger backtracking
    }
    private static int[] findEmptySpot(int[][] board, int size) 
    {
        for (int row = 0; row < size; row++) 
        {
            for (int col = 0; col < size; col++) 
            {
                if (board[row][col] == 0) 
                {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }
    private static boolean isValid(int[][] board, int row, int col, int num, int size) 
    {
        for (int x = 0; x < size; x++) {
            if (board[row][x] == num || board[x][col] == num) 
            {
                return false;
            }
        }
        int boxSize = (int) Math.sqrt(size);
        int startRow = row - row % boxSize;
        int startCol = col - col % boxSize;
        for (int r = 0; r < boxSize; r++) {
            for (int c = 0; c < boxSize; c++) 
            {
                if (board[r + startRow][c + startCol] == num) 
                {
                    return false;
                }
            }
        }

        return true;
    }
    private static void printBoard(int[][] board, int size) 
    {
        for (int r = 0; r < size; r++) 
        {
            for (int d = 0; d < size; d++) 
            {
                System.out.print(board[r][d] + " ");
            }
            System.out.println();
        }
    }
}
