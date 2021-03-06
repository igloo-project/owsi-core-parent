package org.iglooproject.test.cache;

import org.hibernate.internal.SessionImpl;
import org.hibernate.stat.Statistics;
import org.junit.Assert;
import org.junit.Test;

import org.iglooproject.jpa.exception.SecurityServiceException;
import org.iglooproject.jpa.exception.ServiceException;
import org.iglooproject.test.AbstractJpaCoreTestCase;
import org.iglooproject.test.business.company.model.Company;
import org.iglooproject.test.business.person.model.Person;

public class TestCache extends AbstractJpaCoreTestCase {
	
	@Test
	public void testCache() throws ServiceException, SecurityServiceException {
		// cf ehcache-hibernate-company.xml
		
		getStatistics().setStatisticsEnabled(true);
		getStatistics().clear();
		Company company = new Company("Company Test Persist");
		Person person = new Person("Persist", "Numéro");
		companyService.create(company);
		personService.create(person);
		
		entityManagerReset();
		company = companyService.getById(company.getId());
		
		Assert.assertEquals(1, getStatistics().getSecondLevelCacheHitCount());
		Assert.assertEquals(0, getStatistics().getSecondLevelCacheMissCount());
		
		entityManagerReset();
		company = companyService.getById(company.getId());
		
		Assert.assertEquals(2, getStatistics().getSecondLevelCacheHitCount());
		Assert.assertEquals(0, getStatistics().getSecondLevelCacheMissCount());
		
		entityManagerReset();
		company = companyService.getById(company.getId());
		
		Assert.assertEquals(3, getStatistics().getSecondLevelCacheHitCount());
		Assert.assertEquals(0, getStatistics().getSecondLevelCacheMissCount());
		
		personService.getById(person.getId());
		
		// l'entité Person n'a pas l'annotation @Cache donc ne provoque pas de cache hit
		Assert.assertEquals(3, getStatistics().getSecondLevelCacheHitCount());
		Assert.assertEquals(0, getStatistics().getSecondLevelCacheMissCount());
	}
	
	protected Statistics getStatistics() {
		return ((SessionImpl) getEntityManager().getDelegate()).getSessionFactory().getStatistics();
	}
}
