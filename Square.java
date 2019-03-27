package snakes;

/**
 * Represents a square on a game board.
 * Knows its position on the board and
 * which player is on it.
 *
 * Tells the game where to move the current
 * player that is on it.
 *
 * Is created and used inside the (@link Game) class.
 * Is extended by (@link FirstSquare) and other classes.
 * Is build of (@link Game) and of (@Player).
 * Is tested inside the (@link SquareTest) class.
 */
public class Square implements ISquare {

	protected int position;
	protected Game game;
	private Player player;

	private boolean invariant() {
		return game != null
			&& game.isValidPosition(position);
	}

	public Square(Game game, int position) {
		this.game = game;
		this.position = position;
		assert invariant();
	}

	public int position() {
		return this.position;
	}

	public ISquare moveAndLand(int moves) {
		return game.findSquare(position, moves).landHereOrGoHome();
	}

	public ISquare landHereOrGoHome() { return this.isOccupied() ? game.firstSquare() : this; }

	public boolean isInstantLose() {
		return false;
	}

	public String toString() {
		return "[" + this.squareLabel() + this.player() + "]";
	}

	protected String squareLabel() {
		return Integer.toString(position);
	}

	public boolean isOccupied() {
		return player != null;
	}

	public void enter(Player player) {
		assert this.player == null;
		this.player = player;
	}

	public void leave(Player player) {
		assert this.player == player;
		this.player = null;
	}

	public boolean isFirstSquare() {
		return false;
	}

	public boolean isLastSquare() {
		return false;
	}

	public boolean isSkipSquare() { return false; }

	public boolean isScrambleUp() { return false; }

	protected String player() {
		return this.isOccupied() ? ("<" + this.player + ">") : "";
	}
}
