package fr.openwide.core.spring.property.service;

import java.util.Locale;

import fr.openwide.core.jpa.exception.SecurityServiceException;
import fr.openwide.core.jpa.exception.ServiceException;
import fr.openwide.core.spring.property.exception.PropertyServiceIncompleteRegistrationException;
import fr.openwide.core.spring.property.model.IMutablePropertyValueMap;
import fr.openwide.core.spring.property.model.MutablePropertyId;
import fr.openwide.core.spring.property.model.PropertyId;

public interface IPropertyService {

	/**
	 * @param propertyId The ID of the property whose value is to be retrieved.
	 * @return The current value of this property
	 * @throws PropertyServiceIncompleteRegistrationException If the property was not properly
	 * {@link IPropertyRegistry registered}.
	 */
	<T> T get(PropertyId<T> propertyId);

	/**
	 * Alter the value of a property.
	 * @param propertyId The ID of the property whose value is to be set.
	 * @param value The value the given property should be set to.
	 * @throws PropertyServiceIncompleteRegistrationException If the property was not properly
	 * {@link IPropertyRegistry registered}.
	 * @throws ServiceException If an error occurred in the underlying data store.
	 * @throws SecurityServiceException If an error occurred in the underlying data store.
	 */
	<T> void set(MutablePropertyId<T> propertyId, T value) throws ServiceException, SecurityServiceException;

	/**
	 * Alter the value of several properties in a single transaction.
	 * @param valueMap The ID => value map.
	 * @throws PropertyServiceIncompleteRegistrationException If one property was not properly
	 * {@link IPropertyRegistry registered}.
	 * @throws ServiceException If an error occurred in the underlying data store.
	 * @throws SecurityServiceException If an error occurred in the underlying data store.
	 */
	<T> void setAll(IMutablePropertyValueMap valueMap) throws ServiceException, SecurityServiceException;

	/**
	 * @param propertyId The ID of the property whose value is to be retrieved.
	 * @return The current value of this property, as a raw String exactly as it is stored in the underlying data store.
	 * @throws PropertyServiceIncompleteRegistrationException If the property was not properly
	 * {@link IPropertyRegistry registered}.
	 */
	<T> String getAsString(PropertyId<T> propertyId);

	boolean isConfigurationTypeDevelopment();
	Locale toAvailableLocale(Locale locale);

}
