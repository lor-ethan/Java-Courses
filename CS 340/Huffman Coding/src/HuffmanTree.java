import java.util.*;

/**
 * CS 340 Homework 3 - Huffman Coding 
 * October 18, 2017
 * 
 * @author Ethan Lor
 *
 */
public class HuffmanTree {
	private Node root;
	private Node current;
	private LinkedList<HuffmanTree> treeQueue;
	private String postOrder = "";

	private class Node {
		private Node left;
		private char data;
		private Node right;

		private Node(Node L, char d, Node R) {
			left = L;
			data = d;
			right = R;
		}
	}

	public HuffmanTree() {
		root = null;
		current = null;
	}

	public HuffmanTree(char d) {
	// Make a one node tree.
		root = new Node(null, d, null);
		current = root;
	}

	public HuffmanTree(String t, char nonLeaf) {
	// Assumes t represents a post order representation of the tree
	// where a node is either a leaf or has two children.
	// nonLeaf is the char value of the data in the non-leaf nodes.
		treeQueue = new LinkedList<HuffmanTree>();
		int charPosition = 0;

		while (charPosition < t.length()) {
			char character = t.charAt(charPosition);

			if (character != nonLeaf) {
				treeQueue.addFirst(new HuffmanTree(character));
			} else {
				HuffmanTree right = treeQueue.poll();
				HuffmanTree left = treeQueue.poll();
				treeQueue.addFirst(new HuffmanTree(left, right, nonLeaf));
			}
			charPosition++;
		}
		HuffmanTree finished = treeQueue.poll();
		root = finished.root;
		current = root;
	}

	public HuffmanTree(HuffmanTree b1, HuffmanTree b2, char d) {
	// Merge b1 and b2
		root = new Node(b1.root, d, b2.root);
		current = root;
	}

	// The following methods allow a user object to follow a path in the tree.
	// Each method except atLeaf and current changes the value of current.
	// atLeaf returns true if the current position is a leaf, otherwise it returns false.
	// current returns the data value in the current Node.

	public void moveRoot() {
		current = root;
	}

	public void moveLeft() {
		current = current.left;
	}

	public void moveRight() {
		current = current.right;
	}

	public boolean atLeaf() {
		if (current.left == null && current.right == null) {
			return true;
		}
		return false;
	}

	public char current() {
		return current.data;
	}

	// Inner class to create an iterator. The iterator allows the user class to find
	// all paths from the root to a leaf. The paths are sequences of 0s and 1s. 
	// 0 means left and 1 means right.
	// You will find it easier to find all the paths when the iterator is created.

	public class PathIterator implements Iterator<String> {
		private String encoding = "";
		private LinkedList<String> encodeList = new LinkedList<String>();

		public PathIterator() {
			findPaths(root);
		}

		/**
		 * Find encodings of the all character paths and stores them to a linked list.
		 * 
		 * @param r
		 */
		public void findPaths(Node r) {
			if (r.left == null && r.right == null) {
				encodeList.add(r.data + encoding);
				return;
			}

			encoding = encoding + "0";
			findPaths(r.left);
			encoding = encoding.substring(0, encoding.length() - 1);
			encoding = encoding + "1";
			findPaths(r.right);

			if (encoding.length() > 1) {
				encoding = encoding.substring(0, encoding.length() - 1);
			}
			return;
		}

		public boolean hasNext() {
			return !encodeList.isEmpty();
		}

		public String next() {
			return encodeList.poll();
		}

		public void remove() {
		// Optional method not implemented.
		}
	}

	public Iterator<String> iterator() {
		// Return a PathIterator Object.
		return new PathIterator();
	}

	public String toString() {
	// Return a post order representation of the tree
	// in the format we discussed in class.
		return toString(root);
	}

	/**
	 * Builds the post ordered string traversal of the Huffman tree and returns the
	 * post ordered string.
	 * 
	 * @param r
	 * @return
	 */
	private String toString(Node r) {
		if (r.left == null && r.right == null) {
			return postOrder + r.data;
		}

		postOrder = toString(r.left);
		postOrder = toString(r.right);
		postOrder = postOrder + r.data;
		return postOrder;
	}
}
