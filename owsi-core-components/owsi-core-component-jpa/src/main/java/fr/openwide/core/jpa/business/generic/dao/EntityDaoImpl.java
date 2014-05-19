package fr.openwide.core.jpa.business.generic.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import fr.openwide.core.jpa.business.generic.model.GenericEntity;
import fr.openwide.core.jpa.business.generic.model.GenericEntityReference;

@Repository("entityDao")
public class EntityDaoImpl implements IEntityDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public <K extends Serializable & Comparable<K>, E extends GenericEntity<K, ?>> E getEntity(
			Class<E> clazz, K id) {
		return entityManager.find(clazz, id);
	}
	
	@Override
	public <K extends Serializable & Comparable<K>, E extends GenericEntity<K, ?>> E getEntity(GenericEntityReference<K, E> reference) {
		return getEntity(reference.getEntityClass(), reference.getEntityId());
	}

}
