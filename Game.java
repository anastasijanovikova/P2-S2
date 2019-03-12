/**
 * Initializes a game of Snakes and Ladders:
 * Creates a game board with different squares,
 * creates at least three players and
 * keeps track of the game changes.
 *
 * How the game works:
 * Each player rolls the die when it's his turn and
 * moves as many squares forward as the die sais.
 * Some squares have special meanings:
 * The player has to swap places with the next player, etc.
 *
 * A square can only be occupied by one player
 * at a time. If a second player lands on it,
 * he has to return to the first square.
 *
 *
 * @see SimpleGameTest does a test run of this game.
 * Uses (@link Square, @link Player, @link Die) and all their extended classes.
 * */

package snakes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

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
		game.setSquareToLadder(6, 2);
		game.setSquareToSnake(11, -6);
		game.setSquare(3, new SkipSquare(game, 3));
		game.setSquare(3, new SwapSquare(game, 5));
		game.setSquare(3, new InstantLose(game, 10));
		game.setSquare(3, new ScrambleUp(game, 7));
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

	public ArrayList<Player> getPlayers() {
		ArrayList<Player> playersList = new ArrayList<Player>();
		for (Player player: players) {
			Player playerOnTop = players.remove();
			playersList.add(playerOnTop); // adds to the end of the list
			players.add(playerOnTop);
		}
		return playersList; // index 0 first and index length-1 last player
	}

	public Player getNextPlayer() {
		ArrayList<Player> playersList = new ArrayList<Player>();
		playersList = getPlayers();
		Player nextPlayer = playersList.get(1); // second element
		return nextPlayer;
	}


	public void movePlayer(int roll) {
		assert roll >= 1 && roll <= 6;
		Player currentPlayer = players.remove(); // from front of queue
		players.add(currentPlayer); // to back of the queue
		currentPlayer.moveForward(roll);
		if (currentPlayer.wins()) {
			winner = currentPlayer;
		}
		if (currentPlayer.square().isInstantLose()) { removePlayer(); }
		assert invariant();
	}

	public void skip() {
		Player nextPlayer = players.remove();
		players.add(nextPlayer);
	}

	public void swap(Player player) {
		Square next = getNextPlayer().square();
		next.leave(getNextPlayer());
		next.enter(player);
	}

	public void moveRandomPlayer() {
		Player randomPlayer = getRandomPlayer();
		Square randomSquare = getRandomSquare();
		randomPlayer.moveToSquare(this, randomSquare.position());
	}

	public Player getRandomPlayer() {
		Random gen = new Random();
		ArrayList<Player> playersList = new ArrayList<Player>();
		playersList = getPlayers();
		int i = gen.nextInt(playersList.length - 1); // exclude current player
		Player randomPlayer = playersList.get(i);
		return randomPlayer;
	}

	public ISquare getRandomSquare() {
		Random gen = new Random();
		int position = gen.nextInt(this.size-1);
		ISquare square = getSquare(position);
		return square;
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
		players = new LinkedList<Player>();
		for (Player player : initPlayers) {
			player.joinGame(this);
			players.add(player);
		}
	}

	private void removePlayer() {
		assert squares.size() > 0;
		squares.remove(currentPlayer());
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
