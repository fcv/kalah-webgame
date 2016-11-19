package br.fcv.kalah_webgame.core;

import static org.hamcrest.Matchers.equalTo;
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
}
