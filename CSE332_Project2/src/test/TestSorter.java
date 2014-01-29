package test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import main.Sorter;

import org.junit.Before;
import org.junit.Test;

import providedCode.Comparator;


public class TestSorter {
	private static final int TIMEOUT = 2000; // 2000ms = 2sec
	private Sorter sorter;
	private Comparator<Integer> intcomp;
	
	@Before
	public void setup() {
		sorter = new Sorter();
		intcomp = new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { 
				return e1 - e2; 
			}
		};
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortEmptyArray() {
		Integer[] testArray = {};
		Integer[] copyArray = {};
		sorter.heapSort(testArray, intcomp);
		assertTrue("Testing empty heapSort", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortOneElement() {
		Integer[] testArray = {0};
		Integer[] copyArray = {0};
		sorter.heapSort(testArray, intcomp);
		assertTrue("Testing heapSort for one element", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortTwoElements() {
		Integer[] testArray = {1, 0};
		Integer[] copyArray = {0, 1};
		sorter.heapSort(testArray, intcomp);
		assertTrue("Testing heapSort for two elements", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortToTen() {
		Integer[] testArray = {1, 123, 14, 9, 2345, 20, 55};
		Integer[] copyArray = {1, 123, 14, 9, 2345, 20, 55};
		sorter.heapSort(testArray, intcomp);
		sorter.insertionSort(copyArray, intcomp);
		assertTrue("Testing heapsort against insertion sort for 6 elements", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortDuplicates() {
		Integer[] testArray = {2, 2, 1, 1, 3, 3};
		Integer[] copyArray = {2, 2, 1, 1, 3, 3};
		sorter.heapSort(testArray, intcomp);
		sorter.insertionSort(copyArray, intcomp);
		assertTrue("Testing heapsort for duplicates", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortSameDuplicate() {
		Integer[] testArray = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0};
		Integer[] copyArray = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0};
		sorter.heapSort(testArray, intcomp);
		sorter.insertionSort(copyArray, intcomp);
		assertTrue("Testing heapsort for many of the same duplicate", Arrays.equals(testArray, copyArray));
	}

	@Test(timeout = TIMEOUT)
	public void testSortLargeAscendingOrder() {
		Integer[] testArray = new Integer[100];
		Integer[] copyArray = new Integer[100];
		for (int i = 0; i <= 99; i++) {
			testArray[i] = i;
			copyArray[i] = i;
		}
		sorter.heapSort(testArray, intcomp);
		sorter.insertionSort(copyArray, intcomp);
		assertTrue("Testing heapsort for descending order", Arrays.equals(testArray, copyArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSortLargeDescendingOrder() {
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
	public void testSortRandomNumbers() {
		Integer[] testArray = new Integer[10];
		Integer[] copyArray = new Integer[10];
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			int randomInt = r.nextInt(1000);
			testArray[i] = randomInt;
			copyArray[i] = randomInt;
		}
		sorter.heapSort(testArray, intcomp);
		sorter.insertionSort(copyArray, intcomp);
		assertTrue("Testing heapsort for 10 random numbers", Arrays.equals(testArray, copyArray));
	}
}
