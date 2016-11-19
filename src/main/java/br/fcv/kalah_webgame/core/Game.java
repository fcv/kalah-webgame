package br.fcv.kalah_webgame.core;

import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;

import java.util.Optional;

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

	private Player winner;

	public Game() {
		this.player1 = new Player();
		this.player2 = new Player();
		this.activePlayer = player1;
		this.winner = null;
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

	public Optional<Player> getWinner() {
		return Optional.ofNullable(winner);
	}

	public boolean isFinished() {
		return winner != null;
	}

	public void sow(Player player, int resourcePitIndex) {

		// TODO raise a meaningful Exception
		checkState(player.equals(activePlayer), "player should be the active player");
		// TODO raise a meaningful Exception
		checkState(!isFinished(), "game cannot be already finished");

		Player opponent = player.equals(player1) ? player2 : player1;
		player.sow(resourcePitIndex, opponent, (lastSownPit) -> {

			Player p1 = getPlayer1();
			Player p2 = getPlayer2();

			if (p1.isOutOfStones() || p2.isOutOfStones()) {
				finishGame();
			} else {

				// only change active player if current player's sown haven't
				// finished on his own house
				if (!lastSownPit.equals(player.getHouse())) {
					activePlayer = opponent;
				}
			}
		});
	}

	/**
	 * Finishes a game. Move remaining players' stones to theirs house pits
	 * and determine winner player.
	 */
	private void finishGame() {

		Player p1 = getPlayer1();
		Player p2 = getPlayer2();

		p1.moveRemainingStonesToHouse();
		p2.moveRemainingStonesToHouse();

		Player winner = p1.getHouse().getNumberOfStones() > p2.getHouse()
				.getNumberOfStones() ? p1 : p2;

		this.winner = winner;
	}

	@Override
	public String toString() {
		return format("{player1: %s, player2: %s, activePlayer: %s}", player1, player2, activePlayer);
	}

}
