package phaseB;
import providedCode.Hasher;


public class StringHasher implements Hasher<String> {
	
	@Override
	public int hash(String s) {
		int hash = 31;
		for (int i = 0; i < s.length(); i++){
			hash += s.charAt(i) * 7;
		}
			
		return Math.abs(hash);
	}
}
