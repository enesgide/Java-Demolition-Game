package demolition;

import java.util.*;
import processing.core.PApplet;
import processing.core.PImage;

public class YellowEnemy extends GameCharacter{

	/**
    * Creates a new YellowEnemy object.
    * @param x X coordinate
    * @param y Y coordinate
    */
	public YellowEnemy(int x, int y) {
		this.x = x;
		this.y = y;	

		this.downSprites = Manager.downYellowSprites;
		this.upSprites = Manager.upYellowSprites;
		this.leftSprites = Manager.leftYellowSprites;
		this.rightSprites = Manager.rightYellowSprites;			
		
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
			Background.removeYellowPosition(this.x, this.y);
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
			Background.removeYellowPosition(this.x, this.y);
			this.move(direction);
			Background.addYellowPosition(this.x, this.y);
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
    * Decides on the next available direction for the enemy to move. If the current direction is blocked, it finds the next available direction in a clockwise rotation.
    * @return If the current path ahead is clear, return the current facing direction.&nbsp;Otherwise, return the next available direction in a clockwise rotation.
    */
	public String calculateDirection() {
		List<String> directions = Arrays.asList(new String[]{"LEFT", "UP", "RIGHT", "DOWN"});

		int i = directions.indexOf(this.getFacing());
		i --;

		for (int j = 0; j < 4; j++) {
			i ++;
			if (i == directions.size()) {
				i = 0;
			}

			String nextDirection = directions.get(i);

			Position nextPos = this.getNextPosInDirection(nextDirection);
			int nextX = nextPos.getX();
			int nextY = nextPos.getY();

			if (nextX >= 0 && nextY >= 0 && !checkCollisions(nextX, nextY)) {
				this.setLastFacing(this.getFacing());
				this.setFacing(nextDirection);
				return nextDirection;
			}
		}

		return this.getFacing();
	}
}