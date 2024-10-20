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
    public void setUp() {
        board = new Board(6, 7);
        gameState = new GameState(board, "Player1", "Player2");
    }

    @Test
    public void testGetCurrentPlayerName() {
        assertEquals("Player1", gameState.getCurrentPlayerName());
        gameState.switchPlayer();
        assertEquals("Player2", gameState.getCurrentPlayerName());
    }

    @Test
    public void testGetCurrentDisc() {
        assertEquals(Disc.YELLOW, gameState.getCurrentDisc());
        gameState.switchPlayer();
        assertEquals(Disc.RED, gameState.getCurrentDisc());
    }

    @Test
    public void testSwitchPlayer() {
        assertEquals("Player1", gameState.getCurrentPlayerName());
        gameState.switchPlayer();
        assertEquals("Player2", gameState.getCurrentPlayerName());
        gameState.switchPlayer();
        assertEquals("Player1", gameState.getCurrentPlayerName());
    }

    @Test
    public void testDropDisc() {
        Position position = gameState.dropDisc(0);
        assertEquals(5, position.getRow());
        assertEquals(0, position.getCol());
        assertEquals(Disc.YELLOW, board.getDisc(position));

        gameState.switchPlayer();
        position = gameState.dropDisc(0);
        assertEquals(4, position.getRow());
        assertEquals(0, position.getCol());
        assertEquals(Disc.RED, board.getDisc(position));
    }

    @Test
    public void testIsWinningMove() {
        for (int col = 0; col < 4; col++) {
            gameState.dropDisc(col);
        }
        Position lastMove = new Position(5, 3);
        assertTrue(gameState.isWinningMove(lastMove));
    }
}
