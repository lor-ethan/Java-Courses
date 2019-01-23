/*
 * Ethan Lor
 * CS 340
 * Homework 5 B+Tree
 * 
 * November 22, 2017
 */
import java.util.*;
import java.io.*;

public class DBTable {

	RandomAccessFile rows; // The file that stores the rows in the table.
	long free; // Head of the free list for rows.
	int numOtherFields;
	int otherFieldLengths[];
	// Add other instance variables as needed.
	BTree BT; // The associated binary tree.
	long freePointer;

	private class Row {
		private int keyField;
		private char otherFields[][];
		/*
		 * Each row consists of unique key and one or more character array fields. Each
		 * character array field is a fixed length field (for example 10 characters).
		 * Each field can have a different length. Fields are padded with null
		 * characters so a field with a length of x characters always uses space for x
		 * characters.
		 */

		// Constructors and other Row methods.
		private Row(long addr) throws IOException {
			rows.seek(addr);
			keyField = rows.readInt();
			otherFields = new char[numOtherFields][];
			for (int i = 0; i < numOtherFields; i++) {
				otherFields[i] = new char[otherFieldLengths[i]];
			}

			for (int i = 0; i < numOtherFields; i++) {
				for (int j = 0; j < otherFieldLengths[i]; j++) {
					otherFields[i][j] = rows.readChar();
				}
			}
		}
	}

	public DBTable(String filename, int fL[], int bsize) throws IOException {
		/*
		 * Use this constructor to create a new DBTable.
		 * 
		 * filename is the name of the file used to store the table fL is the lengths of
		 * the otherFields fL.length indicates how many other fields are part of the row
		 * bsize is the block size. It is used to calculate the order of the B+Tree
		 * 
		 * A B+Tree must be created for the key field in the table
		 * 
		 * If a file with the name filename exists, the file should be deleted before
		 * the new file is created.
		 */
		newTable(filename, fL, bsize);
	}

	/*
	 * Create a new table and file.
	 */
	private void newTable(String filename, int fL[], int bsize) throws IOException {
		File file = new File(filename);
		if (file.createNewFile()) {
			// No existing file. New file created.
			BT = new BTree(filename + ".index", bsize);
		} else {
			file.delete();
			file.createNewFile();
			BT = new BTree(filename + ".index", bsize);
		}
		
		free = 0;
		numOtherFields = fL.length;
		otherFieldLengths = new int[numOtherFields];
		
		for (int i = 0; i < fL.length; i++) {
			otherFieldLengths[i] = fL[i];
		}
		
		rows = new RandomAccessFile(file, "rw");
		rows.seek(0);
		rows.writeInt(numOtherFields);
		
		for (int i = 0; i < otherFieldLengths.length; i++) {
			rows.writeInt(fL[i]);
		}
		
		freePointer = rows.getFilePointer();
		rows.writeLong(free);
	}

	public DBTable(String filename) throws IOException {
		// Use this constructor to open an existing DBTable.
		openTable(filename);
	}

	/*
	 * Open an existing table file.
	 */
	private void openTable(String filename) throws IOException {
		rows = new RandomAccessFile(new File(filename), "rw");
		BT = new BTree(filename + ".index");
		rows.seek(0);
		numOtherFields = rows.readInt();
		otherFieldLengths = new int[numOtherFields];
		
		for (int i = 0; i < otherFieldLengths.length; i++) {
			otherFieldLengths[i] = rows.readInt();
		}
		
		freePointer = rows.getFilePointer();
		free = rows.readLong();
	}

	public boolean insert(int key, char fields[][]) throws IOException {
		// PRE: the length of each row is fields matches the expected length.
		/*
		 * If a row with the key is not in the table, the row is added and the method
		 * returns true, otherwise the row is not added and the method returns false.
		 * 
		 * The method must use the B+tree to determine if a row with the keys exists.
		 * 
		 * If the row is added the key is also added into the B+Tree.
		 */
		long freeAddr;
		if(free == 0) {
			freeAddr = rows.length();
		} else {
			freeAddr = free;
		}
		
		if(BT.insert(key, freeAddr)) {
			insertRow(key, fields, getFree());
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Write a row into the table.
	 */
	private void insertRow(int key, char fields[][], long addr) throws IOException {
		rows.seek(addr);
		rows.writeInt(key);
		
		for (int i = 0; i < numOtherFields; i++) {
			for (int j = 0; j < otherFieldLengths[i]; j++) {
				rows.writeChar(fields[i][j]);
			}
		}
	}

	/*
	 * Get the next free address that can be written to.
	 */
	private long getFree() throws IOException {
		if (free == 0) {
			return rows.length();
		} else {
			long avaliable = free;
			rows.seek(free);
			free = rows.readLong();
			return avaliable;
		}
	}

	/*
	 * Add an address to the free list.
	 */
	private void addFree(long tableAddr) throws IOException {
		rows.seek(tableAddr);
		rows.writeLong(free);
		free = tableAddr;
	}

	public boolean remove(int key) throws IOException {
		/*
		 * If a row with the key is in the table it is removed and true is returned
		 * otherwise false is returned.
		 * 
		 * The method must use the B+Tree to determine if a row with the key exists.
		 * 
		 * If the row is deleted the key must be deleted from the B+Tree.
		 */
		return removeKey(key);
	}

	/*
	 * Remove a row contain a key from the table file.
	 */
	private boolean removeKey(int key) throws IOException {
		long tableAddr = BT.remove(key);
		if (tableAddr == 0) {
			return false;
		} else {
			addFree(tableAddr);
			return true;
		}
	}

	public LinkedList<String> search(int key) throws IOException {
		/*
		 * If a row with the key is found in the table return a list of other fields in
		 * the row. The string values in the list should not include the null
		 * characters.
		 * 
		 * If a row with the key is not found return an empty list.
		 * 
		 * The method must use the equality search in B+Tree.
		 */
		return find(key);
	}

	/*
	 * If key is found will return a list of other fields in the table row.
	 */
	private LinkedList<String> find(int key) throws IOException {
		LinkedList<String> fields = new LinkedList<String>();
		long addr = BT.search(key);
		if (addr != 0) {
			Row tableRow = new Row(addr);
			for (int i = 0; i < tableRow.otherFields.length; i++) {
				String retString = "";
				for (int j = 0; j < tableRow.otherFields[i].length; j++) {
					if (tableRow.otherFields[i][j] == 0) {
						break;
					}
					retString = retString + tableRow.otherFields[i][j];
				}
				fields.addFirst(retString);
			}
		}
		return fields;
	}

	LinkedList<LinkedList<String>> keysRange;
	public LinkedList<LinkedList<String>> rangeSearch(int low, int high) throws IOException {
		// PRE: low <= high
		/*
		 * For each row with a key that is in the range low to high inclusive, a list of
		 * the fields in the row is added to the list returned by the call.
		 * 
		 * If there are no rows with a key in the range return an empty list.
		 * 
		 * The method must use the range search in B+Tree.
		 */

		// Get the addresses from BTree and then find them in table
		LinkedList<Long> tableAddrs = BT.rangeSerach(low, high);
		keysRange = new LinkedList<LinkedList<String>>();
		return rangeSearch(tableAddrs);
	}

	private LinkedList<LinkedList<String>> rangeSearch(LinkedList<Long> tableAddrs) throws IOException {
		for (long addr : tableAddrs) {
			rows.seek(addr);
			LinkedList<String> dataStrings = new LinkedList<String>();
			for (int i = 0; i < numOtherFields; i++) {
				if (i == 0) {
					dataStrings.addLast(Integer.toString(rows.readInt()));
				}
				String data = "";
				for (int j = 0; j < otherFieldLengths[i]; j++) {
					char character = rows.readChar();
					if (character != 0) {
						// rows.seek(rows.getFilePointer() + j + 1);
						// break;
						data = data + character;
					}
				}
				dataStrings.addLast(data);
			}
			keysRange.addLast(dataStrings);
		}
		return keysRange;
	}

	public void print() throws IOException {
		// Print the rows to standard output in ascending order (based on the keys).
		// One row per line.

		// Currently only written to write an table with only 2 fields.
		rows.seek(0);
		System.out.println(rows.getFilePointer() + ":	" + rows.readInt());
		System.out.println(rows.getFilePointer() + ":	" + rows.readInt());
		System.out.println(rows.getFilePointer() + ":	" + rows.readInt());
		System.out.println(rows.getFilePointer() + ":	" + rows.readLong());
		while (rows.getFilePointer() < rows.length()) {
			System.out.print(rows.getFilePointer() + ":	");
			System.out.print(rows.readInt() + " ");
			for (int i = 0; i < otherFieldLengths[0]; i++) {
				System.out.print(rows.readChar());
			}
			System.out.print(" ");
			for (int i = 0; i < otherFieldLengths[1]; i++) {
				System.out.print(rows.readChar());
			}
			System.out.println();
		}

	}

	/*
	 * Write out free. Close DBTable file and call method to close BTree file.
	 */
	public void close() throws IOException {
		// Close the DBTable. The table should not be used after it is closed.
		rows.seek(freePointer);
		rows.writeLong(free);
		rows.close();
		BT.close(); // DBTable controls B+Tree
	}

}
