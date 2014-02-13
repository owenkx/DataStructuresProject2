package writeupExperiment;

import main.WordCount;

public class hashingTest {
	private static final int NUM_TEST = 25;
	private static final int NUM_WARMUP = 2;
	
	public static void main(String[] args) {
		testDataImplementation("Current Hash Function", "-h", "hamlet.txt");
		testDataImplementation("New Hash Function", "-h2", "hamlet.txt");
	}
	
	private static void testDataImplementation(String name, String dcType, String txtfile) {
		System.out.println("Testing " + name);
		String[] ins = {dcType, "-is", txtfile};
		System.out.println("IS: " + getAverageRuntime(ins));
		
		String[] hs = {dcType, "-hs", txtfile};
		System.out.println("HS: " + getAverageRuntime(hs));
		
		String[] os = {dcType, "-os", txtfile};
		System.out.println("OS: " + getAverageRuntime(os));
	}
	
	private static double getAverageRuntime(String[] args) {
	    double totalTime = 0;
	    for(int i=0; i < NUM_TEST; i++) {
	      long startTime = System.currentTimeMillis();
	      WordCount2.main(args);
	      long endTime = System.currentTimeMillis();
	      if(NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to encounter JVM warmup
	        totalTime += (endTime - startTime);
	      }
	    }
	    return totalTime / (NUM_TEST-NUM_WARMUP);  // Return average runtime.
	  }
}
