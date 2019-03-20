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
		game.setSquare(7, new SkipSquare(game, 7));
		skipSquare = game.getSquare(7);
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

	}

	@Test
	public void skipSquareOccupied(){
		skipSquare.enter(jack);
		assertEquals("Jack is on SkipSquare", 7, jack.position());
		skipSquare.enter(jill);
		assertTrue(skipSquare.isOccupied());
		assertEquals("Jill has to go home", 1, jill.position());
	}

	@Test
	public void skipSquareToString(){
		assertEquals("[7 (Skip)]", skipSquare.toString());
	}




}
