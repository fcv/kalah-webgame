package br.fcv.kalah_webgame.core;

import static java.lang.String.format;

/**
 * Represent one active Kalah game. The players, the board and its state,
 * the active player (the player in turn)
 *
 * @author veronez
 *
 */
public class Game {

	public static final int INITIAL_NUMBER_OF_STONES = 6;
	public static final int NUMBER_OF_PITS = 6;

	private Player player1;
	private Player player2;

	private Player activePlayer;

	public Game() {
		this.player1 = new Player();
		this.player2 = new Player();
		this.activePlayer = player1;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public Player getActivePlayer() {
		return activePlayer;
	}

	@Override
	public String toString() {
		return format("{player1: %s, player2: %s, activePlayer: %s}", player1, player2, activePlayer);
	}

}
