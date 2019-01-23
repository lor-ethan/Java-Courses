/**
 * A simple JFrame-using class to create a window
 * to display graphical JComponent objects.
 *
 * @author M. Allen
 */

import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.Color;

public class Window
{
    private JFrame window;
    
    /**
     * post: JFrame created with title == "Window"
     * and x = 50 and y == 50
     * and width == 200 and height == 200
     * and layout == null
     * and background == Color.white
     * and visible == true
     * and resizable == false
     */
    public Window()
    {
        window = new JFrame( "Window" );
        window.setLocation( 50, 50 );
        window.setSize( 200, 200 );
        window.setLayout( null );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.getContentPane().setBackground( Color.white );
        window.setResizable( false );
        window.setVisible( true );
    }
    
    /**
     * post: width == w and height = h
     */
    public void setSize( int w, int h )
    {
        window.setSize( w + window.getInsets().left +
                       window.getInsets().right,
                       h + window.getInsets().top + window.getInsets().bottom );
    }
    
    /**
     * post: x-coordinate == x and y-coordinate = y
     */
    public void setLocation( int x, int y )
    {
    	window.setLocation( x, y );
    }
    
    /**
     * post: window title == title
     */
    public void setTitle( String title )
    {
        window.setTitle( title );
    }
    
    /**
     * post: window background == col
     */
    public void setBackground( Color col )
    {
        window.getContentPane().setBackground( col );
        window.repaint();
    }
    
    /**
     * post: adds input any graphical JComponent, like a Triangle
     * or Oval, to JFrame
     */
    public void add( JComponent component )
    {
        window.add( component, 0 );
        component.repaint();
    }
    
    /**
     * post: request for window.repaint() to update graphics
     */
    public void repaint()
    {
        window.repaint();
    }
}
