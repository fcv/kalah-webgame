package br.fcv.kalah_webgame.core;

import static java.lang.String.format;

/**
 * Represents one Pit in games's board.
 *
 * @author veronez
 */
public class Pit {

	/**
	 * Number of stones currently in this pit
	 */
	private int numberOfStones;

	public Pit() {
		this(Game.INITIAL_NUMBER_OF_STONES);
	}

	public Pit(int numberOfStones) {
		this.numberOfStones = numberOfStones;
	}

	public int getNumberOfStones() {
		return numberOfStones;
	}

	public void setNumberOfStones(int numberOfStones) {
		this.numberOfStones = numberOfStones;
	}

	public SownPitStatus receiveStones(int numberOfReceivedStones) {
		int previousNumberOfStones = this.numberOfStones;
		int newNumberOfStones = previousNumberOfStones + numberOfReceivedStones;

		this.numberOfStones = newNumberOfStones;
		return new SownPitStatus(this, previousNumberOfStones,
				newNumberOfStones);
	}

	public int removeStones() {
		int previousNumberOfStones = this.numberOfStones;
		this.numberOfStones = 0;
		return previousNumberOfStones;
	}

	@Override
	public String toString() {
		return format("{numberOfStones: %s}", numberOfStones);
	}
}
