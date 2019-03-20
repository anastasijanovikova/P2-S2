package snakes;

/**
 * Moves the entering player to a specified lower square.
 *
 * Is created and used inside the (@link Game) class.
 * Extends (@link Ladder).
 */
public class Snake extends Ladder {

	public Snake(int transport, Game game, int position) {
		super(transport, game, position);
	}

	@Override
	protected String squareLabel() {
		return this.destination().position() + "<-" + position;
	}

}
