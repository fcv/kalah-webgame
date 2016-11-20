package br.fcv.kalah_webgame.core;

import java.util.Optional;

public interface GameService {

	public Game newGame();

	public Game get(Long id);

	public Optional<Game> tryGet(Long id);

}
