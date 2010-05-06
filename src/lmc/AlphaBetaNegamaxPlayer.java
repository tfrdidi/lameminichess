package lmc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AlphaBetaNegamaxPlayer implements IPlayer {

	char myplayer;
	int maxDepth;
	int checkBranches;
	long startTime;
	static Random rnd = new Random();
	Controller ctrl = new Controller();
	
	public AlphaBetaNegamaxPlayer(int checkBranches, long timePerRound) {
		this.startTime = timePerRound;
		this.maxDepth = 40;
		this.checkBranches = checkBranches;
	}

	@Override
	public Move getNextMove(Board board, ArrayList<Move> possibleMoveList) {
		if (possibleMoveList.size() == 0) return null;
		
		Move ret = null;
		int weight = Integer.MIN_VALUE;
		int tempWeight;
		ArrayList<Move> weightedMoveList = new ArrayList<Move>();

		try {
			//TODO 5000 dynamisch berechnen (5000 = Wieviel Zeit läuft das Spiel schon)
			long maxTimePerMove = startTime/possibleMoveList.size();
//			startTime = System.currentTimeMillis();
			for (Move move : possibleMoveList) {
				//You have 5 sec to play recursive...
				tempWeight = recursiveMoves(board, move, maxTimePerMove, System.currentTimeMillis());
//				System.out.print(tempWeight + " ");
				
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
		return ret;
	}

	private int recursiveMoves(Board board, Move move, double maxTimePerMove, long startTime) throws Exception {
		int result = Integer.MAX_VALUE;
		
		Board tempBoard = (Board) board.clone();
		
		char gameStatus = ctrl.move(tempBoard, move);
		
		//If the game is finished, return the win/lose-score --or-- max depth is reached 
		if('?' != gameStatus) {
			int tempret = -ctrl.getBoardScore(tempBoard);
			return tempret;
		}

		//alle möglichen moves des nächsten spielers holen
		ArrayList<Move> moveList = ctrl.collectAllMoves(tempBoard);

		//Moves bewerten
		for (Move item : moveList) {
			item.score = scoreMove(tempBoard, item);
		}
		Collections.sort(moveList);
//		for (Move m : moveList) {
//			System.out.print(m.score + " ");
//		}
//		System.out.println();
		
		
		//Wenn weniger Elemente als checkBranches verfügbar sind, korrigiere checkBranches
		int tempCheckBranches = checkBranches;
		if (tempCheckBranches > moveList.size()) {
			tempCheckBranches = moveList.size();
		}
		

		//this indicates a draw
		if(moveList.size() == 0) {
			//draw is better than losing the game
			return 0;
		}
		
		int tempScore;
		
		//Have I time to play recursive?
		long currTime = System.currentTimeMillis();
		if (startTime + maxTimePerMove > currTime) {
			maxTimePerMove = maxTimePerMove/tempCheckBranches;
//			System.out.println(maxTimePerMove);
			//for every possible move call recusiveMoves <-- recursion :D
			for (int i = 0; i < tempCheckBranches; i++) {
				tempScore = -recursiveMoves(tempBoard, moveList.get(i), maxTimePerMove, currTime);
	
				//save smallest score
				if(tempScore < result) {
					result = tempScore;
				}
				
				currTime += maxTimePerMove;
			}
		} else {
			int tempret = -ctrl.getBoardScore(tempBoard);
			return tempret;
		}

		//Negate result, cause the player changes
		return result;
	}
	
	private int scoreMove(Board board, Move move) throws Exception {
		Board tempBoard = (Board) board.clone();
		
		ctrl.move(tempBoard, move);
		
		return -ctrl.getBoardScore(tempBoard);
	}
}
