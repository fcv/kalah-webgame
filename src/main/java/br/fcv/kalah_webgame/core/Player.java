package br.fcv.kalah_webgame.core;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.util.List;

/**
 * Represent one player in a {@link Game}.
 *
 * @author veronez
 *
 */
public enum Player {

	PLAYER_1 {
		@Override
		public Player getOpponent() {
			return PLAYER_2;
		}

		@Override
		public PlayerBoard getBoard(Game game) {
			return game.getPlayer1Board();
		}
	},

	PLAYER_2 {
		@Override
		public Player getOpponent() {
			return PLAYER_1;
		}

		@Override
		public PlayerBoard getBoard(Game game) {
			return game.getPlayer2Board();
		}
	};

	public abstract Player getOpponent();

	public abstract PlayerBoard getBoard(Game game);

	public void sow(int sourcePitIndex, PlayerBoard board, PlayerBoard opponent, TurnListener turnListener) {
		
		List<Pit> pits = board.getPits();
		Pit house = board.getHouse();

		checkArgument(sourcePitIndex >= 0 && sourcePitIndex < pits.size(),
				"invalid pit value %s", sourcePitIndex);

		int numberOfStones = pits.get(sourcePitIndex).removeStones();

		checkState(numberOfStones > 0, "pit %s has no stone",
				sourcePitIndex);

		int numberOfSownStonesPerPit = 1;
		int startIdx = sourcePitIndex + 1;
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

			// then sow opponent player board's pit
			for (int i = 0; numberOfStones > 0 && i < opponentPits.size(); i++) {
				Pit targetPit = opponentPits.get(i);
				targetPit.receiveStones(numberOfSownStonesPerPit);
				numberOfStones -= numberOfSownStonesPerPit;

				if (numberOfStones == 0) {
					turnListener.turnFinished(targetPit);
					return;
				}
			}

			// on next round starts in player board's first pit
			startIdx = 0;
		}
	}

	/**
	 * Moves all remaining stones is player's "ordinal" pits to his house pit.
	 */
	public void moveRemainingStonesToHouse(PlayerBoard board) {

		int remainingStones = 0;
		for (Pit pit : board.getPits()) {
			remainingStones += pit.removeStones();
		}
		board.getHouse().receiveStones(remainingStones);
	}
}
