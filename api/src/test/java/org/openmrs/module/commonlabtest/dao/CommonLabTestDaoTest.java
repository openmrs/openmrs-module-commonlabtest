/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.commonlabtest.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.UserService;
import org.openmrs.module.commonlabtest.api.dao.CommonLabTestDao;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * It is an integration test (extends BaseModuleContextSensitiveTest), which verifies DAO methods
 * against the in-memory H2 database. The database is initially loaded with data from
 * standardTestDataset.xml in openmrs-api. All test methods are executed in transactions, which are
 * rolled back by the end of each test method.
 */
public class CommonLabTestDaoTest extends BaseModuleContextSensitiveTest {
	
	@Autowired
	CommonLabTestDao dao;
	
	@Autowired
	UserService userService;
	
	@Test
	@Ignore("Unignore if you want to make the Item class persistable, see also Item and liquibase.xml")
	public void saveItem_shouldSaveAllPropertiesInDb() {
		//		//Given
		//		Item item = new Item();
		//		item.setDescription("some description");
		//		item.setOwner(userService.getUser(1));
		//		
		//		//When
		//		dao.saveItem(item);
		//		
		//		//Let's clean up the cache to be sure getItemByUuid fetches from DB and not from cache
		//		Context.flushSession();
		//		Context.clearSession();
		//		
		//		//Then
		//		Item savedItem = dao.getItemByUuid(item.getUuid());
		//		
		//		assertThat(savedItem, org.hamcrest.Matchers.hasProperty("uuid", org.hamcrest.Matchers.is(item.getUuid())));
		//		assertThat(savedItem, org.hamcrest.Matchers.hasProperty("owner", org.hamcrest.Matchers.is(item.getOwner())));
		//		assertThat(savedItem, org.hamcrest.Matchers.hasProperty("description", org.hamcrest.Matchers.is(item.getDescription())));
	}
}
