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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.Concept;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDaoImpl;
import org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl;

/**
 * This is a unit test, which verifies logic in CommonLabTestService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class CommonLabTestServiceTest extends CommonLabTestBaseTest {
	
	@InjectMocks
	CommonLabTestServiceImpl service;
	
	@Mock
	CommonLabTestDaoImpl dao;
	
	@Before
	public void initMockito() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Must set up the service
	 */
	@Test
	public void setupCommonLabService() {
		assertNotNull(Context.getService(CommonLabTestService.class));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getAllLabTestAttributeTypes(boolean)}
	 * .
	 */
	@Test
	public final void testGetAllLabTestAttributeTypes() {
		when(dao.getAllLabTestAttributeTypes(Boolean.TRUE)).thenReturn(labTestAttributeTypes);
		List<LabTestAttributeType> list = service.getAllLabTestAttributeTypes(Boolean.TRUE);
		assertThat(list, Matchers.hasItems(labTestAttributeTypes.toArray(new LabTestAttributeType[] {})));
		verify(dao, times(1)).getAllLabTestAttributeTypes(Boolean.TRUE);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getAllLabTestAttributeTypes(boolean)}
	 * .
	 */
	@Test
	public final void testGetAllActiveLabTestAttributeTypes() {
		labTestAttributeTypes.get(0).setRetired(Boolean.TRUE);
		List<LabTestAttributeType> activeList = labTestAttributeTypes.subList(1, labTestAttributeTypes.size() - 1);
		when(dao.getAllLabTestAttributeTypes(Boolean.FALSE)).thenReturn(activeList);
		List<LabTestAttributeType> list = service.getAllLabTestAttributeTypes(Boolean.FALSE);
		assertThat(list, Matchers.hasItems(activeList.toArray(new LabTestAttributeType[] {})));
		verify(dao, times(1)).getAllLabTestAttributeTypes(Boolean.FALSE);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getAllLabTestTypes(boolean)}
	 * .
	 */
	@Test
	public final void testGetAllLabTestTypes() {
		when(dao.getAllLabTestTypes(Boolean.TRUE)).thenReturn(labTestTypes);
		List<LabTestType> list = service.getAllLabTestTypes(Boolean.TRUE);
		assertThat(list, Matchers.hasItems(labTestTypes.toArray(new LabTestType[] {})));
		verify(dao, times(1)).getAllLabTestTypes(Boolean.TRUE);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getAllLabTestTypes(boolean)}
	 * .
	 */
	@Test
	public final void testGetAllActiveLabTestTypes() {
		labTestTypes.get(0).setRetired(Boolean.TRUE);
		List<LabTestType> activeList = labTestTypes.subList(1, labTestTypes.size() - 1);
		when(dao.getAllLabTestTypes(Boolean.FALSE)).thenReturn(activeList);
		List<LabTestType> list = service.getAllLabTestTypes(Boolean.FALSE);
		assertThat(list, Matchers.hasItems(activeList.toArray(new LabTestType[] {})));
		verify(dao, times(1)).getAllLabTestTypes(Boolean.FALSE);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getEarliestLabTest(org.openmrs.Patient)}
	 * .
	 */
	@Test
	public final void testGetEarliestLabTest() {
		when(dao.getNLabTests(any(Patient.class), 1, true, false, false)).thenReturn(Arrays.asList(harryGxp));
		LabTest labTest = service.getEarliestLabTest(harry);
		assertThat(labTest, Matchers.is(harryGxp));
		verify(dao, times(1)).getNLabTests(any(Patient.class), 1, true, false, false);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getEarliestLabTestSample(org.openmrs.Patient, org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus)}
	 * .
	 */
	@Test
	public final void testGetEarliestLabTestSample() {
		when(dao.getNLabTestSamples(any(Patient.class), LabTestSampleStatus.PROCESSED, 1, true, false, false))
		        .thenReturn(Arrays.asList(harrySample));
		LabTestSample labTestSample = service.getEarliestLabTestSample(harry, LabTestSampleStatus.PROCESSED);
		assertThat(labTestSample, Matchers.is(harrySample));
		verify(dao, times(1)).getNLabTestSamples(any(Patient.class), LabTestSampleStatus.PROCESSED, 1, true, false, false);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttribute(java.lang.Integer)}
	 * .
	 */
	@Test
	public final void testGetLabTestAttribute() {
		when(dao.getLabTestAttribute(any(Integer.class))).thenReturn(harryMtbResult);
		LabTestAttribute labTestAttribute = service.getLabTestAttribute(harryMtbResult.getId());
		assertThat(labTestAttribute, Matchers.is(harryMtbResult));
		verify(dao, times(1)).getLabTestAttribute(any(Integer.class));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttributes(org.openmrs.module.commonlabtest.LabTestAttributeType, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestAttributesByLabTestAttributeType() {
		when(dao.getLabTestAttributes(mtbResult, null, null, null, null, null, false))
		        .thenReturn(Arrays.asList(harryMtbResult, hermioneMtbResult));
		List<LabTestAttribute> list = service.getLabTestAttributes(mtbResult, false);
		assertThat(list, Matchers.hasItems(harryMtbResult, hermioneMtbResult));
		verify(dao, times(1)).getLabTestAttributes(mtbResult, null, null, null, null, null, false);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttributes(org.openmrs.Patient, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestAttributesByPatient() {
		List<LabTestAttribute> expected = new ArrayList<>();
		for (Iterator<LabTestAttribute> iter = harryGxpResults.iterator(); iter.hasNext();) {
			expected.add(iter.next());
		}
		for (Iterator<LabTestAttribute> iter = harryCxrResults.iterator(); iter.hasNext();) {
			expected.add(iter.next());
		}
		when(dao.getLabTestAttributes(null, null, harry, null, null, null, false)).thenReturn(expected);
		List<LabTestAttribute> list = service.getLabTestAttributes(mtbResult, false);
		assertThat(list, Matchers.hasItems(harryMtbResult, hermioneMtbResult));
		verify(dao, times(1)).getLabTestAttributes(null, null, harry, null, null, null, false);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttributes(org.openmrs.Patient, org.openmrs.module.commonlabtest.LabTestAttributeType, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestAttributesByDateRange() {
		List<LabTestAttribute> expected = new ArrayList<>();
		expected.addAll(harryGxpResults);
		expected.addAll(harryCxrResults);
		when(dao.getLabTestAttributes(null, null, null, null, any(Date.class), any(Date.class), false)).thenReturn(expected);
		List<LabTestAttribute> list = service.getLabTestAttributes(null, null, null, new Date(), new Date(), false);
		assertThat(list,
		    Matchers.hasItems(harryCartridgeId, harryMtbResult, harryRifResult, harryCxrResult, harryRadiologistRemarks));
		verify(dao, times(1)).getLabTestAttributes(null, null, null, null, any(Date.class), any(Date.class), false);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTest(org.openmrs.Encounter)}
	 * .
	 */
	@Test
	public final void testGetLabTestByOrder() {
		when(dao.getLabTest(any(Order.class))).thenReturn(harryCxr);
		Order order = Context.getOrderService().getOrder(3);
		LabTest labTest = service.getLabTest(order);
		assertEquals(harryCxr, labTest);
		verify(dao, times(1)).getLabTest(any(Order.class));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSamples(java.lang.String, java.lang.String, java.lang.String, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestSamplesByLabTest() {
		when(dao.getLabTestSamples(harryGxp, false)).thenReturn(Arrays.asList(harrySample));
		Iterable<LabTestSample> list = service.getLabTestSamples(harryGxp, false);
		assertThat(list, Matchers.hasItems(harrySample));
		verify(dao, times(1)).getLabTestSamples(harryGxp, false);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSamples(org.openmrs.Provider, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestSamplesByProvider() {
		when(dao.getLabTestSamples(owais, false)).thenReturn(Arrays.asList(harrySample));
		List<LabTestSample> list = service.getLabTestSamples(owais, false);
		assertThat(list, Matchers.hasItems(harrySample));
		verify(dao, times(1)).getLabTestSamples(owais, false);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSamples(org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus, java.util.Date, java.util.Date, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestSamplesByPatient() {
		when(dao.getLabTestSamples(harry, false)).thenReturn(Arrays.asList(harrySample));
		List<LabTestSample> list = service.getLabTestSamples(harry, false);
		assertThat(list, Matchers.hasItems(harrySample));
		verify(dao, times(1)).getLabTestSamples(harry, false);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestType(java.lang.Integer)}
	 * .
	 */
	@Test
	public final void testGetLabTestType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.module.commonlabtest.LabTestType, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestsByLabTestType() {
		when(dao.getLabTests(any(LabTestType.class), null, null, null, null, null, null, null, false))
		        .thenReturn(Arrays.asList(harryGxp, hermioneGxp));
		List<LabTest> list = service.getLabTests(geneXpert, false);
		assertThat(list, Matchers.hasItems(harryGxp, hermioneGxp));
		verify(dao, times(1)).getLabTests(any(LabTestType.class), null, null, null, null, null, null, null, false);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.Concept, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestsByConcept() {
		when(dao.getLabTests(null, null, null, null, any(Concept.class), null, null, null, false))
		        .thenReturn(Arrays.asList(harryGxp, hermioneGxp));
		service.getLabTests(null, null, null, null, geneXpert.getReferenceConcept(), null, null, null, false);
		verify(dao, times(1)).getLabTests(null, null, null, null, any(Concept.class), null, null, null, false);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.Provider, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestsByProvider() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.Patient, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestsByPatient() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(java.lang.String, boolean)}
	 * .
	 */
	@Test
	public final void testGetLabTestsByReferenceNumber() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(...)} .
	 */
	@Test
	public final void testGetLabTestsByDateRange() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLatestLabTest(org.openmrs.Patient)}
	 * .
	 */
	@Test
	public final void testGetLatestLabTest() {
		when(dao.getNLabTests(harry, 1, false, true, true)).thenReturn(Arrays.asList(harryGxp));
		LabTest labTest = service.getLatestLabTest(harry);
		assertThat(labTest, Matchers.is(harryGxp));
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLatestLabTestSample(org.openmrs.Patient, org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus)}
	 * .
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
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTest(org.openmrs.module.commonlabtest.LabTest)}
	 * .
	 */
	@Test
	public final void testSaveLabTest() {
		when(dao.saveLabTest(any(LabTest.class))).thenReturn(harryGxp);
		when(dao.saveLabTestSample(any(LabTestSample.class))).thenReturn(harrySample);
		for (LabTestAttribute labTestAttribute : harryGxpResults) {
			when(dao.saveLabTestAttribute(labTestAttribute)).thenReturn(labTestAttribute);
		}
		LabTest saveLabTest = service.saveLabTest(harryGxp, harrySample, harryGxpResults);
		assertTrue(saveLabTest.getAttributes().size() == harryGxpResults.size());
		verify(dao, times(1)).saveLabTest(any(LabTest.class));
		verify(dao, times(1)).saveLabTestSample(any(LabTestSample.class));
		verify(dao, times(3)).saveLabTestAttribute(any(LabTestAttribute.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)}
	 * .
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
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)}
	 * .
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
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTestType(org.openmrs.module.commonlabtest.LabTestType)}
	 * .
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
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#retireLabTestType(org.openmrs.module.commonlabtest.LabTestType, java.lang.String)}
	 * .
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
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#retireLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType, java.lang.String)}
	 * .
	 */
	@Test
	public final void testRetireLabTestAttributeType() {
		doNothing().when(dao).saveLabTestAttributeType(any(LabTestAttributeType.class));
		service.retireLabTestAttributeType(radiologistRemarks, "Not required");
		verify(dao, times(1)).saveLabTestAttributeType(any(LabTestAttributeType.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#unretireLabTestType(org.openmrs.module.commonlabtest.LabTestType)}
	 * .
	 */
	@Test
	public final void testUnretireLabTestType() {
		when(dao.saveLabTestType(any(LabTestType.class))).thenReturn(null);
		service.unretireLabTestType(chestXRay);
		verify(dao, times(1)).saveLabTestType(any(LabTestType.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#unretireLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)}
	 * .
	 */
	@Test
	public final void testUnretireLabTestAttributeType() {
		doNothing().when(dao).saveLabTestAttributeType(any(LabTestAttributeType.class));
		service.unretireLabTestAttributeType(radiologistRemarks);
		verify(dao, times(1)).saveLabTestAttributeType(any(LabTestAttributeType.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#deleteLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)}
	 * .
	 */
	@Test
	public final void testDeleteLabTestAttribute() {
		doNothing().when(dao).purgeLabTestAttribute(any(LabTestAttribute.class));
		service.deleteLabTestAttribute(harryCartridgeId);
		verify(dao, times(1)).purgeLabTestAttribute(any(LabTestAttribute.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#deleteLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)}
	 * .
	 */
	@Test
	public final void testDeleteLabTestAttributeType() {
		when(dao.getLabTestAttributes(any(LabTestAttributeType.class), null, null, null, null, null, false))
		        .thenReturn(Arrays.asList(harryCartridgeId, hermioneCartridgeId));
		doNothing().when(dao).purgeLabTestAttributeType(any(LabTestAttributeType.class));
		doNothing().when(dao).purgeLabTestAttribute(any(LabTestAttribute.class));
		service.deleteLabTestAttributeType(cartridgeId, true);
		verify(dao, times(1)).getLabTestAttributes(any(LabTestAttributeType.class), null, null, null, null, null, false);
		verify(dao, times(1)).purgeLabTestAttributeType(any(LabTestAttributeType.class));
		verify(dao, times(2)).purgeLabTestAttribute(any(LabTestAttribute.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#deleteLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)}
	 * .
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
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#deleteLabTestType(org.openmrs.module.commonlabtest.LabTestType)}
	 * .
	 */
	@Test
	public final void shouldNotDeleteLabTestType() {
		doNothing().when(dao).purgeLabTestType(any(LabTestType.class));
		service.deleteLabTestType(chestXRay, true);
		verify(dao, times(1)).purgeLabTestType(any(LabTestType.class));
		verifyNoMoreInteractions(dao);
	}
	
}
