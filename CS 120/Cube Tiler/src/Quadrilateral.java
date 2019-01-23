/**
 * Draws a graphical 4-sided polygon.
 *
 * @author M. Allen
 * @author Ethan Lor
 */
import javax.swing.*;
import java.awt.*;

@SuppressWarnings( "serial" )
public class Quadrilateral extends JComponent
{
    private final int[] xs;
    private final int[] ys;

    /**
     * Draws a 4-sided closed figure between (x,y) points given,
     * with fill color and line color both Color.black.
     * 
     * @param x1 x-coordinate of first point in shape.
     * @param y1 y-coordinate of first point in shape.
     * @param x2 x-coordinate of second point in shape.
     * @param y2 y-coordinate of second point in shape.
     * @param x3 x-coordinate of third point in shape.
     * @param y3 y-coordinate of third point in shape.
     * @param x4 x-coordinate of fourth point in shape.
     * @param y4 y-coordinate of fourth point in shape.
     */
    public Quadrilateral( int x1, int y1, int x2, int y2, int x3, int y3,
                          int x4, int y4 )
    {
        super();
        int[] inxs = { x1, x2, x3, x4 };
        int[] inys = { y1, y2, y3, y4 };
        Polygon p = new Polygon( inxs, inys, 4 );
        setBounds( p.getBounds() );
        setSize( getWidth() + 1, getHeight() + 1 );
        for ( int i = 0; i < inxs.length; i++ )
        {
            inxs[i] -= getX();
        }
        for ( int i = 0; i < inys.length; i++ )
        {
            inys[i] -= getY();
        }
        setBackground( Color.black );
        setForeground( Color.black );
        xs = inxs;
        ys = inys;
    }

    /**
     * Graphic content of Quadrilateral drawn on-screen with fill
     * color == getBackground() and line color == getForeground().
     */
    public void paint( Graphics g )
    {
        super.paint( g );
        g.setColor( getBackground() );
        g.fillPolygon( xs, ys, 4 );
        g.setColor( getForeground() );
        g.drawPolygon( xs, ys, 4 );
    }
}