package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EndScreenTest {

	@Test
    public void consturctorTest() {
        EndScreen screen = new EndScreen();

        assertFalse(screen.getVictory());
        assertFalse(screen.getLoss());
    }

    @Test
    public void victoryTest() {
        EndScreen screen = new EndScreen();

        screen.setVictory(true);

        assertTrue(screen.getVictory());
    }

    @Test
    public void lossTest() {
        EndScreen screen = new EndScreen();

        screen.setLoss(true);

        assertTrue(screen.getLoss());
    }
}