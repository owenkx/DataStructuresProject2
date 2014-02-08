package phaseB;
import phaseA.MoveToFrontList;
import providedCode.*;


/**
 * TODO: Replace this comment with your own as appropriate.
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
	private int size;
	private Hasher h;
	private Comparator<? super E> c;
	
	private final int DEFAULT_CAPACITY = 5;
	
	private final double RESIZE_AT_LOAD_FACTOR_OF = 1.0;
	private final int RESIZE_FACTOR = 2;
	
	public HashTable(Comparator<? super E> c, Hasher<E> h) {
		this.buckets = (MoveToFrontList<E>[]) new MoveToFrontList[DEFAULT_CAPACITY];
		this.c = c;
		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = new MoveToFrontList<E>(this.c);
		}
		this.h = h;
		size = 0;
	}
	
	@Override
	public void incCount(E data) {
		int insertIndex = h.hash(data) % buckets.length;
		MoveToFrontList<E> insertBucket = buckets[insertIndex];
		insertBucket.incCount(data);
		size++;
		if (pastLoadFactor()) {
			rehash();
		}
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getCount(E data) {
		MoveToFrontList<E> findBucket = buckets[h.hash(data) % buckets.length];
		return findBucket.getCount(data);
	}

	@Override
	public SimpleIterator<DataCount<E>> getIterator() {
		return new SimpleIterator<DataCount<E>>() {
			SimpleIterator<DataCount<E>> currentChain = buckets[0].getIterator();
			int bucketIndex = 0;
			
			@Override
			public DataCount<E> next() {
				
				while (bucketIndex < buckets.length) {
					if (currentChain.hasNext()) {
						return currentChain.next();
					} else {
						if (++bucketIndex < buckets.length || buckets[bucketIndex] != null)
							currentChain = buckets[bucketIndex].getIterator();
					}
				}
				throw new java.util.NoSuchElementException();
			}
//				//no more elements in the hashTable
//				if (this.hasNext()) {
//					//if there is another element in the current list
//					if (currentChain.hasNext()){
//						dataIndex++;
//						return currentChain.next();
//					//if not, look in other buckets for a nonempty list.
//					} else {
//						while (++bucketIndex < buckets.length) {
//							MoveToFrontList<E> tempBucket = buckets[bucketIndex];
//							if (tempBucket != null && tempBucket.getSize() != 0) {
//								currentChain = tempBucket.getIterator();
//								dataIndex++;
//								return currentChain.next();
//							}
//						}
//					}

			@Override
			public boolean hasNext() {
				
			}  
		};
	}
	
	private boolean pastLoadFactor() {
		return ((double) size / buckets.length) > RESIZE_AT_LOAD_FACTOR_OF;
	}
	
	private void rehash() {
		MoveToFrontList<E>[] newTable = (MoveToFrontList<E>[]) new MoveToFrontList[newSize()];
		for (int i = 0; i < newTable.length; i++) {
			newTable[i] = new MoveToFrontList<E>(c);
		}
		SimpleIterator<DataCount<E>> it = getIterator();
		while (it.hasNext()) {
			E insertData = it.next().data;
			DataCount<E> newData = new DataCount<E>(insertData, 1);
			MoveToFrontList<E> currentTable = newTable[h.hash(insertData) % newTable.length];
			currentTable.incCount(newData.data);
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
	
	private boolean isPrime(int n) {
		if (n % 2 == 0)
			return false;
		for (int i = 3; i < n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}
	
}
