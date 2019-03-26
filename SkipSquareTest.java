package snakes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SkipSquareTest extends SquareTest {
	private ISquare skipSquare;

	@Override
	@Before
	public void newGame() {
		initializeGame(15);
		skipSquare = new SkipSquare(game, 7);
		game.setSquare(7, skipSquare);
	}

	@Test
	public void moveToSkipSquare() {
		game.movePlayer(6); // moves Jack to skip square
		assertEquals(7, jack.position());
		assertEquals(1, jill.position());
		assertEquals(1, eric.position());
		game.movePlayer(5); // moves Eric
		assertEquals(7, jack.position());
		assertEquals(1, jill.position());
		assertEquals(6, eric.position());
		game.movePlayer(2); // Jack's turn again
		assertEquals(9, jack.position());
		assertEquals(1, jill.position());
		assertEquals(6, eric.position());
		game.movePlayer(3); // Finally Jill's turn
		assertEquals(9, jack.position());
		assertEquals(4, jill.position());
		assertEquals(6, eric.position());
	}

	@Test
	public void onlyTwoPlayers(){
		Square instantLose = new InstantLose(game, 4);
		game.movePlayer(3);
		assertTrue("The InstantLose Square is empty", !instantLose.isOccupied());
		game.movePlayer(6);
		assertEquals("Jill is on SkipSquare", 7, jill.position());
		game.movePlayer(2);
		assertEquals("Eric is still on FirstSquare", 1, eric.position());
		assertEquals("Jill moves two squares further", 9, jill.position());
	}

	@Test
	public void skipSquareOccupied(){
		game.movePlayer(6);
		assertEquals("Jack is on SkipSquare", 7, jack.position());
		assertTrue(skipSquare.isOccupied());
		game.movePlayer(6);
		assertEquals("Jill moved to SkipSquare but had to go home", 1, jill.position());
	}

	@Test
	public void skipSquareToString(){
		assertEquals("[7 (Skip)]", skipSquare.toString());
	}




}
