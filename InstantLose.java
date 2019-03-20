package snakes;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Takes the entering player out of the game.
 *
 * Is created and used inside the (@link Game) class.
 * Extends (@link Square).
 * Is tested inside the (@link InstantLoseSquareTest) class.
 */
public class InstantLose extends Square implements ISquare {

	public InstantLose(Game game, int position) {
		super(game, position);	
	}

	@Override
	public boolean isInstantLose() {
		return true;
	}
	
	@Override
	public String squareLabel() {
		return String.format("%d (InstantLose)", position);
	}

}
