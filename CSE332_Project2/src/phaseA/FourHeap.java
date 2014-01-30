package phaseA;
import providedCode.*;
import java.util.NoSuchElementException;

/**
 * 1. It is exactly like the binary heap we studied, except nodes should have 4 children 
 * 	  instead of 2. Only leaves and at most one other node will have fewer children. 
 * 2. Use array-based implementation, beginning at index 0 (Root should be at index 0). 
 *    Construct the FourHeap by passing appropriate argument to superclass constructor.
 * 3. Throw appropriate exceptions in FourHeap whenever needed. For example, 
 * 	  when deleteMin on an empty heap, you could use UnderFlowException as is done in the Weiss text, 
 *    or you could use NoSuchElementException (in which case it will be fine if you want to import it). 
 */

@SuppressWarnings("unchecked")

public class FourHeap<E> extends Heap<E> {
	//stores every heap value
	private E[] heapArray;
	
	//the last filled index
	private int size;
	private Comparator<? super E> comparator;
	private static final int DEFAULT_CAPACITY = 10; 
	private static final int RESIZE_FACTOR = 2;
	
	//takes in a comparator and initializes a heap of default capacity
	public FourHeap(Comparator<? super E> c) {
		this(DEFAULT_CAPACITY, c);
	}
	
	//takes in a heap size and comparator and initializes a heap
	//to that capacity
	public FourHeap(int n, Comparator<? super E> c) {
		this((E[]) new Object[n], c, 0);
	}
	
	//takes in an array and uses the Floyd method to
	//insert the elements into the heap until the array's last valid index
	public FourHeap(E[] insertArray, Comparator<?super E> c, int lastIndex) {
		size = lastIndex;
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
		if (isFull())
			resize(heapArray.length * RESIZE_FACTOR);
		int hole = size++;
		for(; hole > 0 && comparator.compare(item, heapArray[(hole - 1) / 4]) < 0; hole = (hole - 1) / 4){
			heapArray[hole] = heapArray[(hole - 1) / 4];
		}
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
		int hole = percolateDown(0, heapArray[--size]);
		heapArray[hole] = heapArray[size];
		return min;
	}

	@Override
	//returns whether or not the heap is empty
	public boolean isEmpty() {
		return size == 0;
	}
	
	//makes the heap empty again, for cleanup
	public void makeEmpty() {
		size = 0;
	}
	
	//for debugging purposes
	public void printArray() {
		for (int i = 0; i < size; i++) {
			System.out.println(heapArray[i]);
		}
		System.out.println("Last index is " + (size - 1));
	}
	
	//Takes in an index to percolate down from, finds the 
	//smallest of the four children and swaps the values
	//until the heap is in order.
	private int percolateDown(int hole, E reference) {
		int targetChild;
		for (; hole * 4 + 1 < size; hole = targetChild) {
			int firstChild = hole * 4 + 1;
			targetChild = firstChild; 
			
			//find the minimum child
			for (int i = 1; i <= 3; i++) {
				if (firstChild + i < size && 
						comparator.compare(heapArray[targetChild], heapArray[firstChild + i]) > 0)
					targetChild = firstChild + i;
			}
			//percolate the value down
			if (comparator.compare(heapArray[targetChild], reference) < 0) {
				heapArray[hole] = heapArray[targetChild];
			} else {
				break;
			}
		}
		return hole;
	}

	//resizes the array by the resize factor
	private void resize(int newSize) {
		E[] newArray = (E[]) new Object[newSize];
		for (int i = 0; i < heapArray.length; i++) {
			newArray[i] = heapArray[i];
		}
		heapArray = newArray;
	}
	
	//returns whether or not the heap is full
	private boolean isFull(){
		return size >= heapArray.length - 1;
	}
	
	//sorts the heap from the bottom-most parent nodes
	private void buildHeap() {
		if (size > -1) { 
			for (int i = (size - 2) / 4; i >= 0; i--) {
				E element = heapArray[i];
				int hole = percolateDown(i, element);
				heapArray[hole] = element;
			}
		}
	}
	
}
