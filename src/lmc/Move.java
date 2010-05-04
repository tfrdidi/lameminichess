/*
 * Copyright © 2010 Stefan Liebler, Clemens Henker, Lukas Hahmann. All rights reserved.
 */
package lmc;

public class Move {
	
	Square from;
	public Square getFrom() {
		return from;
	}

	public Square getTo() {
		return to;
	}

	Square to;
	
	public Move(String str) throws Exception {
		//str is in format a4-a5

		String[] input = str.split("-");
		if(input.length != 2)
			throw new Exception("Malformed Move Parameters");
		
		from = new Square(input[0]);
		to = new Square(input[1]);
		
		// TODO: out-of-bounds-tests
		// TODO: keine figur auf from -> exception
		// TODO: Valid moves? Can the king step 2 squares?
	}

	public Move(Square from, Square to){
		this.from = from;
		this.to = to;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return from + "-" + to;
	}
	
	@Override
	public boolean equals(Object obj) {
		Move other = (Move) obj;
		return other.toString().equals(this.toString());
	}
}
