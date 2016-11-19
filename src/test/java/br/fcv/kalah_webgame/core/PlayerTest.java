package br.fcv.kalah_webgame.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Every.everyItem;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.fcv.kalah_webgame.core.Player;

public class PlayerTest {

	@Test
	public void whenAPlayerIsCreatedItShouldHaveSixStoneInEachPit() {
		Player player = new Player();
		assertThat(player.getPits(), everyItem(hasProperty("numberOfStones", equalTo(6))));
	}

	@Test
	public void whenAPlayerIsCreatedItsHouseShouldBeEmpty() {
		Player player = new Player();
		assertThat(player.getHouse().getNumberOfStones(), equalTo(0));
	}

	@Test
	public void whenAPlayerIsCreatedItShouldHaveSixPits() {
		Player player = new Player();
		assertThat(player.getPits(), hasSize(6));
	}

}
