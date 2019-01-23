/**
 * An extension to the BouncingBall class that adds in the ability to change
 * direction or color at random, and to absorb (or be absorbed by) other such
 * objects when colliding with them.
 * 
 * @author Ethan Lor
 */
import java.awt.Color;

@SuppressWarnings( "serial" )
public class Blob extends BouncingBall
{
    /**
     * Creates the basic graphical object, by calling parent class constructor.
     * 
     * @param x x-coordinate of object bounding box.
     * @param y y-coordinate of object bounding box.
     * @param diameter Diameter of circular object.
     * @param windowSize Size of window containing object.
     */
    public Blob( int x, int y, int diameter, int windowSize )
    {
        super( x, y, diameter, windowSize );
    }

    /**
     * Sets color of Blob to some randomly generated color; colors are generated
     * uniformly across entire RGB space.
     */
    public void setRandomColor()
    {
        int red = (int) ( Math.random() * 256 );
        int green = (int) ( Math.random() * 256 );
        int blue = (int) ( Math.random() * 256 );

        setBackground( new Color( red, green, blue ) );
    }

    /**
     * Changes direction at random, uniformly selecting from any of the 4
     * possible diagonal directions of travel.
     */
    public void setRandomDirection()
    {
        xDelta = LEFT;
        if ( Math.random() < 0.5 )
        {
            xDelta = RIGHT;
        }
        yDelta = UP;
        if ( Math.random() < 0.5 )
        {
            yDelta = DOWN;
        }
    }

    /**
     * Checks whether this Blob has collided with another; collisions are
     * generated only when the circular areas of the Blobs intersect. If this
     * happens, then the largest of the two Blob objects absorbs the other one,
     * gaining size and changing color and direction again at random. If the two
     * Blob objects have the same size, then a coin-flip determines which
     * swallows the other.
     * 
     * Note: only Blobs with non-0 size are checked; a 0-size Blob is one that
     * has been already swallowed up by another, and disappears.
     * 
     * @param other Another Blob to be checked for collision with this Blob.
     */
    public void checkCollision( Blob other )
    {
        if ( getWidth() > 0 && other.getWidth() > 0 && circlesTouch( other ) )
        {
            collide( other );
        }
    }

    /**
     * Utility to determine if the circular areas of two Blob objects intersect.
     * 
     * @param other Another Blob to be checked for collision with this Blob.
     * 
     * @return true if and only if the circular areas of this Blob and other
     *         intersect.
     */
    private boolean circlesTouch( Blob other )
    {
        int centerX = getX() + getWidth() / 2;
        int centerY = getY() + getHeight() / 2;
        int otherCenterX = other.getX() + other.getWidth() / 2;
        int otherCenterY = other.getY() + other.getHeight() / 2;

        double xSquared = Math.pow( centerX - otherCenterX, 2 );
        double ySquared = Math.pow( centerY - otherCenterY, 2 );
        double distance = Math.sqrt( xSquared + ySquared );
        double radii = ( getWidth() + other.getWidth() ) / 2.0;

        return distance <= radii;
    }

    /**
     * Utility that determines which Blob is largest; the larger of the two
     * objects will absorb the other. If they are of the same size, then a
     * coin-flip determines which swallows up the other.
     * 
     * @param other Another Blob that has collide with this Blob.
     */
    private void collide( Blob other )
    {
        if ( getWidth() > other.getWidth() || ( getWidth() == other.getWidth() && Math.random() < 0.5 ) )
        {
            swallow( this, other );
        }
        else
        {
            swallow( other, this );
        }
    }

    /**
     * Computes new sizes for two Blob objects, one of which swallows up the
     * other.
     * 
     * @param one The Blob that swallows the other; the size of one will
     *            increase by the size of other, and the color/direction of one
     *            will change randomly.
     * @param other The Blob that is swallowed up by one; the size of other is
     *            set to 0, so that it effectively disappears.
     */
    private void swallow( Blob one, Blob other )
    {
        one.setSize( other.getWidth() + one.getWidth(), other.getHeight() + one.getHeight() );
        other.setSize( 0, 0 );
        one.setRandomColor();
        one.setRandomDirection();
    }
}