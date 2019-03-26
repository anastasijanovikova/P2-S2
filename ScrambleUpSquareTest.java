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

	@Test
	public void randomPlayerHasBeenMoved() {
		game.movePlayer(4);
		assertEquals("Jack is on scrambleUp", 5, jack.position());
		game.moveRandomPlayer();
		assertEquals("Jack is still on scrambleUp", 5, jack.position());
	}

	//TO DO: add 1-2 more specific ScrambleUp-tests

	@Test
	public void scrambleUpToString() {
		assertEquals("[5 (ScrambleUp)]", scrambleUp.toString());
	}


}
