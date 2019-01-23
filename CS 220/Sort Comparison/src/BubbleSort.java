// implements the bubble sort algorithm

public class BubbleSort {

	public void bubbleSort(int [] ar) {
		boolean swapped = true;
		int k=1; // count the number of passes
		while(swapped) {
			swapped = false; // assume no out of order elements until we find one
			for(int i=0; i<ar.length-k; i++) {
				if(ar[i] > ar[i+1]) {
					// found an out of order pair
					swap(ar, i, i+1);
					swapped = true;
				}
			}
			k++;
		}
	}
	
	void swap(int[] ar, int i, int j) {
		int tmp = ar[i];
		ar[i] = ar[j];
		ar[j] = tmp;
	}
	
	public BubbleSort() {
		// test bubble sort sort 
		
		int [] ar = new int[50];
		
		for(int i=0; i<ar.length; i++) {
			ar[i] = (int)(1 + 100*Math.random());
		}
		
		bubbleSort(ar);
		
		for(int i=0; i<ar.length; i++) {
			System.out.println(ar[i]);
		} 
	}
	
	public static void main(String[] args) {
		new BubbleSort();
	}
}
