package testB;
import static org.junit.Assert.*;

import org.junit.Test;

import phaseA.AVLTree;
import phaseA.StringComparator;
import phaseB.HashTable;
import phaseB.StringHasher;
import providedCode.Comparator;
import providedCode.DataCounter;
import providedCode.Hasher;

import test.TestDataCounter;


public class TestHashTable extends TestDataCounter<Integer>{

	private static final int TIMEOUT = 2000;
	
	@Override
	protected DataCounter<Integer> getDataCounter() {
		return new HashTable<Integer>(
				new Comparator<Integer>() {
					public int compare(Integer e1, Integer e2) { 
						return e1 - e2; 
						}
					}, new Hasher<Integer>() {
						public int hash(Integer data) {
							return Math.abs(data * 17 );
						}
					});
	}
	
	@Test (timeout = TIMEOUT)
	public void testEmptySize() {
		assertTrue(dc.getSize() == 0);
	}
	
	@Test (timeout = TIMEOUT)
	public void testGetCount() {
		assertEquals(dc.getCount(0), 0);
	}

	@Test (timeout = TIMEOUT)
	public void testSimpleHash() {
		Integer[] testArray = {0};
		addAndConfirm(testArray, 0, 1);
	}
	
	@Test (timeout = TIMEOUT)
	public void testConsistentMapping() {
		Integer[] testArray = {0, 0};
		addAndConfirm(testArray, 0, 2);
	}
	
	@Test (timeout = TIMEOUT)
	public void testTwoInserts() {
		addAndConfirm(new Integer[]{0}, 0, 1);
		addAndConfirm(new Integer[]{1}, 1, 1);
	}
	
	@Test (timeout = TIMEOUT)
	public void testMultipleInserts() {
		Integer[] testArray = {1, 2, 3, 4, 5};
		addAndConfirm(testArray, 1, 1);
		addAndConfirm(testArray, 2, 2);
		addAndConfirm(testArray, 3, 3);
		addAndConfirm(testArray, 4, 4);
		addAndConfirm(testArray, 5, 5);
	}
	
	@Test (timeout = TIMEOUT)
	public void testMultipleDuplicates() {
		Integer[] testArray = {1, 1, 2, 2, 3, 3};
		addAndConfirm(testArray, 1, 2);
		addAndConfirm(testArray, 2, 4);
		addAndConfirm(testArray, 3, 6);
	}
	
	@Test (timeout = TIMEOUT)
	public void testRehashSize() {
		Integer[] zero = {0};
		addAndConfirm(zero, 0, 1);
		
		Integer[] testArray = new Integer[100];
		for (int i = 0; i < 100; i++)
			testArray[i] = i;
		addAndConfirm(testArray, 0, 2);
	}
	
	@Test (timeout = TIMEOUT)
	public void testRehashConsistency() {
		Integer[] zero = {0};
		addAndConfirm(zero, 0, 1);
		Integer[] one = {1};
		addAndConfirm(one, 1, 1);
		
		Integer[] testArray = new Integer[100];
		for (int i = 0; i < 100; i++)
			testArray[i] = i;
		addAndConfirm(testArray, 0, 2);
		addAndConfirm(testArray, 1, 3);
		
	}
	
	// Add every element in insertArray to dc, check that the count of a certain 
	// is correct
	private void addAndConfirm(Integer[] insertArray, Integer value, int count ) {
		for (Integer i: insertArray) {
			dc.incCount(i);
		}
		assertEquals(dc.getCount(value), count);
	}
}
