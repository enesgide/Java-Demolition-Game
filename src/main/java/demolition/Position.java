package demolition;

public class Position{
	private int x;
	private int y;

	/**
    * Creates a new Position object.
    * @param x X coordinate
    * @param y Y coordinate
    */
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
    * Gets the value of x.
    * @return The value of x.
    */
	public int getX(){
		return this.x;
	}

	/**
    * Gets the value of y.
    * @return The value of y.
    */
	public int getY(){
		return this.y;
	}
}