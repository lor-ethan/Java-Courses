// Solver.java
// Ethan Lor

public class Solver {
	int[][] puzzleReference;
	int[][] solvedPuzzle = new int[6][6];
	
	//Wrapper
	boolean solve(int[][] puzzle) {
		puzzleReference = puzzle;
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle.length; j++) {
				solvedPuzzle[i][j] = puzzle[i][j];
			}
		}
		//Check for bad puzzle before trying to solve
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle.length; j++) {
				if (puzzle[i][j] != 0) {
					if (!canPlaceNum(puzzle[i][j], i, j)) {
						return false;
					}
				}
			}
		}
		//Call method to fill in/solve puzzle
		if (fillInPosition(0, 0)) {
			for (int i = 0; i < puzzle.length; i++) {
				for (int j = 0; j < puzzle.length; j++) {
					puzzle[i][j] = solvedPuzzle[i][j];
				}
			}
			return true;
		}
		return false;
	}

	//Recursive method to try to find solutions to puzzle
	public boolean fillInPosition(int row, int col) {
		if (row == 6) {
			return true;
		}

		if (col < 6) {
			for (int num = 1; num < 7; num++) {
				if (puzzleReference[row][col] == 0) {
					if (canPlaceNum(num, row, col)) {
						solvedPuzzle[row][col] = num;
						if (fillInPosition(row, col + 1)) {
							return true;
						}
						solvedPuzzle[row][col] = puzzleReference[row][col];
					}
				} else {
					if (fillInPosition(row, col + 1)) {
						return true;
					}
					solvedPuzzle[row][col] = puzzleReference[row][col];
				}
			}
		} else {
			if (fillInPosition(row + 1, 0)) {
				return true;
			}
		}
		return false;
	}
	
	//Check if the numbers can be placed "safely"
	public boolean canPlaceNum(int num, int row, int col) {
		for (int i = 0; i < solvedPuzzle.length; i++) {
			if (i != col) {
				if (num == solvedPuzzle[row][i]) {
					return false;
				}
			}
			if (i != row) {
				if (num == solvedPuzzle[i][col]) {
					return false;
				}
			}
		}
		return true;
	}
}