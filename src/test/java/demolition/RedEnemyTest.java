package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RedEnemyTest {

    @Test
    public void positionTest() {
        RedEnemy enemy = new RedEnemy(128, 64);

        assertEquals(128, enemy.getX());
        assertEquals(64, enemy.getY());

        enemy.setX(160);
        enemy.setY(96);

        assertEquals(160, enemy.getX());
        assertEquals(96, enemy.getY());
    }    

    @Test 
    public void calculateDirectionTest() {
        RedEnemy enemy = new RedEnemy(96, 32);

        assertTrue(enemy.calculateDirection().equals("DOWN"));

        Position blockPosition = new Position(96, 64);
        Background.wallPositions.add(blockPosition);

        String nextDirection = enemy.calculateDirection();
        assertTrue(nextDirection.equals("UP") || nextDirection.equals("RIGHT") || nextDirection.equals("LEFT"));

        blockPosition = new Position(64, 32);
        Background.brokenPositions.add(blockPosition);
        
        nextDirection = enemy.calculateDirection();
        assertTrue(nextDirection.equals("UP") || nextDirection.equals("RIGHT"));

        blockPosition = new Position(96, 0);
        Background.brokenPositions.add(blockPosition);
        
        assertEquals("RIGHT", enemy.calculateDirection());

        blockPosition = new Position(128, 32);
        Background.brokenPositions.add(blockPosition);
        
        assertEquals(enemy.getFacing(), enemy.calculateDirection());

        Background.reset();
    }

    @Test 
    public void calculateDirectionOutOfMapTest() {
        RedEnemy enemy = new RedEnemy(-48, 0);

        assertEquals("DOWN", enemy.calculateDirection());

        enemy.setX(0);
        enemy.setY(-48);

        assertEquals("DOWN", enemy.calculateDirection());
    }

    @Test
    public void frameIncrementTest() {
        RedEnemy enemy = new RedEnemy(0, 0);

        for (int i = 2; i <= 60; i++) {
            assertEquals(i, enemy.incrementFrame());
        }  
        
        assertEquals(1, enemy.incrementFrame());
    }

    @Test
    public void tickTest() {
        RedEnemy enemy = new RedEnemy(96, 32);

        assertFalse(enemy.tick());

        for (int i = 3; i <= 60; i++) {
            assertEquals(i, enemy.incrementFrame());
        } 

        assertFalse(enemy.tick());

        boolean foundPos = false;
        for (Position pos : Background.getRedPositions()) {
            if (pos.getX() == enemy.getX() && pos.getY() == enemy.getY()) {
                foundPos = true;
                break;
            }
        }
        assertTrue(foundPos);

        Background.addExplosionPosition(enemy.getX(), enemy.getY());

        assertTrue(enemy.tick());
    }
}