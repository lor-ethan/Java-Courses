import java.io.*;
import java.util.*;

/**
 * CS 340 Homework 3 - Huffman Coding 
 * October 18, 2017
 * 
 * @author Ethan Lor
 *
 */
public class HuffmanEncode {
	private BufferedReader br1;
	private BufferedReader br2;
	private int[] freArr;
	private int charCount = 0;
	private PriorityQueue<FrequencyCount> pQueue;
	private HuffmanTree finalTree;
	private String[] encoding;
	private String postOrder;

	public HuffmanEncode(String in, String out) throws IOException {
	// Implements the Huffman Encoding Algorithm
	// Add private methods as needed
		br1 = new BufferedReader(new FileReader(in));
		findFreq();
		priorityQueue();
		merge();
		encode();
		postOrder();
		br2 = new BufferedReader(new FileReader(in));
		writeOut(out);
	}

	/**
	 * Finds the frequency of each character of input file.
	 * 
	 * @throws IOException
	 */
	private void findFreq() throws IOException {
		freArr = new int[128];
		int index;

		while ((index = br1.read()) != -1) {
			freArr[index]++;
			charCount++;
		}
		br1.close();
	}

	/**
	 * Puts all the character frequencies into single node trees and inserts the
	 * single node trees into a priority queue.
	 */
	private void priorityQueue() {
		pQueue = new PriorityQueue<FrequencyCount>(128);

		for (int i = 0; i < freArr.length; i++) {
			if (freArr[i] != 0) {
				FrequencyCount node = new FrequencyCount(freArr[i], new HuffmanTree((char) i));
				pQueue.add(node);
			}
		}
	}

	/**
	 * Pulls off "highest" priority nodes, merges them, and puts merged tree back
	 * into priority queue until only one final Huffman tree is created.
	 */
	private void merge() {
		while (pQueue.size() >= 2) {
			FrequencyCount node1 = pQueue.poll();
			FrequencyCount node2 = pQueue.poll();
			FrequencyCount merge = new FrequencyCount(node1.priority + node2.priority,
					new HuffmanTree(node1.data, node2.data, (char) 128));
			pQueue.add(merge);
		}
		FrequencyCount finalNode = pQueue.poll();
		finalTree = finalNode.data;
	}

	/**
	 * Finds all character encodings and puts them into an array.
	 */
	private void encode() {
		encoding = new String[128];
		Iterator<String> itr = finalTree.iterator();
		
		while (itr.hasNext()) {
			String next = itr.next();
			char character = next.charAt(0);
			String encode = next.substring(1);
			encoding[character] = encode;
		}
	}

	/**
	 * Finds the post order traversal of the Huffman tree in string form.
	 */
	private void postOrder() {
		postOrder = finalTree.toString();
	}

	/**
	 * Writes out the post order string traversal, character count, and the encoding
	 * of the text file to output file.
	 * 
	 * @param out
	 * @throws IOException
	 */
	private void writeOut(String out) throws IOException {
		HuffmanOutputStream hos = new HuffmanOutputStream(out, postOrder, charCount);
		int index;

		while ((index = br2.read()) != -1) {
			String encodeString = encoding[index];
			int position = 0;
			
			while (position < encodeString.length()) {
				hos.writeBit(encodeString.charAt(position) - 48);
				position++;
			}
		}
		br2.close();
		hos.close();
	}

	public class FrequencyCount implements Comparable<FrequencyCount> {
		private int priority;
		private HuffmanTree data;

		public FrequencyCount(int f, HuffmanTree d) {
			priority = f;
			data = d;
		}

		public int compareTo(FrequencyCount node) {
			return priority - node.priority;
		}
	}

	public static void main(String args[]) {
		try {
			new HuffmanEncode(args[0], args[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
