import java.io.*;

/**
 * CS 340 Homework 3 - Huffman Coding 
 * October 18, 2017
 * 
 * @author Ethan Lor
 *
 */
public class HuffmanDecode {
	private HuffmanInputStream his;
	private String treeString;
	private int totalChars;
	private HuffmanTree rebuildTree;
	private BufferedWriter bw;

	public HuffmanDecode(String in, String out) throws IOException {
	// Implements the Huffman Decoding Algorithm
	// Add private methods as needed
		his = new HuffmanInputStream(in);
		totalChars = his.totalChars();
		treeString = his.getTree();
		buildTree();
		bw = new BufferedWriter(new FileWriter(out));
		decode();
	}

	/**
	 * Rebuilds Huffman tree from post order string.
	 */
	private void buildTree() {
		rebuildTree = new HuffmanTree(treeString, (char)128);
	}

	/**
	 * Decodes the input file and writes out the characters to output file.
	 * 
	 * @throws IOException
	 */
	private void decode() throws IOException {
		int count = 0;

		while (count < totalChars) {
			int bit = his.readBit();

			if (bit == 0) {
				rebuildTree.moveLeft();
			} else {
				rebuildTree.moveRight();
			}

			if (rebuildTree.atLeaf()) {
				bw.write(rebuildTree.current());
				rebuildTree.moveRoot();
				count++;
			}
		}
		bw.flush();
		bw.close();
		his.close();
	}

	public static void main(String args[]) {
		try {
			new HuffmanDecode(args[0], args[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
