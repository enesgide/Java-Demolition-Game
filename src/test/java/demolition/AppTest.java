package demolition;

import processing.core.PApplet;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void windowTest() {
        assertEquals(480, App.HEIGHT);
        assertEquals(480, App.WIDTH);
        assertEquals(60, App.FPS);
    }

    @Test 
    public void enemyKillTest() {
        // Create an instance of your application
        App app = new App();

        // Set the program to not loop automatically
        app.noLoop();

        // Set the path of the config file to use
        app.setConfig("src/test/resources/config.json");

        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] {"App"}, app);

        // Set a 1 second delay to ensure all resources are loaded
        app.delay(1000);

        // Call draw to update the program.
        app.draw();

        assertTrue(32 == app.getPlayer().getX() && 96 == app.getPlayer().getY());
        assertEquals(3, app.getPlayer().getLives());   

        // Move 2 right
        for (int i = 0; i < 2; i++) {            
            app.keyCode = 39;
            app.keyPressed();
            app.keyReleased();
            app.draw();
        }
        assertTrue(96 == app.getPlayer().getX() && 96 == app.getPlayer().getY());

        // Move 8 down
        for (int i = 0; i < 8; i++) {            
            app.keyCode = 40;
            app.keyPressed();
            app.keyReleased();
            app.draw();
        }
        assertTrue(96 == app.getPlayer().getX() && 352 == app.getPlayer().getY());

        // Move 2 left
        for (int i = 0; i < 2; i++) {            
            app.keyCode = 37;
            app.keyPressed();
            app.keyReleased();
            app.draw();
        }
        assertTrue(32 == app.getPlayer().getX() && 352 == app.getPlayer().getY());

        // Move 2 down
        for (int i = 0; i < 8; i++) {            
            app.keyCode = 40;
            app.keyPressed();
            app.keyReleased();
            app.draw();
        }

        // Move 4 right
        for (int i = 0; i < 4; i++) {            
            app.keyCode = 39;
            app.keyPressed();
            app.keyReleased();
            app.draw();
        }

        // Plant bomb
        app.keyCode = 32;
        app.keyPressed();
        app.keyReleased();
        app.draw(); 

        // Move 3 left
        for (int i = 0; i < 3; i++) {            
            app.keyCode = 37;
            app.keyPressed();
            app.keyReleased();
            app.draw();
        }

        // Hold for 2 seconds - bomb explosion (120 frames)
        for (int i = 0; i < 120; i++) {  
            app.draw();
        }        
        assertEquals(3, app.getPlayer().getLives());

        // Hold for 0.5 seconds - explosion removal
        for (int i = 0; i < 30; i++) {  
            app.draw();
        }

        // Reset map to protect other tests
        Background.reset();
    }

    @Test 
    public void deathLossGameplayTest() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);

        // Call draw to update the program.
        app.draw();

        assertTrue(32 == app.getPlayer().getX() && 96 == app.getPlayer().getY());
        assertEquals(3, app.getPlayer().getLives());        
        
        // Move 2 right
        for (int i = 0; i < 2; i++) {            
            app.keyCode = 39;
            app.keyPressed();
            app.keyPressed(); // won't move player, canPress is false
            app.keyReleased();
            app.draw();
        }
        assertTrue(96 == app.getPlayer().getX() && 96 == app.getPlayer().getY());

        // Move 2 down
        for (int i = 0; i < 2; i++) {            
            app.keyCode = 40;
            app.keyPressed();
            app.keyReleased();
            app.draw();
        }
        assertTrue(96 == app.getPlayer().getX() && 160 == app.getPlayer().getY());       

        // Plant bomb
        app.keyCode = 32;
        app.keyPressed();
        app.keyReleased();
        app.draw(); 

        // Hold for 2 seconds - bomb explosion (120 frames)
        for (int i = 0; i < 120; i++) {  
            app.draw();
        }

        // Player in explosion range, should lose a life
        assertTrue(32 == app.getPlayer().getX() && 96 == app.getPlayer().getY());   
        assertEquals(2, app.getPlayer().getLives());

        // Plant bomb
        app.keyCode = 32;
        app.keyPressed();
        app.keyReleased();
        app.draw(); 

        // Hold for 2 seconds - bomb explosion (120 frames)
        for (int i = 0; i < 120; i++) {  
            app.draw();
        }
        assertEquals(1, app.getPlayer().getLives());

        // Move 2 right
        for (int i = 0; i < 2; i++) {            
            app.keyCode = 39;
            app.keyPressed();
            app.keyReleased();
            app.draw();
        }
        assertTrue(96 == app.getPlayer().getX() && 96 == app.getPlayer().getY());

        // Move 4 down
        for (int i = 0; i < 4; i++) {            
            app.keyCode = 40;
            app.keyPressed();
            app.keyReleased();
            app.draw();
        }
        assertTrue(96 == app.getPlayer().getX() && 224 == app.getPlayer().getY());

        // Hold for 9 seconds - maximum time for red to reach position (540 frames)
        for (int i = 0; i < 540; i++) {  
            app.draw();
        }
        assertEquals(0, app.getPlayer().getLives());

        // Reset map to protect other tests
        Background.reset();
    }

    @Test
    public void timerLossGameplayTest() {
        // Create an instance of your application
        App app = new App();

        // Set the program to not loop automatically
        app.noLoop();

        // Set the path of the config file to use
        app.setConfig("src/test/resources/config.json");

        // Tell PApplet to create the worker threads for the program, runs setup
        PApplet.runSketch(new String[] {"App"}, app);

        // Set a 1 second delay to ensure all resources are loaded
        app.delay(1000);

        // Call draw to update the program.
        app.draw();

        // Hold for 16 seconds - maximum time (960 frames)
        for (int i = 0; i < 960; i++) {  
            app.draw();
        }

        //Check that the player won
        assertTrue(app.getEndScreen().getLoss());

        // Reset map to protect other tests
        Background.reset();
    }

    @Test 
    public void victoryGameplayTest() {
        // Create an instance of your application
        App app = new App();

        // Set the program to not loop automatically
        app.noLoop();

        // Set the path of the config file to use
        app.setConfig("src/test/resources/config.json");

        // Tell PApplet to create the worker threads for the program, runs setup
        PApplet.runSketch(new String[] {"App"}, app);

        // Set a 1 second delay to ensure all resources are loaded
        app.delay(1000);

        // Call draw to update the program.
        app.draw();

        assertTrue(32 == app.getPlayer().getX() && 96 == app.getPlayer().getY());
        assertEquals(3, app.getPlayer().getLives()); 

        // Check that keyCode 41 does nothing
        app.keyCode = 41;
        app.keyPressed();
        app.keyReleased();
        app.draw(); 

        assertTrue(32 == app.getPlayer().getX() && 96 == app.getPlayer().getY());

        // Reach the goal
        Background.goalPos = new Position(32, 96);

        // Move to level 2
        app.draw();
        assertEquals(2, app.getPlayer().getLevel());

        // Reach the goal again

        assertTrue(32 == app.getPlayer().getX() && 256 == app.getPlayer().getY());
        Background.goalPos = new Position(32, 256);

        // Check that level 3 does not exist
        app.draw();

        //Check that the player won
        assertTrue(app.getEndScreen().getVictory());

        // Reset map to protect other tests
        Background.reset();
    }
    
}
