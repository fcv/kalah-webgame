package br.fcv.kalah_webgame.core;

import static br.fcv.kalah_webgame.core.Player.PLAYER_1;
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

public class PlayerBoardTest {

	@Test
	public void whenAPlayerBoardIsCreatedItShouldHaveSixStoneInEachPit() {
		PlayerBoard playerBoard = new PlayerBoard(PLAYER_1);
		assertThat(playerBoard.getPits(), everyItem(hasProperty("numberOfStones", equalTo(6))));
	}

	@Test
	public void whenAPlayerBoardIsCreatedItsHouseShouldBeEmpty() {
		PlayerBoard playerBoard = new PlayerBoard(PLAYER_1);
		assertThat(playerBoard.getHouse().getNumberOfStones(), equalTo(0));
	}

	@Test
	public void whenAPlayerBoardIsCreatedItShouldHaveSixPits() {
		PlayerBoard playerBoard = new PlayerBoard(PLAYER_1);
		assertThat(playerBoard.getPits(), hasSize(6));
	}

	//
	// static supporting methods
	//
	public static Matcher<? super Pit> hasNumberOfStones(
			Matcher<Integer> matcher) {
	
		return hasProperty("numberOfStones", matcher);
	}

	/**
	 * Matches whether the number of stones of a list of pits is equals to one
	 * provides as argument (respecting its order)
	 *
	 * @param stones number of stones in each pit
	 * @return
	 */
	public static Matcher<Iterable<? extends Pit>> containsNumberOfStones(
			int... stones) {
	
		List<Matcher<? super Pit>> matchers = stream(stones)
				.mapToObj(n -> PlayerBoardTest.hasNumberOfStones(equalTo(n)))
				.collect(toList());
		return contains(matchers);
	}

}
