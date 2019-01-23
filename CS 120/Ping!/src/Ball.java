/**
 * Author: Ethan Lor
 *
 * Code for creating a red ball, checking its location, changing its direction,
 * changing its speed, and counting the number of bounces of the bat.
 */
import java.awt.Color;

public class Ball 
{
	
	private GameWindow gameWindow;
	private Oval ball;
	private int ballSize = 25;
	private int speed = 1;
	private int xLoc = 0;
	private int yLoc = 0;
	private int xDirection = 0;
	private int yDirection = 0;
	private final int up = -1;
	private final int down = 1;
	private final int right = 1;
	private final int left = -1;
	private int bounces = 0;
	
	/**
	 * Creates a red ball, place ball in window, and choose initial ball direction.
	 * @param x : initial x-coordinate of ball
	 * @param y : initial y-coordinate of ball
	 * @param w : window for ball to be placed in
	 */
	public Ball(int x, int y, GameWindow w)
	{
		gameWindow = w;
		xLoc = x;
		yLoc = y;
		ball = new Oval(xLoc, yLoc, ballSize, ballSize);
		ball.setBackground(Color.red);
		gameWindow.add(ball);
		
		int rand = (int)(Math.random()*2);
		if(rand == 0)
		{
			xDirection = right;
			yDirection = up;
		}
		else
		{
			xDirection = left;
			yDirection = up;
		}
	}

	/**
	 * Changes location of ball.
	 */
	public void move()
	{
		checkLocation();
		xLoc = xLoc + speed * xDirection;
		yLoc = yLoc + speed * yDirection;
		ball.setLocation(xLoc, yLoc); 
	}
	
	/**
	 * Checks location of ball and changes direction of ball.
	 */
	private void checkLocation()
	{
		// Checks if ball has traveled slightly past R/L and top window boarder.
		// If it has will set boarder locations back to zero keeping ball inbounds.
		if (xLoc < 0 || yLoc < 0 || xLoc > (gameWindow.getWidth() - ballSize))
		{
			if(xLoc < 0)
			{
				xLoc = 0;
			}
			else if (yLoc < 0)
			{
				yLoc = 0;
			}
			else if (xLoc > (gameWindow.getWidth() - ballSize))
			{
				xLoc = (gameWindow.getWidth() - ballSize);
			}
		}
				
		// Checks location of ball, if it has cross the bottom edge, hit a boarder,
		// and changes the direction accordingly.
		if (yLoc >= gameWindow.getBottomEdge())
		{
			gameWindow.stopTime();
			return;
		}
		else if (xLoc == 0 && yLoc == 0)
		{
			xDirection = right;
			yDirection = down;
		}
		else if (xLoc == (gameWindow.getWidth() - ballSize) && yLoc == 0)
		{
			xDirection = left;
			yDirection = down;
		}
		else if ((xLoc == 0 && yDirection == up) || (xLoc == 0 && yDirection == down))
		{
			xDirection = right;
		}
		else if ((xLoc == (gameWindow.getWidth() - ballSize) && yDirection == up) || (xLoc == (gameWindow.getWidth() - ballSize) && yDirection == down))
		{
			xDirection = left;
		}
		else if ((yLoc == 0 && xDirection == left) || (yLoc == 0 && xDirection == right))
		{
			yDirection = down;
		}
	}
	
	/**
	 * Checks and compares locations of ball and bat for "bounces" on the bat.
	 * @param B : Bat object reference
	 */
	public void checkBatHit(Bat B)
	{
		int xBat = B.getX();
		int yBat = B.getY();
		int xBall = ball.getX() + (ballSize/2) ;
		int yBall = ball.getY() + ballSize;
		
		if (yDirection == down)
		{
			if (yBall >= (yBat) && yBall <= (yBat + 20) && xBall >= xBat && xBall <= (xBat + B.getWidth()))
			{
				yDirection = up;
				bounces++;
				
				if (bounces % 5 == 0)
				{
					speed++;
				}
			}
		}
	}
	
	/**
	 * Returns number of "bounces" off the bat.
	 * @return : integer number of bounces
	 */
	public int getBounces()
	{
		return bounces;
	}
	
	/**
	 * Returns speed number or pixel amount ball is changing by. 
	 * @return : integer number of speed
	 */
	public int getSpeed()
	{
		return speed;
	}
}
