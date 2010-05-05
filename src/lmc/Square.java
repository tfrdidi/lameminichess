/*
 * Copyright © 2010 Stefan Liebler, Clemens Henker, Lukas Hahmann. All rights reserved.
 */
package lmc;

/*
 * row and column fields are zero based
 */
public class Square implements Cloneable {

	public static final int EMPTY = 0;
	public static final int MATE = 1;
	public static final int OPPONENT = 2;
	public static final int ALL = 3;
	
	/*
	 * parameters are zero based
	 */
	public Square(int column, int row) throws Exception{
		this.row = row;
		this.column = column;
		if (!ValidateRowCols()) {
			throw new Exception("Illegal value for rows and/or columns. Bäh!");
		}
	}


	/*
	 * input string 'a1' means Square 0,0
	 */
	public Square(String string) throws Exception {
		if (string.length() != 2){
			throw new Exception("input string has malformed format");
		}
		string = string.toLowerCase();
		char rowStr = string.charAt(0);
		column = rowStr-'a';
		row = Integer.parseInt("" + string.charAt(1)) -1;
		if (!ValidateRowCols()) {
			throw new Exception("Illegal value for rows and/or columns. Bäh!");
		}
	}

	private boolean ValidateRowCols() {
		return ((row >= 0 && row <= 5) 
				&& (column >= 0 && column <= 4));
	}
	
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	private int row;
	private int column;
	
	@Override
	public String toString() {
		char cchar = (char) ('a' + column);
		int irow = row +1;
		return "" + cchar + irow;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		try {
			return new Square(column, row);
		} catch (Exception e) {
			throw new CloneNotSupportedException();
		}
	}


	public boolean changePosition(int x, int y) {
		column += x;
		row += y;
		return ValidateRowCols();
	}

}
