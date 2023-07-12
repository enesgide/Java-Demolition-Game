package demolition;

import java.util.*;
import java.io.*;
import processing.core.PApplet;

public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;
    public static final int FPS = 60;

    private boolean canPress = true;

    private String config;
    private Manager manager;
    private Background map;
    private Timer timer;
    private Lives lives;
    private Player player;
    private List<RedEnemy> redEnemies;
    private List<YellowEnemy> yellowEnemies;
    private List<Bomb> bombs;
    private List<Explosion> explosions;
    private EndScreen endScreen;

    /**
    * Creates a new App object.
    */
    public App() {}

    /**
    * Sets the width and height of the app window.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
    * Loads in all the sprites and sets up the attibutes of the app object.
    */
    public void setup() {
        frameRate(FPS);

        this.manager = new Manager();
        this.manager.setLevelInfo(this.config);
        this.manager.setupFont(this);
        this.manager.setupMapSprites(this);
        this.manager.setupTopbarSprites(this);
        this.manager.setupCharacterSprites(this);
        this.manager.setupExplosionSprites(this);
        this.manager.setupBombSprites(this);        
   
        this.map = new Background(Manager.path, 1);
        this.timer = new Timer(Manager.time, 256, 16, 298, 42);
        this.lives = new Lives(128, 16, 170, 42);
        this.endScreen = new EndScreen();
        this.map.setup(this);

        Position playerStartPos = Background.getPlayerStartingPosition();
        this.player = new Player(playerStartPos.getX(), playerStartPos.getY(), Manager.playerLevel, Manager.playerLives);           
        this.redEnemies = this.manager.getRedEnemies();
        this.yellowEnemies = this.manager.getYellowEnemies();            
        this.bombs = new ArrayList<Bomb>();
        this.explosions = new ArrayList<Explosion>();         
    }

    /**
    * Loops once every frame to call the tick() and draw() methods of all the other classes.
    */
    public void draw() {
        background(255, 150, 0);

        // Logic
        if (this.timer.tick() || this.lives.tick(this.player)) {
            this.endScreen.setLoss(true);
        } else if (this.player.tick(this.map, this.timer)) {
            if (this.player.getLevel() > Manager.levelPaths.size()) {
                this.endScreen.setVictory(true); 
            } else if (this.player.getLevel() > this.map.getLevel()) {
                this.manager.levelUpReset(this, this.map, this.timer, this.player);
                this.reset();
            }            
        }

        if (this.map.getReset() && !(this.endScreen.getVictory() || this.endScreen.getLoss())){
            this.manager.gameEndedReset(this, this.map);
            this.reset();
        }

        this.manager.handleDangerTicks(this.redEnemies, this.yellowEnemies, this.bombs, this.explosions);

        // Graphics
        this.fill(255,150,0);
        this.rect(-1, -1, WIDTH + 2, HEIGHT + 2);

        if (this.endScreen.getVictory() || this.endScreen.getLoss()) {
            this.endScreen.draw(this);
        } else {
            this.map.draw(this);
            this.timer.draw(this);
            this.lives.draw(this);  
            this.manager.handleLayerDrawings(this, this.player, this.redEnemies, this.yellowEnemies, this.bombs, this.explosions); 
        }    
    }

    /**
    * Event fired when a key is pressed and sets the boolean canPress to false.&nbsp;Determines which key is pressed and either drops the bomb or moves the player based on the key pressed.
    */
    public void keyPressed() {
        List<String> keys = Arrays.asList(new String[]{"LEFT", "UP", "RIGHT", "DOWN"});
        if (canPress) {
            canPress = false;
            if (keyCode == 32) {
                this.bombs.add(new Bomb(this.player.getX(), this.player.getY()));
            } else if (37 <= keyCode && keyCode <= 40) {
                this.player.move(keys.get(keyCode - 37));
            }
        }  
    }

    /**
    * Event fired when a key is released.&nbsp;Sets the boolean canPress to true so that keyPressed can be used again.
    */
    public void keyReleased() {
        canPress = true;
    }

    /**
    * Sets the config filepath used when setting up the map.
    * @param filepath The path of the json file which holds the level data.
    */
    public void setConfig(String filepath) {
        this.config = filepath;
    }

    /**
    * Gets the player object.
    * @return The player attribute of the app.
    */
    public Player getPlayer() {
        return this.player;
    }

    /**
    * Gets the endScreen object.
    * @return The endScreen attribute of the app.
    */
    public EndScreen getEndScreen() {
        return this.endScreen;
    }

    /**
    * Resets the position of all game characters (player, red and yellow enemies), and removes all bombs and explosions from the map.
    */
    public void reset() {
        this.player.reset();
        this.redEnemies = this.manager.getRedEnemies();   
        this.yellowEnemies = this.manager.getYellowEnemies();    
        this.bombs = new ArrayList<Bomb>();
        this.explosions = new ArrayList<Explosion>();        
    }

    /**
    * Runs the program.
    * @param args Command line input.
    */
    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
