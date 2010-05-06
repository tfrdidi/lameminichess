package lmc.Tests;
import java.util.ArrayList;

import lmc.*;
import static org.junit.Assert.*;


import org.junit.Test;


public class ControllerTests {
	
	@Test
	public void testScoring() {
		Controller ctrl = new Controller();
		Board board = null;
		ArrayList<Move> moveList = new ArrayList<Move>();
		
		try {
			board = new Board("1 B\n"
			        +"k....\n"
			        +".....\n"
			        +".....\n"
			        +".....\n"
			        +".....\n"
			        +"...PK\n");
			int score = ctrl.getBoardScore(board);
			assertEquals("Wrong score", -1, score);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testCtrlCollectAllMoves(){
		TestController ctrl = new TestController();
		Board board = null;
		ArrayList<Move> moveList = new ArrayList<Move>();

		try {
			//#1 startup board - white
			board = new Board();
			moveList = ctrl.collectAllMoves(board);
			assertEquals("#1 Wrong size of moveList!", 7, moveList.size());
			
			//#2 startup board -black
			board = new Board("1 B\n"
			        +"kqbnr\n"
			        +"ppppp\n"
			        +".....\n"
			        +".....\n"
			        +"PPPPP\n"
			        +"RNBQK\n");
			moveList = ctrl.collectAllMoves(board);
			assertEquals("#2 Wrong size of moveList!", 7, moveList.size());
			
			//#3 strange board - black
			board = new Board("1 B\n"
			        +"k...q\n"
			        +".....\n"
			        +".....\n"
			        +".....\n"
			        +"..npb\n"
			        +"r...K\n");
			
			//k = 3 + r = 8 + q = 10 + n = 5 + p = 2 + b = 5 == 33
			
			moveList = ctrl.collectAllMoves(board);
			assertEquals("#3 Wrong size of moveList!", 33, moveList.size());
			
			//#4 strange board - white
			board = new Board("100 w\n"
			        +"k...q\n"
			        +".....\n"
			        +".....\n"
			        +".....\n"
			        +"..npb\n"
			        +"r...K\n");
			
			
			moveList = ctrl.collectAllMoves(board);
			assertEquals("#3 Wrong size of moveList!", 3, moveList.size());
		} catch (Exception e) {
			fail("Exception was thrown: " + e);
		}
	}
	
	@Test
	public void testCtrlAutomaticMoves(){
		TestController ctrl = new TestController();
		Board board = null;
		ArrayList<Move> moveList = new ArrayList<Move>();

		try {
			//#1a Pawn
			board = new Board();
			ctrl.addPossibleMoves(board, moveList, new Square("a2"));
			assertEquals("#1a Wrong size of moveList!", 1, moveList.size());
			assertEquals("#1a Wrong move!", "a2-a3", moveList.get(0).toString());
			moveList.clear();
			//#1b Pawn
			board = new Board(
					"1 B\n"
			        +"kqbnr\n"
			        +"ppppp\n"
			        +".K...\n"
			        +".....\n"
			        +"PPPPP\n"
			        +"R.qQK\n");
			ctrl.addPossibleMoves(board, moveList, new Square("a5"));
			assertEquals("#1b Wrong size of moveList!", 2, moveList.size());
			moveList.clear();
			//#1c Pawn
			board = new Board(
					"1 W\n"
			        +"kqbnr\n"
			        +"ppppp\n"
			        +".....\n"
			        +"...b.\n"
			        +"..PPP\n"
			        +"R.qQK\n");
			ctrl.addPossibleMoves(board, moveList, new Square("e2"));
			assertEquals("#1c Wrong size of moveList!", 2, moveList.size());
			moveList.clear();
			
			//#2a Rook
			board = new Board();
			ctrl.addPossibleMoves(board, moveList, new Square("a1"));
			assertEquals("#2a Wrong size of moveList!", 0, moveList.size());
			moveList.clear();
			//#2b Rook
			board = new Board(
					"1 W\n"
			        +"kqbnr\n"
			        +"ppppp\n"
			        +".....\n"
			        +".....\n"
			        +"..PPP\n"
			        +"R.qQK\n");
			ctrl.addPossibleMoves(board, moveList, new Square("a1"));
			assertEquals("#2b Wrong size of moveList!", 6, moveList.size());
			moveList.clear();
			
			//#3a Knight
			board = new Board();
			ctrl.addPossibleMoves(board, moveList, new Square("b1"));
			assertEquals("#3a Wrong size of moveList!", 2, moveList.size());
			moveList.clear();
			//#3b Knight
			board = new Board(
					"1 B\n"
			        +"kqbnr\n"
			        +"ppppp\n"
			        +".....\n"
			        +".....\n"
			        +"..PPP\n"
			        +"R.qQK\n");
			ctrl.addPossibleMoves(board, moveList, new Square("d6"));
			assertEquals("#3b Wrong size of moveList!", 2, moveList.size());
			moveList.clear();

			
			//#4a Bishop
			board = new Board();
			ctrl.addPossibleMoves(board, moveList, new Square("c1"));
			assertEquals("#4a Wrong size of moveList!", 0, moveList.size());
			moveList.clear();
			//#4b Bishop
			board = new Board(
					"1 B\n"
			        +"kqbnr\n"
			        +"p.Ppp\n"
			        +"P....\n"
			        +".....\n"
			        +"..PPP\n"
			        +"R.qQK\n");
			ctrl.addPossibleMoves(board, moveList, new Square("c6"));
			assertEquals("#4b Wrong size of moveList!", 2, moveList.size());
			moveList.clear();
			//#4c Bishop
			board = new Board(
					"1 B\n"
			        +"kqbnr\n"
			        +"pp.pp\n"
			        +".....\n"
			        +".....\n"
			        +"..PPP\n"
			        +"R.qQK\n");
			ctrl.addPossibleMoves(board, moveList, new Square("c6"));
			assertEquals("#4c Wrong size of moveList!", 1, moveList.size());
			moveList.clear();
			
			
			//#5a Queen
			board = new Board();
			ctrl.addPossibleMoves(board, moveList, new Square("d1"));
			assertEquals("#5a Wrong size of moveList!", 0, moveList.size());
			moveList.clear();
			//#5b Queen
			board = new Board(
					"1 B\n"
			        +"kqbnr\n"
			        +"p.Ppp\n"
			        +"P....\n"
			        +".....\n"
			        +"..PPP\n"
			        +"R.qQK\n");
			ctrl.addPossibleMoves(board, moveList, new Square("b6"));
			assertEquals("#5b Wrong size of moveList!", 6, moveList.size());
			moveList.clear();
			
			//#6a King
			board = new Board();
			ctrl.addPossibleMoves(board, moveList, new Square("e1"));
			assertEquals("#6a Wrong size of moveList!", 0, moveList.size());
			moveList.clear();
			//#6b King
			board = new Board(
					"1 B\n"
			        +"qkbnr\n"
			        +"p.Ppp\n"
			        +"P....\n"
			        +".....\n"
			        +"..PPP\n"
			        +"R.qQK\n");
			ctrl.addPossibleMoves(board, moveList, new Square("b6"));
			assertEquals("#6b Wrong size of moveList!", 2, moveList.size());
			moveList.clear();
		} catch (Exception e) {
			fail("Exception was thrown: " + e);
		}
		
		try {
			board = new Board();
			ctrl.addPossibleMoves(board, moveList, new Square("b3"));
			fail("#7 no exception was thrown on trying to move an empty square");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		
		
	}
	
	@Test
	public void testCtrlAddPossibleMoves(){
		TestController ctrl = new TestController();
		Board board = null;
		ArrayList<Move> moveList = new ArrayList<Move>();

		//Start config, test some valid moves
		try {
			//#1 Pawn a2-a3 (valid)
			board = new Board(
					"1 W\n"
			        +"kqbnr\n"
			        +"ppppp\n"
			        +".....\n"
			        +".....\n"
			        +"PPPPP\n"
			        +"RNBQK\n");
			ctrl.testAddPossibleMoves(board, moveList, new Square("a2"),
					-1, 1, true, Controller.CAPTURE_MODE_ONLY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("a2"),
					0, 1, true, Controller.CAPTURE_MODE_NEVER);
			ctrl.testAddPossibleMoves(board, moveList, new Square("a2"),
					1, 1, true, Controller.CAPTURE_MODE_ONLY);
			assertEquals("#1 Wrong size of moveList!", 1, moveList.size());
			assertEquals("#1 Wrong move!", "a2-a3", moveList.get(0).toString());

			//#2 Rook a2-a3, a2-a4, a2-a5 (valid)
			moveList.clear();
			board = new Board(
					"1 W\n"
			        +"kqbnr\n"
			        +"ppppp\n"
			        +".....\n"
			        +".....\n"
			        +"RPPPP\n"
			        +"PNBQK\n");
			ctrl.testAddPossibleMoves(board, moveList, new Square("a2"),
					0, 1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("a2"),
					1, 0, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("a2"),
					0, -1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("a2"),
					-1, 0, false, Controller.CAPTURE_MODE_ANY);
			assertEquals("#2 Wrong size of moveList!", 3, moveList.size());
			assertEquals("#2 Wrong move!", "a2-a3", moveList.get(0).toString());
			assertEquals("#2 Wrong move!", "a2-a4", moveList.get(1).toString());
			assertEquals("#2 Wrong move!", "a2-a5", moveList.get(2).toString());
			
			//#3 Rook a3-a4, a3-a5, a3-b3 (valid)
			moveList.clear();
			board = new Board(
					"1 W\n"
			        +"kqbnr\n"
			        +"ppppp\n"
			        +".....\n"
			        +"Rp...\n"
			        +"PPPPP\n"
			        +"RNBQK\n");
			ctrl.testAddPossibleMoves(board, moveList, new Square("a3"),
					0, 1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("a3"),
					1, 0, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("a3"),
					0, -1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("a3"),
					-1, 0, false, Controller.CAPTURE_MODE_ANY);
			assertEquals("#3 Wrong size of moveList!", 3, moveList.size());
			assertEquals("#3 Wrong move!", "a3-a4", moveList.get(0).toString());
			assertEquals("#3 Wrong move!", "a3-a5", moveList.get(1).toString());
			assertEquals("#3 Wrong move!", "a3-b3", moveList.get(2).toString());
			
			//#4 Pawn d4-c5, d4-d5, d4-e5 (valid)
			moveList.clear();
			board = new Board(
					"1 W\n"
			        +"kqbnr\n"
			        +"ppp.p\n"
			        +"...P.\n"
			        +".....\n"
			        +"PPPPP\n"
			        +"RNBQK\n");
			ctrl.testAddPossibleMoves(board, moveList, new Square("d4"),
					-1, 1, true, Controller.CAPTURE_MODE_ONLY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("d4"),
					0, 1, true, Controller.CAPTURE_MODE_NEVER);
			ctrl.testAddPossibleMoves(board, moveList, new Square("d4"),
					1, 1, true, Controller.CAPTURE_MODE_ONLY);
			assertEquals("#4 Wrong size of moveList!", 3, moveList.size());
			assertEquals("#4 Wrong move!", "d4-c5", moveList.get(0).toString());
			assertEquals("#4 Wrong move!", "d4-d5", moveList.get(1).toString());
			assertEquals("#4 Wrong move!", "d4-e5", moveList.get(2).toString());

			//#5 Pawn a5 (invalid)
			moveList.clear();
			board = new Board(
					"1 W\n"
			        +"k.bnr\n"
			        +"Ppppp\n"
			        +".....\n"
			        +".....\n"
			        +"PPPPP\n"
			        +"RNBQK\n");
			ctrl.testAddPossibleMoves(board, moveList, new Square("a5"),
					-1, 1, true, Controller.CAPTURE_MODE_ONLY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("a5"),
					0, 1, true, Controller.CAPTURE_MODE_NEVER);
			ctrl.testAddPossibleMoves(board, moveList, new Square("a5"),
					1, 1, true, Controller.CAPTURE_MODE_ONLY);
			assertEquals("#5 Wrong size of moveList!", 0, moveList.size());
			
			//#6 Knight b1-a3, b1-c3 (valid)
			moveList.clear();
			board = new Board(
					"1 W\n"
			        +"k.bnr\n"
			        +"Ppppp\n"
			        +".....\n"
			        +".....\n"
			        +"PPPPP\n"
			        +"RNBQK\n");
			ctrl.testAddPossibleMoves(board, moveList, new Square("b1"),
					-2, 1, true, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("b1"),
					-1, 2, true, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("b1"),
					1, 2, true, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("b1"),
					2, 1, true, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("b1"),
					2, -1, true, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("b1"),
					1, -2, true, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("b1"),
					-1, -2, true, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("b1"),
					-2, -1, true, Controller.CAPTURE_MODE_ANY);
			assertEquals("#6 Wrong size of moveList!", 2, moveList.size());
			assertEquals("#6 Wrong move!", "b1-a3", moveList.get(0).toString());
			assertEquals("#6 Wrong move!", "b1-c3", moveList.get(1).toString());
			
			//#7 Queen at c4 (border test) (valid)
			moveList.clear();
			board = new Board(
					"1 W\n"
			        +".....\n"
			        +".....\n"
			        +"..Q..\n"
			        +".....\n"
			        +".....\n"
			        +".....\n");
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					-1, -1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					-1, 1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					1, 1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					1, -1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					0, -1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					0, 1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					-1, 0, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					1, 0, false, Controller.CAPTURE_MODE_ANY);
			assertEquals("#7 Wrong size of moveList!", 17, moveList.size());
						
			//#8 Bishop's special moves, starting at c4 (valid)
			moveList.clear();
			board = new Board(
					"100 W\n"
			        +"p.p..\n"
			        +"...p.\n"
			        +".pBp.\n"
			        +".ppp.\n"
			        +".....\n"
			        +".....\n");
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					-1, -1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					-1, 1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					1, 1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					1, -1, false, Controller.CAPTURE_MODE_ANY);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					0, -1, true, Controller.CAPTURE_MODE_NEVER);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					0, 1, true, Controller.CAPTURE_MODE_NEVER);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					-1, 0, true, Controller.CAPTURE_MODE_NEVER);
			ctrl.testAddPossibleMoves(board, moveList, new Square("c4"),
					1, 0, true, Controller.CAPTURE_MODE_NEVER);
			assertEquals("#8 Wrong size of moveList!", 6, moveList.size());
		} catch (Exception e) {
			fail("Exception was thrown: " + e);
		}
	}

	@Test
	public void testCtrlMove(){
		TestController ctrl = new TestController();
		Board board = null;
		char result = '?';
		Move move = null;

		//Start config, test some valid moves
		try {

			board = new Board(
					"1 W\n"
			        +"kqbn.\n"
			        +".ppp.\n"
			        +"....P\n"
			        +"R...r\n"
			        +"pPPP.\n"
			        +".NBQK\n");
			
			//#1 pawn move without pawn promotion ?
			move = new Move("e4-e5");
			result = ctrl.move(board, move);
			assertEquals("#1 false result!", '?', result);
			assertEquals("#1 fals from piece",'.', board.getSquare(move.getFrom()));
			assertEquals("#1 fals to piece",'P', board.getSquare(move.getTo()));
			assertEquals("#1 next player is false",'B', board.getCurrentPlayer());
			assertEquals("#1 next runCount is false",1, board.getRunCount());
			
		
			//#2 pawn move with pawn promotion - black / white ?
			move = new Move("a2-a1");
			result = ctrl.move(board, move);
			assertEquals("#2a false result!", '?', result);
			assertEquals("#2a fals from piece",'.', board.getSquare(move.getFrom()));
			assertEquals("#2a fals to piece",'q', board.getSquare(move.getTo()));
			assertEquals("#2a next player is false",'W', board.getCurrentPlayer());
			assertEquals("#2a next runCount is false",2, board.getRunCount());
			
			move = new Move("e5-e6");
			result = ctrl.move(board, move);
			assertEquals("#2b false result!", '?', result);
			assertEquals("#2b fals from piece",'.', board.getSquare(move.getFrom()));
			assertEquals("#2b fals to piece",'Q', board.getSquare(move.getTo()));
			assertEquals("#2b next player is false",'B', board.getCurrentPlayer());
			assertEquals("#2b next runCount is false",2, board.getRunCount());
			
			//#3 kill white king W
			move = new Move("e3-e1");
			result = ctrl.move(board, move);
			assertEquals("#3 false result!", 'B', result);
			assertEquals("#3 fals from piece",'.', board.getSquare(move.getFrom()));
			assertEquals("#3 fals to piece",'r', board.getSquare(move.getTo()));
			assertEquals("#3 next player is false",'W', board.getCurrentPlayer());
			assertEquals("#3 next runCount is false",3, board.getRunCount());
			
			//#4 kill black king B
			move = new Move("a3-a6");
			result = ctrl.move(board, move);
			assertEquals("#4 false result!", 'W', result);
			assertEquals("#4 fals from piece",'.', board.getSquare(move.getFrom()));
			assertEquals("#4 fals to piece",'R', board.getSquare(move.getTo()));
			assertEquals("#4 next player is false",'B', board.getCurrentPlayer());
			assertEquals("#4 next runCount is false",3, board.getRunCount());

			
			//#5 runcount > 40 =
			board = new Board(
					"40 B\n"
			        +"kqbn.\n"
			        +".ppp.\n"
			        +"....P\n"
			        +"R...r\n"
			        +"pPPP.\n"
			        +".NBQK\n");
			move = new Move("b5-b4");
			result = ctrl.move(board, move);
			assertEquals("#5 false result!", '=', result);
			assertEquals("#5 fals from piece",'.', board.getSquare(move.getFrom()));
			assertEquals("#5 fals to piece",'p', board.getSquare(move.getTo()));
			assertEquals("#5 next player is false",'W', board.getCurrentPlayer());
			assertEquals("#5 next runCount is false",41, board.getRunCount());
			
			//#6 last possible black win
			board = new Board(
					"40 B\n"
			        +"kqbn.\n"
			        +".ppp.\n"
			        +"....P\n"
			        +"R...r\n"
			        +"pPPP.\n"
			        +".NBQK\n");
			move = new Move("e3-e1");
			result = ctrl.move(board, move);
			assertEquals("#6 false result!", 'B', result);
			assertEquals("#6 fals from piece",'.', board.getSquare(move.getFrom()));
			assertEquals("#6 fals to piece",'r', board.getSquare(move.getTo()));
			assertEquals("#6 next player is false",'W', board.getCurrentPlayer());
			assertEquals("#6 next runCount is false",41, board.getRunCount());
			
			//#7 no move available =
			board = new Board();
			move = null;
			result = ctrl.move(board, move);
			assertEquals("#6 false result!", '=', result);
			

		} catch (Exception e) {
			fail("Exception was thrown: " + e);
		}
		
		

		
	}
}

class TestController extends Controller{
	public void testAddPossibleMoves(
			Board board,
			ArrayList<Move> moveList, 
			Square from,
			int x, int y,
			boolean oneStep,
			int captureMode) throws Exception {
		addPossibleMoves(board, moveList, from, x, y, oneStep, captureMode);
	}
}