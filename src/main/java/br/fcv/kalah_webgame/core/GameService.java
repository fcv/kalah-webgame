package br.fcv.kalah_webgame.core;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

public interface GameService {

	/**
	 * Creates a new Game with all default values initialized.
	 * 
	 * @return newly created Game
	 */
	public Game newGame();

	/**
	 * Retrieves a previously created {@link Game} by its {@link Game#getId() id}.
	 *
	 * @param id not null
	 * @return matched Game instance
	 * 
	 * @throws EntityNotFoundException if there is no {@link Game} with provided {@code id}
	 */
	public Game get(Long id);

	/**
	 * Retrieves a previously created {@link Game} by its {@link Game#getId() id}.
	 *
	 * @param id not null
	 * @return matched Game instance or {@link Optional#empty() empty} if none is found
	 * 
	 */
	public Optional<Game> tryGet(Long id);

	/**
	 * Triggers {@link Game#sow(Player, int)} action in {@link Game} instance
	 * whose {@link Game#getId() id} property matches provided {@code id} value.
	 * 
	 * @param id Game's id, not null
	 * @param player {@link Player} to perform sow action, not null
	 * @param sourcePitIndex index of Player's {@link Pit} where stones will be taken from
	 * 
	 * @throws EntityNotFoundException if there is no {@link Game} with provided {@code id}
	 * @throws IllegalArgumentException if {@code sourcePitIndex} is not a valid pit index
	 * @throws IllegalStateException if {@code player} is not the currently active player
	 * @throws IllegalStateException if the Game has already been finished
	 */
	public Game sow(Long id, Player player, int sourcePitIndex);

}
