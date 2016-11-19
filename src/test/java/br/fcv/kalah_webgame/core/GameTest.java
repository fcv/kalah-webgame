package br.fcv.kalah_webgame.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.fcv.kalah_webgame.core.Game;

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

}
