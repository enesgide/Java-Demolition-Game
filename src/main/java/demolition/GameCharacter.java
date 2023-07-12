package demolition;

import java.util.*;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class GameCharacter{

	protected int frameInterval = 1;

	protected int x;
	protected int y;	

	protected List<PImage> downSprites;
	protected List<PImage> upSprites;
	protected List<PImage> leftSprites;
	protected List<PImage> rightSprites;

	protected PImage sprite;
	protected int spriteCount;
	protected String lastFacing;
	protected String facing;

	public abstract void draw(PApplet app);
	
	/**
    * Moves the game character in the direction passed. Does not move if the next position is taken by a wall or has a negative coordinate.
    * @param direction Direction to move ("LEFT", "RIGHT", "UP", "DOWN").
    */
	public void move(String direction) {
		Position nextPos = this.getNextPosInDirection(direction);
		int nextX = nextPos.getX();
		int nextY = nextPos.getY();

		if (nextX < 0 || nextY < 0 || checkCollisions(nextX, nextY)) {
			return;
		}

		this.setLastFacing(this.getFacing());
		this.setFacing(direction);
		this.updateSprite();

		if (nextX != this.getX()) {
			this.setX(nextX);
		}

		if (nextY != this.getY()) {
			this.setY(nextY);
		}
	}

	/**
    * Sets the value of x to newX.
    * @param newX X coordinate.
    */
	public void setX(int newX) {
		this.x = newX;
	}

	/**
    * Returns the value of x.
    * @return The value of x.
    */
	public int getX() {
		return this.x;
	}

	/**
    * Sets the value of y to newY.
    * @param newY Y coordinate.
    */
	public void setY(int newY) {
		this.y = newY;
	}

	/**
    * Returns the value of y.
    * @return The value of y.
    */
	public int getY() {
		return this.y;
	}

	/**
    * Returns the position that the game character would be if they moved in the direction passed.
    * @param direction The direction to move in.
    * @return The next position.
    */
	public Position getNextPosInDirection(String direction) {
		int nextX = -1;
		int nextY = -1;

		if (direction == "LEFT") {
			nextX = this.getX() - 32;
			nextY = this.getY();
		} else if (direction == "RIGHT") {
			nextX = this.getX() + 32;
			nextY = this.getY();
		} else if (direction == "UP") {
			nextX = this.getX();
			nextY = this.getY() - 32;
		} else if (direction == "DOWN") {
			nextX = this.getX();
			nextY = this.getY() + 32;
		}

		return new Position(nextX, nextY);
	}

	/**
    * Sets the value of facing.
    * @param direction The direction to look.
    */
	public void setFacing(String direction) {
		this.facing = direction;
	}

	/**
    * Gets the value of facing.
    * @return The direction that the game character is facing.
    */
	public String getFacing() {
		return this.facing;
	}

	/**
    * Sets the value of lastFacing.
    * @param direction The direction faced in the previous frame.
    */
	public void setLastFacing(String direction) {
		this.lastFacing = direction;
	}

	/**
    * Gets the value of lastFacing.
    * @return The direction faced in the previous frame.
    */
	public String getLastFacing() {
		return this.lastFacing;
	}

	/**
    * Increments the frameInterval by 1. Resets frameInterval to 1 if it is equal to 60.
    * @return The new value of frameInterval.
    */
	public int incrementFrame() {
		if (frameInterval == 60) {
			frameInterval = 1;		
		} else {
			frameInterval ++;
		}
		return frameInterval;
	}

	/**
    * Changes the value of sprite based on the spriteCount, and then increments the spriteCount. If sprite is null then return.
    */
	public void updateSprite() {	
		if (this.sprite == null) {
			return;		
		}

		if (this.getFacing() != this.getLastFacing() || this.spriteCount >= 4) {
			this.spriteCount = 0;
			this.setLastFacing(this.getFacing());
		}

		if (this.getFacing() == "LEFT") {
			this.sprite = this.leftSprites.get(this.spriteCount);
		} else if (this.getFacing() == "RIGHT") {
			this.sprite = this.rightSprites.get(this.spriteCount);
		} else if (this.getFacing() == "UP") {
			this.sprite = this.upSprites.get(this.spriteCount);
		} else if (this.getFacing() == "DOWN") {
			this.sprite = this.downSprites.get(this.spriteCount);
		}
		this.spriteCount ++;
	}

	/**
    * Checks if there is a solid wall tile or broken wall tile in the position passed.
    * @param nextX X coordinate to check
    * @param nextY Y coordinate to check
    * @return Whether or not a solid or broken wall tile was found.
    */
	public boolean checkCollisions(int nextX, int nextY) {
		Position playerNextPos = new Position(nextX, nextY);

		for (Position checkPos : Background.getWallPositions()) {
			if (playerNextPos.getX() == checkPos.getX() && playerNextPos.getY() == checkPos.getY()) {
				return true;
			}
		}

		for (Position checkPos : Background.getBrokenPositions()) {
			if (playerNextPos.getX() == checkPos.getX() && playerNextPos.getY() == checkPos.getY()) {
				return true;
			}
		}

		return false;
	}

	/**
    * Checks and returns if the game character has been hit by an explosion.
    * @return Whether or not the game character was hit by an explosion.
    */
	public boolean checkExplosion() {
		for (Position explosionPos : Background.getExplosionPositions()) {
			if (explosionPos.getX() == this.x && explosionPos.getY() == this.y) {
				return true;
			}
		}

		return false;
	}
}