package lmc;

/*
 * row and column fields are zero based
 */
public class Square {

	/*
	 * parameters are zero based
	 */
	public Square(int row, int column) throws Exception{
		/*
		 * [row][column]
		 */
		this.row = row;
		this.column = column;
		ValidateRowCols();
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
		ValidateRowCols();
	}

	private void ValidateRowCols() throws Exception {
		if(row < 0 || row > 5 || column < 0 || column > 4) {
			throw new Exception("Illegal value for rows and/or columns. Bäh!");
		}
	}
	
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	private int row;
	private int column;
}
