/*
 * Copyright © 2010 Stefan Liebler, Clemens Henker, Lukas Hahmann. All rights reserved.
 */
package lmc;

import java.util.ArrayList;

public interface IPlayer {
	Move getNextMove(Board b, ArrayList<Move> possibleMoveList);
	void sendOpponentsMove(Move m);
}
