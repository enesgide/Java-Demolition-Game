package demolition;

import java.util.*;
import processing.core.PApplet;
import processing.core.PFont;

public class EndScreen{

	private boolean victory;	
	private boolean loss;
	private PFont font;

	/**
    * Creates a new EndScreen object.
    */
	public EndScreen() {
		this.victory = false;
		this.loss = false;
		this.font = Manager.font;	
	}

	/**
    * Draws the orange end screen and writes the victory/loss text in the middle.
    * @param app PApplet app object.
    */
	public void draw(PApplet app) {		
		int fontSize = 24;
		app.textFont(this.font);
		app.textSize(fontSize);
		app.fill(0, 0, 0);
		if (this.getVictory()) {
			app.text("YOU WIN", 155, 252);
		} else if (this.getLoss()) {
			app.text("GAME OVER", 135, 252);
		}
	}

	/**
    * Sets the victory value to the value of status.
    * @param status New value of victory.
    */
	public void setVictory(boolean status) {
		this.victory = status;
	}

	/**
    * Gets the value of the victory attribute.
    * @return The value of victory.
    */
	public boolean getVictory() {
		return this.victory;
	}

	/**
    * Sets the loss value to the value of status.
    * @param status New value of loss.
    */
	public void setLoss(boolean status) {
		this.loss = status;
	}

	/**
    * Gets the value of the loss attribute.
    * @return The value of loss.
    */
	public boolean getLoss() {
		return this.loss;
	}
}