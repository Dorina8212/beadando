
import classes.connectfour.Board;
import classes.connectfour.Disc;
import classes.connectfour.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testDropDisc() {
        Board board = new Board(6, 7);
        Position pos = board.dropDisc(3, Disc.YELLOW);

        assertEquals(5, pos.getRow());
        assertEquals(3, pos.getCol());
        assertEquals(Disc.YELLOW, board.getDisc(pos));
    }

    @Test
    void testColumnFull() {
        Board board = new Board(6, 7);


        for (int i = 0; i < 6; i++) {
            board.dropDisc(2, Disc.RED);
        }


        assertTrue(board.isColumnFull(2));


        assertThrows(IllegalArgumentException.class, () -> board.dropDisc(2, Disc.YELLOW));
    }

    @Test
    void testHorizontalWin() {
        Board board = new Board(6, 7);


        board.dropDisc(0, Disc.YELLOW);
        board.dropDisc(1, Disc.YELLOW);
        board.dropDisc(2, Disc.YELLOW);
        Position pos = board.dropDisc(3, Disc.YELLOW);


        assertTrue(board.checkWin(pos, Disc.YELLOW));
    }

    @Test
    void testVerticalWin() {
        Board board = new Board(6, 7);


        board.dropDisc(2, Disc.RED);
        board.dropDisc(2, Disc.RED);
        board.dropDisc(2, Disc.RED);
        Position pos = board.dropDisc(2, Disc.RED);


        assertTrue(board.checkWin(pos, Disc.RED));
    }

    @Test
    void testDiagonalWinPositiveSlope() {
        Board board = new Board(6, 7);


        board.dropDisc(0, Disc.YELLOW);
        board.dropDisc(1, Disc.RED);
        board.dropDisc(1, Disc.YELLOW);
        board.dropDisc(2, Disc.RED);
        board.dropDisc(2, Disc.RED);
        board.dropDisc(2, Disc.YELLOW);
        board.dropDisc(3, Disc.RED);
        board.dropDisc(3, Disc.RED);
        board.dropDisc(3, Disc.RED);
        Position pos = board.dropDisc(3, Disc.YELLOW);


        assertTrue(board.checkWin(pos, Disc.YELLOW));
    }

    @Test
    void testDiagonalWinNegativeSlope() {
        Board board = new Board(6, 7);


        board.dropDisc(3, Disc.RED);
        board.dropDisc(2, Disc.YELLOW);
        board.dropDisc(2, Disc.RED);
        board.dropDisc(1, Disc.YELLOW);
        board.dropDisc(1, Disc.YELLOW);
        board.dropDisc(1, Disc.RED);
        board.dropDisc(0, Disc.YELLOW);
        board.dropDisc(0, Disc.YELLOW);
        board.dropDisc(0, Disc.YELLOW);
        Position pos = board.dropDisc(0, Disc.RED);


        assertTrue(board.checkWin(pos, Disc.RED));
    }

    @Test
    void testNoWin() {
        Board board = new Board(6, 7);


        board.dropDisc(0, Disc.RED);
        board.dropDisc(1, Disc.YELLOW);
        board.dropDisc(2, Disc.RED);
        Position pos = board.dropDisc(3, Disc.YELLOW);

        assertFalse(board.checkWin(pos, Disc.RED));
        assertFalse(board.checkWin(pos, Disc.YELLOW));
    }
}
