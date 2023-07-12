package demolition;

import java.util.*;
import java.io.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.data.JSONObject;
import processing.data.JSONArray;

public class Manager extends App{

    // Level info
    public static List<String> levelPaths = new ArrayList<String>();
    public static List<Integer> levelTimes = new ArrayList<Integer>();   

    public static int playerLevel = 1;
    public static int playerLives = 0;
    public static String path = null;
    public static int time = -1;

    // Font
    public static PFont font;

    // Tile sprites
    public static PImage emptyTile;
    public static PImage wallTile;
    public static PImage brokenTile;
    public static PImage goalTile;

    // Topbar sprites
    public static PImage timerSprite;
    public static PImage livesSprite;

    // Bomb sprites
    public static List<PImage> bombSprites = new ArrayList<PImage>();
    public static List<PImage> explosionSprites = new ArrayList<PImage>();

	// Character sprites
	public static List<PImage> leftPlayerSprites;
    public static List<PImage> rightPlayerSprites;
    public static List<PImage> upPlayerSprites;
    public static List<PImage> downPlayerSprites;

    public static List<PImage> leftRedSprites;
    public static List<PImage> rightRedSprites;
    public static List<PImage> upRedSprites;
    public static List<PImage> downRedSprites;

    public static List<PImage> leftYellowSprites;
    public static List<PImage> rightYellowSprites;
    public static List<PImage> upYellowSprites;
    public static List<PImage> downYellowSprites;

    /**
    * Resets the map when the player reaches the goal tile and there is still another level to complete.
    * @param app The PApplet app object
    * @param map The app's map object
    * @param timer The app's timer object
    * @param player The app's player object
    */
    public void levelUpReset(PApplet app, Background map, Timer timer, Player player) {
    	map.changeLevel();
        map.setReset(false);
        map.reset();
        map.setup(app);
        int newTimeLeft = levelTimes.get(player.getLevel() - 1);
        timer.setMaxTime(newTimeLeft);
        timer.setTimeLeft(newTimeLeft);
    }

    /**
    * Resets the map to allow for the end screen when the player reaches the goal tile and there are no levels left.
    * @param app The PApplet app object
    * @param map The app's map object
    */
    public void gameEndedReset(PApplet app, Background map) {
    	map.setReset(false);
        map.reset();
        map.setup(app);
    }

    /**
    * Removes enemies that are killed by explosions.&nbsp;Removes bombs that have exploded, and explosions that have been on the map for 0.5 seconds.
    * @param redEnemies The list of red enemies
    * @param yellowEnemies The list of yellow enemies
    * @param bombs The list of bombs
    * @param explosions The list of explosions
    */
    public void handleDangerTicks(List<RedEnemy> redEnemies, List<YellowEnemy> yellowEnemies, List<Bomb> bombs, List<Explosion> explosions) {
        List<RedEnemy> removeReds = new ArrayList<RedEnemy>();
        for (RedEnemy enemy : redEnemies) {                
            if (enemy.tick()) {
                removeReds.add(enemy);
            }
        }
        for (RedEnemy enemy : removeReds) {                
            redEnemies.remove(redEnemies.indexOf(enemy));
        }

        List<YellowEnemy> removeYellows = new ArrayList<YellowEnemy>();
        for (YellowEnemy enemy : yellowEnemies) {                
            if (enemy.tick()) {
                removeYellows.add(enemy);
            }
        }
        for (YellowEnemy enemy : removeYellows) {                
            yellowEnemies.remove(yellowEnemies.indexOf(enemy));
        }

        //Bombs
        List<Bomb> explodedBombs = new ArrayList<Bomb>();
        for (Bomb bomb : bombs) {                
            if (bomb.tick()) {
                explodedBombs.add(bomb);
                Explosion newExplosion = new Explosion(bomb.getX(), bomb.getY());
                explosions.add(newExplosion);
            }
        }

        for (Bomb bomb : explodedBombs) {                
            bombs.remove(bombs.indexOf(bomb));
        }

        List<Explosion> finishedExplosions = new ArrayList<Explosion>();
        for (Explosion explosion : explosions) {  
            List<Position> oldPositions = explosion.tick() ;    
            if (oldPositions != null) {
                finishedExplosions.add(explosion);
                Background.removeExplosionPosition(explosion.getCentreX(), explosion.getCentreY());
                for (Position pos : oldPositions) {
                    Background.removeExplosionPosition(pos.getX(), pos.getY());
                }
            }
        }

        for (Explosion explosion : finishedExplosions) {                
            explosions.remove(explosions.indexOf(explosion));
        }
    }

    /**
    * Reads the configuration file based on configPath and sets the time and path for the first level.&nbsp;Inserts the data for all the levels into levelPaths and levelTimes.
    * @param configPath The file path for the configuration JSON file which holds the level data.
    */
	public void setLevelInfo(String configPath) {		        
        try {
            if (configPath == null) {
                configPath = "config.json";
            }
            Reader reader = new FileReader(configPath);
            JSONObject obj = new JSONObject(reader);
            String fileContents = obj.toString();

            playerLives = Integer.parseInt(obj.get("lives").toString()); 

            Object levelsObj = obj.get("levels");
            JSONArray levels = JSONArray.parse(levelsObj.toString());

            for (int i = 0; i < levels.size(); i++) {
                Object currLevelObj = levels.get(i); 
                JSONObject currLevel = JSONObject.parse(currLevelObj.toString());   
                String tempPath = currLevel.get("path").toString();
                int tempTime = Integer.parseInt(currLevel.get("time").toString()); 
                if (levelPaths.size() < levels.size()) {
                    levelPaths.add(tempPath);
                }
                if (levelTimes.size() < levels.size()) {
                    levelTimes.add(tempTime);
                }
                if (i == 0) {       
                    time = tempTime;       
                    path = tempPath;                    
                }
            }
            

        } catch(FileNotFoundException e) {            
            System.out.println("FileNotFound when setting level info");
        }
    }

    /**
    * Loads in the font.
    * @param app The PApplet app object
    */
    public void setupFont(PApplet app) {
        font = app.createFont("src/main/resources/PressStart2P-Regular.ttf", 64);
    }

    /**
    * Loads in the tile sprites for the map.
    * @param app The PApplet app object
    */
    public void setupMapSprites(PApplet app) {
        emptyTile = app.loadImage("src/main/resources/empty/empty.png");
        wallTile = app.loadImage("src/main/resources/wall/solid.png");
        brokenTile = app.loadImage("src/main/resources/broken/broken.png");
        goalTile = app.loadImage("src/main/resources/goal/goal.png");
    }

    /**
    * Loads in the topbar (timer and lives) sprites.
    * @param app The PApplet app object
    */
    public void setupTopbarSprites(PApplet app) {
        timerSprite = app.loadImage("src/main/resources/icons/clock.png");
        livesSprite = app.loadImage("src/main/resources/icons/player.png");
    }

    /**
    * Assigns the sprite variables for the player, the red enemies and the yellow enemies.
    * @param app The PApplet app object
    */
    public void setupCharacterSprites(PApplet app) {
    	leftPlayerSprites = this.loadCharacterSprites(app, "player", "player", "left");
        rightPlayerSprites = this.loadCharacterSprites(app, "player", "player", "right");
        upPlayerSprites = this.loadCharacterSprites(app, "player", "player", "up");
        downPlayerSprites = this.loadCharacterSprites(app, "player", "player", "down");
        
        leftRedSprites = this.loadCharacterSprites(app, "red_enemy", "red", "left");
        rightRedSprites = this.loadCharacterSprites(app, "red_enemy", "red", "right");
        upRedSprites = this.loadCharacterSprites(app, "red_enemy", "red", "up");
        downRedSprites = this.loadCharacterSprites(app, "red_enemy", "red", "down");
        
        leftYellowSprites = this.loadCharacterSprites(app, "yellow_enemy", "yellow", "left");
        rightYellowSprites = this.loadCharacterSprites(app, "yellow_enemy", "yellow", "right");
        upYellowSprites = this.loadCharacterSprites(app, "yellow_enemy", "yellow", "up");
        downYellowSprites = this.loadCharacterSprites(app, "yellow_enemy", "yellow", "down");
    }

    /**
    * Loads in the sprites for either the player, red enemy or yellow enemy
    * @param app The PApplet app object
    * @param folderName The name of the folder holding the sprites
    * @param characterName The type of game character
    * @param directionName The direction of the sprites
    * @return List of sprites.
    */
    public List<PImage> loadCharacterSprites(PApplet app, String folderName, String characterName, String directionName) {
        List<PImage> spritesList = new ArrayList<PImage>();
        for (int i = 1; i <= 4; i++) {
            String imagePath = String.format("src/main/resources/%s/%s_%s%s.png", folderName, characterName, directionName, i);
            spritesList.add(app.loadImage(imagePath));
        }        
        return spritesList;
    }

    /**
    * Loads in the bomb sprites.
    * @param app The PApplet app object
    */
    public void setupBombSprites(PApplet app) {
        if (bombSprites.size() == 0) {
        	for (int i = 1; i <= 8; i++) {
                bombSprites.add(app.loadImage(String.format("src/main/resources/bomb/bomb%s.png", i)));
            }
        }
    }

    /**
    * Loads in the explosion sprites.
    * @param app The PApplet app object
    */
    public void setupExplosionSprites(PApplet app) {
        if (explosionSprites.size() == 0) {
            List<String> expSpritePositions = Arrays.asList(new String[]{"centre", "horizontal", "vertical", "end_left", "end_right", "end_top", "end_bottom"});
            for (int i = 0; i < expSpritePositions.size(); i++) {
                explosionSprites.add(app.loadImage(String.format("src/main/resources/explosion/%s.png", expSpritePositions.get(i))));
            }
        }    	
    }
    
    /**
    * Returns the list of red enemies still alive.
    * @return The list of the red enemies
    */
    public List<RedEnemy> getRedEnemies() {
    	List<Position> redPositions = Background.getRedPositions();
        List<RedEnemy> redEnemyList = new ArrayList<RedEnemy>();
        for (Position enemyStartPos : redPositions) {                
            RedEnemy enemy = new RedEnemy(enemyStartPos.getX(), enemyStartPos.getY());
            redEnemyList.add(enemy);
        }
        return redEnemyList;
    }

    /**
    * Returns the list of yellow enemies still alive.
    * @return The list of the yellow enemies
    */
    public List<YellowEnemy> getYellowEnemies() {
    	List<Position> yellowPositions = Background.getYellowPositions();
        List<YellowEnemy> yellowEnemyList = new ArrayList<YellowEnemy>();
        for (Position enemyStartPos : yellowPositions) {                
            YellowEnemy enemy = new YellowEnemy(enemyStartPos.getX(), enemyStartPos.getY());
            yellowEnemyList.add(enemy);
        }
        return yellowEnemyList;
    }

    /**
    * Calls the draw function for the player, enemies, bombs and explosions.&nbsp;Objects with a greater y position will be rendered afterwards.
    * @param app The PApplet app object
    * @param player The app's player object
    * @param redEnemies The app's redEnemies list
    * @param yellowEnemies The app's yellowEnemies list
    * @param bombs The app's bombs list
    * @param explosions The app's explosions list
    */
    public void handleLayerDrawings(PApplet app, Player player, List<RedEnemy> redEnemies, List<YellowEnemy> yellowEnemies, List<Bomb> bombs, List<Explosion> explosions) {
    	List<RedEnemy> leftOverReds = new ArrayList<RedEnemy>();
            for (RedEnemy enemy : redEnemies) { 
                if (enemy.getY() <= player.getY()) {
                    enemy.draw(app);
                } else {
                    leftOverReds.add(enemy);
                }
            }

            List<YellowEnemy> leftOverYellows = new ArrayList<YellowEnemy>();
            for (YellowEnemy enemy : yellowEnemies) { 
                if (enemy.getY() <= player.getY()) {
                    enemy.draw(app);
                } else {
                    leftOverYellows.add(enemy);
                }
            }

            for (Bomb bomb : bombs) {                
                bomb.draw(app);
            }

            for (Explosion explosion : explosions) {                
                explosion.draw(app);
            }

            player.draw(app);

            for (RedEnemy enemy : leftOverReds) { 
                enemy.draw(app);
            }
            for (YellowEnemy enemy : leftOverYellows) { 
                enemy.draw(app);
            }
    }
}