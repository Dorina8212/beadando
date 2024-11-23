import classes.connectfour.Board;
import classes.connectfour.Disc;
import classes.connectfour.GameState;
import classes.connectfour.Position;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Board board = new Board(6, 7); // 6 sor, 7 oszlop
        System.out.print("Enter name for player 1 (Yellow): ");
        String player1 = scanner.nextLine();
        System.out.print("Enter name for player 2 (Red): ");
        String player2 = scanner.nextLine();

        GameState gameState = new GameState(board, player1, player2);

        while (true) {
            displayBoard(board.getGrid());
            System.out.println(gameState.getCurrentPlayerName() + "'s turn (" + gameState.getCurrentDisc() + ")");
            int col = -1;

            while (true) {
                System.out.print("Choose a column (0-" + (board.getColumns() - 1) + "): ");
                if (scanner.hasNextInt()) {
                    col = scanner.nextInt();
                    if (col >= 0 && col < board.getColumns()) {
                        if (board.isColumnFull(col)) {
                            System.out.println("Column is full! Choose another.");
                        } else {
                            break;
                        }
                    } else {
                        System.out.println("Invalid column! Choose a number between 0 and " + (board.getColumns() - 1) + ".");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number.");
                    scanner.next();
                }
            }

            Position position = gameState.dropDisc(col);
            if (gameState.isWinningMove(position)) {
                displayBoard(board.getGrid());
                System.out.println("Congratulations " + gameState.getCurrentPlayerName() + ", you win!");
                break;
            }

            gameState.switchPlayer();
        }

        scanner.close();
    }

    public static void displayBoard(Disc[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(grid[row][col] + " ");
                }
            }
            System.out.println();
        }
    }
}
