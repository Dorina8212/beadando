import classes.connectfour.Board;
import classes.connectfour.Disc;
import classes.connectfour.GameState;
import classes.connectfour.Position;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board(6, 7);
        System.out.print("Enter name for player 1 (Yellow): ");
        String player1 = scanner.nextLine();
        System.out.print("Enter name for player 2 (Red): ");
        String player2 = scanner.nextLine();

        GameState gameState = new GameState(board, player1, player2);

        while (true) {
            displayBoard(board.getGrid());
            System.out.println(gameState.getCurrentPlayerName() + "'s turn (" + gameState.getCurrentDisc() + ")");
            System.out.print("Choose a column (0-6): ");
            int col = scanner.nextInt();

            if (board.isColumnFull(col)) {
                System.out.println("Column is full! Choose another.");
                continue;
            }

            Position position = gameState.dropDisc(col);

            // Check for a win
            if (gameState.isWinningMove(position)) {
                displayBoard(board.getGrid());
                System.out.println("Congratulations " + gameState.getCurrentPlayerName() + ", you win!");
                break;
            }

            // Switch players
            gameState.switchPlayer();
        }

        scanner.close();
    }
    // Method to display the grid
    public static void displayBoard(Disc[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == null) {
                    System.out.print("- ");  // Empty spot
                } else {
                    System.out.print(grid[row][col] + " ");  // Print disc (Yellow or Red)
                }
            }
            System.out.println();  // Newline after each row
        }
    }
}

