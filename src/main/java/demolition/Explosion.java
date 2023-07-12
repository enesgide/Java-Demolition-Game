package demolition;

import java.util.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Explosion{

	private int frameInterval = 1;

	private int x;
	private int y;
	private List<PImage> sprites;
	private List<Position> positions;
	
	/**
    * Creates a new Explosion object.
    * @param x X coordinate
    * @param y Y coordinate
    */
	public Explosion(int x, int y) {
		this.x = x;
		this.y = y;
		this.sprites = Manager.explosionSprites;
		this.positions = new ArrayList<Position>();
	}

	/**
    * On the first method call, it adds the appropriate horizontal and vertical explosion positions to the ArrayList and returns null.&nbsp;After 0.5 seconds, return the object's positions list. 
    * @return Returns the object's positions list after 0.5 seconds (30 frames), otherwise null.
    */
	public List<Position> tick() {	
		// Movement
		if (frameInterval == 1) {
			List<Integer> increments = Arrays.asList(new Integer[]{32, 64});
			Background.addExplosionPosition(this.x, this.y);

			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < increments.size(); j++) {

					int sign = 1;
					if (i % 2 != 0) {
						sign = sign * -1;
					}


					Position pos;
					if (i < 2) {
						pos = new Position(this.x + increments.get(j) * sign, this.y);
					} else {
						pos = new Position(this.x, this.y + increments.get(j) * sign);
					}

					int result = checkCollisions(pos);
					if (result >= 0) {
						this.positions.add(pos);
						Background.addExplosionPosition(pos.getX(), pos.getY());						
					}
					if (result <= 0) {
						break;
					}
				}
			}

		} else if (frameInterval == 30) {
			return this.positions;
		}

		this.incrementFrame();

		return null;
	}

	/**
    * Draws the explosions to the application window.
    * @param app PApplet app object.
    */
	public void draw(PApplet app) {	
		app.image(this.sprites.get(0), this.x, this.y);	

		for (Position pos : this.positions) {
			if (pos.getX() == this.x + 32 || pos.getX() == this.x - 32) {
				app.image(this.sprites.get(1), pos.getX(), pos.getY());	
			} else if (pos.getX() == this.x + 64) {
				app.image(this.sprites.get(4), pos.getX(), pos.getY());	
			} else if (pos.getX() == this.x - 64) {
				app.image(this.sprites.get(3), pos.getX(), pos.getY());	
			} else if (pos.getY() == this.y + 32 || pos.getY() == this.y - 32) {
				app.image(this.sprites.get(2), pos.getX(), pos.getY());	
			} else if (pos.getY() == this.y + 64) {
				app.image(this.sprites.get(6), pos.getX(), pos.getY());	
			} else if (pos.getY() == this.y - 64) {
				app.image(this.sprites.get(5), pos.getX(), pos.getY());	
			}
		}
		
	}

	/**
    * Checks the received position for colliding solid wall and broken wall tiles.
    * @param pos Position to check.
    * @return If a wall tile is found, return -1.&nbsp;If a broken tile is found, return 0.&nbsp;Otherwise, return 1.
    */
	public int checkCollisions(Position pos) {
		for (Position checkPos : Background.getWallPositions()) {
			if (pos.getX() == checkPos.getX() && pos.getY() == checkPos.getY()) {
				return -1;
			}
		}

		for (Position checkPos : Background.getBrokenPositions()) {
			if (pos.getX() == checkPos.getX() && pos.getY() == checkPos.getY()) {
				Background.removeBrokenPosition(checkPos.getX(), checkPos.getY());
				return 0;
			}
		}

		return 1;
	}

	/**
    * Increment frameInterval and return its new value.
    * @return The new value of frameInterval.
    */
	public int incrementFrame() {
		frameInterval ++;
		return frameInterval;
	}

	/**
    * Get the x coordinate of the explosion centre.
    * @return The x coordinate.
    */
	public int getCentreX() {
		return this.x;
	}

	/**
    * Get the y coordinate of the explosion centre.
    * @return The y coordinate.
    */
	public int getCentreY() {
		return this.y;
	}
}