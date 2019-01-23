// File: CAModel.java
// Author: Ethan Lor
// Note: This is the only file you should modify for Project1

public class CAModel {

	// do not modify the following line of code
	boolean[][] grid; // represents the current state of the CA
	
	// Step 1
    public CAModel(int height, int width) {
    	grid = new boolean[height][width];
    }
    
    // Step 2
    //Change grid size preserving the status of cells within new indices ranges
    public void setGridSize(int height, int width) {
    	boolean[][] newGrid = new boolean[height][width];
    	int rowCount = newGrid.length;
    	int colCount = newGrid[0].length;
    	
    	if(newGrid.length >= grid.length)
    	{
    		rowCount = grid.length;
    	}
    	
    	if(newGrid[0].length >= grid[0].length)
    	{
    		colCount = grid[0].length;
    	}
    	
    	for(int row = 0; row < rowCount; row++)
    	{
    		for(int col = 0; col < colCount; col++)
    		{
    			newGrid[row][col] = grid[row][col];
    		}
    	}
    	
    	grid = newGrid;
    }
    
    // Step 3
    public int getGridHeight() { 
    	return grid.length;
    }
    
    public int getGridWidth() { 
    	return grid[0].length;
    }
    
    public void setGridHeight(int height)  {
    	setGridSize(height, grid[0].length);
    }
    
    public void setGridWidth(int width) {
    	setGridSize(grid.length, width);
    }
   
    // Step 4
    public boolean isPopulated(int row, int col) { 
    	if(grid[row][col] == true)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public boolean isUnpopulated(int row, int col) { 
    	if(grid[row][col] == false)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public void setPopulated(int row, int col) {
    	grid[row][col] = true;
    }
    
    public void setUnpopulated(int row, int col) {
    	grid[row][col] = false;
    }
    
    public void invert(int row, int col)  {
    	if(grid[row][col] == false)
    	{
    		grid[row][col] = true;
    	}
    	else
    	{
    		grid[row][col] = false;
    	}
    }
   
    // Step 5
    public void clear() {
    	boolean[][] clearGrid = new boolean[grid.length][grid[0].length];
    	grid = clearGrid;
    }
    
    // Step 6
    public void random() {
    	for(int row = 0; row < grid.length; row++)
    	{
    		for(int col = 0; col < grid[row].length; col++)
    		{
    			int ran = (int)(Math.random()*2);
    			if(ran == 0)
    			{
    				grid[row][col] = true;
    			}
    			else
    			{
    				grid[row][col] = false;
    			}
    		}
    	}
    }
    
    // Step 7
    //Determines the status of each cell for the next generation and creates new grid
    public void step() {
    	boolean[][] nextGenGrid = new boolean[grid.length][grid[0].length];
    	
    	for(int row = 0; row < grid.length; row++)
    	{
    		for(int col = 0; col < grid[row].length; col++)
    		{
    			int cellCount = indexAdj(row, col);
    			
    			if(grid[row][col] == true)
    			{
    				if(cellCount < 2)
    				{
    					nextGenGrid[row][col] = false;
    				}
    				if(cellCount == 2 || cellCount == 3)
    				{
    					nextGenGrid[row][col] = true;
    				}
    				if(cellCount > 3)
    				{
    					nextGenGrid[row][col] = false;
    				}
    			}
    			
    			if(grid[row][col] == false)
    			{
    				if(cellCount == 3)
    				{
    					nextGenGrid[row][col] = true;
    				}
    				if(cellCount < 3 || cellCount > 3)
    				{
    					nextGenGrid[row][col] = false;
    				}
    			}
    		}
    	}
    	
    	grid = nextGenGrid;
    }
    
    //Helper method to find the correct rows and columns indices of surrounding cells 
    private int indexAdj(int x, int y){
		int row1 = (x - 1);
		int row2 = x;
		int row3 = (x + 1);
		int col1 = (y - 1);
		int col2 = y;
		int col3 = (y + 1);
		
		if(x == 0)
		{
			row1 = (grid.length - 1);
			row3 = (x + 1);
		}
		if(y == 0)
		{
			col1 = (grid[x].length - 1);
			col3 = (y + 1);
		}
		if(x == (grid.length -1))
		{
			row1 = (x - 1);
			row3 = 0;
		}
		if(y == (grid[x].length -1))
		{
			col1 = (y - 1);
			col3 = 0;
		}
		
    	return cellCheck(row1, row2, row3, col1, col2, col3);
    }
    
    //Helper method to count the number of surrounding populated cells
    private int cellCheck(int x1, int x2, int x3, int y1, int y2, int y3){
    	int cellCount = 0;

		if(grid[x1][y1] == true)
		{
			cellCount++;
		}
		if(grid[x1][y2] == true)
		{
			cellCount++;
		}
		if(grid[x1][y3] == true)
		{
			cellCount++;
		}
		if(grid[x2][y1] == true)
		{
			cellCount++;
		}
		if(grid[x2][y3] == true)
		{
			cellCount++;
		}
		if(grid[x3][y1] == true)
		{
			cellCount++;
		}
		if(grid[x3][y2] == true)
		{
			cellCount++;
		}
		if(grid[x3][y3] == true)
		{
			cellCount++;
		}
    	
    	return cellCount;
    }
    
}