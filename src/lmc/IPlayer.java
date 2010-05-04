package lmc;

import java.util.ArrayList;

public interface IPlayer {
	Move getNextMove(Board b, ArrayList<Move> possibleMoveList);
}
