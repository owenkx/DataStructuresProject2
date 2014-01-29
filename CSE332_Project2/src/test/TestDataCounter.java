package test;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.*;

import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.SimpleIterator;


public abstract class TestDataCounter<E> {
        protected DataCounter<Integer> dc;
        protected abstract DataCounter<Integer> getDataCounter();
        private static final int TIMEOUT = 2000; // 2000ms = 2sec
        
        @Before
        public void setUp() {
                dc = getDataCounter();
        }
        
        @Test(timeout = TIMEOUT)
        public void testEmptySizeInitial() {
                assertEquals("Test the intial size, must be 0", dc.getSize(), 0);
        }
        
        @Test(timeout = TIMEOUT)
        public void testSizeAfterOneInsert() {
                addAndTestSize("Test the size after adding one number", new int[]{5}, 1);
        }
        
        @Test(timeout = TIMEOUT)
        public void testSizeAfterMultipleInserts() {
                int[] testArray = {1, 2, 3, 4, 5};
                addAndTestSize("Test size of unique inserts", testArray, 5);
        }
        
        @Test(timeout = TIMEOUT)
        public void testCountAfterOneInsert() {
                addAndGetCount("Test count after one insert", new int[]{5}, 5, 1);
        }
        
        @Test(timeout = TIMEOUT)
        public void testCountAfterMultipleInserts() {
                int[] testArray = {1, 2, 3, 4, 5};
                addAndGetCount("Test count of unique inserts", testArray, 1, 1);
        }
        
        @Test(timeout = TIMEOUT)
        public void testCountAfterDuplicateInserts() {
                int[] testArray = {1, 1, 1, 1, 1};
                addAndGetCount("Test count of duplicate inserts", testArray, 1, 5);
        }
        
        @Test(timeout = TIMEOUT)
        public void testMultipleDuplicates() {
                int[] testArray = {1, 1, 2, 2, 3, 3};
                addAndGetCount("Test count of multiple duplicate inserts", testArray, 1, 2);
                addAndGetCount("", testArray, 2, 4);
                addAndGetCount("", testArray, 3, 6);
        }
        
        @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
        public void test_iterator_empty() {
                SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
                iter.next(); 
        }
        
        @Test(timeout = TIMEOUT)
        public void test_iterator_get_all_data() {
                int[] startArray = {7, -5, -5, -6, 6, 10, -9, 4, 8, 6};
                
                for(int i = 0; i < startArray.length; i++) { dc.incCount(startArray[i]); }
                int[] expected = {-9, -6, -5, 4, 6, 7, 8, 10};
                
                int i = 0;
                SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
                int[] actual = new int[expected.length];
                while(iter.hasNext()) { actual[i++] = iter.next().data; }
                
                Arrays.sort(actual);
                assertArrayEquals("Added " + Arrays.toString(startArray), expected, actual);
        }

        private void addAndTestSize(String message, int[] input, int unique){
                for(int num : input) { dc.incCount(num); }
                assertEquals(message, unique, dc.getSize());
        }

        private void addAndGetCount(String message, int[] input, int key, int expected){
                for(int num : input) { dc.incCount(num); }
                assertEquals(message, expected, dc.getCount(key));
        }
}
