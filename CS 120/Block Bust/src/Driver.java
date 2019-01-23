
/**
 * The main Driver class. Creates a window for the block-buster game, adds the
 * blocks, and then regularly loops to update the game board.
 *
 * @author M. Allen
 * @author Ethan Lor
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Driver implements ActionListener {
	private JFrame window;
	private JButton reset;
	private Timer mainTimer, newBlockTimer;
	private Label scoreBox;
	private int score, numBlack;
	private boolean playing = true;

	// size of Block objects
	private final int blockSize = 20;
	private final int rows = 15;
	private final int columns = 20;

	/**
	 * Simple initiating main. Constructs Driver and initiates program.
	 *
	 * @param args
	 *            Not used.
	 */
	public static void main(String[] args) {
		Driver d = new Driver();
		d.makeWindow();
	}

	/**
	 * Set up the graphical elements.
	 */
	private void makeWindow() {
		// create the window
		window = new JFrame("Can't Bust 'Em!");
		window.setBounds(50, 50, blockSize * columns, rows * blockSize + 100);
		window.setVisible(true);
		window.setResizable(false);
		window.setLayout(null);
		window.getContentPane().setBackground(Color.black);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// add Label for score
		Label scoreText = new Label();
		scoreText.setBounds(20, rows * blockSize + 20, 50, 30);
		scoreText.setBackground(Color.black);
		scoreText.setForeground(Color.yellow);
		scoreText.setText("Score: ");
		window.add(scoreText);
		scoreBox = new Label();
		scoreBox.setBounds(scoreText.getX() + scoreText.getWidth(), scoreText.getY(), window.getWidth() - 200, 30);
		scoreBox.setBackground(Color.black);
		scoreBox.setForeground(Color.yellow);
		scoreBox.setText("0");
		window.add(scoreBox);

		// add reset button
		reset = new JButton("Reset");
		reset.setBounds(scoreBox.getX() + scoreBox.getWidth() + 20, scoreBox.getY(), 100, 30);
		reset.addActionListener(this);
		window.add(reset);

		// add in the blocks
		addBlocks();
		numBlack = countBlackBlocks();
		window.repaint();

		// add mainTimer and start it up
		mainTimer = new Timer(80, this);
		mainTimer.start();
		// add second mainTimer for extra blocks
		newBlockTimer = new Timer(5000, this);
		newBlockTimer.start();
	}

	/**
	 * Add a new row of blocks at the top of the window. Called periodically to
	 * keep the game running.
	 */
	private void addNewRow() {

		for (int x = 0; x < columns; x++) {
			Block b = getBlock(x * blockSize, 0);
			if (!b.getBackground().equals(Color.black)) {
				loseGame();
			}
			b.setRandomColor();
		}

		numBlack = countBlackBlocks();
	}

	/**
	 * Counts number of black blocks currently on-screen.
	 *
	 * @return Number of black blocks on-screen.
	 */
	private int countBlackBlocks() {
		int count = 0;

		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				Block b = getBlock((x * blockSize), (y * blockSize));
				Color c = b.getBackground();

				if (c.equals(Color.black)) {
					++count;
				}
			}
		}
		return count;
	}

	/**
	 * Counts all current black blocks, computes the difference, and finds the correct points to assign.
	 * Points are assigned in 5 points increments for each additional block clicked.
	 * Score is printed out to score box once calculations are done.
	 */
	public void increaseScore() {
		int change = (countBlackBlocks() - numBlack);
		int pointIncrement = (0);
		int totalPoints = (0);

		for (int i = 0; i < change; i++) {
			pointIncrement = (pointIncrement + 5);
			totalPoints = (totalPoints + pointIncrement);
		}
		score = score + totalPoints;													
		scoreBox.setText("" + score);
		numBlack = countBlackBlocks();
		checkDone();
	}

	/**
	 * Drops colored blocks by looking at blocks from the bottom up and if colored black
	 * will switch the color with the block above it.
	 */
	private void dropRows() {
		for (int x = 0; x < columns; x++) {
			for (int y = rows - 1; y > 0; y--) {
				Block bottom = getBlock((x * blockSize), (y * blockSize));
				Color below = bottom.getBackground();

				Block top = getBlock((x * blockSize), ((y - 1) * blockSize));
				Color above = top.getBackground();

				if (below.equals(Color.black)) {
					top.setBackground(below);
					bottom.setBackground(above);
				}
			}
		}

	}

	/**
	 * Redraws the window, clears score board, and restart timer when reset button clicked.
	 */
	private void resetGame() {
		makeWindow();
		score = 0;
		newBlockTimer.start();
		playing = true;
		window.repaint();
		
		
	}

	/**
	 * Add blocks to the window, to initiate the game.
	 */
	private void addBlocks() {
		for (int x = 0; x < columns; x++)
			for (int y = 0; y < rows; y++) {
				Block b = new Block((x * blockSize), (y * blockSize), blockSize, this);

				if (y >= 5)
					b.setRandomColor();

				window.add(b);
			}
	}

	/**
	 * Stops the game with a winning message if user gets 2500 points.
	 */
	private void checkDone() {
		if (score >= 2500) {
			newBlockTimer.stop();
			playing = false;
			scoreBox.setText("You Win!  Score: " + score);
			window.repaint();
		}
	}

	/**
	 * Stops the game with a losing message if the blocks ever get too high.
	 */
	private void loseGame() {
		newBlockTimer.stop();
		playing = false;
		scoreBox.setText("You Lose! Final Score: " + score);
		window.repaint();
	}

	/**
	 * Responds to a signal from a button, mainTimer, etc.
	 *
	 * @param actor
	 *            The ActionListener (like an ActionButton or ActionTimer) that
	 *            is sending a signal to the Driver.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == reset) {
			resetGame();
		} else if (e.getSource() == mainTimer) {
			dropRows();
			checkDone();
		} else if (e.getSource() == newBlockTimer) {
			addNewRow();
		}
	}

	/**
	 * @return true if the game is currently playing, false otherwise.
	 */
	public boolean isPlaying() {
		return playing;
	}

	/**
	 * Returns the block at the given location (if any exists).
	 * 
	 * @param x
	 *            x-coordinate of Block.
	 * @param y
	 *            y-coordinate of Block.
	 * @return The Block at the location (x, y) (or null if none exists).
	 */
	public Block getBlock(int x, int y) {
		if ((x >= 0) && (x < columns * blockSize) && (y >= 0) && (y < rows * blockSize))
			return (Block) (window.getContentPane().getComponentAt(x, y));
		else
			return null;
	}
}