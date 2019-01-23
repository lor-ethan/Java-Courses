/**
 * Test that creates three Quadrilateral objects, in a set pattern.
 * Creates one program instances, with each Quadrilateral of a different color. 
 *
 * @author Ethan Lor
 */

import java.awt.Color;
import javax.swing.JFrame;

public class Test
{
	private JFrame win;
    
    private void run()
    {
        win = new JFrame();
        int windowSize = 500;
        win.setSize( windowSize + win.getInsets().left + win.getInsets().right,
                        windowSize + win.getInsets().top + win.getInsets().bottom );
        win.setLocation( 20, 20 );
        win.setLayout( null );
        win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        win.setResizable( false );
        win.setVisible( true );
        
		drawDownCube( 100, 100, 100, Color.white, Color.green, Color.lightGray );
        win.repaint();
    }
    
    public static void main( String[] args )
    {
        Test t = new Test();
        t.run();
    }
    
    private void drawDownCube( int x, int y, int size, Color c1, Color c2, Color c3 )
    {
        int x2 = x;
        int y2 = y + size;
        
        int x3 = x2 + size;
        int y3 = y2 - size / 3;
        
        int x4 = x3;
        int y4 = y3 - size;
        
        Quadrilateral q = new Quadrilateral( x, y, x2, y2, x3, y3, x4, y4 );
        q.setBackground( c1 );
        win.add( q );
        
        int x5 = x3 + size;
        int y5 = y3 + size / 3;
        
        int x6 = x5;
        int y6 = y5 - size;
        
        Quadrilateral q2 = new Quadrilateral( x4, y4, x3, y3, x5, y5, x6, y6 );
        q2.setBackground( c2 );
        win.add( q2 );
        
        Quadrilateral q3 = new Quadrilateral( x2, y2, x3, y2 + size / 3, x5,
                                             y5, x3, y3 );
        q3.setBackground( c3 );
        win.add( q3 );
        
    }
}