import java.io.*;

/**
 * CS 340 Homework 3 - Huffman Coding 
 * October 18, 2017
 * 
 * @author Ethan Lor
 *
 */
public class HuffmanOutputStream extends BitOutputStream {
	private int bitCount = 0;
	private int bitSum = 0;

	public HuffmanOutputStream(String filename, String tree, int totalChars) {
		super(filename);

		try {
			d.writeUTF(tree);
			d.writeInt(totalChars);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeBit(int bit) {
	// PRE bit == 0 || bit == 1
		try {
			bitSum = (bitSum << 1) + bit;
			bitCount++;

			if (bitCount == 8) {
				d.write(bitSum);
				bitCount = 0;
				bitSum = 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (bitCount != 0) {
				for (int i = 0; i < 8 - bitCount; i++) {
					bitSum = (bitSum << 1) + 0;
				}
			}
			d.write(bitSum);
			d.flush();
			d.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
