// MergeSort.java
// Implementation of the Merge Sort Algorithm

public class MergeSort {

	// assuming both left and right are already sorted, merge returns
	// a new sorted array containing the combined contents of left and right
	public int[] merge(int[] left, int[] right) {
		
		// the combined array
		int[] comb = new int[left.length + right.length];
		
		int l=0, r=0, k=0; // indices into left, right and comb
		
		// merge the two arrays into comb
		while(l < left.length && r < right.length) {
			if(left[l] < right[r]) {
				comb[k++] = left[l++];
			} else {
				comb[k++] = right[r++];
			}
		}
		
		// copy any remaining elements in left
		while(l < left.length) {
			comb[k++] = left[l++];
		}
		
		// copy any remaining elements in right
		while(r < right.length) {
			comb[k++] = right[r++];
		}
		return comb;
	}
	
	// return the first half and second half of ar as separate arrays
	int[][] split(int [] ar) {
		
		int[][] halves = new int[2][];
		halves[0] = new int[ar.length/2]; // for left half
		halves[1] = new int[ar.length - halves[0].length]; // for right half
		
		// copy left half
		for(int i=0; i<halves[0].length; i++) {
			halves[0][i] = ar[i];
		}
		
		// copy right half
		for(int j=0; j<halves[1].length; j++) {
			halves[1][j] = ar[j + halves[0].length];
		}
		
		return halves;
	}
	
	int [] mergeSort(int [] ar) {
		
		// base case
		if(ar.length < 2) {
			return ar;
		}
		
		// divide step 
		int[][] halves = split(ar); 
		
		// conquer step
		int[] left = mergeSort(halves[0]);
		int[] right = mergeSort(halves[1]);
		
		// combine step
		return merge(left, right);
	}
	
	public MergeSort() {
		
		/* test merge  */
		int [] ar = {1, 4, 38, 88, 2030, 3300, 4000};
		int [] br = {8, 88, 2080, 3080, 4080, 5000, 6000, 7000, 8000, 9000};
		int [] cr = merge(ar, br);
		
		for(int i=0; i<cr.length; i++) {
			System.out.println(cr[i]);
		}
		/* end merge test */
		
		/* test merge sort 
		int [] ar = new int[50];
		for(int i=0; i<ar.length; i++) {
			ar[i] = (int)(1 + 100*Math.random());
		}
		int [] sorted = mergeSort(ar);
		for(int i=0; i<sorted.length; i++) {
			System.out.println(sorted[i]);
		} */
	}
	
	public static void main(String[] args) {
		new MergeSort();
	}
	
	
	
}
