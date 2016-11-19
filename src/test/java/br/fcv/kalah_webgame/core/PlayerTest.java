package br.fcv.kalah_webgame.core;

import static br.fcv.kalah_webgame.core.Player.PLAYER_1;
import static br.fcv.kalah_webgame.core.PlayerBoardTest.containsNumberOfStones;
import static br.fcv.kalah_webgame.core.PlayerBoardTest.hasNumberOfStones;
import static br.fcv.kalah_webgame.core.TurnListener.emptyListener;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void whenPlayerSnows() {

		Game game = new Game();
		PlayerBoard p1 = game.getPlayer1Board();
		PlayerBoard p2 = game.getPlayer2Board();

		PLAYER_1.sow(0, p1, p2, emptyListener());

		assertThat(p1.getPits(), containsNumberOfStones(0, 7, 7, 7, 7, 7));
		assertThat(p1.getHouse(), hasNumberOfStones(equalTo(1)));

		assertThat(p2.getPits(), containsNumberOfStones(6, 6, 6, 6, 6, 6));
		assertThat(p2.getHouse(), hasNumberOfStones(equalTo(0)));
	}

	@Test
	public void whenPlaySnowEndsOnAnEmptyPitItShouldTakeOpponentStones() {

		Game game = new Game();
		PlayerBoard p1 = game.getPlayer1Board();
		PlayerBoard p2 = game.getPlayer2Board();

		p1.setNumberOfStones(1, 0, 2, 2, 2, 2);
		p2.setNumberOfStones(4, 3, 1, 4, 5, 1);

		PLAYER_1.sow(0, p1, p2, emptyListener());

		assertThat(p1.getPits(), containsNumberOfStones(0, 0, 2, 2, 2, 2));
		assertThat(p1.getHouse(), hasNumberOfStones(equalTo(6)));

		assertThat(p2.getPits(), containsNumberOfStones(4, 3, 1, 4, 0, 1));
		assertThat(p2.getHouse(), hasNumberOfStones(equalTo(0)));
	}

}
