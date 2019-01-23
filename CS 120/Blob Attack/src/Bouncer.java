/**
 * Creates an array of Blob objects, then animates them.
 *
 * @author M. Allen, Ethan Lor
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Bouncer implements ActionListener, MouseListener
{
    private JFrame window;
    private Timer timer;
    private Blob[] blobs = new Blob[12];

    // constants for graphics
    private final int windowSize = 500;
    private final int ballSize = 20;

    /**
     * Simple initiating main().
     *
     * @param args Not used.
     */
    public static void main( String[] args )
    {
        Bouncer bouncer = new Bouncer();
        bouncer.createWindow();
    }

    /**
     * Set up the basic graphical objects.
     */
    private void createWindow()
    {
        window = new JFrame( "The Great Chase" );
        window.setVisible( true );
        window.setLayout( null );
        window.getContentPane().setBackground( Color.white );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setLocation( 50, 50 );
        window.setSize(
                        windowSize + window.getInsets().left + window.getInsets().right,
                        windowSize + window.getInsets().top + window.getInsets().bottom );
        window.setResizable( false );

        // Add Blob objects to window.
        addBlobs();
        window.repaint();

        timer = new Timer( 10, this );
        timer.start();
    }

    /**
     * Responds to events sent by Timer. On each step, moves each of the Blobs,
     * and then checks whether any of them collide with one another.
     */
    @Override
    public void actionPerformed( ActionEvent e )
    {
        for ( int i = 0; i < blobs.length; i++ )
        {
            blobs[i].move();
        }

        for ( int i = 0; i < blobs.length - 1; i++ )
        {
            for ( int j = i + 1; j < blobs.length; j++ )
            {
                blobs[i].checkCollision( blobs[j] );
            }
        }
    }

    /**
     * Add all the Blobs to the window (and to an array).
     */
    private void addBlobs()
    {
        for ( int i = 0; i < blobs.length; i++ )
        {
            int x = ballSize + ( 2 * ballSize * i );

            blobs[i] = new Blob( x, windowSize / 2 - ballSize / 2,
                                 ballSize, windowSize );
            blobs[i].setRandomColor();
            window.add( blobs[i] );
            blobs[i].addMouseListener( this );
        }
    }

    /**
     * On any occurrence of a mouse entering a Blob, will set it moving in a
     * random direction.
     * 
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered( MouseEvent e )
    {
        for ( int i = 0; i < blobs.length; i++ )
        {
            if ( e.getSource() == blobs[i] )
            {
                blobs[i].setRandomDirection();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked( MouseEvent e )
    {
        // DOES NOTHING.
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed( MouseEvent e )
    {
        // DOES NOTHING.
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased( MouseEvent e )
    {
        // DOES NOTHING.
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited( MouseEvent e )
    {
        // DOES NOTHING.
    }

}
