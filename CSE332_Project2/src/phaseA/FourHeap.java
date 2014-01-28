package phaseA;
import providedCode.*;
import java.util.NoSuchElementException;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. It is exactly like the binary heap we studied, except nodes should have 4 children 
 * 	  instead of 2. Only leaves and at most one other node will have fewer children. 
 * 2. Use array-based implementation, beginning at index 0 (Root should be at index 0). 
 *    Construct the FourHeap by passing appropriate argument to superclass constructor.
 *    Hint: Complete written homework #2 before attempting this.   
 * 3. Throw appropriate exceptions in FourHeap whenever needed. For example, 
 * 	  when deleteMin on an empty heap, you could use UnderFlowException as is done in the Weiss text, 
 *    or you could use NoSuchElementException (in which case it will be fine if you want to import it). 
 * TODO: Develop appropriate JUnit tests for your FourHeap.
 */

@SuppressWarnings("unchecked")

public class FourHeap<E> extends Heap<E> {
	private E[] heapArray;
	private int currentIndex;
	private Comparator<? super E> comparator;
	private static final int DEFAULT_CAPACITY = 10; 
	private static final int RESIZE_FACTOR = 2;
	
	public FourHeap(Comparator<? super E> c) {
		// TODO: To-be implemented. Replace dummy parameter to superclass constructor
		this(DEFAULT_CAPACITY, c);
	}
	
	public FourHeap(int n, Comparator<? super E> c) {
		this((E[]) new Object[n], c, 0);
	}
	
	public FourHeap(E[] insertArray, Comparator<?super E> c, int lastIndex) {
		currentIndex = lastIndex - 1;
		comparator = c;
		heapArray = (E []) new Object[insertArray.length];
		for (int i = 0; i < insertArray.length; i++) {
			heapArray[i] = insertArray[i];
		}
		buildHeap();
	}

	@Override
	//inserts an item, and keeps the tree complete
	public void insert(E item) {
		if (currentIndex == heapArray.length - 1)
			resize(heapArray.length * RESIZE_FACTOR);
		int hole = percolateUp(++currentIndex, item);
		heapArray[hole] = item;
	}

	@Override
	//returns the minimum value in the heap if nonempty
	public E findMin() {
		if (isEmpty())
			throw new NoSuchElementException();
		return heapArray[0];
	}

	@Override
	//deletes and returns the minimum value in the heap, if nonempty
	//then resorts the heap.
	public E deleteMin() {
		if (isEmpty())
			throw new NoSuchElementException();
		E min = findMin();
		heapArray[0] = heapArray[currentIndex--];
		percolateDown(0);
		return min;
	}

	@Override
	//returns whether or not the heap is empty
	public boolean isEmpty() {
		return currentIndex == -1;
	}
	
	//makes the heap empty again
	public void makeEmpty() {
		currentIndex = -1;
	}
	
	//Takes in a hole index and an item and 
	//returns the first valid hole in which to insert the item.
	private int percolateUp(int hole, E item) {
		while (hole > 0 && comparator.compare(item, heapArray[(hole - 1) / 4]) < 0) {
			heapArray[hole] = heapArray[(hole - 1) / 4];
			hole = (hole - 1) / 4;
		}
		return hole;
	}
	
	//Takes in an index to percolate down from, finds the 
	//smallest of the four children and swaps the values
	//until the heap is in order.
	private void percolateDown(int hole) {
		E reference = heapArray[hole];
		while (hole * 4 + 1 <= currentIndex) {
			int targetChild = hole * 4 + 1;
			int firstChild = targetChild; 
			
			for (int i = 1; i <= 3; i++) {
				if (firstChild + i <= currentIndex && 
						comparator.compare(heapArray[targetChild], heapArray[firstChild + i]) > 1)
					targetChild = firstChild + i;
			}
			if (comparator.compare(heapArray[targetChild], reference) < 0) {
				E temp = heapArray[hole];
				heapArray[hole] = heapArray[targetChild];
				heapArray[targetChild] = temp;
				hole = targetChild;
			} else {
				break;
			}
		}
	}

	//resizes the array by the resize factor
	private void resize(int newSize) {
		E[] newArray = (E[]) new Object[newSize];
		for (int i = 0; i < heapArray.length; i++) {
			newArray[i] = heapArray[i];
		}
		heapArray = newArray;
	}
	
	//sorts the heap from the bottom-most parent nodes
	private void buildHeap() {
		for (int i = (currentIndex - 1) / 4; i >= 0; i--) {
			percolateDown(i);
		}
	}
}
