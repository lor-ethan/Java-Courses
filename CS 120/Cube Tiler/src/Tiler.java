/**
 * Tiles a window using Quadrilateral objects, in a repeated pattern.
 * Creates two program instances, with two separate patterns, in
 * different sizes and colors.
 *
 * @author M. Allen
 * @author Ethan Lor
 */
import java.awt.Color;
import javax.swing.JFrame;

public class Tiler
{
    private JFrame window;
    private int windowSize;

    /**
     * Creates two distinct Tiler objects, each of which produces a tiled
     * pattern made of various Quadrilateral objects, each in its own window.
     * 
     * @param args Not used.
     */
    public static void main( String[] args )
    {
        Tiler tiler1 = new Tiler();
        Tiler tiler2 = new Tiler();
        tiler1.createWindow( 50, 50, 500 );
        tiler2.createWindow( 600, 50, 500 );
        
        tiler1.tileWindow( 100, Color.red, Color.green, Color.yellow );
        tiler2.tileWindow( 20, Color.cyan, Color.magenta, Color.white );
    }

    /**
     * Creates a window for displaying tiled objects.
     * 
     * @param x x-coordinate of window on-screen.
     * @param y y-coordinate of window on-screen.
     * @param size Side-length of the window (a square).
     */
    private void createWindow( int x, int y, int size )
    {
        window = new JFrame();
        windowSize = size;
        window.setSize( windowSize + window.getInsets().left + window.getInsets().right,
                        windowSize + window.getInsets().top + window.getInsets().bottom );
        window.setLocation( x, y );
        window.setLayout( null );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setResizable( false );
        window.setVisible( true );

    }

    /**
     * Tiles the window with a repeated pattern of "cubes", where each cube is
     * made of 3 separate Quadrilateral objects.
     * 
     * @param cubeSize The side-lenth of the cube.
     * @param c1 Color of the right face of cube (from front).
     * @param c2 Color of the left face of cube (from front).
     * @param c3 Color of the "top" face of cube (or bottom, depending on how
     *            you look at it).
     */
    private void tileWindow( int cubeSize, Color c1, Color c2, Color c3 )
    {
        int counter = 0;
        for ( int y = 0; y < windowSize; y++ )
        {
            if ( counter % 2 == 0 )
            {
                for ( int x = 0; x < windowSize; x++ )
                {
                    drawDownCube( x, y, cubeSize, c1, c2, c3 );
                    x += 2 * cubeSize;
                }
            }
            else
            {
                for ( int x = -cubeSize; x < windowSize; x++ )
                {
                    drawDownCube( x, y, cubeSize, c1, c2, c3 );
                    x += 2 * cubeSize;
                }
            }
            y += cubeSize + cubeSize / 3;
            counter++ ;
        }
        window.repaint();
    }

    /**
     * Creates a single "cube", made from 3 Quadrilateral objects.
     * 
     * @param x x-coordiante of upper-left corner of left face (from front).
     * @param y y-coordiante of upper-left corner of left face (from front).
     * @param size The side-lenth of the cube.
     * @param c1 Color of the right face of cube (from front).
     * @param c2 Color of the left face of cube (from front).
     * @param c3 Color of the "top" face of cube (or bottom, depending on how
     *            you look at it).
     */
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
        window.add( q );

        int x5 = x3 + size;
        int y5 = y3 + size / 3;

        int x6 = x5;
        int y6 = y5 - size;

        Quadrilateral q2 = new Quadrilateral( x4, y4, x3, y3, x5, y5, x6, y6 );
        q2.setBackground( c2 );
        window.add( q2 );

        Quadrilateral q3 = new Quadrilateral( x2, y2, x3, y2 + size / 3, x5,
                                              y5, x3, y3 );
        q3.setBackground( c3 );
        window.add( q3 );

    }
}