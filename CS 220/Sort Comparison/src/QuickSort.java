
public class QuickSort {
	
	// partitions ar[left...right] and returns an integer i so that 
	// for some value p, ar[left...(i-1)] <= p and ar[i...right] >=p
	int partition(int[] ar, int left, int right) {
		int i=left, j=right;
		int p = ar[ left + (right-left)/2 ]; // get middle value in array
		
		while(i<=j) {
			
			while(ar[i]<p) {
				i++;
			}
			
			while(ar[j]>p) {
				j--;
			}
			
			// i is now at a position >=p
			// j is now at a position <=p
			if( i<=j ) {
				// i and j haven't completely crossed paths yet
				swap(ar, i, j); 
				i++;
				j--;
			}
		}
		
		return i;
	}
	
	// helper method for quickSort
	private void quickSort(int [] ar, int low, int high) {
		
		if(low >= high) {
			return; // there's nothing left to sort!
		}
		
		int i = partition(ar, low, high);
		quickSort(ar, low, i-1);
		quickSort(ar, i, high);
	}
	
	// wrapper method for quickSort
	public void quickSort(int [] ar) {
		quickSort(ar, 0, ar.length-1);
	}
	
	void swap(int [] ar, int i, int j) {
		int tmp = ar[i];
		ar[i] = ar[j];
		ar[j] = tmp;
	}
	
	/*
	public void quickSort()
	*/
	
	public QuickSort() {
		// test quick sort sort 
		
		int [] ar = new int[50];
		
		for(int i=0; i<ar.length; i++) {
			ar[i] = (int)(1 + 100*Math.random());
		}
		
		quickSort(ar);
		
		for(int i=0; i<ar.length; i++) {
			System.out.println(ar[i]);
		} 
	}
	
	public static void main(String[] args) {
		new QuickSort();
	}
}
