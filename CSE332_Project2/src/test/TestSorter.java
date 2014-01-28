package test;
import static org.junit.Assert.*;

import main.Sorter;

import org.junit.Before;
import org.junit.Test;

import providedCode.Comparator;


public class TestSorter {
	private static final int TIMEOUT = 2000; // 2000ms = 2sec
	private Sorter sorter;
	private Comparator<Integer> intcomp;
	
	@Before
	public void setup() {
		sorter = new Sorter();
		intcomp = new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { 
				return e1 - e2; 
			}
		};
	}
	
	@Test(timeout = TIMEOUT)
	public void test() {
	}

}
