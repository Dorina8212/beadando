import classes.connectfour.Board;
import classes.connectfour.Disc;
import classes.connectfour.GameState;
import classes.connectfour.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    private GameState gameState;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(6, 7);
        gameState = new GameState(board, "Player 1", "AI", true, false);
    }

    @Test
    void testInitialState() {
        assertEquals("Player 1", gameState.getCurrentPlayerName());
        assertEquals(Disc.YELLOW, gameState.getCurrentDisc());
        assertTrue(gameState.isPlayerHuman());
    }

    @Test
    void testSwitchPlayer() {
        gameState.switchPlayer();
        assertEquals("AI", gameState.getCurrentPlayerName());
        assertEquals(Disc.RED, gameState.getCurrentDisc());
        assertFalse(gameState.isPlayerHuman());
    }

    @Test
    void testDropDisc() {
        Position position = gameState.dropDisc(0);
        assertNotNull(position);
        assertEquals(5, position.getRow());
        assertEquals(0, position.getCol());
        assertEquals(Disc.YELLOW, board.getDisc(position));
    }

    @Test
    void testIsWinningMove() {
        for (int i = 5; i >= 2; i--) {
            board.dropDisc(0, Disc.YELLOW);
        }
        Position winningPosition = gameState.dropDisc(0); // Fourth disc
        assertTrue(gameState.isWinningMove(winningPosition));
        assertEquals("Player 1", gameState.getWinner());
    }

    @Test
    void testGetAIMove() {
        for (int i = 0; i < 6; i++) {
            board.dropDisc(0, Disc.RED);
        }
        int aiMove = gameState.getAIMove();
        assertTrue(aiMove >= 1 && aiMove < board.getColumns()); // Column 0 should be skipped
        assertFalse(board.isColumnFull(aiMove));
    }

    @Test
    void testAIPlaysCorrectly() {
        gameState.switchPlayer(); // Switch to AI
        assertFalse(gameState.isPlayerHuman());

        int aiMove = gameState.getAIMove();
        Position aiPosition = gameState.dropDisc(aiMove);

        assertNotNull(aiPosition);
        assertEquals(5, aiPosition.getRow());
        assertEquals(Disc.RED, board.getDisc(aiPosition));
    }
}

