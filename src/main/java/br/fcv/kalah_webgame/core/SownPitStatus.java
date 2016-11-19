package br.fcv.kalah_webgame.core;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

public class SownPitStatus {

	private final Pit pit;
	private final int previousNumberOfStones;
	private final int newNumberOfStones;

	public SownPitStatus(Pit pit, int previousNumberOfStones,
			int newNumberOfStones) {
		checkArgument(previousNumberOfStones >= 0,
				"previousNumberOfStones cannot be negative");
		checkArgument(newNumberOfStones >= 0,
				"newNumberOfStones cannot be negative");

		this.pit = checkNotNull(pit, "pit cannot be null");
		this.previousNumberOfStones = previousNumberOfStones;
		this.newNumberOfStones = newNumberOfStones;
	}

	public Pit getPit() {
		return pit;
	}

	public int getPreviousNumberOfStones() {
		return previousNumberOfStones;
	}

	public int getNewNumberOfStones() {
		return newNumberOfStones;
	}

	@Override
	public String toString() {
		return format(
				"{pit: %s, previousNumberOfStones: %s, newNumberOfStones: %s}",
				pit, previousNumberOfStones, newNumberOfStones);
	}

}
