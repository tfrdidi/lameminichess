/**
 * 
 */
package lmc;

import java.io.*;
import java.lang.reflect.Array;


/**
 * @author sli
 */
public class Board {

	public Board() throws Exception{
		initBoard("1 W\n"
		        +"kqbnr\n"
		        +"ppppp\n"
		        +".....\n"
		        +".....\n"
		        +"PPPPP\n"
		        +"RNBQK\n");
	}
	
	public Board(String str) throws Exception{
		initBoard(str);
	}
	
	public Board(InputStream reader) throws Exception{
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
	
	private static  String validBoardCharacters = ".PRNBQKprnbqk";
	
	private int runCount;
	private char currentPlayer;
	
	
	private void initBoard(String str) throws Exception{
		board = new char[6][5];
		
		String[] lines = str.split("\n");
		
		if (lines.length < 7){
			throw new Exception("bad Board Format - number of lines");
		}
		
				
		//read game state
		String[] lineOne = lines[0].split(" ");
		runCount = Integer.parseInt(lineOne[0]);
		currentPlayer = lineOne[1].charAt(0);
		
		
		//read board state
		char currchar =' ';
		for (int iRow = 1; iRow < 7; iRow++) {
			if (lines[iRow].length() < 5){
				throw new Exception("bad Board Format - number of columns is false in row " + iRow);
			}
					
			for (int iColumn = 0; iColumn < 5; iColumn++) {
				currchar = lines[iRow].charAt(iColumn);
				if (!isBoardCharValid(currchar))
				{
					throw new Exception("bad Board Format - '" + currchar + "' is not a valid character");
				}
				board[6 - iRow][iColumn] = currchar;
			}
		}
		
	}
	
	private boolean isBoardCharValid(char currchar) {		
		return validBoardCharacters.contains(new String(new char[]{currchar}));		
	}

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
	
	public void print(OutputStream os) throws IOException{
		OutputStreamWriter ow = new OutputStreamWriter(os);
		ow.write(this.toString());
		ow.flush();
	}
	
	public static Board fromFile(String fileName) throws Exception{
	
		FileInputStream fis = new FileInputStream(fileName);
		Board b = null;
		b = new Board(fis);
		fis.close();
		return b;
	}
	
	public static void toFile(Board b, String fileName) throws Exception{
		
		FileOutputStream fos = new FileOutputStream(fileName, true);
		b.print(fos);
		fos.flush();
		fos.close();
	}

	private char getSquare(Square location){
		return board[location.getRow()][location.getColumn()];
	}
	
	private void setSquare(Square location, char piece){
		 board[location.getRow()][location.getColumn()] = piece;
	}
	
	public void move(Move move){
		char currPiece = getSquare(move.from);
		setSquare(move.from, '.');
		setSquare(move.to, currPiece);
	}
	
	public static void main(String args[]){
		
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
		
//		try {
//			
//			Board b = new Board("1 W\n"
//					+"kqnbr\n"
//					+"ppppp\n"
//					+".....\n"
//					+".....\n"
//					+"PPPPP\n"
//					+"RNBQK");
//			System.out.print(b);
//
//			Board.toFile(b, args[0]);
//		} catch (Exception e) {
//			System.out.println("Error while writing!!!");
//			e.printStackTrace();
//			
//		}
//		File f = new File("out.txt");
//		if (f.exists()){
//			System.out.println(f.getAbsolutePath());
//		}
	}
}
