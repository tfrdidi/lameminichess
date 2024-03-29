/*
 * Copyright � 2010 Stefan Liebler, Clemens Henker, Lukas Hahmann. All rights reserved.
 */
package lmc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Program {

	/**
	 * entry point of app
	 * @param args
	 */
	public static void main(String[] args) {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean showIngameInfo = true;
		boolean stopAfterEachTurn = false;
		int battles = 1;
		long now = System.currentTimeMillis();
		int countWinWhite = 0;
		int countWinBlack = 0;
		int countDraw = 0;
		int turns = 0;
		
		for (int i = 0; i < battles; i++) {
			try {
				if(battles > 0)
					System.out.println("Battle #" + (i+1) + " started...");
				Board b = new Board();
//				Board b = new Board(
//"1 W\n"+
//"k.bnr\n"+
//"p.ppp\n"+
//".....\n"+
//"....P\n"+
//"PPPP.\n"+
//"RNBQK");
				Controller ctrl = new Controller();
				IPlayer blackPlayer = new NetworkPlayer();
				IPlayer whitePlayer = new AlphaBetaNegamaxPlayer(4,7000);
				//IPlayer blackPlayer = new NegamaxPlayer(4);
				//IPlayer whitePlayer = new AlphaBetaNegamaxPlayer(4, 6000);
				IPlayer currentPlayer = null;
				IPlayer opponentPlayer = null;
				char result = '?';
				Move currentMove = null;
				ArrayList<Move> possibleMoveList = null;

				if(showIngameInfo)
					System.out.println("Let's start the battle!!! Kill each others king!!!!");

				do {
					if(showIngameInfo)
						System.out.println(b);
					possibleMoveList = ctrl.collectAllMoves(b);

					//get current player
					if (b.getCurrentPlayer() == 'W') {
						currentPlayer = whitePlayer;
						opponentPlayer = blackPlayer;
					} else {
						currentPlayer = blackPlayer;
						opponentPlayer = whitePlayer;
					}

					long startTime = System.currentTimeMillis();
					currentMove = currentPlayer
							.getNextMove(b, possibleMoveList);
					if (currentMove != null && showIngameInfo)
						System.out.println(b.getCurrentPlayer() + " moved " + currentMove + " in ms: " + (System.currentTimeMillis() - startTime));
					
					result = ctrl.move(b, currentMove);
					opponentPlayer.sendOpponentsMove(currentMove);
					
					if(stopAfterEachTurn) {
						if(b.getCurrentPlayer() == 'W') {
							System.out.println("\n\n\n\n\n\n");
							br.readLine();
						}
					}

				} while (result == '?');

				if(showIngameInfo) {
					System.out.println("Result of Battle: " + result);
					System.out.println(b);
				}
				
				//Some stats...
				switch (result) {
				case 'W':
					countWinWhite++;
					break;
				case 'B':
					countWinBlack++;
					break;
				case '=':
					countDraw++;
					break;
				default:
					break;
				}
				turns += b.getRunCount();
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
			}
		} 
		long duration = System.currentTimeMillis()-now;
		double avgDuration = (double) duration / battles;
		double avgRunCount = (double) turns / battles;
		
		System.out.println("\n----Stats---");
		System.out.println("Battles:\t" + battles);
		System.out.println("Duration:\t" + ((double)duration/60000) + "min");
		System.out.println("avgDuration:\t" + avgDuration/60000 + "min");
		System.out.println("avgRunCount:\t" + avgRunCount);
		System.out.println("countWinBlack:\t" + countWinBlack + "\t" + ((double)countWinBlack/battles)*100 + "%");
		System.out.println("countWinWhite:\t" + countWinWhite + "\t" + ((double)countWinWhite/battles)*100 + "%");
		System.out.println("countDraw:\t" + countDraw + "\t" + ((double)countDraw/battles)*100 + "%");
	}

}
