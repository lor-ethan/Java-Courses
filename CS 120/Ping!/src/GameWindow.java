/**
 * Author: M. Allen
 *
 * A simple JFrame extension to create a window to display objects; the window
 * responds to basic key-clicks for 4-directional movement, and produces
 * recurring event calls for animation purposes.
 */
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.*;

@SuppressWarnings( "serial" )
public class GameWindow extends JFrame implements ActionListener, KeyListener
{
    private Driver driver;
    private Timer timer;
    
    // control constants
    public final static String MOVE = "MOVE";
    public final static String UP = "UP";
    public final static String DOWN = "DOWN";
    public final static String RIGHT = "RIGHT";
    public final static String LEFT = "LEFT";
    public final static String STOP = "STOP";
    
    /**
     * post: JFrame created with ( getTitle() == "" ) && ( getX() == 0 )
     * && ( getY() == 50 ) && ( getWidth() == 100 ) && ( getHeight() == 100 ) &&
     * ( getLayout() == null ) && isVisible() && !isResizable()
     *
     * @param d Driver object to communicate with when keyboard and
     *            animation-timer actions occur.
     */
    public GameWindow( Driver d )
    {
        super( "" );
        setLocation( 0, 0 );
        setSize( 100, 100 );
        setLayout( null );
        setResizable( false );
        setVisible( true );
        
        addKeyListener( this );
        driver = d;
        timer = new Timer( 5, this );
    }
    
    /**
     * Starts the timer for the program.
     */
     public void startTimer()
     {
     	timer.start();
     }
     
     /**
      * Stops the timer for the program.
      */
      public void stopTime()
      {
      	timer.stop();
      }
    
    /**
     * @return The y-coordinate of the bottom of the window display area.
     *         NOTE: may be less than getHeight(), due to window insets.
     */
    public int getBottomEdge()
    {
        return getHeight() - getInsets().bottom - getInsets().top;
    }
    
    @Override
    public void setBackground( java.awt.Color c )
    {
        getContentPane().setBackground( c );
    }
    
    @Override
    public void setSize( int w, int h )
    {
        super.setSize( w, h );
        getContentPane().setSize( w + getInsets().left + getInsets().right,
                                 h + getInsets().top + getInsets().bottom );
    }
    
    @Override
    public void actionPerformed( ActionEvent e )
    {
        if ( e.getSource() == timer )
            driver.handleAction( MOVE );
            
        requestFocus();
    }
    
    @Override
    public void keyPressed( KeyEvent k )
    {
        int key = k.getKeyCode();
        
        // 'I' == UP; 'J' == LEFT; 'K' == RIGHT; 'M' == DOWN
        if ( key == KeyEvent.VK_I )
            driver.handleAction( UP );
        else if ( key == KeyEvent.VK_J )
            driver.handleAction( LEFT );
        else if ( key == KeyEvent.VK_K )
            driver.handleAction( RIGHT );
        else if ( key == KeyEvent.VK_M )
            driver.handleAction( DOWN );
        else if ( key == KeyEvent.VK_S )
            driver.handleAction( STOP );
            
        requestFocus();
    }
    
    @Override
    public void keyReleased( KeyEvent k )
    {
        // DOES NOTHING
    }
    
    @Override
    public void keyTyped( KeyEvent k )
    {
        // DOES NOTHING
    }
}
