package fr.openwide.core.jpa.business.generic.model.migration;

import java.io.Serializable;

import javax.persistence.Transient;

import fr.openwide.core.jpa.business.generic.model.GenericEntity;

/**
 * This class should only be used as a temporary measure to migrate entities from an old application
 * to a new one.
 * 
 * It allows us to keep the old id as the new id.
 *
 * @see MigratedFromOldApplicationSequenceGenerator
 */
public abstract class MigratedFromOldApplicationGenericEntity<K extends Serializable & Comparable<K>, E extends GenericEntity<K, ?>>
		extends GenericEntity<K, E> implements IMigratedFromOldApplicationEntity<K> {

	private static final long serialVersionUID = 2034570162020079499L;
	
	@Transient
	private K oldApplicationId;

	/* (non-Javadoc)
	 * @see fr.openwide.core.jpa.business.generic.model.migration.IMigratedFromOldApplicationEntity#getOldApplicationId()
	 */
	@Override
	public K getOldApplicationId() {
		return oldApplicationId;
	}

	public void setOldApplicationId(K oldApplicationId) {
		this.oldApplicationId = oldApplicationId;
	}

}
