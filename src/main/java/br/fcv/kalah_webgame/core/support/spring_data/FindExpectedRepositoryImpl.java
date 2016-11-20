package br.fcv.kalah_webgame.core.support.spring_data;

import static java.lang.String.format;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class FindExpectedRepositoryImpl<Entity, Id extends Serializable>
		extends SimpleJpaRepository<Entity, Id> implements
		FindExpectedRepositoryCustom<Entity, Id> {

	private final Class<Entity> entityClass;
	private final EntityManager em;

	public FindExpectedRepositoryImpl(
			JpaEntityInformation<Entity, Id> entityInformation,
			EntityManager entityManager) {

		super(entityInformation, entityManager);
		this.entityClass = entityInformation.getJavaType();
		this.em = entityManager;
	}

	@Override
	public Entity findExpected(Id id) {
		Entity entity = em.find(entityClass, id);
		if (entity == null) {
			throw newEntityNotFoundException(entityClass, id);
		}

		return entity;
	}

	@Override
	public Optional<Entity> tryFind(Id id) {
		Entity entity = em.find(entityClass, id);
		return Optional.ofNullable(entity);
	}

	private static EntityNotFoundException newEntityNotFoundException(
			Class<?> entityClass, Object id) {
		return new EntityNotFoundException(format(
				"Could not found an instance of %s with id %s",
				entityClass.getName(), id));
	}

}
