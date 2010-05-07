/*
 * Copyright © 2010 Stefan Liebler, Clemens Henker, Lukas Hahmann. All rights reserved.
 */
package lmc;

import java.util.*;

public class DrunkenPlayer implements IPlayer {

	public DrunkenPlayer(){
	}
	
	static Random rnd = new Random();
	
	@Override
	public Move getNextMove(Board b, ArrayList<Move> possibleMoveList) {
		if (possibleMoveList.size() == 0) return null;
		
		Move ret = null;
		int chosenIndex = rnd.nextInt(possibleMoveList.size());
		ret = possibleMoveList.get(chosenIndex);			
		return ret;
	}

	@Override
	public void sendOpponentsMove(Move m) {
		// TODO Auto-generated method stub
		
	}

}
