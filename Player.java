package snakes;

/**
 * Provides players for the Snakes and Ladders game.
 * A player knows his name and position on the game board.
 * Also, he's able to move to another square.
 *
 * Is created and used inside the (@link Game) class.
 * Is created and used inside the (@link Square) class.
 * Is build of (@link ISquare).
 */
public class Player {

	private String name;
	private ISquare square;

	private boolean invariant() {
		return name != null
				&& square != null;
	}

	public Player(String name) {
		this.name = name;
		// invariant holds only after joining a game
	}

	public void joinGame(Game game) {
		square = game.firstSquare();
		square.enter(this);
		assert invariant();
	}

	public int position() {
		assert invariant();
		return square.position();
	}

	public boolean moveForward(int moves) {
		assert moves > 0;
		square.leave(this);
		square = square.moveAndLand(moves);
		square.enter(this);
		return true;
	}

	public boolean moveToSquare(Game game, int position) {
		assert position > 0 && position <= game.getSize();
		square.leave(this);
		square = game.getSquare(position).landHereOrGoHome();
		square.enter(this);
		return true;
	}

	public void enterSquare(Game game, int position) {
		assert position > 0 && position <= game.getSize();
		square = game.getSquare(position).landHereOrGoHome();
		square.enter(this);
	}

	public String toString() {
		return name;
	}

	public ISquare square() {
		return square;
	}

	public boolean wins() {
		return square.isLastSquare();
	}

}
