/*
 * Copyright © 2010 Stefan Liebler, Clemens Henker, Lukas Hahmann. All rights reserved.
 */
package lmc;

import java.util.ArrayList;

public class Program {

	/**
	 * entry point of app
	 * @param args
	 */
	public static void main(String[] args) {

		boolean showIngameInfo = true;
		int battles = 1;
		long now = System.currentTimeMillis();
		int countWinWhite = 0;
		int countWinBlack = 0;
		int countDraw = 0;
		int turns = 0;
		
		for (int i = 0; i < battles; i++) {
			try {
				Board b = new Board();
				Controller ctrl = new Controller();
				IPlayer whitePlayer = new NotSoDrunkenPlayer();
				IPlayer blackPlayer = new DrunkenPlayer();
				IPlayer currentPlayer = null;
				char result = '?';
				Move currentMove = null;
				ArrayList<Move> possibleMoveList = null;

				if(showIngameInfo)
					System.out.println("Let´s start the battle!!! Kill each others king!!!!");

				do {
					if(showIngameInfo)
						System.out.println(b);
					possibleMoveList = ctrl.collectAllMoves(b);

					//get current player
					if (b.getCurrentPlayer() == 'W') {
						currentPlayer = whitePlayer;
					} else {
						currentPlayer = blackPlayer;
					}

					currentMove = currentPlayer
							.getNextMove(b, possibleMoveList);
					if (currentMove != null && showIngameInfo)
						System.out.println(b.getCurrentPlayer() + " moved " + currentMove);

					result = ctrl.move(b, currentMove);

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
				System.out.println(e);
			}
		} 
		long duration = System.currentTimeMillis()-now;
		double avgDuration = (double) duration / battles;
		double avgRunCount = (double) turns / battles;
		
		System.out.println("\n----Stats---");
		System.out.println("Battles: " + battles);
		System.out.println("Duration: " + duration + "ms");
		System.out.println("avgDuration: " + avgDuration + "ms");
		System.out.println("avgRunCount: " + avgRunCount);
		System.out.println("countWinWhite: " + countWinWhite);
		System.out.println("countWinBlack: " + countWinBlack);
		System.out.println("countDraw: " + countDraw);
	}

}
