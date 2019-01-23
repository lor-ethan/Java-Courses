//Ethan Lor
//Assignment 1 CS 340

import java.util.*;

public class PriorityQueue {
	// Implements a d-heap bases on priority queue.
	// The priority is an int (low value is high priority).
	// Associated with each priority is an object.

	private class Item {
		private int priority;
		private Object data;

		private Item(int p, Object d) {
			priority = p;
			data = d;
		}
	}

	private Item queue[];
	private int order;
	private int size;

	public PriorityQueue(int ord, int s) {
		queue = new Item[s];
		order = ord;
		size = 0;
	}

	public int getPriority() {
		// PRE: !empty()
		// Return the Highest priority value in the queue.
		return queue[0].priority;
	}

	public Object getData() {
		// PRE: !empty()
		// Return the data Object associated with the highest priority,
		return queue[0].data;
	}
                                                                                        
	public void remove() {
		// PRE: !empty()
		// Remove the item with the highest priority in the queue.
		Item temp = queue[size-1];
		size--;
		int parent = 0;
		if(empty()) {
			queue[0] = null;
			return;
		}
		while(parent <= (size-1)/order){  //parent with no children check
			int leastChild = getChild(parent);
			if(temp.priority > queue[leastChild].priority) {
				queue[parent] = queue[leastChild];
				parent = leastChild;
			} else {
				queue[parent] = temp;
				return;
			}
		}
		queue[parent] = temp;
	}
	
	private int getChild(int parent) {
		//Returns the smallest child index of the parent node provided.
		int index = parent*order+1;
		int i = 2;
		while(parent*order+i <= size-1 && i <= order){
			if(queue[parent*order+i].priority < queue[index].priority) {
				index = parent*order+i;
			}
			i++;
		}
		return index;
	}

	public int getSize() {
		// Return the number of items in the queue.
		return size;
	}

	public boolean full() {
		if(size == queue.length) {
			return true;
		} 
		return false;
	}

	public boolean empty() {
		if(size == 0) {
			return true;
		}
		return false;
	}

	public void insert(int p, Object d) {
		// PRE: !full()
		// Insert a new item into the queue with priority p and associated data d.	
		Item insert = new Item(p,d);
		size++;
		int child = size-1;
		while(child > 0) {
			if(queue[(child-1)/order].priority > p){
				queue[child] = queue[(child-1)/order];
				child = (child-1)/order;
			} else {
				break;
			}
		}
		queue[child] = insert;
	}

	public static void main(String[] args) {
		//All test cases assumed queues were not empty or full. In practice queues should be 
		//checked before inserting or deleting using the methods provided and should ideally 
		//be placed in try-catch blocks or throw exceptions.
		PriorityQueue dHeapForward = new PriorityQueue(3,21);
		dHeapForward.insert(1,2);
		dHeapForward.insert(3,6);
		dHeapForward.insert(2,4);
		dHeapForward.insert(4,8);
		dHeapForward.insert(5,10);
		dHeapForward.insert(6,12);
		dHeapForward.insert(7,14);
		dHeapForward.insert(8,16);
		dHeapForward.insert(9,16);
		dHeapForward.insert(10,20);
		dHeapForward.insert(11,22);
		dHeapForward.insert(12,24);
		dHeapForward.insert(13,26);
		dHeapForward.insert(14,28);
		dHeapForward.insert(15,30);
		dHeapForward.insert(16,32);
		dHeapForward.insert(17,34);
		dHeapForward.insert(18,36);
		dHeapForward.insert(19,38);
		dHeapForward.insert(20,40);
		dHeapForward.insert(21,41);
		
		//Checks getters
		System.out.println("Heap size? " + dHeapForward.getSize());
		System.out.println("Is empty? " + dHeapForward.empty());
		System.out.println("Is full? " + dHeapForward.full());
		System.out.println("First priority in queue is " + dHeapForward.getPriority() + ".");
		System.out.println("Data associate with first priority in queue is " + dHeapForward.getData() + ".\n");
		
		System.out.println("Inserted In order 4-heap.");
		for(int i = 0; i <= dHeapForward.size-1; i++) {
			System.out.print(dHeapForward.queue[i].priority + " ");
		}
		System.out.println("\n");
		
		System.out.println("In order 4-heap remove.");
		dHeapForward.remove();
		for(int i = 0; i <= dHeapForward.size-1; i++) {
			System.out.print(dHeapForward.queue[i].priority + " ");
		}
		System.out.println("\n");
		
		System.out.println("In order 4-heap insert after remove.");
		dHeapForward.insert(1,2);
		for(int i = 0; i <= dHeapForward.size-1; i++) {
			System.out.print(dHeapForward.queue[i].priority + " ");
		}
		System.out.println("\n");
		
		PriorityQueue dHeapBackwards = new PriorityQueue(4,21);
		dHeapBackwards.insert(21,42);
		dHeapBackwards.insert(20,40);
		dHeapBackwards.insert(19,38);
		dHeapBackwards.insert(18,36);
		dHeapBackwards.insert(17,34);
		dHeapBackwards.insert(16,32);
		dHeapBackwards.insert(15,30);
		dHeapBackwards.insert(14,28);
		dHeapBackwards.insert(13,26);
		dHeapBackwards.insert(12,24);
		dHeapBackwards.insert(11,22);
		dHeapBackwards.insert(10,20);
		dHeapBackwards.insert(9,18);
		dHeapBackwards.insert(8,16);
		dHeapBackwards.insert(7,14);
		dHeapBackwards.insert(6,12);
		dHeapBackwards.insert(5,10);
		dHeapBackwards.insert(4,8);
		dHeapBackwards.insert(3,6);
		dHeapBackwards.insert(2,4);
		dHeapBackwards.insert(1,2);
		
		System.out.println("Inserted in Reverse order 4-heap.");
		for(int i = 0; i <= dHeapBackwards.size-1; i++) {
			System.out.print(dHeapBackwards.queue[i].priority + " ");
		}
		System.out.println("\n");
		
		System.out.println("Reverse order 4-heap remove.");
		dHeapBackwards.remove();
		for(int i = 0; i <= dHeapBackwards.size-1; i++) {
			System.out.print(dHeapBackwards.queue[i].priority + " ");
		}
		System.out.println("\n");
		
		System.out.println("Reverse order 4-heap insert after remove.");
		dHeapBackwards.insert(1,2);
		for(int i = 0; i <= dHeapBackwards.size-1; i++) {
			System.out.print(dHeapBackwards.queue[i].priority + " ");
		}
		System.out.println("\n");

		PriorityQueue dHeapRandom = new PriorityQueue(4,21);
		dHeapRandom.insert(14,28);
		dHeapRandom.insert(4,8);
		dHeapRandom.insert(9,18);
		dHeapRandom.insert(17,34);
		dHeapRandom.insert(7,14);
		dHeapRandom.insert(11,22);
		dHeapRandom.insert(8,16);
		dHeapRandom.insert(19,38);
		dHeapRandom.insert(18,36);
		dHeapRandom.insert(6,12);
		dHeapRandom.insert(21,42);
		dHeapRandom.insert(12,24);
		dHeapRandom.insert(1,2);
		dHeapRandom.insert(15,30);
		dHeapRandom.insert(3,6);
		dHeapRandom.insert(13,26);
		dHeapRandom.insert(20,40);
		dHeapRandom.insert(2,4);
		dHeapRandom.insert(5,10);
		dHeapRandom.insert(10,20);
		dHeapRandom.insert(16,32);
		
		System.out.println("Inserted in Random order 4-heap.");
		for(int i = 0; i <= dHeapRandom.size-1; i++) {
			System.out.print(dHeapRandom.queue[i].priority + " ");
		}
		System.out.println("\n");
		
		System.out.println("Random order 4-heap remove.");
		dHeapRandom.remove();
		for(int i = 0; i <= dHeapRandom.size-1; i++) {
			System.out.print(dHeapRandom.queue[i].priority + " ");
		}
		System.out.println("\n");
		
		System.out.println("Random order 4-heap insert after remove.");
		dHeapRandom.insert(1,2);
		for(int i = 0; i <= dHeapRandom.size-1; i++) {
			System.out.print(dHeapRandom.queue[i].priority + " ");
		}
		System.out.println("\n");
		
		int size = (int)(Math.random()*31)+1;
		System.out.println("Random array size is " + size + ".");
		PriorityQueue dHeapTruelyRandom = new PriorityQueue((int)(Math.random()*9)+2,size);
		for(int i = 0; i < size; i++) {
			dHeapTruelyRandom.insert(((int)(Math.random()*99)+1), ((int)(Math.random()*99)+1));
		}
		
		System.out.println("Random d/order of " + dHeapTruelyRandom.order + ".");
		
		System.out.println("Truely Random insert order d-heap.");
		for(int i = 0; i <= dHeapTruelyRandom.size-1; i++) {
			System.out.print(dHeapTruelyRandom.queue[i].priority + " ");
		}
		System.out.println("\n");
		
		System.out.println("Truely Random order d-heap remove.");
		dHeapTruelyRandom.remove();
		dHeapTruelyRandom.remove();
		dHeapTruelyRandom.remove();
		for(int i = 0; i <= dHeapTruelyRandom.size-1; i++) {
			System.out.print(dHeapTruelyRandom.queue[i].priority + " ");
		}
		System.out.println("\n");
		
		System.out.println("Truely Random order d-heap insert after remove.");
		dHeapTruelyRandom.insert(((int)(Math.random()*99)+1), ((int)(Math.random()*99)+1));
		for(int i = 0; i <= dHeapTruelyRandom.size-1; i++) {
			System.out.print(dHeapTruelyRandom.queue[i].priority + " ");
		}
		System.out.println("\n");
		
		PriorityQueue dHeap = new PriorityQueue(3,21);
		dHeap.insert(14,28);
		dHeap.insert(4,8);
		dHeap.insert(9,18);
		dHeap.insert(17,34);
		dHeap.insert(7,14);
		dHeap.insert(11,22);
		dHeap.insert(8,16);
		dHeap.insert(19,38);
		dHeap.insert(18,36);
		dHeap.insert(6,12);
		dHeap.insert(21,42);
		dHeap.insert(12,24);
		dHeap.insert(1,2);
		dHeap.insert(15,30);
		dHeap.insert(3,6);
		dHeap.insert(13,26);
		dHeap.insert(20,40);
		dHeap.insert(2,4);
		dHeap.insert(5,10);
		dHeap.insert(10,20);
		dHeap.insert(16,32);
		//Checks getters
		System.out.println("Heap size? " + dHeap.getSize());
		System.out.println("Is empty? " + dHeap.empty());
		System.out.println("Is full? " + dHeap.full());
		if(!dHeap.empty()) {
			System.out.println("First priority in queue is " + dHeap.getPriority() + ".");
			System.out.println("Data associate with first priority in queue is " + dHeap.getData() + ".\n");
		}
		System.out.println("dheap.");
		for(int i = 0; i <= dHeap.size-1; i++) {
			System.out.print(dHeap.queue[i].priority + " ");
		}
		System.out.println("\n");
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		dHeap.remove();
		//Checks getters
		System.out.println("Heap size? " + dHeap.getSize());
		System.out.println("Is empty? " + dHeap.empty());
		System.out.println("Is full? " + dHeap.full());
		if(!dHeap.empty()) {
			System.out.println("First priority in queue is " + dHeap.getPriority() + ".");
			System.out.println("Data associate with first priority in queue is " + dHeap.getData() + ".\n");
		}
		System.out.println("dheap.");
		for(int i = 0; i <= dHeap.size-1; i++) {
			System.out.print(dHeap.queue[i].priority + " ");
		}
		System.out.println("\n");
		dHeap.insert(1,2);
		dHeap.insert(3,6);
		dHeap.insert(2,4);
		dHeap.insert(4,8);
		dHeap.insert(5,10);
		dHeap.insert(6,12);
		dHeap.insert(7,14);
		dHeap.insert(8,16);
		dHeap.insert(9,16);
		dHeap.insert(10,20);
		dHeap.insert(11,22);
		dHeap.insert(12,24);
		dHeap.insert(13,26);
		dHeap.insert(14,28);
		dHeap.insert(15,30);
		dHeap.insert(16,32);
		dHeap.insert(17,34);
		dHeap.insert(18,36);
		dHeap.insert(19,38);
		dHeap.insert(20,40);
		dHeap.insert(21,41);
		
		//Checks getters
		System.out.println("Heap size? " + dHeap.getSize());
		System.out.println("Is empty? " + dHeap.empty());
		System.out.println("Is full? " + dHeap.full());
		if(!dHeap.empty()) {
			System.out.println("First priority in queue is " + dHeap.getPriority() + ".");
			System.out.println("Data associate with first priority in queue is " + dHeap.getData() + ".\n");
		}
		
		System.out.println("dheap.");
		for(int i = 0; i <= dHeap.size-1; i++) {
			System.out.print(dHeap.queue[i].priority + " ");
		}
		System.out.println("\n");
		
		PriorityQueue p1 = new PriorityQueue(5, 100);
		PriorityQueue p2 = new PriorityQueue(6, 100);
		PriorityQueue p3 = new PriorityQueue(7, 100);

		int p = -1; //pointless initialization to keep the compiler happy
		p1.insert(0, new Integer(0));
		System.out.println("First insert");

		for (int i = 1; i < 100; i++)
			p1.insert(i, new Integer(i));

		for (int i = 0; i < 100; i++)
			p2.insert(i, new Integer(i));

		for (int i = 0; i < 100; i++)
			p3.insert(i, new Integer(i));

		System.out.println("First insert tests");

		System.out.print(p1.getPriority()+",");
		while (!p1.empty()) {
			p = p1.getPriority();
			Object d = p1.getData();
			p1.remove();
		}
		System.out.println(p);

		System.out.print(p2.getPriority()+",");

		while (!p2.empty()) {
			p = p2.getPriority();
			Object d = p2.getData();
			p2.remove();
		}
		System.out.println(p);

		System.out.print(p3.getPriority()+",");

		while (!p3.empty()) {
			p = p3.getPriority();
			Object d = p3.getData();
			p3.remove();
		}
		System.out.println(p);
		System.out.println("First Remove Test");

		for (int i = 100; i > 0 ; i--)
			p1.insert(i, new Integer(i));

		for (int i = 100; i > 0 ; i--)
			p2.insert(i, new Integer(i));

		for (int i = 100; i > 0 ; i--)
			p3.insert(i, new Integer(i));

		System.out.println("Second insert tests");

		System.out.print(p1.getPriority()+",");
		while (!p1.empty()) {
			p = p1.getPriority();
			Object d = p1.getData();
			p1.remove();
		}
		System.out.println(p);

		System.out.print(p2.getPriority()+",");

		while (!p2.empty()) {
			p = p2.getPriority();
			Object d = p2.getData();
			p2.remove();
		}
		System.out.println(p);

		System.out.print(p3.getPriority()+",");

		while (!p3.empty()) {
			p = p3.getPriority();
			Object d = p3.getData();
			p3.remove();
		}
		System.out.println(p);
		System.out.println("Second Remove Test");

		Random r1 = new Random(1000);
		while (!p3.full()) {
			p = r1.nextInt(200);
			System.out.print(p+",");
			p3.insert(p, new Integer(p));
		}
		System.out.println();

		while (!p3.empty()) {
			System.out.print(p3.getPriority()+",");
			Object d = p3.getData();
			p3.remove();
		}
		System.out.println();
		System.out.println("Third Remove Test");
	}
}