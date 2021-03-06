package test;
import static org.junit.Assert.*;

import java.util.Arrays;
import main.Sorter;

import org.junit.Before;
import org.junit.Test;

import providedCode.Comparator;


public class TestSorter {
	private static final int TIMEOUT = 2000; // 2000ms = 2sec
	private Sorter sorter;
	private Comparator<Integer> intcomp;
	private Comparator<Integer> topKComp;
	
	@Before
	public void setup() {
		sorter = new Sorter();
		intcomp = new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { 
				return e1 - e2; 
			}
		};
		
		topKComp = new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { 
				return e2 - e1; 
			}
		};
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortEmptyArrayHS() {
		Integer[] testArray = {};
		Integer[] copyArray = {};
		sorter.heapSort(testArray, intcomp);
		assertTrue("Testing empty heapSort", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortOneElementHS() {
		Integer[] testArray = {0};
		Integer[] copyArray = {0};
		sorter.heapSort(testArray, intcomp);
		assertTrue("Testing heapSort for one element", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortTwoElementsHS() {
		Integer[] testArray = {1, 0};
		Integer[] copyArray = {0, 1};
		sorter.heapSort(testArray, intcomp);
		assertTrue("Testing heapSort for two elements", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortToTenHS() {
		Integer[] testArray = {1, 123, 14, 9, 2345, 20, 55};
		Integer[] copyArray = {1, 9, 14, 20, 55, 123, 2345};
		sorter.heapSort(testArray, intcomp);
		assertTrue("Testing heapsort against insertion sort for 6 elements", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortDuplicatesHS() {
		Integer[] testArray = {2, 2, 1, 1, 3, 3};
		Integer[] copyArray = {1, 1, 2, 2, 3, 3};
		sorter.heapSort(testArray, intcomp);
		assertTrue("Testing heapsort for duplicates", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortSameDuplicateHS() {
		Integer[] testArray = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0};
		Integer[] copyArray = {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		sorter.heapSort(testArray, intcomp);
		assertTrue("Testing heapsort for many of the same duplicate", Arrays.equals(testArray, copyArray));
	}

	@Test(timeout = TIMEOUT)
	public void testSortLargeDescendingOrderHS() {
		Integer[] testArray = new Integer[100];
		Integer[] copyArray = new Integer[100];
		for (int i = 99; i >= 0; i--) {
			testArray[i] = i;
		}
		
		for (int i = 0; i <= 99; i++) {
			copyArray[i] = i;
		}
		
		sorter.heapSort(testArray, intcomp);
		assertTrue("Testing heapsort for ascending order", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testHeapSortInsertionSortHS() {
		Integer[] testArray = new Integer[100];
		Integer[] copyArray = new Integer[100];
		for (int i = 99; i >= 0; i--) {
			testArray[i] = i;
			copyArray[i] = i;
		}
		sorter.heapSort(testArray, intcomp);
		sorter.insertionSort(copyArray, intcomp);
		assertTrue("Testing heapsort for descending order", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortEmptyArrayQS() {
		Integer[] testArray = {};
		Integer[] copyArray = {};
		sorter.otherSort(testArray, intcomp);
		assertTrue("Testing empty quick sort", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortOneElementQS() {
		Integer[] testArray = {0};
		Integer[] copyArray = {0};
		sorter.otherSort(testArray, intcomp);
		assertTrue("Testing quick sort for one element", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortTwoElementsQS() {
		Integer[] testArray = {1, 0};
		Integer[] copyArray = {0, 1};
		sorter.otherSort(testArray, intcomp);
		assertTrue("Testing quick sort for two elements", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortToTenQS() {
		Integer[] testArray = {1, 123, 14, 9, 2345, 20, 55};
		Integer[] copyArray = {1, 9, 14, 20, 55, 123, 2345};
		sorter.otherSort(testArray, intcomp);
		assertTrue("Testing quick sort against insertion sort for 6 elements", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortDuplicatesQS() {
		Integer[] testArray = {2, 2, 1, 1, 3, 3};
		Integer[] copyArray = {1, 1, 2, 2, 3, 3};
		sorter.otherSort(testArray, intcomp);
		assertTrue("Testing quick sort for duplicates", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortSameDuplicateQS() {
		Integer[] testArray = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0};
		Integer[] copyArray = {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		sorter.otherSort(testArray, intcomp);
		assertTrue("Testing quick sort for many of the same duplicate", Arrays.equals(testArray, copyArray));
	}

	@Test(timeout = TIMEOUT)
	public void testSortLargeDescendingOrderQS() {
		Integer[] testArray = new Integer[100];
		Integer[] copyArray = new Integer[100];
		for (int i = 99; i >= 0; i--) {
			testArray[i] = i;
		}
		
		for (int i = 0; i <= 99; i++) {
			copyArray[i] = i;
		}
		
		sorter.otherSort(testArray, intcomp);
		assertTrue("Testing quick sort for ascending order", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testotherSortInsertionSortQS() {
		Integer[] testArray = new Integer[100];
		Integer[] copyArray = new Integer[100];
		for (int i = 99; i >= 0; i--) {
			testArray[i] = i;
			copyArray[i] = i;
		}
		sorter.otherSort(testArray, intcomp);
		sorter.insertionSort(copyArray, intcomp);
		assertTrue("Testing quick sort for descending order", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortEmptyArrayKS() {
		Integer[] testArray = {};
		Integer[] copyArray = {};
		sorter.topKSort(testArray, topKComp, testArray.length);
		assertTrue("Testing empty top k sort", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortOneElementKS() {
		Integer[] testArray = {0};
		Integer[] copyArray = {0};
		sorter.topKSort(testArray, topKComp, testArray.length);
		assertTrue("Testing top k sort for one element", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortTwoElementsKS() {
		Integer[] testArray = {1, 0};
		Integer[] copyArray = {0, 1};
		sorter.topKSort(testArray, topKComp, testArray.length);
		assertTrue("Testing top k sort for two elements", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortToTenKS() {
		Integer[] testArray = {1, 123, 14, 9, 2345, 20, 55};
		Integer[] copyArray = {1, 9, 14, 20, 55, 123, 2345};
		sorter.topKSort(testArray, topKComp, testArray.length);
		assertTrue("Testing top k sort against insertion sort for 6 elements", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortDuplicatesKS() {
		Integer[] testArray = {2, 2, 1, 1, 3, 3};
		Integer[] copyArray = {1, 1, 2, 2, 3, 3};
		sorter.topKSort(testArray, topKComp, testArray.length);
		assertTrue("Testing top k sort for duplicates", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortSameDuplicateKS() {
		Integer[] testArray = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0};
		Integer[] copyArray = {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		sorter.topKSort(testArray, topKComp, testArray.length);
		assertTrue("Testing top k sort for many of the same duplicate", Arrays.equals(testArray, copyArray));
	}

	@Test(timeout = TIMEOUT)
	public void testSortLargeDescendingOrderKS() {
		Integer[] testArray = new Integer[100];
		Integer[] copyArray = new Integer[100];
		for (int i = 99; i >= 0; i--) {
			testArray[i] = i;
		}
		
		for (int i = 0; i <= 99; i++) {
			copyArray[i] = i;
		}
		
		sorter.topKSort(testArray, topKComp, testArray.length);
		assertTrue("Testing top k sort for ascending order", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testtopKSortInsertionSortKS() {
		Integer[] testArray = new Integer[100];
		Integer[] copyArray = new Integer[100];
		for (int i = 99; i >= 0; i--) {
			testArray[i] = i;
			copyArray[i] = i;
		}
		sorter.topKSort(testArray, topKComp, testArray.length);
		sorter.insertionSort(copyArray, intcomp);
		assertTrue("Testing top k sort for descending order", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testTo2K() {
		Integer[] testArray = {1, 123, 14, 9, 2345, 20, 55};
		sorter.topKSort(testArray, topKComp, 2);
		assertTrue("Testing top k sort with k of 2", firstKAreSorted(testArray, 2));
	}
	
	@Test(timeout = TIMEOUT)
	public void testTo1K() {
		Integer[] testArray = {1, 123, 14, 9, 2345, 20, 55};
		sorter.topKSort(testArray, topKComp, 1);
		assertTrue("Testing top k sort with k of 2", firstKAreSorted(testArray, 1));
	}
	
	@Test(timeout = TIMEOUT)
	public void testTo3K() {
		Integer[] testArray = {1, 123, 14, 9, 2345, 20, 55};
		sorter.topKSort(testArray, topKComp, 3);
		assertTrue("Testing top k sort with k of 2", firstKAreSorted(testArray, 3));
	}
	
	private boolean firstKAreSorted(Integer[] arr, int k) {
		for (int i = 1; i < k; i++) {
			if (arr[i] < arr[i-1]) { return false; }
		}
		return true;
	}
}
