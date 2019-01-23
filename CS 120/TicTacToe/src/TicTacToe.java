import java.util.Scanner;


/**
 * Program creates a tic tac toe board and allows users to manually play
 * or let computer auto-play the game.
 * 
 * @author Ethan Lor
 */
public class TicTacToe {
	
	private Scanner scan = new Scanner(System.in);
	private int boardSize = 0;
	private char[][] boardArray;
	private boolean won = false;

	
	/**
	 * Simple initiating main().  Creates default constructor driver object 
	 * that will initiate and run the program.
	 */
	public static void main(String[] args) 
	{
		TicTacToe driver = new TicTacToe();
		driver.takeInput();
		driver.scan.close();

	}
	
	
	/**
	 * Takes user input parameters for size of board.
	 * Asks if player wants to play manually or auto. 
	 */
	private void takeInput()
	{
		// Ask for board size.
		System.out.print("Please enter a board size [3-10]: ");
		if (scan.hasNextInt())
		{
			int num = scan.nextInt();
			if (num >= 3 && num <= 10)
			{
				boardSize = num;
			} 
			else 
			{
				System.out.println();
				System.out.println("I'm sorry, but that is invalid.  Goodbye.");
				return;
			}
		}
		else
		{
			System.out.println();
			System.out.println("I'm sorry, but that is invalid.  Goodbye.");
			return;
		}
		
		// Fill empty board array.
		boardArray = new char[boardSize][boardSize];
		for (int row = 0; row < boardArray.length; row++)
		{
			for (int col = 0; col < boardArray[row].length; col++)
			{
				boardArray[row][col] = '_';
			}
		}
		
		// Ask for manual or auto play.
		System.out.print("Please enter 0 for manual play or 1 for auto-play: ");
		if (scan.hasNextInt())
		{
			int num = scan.nextInt();
			if (num != 0 && num != 1)
			{
				System.out.println();
				System.out.println("I'm sorry, but that is invalid.  Goodbye.");
				return;
			}
			else 
			{
				if (num == 0)
				{
					displayBoard();
					manualPlay();
				}
				else
				{
					displayBoard();
					autoPlay();
				}
			}
		}
		else
		{
			System.out.println();
			System.out.println("I'm sorry, but that is invalid.  Goodbye.");
			return;
		}
	}
	
	
	/**
	 * Draws tic tac toe board.
	 */
	private void displayBoard()
	{
		System.out.println();
		for (int row = 0; row < boardArray.length; row++)
		{
			for (int col = 0; col < boardArray[row].length; col++)
			{
				System.out.print(boardArray[row][col]);
			}
			System.out.println();
		}
	}
	
	
	/**
	 * Initiates manual play.
	 * Asks where players want to place X's and O's.
	 * Draws board and checks for win after each move.
	 */
	private void manualPlay()
	{
		int turn = 0;
		int row = 0;
		int col = 0;
		char player = ('_');
		while (won == false)
		{
			// Figure out who's turn it always starting with player X.
			if (turn%2 == 0)
			{
				player = ('X');
			}
			else
			{
				player = ('O');
			}
			
			// Ask for row selection.
			System.out.println();
			System.out.print("Player " + player + " please choose a row:    ");
			if (scan.hasNextInt())
			{
				int num = scan.nextInt();
				if(num >= 0 && num < boardSize)
				{
					row = num;
				}
				else
				{
					System.out.println();
					System.out.println("I'm sorry, but that is invalid.  Goodbye.");
					return;
				}
			}
			else
			{
				System.out.println();
				System.out.println("I'm sorry, but that is invalid.  Goodbye.");
				return;
			}
			
			// Ask for column selection.
			System.out.print("Player " + player + " please choose a column: ");
			if (scan.hasNextInt())
			{
				int num = scan.nextInt();
				if(num >= 0 && num < boardSize)
				{
					col = num;
				}
				else
				{
					System.out.println();
					System.out.println("I'm sorry, but that is invalid.  Goodbye.");
					return;
				}
			}
			else
			{
				System.out.println();
				System.out.println("I'm sorry, but that is invalid.  Goodbye.");
				return;
			}
			
			// Add players moves to the board, re-draws board, and checks for win.
			if (boardArray[row][col] != 'X' && boardArray[row][col] != 'O')
			{
				boardArray[row][col] = player;
				displayBoard();
				turn++;
				checkWin();
			}
			else
			{
				System.out.println();
				System.out.println("I'm sorry, but that square is already taken.  Goodbye.");
				return;
			}
		}
	}
	
	
	/**
	 * Initiates auto game play.
	 * Displays board after each play.
	 * Checks for win after each move.
	 */
	private void autoPlay()
	{
		int turn = 0;
		char player = ('_');
		int randomRow = 0;
		int randomCol = 0;
		while (won == false)
		{
			if (turn%2 == 0)
			{
				player = ('X');
			}
			else
			{
				player = ('O');
			}
			
			randomRow = (int)(Math.random() * boardSize);
			randomCol = (int)(Math.random() * boardSize);
			
			// Check to see if spot on board is open before placing X or O.
			if (boardArray[randomRow][randomCol] != 'X' && boardArray[randomRow][randomCol] != 'O')
			{
				boardArray[randomRow][randomCol] = player;
				displayBoard();
				checkWin();
				turn++;
			}
		}
	}
	
	
	/**
	 * Check for player wins starting with horizontal win, then vertical win,
	 * then left to right diagonal win, then right to left diagonal win, and then
	 * lastly checks for a draw.
	 */
	private void checkWin()
	{
		// Check for horizontal win.
		for (int row = 0; row < boardArray.length; row++)
		{
			int countX = 0;
			int countO = 0;
			for (int col = 0; col < boardArray[row].length; col++)
			{
				if (boardArray[row][col] == 'X')
				{
					countX++;
				}
				else if (boardArray[row][col] == 'O')
				{
					countO++;
				}
			}
			
			if (countX == boardSize)
			{
				System.out.println();
				System.out.println("Player X Wins!");
				won = true;
				return;
			}
			else if (countO == boardSize)
			{
				System.out.println();
				System.out.println("Player O Wins!");
				won = true;
				return;
			}
		}
		
		// Check for vertical win.
		for (int col = 0; col < boardArray.length; col++)
		{
			int countX = 0;
			int countO = 0;
			for (int row = 0; row < boardArray.length; row++)
			{
				if (boardArray[row][col] == 'X')
				{
					countX++;
				}
				else if (boardArray[row][col] == 'O')
				{
					countO++;
				}
			}
			
			if (countX == boardSize)
			{
				System.out.println();
				System.out.println("Player X Wins!");
				won = true;
				return;
			}
			else if (countO == boardSize)
			{
				System.out.println();
				System.out.println("Player O Wins!");
				won = true;
				return;
			}
		}
		
		// Check for left to right diagonal win.
		int countX = 0;
		int countO = 0;
		for (int row = 0; row < boardArray.length; row++)
		{
			if (boardArray[row][row] == 'X')
			{
				countX++;
			}
			else if (boardArray[row][row] == 'O')
			{
				countO++;
			}
			
			if (countX == boardSize)
			{
				System.out.println();
				System.out.println("Player X Wins!");
				won = true;
				return;
			}
			else if (countO == boardSize)
			{
				System.out.println();
				System.out.println("Player O Wins!");
				won = true;
				return;
			}
		}
		
		// Check for right to left diagonal win.
		countX = 0;
		countO = 0;
		for (int row = 0; row < boardArray.length; row++)
		{
			int col = boardArray.length - 1;
			if (boardArray[row][col - row] == 'X')
			{
				countX++;
			}
			else if (boardArray[row][col - row] == 'O')
			{
				countO++;
			}
			
			if (countX == boardSize)
			{
				System.out.println();
				System.out.println("Player X Wins!");
				won = true;
				return;
			}
			else if (countO == boardSize)
			{
				System.out.println();
				System.out.println("Player O Wins!");
				won = true;
				return;
			}
		}
		
		// Check for draw.
		int count = 0;
		for (int row = 0; row < boardArray.length; row++)
		{
			for (int col = 0; col < boardArray[row].length; col++)
			{
				if (boardArray[row][col] == 'X' || boardArray[row][col] == 'O')
				{
					count++;
				}
			}
			if (count == boardSize*boardSize)
			{
				System.out.println();
				System.out.println("Game is a Draw...");
				won = true;
				return;
			}
		}
	}
}