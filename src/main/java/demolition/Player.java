package demolition;

import java.util.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends GameCharacter{

	private int level;	 
	private int lives;

	/**
    * Creates a new Lives object.
    * @param x X coordinate
    * @param y Y coordinate
    * @param level Current level
    * @param lives Number of lives
    */
	public Player(int x, int y, int level, int lives) {
		this.level = level;
		this.lives = lives;
		this.x = x;
		this.y = y;	

		this.downSprites = Manager.downPlayerSprites;
		this.upSprites = Manager.upPlayerSprites;
		this.leftSprites = Manager.leftPlayerSprites;
		this.rightSprites = Manager.rightPlayerSprites;			
		
		this.lastFacing = "DOWN";
		this.facing = "DOWN";
		this.spriteCount = 0;
	}

	/**
    * Creates a new Lives object.
    * @param map The map object
    * @param timer The timer object
    * @return If the player collides with an explosion or enemy, return the value of kill().&nbsp;If the player has reached the goal, return true.&nbsp;Otherwise, return false.
    */
	public boolean tick(Background map, Timer timer) {
		if (frameInterval == 12) {
			this.updateSprite();
			frameInterval = 1;		
		} else {
			frameInterval ++;
		}

		if (this.sprite == null && this.downSprites != null) {
			this.sprite = this.downSprites.get(0);
		}

		if (this.checkExplosion() || this.checkEnemy()) {			
			return this.kill(map, timer);
		}

		if (this.checkGoalReached()) {
			return true;
		}

		return false;
	}

	/**
    * Draws the sprite to the application window.
    * @param app PApplet app object.
    */
	public void draw(PApplet app) {	
		app.image(this.sprite, this.x, this.y - 16);
	}

	/**
    * Sets the value of level to newLevel.
    * @param newLevel New value of level
    */
	public void setLevel(int newLevel) {
		this.level = newLevel;
	}

	/**
    * Returns the value of level.
    * @return The value of level.
    */
	public int getLevel() {
		return this.level;
	}

	/**
    * Sets the value of lives to newLives.
    * @param newLives New value of lives
    */
	public void setLives(int newLives) {
		this.lives = newLives;
	}

	/**
    * Returns the value of lives.
    * @return The value of lives.
    */
	public int getLives() {
		return this.lives;
	}

	/**
    * Changes the attributes of the player to their default values.
    */
	public void reset() {
		this.x = Background.plrStartPos.getX();
        this.y = Background.plrStartPos.getY();
        this.facing = "DOWN";
        this.lastFacing = "DOWN";
        this.spriteCount = 0;
	}

	/**
    * Removes 1 life from the player and returns true if they have 0 lives left, otherwise returns false.
    * @param map The map object
    * @param timer The timer object
    * @return Return true if the player has 0 lives, otherwise return false.
    */
	public boolean kill(Background map, Timer timer) {
		if (this.lives > 0) {
			map.setReset(true);
			this.lives --;
			timer.setTimeLeft(timer.getMaxTime());
			return false;
		} else {
			return true;
		}
	}

	/**
    * Checks whether there are any enemies in the same position as the player.
    * @return Return true if an enemy if found, otherwise return false.
    */
	public boolean checkEnemy() {
		for (Position enemyPos : Background.getRedPositions()) {
			if (enemyPos.getX() == this.x && enemyPos.getY() == this.y) {
				return true;
			}
		}
		for (Position enemyPos : Background.getYellowPositions()) {
			if (enemyPos.getX() == this.x && enemyPos.getY() == this.y) {
				return true;
			}
		}
		return false;
	}

	/**
    * Checks whether the player is in the same position as the goal position.
    * @return Return true if the goal is reached, otherwise return false.
    */
	public boolean checkGoalReached() {
		Position goalPos = Background.getGoalPosition();
		if (this.lives > 0 && this.x == goalPos.getX() && this.y == goalPos.getY()) {
			this.level ++;
			return true;
		}

		return false;
	}
}