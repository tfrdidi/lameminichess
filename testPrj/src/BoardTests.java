import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.*;
import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;
import lmc.Board;
import lmc.Controller;
import lmc.Square;


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
			b = new Board("1 w\n"
					+"kqnbr\n"
					+"ppppp\n"
					+".....\n"
					+".....\n"
					+"PPPPP\n"
					+"RNBQK");
			assertEquals("false run count!", 1, b.getRunCount());
			assertEquals("false current player!", 'W', b.getCurrentPlayer());
			
			b = new Board("10 W\n"
					+"kqnbr\n"
					+"ppppp\n"
					+".....\n"
					+".....\n"
					+"PPPPP\n"
					+"RNBQK");
			assertEquals("false run count!", 10, b.getRunCount());
			assertEquals("false current player!", 'W', b.getCurrentPlayer());
			assertEquals("false figure!", Square.OPPONENT, b.getSquareAssignment(new Square("a6")));
			assertEquals("false figure!", Square.MATE, b.getSquareAssignment(new Square("a1")));
			assertEquals("false figure!", Square.EMPTY, b.getSquareAssignment(new Square("c3")));
			
			b = new Board("20 b\n"
					+"kqnbr\n"
					+"ppppp\n"
					+".....\n"
					+".....\n"
					+"PPPPP\n"
					+"RNBQK");
			assertEquals("false run count!", 20, b.getRunCount());
			assertEquals("false current player!", 'B', b.getCurrentPlayer());
			
			b = new Board("39 B\n"
					+"kqnbr\n"
					+"ppppp\n"
					+".....\n"
					+".....\n"
					+"PPPPP\n"
					+"RNBQK");
			assertEquals("false run count!", 39, b.getRunCount());
			assertEquals("false current player!", 'B', b.getCurrentPlayer());
			assertEquals("false figure!", 'r', b.getSquare(new Square("e6")));
			assertEquals("false figure!", 'K', b.getSquare(new Square("e1")));
			assertEquals("false figure!", 'k', b.getSquare(new Square("a6")));
			assertEquals("false figure!", 'R', b.getSquare(new Square("a1")));
			assertEquals("false figure!", '.', b.getSquare(new Square("c3")));
			assertEquals("false figure!", Square.MATE, b.getSquareAssignment(new Square("a6")));
			assertEquals("false figure!", Square.OPPONENT, b.getSquareAssignment(new Square("a1")));
			assertEquals("false figure!", Square.EMPTY, b.getSquareAssignment(new Square("c3")));
		} catch (Exception e) {
			fail("Exception was thrown with Board(string)");			
		}
			
		
		try {	
			b = new Board("39 B\n"
					+"kqnbr\n"
					+"pppp\n"
					+".....\n"
					+".....\n"
					+"PPPPP\n"
					+"RNBQK");
			fail("Exception wasn´t thrown with Board(not complete string)");
			
		} catch (Exception e) {

		}
		
		try {	
			b = new Board(
					"kqnbr\n"
					+"ppppp\n"
					+".....\n"
					+".....\n"
					+"PPPPP\n"
					+"RNBQK");
			fail("Exception wasn´t thrown with Board(not complete string)");
			
		} catch (Exception e) {

		}
		
		try {	
			b = new Board("39 B\n"
					+"ppppp\n"
					+".....\n"
					+".....\n"
					+"PPPPP\n"
					+"RNBQK");
			fail("Exception wasn´t thrown with Board(not complete string)");
			
		} catch (Exception e) {

		}

		try {	
			b = new Board("sd B\n"
					+"kqnbr\n"
					+"ppppp\n"
					+".....\n"
					+".....\n"
					+"PPPPP\n"
					+"RNBQK");
			fail("Exception wasn´t thrown with Board(not complete string)");
			
		} catch (Exception e) {

		}
	}
	

	@Test
	public void testBoardFileHandling(){
		try {
			String fName = "testBoardFileHandling.txt";
			File file = new File(fName);
			file.delete();
			
			Board b = new Board("10 b\n"
					+"kqnbr\n"
					+"ppppp\n"
					+".....\n"
					+".p...\n"
					+"PPPPP\n"
					+"RNBQK");
			Board.toFile(b, fName);
			
			b = Board.fromFile(fName);
			assertEquals("false figure!", 'p', b.getSquare(new Square("b3")));
			assertEquals("false run count!", 10, b.getRunCount());
			assertEquals("false current player!", 'B', b.getCurrentPlayer());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception was thrown with Board(stream)");
		}
		
		try {
			String fName = "testBoardFileHandling2.txt";
			File file = new File(fName);
			file.delete();
			file.createNewFile();
			
	
			Board b = Board.fromFile(fName);
			
			fail("Exception wasn´t thrown with Board(empty file stream)");
			
		} catch (Exception e) {

		}
	}
	
	@Test
	public void testClone(){
		try {
			Board a = new Board();
			Board b = (Board) a.clone();
			
			assertEquals("Clone is mist", a.toString(), b.toString());
			
			a.setSquare(new Square("a1"), '.');
			assertFalse("Clone is mist", a.toString().equals(b.toString()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSquareAssignments(){
		try {
			int assignment;
			Board a = new Board();
			
			assignment = a.getSquareAssignment(new Square("a1"));
			assertEquals("#1 false assignment",Square.MATE,assignment);
			assignment = a.getSquareAssignment(new Square("a6"));
			assertEquals("#1 false assignment",Square.OPPONENT,assignment);
			assignment = a.getSquareAssignment(new Square("c3"));
			assertEquals("#1 false assignment",Square.EMPTY,assignment);

			
			a = new Board("1 B\n"
			        +"kqbnr\n"
			        +"ppppp\n"
			        +".....\n"
			        +".....\n"
			        +"PPPPP\n"
			        +"RNBQK\n");
			
			assignment = a.getSquareAssignment(new Square("a1"));
			assertEquals("#1 false assignment",Square.OPPONENT,assignment);
			assignment = a.getSquareAssignment(new Square("a6"));
			assertEquals("#1 false assignment",Square.MATE,assignment);
			assignment = a.getSquareAssignment(new Square("c3"));
			assertEquals("#1 false assignment",Square.EMPTY,assignment);

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
