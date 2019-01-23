/**
 * Author: M. Allen
 *
 * Code for ball-bouncing game.
 */
import java.awt.Color;
import javax.swing.JLabel;

public class Driver
{
    private GameWindow window;
    private JLabel bounces, speed;
    private Ball ball;
    private Bat blok;
    
    /**
     * post : Driver constructor run and game set-up performed
     */
    public static void main( String[] args )
    {
        Driver d = new Driver();
        d.makeGame();
    }
    
    /**
     * post: constructor sets up window and creates all needed objects
     */
    private void makeGame()
    {
        // create window with animating timer and keyboard input response
        window = new GameWindow( this );
        window.setTitle( "Bounce, bounce, bounce..." );
        window.setLocation( 50, 50 );
        window.setSize( 500, 500 );
        window.setBackground( Color.white );
        
        // add ball and bat to window
        ball = new Ball( window.getWidth() / 2 - 10,
                        window.getHeight() / 2 - 10, window );
        blok = new Bat( window.getWidth() / 2 - 50, window.getHeight() - 50, window );
        
        // add labels for speed and number of bounces
        bounces = new JLabel( "Bounces: " + ball.getBounces() );
        bounces.setBounds( 10, 10, 150, 20 );
        window.add( bounces );
        speed = new JLabel( "Speed: " + ball.getSpeed() );
        speed.setBounds( 10, 30, 150, 20 );
        window.add( speed );
        
        window.startTimer();
        window.setDefaultCloseOperation(GameWindow.EXIT_ON_CLOSE);
    }
    
    /**
     * @param action : a String describing the action taken post: the method
     *            corresponding to the appropriate action will be taken
     */
    public void handleAction( String action )
    {
        if ( action.equals( GameWindow.MOVE ) )
        {
            ball.move();
            blok.move();
            ball.checkBatHit( blok );
            bounces.setText( "Bounces: " + ball.getBounces() );
            speed.setText( "Speed: " + ball.getSpeed() );
        }
        else if ( action.equals( "LEFT" ) )
        	blok.setDirection( "LEFT" );
        else if ( action.equals( "RIGHT" ) )
            blok.setDirection( "RIGHT" );
        else if ( action.equals( "STOP" ) )
            blok.setDirection( "STOP" );
    }
}
