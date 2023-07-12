package demolition;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BackgroundTest {

    @Test
    public void resetTest() {
        Background map = new Background("src/test/resources/level1.txt", 1);

        map.setReset(false);
        assertFalse(map.getReset());
        map.setReset(true);
        assertTrue(map.getReset());

        Background.emptyPositions.add(new Position(0, 0));        
        map.reset();

        assertEquals(0, Background.emptyPositions.size());
        assertEquals(0, Background.wallPositions.size());
        assertEquals(0, Background.brokenPositions.size());
        assertEquals(0, Background.redPositions.size());
        assertEquals(0, Background.yellowPositions.size());
        assertEquals(0, Background.explosionPositions.size());
        assertNull(Background.goalPos);
        assertNull(Background.plrStartPos);
    }

    @Test 
    public void levelTest() {
        Background map = new Background("src/test/resources/level1.txt", 1);

        assertEquals(1, map.getLevel());

        map.changeLevel();

        assertEquals(2, map.getLevel());
    }

    @Test
    public void manipulateTilePositions() {
        Background map = new Background("src/test/resources/level1.txt", 1);

        Background.plrStartPos = new Position(32, 64);

        assertEquals(32, Background.getPlayerStartingPosition().getX());
        assertEquals(64, Background.getPlayerStartingPosition().getY());

        assertEquals(0, Background.getEmptyPositions().size());

        Background.removeBrokenPosition(-1, -1);

        Background.brokenPositions.add(new Position(-1, -1));
        boolean foundPos = false;
        for (Position pos : Background.getBrokenPositions()) {
            if (pos.getX() == -1 && pos.getY() == -1) {
                foundPos = true;
                break;
            }
        }
        assertTrue(foundPos);

        Background.removeBrokenPosition(-1, -2);
        Background.removeBrokenPosition(-2, -1);

        Background.removeBrokenPosition(-1, -1);
        foundPos = false;
        for (Position pos : Background.getBrokenPositions()) {
            if (pos.getX() == -1 && pos.getY() == -1) {
                foundPos = true;
                break;
            }
        }
        assertFalse(foundPos);
    }

    @Test
    public void manipulateExplosionPositionsTest() {
        Background.removeExplosionPosition(-2, -2);

        Background.addExplosionPosition(-2, -2);

        boolean foundPos = false;
        for (Position pos : Background.getExplosionPositions()) {
            if (pos.getX() == -2 && pos.getY() == -2) {
                foundPos = true;
                break;
            }
        }

        assertTrue(foundPos);

        Background.removeExplosionPosition(-3, -2);
        Background.removeExplosionPosition(-2, -3);
        Background.removeExplosionPosition(-2, -2);

        foundPos = false;
        for (Position pos : Background.getExplosionPositions()) {
            if (pos.getX() == -2 && pos.getY() == -2) {
                foundPos = true;
                break;
            }
        }

        assertFalse(foundPos);
    }

    @Test
    public void manipulateRedPositionsTest() {
        Background.removeRedPosition(-2, -2);

        Background.addRedPosition(-2, -2);

        boolean foundPos = false;
        for (Position pos : Background.getRedPositions()) {
            if (pos.getX() == -2 && pos.getY() == -2) {
                foundPos = true;
                break;
            }
        }

        assertTrue(foundPos);

        Background.removeRedPosition(-3, -2);
        Background.removeRedPosition(-2, -3);
        Background.removeRedPosition(-2, -2);

        foundPos = false;
        for (Position pos : Background.getRedPositions()) {
            if (pos.getX() == -2 && pos.getY() == -2) {
                foundPos = true;
                break;
            }
        }

        assertFalse(foundPos);
    }

    @Test
    public void manipulateYellowPositionsTest() {
        Background.removeYellowPosition(-2, -2);

        Background.addYellowPosition(-2, -2);

        boolean foundPos = false;
        for (Position pos : Background.getYellowPositions()) {
            if (pos.getX() == -2 && pos.getY() == -2) {
                foundPos = true;
                break;
            }
        }

        assertTrue(foundPos);

        Background.removeYellowPosition(-3, -2);
        Background.removeYellowPosition(-2, -3);
        Background.removeYellowPosition(-2, -2);

        foundPos = false;
        for (Position pos : Background.getYellowPositions()) {
            if (pos.getX() == -2 && pos.getY() == -2) {
                foundPos = true;
                break;
            }
        }

        assertFalse(foundPos);
    }
}