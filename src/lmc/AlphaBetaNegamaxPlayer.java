package lmc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AlphaBetaNegamaxPlayer implements IPlayer {

	char myplayer;
	int checkBranches;
	long timePerRound;
	double timeUsed = 0;
	double timeStamp = 0;
	double globalStartTime;
	static Random rnd = new Random();
	Controller ctrl = new Controller();
	
	public AlphaBetaNegamaxPlayer(int checkBranches, long timePerRound) {
		this.timePerRound = timePerRound;
		this.checkBranches = checkBranches;
	}

	@Override
	public Move getNextMove(Board board, ArrayList<Move> possibleMoveList) {
		timeStamp = System.currentTimeMillis();
		globalStartTime = timeStamp;
		
		if (possibleMoveList.size() == 0) return null;
		
		Move ret = null;
		double weight = -100000;
		ArrayList<Move> weightedMoveList = new ArrayList<Move>();

		try {
			double maxTimePerMove = timePerRound/possibleMoveList.size();
			for (Move move : possibleMoveList) {
				//TODO statt weight lieber Move.score verwenden
				//TODO nach einem zug wird nicht der maximal mögliche wert für den gegner berechnet!!!
				//TODO bewegungen des königs sind schlecht zu bewerten
				//TODO einheiten der letzten Reihe, die sich nicht bewegen sind auch schlecht zu bewerten!!!
				move.score = recursiveMoves(board, move, maxTimePerMove, System.currentTimeMillis());
				
				System.out.println("move " + move.toString() + ", bewertung: " + move.score);
				
				if (move.score > weight) {
					weightedMoveList.clear();
					weightedMoveList.add(move);
					weight = move.score;
				} else if (move.score == weight) {
					weightedMoveList.add(move);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			
			return null;
		}
		
		int chosenIndex = rnd.nextInt(weightedMoveList.size());
		ret = weightedMoveList.get(chosenIndex);
		timeUsed = 0;
		
		return ret;
	}

	private double recursiveMoves(Board board, Move move, double maxTimePerMove, long startTime) throws Exception {
		double result = 10000;
		
		Board tempBoard = (Board) board.clone();
		
		char gameStatus = ctrl.move(tempBoard, move);
		
		//If the game is finished, return the win/lose-score --or-- max depth is reached 
		if('?' != gameStatus) {
			int tempret = -ctrl.getBoardScore(tempBoard);
			
			//counts the time, refreshes the timestamp
			timeUsed -= timeStamp;
			timeStamp = System.currentTimeMillis();
			timeUsed += timeStamp;
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
		
		double tempScore;
		
		//Have I time to play recursive?
		long currTime = System.currentTimeMillis();
		if ((startTime + maxTimePerMove > globalStartTime + timeUsed)) {
			maxTimePerMove = maxTimePerMove/tempCheckBranches;
//			System.out.println(maxTimePerMove);
			//for every possible move call recusiveMoves <-- recursion :D
			for (int i = 0; i < tempCheckBranches; i++) {
				tempScore = -recursiveMoves(tempBoard, moveList.get(i), maxTimePerMove, currTime);
				//moves deep in future are less worth
				tempScore *= 0.6;
				
				//save smallest score
				if(tempScore < result) {
					result = tempScore;
				}
				
				currTime += maxTimePerMove;
			}
		} else {
			int tempret = -ctrl.getBoardScore(tempBoard);
			
			timeUsed -= timeStamp;
			timeStamp = System.currentTimeMillis();
			timeUsed += timeStamp;

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

	@Override
	public void sendOpponentsMove(Move m) {
		// TODO Auto-generated method stub
		
	}
}
