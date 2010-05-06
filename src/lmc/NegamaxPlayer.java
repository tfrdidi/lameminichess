package lmc;

import java.util.ArrayList;
import java.util.Random;

public class NegamaxPlayer implements IPlayer {

	char myplayer;
	int maxDepth;
	static Random rnd = new Random();
	Controller ctrl = new Controller();
	
	public NegamaxPlayer(int i) {
		maxDepth = i;
	}

	@Override
	public Move getNextMove(Board board, ArrayList<Move> possibleMoveList) {
		if (possibleMoveList.size() == 0) return null;
		
		Move ret = null;
		int weight = Integer.MAX_VALUE;
		int tempWeight;
		ArrayList<Move> weightedMoveList = new ArrayList<Move>();

		try {
			for (Move move : possibleMoveList) {
				tempWeight = -recursiveMoves(board, move, maxDepth);
				System.out.print(tempWeight + " ");
				
				if (tempWeight < weight) {
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

	private int recursiveMoves(Board board, Move move, int depth) throws Exception {
		int result = Integer.MAX_VALUE;
		depth--;
		
		Board tempBoard = (Board) board.clone();
		
		char gameStatus = ctrl.move(tempBoard, move);
		
		//If the game is finished, return the win/lose-score --or-- max depth is reached 
		if('?' != gameStatus || depth <= 0) {
			int tempret = ctrl.getBoardScore(tempBoard);
			return -tempret;
		}

		//alle möglichen moves des nächsten spielers holen
		ArrayList<Move> moveList = ctrl.collectAllMoves(tempBoard);
		
		//this indicates a draw
		if(moveList.size() == 0) {
			//draw is better than losing the game
			return 0;
		}
		
		int tempScore;
		//for every possible move call recusiveMoves <-- recursion :D
		for (Move nextMove : moveList) {
			tempScore = recursiveMoves(tempBoard, nextMove, depth);

			//save smallest score
			if(tempScore < result) {
				result = tempScore;
			}
		}

		//Negate result, cause the player changes
		return -result;
	}
}
