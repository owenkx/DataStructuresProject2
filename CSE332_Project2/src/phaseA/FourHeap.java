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
	
	private static final int NUM_CHILDREN = 4;
	private static final int DEFAULT_CAPACITY = 10; 
	private static final int RESIZE_FACTOR = 2;
	
	/**
	 * @param c: a comparator for determining tree order
	 * @return empty heap with a capacity of 10 elements
	 **/
	public FourHeap(Comparator<? super E> c) {
		this(DEFAULT_CAPACITY, c);
	}
	
	/**
	 * @param n: initial heap capacity
	 * @param c: a comparator for determining tree order
	 * @return empty heap with a capacity of n elements
	 **/
	
	public FourHeap(int n, Comparator<? super E> c) {
		this((E[]) new Object[n], c, 0);
	}
	
	/**
	 * @param insertArray: array of elements to be inserted into the heap
	 * @param c: a comparator for determining tree order
	 * @param lastIndex: the size of the array
	 * @return a heap with the capacity of the inserted array and a size
	 * of the last valid index.
	 **/
	public FourHeap(E[] insertArray, Comparator<?super E> c, int lastIndex) {
		size = lastIndex;
		comparator = c;
		heapArray = (E []) new Object[insertArray.length];
		for (int i = 0; i < insertArray.length; i++) {
			heapArray[i] = insertArray[i];
		}
		buildHeap();
	}

	
	/**
	 * @param item: The item to be inserted
	 * Inserts the item into the heap and readjusts the heap to be valid
	 **/
	@Override
	public void insert(E item) {
		if (isFull())
			resize(heapArray.length * RESIZE_FACTOR);
		int hole = size++;
		for(; hole > 0 && comparator.compare(item, heapArray[(hole - 1) / NUM_CHILDREN]) < 0; hole = (hole - 1) / NUM_CHILDREN){
			heapArray[hole] = heapArray[(hole - 1) / NUM_CHILDREN];
		}
		heapArray[hole] = item;
	}

	@Override
	/**
	 * @return: returns the minimum element in the array
	 **/
	public E findMin() {
		if (isEmpty())
			throw new NoSuchElementException();
		return heapArray[0];
	}
	
	/**
	 * @return: returns the minimum element in the array after deletion
	 **/
	@Override
	public E deleteMin() {
		if (isEmpty())
			throw new NoSuchElementException();
		E min = findMin();
		int hole = percolateDown(0, heapArray[--size]);
		heapArray[hole] = heapArray[size];
		return min;
	}

	@Override
	/**
	 * @return: whether or not the array is Empty
	 **/
	public boolean isEmpty() {
		return size == 0;
	}
		
	/**
	 * prints out every single element in the heap as well as the last valid index
	 **/
	public void printArray() {
		for (int i = 0; i < size; i++) {
			System.out.println(heapArray[i]);
		}
		System.out.println("Last index is " + (size - 1));
	}
	
	/**
	 * @param hole: The index of the element to percolate downwards
	 * @param reference: The element that is being percolated
	 * @return: index of where the element at the index has percolated down to.
	 **/
	private int percolateDown(int hole, E reference) {
		int targetChild;
		for (; hole * NUM_CHILDREN + 1 < size; hole = targetChild) {
			int firstChild = hole * NUM_CHILDREN + 1;
			targetChild = firstChild; 
			
			//find the minimum child
			for (int i = 1; i < NUM_CHILDREN; i++) {
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

	/**
	 * @param newSize: the new capacity of the heap
	 * Copies every element from the heap array into a new heap array of specified size
	 **/
	private void resize(int newSize) {
		E[] newArray = (E[]) new Object[newSize];
		for (int i = 0; i < heapArray.length; i++) {
			newArray[i] = heapArray[i];
		}
		heapArray = newArray;
	}
	
	/**
	 * @return: returns whether or not the heap is full
	 **/
	private boolean isFull(){
		return size >= heapArray.length - 1;
	}
	
	/**
	 * Uses the Floyd method to initialize a heap. Percolates every element down to its
	 * correct position.
	 **/
	private void buildHeap() {
		if (size > -1) { 
			for (int i = (size - 2) / NUM_CHILDREN; i >= 0; i--) {
				E element = heapArray[i];
				int hole = percolateDown(i, element);
				heapArray[hole] = element;
			}
		}
	}
	
}
