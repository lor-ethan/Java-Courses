// Parses an input file into distinct, non-common words,
// counting the occurrences of each such word, before sending the data
// to a CloudWindow for display as a word-cloud.

import java.io.*;
import java.util.*;

public class WordCloud {

	// pre: inFile is a readable input text file
	// post: outFile contains all words from inFile (one occurrence each)
	public void makeCloud(String commonWordsFile, String inputFile) {
		// load common words to one list
		LinkedList<String> commonWords = new LinkedList<String>();
		loadCommon(commonWordsFile, commonWords);

		// open CloudWindow to display words
		LinkedList<Counter> counts = new LinkedList<Counter>();
		countWords(inputFile, commonWords, counts);
		CloudWindow cw = new CloudWindow();
		cw.makeWindow(counts);
	}

	// Pre: the inFile should be an accessible text-file, and list should be a
	// non-null list that can hold Strings.
	// Post: each line of the input file is stored as a String into the list.
	// inFile File to read lines from.
	// list List of Strings to add the lines to.
	private void loadCommon(String inFile, LinkedList<String> list) {
		// FILL IN THIS METHOD
		try {
			FileInputStream fis = new FileInputStream(inFile);
			Scanner scan = new Scanner(fis);
			while (scan.hasNext()) {
				list.add(scan.next());
			}
			scan.close();
			fis.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Pre: the inFile should be an accessible text-file, commonWords should be
	// a list of common words to exclude, and counts should be a list for Counters.
	// Post: the number of occurrences for each word from the input file that is
	// not in the common list should be determined; the counts list will consist of
	// one Counter object per non-common word, holding the count of that word.
	// inFile File to read lines from.
	// commonWords List of words to ignore in the input file.
	// counts List of Counter objects to store the data about word-counts.
	private void countWords(String inFile, LinkedList<String> commonWords, LinkedList<Counter> counts) {
		// FILL IN THIS METHOD
		try {
			FileInputStream fis = new FileInputStream(inFile);
			Scanner scan = new Scanner(fis);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] split = splitLine(line);
				for (String word : split) {
					boolean match = false;
					for (String common : commonWords) {
						if (word.equals(common)) {
							match = true;
							break;
						}
					}
					if (match == false) {
						boolean found = false;
						for (Counter c : counts) {
							if (c.getWord().equals(word)) {
								counts.add(new Counter(word, c.getCount() + 1));
								counts.remove(c);
								found = true;
								break;
							}
						}
						if (found == false) {
							counts.add(new Counter(word, 1));
						}
					}
				}
			}
			scan.close();
			fis.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// post: returns array containing individual words in s, after removing
	// punctuation and converting to lower-case
	private String[] splitLine(String s) {
		// remove punctuation from line and convert to lower-case
		s = s.replaceAll("\\p{Punct}", "");
		s = s.toLowerCase();
		// create array by splitting out all white space
		String[] wds = s.trim().split("\\s+");
		return wds;
	}
}