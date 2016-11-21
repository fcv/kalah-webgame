package br.fcv.kalah_webgame.core;

import static br.fcv.kalah_webgame.core.Player.PLAYER_1;
import static br.fcv.kalah_webgame.core.Player.PLAYER_2;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represent one active Kalah game. The players, the board and its state,
 * the active player (the player in turn)
 *
 * @author veronez
 *
 */
@Entity
@Table(name = "game")
public class Game implements Serializable {

	private static final long serialVersionUID = 8484211766248241897L;

	public static final int INITIAL_NUMBER_OF_STONES = 6;
	public static final int NUMBER_OF_PITS = 6;

	@Id
	@GeneratedValue(strategy = AUTO)
	private Long id;

	@ManyToOne(fetch = LAZY, cascade = ALL)
	@JoinColumn(name = "player1_board", nullable = false)
	private PlayerBoard player1Board;

	@ManyToOne(fetch = LAZY, cascade = ALL)
	@JoinColumn(name = "player2_board", nullable = false)
	private PlayerBoard player2Board;

	@Enumerated(STRING)
	@Column(name = "active_player", nullable = false)
	private Player activePlayer;

	@Enumerated(STRING)
	@Column(name = "winner", nullable = true)
	private Player winner;

	public Game() {
		this(null);
	}

	public Game(Long id) {
		this.id = id;
		this.player1Board = new PlayerBoard(PLAYER_1);
		this.player2Board = new PlayerBoard(PLAYER_2);
		this.activePlayer = PLAYER_1;
		this.winner = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	/**
	 * <p>
	 * Triggers {@code Player}'s {@link Player#sow(int, PlayerBoard, PlayerBoard, TurnListener) sow}
	 * action in this game.
	 * </p>
	 *
	 * @param player the player to perform sow action
	 * @param sourcePitIndex the index of the Pit where stones will be retrieved from
	 * 
	 * @throws IllegalStateException if {@code player} is not the currently active player
	 * @throws IllegalStateException if this game has already been finished
	 */
	public void sow(Player player, int sourcePitIndex) {

		// TODO raise a meaningful Exception
		checkState(player.equals(activePlayer), "player should be the active player");
		// TODO raise a meaningful Exception
		checkState(!isFinished(), "game cannot be already finished");

		Player opponent = player.getOpponent();
		PlayerBoard opponentBoard = opponent.getBoard(this);

		PlayerBoard playerBoard = player.getBoard(this);
		player.sow(sourcePitIndex, playerBoard, opponentBoard, (lastSownPit) -> {

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
		Pit p2House = p2Board.getHouse();

		Player winner = p1House.getNumberOfStones() > p2House
				.getNumberOfStones() ? PLAYER_1 : PLAYER_2;

		this.winner = winner;
	}

	@Override
	public String toString() {
		return format("{id: %s, player1Board: %s, player2Board: %s, activePlayer: %s}", id, player1Board, player2Board, activePlayer);
	}

}
