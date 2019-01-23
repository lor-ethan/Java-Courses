/*
 * Ethan Lor
 * CS 340
 * Homework 5 B+Tree
 * 
 * November 22, 2017
 */
import java.util.*;
import java.io.*;

public class BTree {

	RandomAccessFile f;
	int order;
	int blocksize;
	long root;
	long free;
	// Add instance variables as needed.
	int maxChildren;
	int maxKeys;
	int minChildren;
	int minKeys;
	LinkedList<Long> path;
	boolean split = false;

	private class BTreeNode {
		private int count;
		private int keys[];
		private long children[];

		// Constructor and other methods.
		private BTreeNode() {
			count = 0;
			keys = new int[order - 1];
			children = new long[order];
		}
		
		/*
		 * Method to read in one full node at a time.
		 */
		private void readNode(long addr) throws IOException {
			f.seek(addr);
			count = f.readInt();
			for (int i = 0; i < order - 1; i++) {
				keys[i] = f.readInt();
			}
			for (int j = 0; j < order; j++) {
				children[j] = f.readLong();
			}
		}
	}

	public BTree(String filename, int bsize) throws IOException {
		// bsize is the block size. This value is used to calculate the order of the
		// B+Tree
		// all B+Tree nodes will use bsize bytes.
		// make a new B+Tree
		newTree(filename, bsize);
	}

	/*
	 * Make a new BTree file.
	 */
	private void newTree(String filename, int bsize) throws IOException {
		File file = new File(filename);
		if (file.createNewFile()) {
			// Do nothing. New file created.
		} else {
			file.delete();
			file.createNewFile();
		}
		order = bsize / 12;
		blocksize = bsize;
		root = 0;
		free = 0;
		f = new RandomAccessFile(file, "rw");
		f.writeLong(root);
		f.writeLong(free);
		f.writeInt(blocksize);
		maxChildren = order;
		maxKeys = order - 1;
		minChildren = (int) Math.ceil((double) order / 2);
		minKeys = (int) Math.ceil((double) order / 2) - 1;
	}

	public BTree(String filename) throws IOException {
		// Open an existing B+Tree.
		openBTree(filename);
	}

	/*
	 * Open an existing BTree file.
	 */
	private void openBTree(String filename) throws IOException {
		f = new RandomAccessFile(new File(filename), "rw");
		root = f.readLong();
		free = f.readLong();
		blocksize = f.readInt();
		order = blocksize / 12;
		maxChildren = order;
		maxKeys = order - 1;
		minChildren = (int) Math.ceil((double) order / 2);
		minKeys = (int) Math.ceil((double) order / 2) - 1;
	}

	public boolean insert(int key, long addr) throws IOException {
		/*
		 * If key is not a duplicate add key to the B+Tree. addr (in DBTable) is the
		 * address of the row that contains the key. Return true if the key is added.
		 * Return false if the key is a duplicate.
		 */
		if (search(key) == 0) {
			insertKey(key, addr);
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Insert key into the B+Tree.
	 */
	private void insertKey(int key, long tableAddr) throws IOException {
		if (path.isEmpty()) {
			emptyTree(key, tableAddr);
			return;
		}

		// Look at node.
		long treeAddr = path.poll();
		BTreeNode cur = new BTreeNode();
		cur.readNode(treeAddr);
		if (treeAddr == root && Math.abs(cur.count) == maxKeys) { // Splitting the root.
			rootLeafSplit(key, tableAddr, treeAddr, cur);
			return;
		}

		if (Math.abs(cur.count) < maxKeys) {
			// Can insert
			leafInsert(key, tableAddr, treeAddr, cur);
		} else {
			// Max keys split leaf node
			splitLeaf(key, tableAddr, treeAddr, cur);
		}
	}

	/*
	 * Get the next free address that can be written to.
	 */
	private long getFree() throws IOException {
		if (free == 0) {
			return f.length();
		} else {
			long avaliable = free;
			f.seek(free);
			free = f.readLong();
			return avaliable;
		}
	}

	/*
	 * Add an address to the free list.
	 */
	private void addFree(long treeAddr) throws IOException {
		f.seek(treeAddr);
		f.writeLong(free);
		free = treeAddr;
	}

	/*
	 * Empty tree case.
	 */
	private void emptyTree(int key, long tableAddr) throws IOException {
		long freeAddr = getFree();
		root = freeAddr;
		f.seek(freeAddr);
		f.writeInt(-1);
		f.writeInt(key);
		for (int i = 1; i < maxKeys; i++) {
			f.writeInt(0);
		}
		f.writeLong(tableAddr);
		for (int i = 1; i < maxChildren; i++) {
			f.writeLong(0);
		}
	}

	/*
	 * Insert Key into a leaf case.
	 */
	private void leafInsert(int key, long tableAddr, long treeAddr, BTreeNode cur) throws IOException {
		cur.count--; // leaf count will be negative
		int insertKey = key;
		long insertChild = tableAddr;
		for (int i = 0; i < Math.abs(cur.count); i++) {
			if (i == Math.abs(cur.count) - 1) {
				cur.keys[i] = insertKey;
				cur.children[i] = insertChild;
				break;
			}
			if (key < cur.keys[i]) {
				int temp = cur.keys[i];
				cur.keys[i] = insertKey;
				insertKey = temp;
				long temp2 = cur.children[i];
				cur.children[i] = insertChild;
				insertChild = temp2;
			}
		}
		writeNode(cur, treeAddr);
	}

	/*
	 * Split leaf case when adding new key.
	 */
	private void splitLeaf(int key, long tableAddr, long treeAddr, BTreeNode cur) throws IOException {
		BTreeNode newNode = new BTreeNode();
		int insertKey = key;
		long insertChild = tableAddr;

		// Split keys into the correct siblings
		// Left Sibling
		cur.count = cur.count / 2;
		for (int i = 0; i < Math.abs(cur.count); i++) {
			if (insertKey < cur.keys[i]) {
				int temp = cur.keys[i];
				cur.keys[i] = insertKey;
				insertKey = temp;
				long temp2 = cur.children[i];
				cur.children[i] = insertChild;
				insertChild = temp2;
			}
		}

		// Right Sibling
		int indexOffset = Math.abs(cur.count);
		newNode.count = (-1 * order) - cur.count;
		for (int j = 0; j < Math.abs(newNode.count); j++) {
			if (j == Math.abs(newNode.count) - 1) {
				newNode.keys[j] = insertKey;
				newNode.children[j] = insertChild;
				break;
			}
			if (insertKey < cur.keys[indexOffset]) {
				int temp = cur.keys[indexOffset];
				newNode.keys[j] = insertKey;
				insertKey = temp;
				long temp2 = cur.children[indexOffset];
				newNode.children[j] = insertChild;
				insertChild = temp2;
			} else {
				newNode.keys[j] = cur.keys[indexOffset];
				newNode.children[j] = cur.children[indexOffset];
			}
			indexOffset++;
		}
		// Assign the correct links to siblings
		newNode.children[maxChildren - 1] = cur.children[maxChildren - 1];
		long freeAddr = getFree();
		cur.children[maxChildren - 1] = freeAddr;
		// Write out nodes
		writeNode(cur, treeAddr);
		writeNode(newNode, freeAddr);
		//A new key needs to be added to the parent.
		pushUp(treeAddr, newNode.keys[0], freeAddr);
	}

	/*
	 * New key from child needs to be inserted into parent.
	 */
	private void nonLeafInsert(int key, long keyAddr, long treeAddr, BTreeNode parentNode) throws IOException {
		parentNode.count++;
		int insertKey = key;
		long insertKeyAddr = keyAddr;
		for (int i = 0; i < parentNode.count; i++) {
			if (i == parentNode.count - 1) {
				parentNode.keys[i] = insertKey;
				parentNode.children[i + 1] = insertKeyAddr;
				break;
			}
			if (key < parentNode.keys[i]) {
				int temp = parentNode.keys[i];
				parentNode.keys[i] = insertKey;
				insertKey = temp;
				long temp2 = parentNode.children[i + 1];
				parentNode.children[i + 1] = insertKeyAddr;
				insertKeyAddr = temp2;
			}
		}
		writeNode(parentNode, treeAddr);
	}

	/*
	 * Need to split a non-leaf node.
	 */
	private void nonLeafSplit(int key, long keyAddr, long treeAddr, BTreeNode cur) throws IOException {
		BTreeNode newNode = new BTreeNode();
		int insertKey = key;
		long insertKeyAddr = keyAddr;

		// Split keys into the correct siblings
		// Left Sibling
		cur.count = cur.count / 2;
		for (int i = 0; i < cur.count; i++) {
			if (insertKey < cur.keys[i]) {
				int temp = cur.keys[i];
				cur.keys[i] = insertKey;
				insertKey = temp;
				long temp2 = cur.children[i + 1];
				cur.children[i + 1] = insertKeyAddr;
				insertKeyAddr = temp2;
			}
		}

		// Right sibling
		int pushKey;
		int indexOffset = cur.count;
		newNode.count = maxKeys - (cur.count);
		if (insertKey > cur.keys[cur.count]) {
			// have to keep comparing till end
			pushKey = cur.keys[cur.count];
			newNode.children[0] = cur.children[cur.count + 1];
			for (int j = 0; j < newNode.count; j++) {
				if (j == newNode.count - 1) {
					newNode.keys[j] = insertKey;
					newNode.children[j + 1] = insertKeyAddr;
					break;
				}
				if (insertKey < cur.keys[indexOffset + 1]) {
					int temp = cur.keys[indexOffset + 1];
					newNode.keys[j] = insertKey; // Changed
					insertKey = temp;
					long temp2 = cur.children[indexOffset + 2];
					newNode.children[j + 1] = insertKeyAddr; // Changed
					insertKeyAddr = temp2;
				} else {
					newNode.keys[j] = cur.keys[indexOffset + 1];
					newNode.children[j + 1] = cur.children[indexOffset + 2];
				}
				indexOffset++;
			}
		} else {
			pushKey = insertKey;
			newNode.children[0] = insertKeyAddr;
			for (int k = 0; k < newNode.count; k++) {
				newNode.keys[k] = cur.keys[indexOffset];
				newNode.children[k + 1] = cur.children[indexOffset + 1];
				indexOffset++;
			}
		}
		writeNode(cur, treeAddr);
		long rightAddr = getFree();
		writeNode(newNode, rightAddr);
		pushUp(treeAddr, pushKey, rightAddr);
	}

	/*
	 * Splitting the first root node.
	 */
	private void rootLeafSplit(int key, long keyAddr, long treeAddr, BTreeNode cur) throws IOException {
		BTreeNode newNode = new BTreeNode();
		int insertKey = key;
		long insertChild = keyAddr;

		// Split keys into the correct siblings
		// Left Sibling
		cur.count = cur.count / 2;
		for (int i = 0; i < Math.abs(cur.count); i++) {
			if (insertKey < cur.keys[i]) {
				int temp = cur.keys[i];
				cur.keys[i] = insertKey;
				insertKey = temp;
				long temp2 = cur.children[i];
				cur.children[i] = insertChild;
				insertChild = temp2;
			}
		}

		// Right sibling
		int pushKey;
		int indexOffset = Math.abs(cur.count);
		newNode.count = (-1 * order) - cur.count;
		if (insertKey > cur.keys[Math.abs(cur.count)]) {
			// have to keep comparing till end
			pushKey = cur.keys[Math.abs(cur.count)];
			for (int j = 0; j < Math.abs(newNode.count); j++) {
				if (j == Math.abs(newNode.count) - 1) {
					newNode.keys[j] = insertKey;
					newNode.children[j] = insertChild;
					break;
				}
				if (insertKey < cur.keys[indexOffset]) {
					int temp = cur.keys[indexOffset];
					newNode.keys[j] = insertKey; // Changed
					insertKey = temp;
					long temp2 = cur.children[indexOffset];
					newNode.children[j] = insertChild; // Changed
					insertChild = temp2;
				} else {
					newNode.keys[j] = cur.keys[indexOffset];
					newNode.children[j] = cur.children[indexOffset];
				}
				indexOffset++;
			}
		} else {
			pushKey = insertKey;
			newNode.keys[0] = insertKey;
			newNode.children[0] = insertChild;
			for (int k = 1; k < Math.abs(newNode.count); k++) {
				newNode.keys[k] = cur.keys[indexOffset];
				newNode.children[k] = cur.children[indexOffset];
				indexOffset++;
			}
		}
		// Assign the correct links to siblings
		newNode.children[maxChildren - 1] = cur.children[maxChildren - 1];
		long freeAddr = getFree();
		cur.children[maxChildren - 1] = freeAddr;
		writeNode(cur, treeAddr);
		long rightAddr = getFree();
		writeNode(newNode, rightAddr);
		pushUp(treeAddr, pushKey, rightAddr);
	}

	/*
	 * Key needs to be added to a parent node.
	 */
	private void pushUp(long left, int insertKey, long right) throws IOException {
		if (path.isEmpty()) { // make a new root
			BTreeNode newNode = new BTreeNode();
			newNode.count = 1;
			newNode.keys[0] = insertKey;
			newNode.children[0] = left;
			newNode.children[1] = right;
			long freeAddr = getFree();
			root = freeAddr;
			writeNode(newNode, freeAddr);
		} else {
			// add to non-leaf node
			long parent = path.poll();
			BTreeNode parentNode = new BTreeNode();
			parentNode.readNode(parent);
			if (parentNode.count == maxKeys) {
				// Max keys split non-leaf node
				nonLeafSplit(insertKey, right, parent, parentNode);
			} else {
				nonLeafInsert(insertKey, right, parent, parentNode);
			}
		}
	}

	/*
	 * Write a complete node out to BTree file.
	 */
	private void writeNode(BTreeNode node, long treeAddr) throws IOException {
		f.seek(treeAddr);
		f.writeInt(node.count);
		for (int i = 0; i < maxKeys; i++) {
			f.writeInt(node.keys[i]);
		}
		for (int j = 0; j < maxChildren; j++) {
			f.writeLong(node.children[j]);
		}
	}

	public long remove(int key) throws IOException {
		/*
		 * If they key is in the BTree, remove the key and return the address of the
		 * row. Return 0 if the key is not found in the B+Tree.
		 */
		long rowAddr = search(key);
		if (rowAddr == 0) {
			return 0;
		} else {
			removeKey(key);
			return rowAddr;
		}
	}

	/*
	 * Remove key from node
	 */
	private void removeKey(int key) throws IOException {
		long treeAddr = path.poll();
		BTreeNode treeNode = new BTreeNode();
		treeNode.readNode(treeAddr);

		if (treeAddr == root) { // Removing key at root
			rootKeyRemove(key);
			return;
		}

		if (Math.abs(treeNode.count) > minKeys) {
			// Has enough keys to remove.
			leafKeyRemove(key, treeAddr);
			return;
		}

		// Has minimum number of keys.
		BTreeNode parentNode = new BTreeNode();
		BTreeNode leftSibling = new BTreeNode();
		BTreeNode rightSibling = new BTreeNode();
		long parentAddr = path.peek();
		long leftAddr = 0;
		long rightAddr = 0;
		parentNode.readNode(parentAddr);
		// Try to borrow first
		if (parentNode.children[0] == treeAddr) {
			// Leftmost child. Try to borrow from the right.
			rightAddr = parentNode.children[1];
			rightSibling.readNode(rightAddr);
			if (Math.abs(rightSibling.count) > minKeys) {
				rightSiblingBorrow(key, treeAddr, rightAddr);
				return;
			} else {
				// Must combine with right sibling
				combineRightSibling(key, treeAddr, rightAddr);
				// Special case need to change the parent's parent key to reflect new key if it was deleted
				path.poll();
				if (!path.isEmpty()) {
					treeNode.readNode(treeAddr);
					if(treeNode.keys[0] != key) {
						// Replace Key in parent's parent
						replaceParentKey(key, treeNode.keys[0]);
					}
				}
				return;
			}
		} else if (parentNode.children[parentNode.count] == treeAddr) {
			// Rightmost child. Try to borrow from the left.
			leftAddr = parentNode.children[parentNode.count - 1];
			leftSibling.readNode(leftAddr);
			if (Math.abs(leftSibling.count) > minKeys) {
				leftSiblingBorrow(key, leftAddr, treeAddr);
				return;
			} else {
				// Must combine with left sibling
				combineLeftSibling(key, leftAddr, treeAddr);
				return;
			}
		} else {
			// Has a left and right sibling.
			for (int i = 1; i < parentNode.count; i++) {
				if (parentNode.children[i] == treeAddr) {
					leftAddr = parentNode.children[i - 1];
					leftSibling.readNode(leftAddr);
					rightAddr = parentNode.children[i + 1];
					rightSibling.readNode(rightAddr);
					break;
				}
			}
			if (Math.abs(leftSibling.count) > minKeys) {
				// left sibling borrow
				leftSiblingBorrow(key, leftAddr, treeAddr);
				return;
			} else if (Math.abs(rightSibling.count) > minKeys) {
				// right sibling borrow
				rightSiblingBorrow(key, treeAddr, rightAddr);
				return;
			} else {
				// Must combine siblings. This convention combines with left sibling if can.
				combineLeftSibling(key, leftAddr, treeAddr);
				return;
			}
		}
	}

	/*
	 * Remove a key from the root node.
	 */
	private void rootKeyRemove(int key) throws IOException {
		BTreeNode rootNode = new BTreeNode();
		rootNode.readNode(root);
		if (rootNode.count < 0) { // At root/leaf node
			for (int i = 0; i < Math.abs(rootNode.count); i++) {
				if (rootNode.keys[i] == key) {
					for (int j = i; j < Math.abs(rootNode.count) - 1; j++) {
						rootNode.keys[j] = rootNode.keys[j + 1];
						rootNode.children[j] = rootNode.children[j + 1];
					}
				}
			}
			rootNode.count++;
		} else {
			for (int i = 0; i < rootNode.count; i++) {
				if (rootNode.keys[i] == key) {
					for (int j = i; j < rootNode.count - 1; j++) {
						rootNode.keys[j] = rootNode.keys[j + 1];
						rootNode.children[j + 1] = rootNode.children[j + 2];
					}
				}
			}
			rootNode.count--;
		}
		if (rootNode.count == 0) {
			addFree(root);
			root = 0;
		} else {
			writeNode(rootNode, root);
		}
	}

	/*
	 * Removing a key from a leaf node.
	 */
	private void leafKeyRemove(int key, long treeAddr) throws IOException {
		BTreeNode treeNode = new BTreeNode();
		treeNode.readNode(treeAddr);
		boolean repParentKey = false;
		for (int i = 0; i < Math.abs(treeNode.count); i++) {
			if (treeNode.keys[i] == key) {
				if (i == 0) {
					repParentKey = true;
				}
				treeNode.count++; // Actually subtracting from a negative count
				for (int j = i; j < Math.abs(treeNode.count); j++) {
					treeNode.keys[j] = treeNode.keys[j + 1];
					treeNode.children[j] = treeNode.children[j + 1];
				}
			}
		}
		writeNode(treeNode, treeAddr);
		if (repParentKey == true) {
			replaceParentKey(key, treeNode.keys[0]);
		}
	}

	/*
	 * Borrow from left sibling.
	 */
	private void leftSiblingBorrow(int key, long leftAddr, long rightAddr) throws IOException {
		BTreeNode left = new BTreeNode();
		BTreeNode right = new BTreeNode();
		left.readNode(leftAddr);
		right.readNode(rightAddr);
		int oriKey = right.keys[0];
		int borrowKey = 0;
		long borrowAddr = 0;
		if (left.count < 0) { // At leaf node
			borrowKey = left.keys[Math.abs(left.count) - 1];
			borrowAddr = left.children[Math.abs(left.count) - 1];
			left.count++;
			writeNode(left, leftAddr);
			for (int i = 0; i < Math.abs(right.count); i++) {
				if (borrowKey == key) {
					break;
				}
				if (borrowKey < right.keys[i]) {
					int tempKey = right.keys[i];
					right.keys[i] = borrowKey;
					borrowKey = tempKey;
					long tempAddr = right.children[i];
					right.children[i] = borrowAddr;
					borrowAddr = tempAddr;
				}
			}
			writeNode(right, rightAddr);
			replaceParentKey(oriKey, right.keys[0]);
		} else {
			int upKey = left.keys[left.count - 1];
			long upAddr = left.children[left.count];
			left.count--;
			long parentKeyAddr = right.children[0];
			BTreeNode leftMostChild = new BTreeNode();
			leftMostChild.readNode(parentKeyAddr);
			int parentKey = leftMostChild.keys[0]; // 50
			if (right.keys[0] == key) {
				right.keys[0] = parentKey;
				right.children[0] = upAddr;
				right.children[1] = parentKeyAddr;
			} else {
				int insertKey = right.keys[0];
				long insertAddr = right.children[1];
				right.keys[0] = parentKey;
				right.children[0] = upAddr;
				right.children[1] = parentKeyAddr;
				for (int i = 1; i < right.count; i++) {
					if (right.keys[i] == key) {
						right.keys[i] = insertKey;
						right.children[i + 1] = insertAddr;
						break;
					}
					int temp = right.keys[i];
					right.keys[i] = insertKey;
					insertKey = temp;
					long temp2 = right.children[i + 1];
					right.children[i + 1] = insertAddr;
					insertAddr = temp2;
				}
			}
			writeNode(left, leftAddr);
			writeNode(right, rightAddr);
			replaceParentKey(parentKey, upKey);
		}
	}

	/*
	 * Borrow from right sibling.
	 */
	private void rightSiblingBorrow(int key, long leftAddr, long rightAddr) throws IOException {
		BTreeNode left = new BTreeNode();
		BTreeNode right = new BTreeNode();
		left.readNode(leftAddr);
		right.readNode(rightAddr);
		int oriLeftKey = left.keys[0];
		int oriRightKey = right.keys[0];
		int borrowKey = 0;
		long borrowAddr = 0;
		if (left.count < 0) { // At leaf nodes
			borrowKey = right.keys[0];
			borrowAddr = right.children[0];
			right.count++; // Negative so actually subtracting
			for (int i = 0; i < Math.abs(right.count); i++) {
				right.keys[i] = right.keys[i + 1];
				right.children[i] = right.children[i + 1];
			}
			writeNode(right, rightAddr);
			for (int j = 0; j < Math.abs(left.count); j++) {
				if (left.keys[j] == key) {
					for (int k = j; k < Math.abs(left.count); k++) {
						if (k == Math.abs(left.count) - 1) {
							left.keys[k] = borrowKey;
							left.children[k] = borrowAddr;
							break;
						}
						left.keys[k] = left.keys[k + 1];
						left.children[k] = left.children[k + 1];
					}
					break;
				}
			}
			writeNode(left, leftAddr);
			replaceParentKey(oriRightKey, right.keys[0]);
			if (oriLeftKey == key) {
				replaceParentKey(oriLeftKey, left.keys[0]);
			}
		} else { // At non-leaf node.
			for (int i = 0; i < left.count; i++) {
				if (left.keys[i] == key) {
					for (int j = i; j < left.count - 1; j++) {
						left.keys[j] = left.keys[j + 1];
						left.children[j + 1] = left.children[j + 2];
					}
					break;
				}
			}
			long parentKeyInsert = right.children[0];
			BTreeNode leftMostChild = new BTreeNode();
			leftMostChild.readNode(parentKeyInsert);
			int parentKeyChild = leftMostChild.keys[0];
			left.keys[left.count - 1] = parentKeyChild;
			left.children[left.count] = parentKeyInsert;
			right.children[0] = right.children[1];
			right.count--;
			for (int k = 0; k < right.count; k++) {
				right.keys[k] = right.keys[k + 1];
				right.children[k + 1] = right.children[k + 2];
			}
			writeNode(left, leftAddr);
			writeNode(right, rightAddr);
			replaceParentKey(parentKeyChild, oriRightKey);
		}

	}

	/*
	 * Combine with right sibling.
	 */
	private void combineRightSibling(int key, long leftAddr, long rightAddr) throws IOException {
		BTreeNode left = new BTreeNode();
		BTreeNode right = new BTreeNode();
		left.readNode(leftAddr);
		right.readNode(rightAddr);
		int repKey = 0;
		if (left.count < 0) { // At leaf node
			repKey = right.keys[0];
			left.count++;
			for (int i = 0; i < Math.abs(left.count); i++) {
				if (left.keys[i] == key) {
					for (int j = i; j < Math.abs(left.count); j++) {
						left.keys[j] = left.keys[j + 1];
						left.children[j] = left.children[j + 1];
					}
					break;
				}
			}
			int offset = Math.abs(left.count);
			left.count = left.count + right.count; // for leaf siblings
			left.children[order - 1] = right.children[order - 1]; // get correct next sibling
			for (int k = 0; k < Math.abs(right.count); k++) {
				left.keys[offset] = right.keys[k];
				left.children[offset] = right.children[k];
				offset++;
			}
		} else {
			left.count--;
			for (int i = 0; i < left.count; i++) {
				if (left.keys[i] == key) {
					for (int j = i; j < left.count; j++) {
						left.keys[j] = left.keys[j + 1];
						left.children[j + 1] = left.children[j + 2];
					}
					break;
				}
			}
			long parentKeyInsert = right.children[0];
			BTreeNode leftMostChild = new BTreeNode();
			leftMostChild.readNode(parentKeyInsert);
			int parentKeyChild = leftMostChild.keys[0];
			repKey = parentKeyChild;
			left.keys[left.count] = parentKeyChild;
			left.children[left.count + 1] = parentKeyInsert;
			left.count++;
			int offset = left.count;
			left.count = left.count + right.count; // for non-leaf siblings
			left.children[offset] = right.children[0]; // get correct address to child
			for (int k = 0; k < right.count; k++) {
				left.keys[offset] = right.keys[k];
				left.children[offset + 1] = right.children[k + 1];
				offset++;
			}
		}
		writeNode(left, leftAddr);
		addFree(rightAddr);

		// need to remove a key from parent node.
		BTreeNode parent = new BTreeNode();
		long parentAddr = path.peek();
		parent.readNode(parentAddr);
		if (parentAddr == root && parent.count == 1) { // Deleting last key from root.
			addFree(root);
			root = leftAddr;
			return;
		}
		if (parent.count > minKeys) {
			parent.count--;
			// if root or non-leaf node
			for (int l = 0; l < parent.count; l++) {
				if (parent.keys[l] == repKey) {
					for (int m = l; m < parent.count; m++) { 
						parent.keys[m] = parent.keys[m + 1];
						parent.children[m + 1] = parent.children[m + 2];
					}
					writeNode(parent, parentAddr);
					return;
				}
			}
			writeNode(parent, parentAddr);
		} else {
			// Parent has minimum number of keys
			removeKey(repKey);
		}
	}

	/*
	 * Combine with left sibling.
	 */
	private void combineLeftSibling(int key, long leftAddr, long rightAddr) throws IOException {
		BTreeNode left = new BTreeNode();
		BTreeNode right = new BTreeNode();
		left.readNode(leftAddr);
		right.readNode(rightAddr);
		int repKey = 0;
		if (left.count < 0) { // At leaf node
			repKey = right.keys[0];
			int offset = Math.abs(left.count);
			left.count = left.count + (right.count + 1);
			for (int i = 0; i < Math.abs(right.count); i++) {
				if (right.keys[i] == key) {
					// need to skip over the key to add to left
					for (int j = i; j < Math.abs(right.count) - 1; j++) {
						left.keys[offset] = right.keys[j + 1];
						left.children[offset] = right.children[j + 1];
						offset++;
					}
					break;
				} else {
					left.keys[offset] = right.keys[i];
					left.children[offset] = right.children[i];
				}
				offset++;
			}
			left.children[order - 1] = right.children[order - 1]; // Assign correct next sibling
			writeNode(left, leftAddr);
			addFree(rightAddr);
		} else { // At non-left node.
			long parentKeyInsert = right.children[0];
			BTreeNode leftMostChild = new BTreeNode();
			leftMostChild.readNode(parentKeyInsert);
			int parentKeyChild = leftMostChild.keys[0];
			repKey = parentKeyChild;
			left.keys[left.count] = parentKeyChild;
			left.children[left.count + 1] = parentKeyInsert;
			left.count++;
			int offset = left.count;
			for (int i = 0; i < right.count-1; i++) {
				if (right.keys[i] == key) {
					for (int j = i; j < right.count - 1; j++) {
						left.keys[offset] = right.keys[j + 1];
						left.children[offset] = right.children[j + 1];
						offset++;
					}
					break;
				}
				left.keys[offset] = right.keys[i];
				left.children[offset + 1] = right.children[i + 1];
				offset++;
			}
			left.count = left.count + right.count - 1;
			writeNode(left, leftAddr);
			addFree(rightAddr);
		}

		// need to remove a key from parent node.
		BTreeNode parent = new BTreeNode();
		long parentAddr = path.peek();
		parent.readNode(parentAddr);
		if (parentAddr == root && parent.count == 1) { // Deleting last key from root.
			addFree(root);
			root = leftAddr;
			return;
		}
		if (parent.count > minKeys) {
			parent.count--;
			for (int l = 0; l < parent.count; l++) {
				if (parent.keys[l] == repKey) {
					for (int m = l; m < parent.count; m++) { 
						parent.keys[m] = parent.keys[m + 1];
						parent.children[m + 1] = parent.children[m + 2];
					}
					writeNode(parent, parentAddr);
					return;
				}
			}
			writeNode(parent, parentAddr);
		} else {
			removeKey(repKey);
		}

	}

	/*
	 * A key was removed from child and the key needs to be removed in parent node/s.
	 */
	private void replaceParentKey(int key, int repKey) throws IOException {
		if(path.isEmpty()) {
			return;
		}
		BTreeNode treeNode = new BTreeNode();
		long treeAddr = path.poll();
		treeNode.readNode(treeAddr);
		for (int i = 0; i < treeNode.count; i++) {
			if (treeNode.keys[i] == key) {
				treeNode.keys[i] = repKey;
				writeNode(treeNode, treeAddr);
				break;
			}
		}
		replaceParentKey(key, repKey);
	}

	public long search(int k) throws IOException {
		/*
		 * This is an equality search.
		 * 
		 * If the key is found return the address of the row with the key otherwise
		 * return 0.
		 */
		path = new LinkedList<Long>();
		return searchTree(k, root);
	}

	private long searchTree(int key, long treeAddr) throws IOException {
		if (treeAddr == 0) {
			return 0;
		}
		BTreeNode treeNode = new BTreeNode();
		treeNode.readNode(treeAddr);
		if (treeNode.count > 0) {
			// at non-leaf row/node
			for (int i = 0; i < treeNode.count; i++) {
				if (key < treeNode.keys[i]) {
					path.addFirst(treeAddr);
					return searchTree(key, treeNode.children[i]);
				}
			}
			path.addFirst(treeAddr);
			return searchTree(key, treeNode.children[treeNode.count]);
		} else {
			// at leaf node
			for (int j = 0; j < Math.abs(treeNode.count); j++) {
				if (key == treeNode.keys[j]) {
					path.addFirst(treeAddr);
					return treeNode.children[j];
				}
			}
			path.addFirst(treeAddr);
			return 0;
		}
	}

	LinkedList<Long> range;

	public LinkedList<Long> rangeSerach(int low, int high) throws IOException {
		// PRE: low <= high
		/*
		 * Return a list of row addresses for all keys in the range low to high
		 * inclusive. Return an empty list when no keys are in the range.
		 */
		range = new LinkedList<Long>();
		search(low);
		return range(low, high, path.poll());
	}

	private LinkedList<Long> range(int low, int high, long treeAddr) throws IOException {
		if (treeAddr == 0) {
			return range;
		}
		
		BTreeNode treeNode = new BTreeNode();
		treeNode.readNode(treeAddr);
		if (treeNode.keys[0] > high) {
			return range;
		}
		
		for (int i = 0; i < Math.abs(treeNode.count); i++) {
			if (treeNode.keys[i] >= low && treeNode.keys[i] <= high) {
				range.addLast(treeNode.children[i]);
			}
		}
		return range(low, high, treeNode.children[order - 1]);
	}

	public void print() throws IOException {
		// Print the B+Tree to standard output.
		// Print one node per line.
		
		//Only works for order 5 tree.
		f.seek(0);
		System.out.println(f.getFilePointer() + ":	" + f.readLong());
		System.out.println(f.getFilePointer() + ":	" + f.readLong());
		System.out.println(f.getFilePointer() + ":	" + f.readInt());
		while (f.getFilePointer() < f.length()) {
			System.out.print(f.getFilePointer() + ":	");
			for (int i = 0; i < order; i++) {
				System.out.print(f.readInt() + " ");
			}
			for (int i = 0; i < order; i++) {
				System.out.print(f.readLong() + " ");
			}
			System.out.println();
		}

	}

	/*
	 * Write out root and free. Close BTree file.
	 */
	public void close() throws IOException {
		// Close the B+Tree. The tree should not be accessed after closed is called.
		f.seek(0);
		f.writeLong(root);
		f.writeLong(free);
		f.close();
	}

}
