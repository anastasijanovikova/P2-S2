package snakes;

/**
 * Selects a random player except the current player.
 * Moves the selected player to a random square.
 * The effects of that square still hold.
 *
 * Is created and used inside the (@link Game) class.
 * Extends (@link Square).
 * Implements (@link ISquare).
 * Is tested inside the (@ScrambleUpSquareTest) class.
 */
public class ScrambleUp extends Square implements ISquare{

    public ScrambleUp(Game game, int position) {
        super( game, position );
    }

    @Override
    public String squareLabel() {
        return String.format( "%d (ScrambleUp)", position );
    }

    @Override
    public boolean isScrambleUp() { return true; }
}

