package test;
import static org.junit.Assert.*;
import org.junit.*;
import providedCode.DataCounter;


public abstract class TestDataCounter<E> {
	protected DataCounter<E> dc;
	protected abstract DataCounter<E> getDataCounter();
	private static final int TIMEOUT = 2000; // 2000ms = 2sec
	
	@Before
	public void setUp() {
		dc = getDataCounter();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
}
