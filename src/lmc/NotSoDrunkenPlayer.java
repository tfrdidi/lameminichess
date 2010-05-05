package lmc;

import java.util.ArrayList;
import java.util.Random;

public class NotSoDrunkenPlayer implements IPlayer {

	static Random rnd = new Random();
	Controller ctrl = new Controller();
	
	@Override
	public Move getNextMove(Board b, ArrayList<Move> possibleMoveList) {
		if (possibleMoveList.size() == 0) return null;
		
		Move ret = null;
		int weight = Integer.MIN_VALUE;
		Board tempBoard;
		int tempWeight;
		ArrayList<Move> weightedMoveList = new ArrayList<Move>();

		try {
			for (Move move : possibleMoveList) {
				tempBoard = (Board) b.clone();
				ctrl.move(tempBoard, move);
				tempWeight = ctrl.getBoardScore(tempBoard) * -1;	//-1, da nach move der currentPlayer auf dem Gegner steht
				
				if (tempWeight > weight) {
					weightedMoveList.clear();
					weightedMoveList.add(move);
					weight = tempWeight;
				} else if (tempWeight == weight) {
					weightedMoveList.add(move);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		int chosenIndex = rnd.nextInt(weightedMoveList.size());
		ret = weightedMoveList.get(chosenIndex);
		weightedMoveList.clear();
		tempWeight = 0;
		return ret;
	}


}
