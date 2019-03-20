package snakes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

public class ScrambleUpSquareTest extends SquareTest {

	private ISquare scrambleUp;

	/*
	@Before
	public void initializeGame() {
		jack = new Player("Jack");
		jill = new Player("Jill");
		Player[] args = { jack, jill };
		game = new Game(12, args);
		game.setSquareToLadder(2, 2);
		game.setSquareToLadder(7, -5);
		game.setSquare(5, new ScrambleUp(game, 5));
	}

	@Test
	public void newGame() {
		assertTrue(game.notOver());
		assertTrue(game.firstSquare().isOccupied());
		assertEquals(1, jack.position());
		assertEquals(1, jill.position());
		assertEquals(jack, game.currentPlayer());
		assertEquals("Jack", jack.toString());
		assertEquals("Jill", jill.toString());
		assertEquals("[1<Jack><Jill>]", game.firstSquare().toString());
		assertEquals("[5 (ScrambleUp)]", game.getSquare(5).toString());
	}
	*/
	@Override
	@Before
	public void newGame(){
		initializeGame(15);
		game.setSquare(6,new ScrambleUp(game, 5));
		scrambleUp = game.getSquare(6);
	}

	@Test
	public void jackMove1() {
		game.movePlayer(2);
		assertEquals("Jack is transported to Square ", 3, jack.position());
        assertEquals(1, jill.position());
        assertTrue("Game is not over", game.notOver());
	}

	@Test
	public void moveToScrambleUp() {
		game.movePlayer(5);
		assertEquals("Jack was moved to ScrambleUp",6, jack.position());
		assertTrue(scrambleUp.isOccupied());
	}

  	@Test
	public void scrambleUpOccupied() {
		scrambleUp.enter(jack);
		assertEquals("Jack is on scrambleUp", 6, jack.position());
		scrambleUp.enter(jill);
		assertEquals("Jill went home", 1, jill.position());
	}

	@Test
	public void landOnScrambleUp() {
		scrambleUp.enter(jack);
		assertEquals("Jack is on scrambleUp", 6, jack.position());
		//assert another player has been moved
	}

	@Test
	public void scrambleUpToString() {
		assertEquals("[6 (ScrambleUp)]", scrambleUp.toString());
	}


}
