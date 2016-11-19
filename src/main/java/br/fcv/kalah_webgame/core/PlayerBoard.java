package br.fcv.kalah_webgame.core;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Represents one player's board "sections" (pits and house) in the
 * whole Game's board.
 *
 * @author veronez
 */
public class PlayerBoard {

	/**
	 * The player to whon this board part belongs to
	 */
	public Player owner;

	/**
	 * PlayerBoard's "regular" pits within game's board
	 */
	private List<Pit> pits;

	/**
	 * PlayerBoard's house (kalah) pit
	 */
	private Pit house;

	public PlayerBoard(Player owner) {

		this.owner = owner;
		// player is initialized with 6 pits
		pits = IntStream.range(0, Game.NUMBER_OF_PITS)
			.mapToObj(i -> new Pit())
			.collect(toCollection(ArrayList::new));

		// PlayerBoard's house is initialized with no stone
		house = new Pit(0);
	}

	public List<Pit> getPits() {
		return pits;
	}

	/**
	 * Modifies number of stones on this player's pits.
	 *
	 * It is expected to be used only by test classes. Thus its visibility level
	 * is set to package-private (default)
	 */
	void setNumberOfStones(int... numberOfStones) {
		checkNotNull(numberOfStones, "numberOfStones cannot be null");
		List<Pit> pits = getPits();
		checkState(
				numberOfStones.length == pits.size(),
				"numberOfStones's length (%s) should be equals to pits's size (%s)",
				numberOfStones.length, pits.size());

		for (int i = 0; i < numberOfStones.length; i++) {
			int n = numberOfStones[i];
			pits.get(i).setNumberOfStones(n);
		}
	}

	public Pit getHouse() {
		return house;
	}

	/**
	 * Return <code>true</code> whether all this player's pits, besides its house,
	 * are empty.
	 * 
	 * @return
	 */
	public boolean isOutOfStones() {
		return getPits().stream().noneMatch(p -> p.getNumberOfStones() > 0);
	}

	public String toString() {
		return format("{pits: %s, house: %s}", pits, house);
	}
}
