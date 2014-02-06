package phaseA;
import providedCode.Comparator;
import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.SimpleIterator;

/**
 * A MoveToFrontList is a DataCounter that stores the most recently accessed element
 * in the most easily accessible place.
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
			if (comparator.compare((E) current.dataCount.data, data) == 0) {
				// Found it!
				current.dataCount.count++;

				if (current != this.overallRoot) {
					
					// if we aren't on the end, update the next node's prev
					if (current.next != null) { current.next.prev = current.prev; }
					
					// update the previous node's next
					if (current.prev != null) { current.prev.next = current.next; }
					
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
			if (comparator.compare((E) current.dataCount.data, data) == 0) {
				// Found it!
				// if we aren't on the end, update the next node's prev
				if (current.next != null) { current.next.prev = current.prev; }
				// if we aren't at the beginning, update the previous node's next
				if (current.prev != null) { current.prev.next = current.next; }
				// and finally update the overall root to the current node
				current.next = this.overallRoot;
				this.overallRoot = current;
				return current.dataCount.count;
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
        		return new DataCount<E>((E) old.dataCount.data, old.dataCount.count);
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
		public DataCount dataCount;

		public MTFNode(E data, MTFNode next) {
			this.next = next;
			this.prev = null;
			this.dataCount.data = data;
			this.dataCount.count = 1;
		}

		public MTFNode(E data) {
			this(data, null);
		}

	}
}
