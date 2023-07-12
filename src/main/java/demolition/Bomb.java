package demolition;

import java.util.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Bomb{

	private int frameInterval = 1;

	private int x;
	private int y;
	private PImage sprite;
	private List<PImage> sprites;
	
	/**
    * Creates a new Bomb object.
    * @param x X coordinate
    * @param y Y coordinate
    */
	public Bomb(int x, int y) {
		this.x = x;
		this.y = y;
		this.sprites = Manager.bombSprites;		
	}

	/**
    * Updates the sprite value every 0.25 seconds and returns false, or returns true at 2 seconds.
    * @return Returns if true if 2 seconds (120 frames) have passed, otherwise false.
    */
	public boolean tick() {		
		boolean increment = true;

		if (this.sprite == null && this.sprites != null) {
			this.sprite = this.sprites.get(0);
		}

		// Sprites
		if (frameInterval % 15 == 0) {
			this.sprite = this.sprites.get(this.sprites.size() - (frameInterval % this.sprites.size()) - 1);					
		} 

		// Movement
		if (frameInterval == 120) {
			frameInterval = 1;
			increment = false;
			return true;
		}

		// Reset counter
		if (increment) {
			frameInterval ++;
		}

		return false;
	}

	/**
    * Draws the sprite to the application window.
    * @param app PApplet app object.
    */
	public void draw(PApplet app) {		
		app.image(this.sprite, this.x, this.y);
	}

	/**
    * Returns the x coordinate.
    * @return X coordinate
    */
	public int getX() {
		return this.x;
	}

	/**
    * Returns the y coordinate.
    * @return Y coordinate
    */
	public int getY() {
		return this.y;
	}
}