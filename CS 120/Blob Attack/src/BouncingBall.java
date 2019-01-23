/**
 * A circular object that can move about, appearing to bounce off the edges of a
 * supplied window.
 * 
 * @author M. Allen, Ethan Lor
 */

@SuppressWarnings( "serial" )
public class BouncingBall extends Oval
{
    public static final int LEFT = -1;
    public static final int RIGHT = +1;
    public static final int UP = -1;
    public static final int DOWN = +1;

    protected int xDelta;
    protected int yDelta = UP;
    private int windowSize;

    /**
     * Creates the basic graphical object, and saves the value for the window's
     * size (assumes a square viewing area). Initially each such object will
     * move in an upwards direction in the window.
     * 
     * @param x x-coordinate of object bounding box.
     * @param y y-coordinate of object bounding box.
     * @param diameter Diameter of circular object.
     * @param windowSize Size of window containing object.
     */
    public BouncingBall( int x, int y, int diameter, int windowSize )
    {
        super( x, y, diameter, diameter );
        this.windowSize = windowSize;
    }

    /**
     * Moves the object in its current direction of travel by a pixel.
     */
    public void move()
    {
        int newX = getX() + xDelta;
        int newY = getY() + yDelta;
        setLocation( newX, newY );
        checkWalls();
    }

    /**
     * Reverses direction appropriately when contacting the edges of the window.
     */
    private void checkWalls()
    {
        if ( xDelta == LEFT && getX() <= 0 )
        {
            xDelta = RIGHT;
        }
        else if ( getX() + getWidth() >= windowSize )
        {
            xDelta = LEFT;
        }

        if ( yDelta == UP && getY() <= 0 )
        {
            yDelta = DOWN;
        }
        else if ( getY() + getHeight() >= windowSize )
        {
            yDelta = UP;
        }
    }
}
