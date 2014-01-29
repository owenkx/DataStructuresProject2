package testA;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import phaseA.AVLTree;
import phaseA.MoveToFrontList;
import providedCode.Comparator;
import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.SimpleIterator;
import test.TestDataCounter;


public class TestAVLTree extends TestDataCounter<Integer> {

	private static final int TIMEOUT = 2000;

	/** Creates BinarySearchTree before each test cases **/
	@Override
	public DataCounter<Integer> getDataCounter() {
		return new AVLTree<Integer>(new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { return e1 - e2; }
		});
	}

	// Valid of added 0 elements?
	@Test(timeout = TIMEOUT)
	public void testInitiallyValid() {
		addAndCheck("Is empty valid?", new int[0]);
	}

	@Test(timeout = TIMEOUT)
	public void testOneElement() {
		addAndCheck("Valid after one element?", new int[] {1});
	}
	
	@Test(timeout = TIMEOUT)
	public void testTwoElements() {
		addAndCheck("Valid after adding two elements?", new int[] {5, 10});
	}
	
	@Test(timeout = TIMEOUT)
	public void testThreeElementsBalanced() {
		addAndCheck("Valid after adding three elements?", new int[] {5, 10, 1});
	}
	
	@Test(timeout = TIMEOUT)
	public void testThreeElementsLL() {
		addAndCheck("Valid after adding three elements?", new int[] {5, 3, 1});
	}
	
	@Test(timeout = TIMEOUT)
	public void testThreeElementsRR() {
		addAndCheck("Valid after adding three elements?", new int[] {1, 2, 3});
	}
	
	@Test(timeout = TIMEOUT)
	public void testThreeElementsLR() {
		addAndCheck("Valid after adding three elements?", new int[] {5, 2, 3});
	}
	
	@Test(timeout = TIMEOUT)
	public void testThreeElementsRL() {
		addAndCheck("Valid after adding three elements?", new int[] {5, 10, 7});
	}
	
	@Test(timeout = TIMEOUT)
	public void testLargeCase() {
		addAndCheck("Valid after adding three elements?", new int[] {5, 10, 7, 1, 2, 3, 4, 7, 8, 9});
		addAndCheck("Valid after adding three elements?", new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
		addAndCheck("Valid after adding three elements?", new int[] {10, 9, 8, 7, 6, 5, 4, 3, 2, 1});
	}

	/** Helper methods **/
	private void addAndCheck(String message, int[] input) {
		for (int i : input) { dc.incCount(i); }
		assertTrue(message, ((AVLTree) dc).isValidAVL());
	}
}
