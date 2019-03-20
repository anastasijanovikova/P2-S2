package snakes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

public class InstantLoseSquareTest {
	private Player jack;
	private Player jill;
	private Game game;

	@Before
	public void initializeGame() {
		jack = new Player("Jack");
		jill = new Player("Jill");
		Player[] args = { jack, jill };
		game = new Game(12, args);
		game.setSquareToLadder(2, 2);
		game.setSquareToLadder(7, -5);
		game.setSquare(4, new InstantLose(game, 4));
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
		assertEquals("[4 (InstantLose)]", game.getSquare(4).toString());
	}

    @Test
    public void jackMove1() {
        game.movePlayer(2);
        assertEquals("Jack is transported to Square ", 3, jack.position());
        assertEquals(1, jill.position());
        assertTrue("Game is not over", game.notOver());
    }

    @Test
	public void jackMoveToInstantLose() {
		assertTrue(game.notOver());
		game.movePlayer(3);
		assertEquals("Jack is transported to InstantLose Square", 4, jack.position());
		assertTrue("Game is over", game.isOver());
	}

	@Test
	public void instantLoseToString() {
		ISquare instantLose = game.getSquare(4);
		assertEquals("[4 (InstantLose)]", instantLose.toString());
	}





}
