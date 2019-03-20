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

    @Test //Is this a test for Game instead of SwapSquare? Only game methods are used.
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

    // Can you think of more game scenarios you need/want to cover to make sure your implementation is correct?
    @Test
    public void swapSquareOccupied() {
       ISquare swapSquare = game.getSquare(5);
       swapSquare.enter(jack);
       assertEquals("Jill switched places with Jack", 5, jill.position());
       assertEquals("Jack switched places with Jill", 1, jack.position());
       swapSquare.enter(eric);
       assertTrue(swapSquare.isOccupied());
       assertEquals("Eric has to home", 1, eric.position());
    }

    @Test
    public void landOnSwapSquare() {
        ISquare swapSquare = game.getSquare(5);
        swapSquare.enter(jack);
        assertNotEquals("Jack is not on swapSquare", 5, jack.position());
        assertEquals("Next player Jill is on swapSquare", 5, jill.position());
    }

    @Test
    public void swapSquareToString() {
        ISquare swapSquare = game.getSquare(5);
        assertEquals("[5 (Swap)]", swapSquare.toString());
    }





}