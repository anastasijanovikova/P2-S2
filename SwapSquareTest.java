package snakes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SwapSquareTest extends SquareTest {

    @Override
    @Before
    public void newGame() {
        initializeGame(14);
        game.setSquare(5, new SwapSquare(game, 5));
    }

    @Test
    public void moveToSwapSquare() {
        game.movePlayer(5); // moves Jack to normal square 6
        assertEquals(6, jack.position());
        assertEquals(1, jill.position());
        assertEquals(1, eric.position());
        game.movePlayer(4); // moves Jill to swap square
        assertEquals("Jack stays on his square", 6, jack.position());
        assertEquals("Jill switched places with Eric",1, jill.position());
        assertEquals("Eric switched places with Jill",5, eric.position());
    }

    @Test
    public void afterSwapNextPlayersTurn() {
       ISquare swapSquare = game.getSquare(5);
       game.movePlayer(4); // Jack's turn
       assertEquals("Jack switched places with Jill", 1, jack.position());
       assertEquals("Jill switched places with Jack", 5, jill.position());
       game.movePlayer(2); // Jill's turn
       assertEquals("Jill moved two squares ahead", 7, jill.position());
       assertEquals("Eric is still on square one", 1, eric.position());
    }

    @Test
    public void swapSquareToString() {
        ISquare swapSquare = game.getSquare(5);
        assertEquals("[5 (Swap)]", swapSquare.toString());
    }
}