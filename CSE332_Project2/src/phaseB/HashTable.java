package phaseB;
import phaseA.MoveToFrontList;
import providedCode.*;


/**
 * 1. You may implement any kind of HashTable discussed in class; the only 
 *    restriction is that it should not restrict the size of the input domain 
 *    (i.e., it must accept any key) or the number of inputs (i.e., it must 
 *    grow as necessary).
 * 2. You should use this implementation with the -h option.
 * 3. To use your HashTable for WordCount, you will need to be able to hash 
 *    strings. Implement your own hashing strategy using charAt and length. 
 *    Do not use Java's hashCode method.
 * TODO: Develop appropriate JUnit tests for your HashTable.
 */
public class HashTable<E> extends DataCounter<E> {
	private MoveToFrontList<E>[] buckets;
	private Hasher h;
	private Comparator<? super E> c;
	private int size;
	
	private final int DEFAULT_CAPACITY = 37;
	private final double RESIZE_AT_LOAD_FACTOR_OF = 1.0;
	private final int RESIZE_FACTOR = 2;
	
	public HashTable(Comparator<? super E> c, Hasher<E> h) {
		buckets = (MoveToFrontList<E>[]) new MoveToFrontList[DEFAULT_CAPACITY];
		for (int i = 0; i < buckets.length; i++)
			buckets[i] = new MoveToFrontList(c);
		
		this.c = c;
		this.h = h;
		this.size = 0;
	}
	
	/** {@inheritDoc} */
	public void incCount(E data) {
		incCount(data, 1);
	}

	/** {@inheritDoc} */
	public int getSize() {
		return size;
	}

	/** {@inheritDoc} */
	public int getCount(E data) {
		MoveToFrontList<E> findBucket = buckets[h.hash(data) % buckets.length];
		return findBucket.getCount(data);
	}

	/** {@inheritDoc} */

	public SimpleIterator<DataCount<E>> getIterator() {
		return new SimpleIterator<DataCount<E>>() {
			SimpleIterator<DataCount<E>> currentChain = buckets[0].getIterator();
			int bucketIndex = 0;
			
			@Override
			public DataCount<E> next() {
				
				//finds and verifies the right chain
				if (hasNext())
					return currentChain.next();
				throw new java.util.NoSuchElementException();
			}
			
			@Override
			public boolean hasNext() {
				
				//iterate through the buckets
				while (bucketIndex < buckets.length) {
					
					//if current chain is valid and/or has a next value
					if (currentChain.hasNext()) {
						return true;
					} else {
						if (++bucketIndex < buckets.length)
							currentChain = buckets[bucketIndex].getIterator();
					}
				}
				return false;
			}
		};
	}
	
	//Takes in a datum and a count, and 
	//either increments the data count by the count
	//or inserts a data count with the count.
	private void incCount(E data, int count) {
		
		int insertIndex = h.hash(data) % buckets.length;
		MoveToFrontList<E> insertBucket = buckets[insertIndex];
		
		//find the bucket to increment
		int oldBucketSize = insertBucket.getSize();
		insertBucket.incCount(data, count);
		size += insertBucket.getSize() - oldBucketSize;
		
		//check load factor and rehash
		if (pastLoadFactor()) {
			rehash();
		}
	}
	
	//Tests if the load factor is above the designated
	//load factor for resizing.
	private boolean pastLoadFactor() {
		return ((double) size / buckets.length) > RESIZE_AT_LOAD_FACTOR_OF;
	}
	
	//Creates a new hashtable of prime size and rehashes all of the current elements into the 
	//table.
	private void rehash() {
		
		//Allocate a new list
		MoveToFrontList<E>[] newTable = (MoveToFrontList<E>[]) new MoveToFrontList[newSize()];
		for (int i = 0; i < newTable.length; i++)
			newTable[i] = new MoveToFrontList(c);
		
		//Iterate through the hash table
		SimpleIterator<DataCount<E>> it = getIterator();
		while (it.hasNext()) {
			DataCount<E> insertData = it.next();
			
			//make a copy of the object you found;
			DataCount<E> newData = new DataCount<E>(insertData.data, insertData.count);
			MoveToFrontList<E> currentTable = newTable[h.hash(insertData.data) % newTable.length];
			
			//increment it
			currentTable.addToFront(newData.data, newData.count);
		}
		buckets = newTable;
	}
	
	//returns the next prime greater than the resize factor.
	private int newSize(){
		int val = buckets.length * RESIZE_FACTOR;
		while (!isPrime(val))
			val++;
		return val;
	}
	
	//tests if a number is prime or not
	private boolean isPrime(int n) {
		if (n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}
	
}
