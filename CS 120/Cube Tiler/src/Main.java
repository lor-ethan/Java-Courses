/**
 * Main class using the Quadrilateral object.
 * 
 * @author Ethan Lor
 */
import java.awt.Color;
import javax.swing.JFrame;

public class Main
{
    private JFrame window;

    /**
     * Simple initiating main(); uses Main object to create a shape.
     * 
     * @param args Not used.
     */
    public static void main( String[] args )
    {
        Main m = new Main();
        m.createWindow();
        m.drawShape();
    }

    /**
     * Creates a window to display graphics.
     */
    private void createWindow()
    {
        window = new JFrame();
        int windowSize = 600;
        window.setSize( windowSize + window.getInsets().left + window.getInsets().right,
                        windowSize + window.getInsets().top + window.getInsets().bottom );
        window.setLocation( 100, 100 );
        window.setLayout( null );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setResizable( false );
        window.setVisible( true );
    }

    /**
     * Draw a shape in the window, using Quadrilateral object.
     */
    private void drawShape()
    {
        Quadrilateral q = new Quadrilateral( 100, 100, 100, 200, 250, 200,
                                             250, 100 );
        q.setBackground( Color.green );
        q.setForeground( Color.red );
        window.add( q );
        window.repaint();
    }
}