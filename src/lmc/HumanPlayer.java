/*
 * Copyright © 2010 Stefan Liebler, Clemens Henker, Lukas Hahmann. All rights reserved.
 */
package lmc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HumanPlayer implements IPlayer {

	BufferedReader br;
	
	public HumanPlayer(){
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	@Override
	public Move getNextMove(Board b, ArrayList<Move> possibleMoveList) {

		Move move = null;
		
		try {
			System.out.println("Enter your move:");
			String input = br.readLine();
			
			move = new Move(input);
			if (!possibleMoveList.contains(move)) {
				System.out.println("you failed. No correct move!!!");
				move = null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
		return move;
	}

	@Override
	public void sendOpponentsMove(Move m) {
		// TODO Auto-generated method stub
		
	}

}
