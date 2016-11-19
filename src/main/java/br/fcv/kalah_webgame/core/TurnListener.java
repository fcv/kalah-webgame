package br.fcv.kalah_webgame.core;

/**
 * Action to be called after an {@link Player} has finished its turn's move.
 *
 * @author veronez
 *
 */
public interface TurnListener {

	public static TurnListener emptyListener() {
		return new TurnListener() {

			@Override
			public void turnFinished(Pit lastSowedPit) {
			}
		};
	}

	public void turnFinished(Pit lastSowedPit);

}
