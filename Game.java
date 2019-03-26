package snakes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Initializes a game of Snakes and Ladders:
 * Creates a game board with different squares,
 * creates at least three players and
 * keeps track of the game changes.
 *
 * How the game works:
 * Each player rolls the die when it's his turn and
 * moves as many squares forward as the faces of the die say.
 * Some squares have special meanings:
 * The player has to swap places with the next player,
 * if you land on a Ladder (@Link Ladder) you can move forward
 * a certain number of squares and so forth.
 *
 * A square can only be occupied by one player
 * at a time. If a second player lands on it,
 * he has to return to the first square.
 *
 * The first player to land on the last square wins the game!
 *
 *
 * @see SimpleGameTest does a test run of this game.
 * Uses (@link Square, @link Player, @link Die) and all their extending classes.
 * */
public class Game {
	private List<ISquare> squares;
	private int size;
	private Queue<Player> players;
	private Player winner;
	private Die die;

	private boolean invariant() {
		return squares.size() > 3
				&& size == squares.size()
				&& players.size() > 1;
	}

	public Game(int size, Player[] initPlayers) {
		this.size = size;
		this.addSquares(size);
		this.addPlayers(initPlayers);
		this.die = new Die();
		assert invariant();
	}

	public boolean isValidPosition(int position) {
		return position >= 1 && position <= size;
	}

	public static void main(String args[]) {
		Player[] players = { new Player("Jack"), new Player("Jill") };
		Game game = new Game(15, players);
		game.setSquareToLadder(2, 4);
		game.setSquareToLadder(7, 2);
		game.setSquareToSnake(11, -6);
		game.setSquare(3, new SkipSquare(game, 3));
		game.setSquare(5, new SwapSquare(game, 5));
		game.setSquare(10, new InstantLose(game, 10));
		game.setSquare(13, new ScrambleUp(game, 13));
		game.play();
	}

	public void play() {
		System.out.println("Initial state: " + this);
		while (this.notOver()) {
			int roll = die.roll();
			System.out.println(this.currentPlayer() + " rolls " + roll + ":  " + this);
			this.movePlayer(roll);
		}
		System.out.println("Final state:   " + this);
		System.out.println(this.winner() + " wins!");
	}

	public boolean notOver() {
		return winner == null;
	}

	public boolean isOver() {
		return !this.notOver();
	}

	public Player currentPlayer() {
		return players.peek();
	}

	/**
	 * Returns the next player, who is currently on top of the queue
     * since the current player has already been removed.
     *
	 * Is used in the (@link SwapSquare) class.
     *
	 * @return the next Player in line
	 */
	public Player nextPlayer() {
		assert !players.isEmpty();
		Player nextPlayer = players.peek();
		return nextPlayer;
	}

	/**
	 * It's job is to tell the player where he has to go
	 * according to the number the die has rolled.
	 * Is used in play().
	 *
	 * @param roll int containing the number the die rolled
	 */
	public void movePlayer(int roll) {
		assert roll >= 1 && roll <= 6;
		Player currentPlayer = players.peek();
		players.remove(); // remove player from front of queue
		currentPlayer.moveForward(roll);
        if (!currentPlayer.square().isInstantLose()) {
            players.add(currentPlayer); // adds player to back of queue
        }
		applieEffectsSpecialSquares(currentPlayer);
        if (currentPlayer.wins()) { winner = currentPlayer; }
		assert invariant();
	}

	/**
	 * Is used inside the movePlayer() method.
	 * Squares with special effects require special treatment.
	 *
	 * Example: A player lands on a (@link InstantLose) square
	 * and has to be taken out of the game.
	 *
	 * @param player the Player that lands on the special square
	 */
	private void applieEffectsSpecialSquares(Player player) {
		if (player.square().isInstantLose()) {
			takePlayerOutOfHisSquare(player);
			player.leaveSquare();
		}
		if (player.square().isSkipSquare()) {
			skip();
		}
		if (player.square().isScrambleUp()) {
			moveRandomPlayer();
		}
	}

	/**
	 * Removes the player at the front of the queue
	 * and adds him to the back.
	 * Used inside the applieEffectsSpecialSquares() method.
	 */
	public void skip() {
		Player skippedPlayer = players.remove();
		players.add(skippedPlayer);
		assert !players.isEmpty();
	}

	/**
	 * Makes one player leave his square
	 * and another player enter it.
	 *
	 * @param current
	 * @param next
	 */

	public void swap(Player current, Player next) {
		ISquare nextSquare = next.square();
		takePlayerOutOfHisSquare(next);
		current.enterSquare(this, nextSquare.position());
	}

	public void takePlayerOutOfHisSquare(Player player) {
		assert player.square() != null;
		ISquare leaveSquare = player.square();
		leaveSquare.leave(player);
		if (!leaveSquare.isFirstSquare()) { assert !leaveSquare.isOccupied(); }
	}

	public void moveRandomPlayer() {
	    ISquare square = getRandomSquare();
	    Player player = getRandomPlayer();
	    player.moveToSquare(this, square.position());
    }

	public Player getRandomPlayer() {
        Player[] arrayPlayers = players.toArray(new Player[players.size()-1]);
        Random gen = new Random();
        int indexRandomPlayer = gen.nextInt(players.size()-1);
        return arrayPlayers[indexRandomPlayer];
    }

    public ISquare getRandomSquare() {
        Random gen = new Random();
        int position = gen.nextInt(this.size) + 1;
        return getSquare(position);
    }

	public void setSquare(int position, ISquare square) {
		// Do not change the type of the first or last square!
		assert !this.getSquare(position).isLastSquare()
				&& !this.getSquare(position).isFirstSquare();
		this.initSquare(position, square);
		assert invariant();
	}

	public Player winner() {
		return winner;
	}

	public ISquare firstSquare() {
		return squares.get(0);
	}

	public ISquare getSquare(int position) {
		assert this.isValidPosition(position);
		return squares.get(position - 1);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (ISquare square : squares) {
			buffer.append(square.toString());
		}
		return buffer.toString();
	}

	private void addSquares(int size) {
		squares = new ArrayList<ISquare>();
		for (int i = 0; i < size; i++) {
			Square square = new Square(this, i + 1);
			squares.add(square);
		}
		this.initSquare(1, new FirstSquare(this, 1));
		this.initSquare(size, new LastSquare(this, size));
	}

	private void addPlayers(Player[] initPlayers) {
		players = new LinkedList<Player>(); //players is instantiated as LinkedList
		for (Player player : initPlayers) {
			player.joinGame(this);
			players.add(player);
		}
	}

	private void initSquare(int position, ISquare square) {
		assert this.isValidPosition(position) && square != null;
		squares.set(position - 1, square);
	}

	public void setSquareToLadder(int position, int transport) {
		this.setSquare(position, new Ladder(transport, this, position));
	}

	public void setSquareToSnake(int position, int transport) {
		this.setSquare(position, new Snake(transport, this, position));
	}

	public ISquare findSquare(int position, int moves) {
		assert position + moves <= 2 * size - 1; // can't go more than size-1 moves backwards past end
		int target = position + moves;
		if (target > size) { // reverse direction if we go past the end
			target = size - (target - size);
		}
		return this.getSquare(target);
	}

	public Die getDie() {
		return die;
	}

	public int getSize() { return size; }
}
