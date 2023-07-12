package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void positionTest() {
        Player player = new Player(128, 64, 1, 1);

        assertEquals(128, player.getX());
        assertEquals(64, player.getY());

        player.setX(160);
        player.setY(96);

        assertEquals(160, player.getX());
        assertEquals(96, player.getY());
    }

    @Test
    public void levelTest() {
        Player player = new Player(32, 64, 1, 1);

        assertEquals(1, player.getLevel());

        player.setLevel(2);

        assertEquals(2, player.getLevel());
    }

    @Test
    public void livesTest() {
        Player player = new Player(32, 64, 1, 3);

        assertEquals(3, player.getLives());

        player.setLives(2);
        
        assertEquals(2, player.getLives());
    }

    @Test
    public void facingTest() {
        Player player = new Player(32, 64, 1, 1);        
        assertEquals("DOWN", player.getFacing());

        player.setFacing("LEFT");
        assertEquals("LEFT", player.getFacing());

        player.setFacing("RIGHT");
        assertEquals("RIGHT", player.getFacing());

        player.setFacing("UP");
        assertEquals("UP", player.getFacing());

        player.setFacing("DOWN");
        assertEquals("DOWN", player.getFacing());
    }

    @Test
    public void lastFacingTest() {
        Player player = new Player(32, 64, 1, 1);        
        assertEquals("DOWN", player.getLastFacing());

        player.setLastFacing("LEFT");
        assertEquals("LEFT", player.getLastFacing());

        player.setLastFacing("RIGHT");
        assertEquals("RIGHT", player.getLastFacing());

        player.setLastFacing("UP");
        assertEquals("UP", player.getLastFacing());

        player.setLastFacing("DOWN");
        assertEquals("DOWN", player.getLastFacing());
    }

    @Test 
    public void resetTest() {
        Position startPos = new Position(32, 64);
        Background.plrStartPos = startPos;

        assertEquals(32, Background.plrStartPos.getX());
        assertEquals(64, Background.plrStartPos.getY());

        Player player = new Player(startPos.getX(), startPos.getY(), 1, 3);

        player.setX(128);
        player.setY(128);
        player.setFacing("UP");
        player.setLastFacing("RIGHT");
        player.spriteCount = 2;

        assertEquals(128, player.getX());
        assertEquals(128, player.getY());
        assertEquals("UP", player.getFacing());
        assertEquals("RIGHT", player.getLastFacing());
        assertEquals(2, player.spriteCount);

        player.reset();

        assertEquals(32, player.getX());
        assertEquals(64, player.getY());
        assertEquals("DOWN", player.getFacing());
        assertEquals("DOWN", player.getLastFacing());
        assertEquals(0, player.spriteCount);
    }

    @Test
    public void moveTest() {
        Player player = new Player(32, 32, 1, 3);
        assertEquals(32, player.getX());
        assertEquals(32, player.getY());

        player.move("RIGHT");
        assertEquals(64, player.getX());
        assertEquals(32, player.getY());

        player.move("DOWN");
        assertEquals(64, player.getX());
        assertEquals(64, player.getY());

        player.move("LEFT");
        assertEquals(32, player.getX());
        assertEquals(64, player.getY());

        player.move("UP");
        assertEquals(32, player.getX());
        assertEquals(32, player.getY());
    }

    @Test 
    public void nextPositionTest() {
        Player player = new Player(32, 32, 1, 3);

        assertEquals(0, player.getNextPosInDirection("LEFT").getX());
        assertEquals(32, player.getNextPosInDirection("LEFT").getY());

        assertEquals(64, player.getNextPosInDirection("RIGHT").getX());
        assertEquals(32, player.getNextPosInDirection("RIGHT").getY());

        assertEquals(32, player.getNextPosInDirection("UP").getX());
        assertEquals(0, player.getNextPosInDirection("UP").getY());

        assertEquals(32, player.getNextPosInDirection("DOWN").getX());
        assertEquals(64, player.getNextPosInDirection("DOWN").getY());

        assertEquals(-1, player.getNextPosInDirection("test").getX());
        assertEquals(-1, player.getNextPosInDirection("test").getY());
    }

    @Test
    public void moveIntoCollisionTest() {
        Player player = new Player(0, 32, 1, 3);
        assertEquals(0, player.getX());
        assertEquals(32, player.getY());

        player.move("RIGHT");
        assertEquals(32, player.getX());
        assertEquals(32, player.getY());

        Position blockPositionUp = new Position(32, 0);
        Background.wallPositions.add(blockPositionUp);

        Position blockPositionDown = new Position(32, 64);        
        Background.wallPositions.add(blockPositionDown);

        Position blockPositionLeft = new Position(0, 32);
        Background.wallPositions.add(blockPositionLeft);

        Position blockPositionRight = new Position(64, 32);
        Background.wallPositions.add(blockPositionRight);

        player.move("UP");
        assertEquals(32, player.getX());
        assertEquals(32, player.getY());

        player.move("DOWN");
        assertEquals(32, player.getX());
        assertEquals(32, player.getY());        

        player.move("LEFT");
        assertEquals(32, player.getX());
        assertEquals(32, player.getY());

        player.move("RIGHT");
        assertEquals(32, player.getX());
        assertEquals(32, player.getY());

        player.setX(128);
        player.setY(128);

        blockPositionUp = new Position(128, 96);
        Background.brokenPositions.add(blockPositionUp);

        blockPositionDown = new Position(128, 160);        
        Background.brokenPositions.add(blockPositionDown);

        blockPositionLeft = new Position(96, 128);
        Background.brokenPositions.add(blockPositionLeft);

        blockPositionRight = new Position(160, 128);
        Background.brokenPositions.add(blockPositionRight);

        player.move("UP");
        assertEquals(128, player.getX());
        assertEquals(128, player.getY());

        player.move("DOWN");
        assertEquals(128, player.getX());
        assertEquals(128, player.getY());        

        player.move("LEFT");
        assertEquals(128, player.getX());
        assertEquals(128, player.getY());

        player.move("RIGHT");
        assertEquals(128, player.getX());
        assertEquals(128, player.getY());
    }

    @Test
    public void checkExplosionTest() {
        Player player = new Player(32, 32, 1, 3);
        assertFalse(player.checkExplosion());

        Background.addExplosionPosition(32, 64);
        assertFalse(player.checkExplosion());

        Background.addExplosionPosition(64, 32);
        assertFalse(player.checkExplosion());

        Background.addExplosionPosition(32, 32);        
        assertTrue(player.checkExplosion());
    }

    @Test
    public void checkRedEnemyTest() {
        Player player = new Player(32, 32, 1, 3);
        assertFalse(player.checkEnemy());

        Background.addRedPosition(32, 64);
        assertFalse(player.checkEnemy());

        Background.addRedPosition(64, 32);
        assertFalse(player.checkEnemy());

        Background.addRedPosition(32, 32);        
        assertTrue(player.checkEnemy());
    }

    @Test
    public void checkYellowEnemyTest() {
        Player player = new Player(320, 320, 1, 3);
        assertFalse(player.checkEnemy());

        Background.addYellowPosition(320, 352);
        assertFalse(player.checkEnemy());

        Background.addYellowPosition(352, 320);
        assertFalse(player.checkEnemy());

        Background.addYellowPosition(320, 320);        
        assertTrue(player.checkEnemy());
    }

    @Test
    public void checkGoalReachedTest() {
        Position pos = new Position(448, 0);
        Background.goalPos = pos;

        assertEquals(448, Background.goalPos.getX());
        assertEquals(0, Background.goalPos.getY());

        Player player = new Player(416, 0, 1, 3);
        assertFalse(player.checkGoalReached());
        assertEquals(1, player.getLevel());

        player.move("DOWN");
        assertFalse(player.checkGoalReached());

        player.move("RIGHT");
        assertFalse(player.checkGoalReached());

        player.move("UP");
        assertTrue(player.checkGoalReached());
        assertEquals(2, player.getLevel());

        player.setLives(0);
        assertFalse(player.checkGoalReached());
    }

    @Test
    public void killTest() {
        Background map = new Background("", 1);
        Timer timer = new Timer(180, 0, 0, 0, 0);
        Player player = new Player(0, 0, 1, 3);

        assertFalse(player.kill(map, timer));
        assertTrue(map.getReset());
        assertEquals(2, player.getLives());
        assertEquals(180, timer.getTimeLeft());

        player.setLives(0);
        assertTrue(player.kill(map, timer));
    }

    @Test
    public void nullSpriteTest() {
        Player player = new Player(0, 0, 1, 3);

        player.updateSprite();

        assertNull(player.sprite);
    }

    @Test
    public void tickTest() {
        Background map = new Background("", 1);
        Timer timer = new Timer(180, 0, 0, 0, 0);
        Player player = new Player(0, 0, 1, 3);

        assertFalse(player.tick(map, timer));

        Background.addExplosionPosition(0, 0);
        assertFalse(player.tick(map, timer));

        Background.removeExplosionPosition(0, 0);
        Position pos = new Position(0, 0);
        Background.goalPos = pos;
        assertTrue(player.tick(map, timer));

        player.setLives(0);
        Background.addExplosionPosition(0, 0);
        assertTrue(player.tick(map, timer));
    }
}