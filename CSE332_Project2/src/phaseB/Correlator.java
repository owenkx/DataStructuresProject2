package phaseB;

import java.io.IOException;

import phaseA.AVLTree;
import phaseA.MoveToFrontList;
import phaseA.StringComparator;
import providedCode.BinarySearchTree;
import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.FileWordReader;
import providedCode.SimpleIterator;


public class Correlator {
	
	//Takes in a file and a data counter, puts every single word in the data counter
	//
	private static int countWords(String file, DataCounter<String> counter) {
		int sum = 0;
		try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            sum++;
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
                sum++;
            }
        }catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }
		return sum;
    }
	
	
	private static double findCorrelation(DataCounter<String> counter1, DataCounter<String> counter2, int size1, int size2) {
		SimpleIterator<DataCount<String>> it = counter1.getIterator();
		double sum = 0.0;
		while (it.hasNext()) {
			DataCount<String> data1 = it.next();
			double count1 = (double) data1.count / size1;
			double count2 = (double) counter2.getCount(data1.data) / size2;
			if (count2 != 0 && !isOutlier(count1) && !isOutlier(count2)) {
				sum += (count1 - count2) * (count1 - count2);
			}
				
		}
		return sum;
	}
	
	//Takes in a double and determines whether or not it's insignificantly
	//small or way too large.
	private static boolean isOutlier(double d) {
		return d > 0.01 || d < 0.0001;
	}
	
    public static void main(String[] args) {
    	double variance = 0.0;  
    	if (args.length != 3 && !args[0].matches("^-[bmhas]$")) {
    		System.out.println("Please input valid data counter implementations");
    		System.exit(1);
    	}
    	
    	DataCounter<String> counter1;
    	DataCounter<String> counter2;
    	
    	if (args[0].equals("-b")){
    		counter1 = new BinarySearchTree<String>(new StringComparator());
    		counter2 = new BinarySearchTree<String>(new StringComparator());
    	} else if (args[0].equals("-m")) {
    		counter1 = new MoveToFrontList<String>(new StringComparator());
    		counter2 = new MoveToFrontList<String>(new StringComparator());
    	} else if (args[0].equals("-h")) {
    		counter1 = new HashTable<String>(new StringComparator(), new StringHasher());
    		counter2 = new HashTable<String>(new StringComparator(), new StringHasher());
    	} else {
    		counter1 = new AVLTree<String>(new StringComparator());
    		counter2 = new AVLTree<String>(new StringComparator());
    	}	
    	int size1 = countWords(args[1], counter1);
    	int size2 = countWords(args[2], counter2);
    	variance = findCorrelation(counter1, counter2, size1, size2);
    	
    	System.out.println(variance);  // IMPORTANT: Do not change printing format. Just print the double.
    }
    
}
