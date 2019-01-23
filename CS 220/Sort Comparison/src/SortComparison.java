// SortComparison.java
// Ethan Lor

public class SortComparison {

	public static void main(String[] args) {
		new SortComparison(100, 1000);
		new SortComparison(100, 10000);
		new SortComparison(100, 100000);
	}

	public SortComparison(int num, int numElem) {
		int numOfArr = num;
		int n = numElem;
		long selectionSortMin = 0;
		long selectionSortMax = 0;
		long selectionSortRunTot = 0;

		long bubbleSortMin = 0;
		long bubbleSortMax = 0;
		long bubbleSortRunTot = 0;

		long quickSortMin = 0;
		long quickSortMax = 0;
		long quickSortRunTot = 0;

		long mergeSortMin = 0;
		long mergeSortMax = 0;
		long mergeSortRunTot = 0;

		for (int i = 0; i < numOfArr; i++) {
			int[] ar = new int[n];

			for (int j = 0; j < ar.length; j++) {
				ar[j] = (int) ((Math.random() * Integer.MAX_VALUE) + 1);
			}

			long selectionSortTot = testSelectionSort(ar);
			long bubbleSortTot = testBubbleSort(ar);
			long quickSortTot = testQuickSort(ar);
			long mergeSortTot = testMergeSort(ar);

			if (selectionSortMin == 0) {
				selectionSortMin = selectionSortTot;
			} else if (selectionSortTot < selectionSortMin) {
				selectionSortMin = selectionSortTot;
			}
			if (selectionSortTot > selectionSortMax) {
				selectionSortMax = selectionSortTot;
			}
			selectionSortRunTot = selectionSortRunTot + selectionSortTot;

			if (bubbleSortMin == 0) {
				bubbleSortMin = bubbleSortTot;
			} else if (bubbleSortTot < bubbleSortMin) {
				bubbleSortMin = bubbleSortTot;
			}
			if (bubbleSortTot > bubbleSortMax) {
				bubbleSortMax = bubbleSortTot;
			}
			bubbleSortRunTot = bubbleSortRunTot + bubbleSortTot;

			if (quickSortMin == 0) {
				quickSortMin = quickSortTot;
			} else if (quickSortTot < quickSortMin) {
				quickSortMin = quickSortTot;
			}
			if (quickSortTot > quickSortMax) {
				quickSortMax = quickSortTot;
			}
			quickSortRunTot = quickSortRunTot + quickSortTot;

			if (mergeSortMin == 0) {
				mergeSortMin = mergeSortTot;
			} else if (mergeSortTot < mergeSortMin) {
				mergeSortMin = mergeSortTot;
			}
			if (mergeSortTot > mergeSortMax) {
				mergeSortMax = mergeSortTot;
			}
			mergeSortRunTot = mergeSortRunTot + mergeSortTot;
		}

		printStats("Selection Sort", numOfArr, n, selectionSortMin, selectionSortMax, selectionSortRunTot);
		printStats("Bubble Sort", numOfArr, n, bubbleSortMin, bubbleSortMax, bubbleSortRunTot);
		printStats("Quick Sort", numOfArr, n, quickSortMin, quickSortMax, quickSortRunTot);
		printStats("Merge Sort", numOfArr, n, mergeSortMin, mergeSortMax, mergeSortRunTot);
	}

	private int[] copyAr(int[] ar) {
		int[] arCopy = new int[ar.length];
		for (int i = 0; i < arCopy.length; i++) {
			arCopy[i] = ar[i];
		}
		return arCopy;
	}

	private void printStats(String sort, int numOfArr, int n, long min, long max, long tot) {
		System.out.println(sort + " n = " + n);
		System.out.println("---------------------------------------");
		System.out.println("Min Time (in microseconds): " + min);
		System.out.println("Max Time (in microseconds): " + max);
		System.out.println("Avg Time (in microseconds): " + tot / numOfArr);
		System.out.println("---------------------------------------");
	}

	private long testSelectionSort(int[] ar) {
		int[] arCopy = copyAr(ar);
		long start = System.nanoTime();
		selectionSort(arCopy);
		long end = System.nanoTime();
		long tot = Math.round((end - start) / 1000.0);

		return tot;
	}

	private long testBubbleSort(int[] ar) {
		int[] arCopy = copyAr(ar);
		long start = System.nanoTime();
		bubbleSort(arCopy);
		long end = System.nanoTime();
		long tot = Math.round((end - start) / 1000.0);

		return tot;
	}

	private long testQuickSort(int[] ar) {
		int[] arCopy = copyAr(ar);
		long start = System.nanoTime();
		quickSort(arCopy);
		long end = System.nanoTime();
		long tot = Math.round((end - start) / 1000.0);

		return tot;
	}

	private long testMergeSort(int[] ar) {
		int[] arCopy = copyAr(ar);
		long start = System.nanoTime();
		arCopy = mergeSort(arCopy);
		long end = System.nanoTime();
		long tot = Math.round((end - start) / 1000.0);

		return tot;
	}

	private void selectionSort(int[] ar) {
		for (int i = 0; i < ar.length - 1; i++) {
			int min = i;
			for (int j = i + 1; j < ar.length; j++) {
				if (ar[j] < ar[min]) {
					min = j;
				}
			}
			swap(ar, i, min);
		}
	}

	private void swap(int[] ar, int i, int min) {
		int temp = ar[i];
		ar[i] = ar[min];
		ar[min] = temp;
	}

	private void bubbleSort(int[] ar) {
		boolean swapped = true;
		int k = 1;
		while (swapped) {
			swapped = false;
			for (int i = 0; i < ar.length - k; i++) {
				if (ar[i] > ar[i + 1]) {
					swap(ar, i, i + 1);
					swapped = true;
				}
			}
			k++;
		}
	}

	private void quickSort(int[] ar) {
		quickSort(ar, 0, ar.length - 1);
	}

	private void quickSort(int[] ar, int low, int high) {
		if (low >= high) {
			return;
		}

		int i = partition(ar, low, high);
		quickSort(ar, low, i - 1);
		quickSort(ar, i, high);
	}

	private int partition(int[] ar, int left, int right) {
		int i = left, j = right;
		int p = ar[left + (right - left) / 2];

		while (i <= j) {
			while (ar[i] < p) {
				i++;
			}

			while (ar[j] > p) {
				j--;
			}

			if (i <= j) {
				swap(ar, i, j);
				i++;
				j--;
			}
		}
		return i;
	}

	private int[] mergeSort(int[] ar) {
		if (ar.length < 2) {
			return ar;
		}

		int[][] halves = split(ar);

		int[] left = mergeSort(halves[0]);
		int[] right = mergeSort(halves[1]);

		return merge(left, right);
	}

	private int[][] split(int[] ar) {
		int[][] halves = new int[2][];
		halves[0] = new int[ar.length / 2];
		halves[1] = new int[ar.length - halves[0].length];

		for (int i = 0; i < halves[0].length; i++) {
			halves[0][i] = ar[i];
		}

		for (int j = 0; j < halves[1].length; j++) {
			halves[1][j] = ar[j + halves[0].length];
		}
		return halves;
	}

	private int[] merge(int[] left, int[] right) {
		int[] comb = new int[left.length + right.length];

		int l = 0, r = 0, k = 0;

		while (l < left.length && r < right.length) {
			if (left[l] < right[r]) {
				comb[k++] = left[l++];
			} else {
				comb[k++] = right[r++];
			}
		}

		while (l < left.length) {
			comb[k++] = left[l++];
		}

		while (r < right.length) {
			comb[k++] = right[r++];
		}
		return comb;
	}

}