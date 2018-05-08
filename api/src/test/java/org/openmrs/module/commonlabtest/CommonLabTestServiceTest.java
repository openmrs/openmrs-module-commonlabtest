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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptName;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType.LabTestGroup;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.api.dao.CommonLabTestDao;
import org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * This is a unit test, which verifies logic in CommonLabTestService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class CommonLabTestServiceTest extends BaseModuleContextSensitiveTest {
	
	private static final String DATA_XML = "CommonLabTestService-initialData.xml";
	
	@InjectMocks
	CommonLabTestServiceImpl service;
	
	@Mock
	CommonLabTestDao dao;
	
	Patient harry;
	
	Patient hermione;
	
	LabTestType geneXpert;
	
	LabTestType chestXRay;
	
	List<LabTestType> labTestTypes;
	
	LabTestAttributeType cartridgeId;
	
	LabTestAttributeType mtbResult;
	
	LabTestAttributeType rifResult;
	
	LabTestAttributeType cxrResult;
	
	LabTestAttributeType radiologistRemarks;
	
	List<LabTestAttributeType> labTestAttributeTypes;
	
	LabTest harryGxp;
	
	LabTest harryCxr;
	
	LabTest hermioneGxp;
	
	LabTestSample harrySample;
	
	LabTestSample hermioneSample;
	
	LabTestAttribute harryCartridgeId;
	
	LabTestAttribute harryMtbResult;
	
	LabTestAttribute harryRifResult;
	
	LabTestAttribute harryCxrResult;
	
	LabTestAttribute harryRadiologistRemarks;
	
	LabTestAttribute hermioneCartridgeId;
	
	LabTestAttribute hermioneMtbResult;
	
	LabTestAttribute hermioneRifResult;
	
	Set<LabTestAttribute> harryGxpResults;
	
	Set<LabTestAttribute> harryCxrResults;
	
	Set<LabTestAttribute> hermioneGxpResults;
	
	Provider owais;
	
	Provider tahira;
	
	@Before
	public void runBeforeTest() throws Exception {
		MockitoAnnotations.initMocks(this);
		initializeInMemoryDatabase();
		executeDataSet(DATA_XML);
		initData();
	}
	
	/**
	 * Initialize all objects
	 * 
	 * @throws Exception
	 */
	public void initData() throws Exception {
		
		Context.getService(CommonLabTestService.class);
		
		owais = Context.getProviderService().getProvider(3);
		tahira = Context.getProviderService().getProvider(4);
		
		harry = Context.getPatientService().getPatient(100);
		hermione = Context.getPatientService().getPatient(101);
		
		ConceptClass testClass = new ConceptClass(1);
		testClass.setName("Test");
		Concept gxpConcept = new Concept(1);
		Concept cxrConcept = new Concept(2);
		gxpConcept.addName(new ConceptName("GeneXpert Test", Locale.ENGLISH));
		gxpConcept.setShortName(new ConceptName("GXP", Locale.ENGLISH));
		gxpConcept.setConceptClass(testClass);
		
		cxrConcept.addName(new ConceptName("Chest X-ray Test", Locale.ENGLISH));
		cxrConcept.setShortName(new ConceptName("CXR", Locale.ENGLISH));
		cxrConcept.setConceptClass(testClass);
		
		geneXpert = new LabTestType(1);
		geneXpert.setName("GeneXpert Test");
		geneXpert.setShortName("GXP");
		geneXpert.setTestGroup(LabTestGroup.BACTERIOLOGY);
		geneXpert.setRequiresSpecimen(Boolean.TRUE);
		geneXpert.setReferenceConcept(gxpConcept);
		
		chestXRay = new LabTestType(2);
		chestXRay.setName("Chest X-ray Test");
		chestXRay.setShortName("CXR");
		chestXRay.setTestGroup(LabTestGroup.RADIOLOGY);
		chestXRay.setRequiresSpecimen(Boolean.FALSE);
		chestXRay.setReferenceConcept(cxrConcept);
		
		Arrays.asList(geneXpert, chestXRay);
		
		cartridgeId = new LabTestAttributeType(1);
		cartridgeId.setName("Cartridge ID");
		cartridgeId.setLabTestType(geneXpert);
		cartridgeId.setSortWeight(1);
		cartridgeId.setDatatypeClassname("org.openmrs.customdatatype.FreeTextDatatype");
		cartridgeId.setMinOccurs(1);
		cartridgeId.setMinOccurs(1);
		
		mtbResult = new LabTestAttributeType(2);
		mtbResult.setName("GeneXpert MTB Result");
		mtbResult.setLabTestType(geneXpert);
		mtbResult.setSortWeight(1);
		mtbResult.setDatatypeClassname("org.openmrs.customdatatype.ConceptDatatype");
		mtbResult.setMinOccurs(0);
		mtbResult.setMinOccurs(1);
		
		rifResult = new LabTestAttributeType(3);
		rifResult.setName("GeneXpert RIF Result");
		rifResult.setLabTestType(geneXpert);
		rifResult.setSortWeight(1);
		rifResult.setDatatypeClassname("org.openmrs.customdatatype.ConceptDatatype");
		rifResult.setMinOccurs(0);
		rifResult.setMinOccurs(1);
		
		cxrResult = new LabTestAttributeType(4);
		cxrResult.setName("Chest X-Ray Result");
		cxrResult.setLabTestType(chestXRay);
		cxrResult.setSortWeight(1);
		cxrResult.setDatatypeClassname("org.openmrs.customdatatype.RegexValidatedTextDatatype");
		cxrResult.setDatatypeConfig("(AB)?NORMAL|ERROR");
		cxrResult.setMinOccurs(1);
		cxrResult.setMinOccurs(1);
		
		radiologistRemarks = new LabTestAttributeType(5);
		radiologistRemarks.setName("Cartridge ID");
		radiologistRemarks.setLabTestType(chestXRay);
		radiologistRemarks.setSortWeight(1);
		radiologistRemarks.setDatatypeClassname("org.openmrs.customdatatype.FreeTextDatatype");
		radiologistRemarks.setMinOccurs(0);
		radiologistRemarks.setMinOccurs(5);
		
		Arrays.asList(cartridgeId, mtbResult, rifResult, cxrResult, radiologistRemarks);
		
		harrySample = new LabTestSample(1);
		harrySample.setCollector(owais);
		Calendar collectionDate = Calendar.getInstance();
		collectionDate.set(2018 - 1900, 5, 1);
		harrySample.setCollectionDate(collectionDate.getTime());
		harrySample.setStatus(LabTestSampleStatus.PROCESSED);
		harrySample.setSpecimenType(new Concept(99));
		harrySample.setSpecimenSite(new Concept(100));
		harrySample.setSampleIdentifier("HARRY-SPUTUM-1");
		Calendar processingDate = Calendar.getInstance();
		processingDate.set(2018 - 1900, 5, 3);
		harrySample.setProcessedDate(processingDate.getTime());
		harrySample.setQuantity(20D);
		harrySample.setUnits("ml");
		
		hermioneSample = new LabTestSample(1);
		hermioneSample.setCollector(owais);
		collectionDate = Calendar.getInstance();
		collectionDate.set(2018 - 1900, 5, 2);
		hermioneSample.setCollectionDate(collectionDate.getTime());
		hermioneSample.setStatus(LabTestSampleStatus.ACCEPTED);
		hermioneSample.setSpecimenType(new Concept(99));
		hermioneSample.setSpecimenSite(new Concept(100));
		hermioneSample.setSampleIdentifier("HERMIONE-SPUTUM-1");
		hermioneSample.setQuantity(30D);
		hermioneSample.setUnits("ml");
		
		harryCartridgeId = new LabTestAttribute(1);
		harryCartridgeId.setLabTest(harryGxp);
		harryCartridgeId.setAttributeType(cartridgeId);
		harryCartridgeId.setValueReferenceInternal("201805071211");
		
		harryMtbResult = new LabTestAttribute(2);
		harryMtbResult.setLabTest(harryGxp);
		harryMtbResult.setAttributeType(mtbResult);
		harryMtbResult.setValueReferenceInternal("MTB DETECTED");
		
		harryRifResult = new LabTestAttribute(3);
		harryRifResult.setLabTest(harryGxp);
		harryRifResult.setAttributeType(rifResult);
		harryRifResult.setValueReferenceInternal("DETECTED");
		
		harryCxrResult = new LabTestAttribute(4);
		harryCxrResult.setLabTest(harryCxr);
		harryCxrResult.setAttributeType(cxrResult);
		harryCxrResult.setValueReferenceInternal("ABNORMAL");
		
		harryRadiologistRemarks = new LabTestAttribute(5);
		harryRadiologistRemarks.setLabTest(harryCxr);
		harryRadiologistRemarks.setAttributeType(radiologistRemarks);
		harryRadiologistRemarks.setValueReferenceInternal("Not just abnormal, but paranormal");
		
		hermioneCartridgeId = new LabTestAttribute(6);
		hermioneCartridgeId.setLabTest(hermioneGxp);
		hermioneCartridgeId.setAttributeType(cartridgeId);
		hermioneCartridgeId.setValueReferenceInternal("201805071325");
		
		hermioneMtbResult = new LabTestAttribute(7);
		hermioneMtbResult.setLabTest(hermioneGxp);
		hermioneMtbResult.setAttributeType(mtbResult);
		hermioneMtbResult.setValueReferenceInternal("NOT DETECTED");
		
		Order harryGxpOrder = Context.getOrderService().getOrder(1);
		Order harryCxrOrder = Context.getOrderService().getOrder(2);
		Order hermioneGxpOrder = Context.getOrderService().getOrder(3);
		
		harryGxp = new LabTest(harryGxpOrder);
		harryGxp.setLabTestType(geneXpert);
		harryGxp.addLabTestSample(harrySample);
		harryGxp.setLabReferenceNumber("HARRY-GXP-1");
		harryGxpResults = new HashSet<>();
		harryGxpResults.addAll(Arrays.asList(harryCartridgeId, harryMtbResult, harryRifResult));
		harryGxp.setAttributes(harryGxpResults);
		
		harryCxr = new LabTest(harryCxrOrder);
		harryCxr.setLabTestType(chestXRay);
		harryCxr.setLabReferenceNumber("HARRY-CXR-1");
		harryCxrResults = new HashSet<>();
		harryCxrResults.addAll(Arrays.asList(harryCxrResult, harryRadiologistRemarks));
		harryCxr.setAttributes(harryCxrResults);
		
		hermioneGxp = new LabTest(hermioneGxpOrder);
		hermioneGxp.setLabTestType(geneXpert);
		hermioneGxp.addLabTestSample(hermioneSample);
		hermioneGxp.setLabReferenceNumber("HERMIONE-GXP-1");
		hermioneGxpResults = new HashSet<>();
		hermioneGxpResults.addAll(Arrays.asList(harryCartridgeId, harryMtbResult, harryRifResult));
		hermioneGxp.setAttributes(hermioneGxpResults);
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
	@Ignore
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
	@Ignore
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
	@Ignore
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
	@Ignore
	public final void testGetAllActiveLabTestTypes() {
		labTestTypes.get(0).setRetired(Boolean.TRUE);
		List<LabTestType> activeList = labTestTypes.subList(1, labTestTypes.size() - 1);
		when(dao.getAllLabTestTypes(Boolean.FALSE)).thenReturn(activeList);
		List<LabTestType> list = service.getAllLabTestTypes(Boolean.FALSE);
		assertThat(list, Matchers.hasItems(activeList.toArray(new LabTestType[] {})));
		verify(dao, times(1)).getAllLabTestTypes(Boolean.TRUE);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getEarliestLabTest(org.openmrs.Patient)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetEarliestLabTest() {
		when(dao.getNLabTests(any(Patient.class), 1, true, false, true)).thenReturn(Arrays.asList(harryGxp));
		LabTest labTest = service.getEarliestLabTest(harry);
		assertThat(labTest, Matchers.is(harryGxp));
		verify(dao, times(1)).getNLabTests(any(Patient.class), 1, true, false, true);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getEarliestLabTestSample(org.openmrs.Patient, org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetEarliestLabTestSample() {
		when(dao.getNLabTestSamples(any(Patient.class), any(LabTestSampleStatus.class), 1, true, false, true))
		        .thenReturn(Arrays.asList(harrySample));
		LabTestSample labTestSample = service.getEarliestLabTestSample(harry, null);
		assertThat(labTestSample, Matchers.is(harrySample));
		verify(dao, times(1)).getNLabTestSamples(any(Patient.class), any(LabTestSampleStatus.class), 1, true, false, true);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttribute(java.lang.Integer)}
	 * .
	 */
	@Test
	@Ignore
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
	@Ignore
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
	@Ignore
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
	@Ignore
	public final void testGetLabTestAttributesByPatientAndLabTestAttributeType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttributeType(java.lang.Integer)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestAttributeType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttributeTypeByUuid(java.lang.String)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestAttributeTypeByUuid() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestAttributeTypes(java.lang.String, java.lang.String, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestAttributeTypes() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestByUuid(java.lang.String)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestByUuid() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTest(org.openmrs.Encounter)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestEncounter() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTest(org.openmrs.Order)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestOrder() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSample(java.lang.Integer)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestSample() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSampleByUuid(java.lang.String)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestSampleByUuid() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSamples(org.openmrs.module.commonlabtest.LabTest, org.openmrs.Patient, org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.openmrs.Provider, java.util.Date, java.util.Date, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestSamplesLabTestPatientLabTestSampleStatusStringStringStringStringProviderDateDateBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSamples(java.lang.String, java.lang.String, java.lang.String, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestSamplesStringStringStringBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSamples(org.openmrs.module.commonlabtest.LabTest, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestSamplesLabTestBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSamples(org.openmrs.Provider, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestSamplesProviderBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestSamples(org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus, java.util.Date, java.util.Date, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestSamplesLabTestSampleStatusDateDateBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestType(java.lang.Integer)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestTypeByUuid(java.lang.String)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestTypeByUuid() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTestTypes(java.lang.String, java.lang.String, org.openmrs.module.commonlabtest.LabTestType.LabTestGroup, java.lang.Boolean, org.openmrs.Concept, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestTypes() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.module.commonlabtest.LabTestType, org.openmrs.Patient, java.lang.String, java.lang.String, org.openmrs.Concept, org.openmrs.Provider, java.util.Date, java.util.Date, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestsLabTestTypePatientStringStringConceptProviderDateDateBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.module.commonlabtest.LabTestType, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestsLabTestTypeBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.Concept, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestsConceptBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.Provider, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestsProviderBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(org.openmrs.Patient, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestsPatientBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLabTests(java.lang.String, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testGetLabTestsStringBoolean() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#getLatestLabTest(org.openmrs.Patient)}
	 * .
	 */
	@Test
	@Ignore
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
	@Ignore
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
	@Ignore
	public final void testSaveLabTestLabTest() {
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTest(org.openmrs.module.commonlabtest.LabTest, org.openmrs.module.commonlabtest.LabTestSample, java.util.Collection)}
	 * .
	 */
	@Test
	@Ignore
	public final void testSaveLabTestLabTestLabTestSampleCollectionOfLabTestAttribute() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)}
	 * .
	 */
	@Test
	@Ignore
	public final void testSaveLabTestAttribute() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTestAttributes(java.util.List)}
	 * .
	 */
	@Test
	@Ignore
	public final void testSaveLabTestAttributes() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#saveLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)}
	 * .
	 */
	@Test
	@Ignore
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
	@Ignore
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
	@Ignore
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
	@Ignore
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
	@Ignore
	public final void testRetireLabTestAttributeType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#unretireLabTestType(org.openmrs.module.commonlabtest.LabTestType)}
	 * .
	 */
	@Test
	@Ignore
	public final void testUnretireLabTestType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#unretireLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)}
	 * .
	 */
	@Test
	@Ignore
	public final void testUnretireLabTestAttributeType() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#voidLabTest(org.openmrs.module.commonlabtest.LabTest, java.lang.String)}
	 * .
	 */
	@Test
	@Ignore
	public final void testVoidLabTest() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#voidLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute, java.lang.String)}
	 * .
	 */
	@Test
	@Ignore
	public final void testVoidLabTestAttribute() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#voidLabTestSample(org.openmrs.module.commonlabtest.LabTestSample, java.lang.String)}
	 * .
	 */
	@Test
	@Ignore
	public final void testVoidLabTestSample() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#unvoidLabTest(org.openmrs.module.commonlabtest.LabTest)}
	 * .
	 */
	@Test
	@Ignore
	public final void testUnvoidLabTest() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#unvoidLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)}
	 * .
	 */
	@Test
	@Ignore
	public final void testUnvoidLabTestAttribute() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#unvoidLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)}
	 * .
	 */
	@Test
	@Ignore
	public final void testUnvoidLabTestSample() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#deleteLabTest(org.openmrs.module.commonlabtest.LabTest)}
	 * .
	 */
	@Test
	@Ignore
	public final void shouldNotDeleteLabTest() {
		// TODO
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#deleteLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)}
	 * .
	 */
	@Test
	@Ignore
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
	@Ignore
	public final void testDeleteLabTestAttributeType() {
		doNothing().when(dao).purgeLabTestAttributeType(any(LabTestAttributeType.class));
		service.deleteLabTestAttributeType(cartridgeId, true);
		verify(dao, times(1)).purgeLabTestAttributeType(any(LabTestAttributeType.class));
		verifyNoMoreInteractions(dao);
	}
	
	/**
	 * Test method for
	 * {@link org.openmrs.module.commonlabtest.api.impl.CommonLabTestServiceImpl#deleteLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)}
	 * .
	 */
	@Test
	@Ignore
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
	@Ignore
	public final void shouldNotDeleteLabTestType() {
		doNothing().when(dao).purgeLabTestType(any(LabTestType.class));
		service.deleteLabTestType(chestXRay, true);
		verify(dao, times(1)).purgeLabTestType(any(LabTestType.class));
		verifyNoMoreInteractions(dao);
	}
	
}
