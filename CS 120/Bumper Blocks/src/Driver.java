/**
 * Creates an array of ChaseBlock objects, then animates them.
 *
 * @author Ethan Lor
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Driver implements ActionListener
{
    private JFrame window;
    private ChaseBlock[] blockArray = new ChaseBlock[15];
    private Timer timer;
    
    // constants for graphics
    private final int windowSize = 500;
    private final int blockSize = 20;
    
    /**
     * Simple initiating main().
     *
     * @param args Not used.
     */
    public static void main( String[] args )
    {
        Driver d = new Driver();
        d.createWindow();
    }
    
    /**
     * Set up the basic graphical objects.
     */
    private void createWindow()
    {
        // create the window
        window = new JFrame( "Bump Around" );
        window.setVisible( true );
        window.setLayout( null );
        window.getContentPane().setBackground( Color.white );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setLocation( 50, 50 );
        window.setSize(
                       windowSize + window.getInsets().left + window.getInsets().right,
                       windowSize + window.getInsets().top + window.getInsets().bottom );
        window.setResizable( false );
        addBlocks();
        window.repaint();
        
        timer = new Timer(10, this);
        timer.start();
    }
    
    /**
     * Add blocks to window.
     */
    private void addBlocks()
    {
    	int x = 0;
    	int y = 0;
    	for (int i = 0; i < blockArray.length; i++)
    	{
    		x = (i * blockSize + 12 * (1 + i));
    		y = ((windowSize - blockSize) / 2);
    		blockArray[i] = new ChaseBlock(x, y, blockSize, blockSize, window);
    	}

    	for (int i = 0; i < blockArray.length; i++)
    	{
    		window.add(blockArray[i]);
    	}
    }
    
    /**
     * Draws blocks in their locations.
     */
    private void animate()
    {
    	for (int i = 0; i < blockArray.length; i++)
    	{
    		blockArray[i].move();
    	}
    	
    	for (int b1 = 0; b1 < blockArray.length; b1++)
    	{
    		for (int b2 = 0; b2 < blockArray.length; b2++)
    		{
    			if (b1 != b2)
    			{
    				blockArray[b1].checkCollision(blockArray[b2]);
    			}
    		}
    	}
    }

    /**
     * Listens to timer and calls animate method.
     */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		animate();
	}
}
