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
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.Patient;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.api.dao.CommonLabTestDao;
import org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl;

/**
 * This is a unit test, which verifies logic in CommonLabTestService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class CommonLabTestServiceTest extends CommonLabTestModuleTestingData {
	
	@InjectMocks
	CommonLabTestServiceImpl service;
	
	@Mock
	CommonLabTestDao dao;
	
	@Override
	@Before
	public void initTestData() {
		super.initTestData();
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getAllLabTestAttributeTypes(boolean)}.
	 */
	@Test
	public final void testGetAllLabTestAttributeTypes() {
		when(dao.getAllLabTestAttributeTypes(Boolean.TRUE)).thenReturn(labTestAttributeTypes);
		List<LabTestAttributeType> list = service.getAllLabTestAttributeTypes(Boolean.TRUE);
		assertThat(list, Matchers.hasItems(labTestAttributeTypes.toArray(new LabTestAttributeType[] {})));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getAllLabTestAttributeTypes(boolean)}.
	 */
	@Test
	public final void testGetAllActiveLabTestAttributeTypes() {
		labTestAttributeTypes.get(0).setRetired(Boolean.TRUE);
		List<LabTestAttributeType> activeList = labTestAttributeTypes.subList(1, labTestAttributeTypes.size() - 1);
		when(dao.getAllLabTestAttributeTypes(Boolean.FALSE)).thenReturn(activeList);
		List<LabTestAttributeType> list = service.getAllLabTestAttributeTypes(Boolean.FALSE);
		assertThat(list, Matchers.hasItems(activeList.toArray(new LabTestAttributeType[] {})));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getAllLabTestTypes(boolean)}.
	 */
	@Test
	public final void testGetAllLabTestTypes() {
		when(dao.getAllLabTestTypes(Boolean.TRUE)).thenReturn(labTestTypes);
		List<LabTestType> list = service.getAllLabTestTypes(Boolean.TRUE);
		assertThat(list, Matchers.hasItems(labTestTypes.toArray(new LabTestType[] {})));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getAllLabTestTypes(boolean)}.
	 */
	@Test
	public final void testGetAllActiveLabTestTypes() {
		labTestTypes.get(0).setRetired(Boolean.TRUE);
		List<LabTestType> activeList = labTestTypes.subList(1, labTestTypes.size() - 1);
		when(dao.getAllLabTestTypes(Boolean.FALSE)).thenReturn(activeList);
		List<LabTestType> list = service.getAllLabTestTypes(Boolean.FALSE);
		assertThat(list, Matchers.hasItems(activeList.toArray(new LabTestType[] {})));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getEarliestLabTest(org.openmrs.Patient)}.
	 */
	@Test
	public final void testGetEarliestLabTest() {
		when(dao.getNLabTests(harry, 1, true, false, true)).thenReturn(Arrays.asList(harryGxp));
		LabTest labTest = service.getEarliestLabTest(harry);
		assertThat(labTest, Matchers.is(harryGxp));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getEarliestLabTestSample(org.openmrs.Patient, org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus)}.
	 */
	@Test
	public final void testGetEarliestLabTestSample() {
		when(dao.getNLabTestSamples(any(Patient.class), any(LabTestSampleStatus.class), 1, true, false, true))
		        .thenReturn(Arrays.asList(harrySample));
		LabTestSample labTestSample = service.getEarliestLabTestSample(harry, null);
		assertThat(labTestSample, Matchers.is(harrySample));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttribute(java.lang.Integer)}.
	 */
	@Test
	public final void testGetLabTestAttribute() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttributes(org.openmrs.module.commonlabtest.LabTestAttributeType, org.openmrs.Patient, java.lang.String, java.util.Date, java.util.Date, boolean)}.
	 */
	@Test
	public final void testGetLabTestAttributesLabTestAttributeTypePatientStringDateDateBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttributes(org.openmrs.module.commonlabtest.LabTestAttributeType, boolean)}.
	 */
	@Test
	public final void testGetLabTestAttributesLabTestAttributeTypeBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttributes(org.openmrs.Patient, boolean)}.
	 */
	@Test
	public final void testGetLabTestAttributesPatientBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttributes(org.openmrs.Patient, org.openmrs.module.commonlabtest.LabTestAttributeType, boolean)}.
	 */
	@Test
	public final void testGetLabTestAttributesPatientLabTestAttributeTypeBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttributeType(java.lang.Integer)}.
	 */
	@Test
	public final void testGetLabTestAttributeType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttributeTypeByUuid(java.lang.String)}.
	 */
	@Test
	public final void testGetLabTestAttributeTypeByUuid() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttributeTypes(java.lang.String, java.lang.String, boolean)}.
	 */
	@Test
	public final void testGetLabTestAttributeTypes() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestByUuid(java.lang.String)}.
	 */
	@Test
	public final void testGetLabTestByUuid() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTest(org.openmrs.Encounter)}.
	 */
	@Test
	public final void testGetLabTestEncounter() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTest(org.openmrs.Order)}.
	 */
	@Test
	public final void testGetLabTestOrder() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSample(java.lang.Integer)}.
	 */
	@Test
	public final void testGetLabTestSample() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSampleByUuid(java.lang.String)}.
	 */
	@Test
	public final void testGetLabTestSampleByUuid() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSamples(org.openmrs.module.commonlabtest.LabTest, org.openmrs.Patient, org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.openmrs.Provider, java.util.Date, java.util.Date, boolean)}.
	 */
	@Test
	public final void testGetLabTestSamplesLabTestPatientLabTestSampleStatusStringStringStringStringProviderDateDateBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSamples(java.lang.String, java.lang.String, java.lang.String, boolean)}.
	 */
	@Test
	public final void testGetLabTestSamplesStringStringStringBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSamples(org.openmrs.module.commonlabtest.LabTest, boolean)}.
	 */
	@Test
	public final void testGetLabTestSamplesLabTestBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSamples(org.openmrs.Provider, boolean)}.
	 */
	@Test
	public final void testGetLabTestSamplesProviderBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSamples(org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus, java.util.Date, java.util.Date, boolean)}.
	 */
	@Test
	public final void testGetLabTestSamplesLabTestSampleStatusDateDateBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestType(java.lang.Integer)}.
	 */
	@Test
	public final void testGetLabTestType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestTypeByUuid(java.lang.String)}.
	 */
	@Test
	public final void testGetLabTestTypeByUuid() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestTypes(java.lang.String, java.lang.String, org.openmrs.module.commonlabtest.LabTestType.LabTestGroup, java.lang.Boolean, org.openmrs.Concept, boolean)}.
	 */
	@Test
	public final void testGetLabTestTypes() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.module.commonlabtest.LabTestType, org.openmrs.Patient, java.lang.String, java.lang.String, org.openmrs.Concept, org.openmrs.Provider, java.util.Date, java.util.Date, boolean)}.
	 */
	@Test
	public final void testGetLabTestsLabTestTypePatientStringStringConceptProviderDateDateBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.module.commonlabtest.LabTestType, boolean)}.
	 */
	@Test
	public final void testGetLabTestsLabTestTypeBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.Concept, boolean)}.
	 */
	@Test
	public final void testGetLabTestsConceptBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.Provider, boolean)}.
	 */
	@Test
	public final void testGetLabTestsProviderBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.Patient, boolean)}.
	 */
	@Test
	public final void testGetLabTestsPatientBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(java.lang.String, boolean)}.
	 */
	@Test
	public final void testGetLabTestsStringBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLatestLabTest(org.openmrs.Patient)}.
	 */
	@Test
	public final void testGetLatestLabTest() {
		when(dao.getNLabTests(harry, 1, false, true, true)).thenReturn(Arrays.asList(harryGxp));
		LabTest labTest = service.getLatestLabTest(harry);
		assertThat(labTest, Matchers.is(harryGxp));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLatestLabTestSample(org.openmrs.Patient, org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus)}.
	 */
	@Test
	public final void testGetLatestLabTestSample() {
		when(dao.getNLabTestSamples(any(Patient.class), any(LabTestSampleStatus.class), 1, false, true, true))
		        .thenReturn(Arrays.asList(harrySample));
		LabTestSample labTestSample = service.getLatestLabTestSample(harry, null);
		assertThat(labTestSample, Matchers.is(harrySample));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTest(org.openmrs.module.commonlabtest.LabTest)}.
	 */
	@Test
	public final void testSaveLabTestLabTest() {
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTest(org.openmrs.module.commonlabtest.LabTest, org.openmrs.module.commonlabtest.LabTestSample, java.util.Collection)}.
	 */
	@Test
	public final void testSaveLabTestLabTestLabTestSampleCollectionOfLabTestAttribute() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)}.
	 */
	@Test
	public final void testSaveLabTestAttribute() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTestAttributes(java.util.List)}.
	 */
	@Test
	public final void testSaveLabTestAttributes() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)}.
	 */
	@Test
	public final void testSaveLabTestAttributeType() {
		when(dao.saveLabTestAttributeType(any(LabTestAttributeType.class))).thenReturn(cartridgeId);
		LabTestAttributeType labTestAttributeType = new LabTestAttributeType(99);
		labTestAttributeType = service.saveLabTestAttributeType(labTestAttributeType);
		assertEquals(labTestAttributeType, cartridgeId);
		verify(dao, times(1)).saveLabTestAttributeType(any(LabTestAttributeType.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)}.
	 */
	@Test
	public final void testSaveLabTestSample() {
		when(dao.saveLabTestSample(any(LabTestSample.class))).thenReturn(harrySample);
		LabTestSample labTestSample = new LabTestSample(99);
		labTestSample = service.saveLabTestSample(labTestSample);
		assertEquals(labTestSample, harrySample);
		verify(dao, times(1)).saveLabTestSample(any(LabTestSample.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTestType(org.openmrs.module.commonlabtest.LabTestType)}.
	 */
	@Test
	public final void testSaveLabTestType() {
		when(dao.saveLabTestType(any(LabTestType.class))).thenReturn(geneXpert);
		LabTestType labTestType = new LabTestType(99);
		labTestType = service.saveLabTestType(labTestType);
		assertEquals(labTestType, geneXpert);
		verify(dao, times(1)).saveLabTestType(any(LabTestType.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#retireLabTestType(org.openmrs.module.commonlabtest.LabTestType, java.lang.String)}.
	 */
	@Test
	public final void testRetireLabTestType() {
		doNothing().when(dao).saveLabTestType(any(LabTestType.class));
		service.retireLabTestType(chestXRay, "Got too old");
		verify(dao, times(1)).saveLabTestType(any(LabTestType.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#retireLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType, java.lang.String)}.
	 */
	@Test
	public final void testRetireLabTestAttributeType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#unretireLabTestType(org.openmrs.module.commonlabtest.LabTestType)}.
	 */
	@Test
	public final void testUnretireLabTestType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#unretireLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)}.
	 */
	@Test
	public final void testUnretireLabTestAttributeType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#voidLabTest(org.openmrs.module.commonlabtest.LabTest, java.lang.String)}.
	 */
	@Test
	public final void testVoidLabTest() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#voidLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute, java.lang.String)}.
	 */
	@Test
	public final void testVoidLabTestAttribute() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#voidLabTestSample(org.openmrs.module.commonlabtest.LabTestSample, java.lang.String)}.
	 */
	@Test
	public final void testVoidLabTestSample() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#unvoidLabTest(org.openmrs.module.commonlabtest.LabTest)}.
	 */
	@Test
	public final void testUnvoidLabTest() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#unvoidLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)}.
	 */
	@Test
	public final void testUnvoidLabTestAttribute() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#unvoidLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)}.
	 */
	@Test
	public final void testUnvoidLabTestSample() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#deleteLabTest(org.openmrs.module.commonlabtest.LabTest)}.
	 */
	@Test
	public final void shouldNotDeleteLabTest() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#deleteLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)}.
	 */
	@Test
	public final void testDeleteLabTestAttribute() {
		doNothing().when(dao).purgeLabTestAttribute(any(LabTestAttribute.class));
		service.deleteLabTestAttribute(harryGxpResults.get(0));
		verify(dao, times(1)).purgeLabTestAttribute(any(LabTestAttribute.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#deleteLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)}.
	 */
	@Test
	public final void testDeleteLabTestAttributeType() {
		doNothing().when(dao).purgeLabTestAttributeType(any(LabTestAttributeType.class));
		service.deleteLabTestAttributeType(cartridgeId, true);
		verify(dao, times(1)).purgeLabTestAttributeType(any(LabTestAttributeType.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#deleteLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)}.
	 */
	@Test
	public final void testDeleteLabTestSample() {
		doNothing().when(dao).purgeLabTestSample(any(LabTestSample.class));
		service.deleteLabTestSample(hermioneSample);
		verify(dao, times(1)).purgeLabTestSample(any(LabTestSample.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#deleteLabTestType(org.openmrs.module.commonlabtest.LabTestType)}.
	 */
	@Test
	public final void shouldNotDeleteLabTestType() {
		doNothing().when(dao).purgeLabTestType(any(LabTestType.class));
		service.deleteLabTestType(chestXRay, true);
		verify(dao, times(1)).purgeLabTestType(any(LabTestType.class));
		verifyNoMoreInteractions(dao);
	}
	
}
