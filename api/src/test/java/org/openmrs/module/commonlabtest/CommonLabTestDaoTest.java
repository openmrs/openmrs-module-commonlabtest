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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.Order.Action;
import org.openmrs.Order.Urgency;
import org.openmrs.TestOrder;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType.LabTestGroup;
import org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * It is an integration test (extends BaseModuleContextSensitiveTest), which verifies DAO methods
 * against the in-memory H2 database. The database is initially loaded with data from
 * standardTestDataset.xml in openmrs-api. All test methods are executed in transactions, which are
 * rolled back by the end of each test method.
 */
public class CommonLabTestDaoTest extends CommonLabTestBase {
	
	@Autowired
	CommonLabTestDaoImpl dao;
	
	@Before
	public void runBeforeEachTest() throws Exception {
		super.initTestData();
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getAllLabTestAttributeTypes(boolean)}
	 * .
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
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getAllLabTestTypes(boolean)}
	 * .
	 */
	@Test
	public final void testGetAllLabTestTypes() {
		Context.clearSession();
		List<LabTestType> list = dao.getAllLabTestTypes(false);
		assertThat(list, Matchers.hasItems(geneXpert, chestXRay));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestTypes(java.lang.String, java.lang.String, org.openmrs.module.commonlabtest.LabTestType.LabTestGroup, org.openmrs.Concept, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestTypesByName() {
		Context.clearSession();
		List<LabTestType> list = dao.getLabTestTypes("GeneXpert Test", null, LabTestGroup.BACTERIOLOGY, null, false);
		assertThat(list, Matchers.hasItem(geneXpert));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTest(org.openmrs.Encounter)}
	 * .
	 */
	@Test
	public final void testGetLabTestByOrder() {
		Context.clearSession();
		Order order = Context.getOrderService().getOrder(100);
		LabTest labTest = dao.getLabTest(order);
		assertEquals(labTest, harryGxp);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTest(java.lang.Integer)}
	 * .
	 */
	@Test
	public final void testGetLabTestById() {
		Context.clearSession();
		LabTest labTest = dao.getLabTest(100);
		assertEquals(labTest, harryGxp);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttribute(java.lang.Integer)}
	 * .
	 */
	@Test
	public final void testGetLabTestAttribute() {
		Context.clearSession();
		LabTestAttribute labTestAttribute = dao.getLabTestAttribute(1);
		assertEquals(labTestAttribute, harryCartridgeId);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttributeByUuid(java.lang.String)}
	 * .
	 */
	@Test
	public final void testGetLabTestAttributeByUuid() {
		Context.clearSession();
		LabTestAttribute labTestAttribute = dao.getLabTestAttributeByUuid("2c9737d9-47c2-11e8-943c-40b034c3cfee");
		assertEquals(labTestAttribute, harryCartridgeId);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttributes(org.openmrs.module.commonlabtest.LabTestAttributeType, org.openmrs.module.commonlabtest.LabTest, org.openmrs.Patient, java.lang.String, java.util.Date, java.util.Date, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestAttributesByType() {
		Context.clearSession();
		List<LabTestAttribute> list = dao.getLabTestAttributes(mtbResult, null, null, null, true);
		assertThat(list, Matchers.hasItem(harryMtbResult));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttributes(org.openmrs.module.commonlabtest.LabTestAttributeType, org.openmrs.module.commonlabtest.LabTest, org.openmrs.Patient, java.lang.String, java.util.Date, java.util.Date, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestAttributesByTestOrderId() {
		Context.clearSession();
		List<LabTestAttribute> list = dao.getLabTestAttributes(harryGxp.getTestOrderId());
		assertThat(list, Matchers.hasItems(harryCartridgeId, harryMtbResult, harryRifResult));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttributes(org.openmrs.module.commonlabtest.LabTestAttributeType, org.openmrs.module.commonlabtest.LabTest, org.openmrs.Patient, java.lang.String, java.util.Date, java.util.Date, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestAttributesByLabTest() {
		Context.clearSession();
		List<LabTestAttribute> list = dao.getLabTestAttributes(harryGxp.getTestOrderId());
		assertThat(list, Matchers.hasItem(harryCartridgeId));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttributes(org.openmrs.module.commonlabtest.LabTestAttributeType, org.openmrs.module.commonlabtest.LabTest, org.openmrs.Patient, java.lang.String, java.util.Date, java.util.Date, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestAttributesByPatient() {
		Context.clearSession();
		List<LabTestAttribute> list = dao.getLabTestAttributes(harry, null, false);
		assertThat(list,
		    Matchers.hasItems(harryCartridgeId, harryMtbResult, harryRifResult, harryCxrResult, harryRadiologistRemarks));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttributeType(java.lang.Integer)}
	 * .
	 */
	@Test
	public final void testGetLabTestAttributeType() {
		
		List<LabTestAttributeType> list = dao.getLabTestAttributeTypes(geneXpert, false);
		assertThat(list, Matchers.containsInAnyOrder(cartridgeId, mtbResult, rifResult));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttributeTypeByUuid(java.lang.String)}
	 * .
	 */
	@Test
	public final void testGetLabTestAttributeTypeByUuid() {
		
		LabTestAttributeType retrivedObject = dao.getLabTestAttributeTypeByUuid(cartridgeId.getUuid());
		assertEquals("should be equal !", retrivedObject, cartridgeId);
		
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestAttributeTypes(java.lang.String, java.lang.String, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestAttributeTypes() {
		
		List<LabTestAttributeType> classNameList = dao.getLabTestAttributeTypes(null,
		    "org.openmrs.customdatatype.datatype.FreeTextDatatype", false);
		assertThat(classNameList, Matchers.hasSize(2));
		
		List<LabTestAttributeType> classNameListRetired = dao.getLabTestAttributeTypes(null,
		    "org.openmrs.customdatatype.datatype.FloatDatatype", true);
		assertThat(classNameListRetired, Matchers.hasSize(1));
		
		List<LabTestAttributeType> nameListRetired = dao.getLabTestAttributeTypes("CAD4TB Score", null, true);
		assertThat(nameListRetired, Matchers.hasSize(1));
		
		List<LabTestAttributeType> nameList = dao.getLabTestAttributeTypes("Radiologist Remarks", null, false);
		assertThat(nameList, Matchers.hasSize(1));
		
		List<LabTestAttributeType> nameWithClassNameListRetired = dao.getLabTestAttributeTypes("CAD4TB Score",
		    "org.openmrs.customdatatype.datatype.FloatDatatype", true);
		assertThat(nameWithClassNameListRetired, Matchers.hasSize(1));
		
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestByUuid(java.lang.String)}
	 * .
	 */
	@Test
	public final void testGetLabTestByUuid() {
		LabTest retrivedObject = dao.getLabTestByUuid(harryGxp.getUuid());
		assertEquals("should be equal !", retrivedObject, harryGxp);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTests(org.openmrs.module.commonlabtest.LabTestType, org.openmrs.Patient, java.lang.String, java.lang.String, org.openmrs.Concept, org.openmrs.Provider, java.util.Date, java.util.Date, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTests() {
		List<LabTest> allLabTests = dao.getLabTests(null, harry, null, null, null, null, null, null, false);
		assertThat(allLabTests, Matchers.containsInAnyOrder(harryGxp, harryCxr));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestSample(java.lang.Integer)}
	 * .
	 */
	@Test
	public final void testGetLabTestSample() {
		LabTestSample retrivedObject = dao.getLabTestSample(harrySample.getId());
		assertEquals("should be equal !", retrivedObject, harrySample);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestSampleByUuid(java.lang.String)}
	 * .
	 */
	@Test
	public final void testGetLabTestSampleByUuid() {
		LabTestSample retrivedObject = dao.getLabTestSampleByUuid(harrySample.getUuid());
		assertEquals("should be equal !", retrivedObject, harrySample);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestSamples(org.openmrs.module.commonlabtest.LabTest, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestSamplesByLabTest() {
		List<LabTestSample> harryList = dao.getLabTestSamples(harryGxp, false);
		assertThat(harryList, Matchers.hasSize(1));
		
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestSamples(org.openmrs.Patient, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestSamplesByPatient() {
		
		List<LabTestSample> list = dao.getLabTestSamples(harry, false);
		assertThat(list, Matchers.hasSize(1));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestSamples(org.openmrs.Provider, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestSamplesByProvider() {
		List<LabTestSample> list = dao.getLabTestSamples(owais, false);
		assertThat(list, Matchers.hasItem(harrySample));
		
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestType(java.lang.Integer)}
	 * .
	 */
	@Test
	public final void testGetLabTestType() {
		LabTestType retrivedObject = dao.getLabTestType(geneXpert.getId());
		assertEquals(geneXpert, retrivedObject);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getLabTestTypeByUuid(java.lang.String)}
	 * .
	 */
	@Test
	public final void testGetLabTestTypeByUuid() {
		LabTestType retrivedObject = dao.getLabTestTypeByUuid(geneXpert.getUuid());
		assertEquals(geneXpert, retrivedObject);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getNLabTests(org.openmrs.Patient, int, boolean, boolean, boolean)}
	 * .
	 */
	@Test
	public final void testGetNLabTests() {
		List<LabTest> list = dao.getNLabTests(harry, 2, true, false, false);
		assertThat(list, Matchers.containsInAnyOrder(harryGxp, harryCxr));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#getNLabTestSamples(org.openmrs.Patient, org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus, int, boolean, boolean, boolean)}
	 * .
	 */
	@Test
	public final void testGetNLabTestSamples() {
		List<LabTestSample> samples = dao.getNLabTestSamples(harry, null, 2, true, false, false);
		assertThat(samples, Matchers.hasItem(harrySample));
		
		List<LabTestSample> processedSamples = dao.getNLabTestSamples(harry, LabTestSampleStatus.PROCESSED, 2, true, false,
		    false);
		assertThat(processedSamples, Matchers.hasItem(harrySample));
		assertThat(processedSamples, Matchers.hasSize(1));
		assertEquals(processedSamples.get(0).getStatus(), LabTestSampleStatus.PROCESSED);
		
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#purgeLabTest(org.openmrs.module.commonlabtest.LabTest)}
	 * .
	 */
	@Test
	public final void testPurgeLabTest() {
		
		/*
		 * LabTest test = dao.getLabTest(200); dao.purgeLabTest(test);
		 * Context.clearSession(); LabTest labTest = dao.getLabTest(200);
		 * assertNull(labTest);
		 */
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#purgeLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)}
	 * .
	 */
	@Test
	public final void testPurgeLabTestAttribute() {
		
		LabTestAttribute labTestAttribute = dao.getLabTestAttribute(2);
		dao.purgeLabTestAttribute(labTestAttribute);
		// clear cache
		Context.flushSession();
		Context.clearSession();
		LabTestAttribute exists = dao.getLabTestAttributeByUuid(labTestAttribute.getUuid());
		assertNull(exists);
		
	}
	
	/**
	 * Test method for sequence purg..
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#purgeLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)}
	 * .
	 */
	@Test
	public final void testPurgeLabTestAttributeType() {
		
		/*LabTestAttributeType labTestAttributeType = dao.getLabTestAttributeType(2);
		dao.purgeLabTestAttributeType(labTestAttributeType);
		// clear cache
		Context.flushSession();
		Context.clearSession();
		LabTestAttributeType exists = dao.getLabTestAttributeTypeByUuid(labTestAttributeType.getUuid());
		assertNull(exists);*/
		
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#purgeLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)}
	 * .
	 */
	@Test
	public final void testPurgeLabTestSample() {
		
		LabTestSample sample = dao.getLabTestSample(2);
		// Purge object
		dao.purgeLabTestSample(sample);
		// Clear cache
		Context.flushSession();
		Context.clearSession();
		// Read again
		LabTestSample exists = dao.getLabTestSampleByUuid(sample.getUuid());
		assertNull(exists);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#purgeLabTestType(org.openmrs.module.commonlabtest.LabTestType)}
	 * .
	 */
	@Test
	public final void testPurgeLabTestType() {
		LabTestType testType = dao.getLabTestType(4);
		// Purge object
		dao.purgeLabTestType(testType);
		// Clear cache
		Context.flushSession();
		Context.clearSession();
		// Read again
		LabTestType exists = dao.getLabTestTypeByUuid(testType.getUuid());
		assertNull(exists);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#saveLabTest(org.openmrs.module.commonlabtest.LabTest)}
	 * .
	 */
	@Test
	public final void testSaveLabTest() {
		
		LabTest labTest = new LabTest();
		Order order = Context.getOrderService().getOrder(100);
		labTest.setOrder(order);
		LabTestType labTestType = dao.getLabTestType(2);
		labTest.setLabTestType(labTestType);
		LabTest resultLabTest = dao.saveLabTest(labTest);
		labTest.setLabReferenceNumber("dummy reference");
		
		assertThat(resultLabTest, Matchers.hasProperty("labReferenceNumber", Matchers.anything("dummy reference")));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#saveLabTestOrder(org.openmrs.Order)}
	 * .
	 */
	@Test
	public final void testSaveLabTestOrder_Create() {
		// Create CXR order for Hermione
		Order testOrder = new TestOrder();
		testOrder.setOrderId(Integer.MAX_VALUE);
		testOrder.setOrderType(Context.getOrderService().getOrderType(3));
		testOrder.setConcept(Context.getConceptService().getConcept(600));
		testOrder.setOrderer(Context.getProviderService().getProvider(300));
		Encounter encounter = Context.getEncounterService().getEncounter(1000);
		encounter.setPatient(hermione);
		testOrder.setEncounter(encounter);
		testOrder.setInstructions("PERFORM CXR");
		testOrder.setDateActivated(new Date());
		testOrder.setAction(Action.NEW);
		testOrder.setOrderReasonNonCoded("Testing");
		testOrder.setPatient(hermione);
		testOrder.setUrgency(Urgency.ROUTINE);
		testOrder.setCareSetting(Context.getOrderService().getCareSetting(1));
		Order savedOrder = dao.saveLabTestOrder(testOrder);
		assertThat(savedOrder, Matchers.hasProperty("orderId", org.hamcrest.Matchers.notNullValue()));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#saveLabTestOrder(org.openmrs.Order)}
	 * .
	 */
	@Test
	public final void testSaveLabTestOrder_CreateWhenNotFound() {
		// Create CXR order for Hermione
		Order testOrder = new TestOrder();
		testOrder.setOrderId(Integer.MAX_VALUE);
		testOrder.setOrderType(Context.getOrderService().getOrderType(3));
		testOrder.setConcept(Context.getConceptService().getConcept(600));
		testOrder.setOrderer(Context.getProviderService().getProvider(300));
		Encounter encounter = Context.getEncounterService().getEncounter(1000);
		encounter.setPatient(hermione);
		testOrder.setEncounter(encounter);
		testOrder.setInstructions("PERFORM CXR");
		testOrder.setDateActivated(new Date());
		testOrder.setAction(Action.NEW);
		testOrder.setOrderReasonNonCoded("Testing");
		testOrder.setPatient(hermione);
		testOrder.setUrgency(Urgency.ROUTINE);
		testOrder.setCareSetting(Context.getOrderService().getCareSetting(1));
		Order savedOrder = dao.saveLabTestOrder(testOrder);
		assertThat(savedOrder, Matchers.hasProperty("orderId", org.hamcrest.Matchers.notNullValue()));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#saveLabTestOrder(org.openmrs.Order)}
	 * .
	 */
	@Test
	public final void testSaveLabTestOrder_Update() {
		Order order = Context.getOrderService().getOrder(100);
		order.setUrgency(Urgency.STAT);
		Order savedOrder = dao.saveLabTestOrder(order);
		assertThat(savedOrder, Matchers.hasProperty("orderId", org.hamcrest.Matchers.is(100)));
		assertThat(savedOrder, Matchers.hasProperty("urgency", org.hamcrest.Matchers.is(Urgency.STAT)));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#saveLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)}
	 * .
	 */
	@Test
	public final void testSaveLabTestAttribute() {
		
		LabTestAttribute labTestAttribute = new LabTestAttribute();
		LabTest labTest = dao.getLabTest(Context.getOrderService().getOrder(100));
		labTestAttribute.setLabTest(labTest);
		LabTestAttributeType attributeType = new LabTestAttributeType();
		attributeType.setLabTestType(geneXpert);
		attributeType.setDatatypeClassname("org.openmrs.customdatatype.datatype.FloatDatatype");
		attributeType.setDatatypeConfig("");
		attributeType.setDescription("discriptions");
		attributeType.setMaxOccurs(2);
		attributeType.setMinOccurs(1);
		attributeType.setSortWeight(1.1);
		attributeType.setUuid("b98b5208-5bbf-11e8-b60d-08dd27ea421d");
		attributeType.setName("Name");
		attributeType.setPreferredHandlerClassname("asd");
		attributeType = dao.saveLabTestAttributeType(attributeType);
		labTestAttribute.setLabTestAttributeId(attributeType.getId());
		labTestAttribute.setValueReferenceInternal("2018-10-03");
		LabTestAttribute resultLabTestAttribute = dao.saveLabTestAttribute(labTestAttribute);
		
		assertThat(resultLabTestAttribute,
		    Matchers.hasProperty("uuid", org.hamcrest.Matchers.is(resultLabTestAttribute.getUuid())));
		
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#saveLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)}
	 * .
	 */
	@Test
	public final void testSaveLabTestAttributeType() {
		
		LabTestAttributeType attributeType = new LabTestAttributeType();
		attributeType.setLabTestType(geneXpert);
		attributeType.setDatatypeClassname("org.openmrs.customdatatype.datatype.FloatDatatype");
		attributeType.setDatatypeConfig("");
		attributeType.setDescription("discriptions");
		attributeType.setMaxOccurs(2);
		attributeType.setMinOccurs(1);
		attributeType.setSortWeight(1.1);
		attributeType.setUuid("b98b5208-5bbf-11e8-b60d-08dd27ea421d");
		attributeType.setName("Name");
		attributeType.setPreferredHandlerClassname("asd");
		
		attributeType = dao.saveLabTestAttributeType(attributeType);
		
		// Clear cache
		// Context.flushSession();
		Context.clearSession();
		// Read again
		
		LabTestAttributeType savedAttributeType = dao.getLabTestAttributeTypeByUuid(attributeType.getUuid());
		assertThat(savedAttributeType, Matchers.hasProperty("uuid", org.hamcrest.Matchers.is(attributeType.getUuid())));
		assertThat(savedAttributeType,
		    Matchers.hasProperty("creator", org.hamcrest.Matchers.is(attributeType.getCreator())));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#saveLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)}
	 * .
	 */
	@Test
	public final void testSaveLabTestSample() {
		LabTestSample testSample = new LabTestSample();
		testSample.setLabTest(hermioneGxp);
		testSample.setSampleIdentifier("TEST-ID-1234");
		testSample.setCollectionDate(new Date());
		testSample.setCollector(owais);
		testSample.setLabTest(hermioneGxp);
		
		testSample.setSpecimenType(geneXpert.getReferenceConcept());
		testSample.setSpecimenSite(geneXpert.getReferenceConcept());
		testSample.setSampleIdentifier("123");
		testSample = dao.saveLabTestSample(testSample);
		// Clear cache
		// Context.flushSession();
		Context.clearSession();
		// Read again
		LabTestSample savedLabTestSample = dao.getLabTestSample(testSample.getId());
		
		assertThat(savedLabTestSample, Matchers.hasProperty("uuid", org.hamcrest.Matchers.is(testSample.getUuid())));
		assertThat(savedLabTestSample, Matchers.hasProperty("creator", org.hamcrest.Matchers.is(testSample.getCreator())));
		
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl#saveLabTestType(org.openmrs.module.commonlabtest.LabTestType)}
	 * .
	 */
	@Test
	public final void testSaveLabTestType() {
		LabTestType testType = new LabTestType();
		testType.setName("AFB Smear Microscopy");
		testType.setShortName("AFB");
		testType.setTestGroup(LabTestGroup.BACTERIOLOGY);
		testType.setRequiresSpecimen(Boolean.TRUE);
		Concept concept = Context.getConceptService().getConcept(500);
		testType.setReferenceConcept(concept);
		// Save object
		testType = dao.saveLabTestType(testType);
		// Clear cache
		// Context.flushSession();
		Context.clearSession();
		// Read again
		LabTestType savedTestType = dao.getLabTestType(testType.getId());
		assertThat(savedTestType, Matchers.hasProperty("uuid", org.hamcrest.Matchers.is(testType.getUuid())));
		assertThat(savedTestType, Matchers.hasProperty("creator", org.hamcrest.Matchers.is(testType.getCreator())));
	}
}
