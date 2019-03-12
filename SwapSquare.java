/**
 * Swaps the entering player with
 * the player who plays next.
 *
 * Example:
 * Player 2 lands on the square.
 * Player 2 swaps places with player 3,
 * so player 3 is now on this square.
 *
 * Is created and used inside the (@link Game) class.
 * Extends (@link Square).
 * Is tested inside the (@link SwapSquareTest) class.
 */

package snakes;

public class SwapSquare extends Square {

	private boolean hasSwapped;

	public SwapSquare(Game game, int position) { super( game, position ); }
	
	@Override
	public String squareLabel() {
		return String.format("%d (Swap)", position);
	}

	@Override
	public void enter(Player player) {
		if (!hasSwapped) {
			hasSwapped = true;
			game.swap(player);
			this.enter(game.getNextPlayer());
		} else {
			hasSwapped = false;
			super.enter(player);
		}
	}
}
