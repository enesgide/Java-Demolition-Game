package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

public abstract class Topbar{
	protected int frameInterval = 1;

	protected PFont font;
	protected PImage sprite;
	protected String message;
	protected int imgX;
	protected int imgY;
	protected int msgX;
	protected int msgY;
	
	/**
    * Draws the sprite and writes the text to the application window.
    * @param app PApplet app object.
    */
	public void draw(PApplet app) {
		app.image(this.sprite, this.imgX, this.imgY);

		int fontSize = 20;
		app.textFont(this.font);
		app.textSize(fontSize);
		app.fill(0,0,0);
		app.text(this.message, this.msgX, this.msgY);
	}
}

 