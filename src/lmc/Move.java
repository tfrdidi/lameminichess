package lmc;

public class Move {
	
	Square from;
	Square to;
	
	public Move(String str) throws Exception {
		//str is in format a4-a5

		String[] input = str.split("-");
		if(input.length != 2)
			throw new Exception("Malformed Move Parameters");
		
		from = new Square(input[0]);
		to = new Square(input[1]);
	}
	
	
	

	public Move(int row, int column){
		
	}

}
