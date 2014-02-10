package phaseB;
import providedCode.Hasher;


public class StringHasher implements Hasher<String> {
	
	@Override
	public int hash(String s) {
		return Math.abs(s.charAt(0) * 31 + s.length());
	}
}
