/**
 * Author: Ethan Lor
 *
 * Code for creating a bat and changing its location.
 */
import java.awt.Color;

public class Bat 
{

	private int width = 100;
	private int height = 20;
	private GameWindow gameWindow;
	private int xLoc = 0;
	private int yLoc = 0;
	private Rectangle bat;
	private int move;
	
	/**
	 * Creates bat and places it in window.
	 * @param x : initial x-coordinate of bat
	 * @param y : initial y-coordinate of bat
	 * @param w : window for bat to be placed in
	 */
	public Bat (int x, int y, GameWindow w)
	{
		gameWindow = w;
		xLoc = x;
		yLoc = y;
		bat = new Rectangle(xLoc, yLoc, width, height);
		bat.setBackground(Color.blue);
		gameWindow.add(bat);
	}
	
	/**
	 * Keeps track of bat location and moves bat.
	 */
	public void move()
	{
		int newXloc = xLoc + move;
		if (newXloc <= 0 || newXloc >= 400)
		{
			move = 0;
		}
		
		xLoc = xLoc + move;
		bat.setLocation(xLoc, yLoc);	
	}
	
	/**
	 * Set direction of bat to move left/right or stop movement. 
	 * @param dir : a String describing the direction of moment
	 */
	public void setDirection(String dir)
	{
		if (dir.equals("LEFT"))
		{
			move = -3;
		}
		else if (dir.equals("RIGHT"))
		{
			move = 3;
		}
		else if (dir.equals("STOP"))
		{
			move = 0;
		}
	}
	
	/**
	 * Returns width of bat.
	 * @return : integer number of width
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Returns x-coordinate.
	 * @return : integer number of x-coordinate location
	 */
	public int getX()
	{
		return bat.getX();
	}
	
	/**
	 * Returns y-coordinate.
	 * @return : integer number of y-coordinate location
	 */
	public int getY()
	{
		return bat.getY();
	}
}
