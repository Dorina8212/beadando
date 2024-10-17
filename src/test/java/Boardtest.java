import classes.connectfour.Board;
import classes.connectfour.Disc;
import classes.connectfour.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test
    void testDropDisc() {
        Board board = new Board(6, 7);
        Position pos = board.dropDisc(0, Disc.YELLOW);
        assertEquals(new Position(5, 0), pos);
        assertEquals(Disc.YELLOW, board.getDisc(new Position(5, 0)));
    }

    @Test
    void testColumnFull() {
        Board board = new Board(6, 7);
        for (int i = 0; i < 6; i++) {
            board.dropDisc(0, Disc.YELLOW);
        }
        assertTrue(board.isColumnFull(0));
    }
}

