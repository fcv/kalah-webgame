package br.fcv.kalah_webgame.core;

import static br.fcv.kalah_webgame.core.TurnListener.emptyListener;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Every.everyItem;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Matcher;
import org.junit.Test;

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

	@Test
	public void whenPlayerSnows() {

		Game game = new Game();
		Player p1 = game.getPlayer1();
		Player p2 = game.getPlayer2();

		p1.sow(0, p2, emptyListener());

		assertThat(p1.getPits(), containsNumberOfStones(0, 7, 7, 7, 7, 7));
		assertThat(p1.getHouse(), hasNumberOfStones(equalTo(1)));

		assertThat(p2.getPits(), containsNumberOfStones(6, 6, 6, 6, 6, 6));
		assertThat(p2.getHouse(), hasNumberOfStones(equalTo(0)));
	}

	@Test
	public void whenPlaySnowEndsOnAnEmptyPitItShouldTakeOpponentStones() {

		Game game = new Game();
		Player p1 = game.getPlayer1();
		Player p2 = game.getPlayer2();

		p1.setNumberOfStones(1, 0, 2, 2, 2, 2);
		p2.setNumberOfStones(4, 3, 1, 4, 5, 1);

		p1.sow(0, p2, emptyListener());

		assertThat(p1.getPits(), containsNumberOfStones(0, 0, 2, 2, 2, 2));
		assertThat(p1.getHouse(), hasNumberOfStones(equalTo(6)));

		assertThat(p2.getPits(), containsNumberOfStones(4, 3, 1, 4, 0, 1));
		assertThat(p2.getHouse(), hasNumberOfStones(equalTo(0)));
	}

	//
	// static supporting methods
	//

	/**
	 * Matches whether the number of stones of a list of pits is equals to one
	 * provides as argument (respecting its order)
	 *
	 * @param stones number of stones in each pit
	 * @return
	 */
	private static Matcher<Iterable<? extends Pit>> containsNumberOfStones(
			int... stones) {

		List<Matcher<? super Pit>> matchers = stream(stones)
				.mapToObj(n -> hasNumberOfStones(equalTo(n)))
				.collect(toList());
		return contains(matchers);
	}

	private static Matcher<? super Pit> hasNumberOfStones(
			Matcher<Integer> matcher) {

		return hasProperty("numberOfStones", matcher);
	}
}
