package br.fcv.kalah_webgame.core;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
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

	public String toString() {
		return format("{pits: %s, house: %s}", pits, house);
	}

	public void sow(int pitIdx, Player opponent, TurnListener turnListener) {
		checkArgument(pitIdx >= 0 && pitIdx < pits.size(),
				"invalid pit value %s", pitIdx);

		int numberOfStones = pits.get(pitIdx).removeStones();

		checkState(numberOfStones > 0, "pit %s has no stone",
				pitIdx);

		int numberOfSownStonesPerPit = 1;
		int startIdx = pitIdx + 1;
		List<Pit> opponentPits = opponent.getPits();

		while (numberOfStones > 0) {

			// sow player's own pits
			for (int i = startIdx; numberOfStones > 0 && i < pits.size(); i++) {
				Pit targetPit = pits.get(i);
				SownPitStatus status = targetPit
						.receiveStones(numberOfSownStonesPerPit);
				numberOfStones -= numberOfSownStonesPerPit;

				if (numberOfStones == 0) {

					// when the last stone lands in an own empty pit, the player
					// captures this stone and all stones in the opposite pit
					// (the opponent players' pit) and puts them in his own
					// house.
					if (status.getPreviousNumberOfStones() == 0) {

						int opponentPitIndex = (pits.size() - 1) - i;
						Pit opponentPit = opponentPits.get(opponentPitIndex);
						int opponentPitStones = opponentPit.removeStones();
						int thisStonePit = targetPit.removeStones();
						house.receiveStones(thisStonePit + opponentPitStones);
					}
					turnListener.turnFinished(targetPit);
					return;
				}
			}

			// after reaching end of "ordinal" pits then sow own player's house
			// pit
			if (numberOfStones > 0) {
				house.receiveStones(numberOfSownStonesPerPit);
				numberOfStones -= numberOfSownStonesPerPit;

				if (numberOfStones == 0) {
					turnListener.turnFinished(house);
					return;
				}
			}

			// then sow opponent player's pit
			for (int i = 0; numberOfStones > 0 && i < opponentPits.size(); i++) {
				Pit targetPit = opponentPits.get(i);
				targetPit.receiveStones(numberOfSownStonesPerPit);
				numberOfStones -= numberOfSownStonesPerPit;

				if (numberOfStones == 0) {
					turnListener.turnFinished(targetPit);
					return;
				}
			}

			// on next round starts in player's first pit
			startIdx = 0;
		}
	}

}
