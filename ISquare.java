package snakes;

/**
 * Sets requirements for squares on the game board
 * of the Snakes and Ladders game.
 * This includes the square's position and permitting
 * players to enter and leave it.
 *
 * Implementing classes: (@link Square), (@link ScrambleUp)
 * and (@link InstantLose).
 *
 */
public interface ISquare {

	/**
	 * @return the position of the square on the game board
	 */
	public int position();

	/**
	 * Returns the square where the player currently
	 * on this square needs to go.
	 *
	 * @param moves		the number of squares to advance
	 * @return 	the square to land on
	 */
	public ISquare moveAndLand(int moves);

	/**
	 * @return true if this square is the first square on the board
	 */
	public boolean isFirstSquare();

	/**
	 * @return true if this square is the last square on the board
	 */
	public boolean isLastSquare();

	/**
	 * Enables players to occupy this square.
	 *
	 * @param player	the player who enters this square
	 */

	public void enter(Player player);

	/**
	 * Enables players to leave this square.
	 *
	 * @param player	the player who leaves this square
	 */
	public void leave(Player player);

	/**
	 * Checks whether this square is occupied.
	 *
	 * @return true if this square is occupied
	 */
	public boolean isOccupied();

	/**
	 * Returns the (@link FirstSquare) if this square is
	 * already occupied when a player attempts to land on it.
	 * Otherwise returns this square.
	 *
	 * @return this square or the first square
	 */
	public ISquare landHereOrGoHome();

	/**
	 * @return true if this square is a (@link InstantLose) square
	 */
	public boolean isInstantLose();

	/**
	 *
	 * @return true if this square is a (@link SkipSquare) square
	 */
	public boolean isSkipSquare();

	/**
	 *
	 * @return true if this square is a (@link ScrambleUp) square
	 */
	public boolean isScrambleUp();
}
