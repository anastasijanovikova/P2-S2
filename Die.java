package snakes;

/**
 * Represents a 6-faced die that can be rolled.
 * Chooses a random number between 1 and 6.
 *
 * Is created and used inside the (@link Game) class.
 * Is tested inside the (@link DieTest) class.
 */
public class Die {
	static final int FACES = 6;
	
	public int roll() {
		int result = 1 + (int) (FACES * Math.random());
		assert result >= 1 && result <= FACES;
		return result;
	}
}
