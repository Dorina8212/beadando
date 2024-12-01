package classes.connectfour;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static final String STATS_FILE = "player_stats.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        File directory = new File(".");
        File[] savedGames = directory.listFiles((dir, name) -> name.startsWith("game_state_") && name.endsWith(".txt"));


        System.out.print("Do you want to load a saved game? (yes/no): ");
        String loadSavedGameInput = scanner.nextLine();
        GameState gameState;

        if (loadSavedGameInput.equalsIgnoreCase("yes")) {

            if (savedGames != null && savedGames.length > 0) {
                System.out.println("Available saved games:");
                for (int i = 0; i < savedGames.length; i++) {
                    System.out.println(i + 1 + ". " + savedGames[i].getName());
                }

                System.out.print("Enter the number of the saved game you want to load: ");
                int gameNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                if (gameNumber >= 1 && gameNumber <= savedGames.length) {
                    String selectedFile = savedGames[gameNumber - 1].getName();
                    gameState = GameState.loadGameState(selectedFile);

                    if (gameState == null) {
                        System.out.println("Failed to load the selected game. Starting a new game.");
                        gameState = startNewGame(scanner);
                    }
                } else {
                    System.out.println("Invalid selection. Starting a new game.");
                    gameState = startNewGame(scanner);
                }
            } else {
                System.out.println("No saved games found. Starting a new game.");
                gameState = startNewGame(scanner);
            }
        } else {
            gameState = startNewGame(scanner);
        }


        while (true) {
            displayBoard(gameState.board.getGrid());
            System.out.println(gameState.getCurrentPlayerName() + "'s turn (" + gameState.getCurrentDisc() + ")");
            int col = -1;

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

                col = gameState.getAIMove();
                System.out.println("AI chose column: " + col);
            }

            Position position = gameState.dropDisc(col);
            if (gameState.isWinningMove(position)) {
                displayBoard(gameState.board.getGrid());
                System.out.println("Congratulations " + gameState.getCurrentPlayerName() + ", you win!");


                updatePlayerStats(gameState.getCurrentPlayerName());


                System.out.print("Do you want to save the game? (yes/no): ");
                String saveGameInput = scanner.nextLine();
                if (saveGameInput.equalsIgnoreCase("yes")) {
                    String newFileName = "game_state_" + UUID.randomUUID().toString() + ".txt";
                    GameState.saveBoardState(gameState.board, newFileName);
                    System.out.println("Game saved to file: " + newFileName);
                }

                break;
            }

            gameState.switchPlayer();
        }


        System.out.print("Do you want to see the high-score table? (yes/no): ");
        String showHighScoreInput = scanner.nextLine();
        if (showHighScoreInput.equalsIgnoreCase("yes")) {
            displayHighScoreTable();
        }

        scanner.close();
    }

    private static GameState startNewGame(Scanner scanner) {
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

        Board board = new Board(6, 7);
        return new GameState(board, player1, player2, player1Human, player2Human);
    }

    public static void displayBoard(Disc[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == null) {
                    System.out.print("- ");
                } else if (grid[row][col] == Disc.YELLOW) {
                    System.out.print("Y ");
                } else if (grid[row][col] == Disc.RED) {
                    System.out.print("R ");
                }
            }
            System.out.println();
        }
    }


    private static void updatePlayerStats(String playerName) {
        Map<String, Integer> stats = loadPlayerStats();
        stats.put(playerName, stats.getOrDefault(playerName, 0) + 1);
        savePlayerStats(stats);
    }


    private static Map<String, Integer> loadPlayerStats() {
        Map<String, Integer> stats = new HashMap<>();
        File file = new File(STATS_FILE);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        stats.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading stats file: " + e.getMessage());
            }
        }

        return stats;
    }


    private static void savePlayerStats(Map<String, Integer> stats) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STATS_FILE))) {
            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to stats file: " + e.getMessage());
        }
    }


    private static void displayHighScoreTable() {
        System.out.println("\n--- High Score Table ---");
        Map<String, Integer> stats = loadPlayerStats();
        if (stats.isEmpty()) {
            System.out.println("No high scores available.");
        } else {
            stats.forEach((player, wins) -> System.out.println(player + ": " + wins + " wins"));
        }
    }
}