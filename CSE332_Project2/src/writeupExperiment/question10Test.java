package writeupExperiment;

import main.WordCount;

public class question10Test {
	
	private static final int NUM_TEST = 50;
	private static final int NUM_WARMUP = 2;
	
	public static void main(String[] args) {
		
		String[] ins = {"-m", "-is", "hamlet.txt"};
		System.out.println("IS: " + getAverageRuntime(ins));
		
		String[] hs = {"-m", "-hs", "hamlet.txt"};
		System.out.println("HS: " + getAverageRuntime(hs));
		
		String[] os = {"-m", "-os", "hamlet.txt"};
		System.out.println("OS: " + getAverageRuntime(os));
	}
	
	// Provided code: find averageRuntime of tested code
	private static double getAverageRuntime(String[] args) {
	    double totalTime = 0;
	    for(int i=0; i<NUM_TEST; i++) {
	      long startTime = System.currentTimeMillis();
	      WordCount.main(args);
	      long endTime = System.currentTimeMillis();
	      if(NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to encounter JVM warmup
	        totalTime += (endTime - startTime);
	      }
	    }
	    return totalTime / (NUM_TEST-NUM_WARMUP);  // Return average runtime.
	  }
	
}
