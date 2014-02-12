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
							return Math.abs(data * 7 << 1);
						}
					});
	}
	
	@Test (timeout = TIMEOUT)
	public void testEmptySize() {
		assertTrue(dc.getSize() == 0);
	}
	
	@Test (timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
	public void testNoSuchElementException() {
		dc.getCount(0);
	}

	@Test (timeout = TIMEOUT)
	public void testSimpleHash() {
		dc.incCount(0);
		assertEquals(dc.getCount(0), 1);
	}
	
	@Test (timeout = TIMEOUT)
	public void testConsistentMapping() {
		dc.incCount(0);
		dc.incCount(0);
		assertEquals(dc.getCount(0), 2);
	}
	
	@Test (timeout = TIMEOUT)
	public void testTwoValues() {
		dc.incCount(0);
		dc.incCount(1);
	}
	
	@Test (timeout = TIMEOUT)
	public void testMultiple() {
		
	}
	
	@Test (timeout = TIMEOUT)
	public void testMultipleDuplicates() {
		
	}
	
	@Test (timeout = TIMEOUT)
	public void testRehashSize() {
		
	}
	
	@Test (timeout = TIMEOUT)
	public void testRehashConsistency() {
		
	}
}
