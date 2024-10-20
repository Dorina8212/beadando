import classes.connectfour.Disc;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiscTest {

    @Test
    public void testEnumValues() {
        assertNotNull(Disc.valueOf("RED"));
        assertNotNull(Disc.valueOf("YELLOW"));
    }

    @Test
    public void testEnumComparison() {
        assertEquals(Disc.RED, Disc.valueOf("RED"));
        assertEquals(Disc.YELLOW, Disc.valueOf("YELLOW"));
    }

    @Test
    public void testEnumToString() {
        assertEquals("R", Disc.RED.toString());
        assertEquals("Y", Disc.YELLOW.toString());
    }

    @Test
    public void testInvalidEnumValue() {
        assertThrows(IllegalArgumentException.class, () -> Disc.valueOf("BLUE"));
    }
}
