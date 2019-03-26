package snakes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

public class InstantLoseSquareTest {
	private Player jack;
	private Player jill;
	private Player eric;
	private Game game;

	@Before
	public void initializeGame() {
		jack = new Player("Jack");
		jill = new Player("Jill");
		eric = new Player("Eric");
		Player[] args = { jack, jill, eric };
		game = new Game(12, args);
		ISquare instantLose = new InstantLose(game, 4);
		game.setSquareToLadder(2, 2);
		game.setSquareToLadder(7, -5);
		game.setSquare(4, instantLose);
	}

	@Test
	public void newGame() {
		assertTrue(game.notOver());
		assertTrue(game.firstSquare().isOccupied());
		assertEquals(1, jack.position());
		assertEquals(1, jill.position());
		assertEquals(1, eric.position());
		assertEquals(jack, game.currentPlayer());
		assertEquals("Jack", jack.toString());
		assertEquals("Jill", jill.toString());
		assertEquals("Eric", eric.toString());
		assertEquals("[1<Jack><Jill><Eric>]", game.firstSquare().toString());
		assertEquals("[4 (InstantLose)]", game.getSquare(4).toString());
	}

    @Test
    public void jackMove1() {
        game.movePlayer(2);
        assertEquals("Jack is transported to Square ", 3, jack.position());
        assertEquals(1, jill.position());
        assertEquals(1, eric.position());
        assertTrue("Game is not over", game.notOver());
    }

    @Test
	public void instantLoseRemainsEmpty() {
		assertTrue(game.notOver());
		game.movePlayer(3); // Jack's turn
		assertTrue("InstantLose is empty", !game.getSquare(4).isOccupied());
		assertEquals("Jill is on first square", 1, jill.position());
		assertEquals("Eric is on first square", 1, eric.position());
		assertTrue(game.notOver());
	}

	@Test
	public void onlyTwoPlayersLeft() {
		game.movePlayer(3); // Jack to InstantLose
		assertEquals("Jill still on first square", 1, jill.position());
		assertEquals("Eric still on first square", 1, eric.position());
		game.movePlayer(4); // Jill to normal square
		assertEquals("Jill moves to square 5", 5, jill.position());
		assertEquals("Eric still on first square", 1, eric.position());
		game.movePlayer(5); // Eric to normal square
		assertEquals("Jill still on square 5", 5, jill.position());
		assertEquals("Eric moves to square 7", 6, eric.position());
		game.movePlayer(3); // Jill's turn!
		assertEquals("Jill moves to square 8", 8, jill.position());
		assertEquals("Eric still on square 7", 6, eric.position());
	}

	@Test
	public void transportToInstantLose() {
		game.movePlayer(1); // Jack to ladder
		assertTrue("Ladder not occupied", !game.getSquare(2).isOccupied());
		assertTrue("InstantLose not occupied", !game.getSquare(4).isOccupied());
	}

	@Test(expected=AssertionError.class)
	public void jackHasNoSquare() throws AssertionError {
		game.movePlayer(3);
		assertTrue("InstantLose not occupied", !game.getSquare(4).isOccupied());
		jack.position(); // should throw AssertionError
	}

	@Test
	public void instantLoseToString() {
		ISquare instantLose = game.getSquare(4);
		assertEquals("[4 (InstantLose)]", instantLose.toString());
	}





}
