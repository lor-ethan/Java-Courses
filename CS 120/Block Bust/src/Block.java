
/**
 * Graphical block that can respond to mouse clicks by turning black
 * (and turning neighboring blocks black as well).
 *
 * @author M. Allen
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;

@SuppressWarnings( "serial" )
public class Block extends JComponent implements MouseListener
{
    private Driver driver;
    
    /**
     * Constructor for objects of class Block
     */
    public Block( int x, int y, int size, Driver d )
    {
        super();
        setBounds( x, y, size, size );
        setBackground( Color.black );
        addMouseListener( this );
        driver = d;
    }
    
    // helper method to get a random color (R/G/B/Y)
    public void setRandomColor()
    {
        int c = (int) ( Math.random() * 4 );
        if ( c == 0 )
        {
            setBackground( Color.red );
        }
        else if ( c == 1 )
        {
            setBackground( Color.green );
        }
        else if ( c == 2 )
        {
            setBackground( Color.blue );
        }
        else
        {
            setBackground( Color.yellow );
        }
    }
    
    // turns object and all connected objects black
    private void turnConnectedBlack( Color c )
    {
        setBackground( Color.black );
        
        // look at blocks in four adjacent neighboring cells:
        // [1] to the left
        Block b = driver.getBlock( getX() - getWidth(), getY() );
        if ( b != null && b.getBackground() == c )
            b.turnConnectedBlack( c );
        
        // [2] to the right
        b = driver.getBlock( getX() + getWidth(), getY() );
        if ( b != null && b.getBackground() == c )
            b.turnConnectedBlack( c );
        
        // [3] above
        b = driver.getBlock( getX(), getY() - getHeight() );
        if ( b != null && b.getBackground() == c )
            b.turnConnectedBlack( c );
        
        // [4] below
        b = driver.getBlock( getX(), getY() + getHeight() );
        if ( b != null && b.getBackground() == c )
            b.turnConnectedBlack( c );
    }
    
    // responds to mouse click by turning black as needed
    public void mouseClicked( MouseEvent e )
    {
        Color c = getBackground();
        if ( driver.isPlaying() && c != Color.black )
        {
            turnConnectedBlack( c );
            driver.increaseScore();
        }
    }
    
    // not used; only needed for MouseListener interface
    public void mousePressed( MouseEvent e )
    {
    }
    
    // not used; only needed for MouseListener interface
    public void mouseReleased( MouseEvent e )
    {
    }
    
    // not used; only needed for MouseListener interface
    public void mouseEntered( MouseEvent e )
    {
    }
    
    // not used; only needed for MouseListener interface
    public void mouseExited( MouseEvent e )
    {
    }
    
    /**
     * post: Draws a filled Rectangle
     * and the upper left corner is ( getX(), getY() )
     * and the rectangle's dimensions are getWidth() and getHeight()
     * and the rectangle's color is getBackground()
     */
    public void paint( Graphics g )
    {
        g.setColor( getBackground() );
        g.fillRect( 0, 0, getWidth() - 1, getHeight() - 1 );
        paintChildren( g );
    }
}
