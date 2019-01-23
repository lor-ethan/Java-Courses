// Description: Runs WordCloud() to create a word cloud
// display of words from a given input file; words and counts
// written to given output file (in word-sorted order).

import java.io.*;
import javax.swing.*;


public class Main
{

    public static void main( String[] args )
    {
        final String HOME_DIRECTORY = System.getProperty("user.home");
    	final String commonWordsFile = "commonest.txt";
        String inputFile = "dream.txt";  //Default text file
        
        JFileChooser chooser = new JFileChooser(HOME_DIRECTORY + "/Desktop");
    	int retVal = chooser.showOpenDialog(null);
    	if(retVal == JFileChooser.APPROVE_OPTION)
    	{
    		File f = chooser.getSelectedFile();
    		inputFile = f.getAbsolutePath();
    	}
        
        WordCloud wc = new WordCloud();
        wc.makeCloud( commonWordsFile, inputFile );
    }
}