package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    public void constructorTest() {
        Position pos = new Position(0, 0);

        assertEquals(0, pos.getX());
        assertEquals(0, pos.getY());        
    }
}