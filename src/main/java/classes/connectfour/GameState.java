package classes.connectfour;

public class GameState {
    private final Board board;
    private final String player1;
    private final String player2;
    private int currentPlayer;

    public GameState(Board board, String player1, String player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = 0;
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
        return board.checkWin(position, getCurrentDisc());
    }

    public Position dropDisc(int col) {
        return board.dropDisc(col, getCurrentDisc());
    }
}

