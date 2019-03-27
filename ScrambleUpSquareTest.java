package snakes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

public class ScrambleUpSquareTest extends SquareTest {

	private ISquare scrambleUp;

	@Override
	@Before
	public void newGame(){
		initializeGame(15);
		scrambleUp = new ScrambleUp(game, 5);
		game.setSquare(5, scrambleUp);
	}

	@Test
	public void jackMove1() {
		game.movePlayer(2);
		assertEquals("Jack is transported to Square", 3, jack.position());
        assertEquals(1, jill.position());
        assertTrue("Game is not over", game.notOver());
	}

  	@Test
	public void scrambleUpOccupied() {
		game.movePlayer(4);
		assertEquals("Jack is on scrambleUp", 5, jack.position());
		assertTrue("ScrambleUp is occupied", scrambleUp.isOccupied());
	}

	/**
	 * Tests the (@link Game#moveRandomPlayer()) method.
	 *
	 * Indirect testing, since we don't know which player was scrambled up
	 * and to which square he was moved.
	 * He might even have stayed on the first square, so we can't assert that
	 * another square has been occupied.
	 *
	 */
	@Test
	public void testMoveRandomPlayer() {
		game.movePlayer(4); // A random player is scrambled up
		assertEquals("Jack is on scrambleUp", 5, jack.position());
		game.moveRandomPlayer(); // Doesn't advance the game, but a player is again scrambled up
		assertEquals("Jack is still on scrambleUp", 5, jack.position());
	}

	@Test
	public void scrambleUpToString() {
		assertEquals("[5 (ScrambleUp)]", scrambleUp.toString());
	}


}
