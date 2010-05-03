import static org.junit.Assert.*;
import lmc.Board;
import lmc.Square;

import org.junit.Test;



public class SquareTests {

	
	@Test
	public void testSquareConstructors(){
	
		String[] testNiceSquares = {"a1","A1","e6"};
		int[][] testNiceResult = {{0,0},{0,0},{4,5}};
		String[] testUglySquares = {"","a9","f0", null, "a1snd", "sadf", "a","e", "11","-1", "0a"};

		for (int i = 0; i < testNiceResult.length; i++) {
			int[] expected = testNiceResult[i];
			
			try {
				Square sq = new Square(expected[0], expected[1]);
				assertEquals("false column with nice string", expected[0], sq.getColumn());
				assertEquals("false row with nice string", expected[1], sq.getRow());
			} catch (Exception e) {
				fail("Exception was thrown with Square(" + expected[0] + "," + expected[1] + "): " + e);
			}
		}

		for (int i = 0; i < testNiceSquares.length; i++) {
			String string = testNiceSquares[i];
			int[] expected = testNiceResult[i];
			
			try {
				Square sq = new Square(string);
				assertEquals("false column with nice string", expected[0], sq.getColumn());
				assertEquals("false row with nice string", expected[1], sq.getRow());
			} catch (Exception e) {
				fail("Exception was thrown with Square(" + string + "): " + e);
			}
		}
		
		
		for (String string : testUglySquares) {
			try {
				new Square(string);
				fail("Exception wasn´t thrown with Square(" + string + ")");
			} catch (Exception e) {
			}
		}
	}
	
}
