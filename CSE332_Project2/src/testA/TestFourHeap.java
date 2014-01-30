package testA;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import phaseA.FourHeap;
import providedCode.Comparator;


public class TestFourHeap {
	private final static int TIMEOUT = 2000;
	private FourHeap<Integer> heap;
	
	@Before
	public void setup() {
		Comparator<Integer> intComp = new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { 
				return e1 - e2; 
			}
		};
		heap = new FourHeap<Integer>(intComp);
	}
	
	@Test(timeout = TIMEOUT)
	public void testIfEmpty() {
		assertTrue("Heap initialized, should be empty", heap.isEmpty());
	}
	
	@Test(timeout = TIMEOUT)
	public void testInsert() {
		heap.insert(1);
		assertEquals("Testing insert, inserting 1", heap.findMin(), (Integer) 1);
	}

	@Test(timeout = TIMEOUT)
	public void testNonEmpty() {
		heap.insert(0);
		assertFalse("Heap should be nonempty", heap.isEmpty());
	}
	
	@Test(timeout = TIMEOUT)
	public void testOrderAfterTwoInserts() {
		heap.insert(0);
		heap.insert(1);
		assertEquals("Testing order after two inserts", heap.findMin(), (Integer) 0);
		heap.makeEmpty();
	}
	
	@Test(timeout = TIMEOUT)
	public void testDeleteMin() {
		heap.insert(0);
		heap.insert(1);
		Integer deleted = heap.deleteMin();
		heap.insert(0);
		Integer found = heap.findMin();
		assertEquals("Testing deleteMin is the same as findMin", found, deleted);
	}
	
	//Tests percolation for unsorted insertions.
	@Test(timeout = TIMEOUT)
	public void testPercolateUp() {
		Integer[] testArray = {5, 6, 1};
		Integer[] expected = {1, 5, 6};
		insertArray(testArray);
		assertTrue("Testing percolate up for one level", deleteMinTest(expected));
	}
	
	//Tests the heap for two complete levels and one element 
	@Test(timeout = TIMEOUT)
	public void testingInsertThreeLevels() {
		Integer[] testArray = {0, 1, 2, 3, 4, 5};
		insertArray(testArray);
		assertTrue("Testing insertion and deletion on three levels", deleteMinTest(testArray));
	}
	
	//Since the initial capacity is 10, the array should resize twice at
	//21 elements
	@Test(timeout = TIMEOUT)
	public void testHeapAfterResize() {
		Integer[] testArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		insertArray(testArray);
		assertTrue("Making sure that the heap works after a resize", deleteMinTest(testArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testHugeArray() {
		Integer[] testArray = new Integer[100];
		for (int i = 0; i < 100; i++) {
			testArray[i] = i;
			heap.insert(i);
		}
		assertTrue("Testing the heap for 100 elements", deleteMinTest(testArray));
	}
	
	@Test(timeout = TIMEOUT)
	public void testDuplicateNumbers() {
		Integer[] testArray = {0, 0, 1, 1, 2, 2, 3, 3};
		insertArray(testArray);
		assertTrue("Testing for 4 consecutive duplicates", deleteMinTest(testArray));
	}
	
	@Test (timeout = TIMEOUT)
	public void testSameDuplicate() {
		Integer[] testArray = {5, 5, 5, 5, 5, 5, 5};
		insertArray(testArray);
		assertTrue("Testing for 7 of the same number", deleteMinTest(testArray));
	}
	
    /** Test Floyd Method =======================================================================================**/
	
	//Tests the Floyd method of heap construction
	@Test(timeout = TIMEOUT)
	public void testFloydConstructor() {
		Integer[] testArray = {0, 1, 2, 3, 4, 5, 6, 7, 8};
		setupArrayConstructor(testArray);
		assertTrue("Testing the array constructor", deleteMinTest(testArray));
	}
	
	public void testFloydHuge() {
		Integer[] testArray = new Integer[100];
		for (int i = 0; i < 100; i++) {
			testArray[i] = i;
		}
		setupArrayConstructor(testArray);
		assertTrue("Testing the FloydMethod for 100 elements", deleteMinTest(testArray));
	}
	
	private void insertArray(Integer[] elements) {
		for (int i: elements)
			heap.insert(i);
	}
	
	private boolean deleteMinTest(Integer[] testArray) {
		for (int index = 0; !heap.isEmpty(); index++) {
			if (heap.deleteMin() != testArray[index])
				return false;
		}
		return true;
	}
	
	private void setupArrayConstructor(Integer[] list) {
		heap = new FourHeap<Integer>(list, new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { 
				return e1 - e2; 
			}
		}, list.length);
	}
}
