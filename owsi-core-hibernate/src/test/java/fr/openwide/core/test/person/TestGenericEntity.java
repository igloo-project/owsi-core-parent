/*
 * Copyright (C) 2009-2010 Open Wide
 * Contact: contact@openwide.fr
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.openwide.core.test.person;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.openwide.core.hibernate.exception.SecurityServiceException;
import fr.openwide.core.hibernate.exception.ServiceException;
import fr.openwide.core.test.AbstractHibernateCoreTestCase;
import fr.openwide.core.test.hibernate.example.business.person.model.Person;
import fr.openwide.core.test.hibernate.example.business.person.service.PersonService;

public class TestGenericEntity extends AbstractHibernateCoreTestCase {

	@Autowired
	protected PersonService personService;

	@Test
	public void testGenericEntity() throws ServiceException, SecurityServiceException {
		Person person = new Person("FirstName", "LastName");

		assertNull(person.getId());
		assertTrue(person.isNew());

		personService.create(person);

		assertFalse(person.isNew());
		assertEquals(1, (int) person.getId());

		person.setId(2);

		assertEquals(2, (int) person.getId());

		person.setId(1);

		Person person1 = new Person("FirstName1", "LastName1");
		personService.create(person1);

		Person person2 = personService.getById(person.getId());

		assertFalse(person.equals(person1));
		assertTrue(person.equals(person2));

		Person person4 = person;

		assertFalse(person.compareTo(person1) == 0);
		assertTrue(person.compareTo(person4) == 0);

		assertEquals("LastName FirstName", person.getDisplayName());
		assertEquals("LastName FirstName", person.getNameForToString());
	}
}
