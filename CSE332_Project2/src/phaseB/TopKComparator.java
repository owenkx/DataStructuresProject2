package phaseB;

import phaseA.StringComparator;
import providedCode.Comparator;
import providedCode.DataCount;


/**
 * A Comparator for DataCount<String> that sorts two data-counts in the order
 * we need for the output of WordCount: /LOWER/ frequency comes first and
 * frequency ties are resolved in /reverse/ alphabetical order.
 * Uses StringComparator, which you must implement.
 */
public class TopKComparator implements Comparator<DataCount<String>>{
	StringComparator alphabetical = new StringComparator();
	public int compare(DataCount<String> c1, DataCount<String> c2) {
		if(c1.count != c2.count) {
			return c1.count - c2.count;
		}
		return alphabetical.compare(c2.data, c1.data);
	}


}