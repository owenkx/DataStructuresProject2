package phaseA;
import providedCode.*;

/**
 * TODO: Replace this comment with your own as appropriate.
 * This comparator is used by the provided code for both data-counters and 
 * sorting. Because of how the output must be sorted in the case of ties, 
 * your implementation should return a negative number when the first argument
 * to compare comes first alphabetically. Do not use any String comparison 
 * provided in Java's standard library; the only String methods you should 
 * use are length and charAt.
 */
public class StringComparator implements Comparator<String>{
	
	@Override
	public int compare(String s1, String s2) {
		int length = s1.length();
		if (s1.length() > s2.length())
			length = s2.length();
		
		for (int i = 0; i < length; i++) {
			char char1 = s1.charAt(i);
			char char2 = s2.charAt(i); 
			if (char1 < char2)
				return -1;
			else if (char2 < char1)
				return 1;
		}
		
		return s1.length() - s2.length();
	}
}
