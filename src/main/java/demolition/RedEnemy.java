package demolition;

import java.util.*;
import processing.core.PApplet;
import processing.core.PImage;

public class RedEnemy extends GameCharacter{

	/**
    * Creates a new RedEnemy object.
    * @param x X coordinate
    * @param y Y coordinate
    */
	public RedEnemy(int x, int y) {
		this.x = x;
		this.y = y;	

		this.downSprites = Manager.downRedSprites;
		this.upSprites = Manager.upRedSprites;
		this.leftSprites = Manager.leftRedSprites;
		this.rightSprites = Manager.rightRedSprites;			
		
		this.lastFacing = "DOWN";
		this.facing = "DOWN";
		this.spriteCount = 0;
	}

	/**
    * Checks whether the enemy was killed by an explosion.&nbsp;If not, the sprite is updated every 0.2 seconds (12 frames) and the enemy is moved every 1 second (60 frames).
    * @return If the enemy collides with an explosion, return true.&nbsp;Otherwise, return false.
    */
	public boolean tick() {

		// Check death
		if (this.checkExplosion()) {
			Background.removeRedPosition(this.x, this.y);
			return true;
		}

		// Sprites
		if (this.sprite == null && this.downSprites != null) {
			this.sprite = this.downSprites.get(0);
		}

		if (frameInterval % 12 == 0) {
			this.updateSprite();					
		} 

		// Movement
		if (frameInterval == 60) {
			String direction = this.calculateDirection();
			Background.removeRedPosition(this.x, this.y);
			this.move(direction);
			Background.addRedPosition(this.x, this.y);
		}

		this.incrementFrame();

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
    * Decides on the next available direction for the enemy to move. If the current direction is blocked, the direction is chosen at random by checking which directions are not blocked by walls.
    * @return If the current path ahead is clear, return the current facing direction.&nbsp;Otherwise, return the randomly chosen direction for the enemy to move.
    */
	public String calculateDirection() {
		List<String> directions = Arrays.asList(new String[]{"LEFT", "UP", "RIGHT", "DOWN"});
		List<String> availableDirections = new ArrayList<String>();

		// Check if direction even needs to be changed (only if enemy is at a dead end)
		Position nextPos = this.getNextPosInDirection(this.getFacing());
		int nextX = nextPos.getX();
		int nextY = nextPos.getY();

		if (nextX >= 0 && nextY >= 0 && !checkCollisions(nextX, nextY)) {
			return this.getFacing();
		}

		// If at a dead end then continue
		for (int i = 0; i < 4; i++) {
			String nextDirection = directions.get(i);

			nextPos = this.getNextPosInDirection(nextDirection);
			nextX = nextPos.getX();
			nextY = nextPos.getY();

			if (nextX >= 0 && nextY >= 0 && !checkCollisions(nextX, nextY)) {
				availableDirections.add(nextDirection);
			}
		}

		if (availableDirections.size() == 0) {
			return this.getFacing();
		}

		int randInt = (int)(Math.random() * availableDirections.size());
		String chosenDirection = availableDirections.get(randInt);

		this.setLastFacing(this.getFacing());
		this.setFacing(chosenDirection);

		return chosenDirection;
	}
}