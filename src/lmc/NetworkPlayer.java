/*
 * Copyright © 2010 Stefan Liebler, Clemens Henker, Lukas Hahmann. All rights reserved.
 */
package lmc;

import java.io.IOException;
import java.util.ArrayList;

public class NetworkPlayer implements IPlayer {

	public Client c;
	public NetworkPlayer() throws IOException {
		c = new Client("barton.cs.pdx.edu","80","lame","buh");
		c.offer('W');
	}
	
	public NetworkPlayer(int gameID) throws IOException {
		c = new Client("barton.cs.pdx.edu","80","lame","buh");
		c.accept(Integer.toString(gameID), 'B');
	}

	@Override
	public Move getNextMove(Board b, ArrayList<Move> possibleMoveList) {

		Move m;
		try {
			String input = c.getMove();
			if (input == null) return null;
			m = new Move(input);
			return m;
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public void sendOpponentsMove(Move m) {
		try {
			c.sendMove(m.toString());
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
