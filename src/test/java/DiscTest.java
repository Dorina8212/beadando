import classes.connectfour.Disc;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscTest {

    @Test
    void testYellowDiscToString() {

        assertEquals("Y", Disc.YELLOW.toString());
    }

    @Test
    void testRedDiscToString() {

        assertEquals("R", Disc.RED.toString());
    }

    @Test
    void testEnumValues() {

        Disc[] expectedValues = {Disc.YELLOW, Disc.RED};
        assertArrayEquals(expectedValues, Disc.values());
    }

    @Test
    void testEnumValueOf() {

        assertEquals(Disc.YELLOW, Disc.valueOf("YELLOW"));
        assertEquals(Disc.RED, Disc.valueOf("RED"));
    }
}
