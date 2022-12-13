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
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.HibernateConceptDAO;
import org.openmrs.api.db.hibernate.HibernateOrderDAO;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.api.dao.impl.CommonLabTestDAOImpl;
import org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl;

/**
 * This is a unit test, which verifies logic in CommonLabTestService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class CommonLabTestServiceTest extends CommonLabTestBase {

	@InjectMocks
	CommonLabTestServiceImpl service;

	@Mock
	CommonLabTestDAOImpl dao;

	@Mock
	ConceptService conceptService;

	@Mock
	HibernateOrderDAO orderDao;

	@Before
	public void initMockito() throws Exception {
		super.initTestData();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void setupCommonLabService() {
		assertNotNull(Context.getService(CommonLabTestService.class));
	}

	@Test
	public final void testGetAllLabTestAttributeTypes() {
		when(dao.getAllLabTestAttributeTypes(Boolean.TRUE)).thenReturn(labTestAttributeTypes);
		List<LabTestAttributeType> list = service.getAllLabTestAttributeTypes(Boolean.TRUE);
		assertThat(list, Matchers.hasItems(labTestAttributeTypes.toArray(new LabTestAttributeType[] {})));
		verify(dao, times(1)).getAllLabTestAttributeTypes(Boolean.TRUE);
	}

	@Test
	public final void testGetAllActiveLabTestAttributeTypes() {
		labTestAttributeTypes.get(0).setRetired(Boolean.TRUE);
		List<LabTestAttributeType> activeList = labTestAttributeTypes.subList(1, labTestAttributeTypes.size() - 1);
		when(dao.getAllLabTestAttributeTypes(Boolean.FALSE)).thenReturn(activeList);
		List<LabTestAttributeType> list = service.getAllLabTestAttributeTypes(Boolean.FALSE);
		assertThat(list, Matchers.hasItems(activeList.toArray(new LabTestAttributeType[] {})));
		verify(dao, times(1)).getAllLabTestAttributeTypes(Boolean.FALSE);
	}

	@Test
	public final void testGetAllLabTestTypes() {
		when(dao.getAllLabTestTypes(Boolean.TRUE)).thenReturn(labTestTypes);
		List<LabTestType> list = service.getAllLabTestTypes(Boolean.TRUE);
		assertThat(list, Matchers.hasItems(labTestTypes.toArray(new LabTestType[] {})));
		verify(dao, times(1)).getAllLabTestTypes(Boolean.TRUE);
	}

	@Test
	public final void testGetAllActiveLabTestTypes() {
		labTestTypes.get(0).setRetired(Boolean.TRUE);
		List<LabTestType> activeList = labTestTypes.subList(1, labTestTypes.size() - 1);
		when(dao.getAllLabTestTypes(Boolean.FALSE)).thenReturn(activeList);
		List<LabTestType> list = service.getAllLabTestTypes(Boolean.FALSE);
		assertThat(list, Matchers.hasItems(activeList.toArray(new LabTestType[] {})));
		verify(dao, times(1)).getAllLabTestTypes(Boolean.FALSE);
	}

	@Test
	public final void testLabTestConcepts() {
		List<Concept> list = service.getLabTestConcepts();
		assertThat(list, Matchers.hasItems(tbCultureConcept));
	}

	@Test
	@Ignore
	public final void testSpecimenTypeConcepts() {
		List<Concept> list = service.getSpecimenTypeConcepts();
		// TODO:
		// assertThat(list, Matchers.hasItems());
	}

	@Test
	@Ignore
	public final void testSpecimenSiteConcepts() {
		List<Concept> list = service.getSpecimenSiteConcepts();
		// TODO:
		// assertThat(list, Matchers.hasItems());
	}

	@Test
	public final void testGetEarliestLabTest() {
		when(dao.getNLabTests(any(Patient.class), any(Integer.class), any(Boolean.class), any(Boolean.class),
		    any(Boolean.class))).thenReturn(Arrays.asList(harryGxp));
		LabTest labTest = service.getEarliestLabTest(harry);
		assertThat(labTest, Matchers.is(harryGxp));
		verify(dao, times(1)).getNLabTests(any(Patient.class), any(Integer.class), any(Boolean.class), any(Boolean.class),
		    any(Boolean.class));
	}

	@Test
	public final void testGetEarliestLabTestSample() {
		when(dao.getNLabTestSamples(any(Patient.class), any(LabTestSampleStatus.class), any(Integer.class),
		    any(Boolean.class), any(Boolean.class), any(Boolean.class))).thenReturn(Arrays.asList(harrySample));
		LabTestSample labTestSample = service.getEarliestLabTestSample(harry, LabTestSampleStatus.PROCESSED);
		assertThat(labTestSample, Matchers.is(harrySample));
		verify(dao, times(1)).getNLabTestSamples(any(Patient.class), any(LabTestSampleStatus.class), any(Integer.class),
		    any(Boolean.class), any(Boolean.class), any(Boolean.class));
	}

	@Test
	public final void testGetLabTestAttribute() {
		when(dao.getLabTestAttribute(any(Integer.class))).thenReturn(harryMtbResult);
		LabTestAttribute labTestAttribute = service.getLabTestAttribute(harryMtbResult.getId());
		assertThat(labTestAttribute, Matchers.is(harryMtbResult));
		verify(dao, times(1)).getLabTestAttribute(any(Integer.class));
	}

	@Test
	public final void testGetLabTestAttributeTypesByTestType() {
		when(dao.getLabTestAttributeTypes(any(LabTestType.class), any(Boolean.class)))
		        .thenReturn(Arrays.asList(cartridgeId, mtbResult, rifResult));
		List<LabTestAttributeType> list = service.getLabTestAttributeTypes(geneXpert, Boolean.FALSE);
		assertThat(list, Matchers.hasItems(cartridgeId, mtbResult, rifResult));
		verify(dao, times(1)).getLabTestAttributeTypes(any(LabTestType.class), any(Boolean.class));
		// Check order of items
		for (int i = 1; i < list.size(); i++) {
			assertTrue("List of objects is not sorted by sort weight.",
			    list.get(i - 1).getSortWeight() <= list.get(i).getSortWeight());
		}
	}

	@Test
	public final void testGetLabTestAttributesByLabTestAttributeType() {
		when(dao.getLabTestAttributes(mtbResult, null, null, null, false))
		        .thenReturn(Arrays.asList(harryMtbResult, hermioneMtbResult));
		List<LabTestAttribute> list = service.getLabTestAttributes(mtbResult, false);
		assertThat(list, Matchers.hasItems(harryMtbResult, hermioneMtbResult));
		verify(dao, times(1)).getLabTestAttributes(mtbResult, null, null, null, false);
	}

	@Test
	public final void testGetLabTestAttributesByTestOrder() {
		when(dao.getLabTestAttributes(any(Integer.class)))
		        .thenReturn(Arrays.asList(harryCartridgeId, harryMtbResult, harryRifResult));
		List<LabTestAttribute> list = service.getLabTestAttributes(harryGxp.getTestOrderId());
		assertThat(list, Matchers.hasItems(harryCartridgeId, harryMtbResult, harryRifResult));
		verify(dao, times(1)).getLabTestAttributes(any(Integer.class));
	}

	@Test
	public final void testGetLabTestAttributesByPatient() {
		when(dao.getLabTestAttributes(harry, null, false))
		        .thenReturn(Arrays.asList(harryCartridgeId, harryMtbResult, harryRifResult));
		List<LabTestAttribute> list = service.getLabTestAttributes(harry, false);

		assertThat(list, Matchers.hasItems(harryCartridgeId, harryMtbResult, harryRifResult));
		verify(dao, times(1)).getLabTestAttributes(harry, null, false);
	}

	@Test
	public final void testGetLabTestAttributesByDateRange() {
		List<LabTestAttribute> expected = new ArrayList<LabTestAttribute>();
		expected.addAll(harryGxpResults);
		expected.addAll(harryCxrResults);
		when(dao.getLabTestAttributes(isNull(LabTestAttributeType.class), isNull(String.class), any(Date.class),
		    any(Date.class), any(Boolean.class))).thenReturn(expected);
		List<LabTestAttribute> list = service.getLabTestAttributes(null, null, new Date(), new Date(), false);
		assertThat(list,
		    Matchers.hasItems(harryCartridgeId, harryMtbResult, harryRifResult, harryCxrResult, harryRadiologistRemarks));
		verify(dao, times(1)).getLabTestAttributes(isNull(LabTestAttributeType.class), isNull(String.class), any(Date.class),
		    any(Date.class), any(Boolean.class));
	}

	@Test
	public final void testGetLabTestByOrder() {
		when(dao.getLabTest(any(Order.class))).thenReturn(harryCxr);
		Order order = Context.getOrderService().getOrder(3);
		LabTest labTest = service.getLabTest(order);
		assertEquals(harryCxr, labTest);
		verify(dao, times(1)).getLabTest(any(Order.class));
	}

	@Test
	public final void testGetLabTestSamplesByLabTest() {
		when(dao.getLabTestSamples(harryGxp, false)).thenReturn(Arrays.asList(harrySample));
		Iterable<LabTestSample> list = service.getLabTestSamples(harryGxp, false);
		assertThat(list, Matchers.hasItems(harrySample));
		verify(dao, times(1)).getLabTestSamples(harryGxp, false);
	}

	@Test
	public final void testGetLabTestSamplesByProvider() {
		when(dao.getLabTestSamples(owais, false)).thenReturn(Arrays.asList(harrySample));
		List<LabTestSample> list = service.getLabTestSamples(owais, false);
		assertThat(list, Matchers.hasItems(harrySample));
		verify(dao, times(1)).getLabTestSamples(owais, false);
	}

	@Test
	public final void testGetLabTestSamplesByPatient() {
		when(dao.getLabTestSamples(harry, false)).thenReturn(Arrays.asList(harrySample));
		List<LabTestSample> list = service.getLabTestSamples(harry, false);
		assertThat(list, Matchers.hasItems(harrySample));
		verify(dao, times(1)).getLabTestSamples(harry, false);
	}

	@Test
	public final void testGetLabTestType() {
		when(dao.getLabTestType(any(Integer.class))).thenReturn(geneXpert);
		LabTestType labTestType = service.getLabTestType(geneXpert.getId());
		assertThat(labTestType, Matchers.equalTo(geneXpert));
		verify(dao, times(1)).getLabTestType(any(Integer.class));
	}

	@Test
	public final void testGetLabTestsByLabTestType() {
		when(dao.getLabTests(any(LabTestType.class), isNull(Patient.class), isNull(String.class), isNull(String.class),
		    isNull(Concept.class), isNull(Provider.class), any(Date.class), any(Date.class), any(Boolean.class)))
		            .thenReturn(Arrays.asList(harryGxp, hermioneGxp));
		List<LabTest> list = service.getLabTests(geneXpert, false);
		assertThat(list, Matchers.hasItems(harryGxp, hermioneGxp));
		verify(dao, times(1)).getLabTests(any(LabTestType.class), isNull(Patient.class), isNull(String.class),
		    isNull(String.class), isNull(Concept.class), isNull(Provider.class), any(Date.class), any(Date.class),
		    any(Boolean.class));
	}

	@Test
	public final void testGetLabTestsByConcept() {
		when(dao.getLabTests(isNull(LabTestType.class), isNull(Patient.class), isNull(String.class), isNull(String.class),
		    any(Concept.class), isNull(Provider.class), isNull(Date.class), isNull(Date.class), any(Boolean.class)))
		            .thenReturn(Arrays.asList(harryGxp, hermioneGxp));
		service.getLabTests(null, null, null, null, geneXpert.getReferenceConcept(), null, null, null, false);
		verify(dao, times(1)).getLabTests(isNull(LabTestType.class), isNull(Patient.class), isNull(String.class),
		    isNull(String.class), any(Concept.class), isNull(Provider.class), isNull(Date.class), isNull(Date.class),
		    any(Boolean.class));
	}

	@Test
	public final void testGetLabTestsByProvider() {
		when(dao.getLabTests(isNull(LabTestType.class), isNull(Patient.class), isNull(String.class), isNull(String.class),
		    isNull(Concept.class), any(Provider.class), isNull(Date.class), isNull(Date.class), any(Boolean.class)))
		            .thenReturn(Arrays.asList(harryGxp, hermioneGxp));

		List<LabTest> list = service.getLabTests(owais, false);
		assertThat(list, Matchers.hasItems(harryGxp, hermioneGxp));
		verify(dao, times(1)).getLabTests(isNull(LabTestType.class), isNull(Patient.class), isNull(String.class),
		    isNull(String.class), isNull(Concept.class), any(Provider.class), isNull(Date.class), isNull(Date.class),
		    any(Boolean.class));
	}

	@Test
	public final void testGetLabTestsByPatient() {
		when(dao.getLabTests(isNull(LabTestType.class), any(Patient.class), isNull(Date.class), isNull(Date.class),
		    any(Boolean.class))).thenReturn(Arrays.asList(harryGxp, harryCxr));
		List<LabTest> list = service.getLabTests(harry, false);
		assertThat(list, Matchers.containsInAnyOrder(harryGxp, harryCxr));
		verify(dao, times(1)).getLabTests(isNull(LabTestType.class), any(Patient.class), isNull(Date.class),
		    isNull(Date.class), any(Boolean.class));
	}

	@Test
	public final void testGetLabTestsByReferenceNumber() {
		when(dao.getLabTests(isNull(LabTestType.class), isNull(Patient.class), isNull(String.class), any(String.class),
		    isNull(Concept.class), isNull(Provider.class), isNull(Date.class), isNull(Date.class), any(Boolean.class)))
		            .thenReturn(Arrays.asList(harryGxp));

		List<LabTest> list = service.getLabTests(harryGxp.getLabReferenceNumber(), false);
		assertThat(list, Matchers.hasItems(harryGxp));
		verify(dao, times(1)).getLabTests(isNull(LabTestType.class), isNull(Patient.class), isNull(String.class),
		    any(String.class), isNull(Concept.class), isNull(Provider.class), isNull(Date.class), isNull(Date.class),
		    any(Boolean.class));

	}

	@Test
	public final void testGetLabTestsByDateRange() {
		when(dao.getLabTests(isNull(LabTestType.class), isNull(Patient.class), isNull(String.class), isNull(String.class),
		    isNull(Concept.class), isNull(Provider.class), any(Date.class), any(Date.class), any(Boolean.class)))
		            .thenReturn(Arrays.asList(harryGxp));
		List<LabTest> list = service.getLabTests(null, null, null, null, null, null, new Date(), new Date(), false);
		assertThat(list, Matchers.hasItems(harryGxp));
		verify(dao, times(1)).getLabTests(isNull(LabTestType.class), isNull(Patient.class), isNull(String.class),
		    isNull(String.class), isNull(Concept.class), isNull(Provider.class), any(Date.class), any(Date.class),
		    any(Boolean.class));
	}

	@Test
	public final void testGetLatestLabTest() {
		when(dao.getNLabTests(any(Patient.class), any(Integer.class), any(Boolean.class), any(Boolean.class),
		    any(Boolean.class))).thenReturn(Arrays.asList(harryGxp));
		LabTest labTest = service.getLatestLabTest(harry);
		assertThat(labTest, Matchers.is(harryGxp));
		verify(dao, times(1)).getNLabTests(any(Patient.class), any(Integer.class), any(Boolean.class), any(Boolean.class),
		    any(Boolean.class));
	}

	@Test
	public final void testGetLatestLabTestSample() {
		when(dao.getNLabTestSamples(any(Patient.class), any(LabTestSampleStatus.class), any(Integer.class),
		    any(Boolean.class), any(Boolean.class), any(Boolean.class))).thenReturn(Arrays.asList(harrySample));
		LabTestSample labTestSample = service.getLatestLabTestSample(harry, null);
		assertThat(labTestSample, Matchers.is(harrySample));
	}

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

	@Test
	public final void testSaveLabTestAttributeType() {
		when(dao.saveLabTestAttributeType(any(LabTestAttributeType.class))).thenReturn(cartridgeId);
		LabTestAttributeType labTestAttributeType = new LabTestAttributeType(99);
		labTestAttributeType = service.saveLabTestAttributeType(labTestAttributeType);
		assertEquals(labTestAttributeType, cartridgeId);
		verify(dao, times(1)).saveLabTestAttributeType(any(LabTestAttributeType.class));
		verifyNoMoreInteractions(dao);
	}

	@Test
	public final void testSaveLabTestSample() {
		when(dao.saveLabTestSample(any(LabTestSample.class))).thenReturn(harrySample);
		LabTestSample labTestSample = new LabTestSample(99);
		labTestSample = service.saveLabTestSample(labTestSample);
		assertEquals(labTestSample, harrySample);
		verify(dao, times(1)).saveLabTestSample(any(LabTestSample.class));
		verifyNoMoreInteractions(dao);
	}

	@Test
	public final void testSaveLabTestType() {
		when(dao.saveLabTestType(any(LabTestType.class))).thenReturn(geneXpert);
		LabTestType labTestType = new LabTestType(99);
		labTestType = service.saveLabTestType(labTestType);
		assertEquals(labTestType, geneXpert);
		verify(dao, times(1)).saveLabTestType(any(LabTestType.class));
		verifyNoMoreInteractions(dao);
	}

	@Test
	public final void testRetireLabTestType() {
		when(dao.saveLabTestType(any(LabTestType.class))).thenReturn(chestXRay);
		service.retireLabTestType(chestXRay, "Got too old");
		verify(dao, times(1)).saveLabTestType(any(LabTestType.class));
		verifyNoMoreInteractions(dao);
	}

	@Test
	public final void testRetireLabTestAttributeType() {
		when(dao.saveLabTestAttributeType(any(LabTestAttributeType.class))).thenReturn(radiologistRemarks);
		service.retireLabTestAttributeType(radiologistRemarks, "Not required");
		verify(dao, times(1)).saveLabTestAttributeType(any(LabTestAttributeType.class));
		verifyNoMoreInteractions(dao);
	}

	@Test
	public final void testUnretireLabTestType() {
		when(dao.saveLabTestType(any(LabTestType.class))).thenReturn(null);
		service.unretireLabTestType(chestXRay);
		verify(dao, times(1)).saveLabTestType(any(LabTestType.class));
		verifyNoMoreInteractions(dao);
	}

	@Test
	public final void testUnretireLabTestAttributeType() {
		when(dao.saveLabTestAttributeType(any(LabTestAttributeType.class))).thenReturn(radiologistRemarks);
		service.unretireLabTestAttributeType(radiologistRemarks);
		verify(dao, times(1)).saveLabTestAttributeType(any(LabTestAttributeType.class));
		verifyNoMoreInteractions(dao);
	}

	@Test
	public final void testDeleteLabTestAttribute() {
		doNothing().when(dao).purgeLabTestAttribute(any(LabTestAttribute.class));
		service.deleteLabTestAttribute(harryCartridgeId);
		verify(dao, times(1)).purgeLabTestAttribute(any(LabTestAttribute.class));
		verifyNoMoreInteractions(dao);
	}

	@Test
	public final void testDeleteLabTestAttributeType() {
		when(dao.getLabTestAttributes(any(LabTestAttributeType.class), isNull(String.class), isNull(Date.class),
		    isNull(Date.class), any(Boolean.class))).thenReturn(Arrays.asList(harryCartridgeId, hermioneCartridgeId));
		doNothing().when(dao).purgeLabTestAttributeType(any(LabTestAttributeType.class));
		doNothing().when(dao).purgeLabTestAttribute(any(LabTestAttribute.class));
		service.deleteLabTestAttributeType(cartridgeId, true);
		verify(dao, times(1)).getLabTestAttributes(any(LabTestAttributeType.class), isNull(String.class), isNull(Date.class),
		    isNull(Date.class), any(Boolean.class));
		verify(dao, times(1)).purgeLabTestAttributeType(any(LabTestAttributeType.class));
		verify(dao, times(2)).purgeLabTestAttribute(any(LabTestAttribute.class));
		verifyNoMoreInteractions(dao);
	}

	@Test
	public final void testDeleteLabTestSample() {
		doNothing().when(dao).purgeLabTestSample(any(LabTestSample.class));
		service.deleteLabTestSample(hermioneSample);
		verify(dao, times(1)).purgeLabTestSample(any(LabTestSample.class));
		verifyNoMoreInteractions(dao);
	}

	@SuppressWarnings("deprecation")
	@Test(expected = APIException.class)
	public final void shouldNotDeleteLabTestType() {
		service.deleteLabTestType(chestXRay, false);
	}

	@Test
	public final void testVoidLabTest() {

		when(dao.getLabTestSamples(any(LabTest.class), any(Boolean.class))).thenReturn(Arrays.asList(harrySample));
		when(dao.getLabTestAttributes(any(Integer.class)))
		        .thenReturn(Arrays.asList(harryCartridgeId, harryMtbResult, harryRifResult));
		when(dao.saveLabTest(any(LabTest.class))).thenReturn(harryGxp);

		service.voidLabTest(harryGxp, "Incorrect test order");
		verify(dao, times(1)).getLabTestSamples(any(LabTest.class), any(Boolean.class));
		verify(dao, times(1)).getLabTestAttributes(any(Integer.class));
		verify(dao, times(1)).saveLabTest(any(LabTest.class));
		verify(dao, times(1)).saveLabTestSample(any(LabTestSample.class));
		verify(dao, times(3)).saveLabTestAttribute(any(LabTestAttribute.class));

		verifyNoMoreInteractions(dao);

	}

	@Test
	public final void testUnVoidLabTest() {
		when(dao.getLabTestSamples(any(LabTest.class), any(Boolean.class))).thenReturn(Arrays.asList(harrySample));
		when(dao.getLabTestAttributes(any(Integer.class)))
		        .thenReturn(Arrays.asList(harryCartridgeId, harryMtbResult, harryRifResult));
		when(dao.saveLabTest(any(LabTest.class))).thenReturn(harryGxp);

		service.unvoidLabTest(harryGxp);
		verify(dao, times(1)).getLabTestSamples(any(LabTest.class), any(Boolean.class));
		verify(dao, times(1)).getLabTestAttributes(any(Integer.class));
		verify(dao, times(1)).saveLabTest(any(LabTest.class));
		verify(dao, times(1)).saveLabTestSample(any(LabTestSample.class));
		verify(dao, times(3)).saveLabTestAttribute(any(LabTestAttribute.class));

		verifyNoMoreInteractions(dao);

	}
}
