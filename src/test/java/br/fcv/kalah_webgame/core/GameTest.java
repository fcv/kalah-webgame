package br.fcv.kalah_webgame.core;

import static br.fcv.kalah_webgame.core.PlayerTest.containsNumberOfStones;
import static br.fcv.kalah_webgame.core.PlayerTest.hasNumberOfStones;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GameTest {

	@Test
	public void whenAGameIsInitializedAllPlayersShouldBeInitialized() {
		Game game = new Game();
		assertThat(game.getPlayer1(), notNullValue());
		assertThat(game.getPlayer2(), notNullValue());
	}

	@Test
	public void whenAGameIsInitializedItShouldStartWithPlayer1() {
		Game game = new Game();
		assertThat(game.getActivePlayer(), equalTo(game.getPlayer1()));
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
		assertThat(originalActivePlayer, equalTo(game.getPlayer1()));
		game.sow(originalActivePlayer, 1);
		assertThat(game.getActivePlayer(), equalTo(game.getPlayer2()));
	}

	@Test
	public void whenAPlayerRunOutOfStonesGameIsFinished() {

		Game game = new Game();
		Player p1 = game.getPlayer1();
		Player p2 = game.getPlayer2();

		p1.setNumberOfStones(0, 0, 0, 0, 0, 1);
		game.sow(p1, 5);

		assertThat(p1.getPits(), containsNumberOfStones(0, 0, 0, 0, 0, 0));
		assertThat(p1.getHouse(), hasNumberOfStones(equalTo(1)));

		assertThat(p2.getPits(), containsNumberOfStones(0, 0, 0, 0, 0, 0));
		assertThat(p2.getHouse(), hasNumberOfStones(equalTo(36)));

		assertThat(game.isFinished(), is(true));
		assertThat(game.getWinner().get(), equalTo(p2));
	}
}
