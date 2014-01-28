package phaseA;

import providedCode.Comparator;

public class ScrubTest {
	public static void main(String[] args) {
		Comparator<Integer> intComp = new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { 
				return e1 - e2; 
			}
		};
		FourHeap<Integer> heap = new FourHeap<Integer>(intComp);
		for (int i = 0; i < 200; i++)
			heap.insert(i);
		for (int i = 0; i < 200; i ++)
			heap.deleteMin();
		heap.printArray();
	}
}
