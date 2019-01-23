/**
 * @author Ethan Lor
 * CS 340 Homework 2
 * October 5, 2017
 * 
 * Implements a binary search tree of integers stored in a random access file.
 * Duplicates are recored by a count filed associated with the integer.
 */
import java.io.*;
import java.util.*;

public class BinarySearchTree {
	
	final int CREATE = 0;
	final int REUSE = 1;

	private RandomAccessFile f;
	long root; // The address of the root node in the file.
	long free; // The address of the file of the first node in the free list.

	private class Node {
		private long left;
		private int data;
		private int count;
		private long right;

		private Node(long l, int d, long r) {
		// constructor for a new node
			left = l;
			data = d;
			right = r;
			count = 1;
		}

		private Node(long addr) throws IOException {
		// constructor for a node that exists and is stored in the file
			f.seek(addr);
			data = f.readInt();
			count = f.readInt();
			left = f.readLong();
			right = f.readLong();
		}

		private void writeNode(long addr) throws IOException {
		// writes the node to the file location addr
			f.seek(addr);
			f.writeInt(data);
			f.writeInt(count);
			f.writeLong(left);
			f.writeLong(right);
		}
	}

	public BinarySearchTree(String fname, int mode) throws IOException {
	// if mode is CREATE a new empty file is created
	// if mode is CREATE and a file with the file name fname exists the file
	// with the fname must be
	// deleted before the new empty file is created.
	// if mode is REUSE an existing file is used if it exists otherwise a
	// new empty file is created
		File file = new File(fname);

		if (mode == CREATE) {
			if (file.createNewFile()) {
				//System.out.println("New File created!");
			} else {
				file.delete();
				file.createNewFile();
				//System.out.println("Existing file replaced!");
			}
			f = new RandomAccessFile(file, "rw");
			root = 0;
			free = 0;
			f.writeLong(root);
			f.writeLong(free);
		}

		if (mode == REUSE) {
			if (!file.exists()) {
				file.createNewFile();
				f = new RandomAccessFile(file, "rw");
				root = 0;
				free = 0;
				f.writeLong(root);
				f.writeLong(free);
				//System.out.println("New File created!");
			} else {
				f = new RandomAccessFile(file, "rw");
				root = f.readLong();
				free = f.readLong();
				//System.out.println("Existing file used!");
			}
		}
	}
	
	/**
	 * Returns the address of available free space.
	 * 
	 * @return long
	 * @throws IOException
	 */
	private long getFree() throws IOException {
		long addr = free;

		if (free != 0) {
			f.seek(free);
			free = f.readLong();
			return addr;
		} else {
			return f.length();
		}
	}
	
	/**
	 * Adds address of deleted nodes to free address space.
	 * 
	 * @param addr
	 * @throws IOException
	 */
	private void addFree(long addr) throws IOException {
		f.seek(addr);
		f.writeLong(free);
		free = addr;
	}

	public void insert(int d) throws IOException {
	// insert d into the tree
	// if d is in the tree increment the count field associated with d
		root = insert(root, d);
	}

	private long insert(long addr, int d) throws IOException {
		long curAddr = addr;
		Node curNode;

		if (curAddr == 0) {
			long freeAddr = getFree();
			Node newNode = new Node(0, d, 0);
			newNode.writeNode(freeAddr);
			return freeAddr;
		}

		curNode = new Node(curAddr);
		if (curNode.data > d) {
			curNode.left = insert(curNode.left, d);
			curNode.writeNode(curAddr);
		} else if (curNode.data < d) {
			curNode.right = insert(curNode.right, d);
			curNode.writeNode(curAddr);
		} else {
			curNode.count++;
			curNode.writeNode(curAddr);
		}
		return curAddr;
	}

	public int find(int d) throws IOException {
	// if d is in the tree return the value of count associated with d
	// otherwise return 0
		return find(root, d);
	}
	
	/**
	 * Finds node containing 'd' and returns its count.
	 * 
	 * @param addr
	 * @param d
	 * @return int
	 * @throws IOException
	 */
	private int find(long addr, int d) throws IOException {
		long nodeAddr = addr;
		Node curNode;
		
		if(nodeAddr == 0) {
			return 0;
		}
		
		curNode = new Node(nodeAddr);
		if (curNode.data > d) {
			return find(curNode.left, d);
		} else if (curNode.data < d) {
			return find(curNode.right, d);
		} else {
			return curNode.count;
		}
	}
	
	public void removeOne(int d) throws IOException {
	// remove one copy of d from the tree
	// if the copy is the last copy remove d from the tree
	// if d is not in the tree then method has no effect
		root = remove(root, d, 0);
	}
	
	public void removeAll(int d) throws IOException {
	// remove d from the tree
	// if d is not in the tree the method as no effect
		root = remove(root, d, 1);
	}

	/**
	 * Finds the node of interest and decrement its count or removes it completely
	 * from the list if count is zero or called from removeAll.
	 * 
	 * @param addr
	 * @param d
	 * @return long
	 * @throws IOException
	 */
	private long remove(long addr, int d, int remv) throws IOException {
		int removeAll = remv;
		long curAddr = addr;
		long retAddr = addr;
		Node curNode;

		if (curAddr == 0) {
			return 0;
		}

		curNode = new Node(curAddr);
		if (curNode.data > d) {
			curNode.left = remove(curNode.left, d, remv);
			curNode.writeNode(curAddr);
		} else if (curNode.data < d) {
			curNode.right = remove(curNode.right, d, remv);
			curNode.writeNode(curAddr);
		} else {
			curNode.count--;
			curNode.writeNode(curAddr);
			if (curNode.count == 0 || removeAll == 1) {
				if(curNode.left == 0) {
					retAddr = curNode.right;
					addFree(curAddr);
				} else if (curNode.right == 0) {
					retAddr = curNode.left;
					addFree(curAddr);
				} else {
					long left = replace(curNode.left, curAddr);
					curNode = new Node(curAddr);
					curNode.left = left;
					curNode.writeNode(curAddr);
				}
			}
		}
		return retAddr;
	}
	
	private long replace(long addr, long rAddr) throws IOException {
		long curAddr = addr;
		long repAddr = rAddr;
		Node curNode = new Node(curAddr);
		Node repNode = new Node(repAddr);
		if(curNode.right != 0) {
			curNode.right = replace(curNode.right, repAddr);
			curNode.writeNode(curAddr);
			return curAddr;
		} else {
			repNode.data = curNode.data;
			repNode.count = curNode.count;
			repNode.writeNode(repAddr);
			long left = curNode.left;
			addFree(curAddr);
			return left;
		}
	}

	public void close() throws IOException {
	// close the random access file
	// before closing update the values of root and the free if necessary
		f.seek(0);
		f.writeLong(root);
		f.writeLong(free);
		f.close();
	}
	
	/**
	 * Prints the table representation of the random access file.
	 * 
	 * @throws IOException
	 */
	public void printTable() throws IOException {
		f.seek(0);
		System.out.print(f.getFilePointer() + "		");
		System.out.println(f.readLong() + "		");
		System.out.print(f.getFilePointer() + "		");
		System.out.println(f.readLong() + "		");
		while (f.getFilePointer() < f.length()) {
			System.out.print(f.getFilePointer() + "		");
			System.out.print(f.readInt() + "		");
			System.out.print(f.readInt() + "		");
			System.out.print(f.readLong() + "		");
			System.out.println(f.readLong() + "		");
		}
	}
	
	/*
	 * Print method.
	 */
	public void printTree() throws IOException {
		printInOrder(root);
	}
	
	/*
	 * Traverses the random access file binary tree and prints in ascending order.
	 */
	private void printInOrder(long root) throws IOException {
		Node curNode;
		if(root != 0) {
			curNode = new Node(root);
			printInOrder(curNode.left);
			System.out.print(curNode.data + " ");
			printInOrder(curNode.right);
		}
	}
	
	public static void main(String[] args) {
		try {
			BinarySearchTree bst0 = new BinarySearchTree("BinarySearchTree.txt", 0);
			System.out.println("Created a blank file.");
			bst0.insert(100);
			bst0.insert(200);
			bst0.insert(50);
			bst0.insert(55);
			bst0.insert(60);
			bst0.close();

			BinarySearchTree bst0open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst0open.printTable();
			System.out.print("Ordered array: ");
			bst0open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst0open.close();

			BinarySearchTree bst1 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Insert 100 to empty tree.");
			bst1.removeOne(100);
			bst1.close();

			BinarySearchTree bst1open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst1open.printTable();
			System.out.print("Ordered array: ");
			bst1open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst1open.close();
			
			BinarySearchTree bst2 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Insert 200.");
			bst2.insert(200);
			bst2.close();

			BinarySearchTree bst2open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst2open.printTable();
			System.out.print("Ordered array: ");
			bst2open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst2open.close();
			
			BinarySearchTree bst3 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Remove 100 root.");
			bst3.removeOne(100);
			bst3.close();

			BinarySearchTree bst3open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst3open.printTable();
			System.out.print("Ordered array: ");
			bst3open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst3open.close();
			
			BinarySearchTree bst4 = new BinarySearchTree("BinarySearchTree.txt", 0);
			System.out.println("New binary tree.");
			bst4.insert(100);
			bst4.insert(50);
			bst4.close();

			BinarySearchTree bst4open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst4open.printTable();
			System.out.print("Ordered array: ");
			bst4open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst4open.close();
			
			BinarySearchTree bst5 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Remove 100 root.");
			bst5.removeOne(100);
			bst5.close();

			BinarySearchTree bst5open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst5open.printTable();
			System.out.print("Ordered array: ");
			bst5open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst5open.close();
			
			BinarySearchTree bst6 = new BinarySearchTree("BinarySearchTree.txt", 0);
			System.out.println("New binary tree.");
			bst6.insert(100);
			bst6.insert(50);
			bst6.insert(200);
			bst6.close();

			BinarySearchTree bst6open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst6open.printTable();
			System.out.print("Ordered array: ");
			bst6open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst6open.close();
			
			BinarySearchTree bst7 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Remove 100 root.");
			bst7.removeOne(100);
			bst7.close();

			BinarySearchTree bst7open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst7open.printTable();
			System.out.print("Ordered array: ");
			bst7open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst7open.close();
			
			BinarySearchTree bst8 = new BinarySearchTree("BinarySearchTree.txt", 0);
			System.out.println("Insert 100.");
			bst8.insert(100);
			bst8.close();

			BinarySearchTree bst8open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst8open.printTable();
			System.out.print("Ordered array: ");
			bst8open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst8open.close();
			
			BinarySearchTree bst9 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Remove 100 root.");
			bst9.removeAll(100);
			bst9.close();

			BinarySearchTree bst9open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst9open.printTable();
			System.out.print("Ordered array: ");
			bst9open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst9open.close();
			
			BinarySearchTree bst10 = new BinarySearchTree("BinarySearchTree.txt", 0);
			System.out.println("New Binary Tree.");
			bst10.insert(100);
			bst10.insert(50);
			bst10.insert(50);
			bst10.insert(200);
			bst10.insert(30);
			bst10.insert(60);
			bst10.insert(300);
			bst10.insert(20);
			bst10.insert(40);
			bst10.insert(250);
			bst10.insert(400);
			bst10.insert(45);
			bst10.close();

			BinarySearchTree bst10open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst10open.printTable();
			System.out.print("Ordered array: ");
			bst10open.printTree();
			System.out.println();
			System.out.println("Find 50: " + bst10open.find(50));
			System.out.println("Find 99: " + bst10open.find(99));
			System.out.println("--------------------------------------------------------------------------------------------");
			bst10open.close();
			
			BinarySearchTree bst11 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Remove one 100 root.");
			bst11.removeOne(100);
			bst11.close();

			BinarySearchTree bst11open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst11open.printTable();
			System.out.print("Ordered array: ");
			bst11open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst11open.close();
			
			BinarySearchTree bst12 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Remove all 50.");
			bst12.removeAll(50);
			bst12.close();

			BinarySearchTree bst12open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst12open.printTable();
			System.out.print("Ordered array: ");
			bst12open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst12open.close();
			
			BinarySearchTree bst13 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Insert 10.");
			bst13.insert(10);
			bst13.close();

			BinarySearchTree bst13open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst13open.printTable();
			System.out.print("Ordered array: ");
			bst13open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst13open.close();
			
			BinarySearchTree bst14 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Remove 30.");
			bst14.removeOne(30);
			bst14.close();

			BinarySearchTree bst14open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst14open.printTable();
			System.out.print("Ordered array: ");
			bst14open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst14open.close();
			
			BinarySearchTree bst15 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Insert 15 and 16.");
			bst15.insert(15);
			bst15.insert(16);
			bst15.insert(15);
			bst15.insert(16);
			bst15.close();

			BinarySearchTree bst15open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst15open.printTable();
			System.out.print("Ordered array: ");
			bst15open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst15open.close();
			
			BinarySearchTree bst16 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Remove 20.");
			bst16.removeOne(20);
			bst16.close();

			BinarySearchTree bst16open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst16open.printTable();
			System.out.print("Ordered array: ");
			bst16open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst16open.close();
			
			BinarySearchTree bst17 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Remove 300.");
			bst17.removeOne(300);
			bst17.close();

			BinarySearchTree bst17open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst17open.printTable();
			System.out.print("Ordered array: ");
			bst17open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst17open.close();
			
			BinarySearchTree bst18 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Remove one 16 and then 30.");
			bst18.removeOne(16);
			bst18.removeOne(30);
			bst18.close();

			BinarySearchTree bst18open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst18open.printTable();
			System.out.print("Ordered array: ");
			bst18open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst18open.close();
			
			BinarySearchTree bst19 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Remove 16 and then 60.");
			bst19.removeOne(16);
			bst19.removeOne(60);
			bst19.close();

			BinarySearchTree bst19open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst19open.printTable();
			System.out.print("Ordered array: ");
			bst19open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst19open.close();
			
			BinarySearchTree bst20 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Remove 250 then 400 and then 200.");
			bst20.removeAll(250);
			bst20.removeAll(400);
			bst20.removeAll(200);
			bst20.close();

			BinarySearchTree bst20open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst20open.printTable();
			System.out.print("Ordered array: ");
			bst20open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst20open.close();
			
			BinarySearchTree bst21 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Remove 45 root.");
			bst21.removeOne(45);
			bst21.close();

			BinarySearchTree bst21open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst21open.printTable();
			System.out.print("Ordered array: ");
			bst21open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst21open.close();
			
			BinarySearchTree bst22 = new BinarySearchTree("BinarySearchTree.txt", 0);
			System.out.println("Ramdon Binary Tree.");
			for(int i = 0; i < 25; i++) {
				bst22.insert((int)(Math.random()*100)+1);
			}
			bst22.close();

			BinarySearchTree bst22open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst22open.printTable();
			System.out.print("Ordered array: ");
			bst22open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst22open.close();
			
			BinarySearchTree bst23 = new BinarySearchTree("BinarySearchTree.txt", 1);
			System.out.println("Binary Tree Random DeleteAll.");
			for(int i = 0; i < 25; i++) {
				bst23.removeAll((int)(Math.random()*100)+1);
			}
			bst23.close();

			BinarySearchTree bst23open = new BinarySearchTree("BinarySearchTree.txt", 1);
			bst23open.printTable();
			System.out.print("Ordered array: ");
			bst23open.printTree();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------");
			bst23open.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
