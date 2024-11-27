import classes.connectfour.Board;
import classes.connectfour.Disc;
import classes.connectfour.GameState;
import classes.connectfour.Position;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Kérjük be a játékosok nevét
        System.out.print("Enter name for player 1 (Yellow): ");
        String player1 = scanner.nextLine();
        System.out.print("Is player 1 human? (yes/no): ");
        String isPlayer1Human = scanner.nextLine();
        boolean player1Human = isPlayer1Human.equalsIgnoreCase("yes");

        System.out.print("Enter name for player 2 (Red): ");
        String player2 = scanner.nextLine();
        System.out.print("Is player 2 human? (yes/no): ");
        String isPlayer2Human = scanner.nextLine();
        boolean player2Human = isPlayer2Human.equalsIgnoreCase("yes");

        // Hozzunk létre egy új Board objektumot (6x7-es tábla)
        Board board = new Board(6, 7);

        // Hozzunk létre egy új GameState objektumot a fent megadott paraméterekkel
        GameState gameState = new GameState(board, player1, player2, player1Human, player2Human);

        // A játék ciklus kezdete
        while (true) {
            displayBoard(gameState.board.getGrid());
            System.out.println(gameState.getCurrentPlayerName() + "'s turn (" + gameState.getCurrentDisc() + ")");
            int col = -1;

            // Ha az aktuális játékos emberi, akkor kérjen be oszlopot
            if (gameState.isPlayerHuman()) {
                while (true) {
                    System.out.print("Choose a column (0-" + (gameState.board.getColumns() - 1) + "): ");
                    if (scanner.hasNextInt()) {
                        col = scanner.nextInt();
                        if (col >= 0 && col < gameState.board.getColumns()) {
                            if (gameState.board.isColumnFull(col)) {
                                System.out.println("Column is full! Choose another.");
                            } else {
                                break;
                            }
                        } else {
                            System.out.println("Invalid column! Choose a number between 0 and " + (gameState.board.getColumns() - 1) + ".");
                        }
                    } else {
                        System.out.println("Invalid input! Please enter a number.");
                        scanner.next();
                    }
                }
            } else {
                // Ha AI a játékos, akkor válasszon oszlopot véletlenszerűen
                col = gameState.getAIMove();
                System.out.println("AI chose column: " + col);
            }

            // Rakjuk le a korongot az adott oszlopba
            Position position = gameState.dropDisc(col);

            // Ellenőrizzük, hogy nyert-e a játékos
            if (gameState.isWinningMove(position)) {
                displayBoard(gameState.board.getGrid());
                System.out.println("Congratulations " + gameState.getCurrentPlayerName() + ", you win!");
                break;
            }

            // Váltsunk játékost
            gameState.switchPlayer();
        }

        scanner.close();
    }

    public static void displayBoard(Disc[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == null) {
                    System.out.print("- ");
                } else if (grid[row][col] == Disc.YELLOW) {
                    System.out.print("O ");
                } else if (grid[row][col] == Disc.RED) {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }
}
