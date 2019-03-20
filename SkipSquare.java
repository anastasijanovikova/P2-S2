package snakes;

/**
 * Skips the next player's turn.
 *
 * Example:
 * There are 3 Players.
 * Player 1 lands on this square.
 * Player 2 is skipped over and it's player 3's turn.
 *
 * Is created an used inside the (@link Game) class.
 * Extends (@link Square).
 * Is tested inside the (@link SkipSquareTest) class.
 */
public class SkipSquare extends Square {

    public SkipSquare(Game game, int position) {
        super(game, position);
    }

    @Override
    public void enter(Player player){
        game.skip();
        super.enter(player);
    }

    @Override
    public String squareLabel() {
        return String.format("%d (Skip)", position);
    }
}
