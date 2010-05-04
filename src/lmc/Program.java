/*
 * Copyright © 2010 Stefan Liebler, Clemens Henker, Lukas Hahmann. All rights reserved.
 */
package lmc;

import java.io.*;
import java.util.ArrayList;

public class Program {

	/**
	 * entry point of app
	 * @param args
	 */
	public static void main(String[] args) {


		try{
			Board b = new Board();
			Controller ctrl = new Controller();
			IPlayer whitePlayer = new RandomPlayer();
			IPlayer blackPlayer = new HumanPlayer();
			IPlayer currentPlayer = null;
			char result = '?';
			Move currentMove = null;
			ArrayList<Move> possibleMoveList = null;
			
			System.out.println("Let´s start the battle!!! Kill each others king!!!!");
			
			do {
				System.out.println(b);
				possibleMoveList = ctrl.collectAllMoves(b);
				
				//get current player
				if (b.getCurrentPlayer() == 'W'){
					currentPlayer = whitePlayer;
				}
				else {
					currentPlayer = blackPlayer;
				}
				
				currentMove = currentPlayer.getNextMove(b, possibleMoveList);
				if (currentMove != null)
					System.out.println(b.getCurrentPlayer() +  " moved " + currentMove);
				
				result = ctrl.move(b, currentMove);
			
			} while (result == '?');
			
			System.out.println("Result of Battle: " + result);
			System.out.println(b);
		}catch(Exception e){
			System.out.println(e);
		} 
	}

}
