package br.fcv.kalah_webgame.core;

import static br.fcv.kalah_webgame.core.Player.PLAYER_1;
import static br.fcv.kalah_webgame.core.Player.PLAYER_2;
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

	private PlayerBoard player1Board;
	private PlayerBoard player2Board;

	private Player activePlayer;

	private Player winner;

	public Game() {
		this.player1Board = new PlayerBoard(PLAYER_1);
		this.player2Board = new PlayerBoard(PLAYER_2);
		this.activePlayer = PLAYER_1;
		this.winner = null;
	}

	public PlayerBoard getPlayer1Board() {
		return player1Board;
	}

	public PlayerBoard getPlayer2Board() {
		return player2Board;
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

		Player opponent = player.getOpponent();
		PlayerBoard opponentBoard = opponent.getBoard(this);

		PlayerBoard playerBoard = player.getBoard(this);
		player.sow(resourcePitIndex, playerBoard, opponentBoard, (lastSownPit) -> {

			PlayerBoard p1 = getPlayer1Board();
			PlayerBoard p2 = getPlayer2Board();

			if (p1.isOutOfStones() || p2.isOutOfStones()) {
				finishGame();
			} else {

				// only change active player if current player's sown haven't
				// finished on his own house
				if (!lastSownPit.equals(playerBoard.getHouse())) {
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

		// player 1's board
		PlayerBoard p1Board = getPlayer1Board();
		// player 2's board
		PlayerBoard p2Board = getPlayer2Board();

		PLAYER_1.moveRemainingStonesToHouse(p1Board);
		PLAYER_2.moveRemainingStonesToHouse(p2Board);

		Pit p1House = p1Board.getHouse();
		Pit p2House = p1Board.getHouse();

		Player winner = p1House.getNumberOfStones() > p2House
				.getNumberOfStones() ? PLAYER_1 : PLAYER_2;

		this.winner = winner;
	}

	@Override
	public String toString() {
		return format("{player1Board: %s, player2Board: %s, activePlayer: %s}", player1Board, player2Board, activePlayer);
	}

}
