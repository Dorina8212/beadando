package classes.connectfour;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class GameState {
    public final Board board;
    private final String player1;
    private final String player2;
    private final boolean isPlayer1Human;  // Player 1 is human or AI
    private final boolean isPlayer2Human;  // Player 2 is human or AI
    private int currentPlayer;
    private String winner;

    public GameState(Board board, String player1, String player2, boolean isPlayer1Human, boolean isPlayer2Human) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.isPlayer1Human = isPlayer1Human;
        this.isPlayer2Human = isPlayer2Human;
        this.currentPlayer = 0;  // Player 1 starts
        this.winner = null;
    }

    public String getCurrentPlayerName() {
        return currentPlayer == 0 ? player1 : player2;
    }

    public Disc getCurrentDisc() {
        return currentPlayer == 0 ? Disc.YELLOW : Disc.RED;
    }

    public boolean isPlayerHuman() {
        return currentPlayer == 0 ? isPlayer1Human : isPlayer2Human;
    }

    public void switchPlayer() {
        currentPlayer = 1 - currentPlayer;
    }

    public boolean isWinningMove(Position position) {
        boolean winning = board.checkWin(position, getCurrentDisc());
        if (winning) {
            this.winner = getCurrentPlayerName();
        }
        return winning;
    }

    public Position dropDisc(int col) {
        return board.dropDisc(col, getCurrentDisc());
    }

    public int getAIMove() {
        Random random = new Random();
        int col;
        do {
            col = random.nextInt(board.getColumns());
        } while (board.isColumnFull(col));
        return col;
    }

    public void setWinner(String winnerName) {
        this.winner = winnerName;
    }

    public String getWinner() {
        return winner;
    }

    public static void saveBoardState(Board board, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int row = 0; row < board.getRows(); row++) {
                for (int col = 0; col < board.getColumns(); col++) {
                    Disc disc = board.getGrid()[row][col];
                    writer.write(disc == null ? "-" : (disc == Disc.YELLOW ? "Y" : "R"));
                    writer.write(" ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameState loadGameState(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            Board board = new Board(6, 7);  // Create an empty board

            int row = 0;
            while (scanner.hasNextLine() && row < 6) {
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");
                for (int col = 0; col < tokens.length; col++) {
                    if ("Y".equals(tokens[col])) {
                        board.getGrid()[row][col] = Disc.YELLOW;
                    } else if ("R".equals(tokens[col])) {
                        board.getGrid()[row][col] = Disc.RED;
                    }
                }
                row++;
            }

            return new GameState(board, "Player 1", "Player 2", true, true);  // Default players and AI status
        } catch (FileNotFoundException e) {
            return null;
        }
    }

}
