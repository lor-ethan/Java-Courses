/**
 * Creates Rectangle block object.  Sets block colors, locations, and directions.
 * Checks block locations and collisions with walls or each other.
 * Changes block directions.
 *
 * @author Ethan Lor
 */
import java.awt.Color;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ChaseBlock extends Rectangle
{
	private JFrame window;
	private int blockSize = 0;
	private int xDirection = 0;
	private int yDirection = 0;
	
	private final int up = -1;
	private final int down = 1;
	private final int right = 1;
	private final int left = -1;

	/**
	 * Creates Rectangle object assigning random color and directions.
	 * 
	 * @param x : block x location
	 * @param y : block y location
	 * @param w : block width
	 * @param h : block height
	 * @param win : window to put block in
	 */
	public ChaseBlock(int x, int y, int w, int h, JFrame win) 
	{
		super(x, y, w, h);
		
		blockSize = w;
		window = win;
		
		randomColor();
		direction();
	}
	
	/**
	 * Helper method to assign random color.
	 */
	private void randomColor()
	{
		int randColor = (int)(Math.random()*4);
		if (randColor == 0)
		{
			setBackground(Color.red);
		}
		else if (randColor == 1)
		{
			setBackground(Color.yellow);
		}
		else if (randColor == 2)
		{
			setBackground(Color.green);
		}
		else if (randColor == 3)
		{
			setBackground(Color.blue);
		}
	}

	/**
	 * Helper method to assign random direction.
	 */
	private void direction()
	{
		int rand = (int)(Math.random()*4);
		if(rand == 0)
		{
			xDirection = right;
			yDirection = up;
		}
		else if (rand == 1)
		{
			xDirection = left;
			yDirection = up;
		}
		else if (rand == 2)
		{
			xDirection = right;
			yDirection = down;
		}
		else if (rand == 3)
		{
			xDirection = left;
			yDirection = down;
		}
	}
	/**
	 * Moves block in the assigned directions.
	 */
	public void move()
	{
		checkLocation();
		setLocation(getX() + xDirection, getY() + yDirection);
	}
	
	/**
	 * Checks location of block in window and change directions accordingly if wall has been hit.
	 */
	private void checkLocation()
	{
		if (getX() == 0 && getY() == 0)
		{
			xDirection = right;
			yDirection = down;
		}
		else if (getX() == (window.getWidth() - blockSize) && getY() == 0)
		{
			xDirection = left;
			yDirection = down;
		}
		else if (getX() == 0 && getY() == window.getHeight() - 2*blockSize)
		{
			xDirection = right;
			yDirection = up;
		}
		else if (getX() == (window.getWidth() - blockSize) && getY() == window.getHeight() - 2*blockSize)
		{
			xDirection = left;
			yDirection = up;
		}
		else if (getY() >= window.getHeight() - 2*blockSize)
		{
			yDirection = up;
		}
		else if (getY() <= 0)
		{
			yDirection = down;
		}
		else if (getX() <= 0)
		{
			xDirection = right;
		}
		else if (getX() >= (window.getWidth() - blockSize))
		{
			xDirection = left;
		}
	}
	
	/**
	 * Returns the direction of block along x axis.
	 * 
	 * @return : x direction of block.
	 */
	public int getXdirection()
	{
		return xDirection;
	}
	
	/**
	 * Returns the direction of block along y axis.
	 * 
	 * @return : y direction of block.
	 */
	public int getYdirection()
	{
		return yDirection;
	}
	
	/**
	 * Checks for block collisions and changes directions accordingly.
	 * 
	 * @param b2 : Rectangle/ChaseBlock object for comparison.
	 */
	public void checkCollision(ChaseBlock b2)
	{
		if ((getX() + blockSize) == b2.getX() && (getY() + blockSize) == b2.getY() && yDirection == down && b2.getYdirection() == up)
		{
			xDirection = left;
			yDirection = up;
			randomColor();
			
			b2.xDirection = right;
			b2.yDirection = down;
			b2.randomColor();
		}
		else if (getX() == (b2.getX() + blockSize) && (getY() + blockSize) == b2.getY() && yDirection == down && b2.getYdirection() == up)
		{
			xDirection = right;
			yDirection = up;
			randomColor();
			
			b2.xDirection = left;
			b2.yDirection = down;
			b2.randomColor();
		}
		else if (getX() == (b2.getX() + blockSize) && getY() == (b2.getY()  + blockSize) && yDirection == up && b2.getYdirection() == down)
		{
			xDirection = right;
			yDirection = down;
			randomColor();
			
			b2.xDirection = left;
			b2.yDirection = up;
			b2.randomColor();
		}
		else if ((getX() + blockSize) == b2.getX() && getY() == (b2.getY() + blockSize) && yDirection == up && b2.getYdirection() == down)
		{
			xDirection = left;
			yDirection = down;
			randomColor();
			
			b2.xDirection = right;
			b2.yDirection = up;
			b2.randomColor();
		}
		else if ((getX() + blockSize) == b2.getX()  && getY() > (b2.getY() - blockSize) && getY() < (b2.getY() + blockSize))
		{
			xDirection = left;
			randomColor();
		}
		else if (getX() == (b2.getX() + blockSize)  && getY() > (b2.getY() - blockSize) && getY() < (b2.getY() + blockSize))
		{
			xDirection = right;
			randomColor();
		}
		else if ((getY() + blockSize) == b2.getY()  && getX() > (b2.getX() - blockSize) && getX() < (b2.getX() + blockSize))
		{
			yDirection = up;
			randomColor();
		}
		else if (getY() == (b2.getY() + blockSize)  && getX() > (b2.getX() - blockSize) && getX() < (b2.getX() + blockSize))
		{
			yDirection = down;
			randomColor();
		}
	}
}
