import classes.connectfour.Board;
import classes.connectfour.Disc;
import classes.connectfour.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;
    private final int rows = 6;
    private final int cols = 7;

    @BeforeEach
    public void setUp() {
        board = new Board(rows, cols);
    }

    @Test
    public void testBoardInitialization() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                assertNull(board.getDisc(new Position(row, col)));
            }
        }
    }

    @Test
    public void testDropDisc() {
        Position position = board.dropDisc(0, Disc.RED);
        assertEquals(5, position.getRow());
        assertEquals(0, position.getCol());
        assertEquals(Disc.RED, board.getDisc(position));
    }

    @Test
    public void testDropDiscFullColumn() {
        for (int i = 0; i < rows; i++) {
            board.dropDisc(0, Disc.RED);
        }
        assertThrows(IllegalArgumentException.class, () -> board.dropDisc(0, Disc.RED));
    }

    @Test
    public void testIsColumnFull() {
        assertFalse(board.isColumnFull(0));

    }
}