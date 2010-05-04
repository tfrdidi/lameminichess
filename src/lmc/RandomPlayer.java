package lmc;

import java.util.*;

public class RandomPlayer implements IPlayer {

	public RandomPlayer(){
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

}
