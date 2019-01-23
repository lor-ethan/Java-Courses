import java.io.*;

/**
 * CS 340 Homework 3 - Huffman Coding 
 * October 18, 2017
 * 
 * @author Ethan Lor
 *
 */
public class HuffmanInputStream extends BitInputStream {
	private String tree;
	private int totalChars;
	private int position = 8;
	private int readByte = 0;
	private int bit = 0;

	public HuffmanInputStream(String filename) {
		super(filename);
		try {
			tree = d.readUTF();
			totalChars = d.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int readBit() {
		try {
			if (position == 8) {
				readByte = d.readUnsignedByte();
			}
			position--;
			bit = readByte >> position;
			readByte = readByte - (bit << position);

			if (position == 0) {
				position = 8;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bit;
	}

	public String getTree() {
		return tree;
	}

	public int totalChars() {
		return totalChars;
	}

	public void close() {
		try {
			d.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
