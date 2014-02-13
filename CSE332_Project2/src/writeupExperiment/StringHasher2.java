package writeupExperiment;
import providedCode.Hasher;


public class StringHasher2 implements Hasher<String> {
	
	@Override
	public int hash(String s) {
		int sum = 0;
		for (int i = 0; i < s.length(); i++) {
			sum += s.charAt(i) * 37;
		}
		return Math.abs(sum);
	}
}
