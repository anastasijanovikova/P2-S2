package snakes;

/**
 * Moves the entering player to a specified higher square.
 *
 * Is created and used inside the (@link Game) class.
 * Extends (@link Square).
 */
public class Ladder extends Square {

	private int transport;

	private boolean invariant() {
		return isValidTransport(transport);
	}

	private boolean isValidTransport(int transport) {
		return transport != 0 && game.isValidPosition(position + transport);
	}

	public Ladder(int transport, Game game, int position) {
		super(game, position);
		assert isValidTransport(transport);
		this.transport = transport;
		assert invariant();
	}

	@Override
	protected String squareLabel() {
		return position + "->" + this.destination().position();
	}

	@Override
	public ISquare landHereOrGoHome() {
		return this.destination().landHereOrGoHome();
	}

	protected ISquare destination() {
		return game.getSquare(position+transport);
	}
}
