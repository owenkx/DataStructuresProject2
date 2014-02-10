package main;
import providedCode.Comparator;
import phaseA.FourHeap;

/** 
 *  TODO: Replace this comment with your own as appropriate.
 *  Implement the sorting methods below. Do not change the provided method signature,
 *  but you may add as many other methods as you want.
 */
public class Sorter {

	/**
	 * Sort the count array in descending order of count. If two elements have
	 * the same count, they should be ordered according to the comparator.
	 * This code uses insertion sort. The code is generic, but in this project
	 * we use it with DataCount<String> and DataCountStringComparator.
	 * @param counts array to be sorted.
	 * @param comparator for comparing elements.
	 */
	public static <E> void insertionSort(E[] array, Comparator<E> comparator) {
		for (int i = 1; i < array.length; i++) {
			E x = array[i];
			int j;
			for (j=i-1; j>=0; j--) {
				if (comparator.compare(x,array[j]) >= 0) { break; }
				array[j + 1] = array[j];
			}
			array[j + 1] = x;
		}
	}

	//Takes in an array and comparator, then sorts the array by placing
	//all of the elements in a heap and then removing them.
	public static <E> void heapSort(E[] array, Comparator<E> comparator) {
		FourHeap<E> heap = new FourHeap<E>(array, comparator, array.length);
		for (int i = 0; i < array.length; i++) {
			array[i] = heap.deleteMin();
		}
	}

	public static <E> void topKSort(E[] array, Comparator<E> reverse_comparator, int k) {
		System.out.println("test");
		// If k is larger than our array, we can just print out the whole array
		if (k >= array.length) { k = array.length; }
		
		FourHeap<E> heap = new FourHeap<E>(reverse_comparator);
		// Put the first k items in our reverse heap
		for (int i = 0; i < k; i++) {
			heap.insert(array[i]);
		}
		
		// Now alternate deleting min and inserting elements to always have k in our reverse heap
		for (int i = k; i < array.length; i++) {
			heap.deleteMin();
			heap.insert(array[i]);
		}
		
		// now get everything out of the reverse heap, into a list
		for (int i = 0; i < k; i++) {
			array[i] = heap.deleteMin();
		}
		
		if (!heap.isEmpty()) { throw new IllegalStateException(); }
		
		// and finally, reverse the elements in our list
		for (int i = 0; i < k / 2; i++) {
			E temp = array[i];
			array[i] = array[k - 1 - i];
			array[k - 1 - i] = temp;
		}
		
		// O (k log k) + O ( (n - k) log k) + O ( k log (k) ) + O ( k ) = O ( n log k )
	}

	/**
	 * Sort the count array in descending order of count. If two elements have
	 * the same count, they should be ordered according to the comparator.
	 * This code uses insertion sort. The code is generic, but in this project
	 * we use it with DataCount<String> and DataCountStringComparator.
	 * @param counts array to be sorted.
	 * @param comparator for comparing elements.
	 */
	public static <E> void otherSort(E[] array, Comparator<E> comparator) {
		if (array.length >= 2) { 
			quickSort(array, 0, array.length - 1, comparator);
		}
	}

	private static <E> void quickSort(E[] contents, int left, int right, Comparator<E> comparator) {
		if ((left + 2) > right) {
			if (comparator.compare(contents[left], contents[right]) > 0) { swap(contents, left, right); }
		} else if (left < right) {
			medianOf3Pivot(contents, left, right, comparator);
			int pivotIndex = partition(contents, left+1, right-1, comparator);
			quickSort(contents, left, pivotIndex - 1, comparator);
			quickSort(contents, pivotIndex + 1, right, comparator);
		}
	}

	private static <E> void medianOf3Pivot(E[] contents, int left, int right, Comparator<E> comparator) {

		int middle = (left + right) / 2;
		int l, r, pivot;

		if (comparator.compare(contents[right], contents[middle]) >= 0 && comparator.compare(contents[right], contents[left]) >= 0) {
			r = right; 
			if (comparator.compare(contents[middle], contents[left]) >= 0) { pivot = middle; l = left; }
			else { pivot = left; l = middle; }
		} else if (comparator.compare(contents[left], contents[middle]) >= 0 && comparator.compare(contents[left], contents[right]) >= 0) {
			r = left; 
			if (comparator.compare(contents[middle], contents[right]) >= 0) { pivot = middle; l = right; }
			else { pivot = right; l = middle; }
		} else { // middle is the largest
			r = middle;
			if (comparator.compare(contents[left], contents[right]) >= 0) { pivot = left; l = right; }
			else { pivot = right; l = left; }
		}

		E temp_l = contents[l];
		E temp_r = contents[r];
		E temp_pivot = contents[pivot];
		contents[middle] = contents[right - 1];
		contents[right-1] = temp_pivot;
		contents[left] = temp_l;
		contents[right] = temp_r;

	}

	private static <E> int partition(E[] contents, int left, int right, Comparator<E> comparator) {

		// assume pivot value is at right
		E v = contents[right];
		int i = left;
		int j = right - 1;

		while (true) {
			while (comparator.compare(contents[i], v) < 0) { i++; }
			while (comparator.compare(contents[j], v) > 0) { j--; }
			if (i < j) {
				swap(contents, i, j);
				i++;
				j--;
			} else { 
				break;
			}
		}

		swap(contents, i, right);

		return i;
	}

	public static <E> void swap(E[] arr, int i, int j) {
		E temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
