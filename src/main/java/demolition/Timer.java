package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

public class Timer extends Topbar{	

	private int maxTime;
	private int timeLeft;

	/**
    * Creates a new Timer object.
    * @param maxTime The maximum time for each round.
    * @param imgX X coordinate for the sprite
    * @param imgY Y coordinate for the sprite
    * @param msgX X coordinate for the message text
    * @param msgY Y coordinate for the message text
    */
	public Timer(int maxTime, int imgX, int imgY, int msgX, int msgY) {	
		this.maxTime = maxTime;
		this.timeLeft = maxTime;
		this.message = Integer.toString(maxTime);
		this.font = Manager.font;
		this.sprite = Manager.timerSprite;	
		this.imgX = imgX;
		this.imgY = imgY;	
		this.msgX = msgX;
		this.msgY = msgY;		
	}

	/**
    * Decrements timeLeft every second (60 frames) and returns true if the time left is 0.&nbsp;Otherwise, return false.
    * @return If the time remaining is 0, return true.&nbsp;Otherwise, return false.
    */
	public boolean tick() {		
		if (frameInterval == 60) {
			if (this.getTimeLeft() > 0) {
				this.setTimeLeft(this.getTimeLeft() - 1);			
			} else {
				return true;
			}
		}

		this.incrementFrame();

		return false;	
	}

	/**
    * Sets the value of maxTime.
    * @param maxTime Maximum time for thr round
    */
	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	/**
    * Gets the value of maxTime.
    * @return The value of maxTime.
    */
	public int getMaxTime() {
		return this.maxTime;
	}

	/**
    * Sets the value of timeLeft and updates the message to be the string value of timeLeft.
    * @param timeLeft Time remaining in the round
    */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
		this.message = Integer.toString(timeLeft);
	}

	/**
    * Gets the value of timeLeft.
    * @return The value of timeleft.
    */
	public int getTimeLeft() {
		return this.timeLeft;
	}

	/**
    * Increments frameInterval, or sets it to 1 if it reaches 60.
    * @return The value of frameInterval.
    */
	public int incrementFrame() {
		if (frameInterval == 60) {
			frameInterval = 1;		
		} else {
			frameInterval ++;
		}
		return frameInterval;
	}
}