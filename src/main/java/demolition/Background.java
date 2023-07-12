package demolition;

import java.util.*;
import java.io.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

public class Background{	
	public static List<Position> emptyPositions = new ArrayList<Position>();
	public static List<Position> wallPositions = new ArrayList<Position>();
	public static List<Position> brokenPositions = new ArrayList<Position>();

	public static List<Position> redPositions = new ArrayList<Position>();
	public static List<Position> yellowPositions = new ArrayList<Position>();

	public static List<Position> explosionPositions = new ArrayList<Position>();

	public static Position goalPos;
	public static Position plrStartPos;

	private String mapFile;
	private int level;
	private boolean reset;
	private PFont font;
	private PImage lives;
	private PImage empty;
	private PImage broken;
	private PImage goal;
	private PImage wall;

	/**
    * Creates a new App object.
    * @param mapFile Path of the file containing the map data.
    * @param level Level of the current map.
    */
	public Background(String mapFile, int level) {	
		this.mapFile = mapFile;
		this.level = level;	
		this.reset = false;
		
		this.font = Manager.font;
		this.empty = Manager.emptyTile;
		this.broken = Manager.brokenTile;
		this.goal = Manager.goalTile;
		this.wall = Manager.wallTile;	
	}

	/**
    * Loads in a new map, or overrides any old maps based on the mapFile of the Background object.
    * @param app PApplet app object.
    */
	public void setup(PApplet app) {

		int scale = 32;
		int x = 0;
		int y = 2 * scale;

		try {
			File f = new File(this.mapFile);
			Scanner reader = new Scanner(f);
			List<Character> chars = Arrays.asList(new Character[]{' ', 'B', 'G', 'W'});

			while (reader.hasNextLine()) {
				String line = reader.nextLine();

				for (int i = 0; i < line.length(); i++) {
					Character c = line.charAt(i);

					if (c == 'B') {
						Position newPos = new Position(x, y);
						brokenPositions.add(newPos);
						PImage img = app.loadImage("src/main/resources/broken/broken.png");
						app.image(img, x, y);
					} else if (c == 'G') {
						goalPos = new Position(x, y);
						app.image(this.goal, x, y);
					} else if (c == 'W') {
						Position newPos = new Position(x, y);
						wallPositions.add(newPos);
						PImage img = app.loadImage("src/main/resources/wall/solid.png");
						app.image(img, x, y);
					} else {
						Position newPos = new Position(x, y);
						emptyPositions.add(newPos);
						app.image(this.empty, x, y);
						if (c == 'P') {
							plrStartPos = new Position(x, y);
						} else if (c == 'R') {
							Position enemyPos = new Position(x, y);
							redPositions.add(enemyPos);
						} else if (c == 'Y') {
							Position enemyPos = new Position(x, y);
							yellowPositions.add(enemyPos);
						}
					}

					x += scale;
		        }

		        x = 0;
		        y += scale;
			}

			reader.close();

		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound when setting up the map");
		}

	}

	/**
    * Draws in the map components into the application window.
    * @param app PApplet app object.
    */
	public void draw(PApplet app) {

		for (Position pos : emptyPositions) {
			app.image(this.empty, pos.getX(), pos.getY());
		}

		for (Position pos : wallPositions) {
			app.image(this.wall, pos.getX(), pos.getY());
		}

		for (Position pos : brokenPositions) {
			app.image(this.broken, pos.getX(), pos.getY());
		}

		app.image(this.goal, goalPos.getX(), goalPos.getY());
	}

	/**
    * Sets the reset attribute to either true or false.
    * @param status The boolean value to change reset to.
    */
	public void setReset(boolean status) {
        this.reset = status;
    }

    /**
    * Gets the reset value.
    * @return The value of reset.
    */
    public boolean getReset() {
        return this.reset;
    }

    /**
    * Increments the level value and updates the mapFile to correspond with the new level.
    */
	public void changeLevel() {
		this.level += 1;
		this.mapFile = Manager.levelPaths.get(this.level-1);
	}

	/**
    * Gets the current map's level.
    * @return The current level.
    */
	public int getLevel() {
		return this.level;
	}

	/**
    * Static method which clears all array lists and static variables stored in Background.java to clear old map data.
    */
	public static void reset() {
		emptyPositions.clear();
		wallPositions.clear();
		brokenPositions.clear();
		redPositions.clear();
		yellowPositions.clear();
		explosionPositions.clear();
		goalPos = null;
		plrStartPos = null;
	}

	/**
    * Static method which returns the player's starting position.
    * @return The player's starting position.
    */
	public static Position getPlayerStartingPosition() {
		return plrStartPos;
	}

	/**
    * Static method which returns the goal tile's position.
    * @return The goal position.
    */
	public static Position getGoalPosition() {
		return goalPos;
	}

	/**
    * Static method which returns the positions for empty tiles.
    * @return List of positions for empty tiles.
    */
	public static List<Position> getEmptyPositions() {
		return emptyPositions;
	}

	/**
    * Static method which returns the positions for solid wall tiles.
    * @return List of positions for solid wall tiles.
    */
	public static List<Position> getWallPositions() {
		return wallPositions;
	}

	/**
    * Static method which returns the positions for broken wall tiles.
    * @return List of positions for broken wall tiles.
    */
	public static List<Position> getBrokenPositions() {
		return brokenPositions;
	}

	/**
    * Static method which returns the positions for explosions.
    * @return List of positions for explosions.
    */
	public static List<Position> getExplosionPositions() {
		return explosionPositions;
	}

	/**
    * Static method which returns the positions for red enemies.
    * @return List of positions for red enemies.
    */
	public static List<Position> getRedPositions() {
		return redPositions;
	}

	/**
    * Static method which returns the positions for yellow enemies.
    * @return List of positions for yellow enemies.
    */
	public static List<Position> getYellowPositions() {
		return yellowPositions;
	}

	/**
    * Static method which adds a Position object to explosionPositions.
    * @param newX X coordinate
    * @param newY Y coordinate
    */
	public static void addExplosionPosition(int newX, int newY) {
		Position newPos = new Position(newX, newY);
		explosionPositions.add(newPos);
	}

	/**
    * Static method which adds a Position object to redPositions.
    * @param newX X coordinate
    * @param newY Y coordinate
    */
	public static void addRedPosition(int newX, int newY) {
		Position newPos = new Position(newX, newY);
		redPositions.add(newPos);
	}

	/**
    * Static method which adds a Position object to yellowPositions.
    * @param newX X coordinate
    * @param newY Y coordinate
    */
	public static void addYellowPosition(int newX, int newY) {
		Position newPos = new Position(newX, newY);
		yellowPositions.add(newPos);
	}

	/**
    * Static method which removes a position from brokenPositions.
    * @param oldX X coordinate
    * @param oldY Y coordinate
    */
	public static void removeBrokenPosition(int oldX, int oldY) {
		for (Position oldPos : brokenPositions) {
			if (oldPos.getX() == oldX && oldPos.getY() == oldY) {
				brokenPositions.remove(oldPos);
				emptyPositions.add(oldPos);
				break;
			}
		}
	}

	/**
    * Static method which removes a position from explosionPositions.
    * @param oldX X coordinate
    * @param oldY Y coordinate
    */
	public static void removeExplosionPosition(int oldX, int oldY) {
		for (Position oldPos : explosionPositions) {
			if (oldPos.getX() == oldX && oldPos.getY() == oldY) {
				explosionPositions.remove(oldPos);
				break;
			}
		}
	}

	/**
    * Static method which removes a position from redPositions.
    * @param oldX X coordinate
    * @param oldY Y coordinate
    */
	public static void removeRedPosition(int oldX, int oldY) {
		for (Position oldPos : redPositions) {
			if (oldPos.getX() == oldX && oldPos.getY() == oldY) {
				redPositions.remove(oldPos);
				break;
			}
		}
	}

	/**
    * Static method which removes a position from yellowPositions.
    * @param oldX X coordinate
    * @param oldY Y coordinate
    */
	public static void removeYellowPosition(int oldX, int oldY) {
		for (Position oldPos : yellowPositions) {
			if (oldPos.getX() == oldX && oldPos.getY() == oldY) {
				yellowPositions.remove(oldPos);
				break;
			}
		}
	}
}