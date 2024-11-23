import classes.connectfour.Board;
import classes.connectfour.Disc;
import classes.connectfour.GameState;
import classes.connectfour.Position;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to load a saved game? (yes/no): ");
        String loadChoice = scanner.nextLine();

        GameState gameState;
        if (loadChoice.equalsIgnoreCase("yes")) {
            System.out.print("Enter the filename to load: ");
            String filename = scanner.nextLine();
            gameState = GameState.loadGame(filename);
            if (gameState == null) {
                System.out.println("Could not load the game. Starting a new game.");
                gameState = createNewGame(scanner);
            }
        } else {
            gameState = createNewGame(scanner);
        }

        while (true) {
            GameState.displayBoard(gameState.getBoard().getGrid());
            System.out.println(gameState.getCurrentPlayerName() + "'s turn (" + gameState.getCurrentDisc() + ")");
            int col = -1;

            while (true) {
                System.out.print("Choose a column (0-" + (gameState.getBoard().getColumns() - 1) + "): ");
                if (scanner.hasNextInt()) {
                    col = scanner.nextInt();
                    if (col >= 0 && col < gameState.getBoard().getColumns()) {
                        if (gameState.getBoard().isColumnFull(col)) {
                            System.out.println("Column is full! Choose another.");
                        } else {
                            break;
                        }
                    } else {
                        System.out.println("Invalid column! Choose a number between 0 and " + (gameState.getBoard().getColumns() - 1) + ".");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number.");
                    scanner.next();
                }
            }

            Position position = gameState.dropDisc(col);
            if (gameState.isWinningMove(position)) {
                GameState.displayBoard(gameState.getBoard().getGrid());
                System.out.println("Congratulations " + gameState.getCurrentPlayerName() + ", you win!");
                break;
            }

            gameState.switchPlayer();
        }

        // Kérdezzük meg, hogy elmentjük-e a játékot
        System.out.print("Do you want to save the game? (yes/no): ");
        String saveChoice = scanner.next();
        if (saveChoice.equalsIgnoreCase("yes")) {
            System.out.print("Enter the filename to save the game: ");
            String filename = scanner.next();
            gameState.saveGame(filename);
        }

        scanner.close();
    }

    public static GameState createNewGame(Scanner scanner) {
        System.out.print("Enter name for player 1 (Yellow): ");
        String player1 = scanner.nextLine();
        System.out.print("Enter name for player 2 (Red): ");
        String player2 = scanner.nextLine();
        Board board = new Board(6, 7);
        return new GameState(board, player1, player2);
    }
}
