/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.commonlabtest;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * It is an integration test (extends BaseModuleContextSensitiveTest), which verifies DAO methods
 * against the in-memory H2 database. The database is initially loaded with data from
 * standardTestDataset.xml in openmrs-api. All test methods are executed in transactions, which are
 * rolled back by the end of each test method.
 */
public class CommonLabTestDaoTest extends CommonLabTestBaseTest {
	
	@Autowired
	CommonLabTestDaoImpl dao;
	
	@Test
	@Ignore("Unignore if you want to make the Item class persistable, see also Item and liquibase.xml")
	public void saveItem_shouldSaveAllPropertiesInDb() {
		//		//Given
		//		Item item = new Item();
		//		//When
		//		dao.saveItem(item);
		//		//Let's clean up the cache to be sure getItemByUuid fetches from DB and not from cache
		//		Context.flushSession();
		//		Context.clearSession();
		//		//Then
		//		Item savedItem = dao.getItemByUuid(item.getUuid());
		//		assertThat(savedItem, org.hamcrest.Matchers.hasProperty("uuid", org.hamcrest.Matchers.is(item.getUuid())));
		//		assertThat(savedItem, org.hamcrest.Matchers.hasProperty("owner", org.hamcrest.Matchers.is(item.getOwner())));
		//		assertThat(savedItem, org.hamcrest.Matchers.hasProperty("description", org.hamcrest.Matchers.is(item.getDescription())));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getAllLabTestAttributeTypes(boolean)}.
	 */
	@Test
	public final void testGetAllLabTestAttributeTypes() {
		Context.clearSession();
		List<LabTestAttributeType> list = dao.getAllLabTestAttributeTypes(false);
		assertTrue(list.size() == activeLabTestAttributeTypes.size());
		assertThat(list, Matchers.not(Matchers.hasItems(cad4tbScore, xrayFilmPrinted)));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getAllLabTestTypes(boolean)}.
	 */
	@Test
	public final void testGetAllLabTestTypes() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestTypes(java.lang.String, java.lang.String, org.openmrs.module.commonlabtest.LabTestType.LabTestGroup, org.openmrs.Concept, boolean)}.
	 */
	@Test
	public final void testGetLabTestTypes() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTest(org.openmrs.Encounter)}.
	 */
	@Test
	public final void testGetLabTestEncounter() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTest(java.lang.Integer)}.
	 */
	@Test
	public final void testGetLabTestInteger() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttribute(java.lang.Integer)}.
	 */
	@Test
	public final void testGetLabTestAttribute() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttributeByUuid(java.lang.String)}.
	 */
	@Test
	public final void testGetLabTestAttributeByUuid() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttributes(org.openmrs.module.commonlabtest.LabTestAttributeType, org.openmrs.module.commonlabtest.LabTest, org.openmrs.Patient, java.lang.String, java.util.Date, java.util.Date, boolean)}.
	 */
	@Test
	public final void testGetLabTestAttributes() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttributeType(java.lang.Integer)}.
	 */
	@Test
	public final void testGetLabTestAttributeType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttributeTypeByUuid(java.lang.String)}.
	 */
	@Test
	public final void testGetLabTestAttributeTypeByUuid() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttributeTypes(java.lang.String, java.lang.String, boolean)}.
	 */
	@Test
	public final void testGetLabTestAttributeTypes() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestByUuid(java.lang.String)}.
	 */
	@Test
	public final void testGetLabTestByUuid() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTests(org.openmrs.module.commonlabtest.LabTestType, org.openmrs.Patient, java.lang.String, java.lang.String, org.openmrs.Concept, org.openmrs.Provider, java.util.Date, java.util.Date, boolean)}.
	 */
	@Test
	public final void testGetLabTests() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestSample(java.lang.Integer)}.
	 */
	@Test
	public final void testGetLabTestSample() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestSampleByUuid(java.lang.String)}.
	 */
	@Test
	public final void testGetLabTestSampleByUuid() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestSamples(org.openmrs.module.commonlabtest.LabTest, boolean)}.
	 */
	@Test
	public final void testGetLabTestSamplesLabTestBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestSamples(org.openmrs.Patient, boolean)}.
	 */
	@Test
	public final void testGetLabTestSamplesPatientBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestSamples(org.openmrs.Provider, boolean)}.
	 */
	@Test
	public final void testGetLabTestSamplesProviderBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestType(java.lang.Integer)}.
	 */
	@Test
	public final void testGetLabTestType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestTypeByUuid(java.lang.String)}.
	 */
	@Test
	public final void testGetLabTestTypeByUuid() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getNLabTests(org.openmrs.Patient, int, boolean, boolean, boolean)}.
	 */
	@Test
	public final void testGetNLabTests() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getNLabTestSamples(org.openmrs.Patient, org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus, int, boolean, boolean, boolean)}.
	 */
	@Test
	public final void testGetNLabTestSamples() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#purgeLabTest(org.openmrs.module.commonlabtest.LabTest)}.
	 */
	@Test
	public final void testPurgeLabTest() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#purgeLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)}.
	 */
	@Test
	public final void testPurgeLabTestAttribute() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#purgeLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)}.
	 */
	@Test
	public final void testPurgeLabTestAttributeType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#purgeLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)}.
	 */
	@Test
	public final void testPurgeLabTestSample() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#purgeLabTestType(org.openmrs.module.commonlabtest.LabTestType)}.
	 */
	@Test
	public final void testPurgeLabTestType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#saveLabTest(org.openmrs.module.commonlabtest.LabTest)}.
	 */
	@Test
	public final void testSaveLabTest() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#saveLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)}.
	 */
	@Test
	public final void testSaveLabTestAttribute() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#saveLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)}.
	 */
	@Test
	public final void testSaveLabTestAttributeType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#saveLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)}.
	 */
	@Test
	public final void testSaveLabTestSample() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#saveLabTestType(org.openmrs.module.commonlabtest.LabTestType)}.
	 */
	@Test
	public final void testSaveLabTestType() {
		// TODO
	}
}
