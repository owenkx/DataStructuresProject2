package main;
import java.io.IOException;

import phaseA.*;
import phaseB.HashTable;
import phaseB.StringHasher;
import providedCode.*;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

	
	// Reads every word in the file and adds it to the appropriate DataCounter.
    private static void countWords(String file, DataCounter<String> counter) {
        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }
        }catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }
    }
    
    //Takes in a DataCounter, and returns an array of DataCounts
 	private static <E> DataCount<E>[] getCountsArray(DataCounter<E> counter) {
 		DataCount<E>[] dataCounts = (DataCount<E>[]) new DataCount[counter.getSize()];
 		SimpleIterator<DataCount<E>> iter = counter.getIterator();
 		for (int index = 0; iter.hasNext(); index++) {
 			dataCounts[index] = iter.next();
 		}
 		return dataCounts;
 	}
    
 	
    // IMPORTANT: Used for grading. Do not modify the printing format!
 	// You may modify this method if you want.
    private static void printDataCount(DataCount<String>[] counts) {
    	for (DataCount<String> c : counts) {
            System.out.println(c.count + "\t" + c.data);
        }
    }
    
    
    /** 
     * Takes in flags for the implementation of DataCounter and sorting algorithm
     * as well as the name of the .txt file. Parses the text file and stores data
     * count objects in the data counter implementation. Then uses a sorting algorithm
     * to implement a dictionary ADT
 	 */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: DataCounter implementation argument");
            System.err.println("Usage: specification of sorting algorithm");
            System.err.println("Usage: name of .txt file");
            System.exit(1);
        }
        
        
        //checks the first argument for which data counter implementation to use
        if (args[0].matches("^-[bmha]$")) {
        	DataCounter<String> counter;
	        if (args[0].equals("-b")) {
	        	counter = new BinarySearchTree<String>(new StringComparator());
	        } else if (args[0].equals("-m")) {
	        	counter = new MoveToFrontList<String>(new StringComparator());
	        } else if (args[0].equals("-h")) {
	        	counter = new HashTable<String>(new StringComparator(), new StringHasher());
	        } else {
	        	counter = new AVLTree<String>(new StringComparator());
	        } 
	        countWords(args[2], counter); 
	        DataCount<String>[] counts = getCountsArray(counter);
	        
	        //checks argument 2 for the sorting algorithm
	        if (args[1].equals("-is")) {
	        	Sorter.insertionSort(counts, new DataCountStringComparator());
	        } else if (args[1].equals("-hs")) {
	        	Sorter.heapSort(counts, new DataCountStringComparator());
	        } else if (args[1].equals("-os")) {
	        	Sorter.otherSort(counts,  new DataCountStringComparator());
	        } else if (args[1].equals("-k")) {
	        	//Sorter.topKSort(counts, new DataCountStringComparator());
	        }
	        
	        printDataCount(counts);
        }
    }
}
