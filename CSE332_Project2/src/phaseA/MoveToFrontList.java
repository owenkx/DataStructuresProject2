package phaseA;
import providedCode.Comparator;
import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.SimpleIterator;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items (with a count of 1) to the front of the list.
 * 3. Whenever an existing item has its count incremented by incCount or 
 *    retrieved by getCount, move it to the front of the list. That means 
 *    you remove the node from its current position and make it the first 
 *    node in the list.
 * 4. You need to implement an iterator. The iterator should not move elements
 *    to the front. The iterator should return elements in the order they are 
 *    stored in the list, starting with the first element in the list.
 * TODO: Develop appropriate JUnit tests for your MoveToFrontList.
 */
public class MoveToFrontList<E> extends DataCounter<E> {

	private MTFNode overallRoot; 
	private int size;
	private Comparator<? super E> comparator;

	public MoveToFrontList(Comparator<? super E> c) {
		this.comparator = c;
		this.size = 0;
		this.overallRoot = null;
	}

	/** {@inheritDoc} */
	public void incCount(E data) {
		if (this.overallRoot == null) {
			// first thing
			this.overallRoot = new MTFNode(data);
			this.size++;
			return;
		}
		// we have to linear search along
		MTFNode current = overallRoot;

		while (current != null) {
			if (comparator.compare(current.data, data) == 0) {
				// Found it!
				current.count++;

				if (current != this.overallRoot) {
					
					// if we aren't on the end, update the next node's prev
					if (current.next != null) { current.next.prev = current.prev; }
					
					// update the previous node's next
					current.prev.next = current.next;
					
					// and finally update the overall root to the current node
					this.overallRoot.prev = current;
					current.next = this.overallRoot;
					
					current.prev = null;
					this.overallRoot = current;
				}
				return;
			} else {
				current = current.next;
			}
		}

		//System.out.println("got here");
		// didn't find it; add to front
		this.size++;
		this.overallRoot = new MTFNode(data, this.overallRoot);
		this.overallRoot.next.prev = this.overallRoot;

	}

	/** {@inheritDoc} */
	public int getSize() {
		return size;
	}

	/** {@inheritDoc} */
	public int getCount(E data) {
		// we have to linear search along
		MTFNode current = overallRoot;

		while (current != null) {
			if (comparator.compare(current.data, data) == 0) {
				// Found it!
				// if we aren't on the end, update the next node's prev
				if (current.next != null) { current.next.prev = current.prev; }
				// if we aren't at the beginning, update the previous node's next
				if (current.prev != null) { current.prev.next = current.next; }
				// and finally update the overall root to the current node
				current.next = this.overallRoot;
				this.overallRoot = current;
				return current.count;
			}
			current = current.next;
		}
		return 0;
	}

	/** {@inheritDoc} */
	public SimpleIterator<DataCount<E>> getIterator() {
		
		return new SimpleIterator<DataCount<E>>() {  
    		MTFNode current = overallRoot;
    		
    		public boolean hasNext() {
        		return (current != null);
        	}
        	public DataCount<E> next() {
        		if(!hasNext()) {
        			throw new java.util.NoSuchElementException();
        		}
        		MTFNode old = current;
        		current = current.next;
        		return new DataCount<E>(old.data, old.count);
        	}
    	};
    }
		

	/** PRIVATE INNER CLASS ============= **/

	/** Inner class to represent a node in the move-to-front list. Each node has a 
	 * to the next reference
	 */
	private class MTFNode {
		public MTFNode next;
		public MTFNode prev;
		public E data;
		public int count;

		public MTFNode(E data, MTFNode next) {
//			if (((Integer) data).equals(7)) {
//				System.out.println("7 found!");
//			}
			this.next = next;
			this.prev = null;
			this.data = data;
			this.count = 1;
		}

		public MTFNode(E data) {
			this(data, null);
		}
		
		public String toString() {
			String result = "";
			if (next == null) { result += "Next: null "; } else { result += "Next: nonnull " + next.data; }
			if (prev == null) { result += "prev: null "; } else { result += "prev: nonnull "; }
			return result + " data: " + data;
		}
	}
}
