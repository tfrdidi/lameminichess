/*
 * Copyright � 2010 Stefan Liebler, Clemens Henker, Lukas Hahmann. All rights reserved.
 */
package lmc;

import java.util.*;

public class Controller {
	
    public final static int CAPTURE_MODE_ANY = 0;
    public final static int CAPTURE_MODE_ONLY = 1;
    public final static int CAPTURE_MODE_NEVER = 2;
    private final static int[][] CROSS_MOVES = {{0,1},{0,-1},{1,0},{-1,0}};
    private final static int[][] DIAGONAL_MOVES = {{-1,1},{1,1},{1,-1},{-1,-1}};
    private final static int[][] KNIGHT_MOVES = {{-1,2},{-1,-2},{1,2}, {1,-2}, {2,1},{2,-1}, {-2,1} , {-2,-1} };
	
	
	/**
     * Processes a move on the board.
     * 
     * @param		move		The <code>Move</code> to do
     */
	public char move(Board board , Move move) {
		if (move == null){
			//kein move m�glich
			return '=';
		}
		
		char ret = '?';
		
		char fromPiece = board.getSquare(move.from);
		char toPiece = board.getSquare(move.to);
		board.setSquare(move.from, Board.EMPTY_SQUARE_CHARACTER);
		
		//pawn promotion
		if (fromPiece == 'P'
			&& move.to.getRow() == 5)
		{
			fromPiece = 'Q';
		}else if (fromPiece == 'p'
			&& move.to.getRow() == 0)
		{
			fromPiece = 'q';
		}
		
		//set to piece
		board.setSquare(move.to, fromPiece);
		
		//analyse game state
		board.changePlayer();
		if (toPiece =='k'){
			ret = 'W';
		} else if (toPiece =='K'){
			ret = 'B';
		} else if (board.getRunCount() > 40) 
			ret = '=';
		
		return ret;
	}
	
	public ArrayList<Move> collectAllMoves(Board board) throws Exception{
		ArrayList<Move> moveList = new ArrayList<Move>(); 

		for (Square sq : board.getSquares(Square.MATE)) {
			addPossibleMoves(board, moveList, sq);
		}
		
		return moveList;
	}
	
	public void addPossibleMoves (
			Board board,
			ArrayList<Move> moveList, 
			Square from) throws Exception {
		char figure = board.getSquare(from);
		
		switch (figure) {
		case 'K':
		case 'k':
			addPossibleMoves(board, moveList, from, CROSS_MOVES, true, CAPTURE_MODE_ANY);
			addPossibleMoves(board, moveList, from, DIAGONAL_MOVES, true, CAPTURE_MODE_ANY);
			break;
		case 'Q':
		case 'q':
			addPossibleMoves(board, moveList, from, CROSS_MOVES, false, CAPTURE_MODE_ANY);
			addPossibleMoves(board, moveList, from, DIAGONAL_MOVES, false, CAPTURE_MODE_ANY);
			break;
		case 'B':
		case 'b':
			addPossibleMoves(board, moveList, from, CROSS_MOVES, true, CAPTURE_MODE_NEVER);
			addPossibleMoves(board, moveList, from, DIAGONAL_MOVES, false, CAPTURE_MODE_ANY);
			break;
		case 'N':
		case 'n':
			addPossibleMoves(board, moveList, from, KNIGHT_MOVES, true, CAPTURE_MODE_ANY);
			break;
		case 'R':
		case 'r':
			addPossibleMoves(board, moveList, from, CROSS_MOVES, false, CAPTURE_MODE_ANY);
			break;
		case 'P':
			addPossibleMoves(board, moveList, from, 0, 1, true, CAPTURE_MODE_NEVER);
			addPossibleMoves(board, moveList, from, -1, 1, true, CAPTURE_MODE_ONLY);
			addPossibleMoves(board, moveList, from, 1, 1, true, CAPTURE_MODE_ONLY);
			break;
		case 'p':
			addPossibleMoves(board, moveList, from, 0, -1, true, CAPTURE_MODE_NEVER);
			addPossibleMoves(board, moveList, from, -1, -1, true, CAPTURE_MODE_ONLY);
			addPossibleMoves(board, moveList, from, 1, -1, true, CAPTURE_MODE_ONLY);
			break;

		default:
			throw new Exception("You move an empty square... Wow!");
		}
		
		
	}
	
	protected void addPossibleMoves(
			Board board,
			ArrayList<Move> moveList, 
			Square from,
			int x, int y,
			boolean oneStep,
			int captureMode) throws Exception {
		
		
		Square to = (Square) from.clone();
		int squareAssignment;
		
		do {
		
			//calculate to and check border
			if (!to.changePosition(x,y)){
				break;
			}

			//check piece
			squareAssignment = board.getSquareAssignment(to);
			
			if (squareAssignment == Square.MATE) {
				//keine weiteren moves m�glich
				break;
			}
			
			if (captureMode == CAPTURE_MODE_ANY){
				//es darf leer oder gegner
				if (squareAssignment == Square.OPPONENT) {
					oneStep = true;
				}
			} else if (captureMode == CAPTURE_MODE_NEVER){
				//es darf nur leer
				if (squareAssignment == Square.OPPONENT){
					break;
				}
			} else { //capture_mode_only
				//es darf nur gegner
				if (squareAssignment == Square.EMPTY){
					break;
				}
				else { //squareAssignment == Square.OPPONENT) 
					oneStep = true;
				} 
			}

			moveList.add(
					new Move(from, 
							(Square) to.clone())
					);
			
		} while (!oneStep);
		
	}

	protected void addPossibleMoves(
			Board board,
			ArrayList<Move> moveList, 
			Square from,
			int[][] moves,
			boolean oneStep,
			int captureMode) throws Exception {
		
		for (int[] move : moves) {
			addPossibleMoves(board, moveList, from, move[0], move[1], oneStep, captureMode);
		}
	}
	
	protected int getScore(Square sq, Board board){
		int result = 0;
		
		char piece = board.getSquare(sq);
		
		switch (Character.toLowerCase(piece)) {
		case 'k':
			result = 1000;
			if(board.getRunCount() < 10) {
				if(board.getCurrentPlayer() == 'W' && sq.getRow() == 0 && sq.getColumn() == 4 && piece == 'K') {
					result += 10;
				} else if (board.getCurrentPlayer() == 'B' && sq.getRow() == 5 && sq.getColumn() == 0 && piece == 'k') {
					result += 10;
				}
			}
			break;
		case 'q':
			result = 10;
			break;
		case 'b':
			result = 3;
			break;
		case 'n':
			result = 3;
			break;
		case 'r':
			result = 5;
			break;
		case 'p':
			result = 1;
			if(board.getCurrentPlayer() == 'W' && sq.getRow() == 4 && piece == 'P') {
				result += 3;
			} else if (board.getCurrentPlayer() == 'B' && sq.getRow() == 1 && piece == 'p') {
				result += 3;
			}
			break;
		default: //case '.'
			break;
		}
		
		return result;
		
	}
	
	public int getBoardScore(Board board, int param) throws Exception {
		int result = 0;
	
		for (Square sq : board.getSquares(param)) {
			result += getScore(sq, board);
		}
		
		return result;
	}
	
	public int getBoardScore(Board board) throws Exception {
		return getBoardScore(board, Square.MATE)-getBoardScore(board, Square.OPPONENT);
	}
}
