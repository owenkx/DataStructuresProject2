package testA;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import phaseA.MoveToFrontList;
import providedCode.Comparator;
import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.SimpleIterator;
import test.TestDataCounter;


public class TestMoveToFrontList extends TestDataCounter<Integer> {

	private static final int TIMEOUT = 2000;
	
	/** Creates BinarySearchTree before each test cases **/
	@Override
	public DataCounter<Integer> getDataCounter() {
		return new MoveToFrontList<Integer>(new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { return e1 - e2; }
		});
	}


	
	/** Test Size =======================================================================================**/

	@Test(timeout = TIMEOUT)
	public void test_size_empty(){
		assertEquals("Tree should have size 0 when constructed", 0, dc.getSize());
	}
	
	@Test(timeout = TIMEOUT) 
	public void test_size_after_adding_single_num(){
		addAndTestSize("Tree should have size 1 after adding single 5", new int[]{5}, 1);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_many_same_num(){
		addAndTestSize("Tree should have size 1 after adding multiple 5", new int[]{5,5,5}, 1);
	}

	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_unique_nums(){
		int[] testArray = {0,1,2,3,4};
		addAndTestSize("Added " + Arrays.toString(testArray), testArray, 5);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_duplicate_nums(){
		int[] testArray = {0,0,1,1,2,2,3,3,4,4};
		addAndTestSize("Added " + Arrays.toString(testArray), testArray, 5);
	}
	
	
	
	/** Test getCount =======================================================================================**/
	
	@Test(timeout = TIMEOUT)
	public void test_get_count_after_adding_many_same_num(){
		int key = 9;
		int[] testArray = {9, 9, 9, 9, 9, 9, 9};
		addAndGetCount("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, 7);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_get_count_after_adding_many_diff_nums(){
		int key = 5;
		int[] testArray = {0, 5, -1, 5, 1, 5, 2};
		addAndGetCount("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, 3);
	}
	
	
	
	/** Test Iterator =======================================================================================**/

	@Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
	public void test_iterator_empty() {
		SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
		iter.next(); 
	}
	
	@Test(timeout = TIMEOUT)
	public void test_iterator_get_all_data() {
		int[] startArray = {7, -5, -5, -6, 6, 10, -9, 4, 8, 6};
		
		// Expected array has no duplicates and is sorted
		for(int i = 0; i < startArray.length; i++) { dc.incCount(startArray[i]); }
		int[] expected = {-9, -6, -5, 4, 6, 7, 8, 10};
		
		// Actual array returned by the iterator
		int i = 0;
		SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
		int[] actual = new int[expected.length];
		while(iter.hasNext()) { actual[i++] = iter.next().data; }
		
		// Sort and test
		Arrays.sort(actual);
		System.out.println("Actual: " + Arrays.toString(actual));
		System.out.println("Expected: " + Arrays.toString(expected));
		assertArrayEquals("Added " + Arrays.toString(startArray), expected, actual);
	}
	
	
	
	/** Private methods =======================================================================================**/

	private void addAndTestSize(String message, int[] input, int unique){
		for(int num : input) { dc.incCount(num); }
		assertEquals(message, unique, dc.getSize());
	}

	private void addAndGetCount(String message, int[] input, int key, int expected){
		for(int num : input) { dc.incCount(num); }
		assertEquals(message, expected, dc.getCount(key));
	}	
}
