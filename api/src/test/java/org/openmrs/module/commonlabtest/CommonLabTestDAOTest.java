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
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * It is an integration test (extends BaseModuleContextSensitiveTest), which verifies DAO methods
 * against the in-memory H2 database. The database is initially loaded with data from
 * standardTestDataset.xml in openmrs-api. All test methods are executed in transactions, which are
 * rolled back by the end of each test method.
 */
public class CommonLabTestDAOTest extends CommonLabTestBase {

	@Autowired
	CommonLabTestDAOImpl dao;

	@Before
	public void runBeforeEachTest() throws Exception {
		super.initTestData();
	}

	@Test
	public final void testGetAllLabTestAttributeTypes() {
		Context.clearSession();
		List<LabTestAttributeType> list = dao.getAllLabTestAttributeTypes(false);
		assertTrue(list.size() == activeLabTestAttributeTypes.size());
		assertThat(list, Matchers.not(Matchers.hasItems(cad4tbScore, xrayFilmPrinted)));
	}

	@Test
	public final void testGetAllLabTestTypes() {
		Context.clearSession();
		List<LabTestType> list = dao.getAllLabTestTypes(false);
		assertThat(list, Matchers.hasItems(geneXpert, chestXRay));
	}

	@Test
	public final void testGetLabTestTypesByName() {
		Context.clearSession();
		List<LabTestType> list = dao.getLabTestTypes("GeneXpert Test", null, LabTestGroup.BACTERIOLOGY, null, false);
		assertThat(list, Matchers.hasItem(geneXpert));
	}

	@Test
	public final void testGetLabTestByOrder() {
		Context.clearSession();
		Order order = Context.getOrderService().getOrder(100);
		LabTest labTest = dao.getLabTest(order);
		assertEquals(labTest, harryGxp);
	}

	@Test
	public final void testGetLabTestById() {
		Context.clearSession();
		LabTest labTest = dao.getLabTest(100);
		assertEquals(labTest, harryGxp);
	}

	@Test
	public final void testGetLabTestAttribute() {
		Context.clearSession();
		LabTestAttribute labTestAttribute = dao.getLabTestAttribute(1);
		assertEquals(labTestAttribute, harryCartridgeId);
	}

	@Test
	public final void testGetLabTestAttributeByUuid() {
		Context.clearSession();
		LabTestAttribute labTestAttribute = dao.getLabTestAttributeByUuid("2c9737d9-47c2-11e8-943c-40b034c3cfee");
		assertEquals(labTestAttribute, harryCartridgeId);
	}

	@Test
	public final void testGetLabTestAttributesByType() {
		Context.clearSession();
		List<LabTestAttribute> list = dao.getLabTestAttributes(mtbResult, null, null, null, true);
		assertThat(list, Matchers.hasItem(harryMtbResult));
	}

	@Test
	public final void testGetLabTestAttributesByTestOrderId() {
		Context.clearSession();
		List<LabTestAttribute> list = dao.getLabTestAttributes(harryGxp.getTestOrderId());
		assertThat(list, Matchers.hasItems(harryCartridgeId, harryMtbResult, harryRifResult));
	}

	@Test
	public final void testGetLabTestAttributesByLabTest() {
		Context.clearSession();
		List<LabTestAttribute> list = dao.getLabTestAttributes(harryGxp.getTestOrderId());
		assertThat(list, Matchers.hasItem(harryCartridgeId));
	}

	@Test
	public final void testGetLabTestAttributesByPatient() {
		Context.clearSession();
		List<LabTestAttribute> list = dao.getLabTestAttributes(harry, null, false);
		assertThat(list,
		    Matchers.hasItems(harryCartridgeId, harryMtbResult, harryRifResult, harryCxrResult, harryRadiologistRemarks));
	}

	@Test
	public final void testGetLabTestAttributeType() {
		List<LabTestAttributeType> list = dao.getLabTestAttributeTypes(geneXpert, false);
		assertThat(list, Matchers.containsInAnyOrder(cartridgeId, mtbResult, rifResult));
	}

	@Test
	public final void testGetLabTestAttributeTypeByUuid() {
		LabTestAttributeType retrivedObject = dao.getLabTestAttributeTypeByUuid(cartridgeId.getUuid());
		assertEquals("should be equal !", retrivedObject, cartridgeId);

	}

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

	@Test
	public final void testGetLabTestByUuid() {
		LabTest retrivedObject = dao.getLabTestByUuid(harryGxp.getUuid());
		assertEquals("should be equal !", retrivedObject, harryGxp);
	}

	@Test
	public final void testGetLabTests() {
		List<LabTest> allLabTests = dao.getLabTests(null, harry, null, null, null, null, null, null, false);
		assertThat(allLabTests, Matchers.containsInAnyOrder(harryGxp, harryCxr));
	}

	@Test
	public final void testGetLabTestSample() {
		LabTestSample retrivedObject = dao.getLabTestSample(harrySample.getId());
		assertEquals("should be equal !", retrivedObject, harrySample);
	}

	@Test
	public final void testGetLabTestSampleByUuid() {
		LabTestSample retrivedObject = dao.getLabTestSampleByUuid(harrySample.getUuid());
		assertEquals("should be equal !", retrivedObject, harrySample);
	}

	@Test
	public final void testGetLabTestSamplesByLabTest() {
		List<LabTestSample> harryList = dao.getLabTestSamples(harryGxp, false);
		assertThat(harryList, Matchers.hasSize(1));

	}

	@Test
	public final void testGetLabTestSamplesByPatient() {
		List<LabTestSample> list = dao.getLabTestSamples(harry, false);
		assertThat(list, Matchers.hasSize(1));
	}

	@Test
	public final void testGetLabTestSamplesByProvider() {
		List<LabTestSample> list = dao.getLabTestSamples(owais, false);
		assertThat(list, Matchers.hasItem(harrySample));
	}

	@Test
	public final void testGetLabTestType() {
		LabTestType retrivedObject = dao.getLabTestType(geneXpert.getId());
		assertEquals(geneXpert, retrivedObject);
	}

	@Test
	public final void testGetLabTestTypeByUuid() {
		LabTestType retrivedObject = dao.getLabTestTypeByUuid(geneXpert.getUuid());
		assertEquals(geneXpert, retrivedObject);
	}

	@Test
	public final void testGetNLabTests() {
		List<LabTest> list = dao.getNLabTests(harry, 2, true, false, false);
		assertThat(list, Matchers.containsInAnyOrder(harryGxp, harryCxr));
	}

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

	@Test
	public final void testPurgeLabTestSample() {
		LabTestSample sample = dao.getLabTestSample(2);
		dao.purgeLabTestSample(sample);
		Context.flushSession();
		Context.clearSession();
		LabTestSample exists = dao.getLabTestSampleByUuid(sample.getUuid());
		assertNull(exists);
	}

	@Test
	public final void testPurgeLabTestType() {
		LabTestType testType = dao.getLabTestType(4);
		dao.purgeLabTestType(testType);
		Context.flushSession();
		Context.clearSession();
		LabTestType exists = dao.getLabTestTypeByUuid(testType.getUuid());
		assertNull(exists);
	}

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

	@Test
	public final void testSaveLabTestOrder_Update() {
		Order order = Context.getOrderService().getOrder(100);
		Order savedOrder = dao.saveLabTestOrder(order);
		assertThat(savedOrder, Matchers.hasProperty("orderId", org.hamcrest.Matchers.is(100)));
	}

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
		Context.clearSession();
		LabTestAttributeType savedAttributeType = dao.getLabTestAttributeTypeByUuid(attributeType.getUuid());
		assertThat(savedAttributeType, Matchers.hasProperty("uuid", org.hamcrest.Matchers.is(attributeType.getUuid())));
		assertThat(savedAttributeType,
		    Matchers.hasProperty("creator", org.hamcrest.Matchers.is(attributeType.getCreator())));
	}

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
		Context.clearSession();
		LabTestSample savedLabTestSample = dao.getLabTestSample(testSample.getId());
		assertThat(savedLabTestSample, Matchers.hasProperty("uuid", org.hamcrest.Matchers.is(testSample.getUuid())));
		assertThat(savedLabTestSample, Matchers.hasProperty("creator", org.hamcrest.Matchers.is(testSample.getCreator())));
	}

	@Test
	public final void testSaveLabTestType() {
		LabTestType testType = new LabTestType();
		testType.setName("AFB Smear Microscopy");
		testType.setShortName("AFB");
		testType.setTestGroup(LabTestGroup.BACTERIOLOGY);
		testType.setRequiresSpecimen(Boolean.TRUE);
		Concept concept = Context.getConceptService().getConcept(500);
		testType.setReferenceConcept(concept);
		testType = dao.saveLabTestType(testType);
		Context.clearSession();
		LabTestType savedTestType = dao.getLabTestType(testType.getId());
		assertThat(savedTestType, Matchers.hasProperty("uuid", org.hamcrest.Matchers.is(testType.getUuid())));
		assertThat(savedTestType, Matchers.hasProperty("creator", org.hamcrest.Matchers.is(testType.getCreator())));
	}
}
