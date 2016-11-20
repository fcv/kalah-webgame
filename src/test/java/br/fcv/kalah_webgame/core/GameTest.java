package br.fcv.kalah_webgame.core;

import static br.fcv.kalah_webgame.core.Player.PLAYER_1;
import static br.fcv.kalah_webgame.core.Player.PLAYER_2;
import static br.fcv.kalah_webgame.core.PlayerBoardTest.containsNumberOfStones;
import static br.fcv.kalah_webgame.core.PlayerBoardTest.hasNumberOfStones;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GameTest {

	@Test
	public void whenAGameIsInitializedAllPlayersShouldBeInitialized() {
		Game game = new Game();
		assertThat(game.getPlayer1Board(), notNullValue());
		assertThat(game.getPlayer2Board(), notNullValue());
	}

	@Test
	public void whenAGameIsInitializedItShouldStartWithPlayer1() {
		Game game = new Game();
		assertThat(game.getActivePlayer(), equalTo(PLAYER_1));
	}

	@Test
	public void whenSowEndsOnPlayerHouseHeGetsAnotherTurn() {

		Game game = new Game();
		Player originalActivePlayer = game.getActivePlayer();
		game.sow(originalActivePlayer, 0);
		assertThat(game.getActivePlayer(), equalTo(originalActivePlayer));
	}

	@Test
	public void whenSowEndsActiveUserShouldBeChanged() {

		Game game = new Game();
		Player originalActivePlayer = game.getActivePlayer();
		assertThat(originalActivePlayer, equalTo(PLAYER_1));
		game.sow(originalActivePlayer, 1);
		assertThat(game.getActivePlayer(), equalTo(PLAYER_2));
	}

	@Test
	public void whenAPlayerRunOutOfStonesGameIsFinished() {

		Game game = new Game();
		PlayerBoard p1 = game.getPlayer1Board();
		PlayerBoard p2 = game.getPlayer2Board();

		p1.setNumberOfStones(0, 0, 0, 0, 0, 1);
		game.sow(PLAYER_1, 5);

		assertThat(p1.getPits(), containsNumberOfStones(0, 0, 0, 0, 0, 0));
		assertThat(p1.getHouse(), hasNumberOfStones(equalTo(1)));

		assertThat(p2.getPits(), containsNumberOfStones(0, 0, 0, 0, 0, 0));
		assertThat(p2.getHouse(), hasNumberOfStones(equalTo(36)));

		assertThat(game.isFinished(), is(true));
		assertThat(game.getWinner().get(), equalTo(PLAYER_2));
	}

	@Test
	public void whenAGameFinishesPlayerWithMoreStonesOnItsHouseWins() {

		// Player 2 wins scenario
		Game game = new Game();
		PlayerBoard p1 = game.getPlayer1Board();
		PlayerBoard p2 = game.getPlayer2Board();

		p1.setNumberOfStones(0, 0, 0, 0, 0, 1);
		game.sow(PLAYER_1, 5);

		assertThat(p1.getPits(), containsNumberOfStones(0, 0, 0, 0, 0, 0));
		assertThat(p1.getHouse(), hasNumberOfStones(equalTo(1)));

		assertThat(p2.getPits(), containsNumberOfStones(0, 0, 0, 0, 0, 0));
		assertThat(p2.getHouse(), hasNumberOfStones(equalTo(36)));

		assertThat(game.isFinished(), is(true));
		assertThat(game.getWinner().get(), equalTo(PLAYER_2));

		// Player 1 wins scenario
		game = new Game();
		p1 = game.getPlayer1Board();
		p2 = game.getPlayer2Board();

		p1.setNumberOfStones(1, 6, 6, 6, 6, 6);
		p2.setNumberOfStones(0, 0, 0, 0, 0, 1);

		game.sow(PLAYER_1, 0);
		game.sow(PLAYER_2, 5);

		assertThat(p1.getPits(), containsNumberOfStones(0, 0, 0, 0, 0, 0));
		assertThat(p1.getHouse(), hasNumberOfStones(equalTo(31)));

		assertThat(p2.getPits(), containsNumberOfStones(0, 0, 0, 0, 0, 0));
		assertThat(p2.getHouse(), hasNumberOfStones(equalTo(1)));

		assertThat(game.isFinished(), is(true));
		assertThat(game.getWinner().get(), equalTo(PLAYER_1));
	}
}
