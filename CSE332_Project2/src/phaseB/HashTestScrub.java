package phaseB;

import providedCode.Comparator;
import providedCode.DataCount;
import providedCode.Hasher;
import providedCode.SimpleIterator;

public class HashTestScrub {

	public static void main(String[] args) {
		HashTable<Integer> intTable = new HashTable<Integer>(
				new Comparator<Integer>() {
					public int compare(Integer e1, Integer e2) { 
						return e1 - e2; 
						}
					}, new Hasher<Integer>() {
						public int hash(Integer data) {
							return Math.abs(data * 7 << 1);
						}
					});
		Integer[] ints = {1, 2, 3, 4, 5};
		for(Integer i: ints) {
			intTable.incCount(i);
		}
		SimpleIterator<DataCount<Integer>> it = intTable.getIterator();
		while (it.hasNext())
			System.out.println(it.next().data);
	}
}
