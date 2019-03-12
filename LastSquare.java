/**
 * Represents the last square on the game board.
 * The player that enters it first wins the game.
 *
 * Is created and used inside the (@link Game) class.
 * Extends (@link Square).
 */

package snakes;

public class LastSquare extends Square {

	public LastSquare(Game game, int position) {
		super(game, position);
	}
	
	@Override
	public boolean isLastSquare() {
		return true;
	}
}
