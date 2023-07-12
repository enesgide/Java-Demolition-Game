package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

public class Lives extends Topbar{	

	private int lives;

	/**
    * Creates a new Lives object.
    * @param imgX X coordinate for the sprite
    * @param imgY Y coordinate for the sprite
    * @param msgX X coordinate for the message text
    * @param msgY Y coordinate for the message text
    */
	public Lives(int imgX, int imgY, int msgX, int msgY) {	
		this.message = Integer.toString(lives);
		this.font = Manager.font;
		this.sprite = Manager.livesSprite;		
		this.imgX = imgX;
		this.imgY = imgY;	
		this.msgX = msgX;
		this.msgY = msgY;	
	}

	/**
    * Calls updateMessage(), and returns true if the player's lives equal 0.&nbsp;Otherwise, return false.
    * @param player The player object.
    * @return If the player's lives equal 0, return true.&nbsp;Otherwise, return false.
    */
	public boolean tick(Player player) {
		this.updateMessage(player.getLives());
		if (player.getLives() == 0) {
			return true;
		}
		return false;	
	}

	/**
    * Changes message based on the player's lives left.
    * @param newLives Number of lives that the player has left.
    */
	public void updateMessage(int newLives) {
		this.message = Integer.toString(newLives);
	}
}