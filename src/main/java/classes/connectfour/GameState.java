package classes.connectfour;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameState {
    private final Board board;
    private final String player1;
    private final String player2;
    private int currentPlayer;
    private String winner;


    public GameState(Board board, String player1, String player2, int currentPlayer) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = currentPlayer;
        this.winner = null;
    }


    public GameState(Board board, String player1, String player2) {
        this(board, player1, player2, 0);
    }


    public String getCurrentPlayerName() {
        return currentPlayer == 0 ? player1 : player2;
    }

    public Disc getCurrentDisc() {
        return currentPlayer == 0 ? Disc.YELLOW : Disc.RED;
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


    public void saveGame(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            writer.write(player1 + "\n");
            writer.write(player2 + "\n");


            for (int row = 0; row < board.getRows(); row++) {
                for (int col = 0; col < board.getColumns(); col++) {
                    Disc disc = board.getDisc(new Position(row, col));

                    if (disc == null) {
                        writer.write("- ");
                    } else if (disc == Disc.YELLOW) {
                        writer.write("O ");
                    } else if (disc == Disc.RED) {
                        writer.write("X ");
                    }
                }
                writer.newLine();
            }


            writer.write("Winner: " + (winner != null ? winner : "None") + "\n");

            System.out.println("Game saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving the game: " + e.getMessage());
        }
    }



    public static GameState loadGame(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String player1 = reader.readLine();
            String player2 = reader.readLine();

            Board board = new Board(6, 7);
            for (int row = 0; row < board.getRows(); row++) {
                String[] line = reader.readLine().split(" ");
                for (int col = 0; col < board.getColumns(); col++) {
                    if (line[col].equals("O")) {
                        board.getGrid()[row][col] = Disc.YELLOW;
                    } else if (line[col].equals("X")) {
                        board.getGrid()[row][col] = Disc.RED;
                    }
                }
            }

            String winnerLine = reader.readLine();
            String winner = winnerLine.split(": ")[1].trim();  // A nyertes neve


            GameState gameState = new GameState(board, player1, player2);
            gameState.setWinner(winner);

            return gameState;
        } catch (IOException e) {
            System.err.println("Error loading the game: " + e.getMessage());
            return null;
        }
    }


    public Board getBoard() {
        return board;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
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



    public void setWinner(String winnerName) {
        this.winner = winnerName;
    }


    public String getWinner() {
        return winner;
    }
}
