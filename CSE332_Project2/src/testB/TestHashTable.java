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
	
	//@Test
	public void test() {
		fail("Not yet implemented");
	}

	

}
