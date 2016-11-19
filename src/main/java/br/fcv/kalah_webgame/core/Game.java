package br.fcv.kalah_webgame.core;

import static com.google.common.base.Preconditions.checkState;
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

	public void sow(Player player, int resourcePitIndex) {

		// TODO raise a meaningful Exception
		checkState(player.equals(activePlayer), "player should be the active player");

		Player opponent = player.equals(player1) ? player2 : player1;
		player.sow(resourcePitIndex, opponent, (lastSownPit) -> {

			// only change active player if current player's sown haven't
			// finished on his own house
			if (!lastSownPit.equals(player.getHouse())) {
				activePlayer = opponent;
			}
		});
	}

	@Override
	public String toString() {
		return format("{player1: %s, player2: %s, activePlayer: %s}", player1, player2, activePlayer);
	}

}
