/*
 * Copyright © 2010 Stefan Liebler, Clemens Henker, Lukas Hahmann. All rights reserved.
 */
package lmc;

import java.io.*;

/**
 * The class <code>Board</code> represents a 5x6 mini chess board 
 * and basic I/O to manage fundamental communication.
 *
 * @author  
 * 	Stefan Liebler <liebler.stefan@googlemail.com>,
 *  Clemens Henker <clemens.henker@googlemail.com>, 
 *  Lukas Hahmann <tfr.didi@googlemail.com>
 * @version 0.1 2010/05/03
 * @since 0.1
 */
public class Board {

    /**
     * Constructs a new mini chess board with default start configuration.
     * 
     * @exception  Exception  If default initialization string is malformed
     */
	public Board() throws Exception {
		initBoard("1 W\n"
		        +"kqbnr\n"
		        +"ppppp\n"
		        +".....\n"
		        +".....\n"
		        +"PPPPP\n"
		        +"RNBQK\n");
	}
	
	/**
     * Constructs a new mini chess board at an individual state per <code>String</code>.
     * 
     * @param state A string, representing the game start state
     * 
     * @exception  Exception  If <code>state</code> is malformed
     */
	public Board(String state) throws Exception {
		initBoard(state);
	}
	
	/**
     * Constructs a new mini chess board at an individual state per <code>InputStreamReader</code>.
     * 
     * @param reader An input stream, which initializes the board
     * 
     * @exception  Exception  If reader is malformed
     */
	public Board(InputStream reader) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(reader));
		
		String all = "";
		
		for (int i = 0; i < 7; i++) {
			all += br.readLine();
			all += "\n";
		}
		
		initBoard(all);
	}
	
	/*
	 * [row][column]
	 */
	private char[][] board;
	
	private static String validBoardCharacters = ".PRNBQKprnbqk";
	
	private int runCount;
	private char currentPlayer;
	
	/**
     * Initializes the <code>Board</code>.
     * 
     * @param initState A state, representing a board state
     * 
     * @exception  Exception  If initState is malformed
     */
	private void initBoard(String initState) throws Exception{
		board = new char[6][5];
		
		String[] lines = initState.split("\n");
		
		if (lines.length != 7){
			throw new Exception("Bad initState format - Parameter needs 7 lines (has " + lines.length + " lines)");
		}
		
		//read game state
		String[] lineOne = lines[0].split(" ");
		runCount = Integer.parseInt(lineOne[0]);
		currentPlayer = lineOne[1].charAt(0);
		
		
		//read board state
		char currchar =' ';
		for (int iRow = 1; iRow < 7; iRow++) {
			if (lines[iRow].length() != 5){
				throw new Exception("Bad initState format - Column #" + iRow + " needs 5 values (has " + lines[iRow].length() + " values)");
			}
					
			for (int iColumn = 0; iColumn < 5; iColumn++) {
				currchar = lines[iRow].charAt(iColumn);
				if (!isBoardCharValid(currchar))
				{
					throw new Exception("Bad initState format - '" + currchar + "' is not a valid character");
				}
				board[6 - iRow][iColumn] = currchar;
			}
		}
		
	}
	
	/**
     * Returns <code>true</code> if character is element of <code>validBoardCharacters</code>.
     * 
     * @param currchar A character to test
     */
	private boolean isBoardCharValid(char currchar) {		
		return validBoardCharacters.contains(new String(new char[]{currchar}));		
	}

	/**
     * Returns a <code>String</code> representing the actual board state.
     */
	@Override
	public String toString() {

		String ret = runCount + " " + currentPlayer + "\n";
		
		for (int iRow = board.length - 1; iRow >= 0; iRow--) {
			for (int iCol = 0; iCol < board[iRow].length; iCol++) {
				ret += board[iRow][iCol];
			}
			ret += "\n";
		}
		
		return ret;
	}
	
	/**
     * Returns an <code>OutputStream</code> representing the actual board state.
     * 
     * @exception	IOException	If problem with <code>OutputStream</code>
     */
	public void print(OutputStream os) throws IOException {
		OutputStreamWriter ow = new OutputStreamWriter(os);
		ow.write(this.toString());
		ow.flush();
	}
	
	/**
     * Returns a <code>Board</code> representing the actual board state from a file.
     * 
     * @param		fileName	The name of the file containing the board state
     * 
     * @exception	Exception	If some problem with <code>FileInputStream</code>
     */
	public static Board fromFile(String fileName) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		Board b = null;
		b = new Board(fis);
		fis.close();
		return b;
	}
	
	/**
     * Saves the current state of the <code>Board</code> in a file.
     * 
     * @param		b			The Board which state should be saved
     * @param		fileName	The name of the file where to write the state information
     * 
     * @exception	Exception	If some problem with <code>FileOutputStream</code>
     */
	public static void toFile(Board b, String fileName) throws Exception {
		FileOutputStream fos = new FileOutputStream(fileName, true);
		b.print(fos);
		fos.flush();
		fos.close();
	}

	/**
     * Returns the value of a <code>Square</code>.
     * 
     * @param		location	Value of this <code>Square</code> will be returned
     */
	private char getSquare(Square location) {
		return board[location.getRow()][location.getColumn()];
	}
	
	/**
     * Sets the value of a <code>Square</code>.
     * 
     * @param		location	Value of this <code>Square</code> will be set
     * @param		piece		New value of the <code>Square</code>
     */
	private void setSquare(Square location, char piece) {
		 board[location.getRow()][location.getColumn()] = piece;
	}
	
	/**
     * Processes a move on the board.
     * 
     * @param		move		The <code>Move</code> to do
     */
	public void move(Move move) {
		char currPiece = getSquare(move.from);
		setSquare(move.from, '.');
		setSquare(move.to, currPiece);
	}
	
	
	public static void main(String args[]) {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		boolean exit = false;
		
		try{
			Board b = new Board();
			System.out.println(b);
			System.out.println("Please input your move or x to exit");
			do {
				String input = br.readLine();
				if (input.equals("x")){
					exit = true;
				}
				else
				{
					Move move = new Move(input); 
					b.move(move);
					System.out.println(move);
					System.out.print(b);
				}
				
			} while (!exit);
		}catch(Exception e){
			System.out.println(e);
		} 
	}
}
