package demolition;

import java.util.*;
import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExplosionTest {

    @Test
    public void positionTest() {
        Explosion explosion = new Explosion(32, -96);

        assertEquals(32, explosion.getCentreX());
        assertEquals(-96, explosion.getCentreY());
    }

    @Test
    public void collisionsTest() {
        Explosion explosion = new Explosion(32, -96);

        Position blockPosition1 = new Position(32, -32);
        Background.wallPositions.add(blockPosition1);

        Position blockPosition2 = new Position(32, -128);
        Background.brokenPositions.add(blockPosition2);

        Position blockPosition3 = new Position(96, -96);
        Background.wallPositions.add(blockPosition3);

        Position blockPosition4 = new Position(0, -96);
        Background.brokenPositions.add(blockPosition4);

        assertEquals(1, explosion.checkCollisions(new Position(32, -160)));
        assertEquals(-1, explosion.checkCollisions(blockPosition1));
        assertEquals(0, explosion.checkCollisions(blockPosition2));
        assertEquals(-1, explosion.checkCollisions(blockPosition3));
        assertEquals(0, explosion.checkCollisions(blockPosition4));
    }

    @Test
    public void tickTest() {
        Explosion explosion = new Explosion(-96, -96);

        assertNull(explosion.tick());

        for (int i = 3; i <= 30; i++) {
            assertEquals(i, explosion.incrementFrame());
        }  

        Position blockPosition = new Position(-64, -96);
        Background.brokenPositions.add(blockPosition);

        assertEquals(8, explosion.tick().size());
    }

    
}