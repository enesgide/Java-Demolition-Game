package demolition;

import processing.core.PApplet;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {

    @Test
    public void levelInfoTest() {
        // Create an instance of your application
        App app = new App();

        // Set the program to not loop automatically
        app.noLoop();

        // Create manager
        Manager manager = new Manager();

        manager.setLevelInfo("src/test/resources/config.json");
        assertEquals(3, Manager.playerLives);
        assertEquals(15, Manager.time);
        assertEquals("src/test/resources/level1.txt", Manager.path);
        assertEquals(2, Manager.levelTimes.size());  
        assertEquals(2, Manager.levelPaths.size());  
    }

    @Test
    public void fontTest() {
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

        // Create manager
        Manager manager = new Manager();

        manager.setupFont(app);
        assertNotNull(Manager.font);       

        Background.reset(); 
    }



    @Test
    public void mapSpritesTest() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);

        Manager manager = new Manager();

        manager.setupMapSprites(app);

        assertNotNull(Manager.wallTile);
        assertNotNull(Manager.brokenTile);
        assertNotNull(Manager.emptyTile);
        assertNotNull(Manager.goalTile);         

        Background.reset();       
    }

    @Test
    public void topbarSpritesTest() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);

        Manager manager = new Manager();

        manager.setupTopbarSprites(app);
        assertNotNull(Manager.livesSprite);
        assertNotNull(Manager.timerSprite);        

        Background.reset();        
    }

    @Test
    public void characterSpritesTest() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);

        Manager manager = new Manager();

        manager.setupCharacterSprites(app);

        // Player sprites
        assertEquals(4, Manager.leftPlayerSprites.size());
        assertEquals(4, Manager.rightPlayerSprites.size());
        assertEquals(4, Manager.upPlayerSprites.size());
        assertEquals(4, Manager.downPlayerSprites.size());

        // RedEnemy sprites
        assertEquals(4, Manager.leftRedSprites.size());
        assertEquals(4, Manager.rightRedSprites.size());
        assertEquals(4, Manager.upRedSprites.size());
        assertEquals(4, Manager.downRedSprites.size());

        // YellowEnemy sprites
        assertEquals(4, Manager.leftYellowSprites.size());
        assertEquals(4, Manager.rightYellowSprites.size());
        assertEquals(4, Manager.upYellowSprites.size());
        assertEquals(4, Manager.downYellowSprites.size());       

        Background.reset();         
    }

    @Test
    public void explosionSpritesTest() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        Manager manager = new Manager();

        manager.setupExplosionSprites(app);
        assertEquals(7, Manager.explosionSprites.size());       

        Background.reset(); 
    }

    @Test
    public void bombSpritesTest() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);

        Manager manager = new Manager();

        manager.setupBombSprites(app);
        assertEquals(8, Manager.bombSprites.size());        

        Background.reset();        
    }

}
