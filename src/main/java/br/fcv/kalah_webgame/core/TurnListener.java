package br.fcv.kalah_webgame.core;

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
