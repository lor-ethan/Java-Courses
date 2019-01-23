// File: CAModelIO.java
// Author: Ethan Lor

import java.io.*;
import java.util.*;
import javax.swing.*;

public class CAModelIO extends CAModel {
	
	final String HOME_DIRECTORY = System.getProperty("user.home");
	File f;
	
    public CAModelIO(int height, int width) {
    	super(height, width);
    }
    
    // Task 1: Save functionality
    public void save() {
    	
    	// Step 1: Put up a JFileChooser (starting on the user's desktop)
    	JFileChooser chooser = new JFileChooser(HOME_DIRECTORY + "/Desktop");
    	int retVal = chooser.showSaveDialog(null);
    	
    	if(retVal == JFileChooser.APPROVE_OPTION)
    	{
    		f = chooser.getSelectedFile();
    		// Step 2: Write the current CA configuration to the file selected in Step 1
    		try{
        		FileOutputStream fos = new FileOutputStream(f);
        		PrintWriter writer = new PrintWriter(fos);
        		
        		writer.println(grid.length + " " + grid[0].length);
        		for(int i = 0; i < grid.length; i++)
        		{
        			for(int j = 0; j < grid[i].length; j++)
        			{
        				if(grid[i][j] == false)
        				{
        					writer.print("F");
        				}
        				else
        				{
        					writer.print("T");
        				}
        			}
        			writer.println("");
        		}
        		writer.close();
        		fos.close();
        	}
        	catch(Exception e)
        	{
        		reportProblem("Error occured, did not save");
        	}
    	}
    }
    
    // Task 2: Load functionality
    public void load() {
    	
    	// Step 1: Put up a JFileChooser (starting on the user's desktop)
    	JFileChooser chooser = new JFileChooser(HOME_DIRECTORY + "/Desktop");
    	int retVal = chooser.showOpenDialog(null);
    	Scanner scan = null;
    	
    	if(retVal == JFileChooser.APPROVE_OPTION)
    	{
    		f = chooser.getSelectedFile();
    		// Step 2: Load the CA configuration in the file selected in Step 1 into the simulator
    		try
    		{
    			FileInputStream fis = new FileInputStream(f);
    			scan = new Scanner(fis);
    			int row = 0;
    			int col = 0;
    			
    			//Check for row and column size/integers
    			if(scan.hasNextInt())
    			{
    				row = scan.nextInt();
    				
    				if(scan.hasNextInt())
        			{
        				col = scan.nextInt();
        			}
        			else
        			{
        				throw new IOException();
        			}
    			}
    			else
    			{
    				throw new IOException();
    			}
    			
    			String rowString = "";
    			boolean[][] loadGrid = new boolean[row][col];
    			
    			//Check rows and columns
    			for(int i = 0; i < row; i++)
    			{
    				if(scan.hasNext())
    				{
    					rowString = scan.next();
    					if(rowString.length() == col)
    					{
    						for(int j = 0; j < col; j++)
    						{
    							if(rowString.charAt(j) == 'T')
    							{
    								loadGrid[i][j] = true;
    							}
    							else if(rowString.charAt(j) == 'F')
    							{
    								loadGrid[i][j] = false;
    							}
    							else
    							{
    								throw new IOException();
    							}
    						}
    					}
    					else
    					{
    						throw new IOException();
    					}
    				}
    				else
    				{
    					throw new IOException();
    				}
    			}
    			if(scan.hasNext())
    			{
    				throw new IOException();
    			}
    			fis.close();
    			grid = loadGrid;
    		}
    		catch(IOException e)
    		{
    			reportProblem("The file is corrupt and cannot be opened");
    		}
    		catch(Exception e)
    		{
    			reportProblem(e.getMessage());
    		}
    		finally
    		{
    			scan.close();
    		}
    	}
    }
    
    // call this method to report a problem to the user
    // puts up a dialog box to report a problem
    private void reportProblem(String msg) {
    	JOptionPane.showMessageDialog(null, msg, "Uh Oh!", JOptionPane.INFORMATION_MESSAGE);
    }

}
