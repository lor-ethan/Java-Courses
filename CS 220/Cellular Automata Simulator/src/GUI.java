// File: GUI.java
// Author: Brad Shutters
// WARNING: Don't modify this file for any reason.

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, ChangeListener {

	private static final String APP_TITLE = "Cellular Automata Simulator";
    private static final Color GRID_COLOR = new Color(230, 230, 230);
    private static final Color CELL_COLOR = Color.black;
    private static final int DEFAULT_GRID_WIDTH = 50;
    private static final int DEFAULT_GRID_HEIGHT = 50;
    
    public static void main(String[] args) {
        GUI frame = new GUI();
        frame.setVisible(true);
    }

    private CAPanel caPanel;
    public JSlider gridHeightSlider, gridWidthSlider;
    private JButton clear, random, step, run;
    private JPanel ctrlPanel;
    java.util.Timer timer;

    public GUI() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        pack();
        setSize(569, 549);
        this.setTitle(APP_TITLE);

        caPanel = new CAPanel(DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
        caPanel.setBorder(BorderFactory.createEtchedBorder());
        this.getContentPane().add(caPanel, BorderLayout.CENTER);

        gridWidthSlider = new JSlider(JSlider.HORIZONTAL, 5, 100, caPanel.getGridWidth());
        gridWidthSlider.setMajorTickSpacing(5);
        gridWidthSlider.setMinorTickSpacing(1);
        gridWidthSlider.setPaintTicks(true);
        gridWidthSlider.setPaintLabels(true);
        gridWidthSlider.addChangeListener(this);
        this.add(gridWidthSlider, BorderLayout.NORTH);
        
        gridHeightSlider = new JSlider(JSlider.VERTICAL, 5, 100, caPanel.getGridHeight());
        gridHeightSlider.setMajorTickSpacing(5);
        gridHeightSlider.setMinorTickSpacing(1);
        gridHeightSlider.setPaintTicks(true);
        gridHeightSlider.setPaintLabels(true);
        gridHeightSlider.addChangeListener(this);
        this.add(gridHeightSlider, BorderLayout.WEST);
        
        ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        clear = new JButton("Clear");
        clear.addActionListener(this);
        ctrlPanel.add(clear);

        random = new JButton("Random");
        random.addActionListener(this);
        ctrlPanel.add(random);

        step = new JButton("Step");
        step.addActionListener(this);
        ctrlPanel.add(step);

        run = new JButton("Run");
        run.addActionListener(this);
        ctrlPanel.add(run);

        this.add(ctrlPanel, BorderLayout.SOUTH);

        // add some padding to the east
        this.add(new JPanel(), BorderLayout.EAST);

    }

    private void run() {
        timer = new java.util.Timer();
        caPanel.setInteractive(false);
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        caPanel.step();
                    }
                }, 0, 200);
        this.run.setText("Pause");
        this.gridHeightSlider.setEnabled(false);
        this.gridWidthSlider.setEnabled(false);
        this.clear.setEnabled(false);
        this.random.setEnabled(false);
        this.step.setEnabled(false);
    }

    private void pause() {
        if(timer!=null) {
            timer.cancel();
        }
        this.run.setText("Run");
        this.gridHeightSlider.setEnabled(true);
        this.gridWidthSlider.setEnabled(true);
        this.clear.setEnabled(true);
        this.random.setEnabled(true);
        this.step.setEnabled(true);
        this.caPanel.setInteractive(true);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // implement the ActionListener interface.
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.clear) {
            caPanel.clear();
        } else if(e.getSource()==this.random) {
            caPanel.random();
        } else if(e.getSource()==this.step) {
            caPanel.step();
        } else if(e.getSource()==this.run) {
            if(this.run.getText().equals("Run")) {
                run();
            } else {
                pause();
            }
        }
        this.repaint();
    }
    // done implementing the ActionListener interface.
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // implement the ChangeListener interface.
    public void stateChanged(ChangeEvent e) {
        if(e.getSource()==this.gridHeightSlider) {
            this.caPanel.setGridHeight(this.gridHeightSlider.getValue());
        } else if (e.getSource()==this.gridWidthSlider) {
        	this.caPanel.setGridWidth(this.gridWidthSlider.getValue());
        }
    }
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // CAPanel
    class CAPanel extends JPanel implements MouseListener {

        CAModel model;

        public CAPanel(int gridHeight, int gridWidth){
            model = new CAModel(gridHeight, gridWidth);
            setInteractive(true);
        }

        public void paintComponent(Graphics g) {

            int height = model.getGridHeight();
            int width = model.getGridWidth();
            
            double yGridSize = getHeight() / (double)height;
            double xGridSize = getWidth() / (double)width;
            
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(GRID_COLOR);
            for (int i = 0; i <= height; i++) {
                g.drawLine(0, (int) (i * yGridSize), (int) (xGridSize * width), (int) (i * yGridSize));
            }
            for (int i = 0; i <= width; i++) {
                g.drawLine((int) (i * xGridSize), 0, (int) (i * xGridSize), (int) (yGridSize * height));
            }

            g.setColor(CELL_COLOR);
            for (int row = 0; row < height; row++) { // rows
                for (int col = 0; col < width; col++) {  // cols
                    if (model.isPopulated(row,  col)) {
                        g.fill3DRect((int) (col * xGridSize + 2), (int) (row * yGridSize + 2), (int) (xGridSize - 2), (int) (yGridSize - 2), true);
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////
        // grid size
        public int getGridHeight() { return model.getGridHeight(); }
        public int getGridWidth() { return model.getGridWidth(); }

        public void setGridHeight(int height) {
            model.setGridHeight(height);
            repaint();
        }
        
        public void setGridWidth(int width) {
            model.setGridWidth(width);
            repaint();
        }
        ///////////////////////////////////////////////////////////////////////////


        ///////////////////////////////////////////////////////////////////////////
        // button operations
        
        public void clear() {
            this.model.clear();
            repaint();
        }

        public void random() {
            this.model.random();
            repaint();
        }

        public void step() {
            this.model.step();
            repaint();
        }

        // flag=true allows grid to respond to mouse
        // flag=false grid does not respond to mouse
        public void setInteractive(boolean flag) {
            if(flag) {
                this.addMouseListener(this);
            } else {
                this.removeMouseListener(this);
            }
        }
        
        // done with button operations
        ///////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////
        // implement the MouseListenerInterface
        
        // invert cell when mouse clicked on it
        public void mouseClicked(MouseEvent e) {
            int width = model.getGridWidth();
            int height = model.getGridHeight();
            int col = e.getX() * width / getWidth();
            int row = e.getY() * height / getHeight();
            model.invert(row, col);
            repaint();
        }

        // unused methods
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        ///////////////////////////////////////////////////////////////////////////
    }
}




