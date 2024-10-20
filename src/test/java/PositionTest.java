import classes.connectfour.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    public void testGetRowAndCol() {
        Position position = new Position(3, 5);
        assertEquals(3, position.getRow());
        assertEquals(5, position.getCol());
    }

    @Test
    public void testEquals() {
        Position position1 = new Position(2, 4);
        Position position2 = new Position(2, 4);
        assertEquals(position1, position2);

        Position position3 = new Position(3, 5);
        assertNotEquals(position1, position3);
    }

    @Test
    public void testNotEqualsWithNullAndDifferentClass() {
        Position position = new Position(1, 2);
        assertNotEquals(null, position);

        assertNotEquals("Some String", position);
    }

    @Test
    public void testHashCode() {
        Position position1 = new Position(2, 4);
        Position position2 = new Position(2, 4);
        assertEquals(position1.hashCode(), position2.hashCode());

        Position position3 = new Position(3, 5);
        assertNotEquals(position1.hashCode(), position3.hashCode());
    }
}
