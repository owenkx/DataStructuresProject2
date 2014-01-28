package main;
import java.io.IOException;

import phaseA.*;
import providedCode.*;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

	
	// TODO: Replace this comment with your own as appropriate.
	// You may modify this method if you want.
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
    
    
    // TODO: Replace this comment with your own as appropriate.
    // Implement method that returns an array of DataCount objects containing each unique word.
    // If generics confuse you, write non-generic version first and then adjust it.
 	private static <E> DataCount<E>[] getCountsArray(DataCounter<E> counter) {
 		DataCount<E>[] dataCounter = (DataCount<E>[]) new DataCount[counter.getSize()];
 		SimpleIterator<DataCount<E>> iter = counter.getIterator();
 		int index = 0;
 		while (iter.hasNext()) {
 			dataCounter[index] = iter.next();
 			index++;
 		}
 		return dataCounter;
 	}
    
 	
    // IMPORTANT: Used for grading. Do not modify the printing format!
 	// You may modify this method if you want.
    private static void printDataCount(DataCount<String>[] counts) {
    	for (DataCount<String> c : counts) {
            System.out.println(c.count + "\t" + c.data);
        }
    }
    
    
    /** 
     *  TODO: Replace this comment with your own as appropriate.
 	 *  Edit this method (including replacing the dummy parameter checking below) 
 	 *  to process all parameters as shown in the spec.
 	 */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: filename of document to analyze");
            System.err.println("Usage: DataCounter implementation argument");
            System.err.println("Usage: specification of sorting algorithm");
            System.exit(1);
        }
        
        DataCounter<String> counter;
        if (args[0].equals("-b")) {
        	counter = new BinarySearchTree<String>(new StringComparator());
        } else if (args[0].equals("-m")) {
        	counter = new MoveToFrontList<String>(new StringComparator());
        } else {
        	counter = new AVLTree<String>(new StringComparator());
        }
        countWords(args[2], counter); 
        DataCount<String>[] counts = getCountsArray(counter);
        
        if (args[1].equals("-is")) {
        	Sorter.insertionSort(counts, new DataCountStringComparator());
        } else {
        	Sorter.heapSort(counts, new DataCountStringComparator());
        }
        
        printDataCount(counts);
    }
}
