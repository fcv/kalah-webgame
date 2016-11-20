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
 * @param <Entity>
 * @param <Id>
 */
@NoRepositoryBean
public interface FindExpectedRepositoryCustom<Entity, Id extends Serializable> {

	public Entity findExpected(Id id) throws EntityNotFoundException;

	public Optional<Entity> tryFind(Id id);

}
