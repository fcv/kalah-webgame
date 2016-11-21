package br.fcv.kalah_webgame.core.support.spring_data;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * <p>
 * Defines {@code find} methods that do not return {@code null} when an Entity
 * is not found.
 * </p>
 * <p>
 * {@code find} method defined in this {@link Repository} either throw {@link EntityNotFoundException}
 * or return an {@link Optional}.
 * </p>
 * 
 * @author veronez
 *
 * @param <Entity> Entity type
 * @param <Id> Entity's Id type
 */
@NoRepositoryBean
public interface FindExpectedRepositoryCustom<Entity, Id extends Serializable> {

	/**
	 * Retrieves an entity by its id or throws {@link EntityNotFoundException}
	 * if none is found.
	 *
	 * @param id Entity's id
	 * @return the Entity whose id matches the given {@code id} value
	 *
	 * @throws EntityNotFoundException if no entity is found
	 */
	public Entity findExpected(Id id) throws EntityNotFoundException;

	/**
	 * Retrieves an entity by its id or {@link Optional#empty() empty} if none
	 * is found.
	 *
	 * @param id Entity's id
	 * @return the Entity whose id matches the given {@code id} value
	 */
	public Optional<Entity> tryFind(Id id);

}
