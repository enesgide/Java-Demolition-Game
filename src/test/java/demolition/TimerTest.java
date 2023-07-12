package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TimerTest {

    @Test
    public void constructorTest() {
        Timer timer = new Timer(60, 240, 16, 272, 16);    

        assertEquals(60, timer.getMaxTime());
        assertEquals(60, timer.getTimeLeft());
        assertEquals("60", Integer.toString(timer.getMaxTime()));

        assertEquals(240, timer.imgX);
        assertEquals(16, timer.imgY);
        assertEquals(272, timer.msgX);
        assertEquals(16, timer.msgY);
    }

    @Test
    public void maxTimeTest() {
        Timer timer = new Timer(180, 0, 0, 0, 0);

        assertEquals(180, timer.getMaxTime());
        assertEquals("180", Integer.toString(timer.getMaxTime()));

        timer.setMaxTime(90);

        assertEquals(90, timer.getMaxTime());
        assertEquals("90", Integer.toString(timer.getMaxTime()));
    }

    @Test
    public void timeLeftTest() {
        Timer timer = new Timer(180, 0, 0, 0, 0);

        assertEquals(180, timer.getTimeLeft());
        assertEquals("180", Integer.toString(timer.getTimeLeft()));

        timer.setTimeLeft(179);

        assertEquals(179, timer.getTimeLeft());
        assertEquals("179", Integer.toString(timer.getTimeLeft()));
    }

    @Test
    public void frameIncrementTest() {
        Timer timer = new Timer(180, 0, 0, 0, 0);

        for (int i = 2; i <= 60; i++) {
            assertEquals(i, timer.incrementFrame());
        }  
        
        assertEquals(1, timer.incrementFrame());
    }

    @Test
    public void tickTest() {
        Timer timer = new Timer(180, 0, 0, 0, 0);

        assertFalse(timer.tick());   

        for (int i = 2; i < 60; i++) {
            timer.incrementFrame();
        }  

        timer.setTimeLeft(0);

        assertTrue(timer.tick());
    }
}