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

	@Test(timeout = TIMEOUT)
	public void firstElementAtFrontTest(){
		checkFirstElementCorrectincCount("Only element should be first element", new int[]{5});
	}
	
	@Test(timeout = TIMEOUT)
	public void multipleUniqueAscAtFrontTest(){
		checkFirstElementCorrectincCount("Last element should be first: ascending", new int[]{1, 2, 3, 4, 5});
	}
	
	@Test(timeout = TIMEOUT)
	public void multipleUniqueDescAtFrontTest(){
		checkFirstElementCorrectincCount("Last element should be first: descending", new int[]{5, 4, 3, 2, 1});
	}
	
	@Test(timeout = TIMEOUT)
	public void duplicatesTest(){
		checkFirstElementCorrectincCount("Check internal correctness with duplicates", new int[]{1, 2, 3, 3, 3, 4});
	}
	
	@Test(timeout = TIMEOUT)
	public void multipleDuplicatesTest(){
		checkFirstElementCorrectincCount("Check internal correctness with multiple duplicates", new int[]{1, 2, 3, 3, 3, 1});
	}
	
	@Test(timeout = TIMEOUT)
	public void firstElementAtFrontSearchTest(){
		checkFirstElementCorrectGetCount("Only element should be first element", new int[]{5}, 5);
	}
	
	@Test(timeout = TIMEOUT)
	public void multipleUniqueAscAtFrontSearchTest(){
		checkFirstElementCorrectGetCount("Last element should be first: ascending", new int[]{1, 2, 3, 4, 5}, 3);
	}
	
	@Test(timeout = TIMEOUT)
	public void multipleUniqueDescAtFrontSearchTest(){
		checkFirstElementCorrectGetCount("Last element should be first: descending", new int[]{5, 4, 3, 2, 1}, 1);
	}
	
	@Test(timeout = TIMEOUT)
	public void duplicatesSearchTest(){
		checkFirstElementCorrectGetCount("Check internal correctness with duplicates", new int[]{1, 2, 3, 3, 3, 4}, 3);
	}
	
	@Test(timeout = TIMEOUT)
	public void multipleDuplicatesSearchTest(){
		checkFirstElementCorrectGetCount("Check internal correctness with multiple duplicates", new int[]{1, 2, 3, 3, 3, 1}, 2);
	}
	
	/** Private methods =======================================================================================**/

	// checks that the last element added is the first element in the list
	private void checkFirstElementCorrectincCount(String message, int[] input){
		for(int num : input) { dc.incCount(num); }
		SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
		assertEquals(message, input[input.length - 1], (int) iter.next().data);
	}
	
	// checks that the last element searched for is the first element in the list
	private void checkFirstElementCorrectGetCount(String message, int[] input, int search){
		for(int num : input) { dc.incCount(num); }
		dc.getCount(search);
		SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
		assertEquals(message, search, (int) iter.next().data);
	}
	
}
