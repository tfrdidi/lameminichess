import org.junit.*;
import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;
import lmc.Board;


public class BoardTests extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	
	@Test
	public void testBoardConstructors(){
		Board b = null;
		 
		try {
			b = new Board();
		} catch (Exception e) {
			fail("Exception was thrown with Board(): " + e);
		}
		
		try {
			b = new Board("1 W\n"
					+"kqnbr\n"
					+"ppppp\n"
					+".....\n"
					+".....\n"
					+"PPPPP\n"
					+"RNBQK");
		} catch (Exception e) {
			fail("Exception was thrown with Board(string)");			
		}
				
	}
	

}
