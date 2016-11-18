package br.fcv.kalah_webgame.core;

import static java.lang.String.format;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Represents one player and its board "section" (pits and house) in the
 * whole Game's board.
 *
 * @author veronez
 */
public class Player {

	/**
	 * Player's "regular" pits within game's board
	 */
	private List<Pit> pits;

	/**
	 * Player's house (kalah) pit
	 */
	private Pit house;

	public Player() {
		// player is initialized with 6 pits
		pits = IntStream.range(0, Game.NUMBER_OF_PITS)
			.mapToObj(i -> new Pit())
			.collect(toCollection(ArrayList::new));

		// Player's house is initialized with no stone
		house = new Pit(0);
	}

	public List<Pit> getPits() {
		return pits;
	}

	public Pit getHouse() {
		return house;
	}

	public String toString() {
		return format("{pits: %s, house: %s}", pits, house);
	}

}
