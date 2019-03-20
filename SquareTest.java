package snakes;

public abstract class SquareTest {
	protected Game game;
	protected Player jack, jill, eric;

	void initializePlayers() {
		jack = new Player("Jack");
		jill = new Player("Jill");
		eric = new Player("Eric");
	}

	void initializeGame(int size) {
		initializePlayers();
		game = new Game(size, new Player[] { jack, jill, eric });
	}

	public abstract void newGame();
}
