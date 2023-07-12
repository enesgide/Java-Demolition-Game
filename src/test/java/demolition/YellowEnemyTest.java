package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class YellowEnemyTest {

    @Test
    public void positionTest() {
        YellowEnemy enemy = new YellowEnemy(128, 64);

        assertEquals(128, enemy.getX());
        assertEquals(64, enemy.getY());

        enemy.setX(160);
        enemy.setY(96);

        assertEquals(160, enemy.getX());
        assertEquals(96, enemy.getY());
    }

    @Test 
    public void calculateDirectionTest() {
        YellowEnemy enemy = new YellowEnemy(224, 32);

        assertTrue(enemy.calculateDirection().equals("DOWN"));

        Position blockPosition = new Position(224, 64);
        Background.wallPositions.add(blockPosition);

        assertTrue(enemy.calculateDirection().equals("LEFT"));

        blockPosition = new Position(192, 32);
        Background.brokenPositions.add(blockPosition);
        
        assertTrue(enemy.calculateDirection().equals("UP"));

        blockPosition = new Position(224, 0);
        Background.brokenPositions.add(blockPosition);
        
        assertTrue(enemy.calculateDirection().equals("RIGHT"));

        blockPosition = new Position(256, 32);
        Background.brokenPositions.add(blockPosition);
        
        assertEquals(enemy.getFacing(), enemy.calculateDirection());
    }

    @Test 
    public void calculateDirectionOutOfMapTest() {
        YellowEnemy enemy = new YellowEnemy(-48, 0);

        assertEquals("DOWN", enemy.calculateDirection());

        enemy.setX(0);
        enemy.setY(-48);

        assertEquals("DOWN", enemy.calculateDirection());
    }

    @Test
    public void tickTest() {
        YellowEnemy enemy = new YellowEnemy(160, 32);

        assertFalse(enemy.tick());

        for (int i = 3; i <= 60; i++) {
            assertEquals(i, enemy.incrementFrame());
        } 

        assertFalse(enemy.tick());

        boolean foundPos = false;
        for (Position pos : Background.getYellowPositions()) {
            if (pos.getX() == enemy.getX() && pos.getY() == enemy.getY()) {
                foundPos = true;
                break;
            }
        }
        assertTrue(foundPos);

        Background.addExplosionPosition(160, 64);

        assertTrue(enemy.tick());
    }    
}