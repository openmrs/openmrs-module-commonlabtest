/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.commonlabtest;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.openmrs.Concept;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType.LabTestGroup;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * @author owais.hussain@ihsinformatics.com
 */
public class CommonLabTestBaseTest extends BaseModuleContextSensitiveTest {
	
	protected static final String DATA_XML = "CommonLabTestService-initialData.xml";
	
	protected Patient harry;
	
	protected Patient hermione;
	
	protected LabTestType geneXpert;
	
	protected LabTestType chestXRay;
	
	protected List<LabTestType> labTestTypes;
	
	protected LabTestAttributeType cartridgeId;
	
	protected LabTestAttributeType mtbResult;
	
	protected LabTestAttributeType rifResult;
	
	protected LabTestAttributeType cxrResult;
	
	protected LabTestAttributeType radiologistRemarks;
	
	protected LabTestAttributeType cad4tbScore;
	
	protected LabTestAttributeType xrayFilmPrinted;
	
	protected List<LabTestAttributeType> labTestAttributeTypes;
	
	protected List<LabTestAttributeType> activeLabTestAttributeTypes;
	
	protected LabTest harryGxp;
	
	protected LabTest harryCxr;
	
	protected LabTest hermioneGxp;
	
	protected LabTestSample harrySample;
	
	protected LabTestSample hermioneSample;
	
	protected LabTestAttribute harryCartridgeId;
	
	protected LabTestAttribute harryMtbResult;
	
	protected LabTestAttribute harryRifResult;
	
	protected LabTestAttribute harryCxrResult;
	
	protected LabTestAttribute harryRadiologistRemarks;
	
	protected LabTestAttribute hermioneCartridgeId;
	
	protected LabTestAttribute hermioneMtbResult;
	
	protected LabTestAttribute hermioneRifResult;
	
	protected Set<LabTestAttribute> harryGxpResults;
	
	protected Set<LabTestAttribute> harryCxrResults;
	
	protected Set<LabTestAttribute> hermioneGxpResults;
	
	protected Provider owais;
	
	protected Provider tahira;
	
	/**
	 * Initialize all data objects before each test
	 * 
	 * @throws Exception
	 */
	@Before
	public void runBeforeTest() throws Exception {
		initializeInMemoryDatabase();
		executeDataSet(DATA_XML);
		
		owais = Context.getProviderService().getProvider(300);
		tahira = Context.getProviderService().getProvider(400);
		
		harry = Context.getPatientService().getPatient(1000);
		hermione = Context.getPatientService().getPatient(2000);
		
		Concept gxpConcept = Context.getConceptService().getConcept(500);
		Concept cxrConcept = Context.getConceptService().getConcept(600);
		
		geneXpert = new LabTestType(1);
		geneXpert.setName("GeneXpert Test");
		geneXpert.setShortName("GXP");
		geneXpert.setTestGroup(LabTestGroup.BACTERIOLOGY);
		geneXpert.setRequiresSpecimen(Boolean.TRUE);
		geneXpert.setReferenceConcept(gxpConcept);
		geneXpert.setUuid("4bf46c09-46e9-11e8-943c-40b034c3cfee");
		
		chestXRay = new LabTestType(2);
		chestXRay.setName("Chest X-ray Test");
		chestXRay.setShortName("CXR");
		chestXRay.setTestGroup(LabTestGroup.RADIOLOGY);
		chestXRay.setRequiresSpecimen(Boolean.FALSE);
		chestXRay.setReferenceConcept(cxrConcept);
		chestXRay.setUuid("a277edf4-46ea-11e8-943c-40b034c3cfee");
		
		labTestTypes = Arrays.asList(geneXpert, chestXRay);
		
		cartridgeId = new LabTestAttributeType(1);
		cartridgeId.setName("Cartridge ID");
		cartridgeId.setLabTestType(geneXpert);
		cartridgeId.setSortWeight(1D);
		cartridgeId.setDatatypeClassname("org.openmrs.customdatatype.FreeTextDatatype");
		cartridgeId.setMinOccurs(1);
		cartridgeId.setMinOccurs(1);
		cartridgeId.setUuid("ecf166e5-478e-11e8-943c-40b034c3cfee");
		
		mtbResult = new LabTestAttributeType(2);
		mtbResult.setName("MTB Result");
		mtbResult.setLabTestType(geneXpert);
		mtbResult.setSortWeight(2D);
		mtbResult.setDatatypeClassname("org.openmrs.customdatatype.ConceptDatatype");
		mtbResult.setMinOccurs(0);
		mtbResult.setMinOccurs(1);
		mtbResult.setUuid("ea22684f-478e-11e8-943c-40b034c3cfee");
		
		rifResult = new LabTestAttributeType(3);
		rifResult.setName("RIF Result");
		rifResult.setLabTestType(geneXpert);
		rifResult.setSortWeight(3D);
		rifResult.setDatatypeClassname("org.openmrs.customdatatype.ConceptDatatype");
		rifResult.setMinOccurs(0);
		rifResult.setMinOccurs(1);
		rifResult.setUuid("eb66655f-478e-11e8-943c-40b034c3cfee");
		
		cxrResult = new LabTestAttributeType(4);
		cxrResult.setName("Chest X-Ray Result");
		cxrResult.setLabTestType(chestXRay);
		cxrResult.setSortWeight(1D);
		cxrResult.setDatatypeClassname("org.openmrs.customdatatype.RegexValidatedTextDatatype");
		cxrResult.setDatatypeConfig("(AB)?NORMAL|ERROR");
		cxrResult.setMinOccurs(1);
		cxrResult.setMinOccurs(1);
		cxrResult.setUuid("efeb9339-538d-11e8-9c7c-40b034c3cfee");
		
		radiologistRemarks = new LabTestAttributeType(5);
		radiologistRemarks.setName("Radiologist Remarks");
		radiologistRemarks.setLabTestType(chestXRay);
		radiologistRemarks.setSortWeight(2D);
		radiologistRemarks.setDatatypeClassname("org.openmrs.customdatatype.FreeTextDatatype");
		radiologistRemarks.setMinOccurs(0);
		radiologistRemarks.setMinOccurs(5);
		radiologistRemarks.setUuid("f43de058-538d-11e8-9c7c-40b034c3cfee");
		
		cad4tbScore = new LabTestAttributeType(6);
		cad4tbScore.setName("CAD4TB Score");
		cad4tbScore.setLabTestType(chestXRay);
		cad4tbScore.setSortWeight(3D);
		cad4tbScore.setDatatypeClassname("org.openmrs.customdatatype.FloatDatatype");
		cad4tbScore.setMinOccurs(0);
		cad4tbScore.setMinOccurs(1);
		cad4tbScore.setRetired(Boolean.TRUE);
		cad4tbScore.setRetireReason("CAD4TB no longer functional.");
		cad4tbScore.setDateRetired(new Date());
		cad4tbScore.setUuid("ed8b4caf-478e-11e8-943c-40b034c3cfee");
		
		xrayFilmPrinted = new LabTestAttributeType(7);
		xrayFilmPrinted.setName("X-Ray Film Printed");
		xrayFilmPrinted.setLabTestType(chestXRay);
		xrayFilmPrinted.setSortWeight(4D);
		xrayFilmPrinted.setDatatypeClassname("org.openmrs.customdatatype.datatype.BooleanDatatype");
		xrayFilmPrinted.setMinOccurs(0);
		xrayFilmPrinted.setMinOccurs(1);
		xrayFilmPrinted.setRetired(Boolean.TRUE);
		xrayFilmPrinted.setRetireReason("Printing has been outsourced.");
		xrayFilmPrinted.setDateRetired(new Date());
		xrayFilmPrinted.setUuid("ee261470-478e-11e8-943c-40b034c3cfee");
		
		labTestAttributeTypes = Arrays.asList(cartridgeId, mtbResult, rifResult, cxrResult, radiologistRemarks, cad4tbScore,
		    xrayFilmPrinted);
		
		activeLabTestAttributeTypes = Arrays.asList(cartridgeId, mtbResult, rifResult, cxrResult, radiologistRemarks);
		
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
		harrySample.setUuid("f4bffc2f-5343-11e8-9c7c-40b034c3cfee");
		
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
		hermioneSample.setUuid("f40420f8-5346-11e8-9c7c-40b034c3cfee");
		
		harryCartridgeId = new LabTestAttribute(1);
		harryCartridgeId.setLabTest(harryGxp);
		harryCartridgeId.setAttributeType(cartridgeId);
		harryCartridgeId.setValueReferenceInternal("201805071211");
		harryCartridgeId.setUuid("2c9737d9-47c2-11e8-943c-40b034c3cfee");
		
		harryMtbResult = new LabTestAttribute(2);
		harryMtbResult.setLabTest(harryGxp);
		harryMtbResult.setAttributeType(mtbResult);
		harryMtbResult.setValueReferenceInternal("MTB DETECTED");
		harryMtbResult.setUuid("2d9cc0d3-47c2-11e8-943c-40b034c3cfee");
		
		harryRifResult = new LabTestAttribute(3);
		harryRifResult.setLabTest(harryGxp);
		harryRifResult.setAttributeType(rifResult);
		harryRifResult.setValueReferenceInternal("DETECTED");
		harryRifResult.setUuid("2e45af47-47c2-11e8-943c-40b034c3cfee");
		
		harryCxrResult = new LabTestAttribute(4);
		harryCxrResult.setLabTest(harryCxr);
		harryCxrResult.setAttributeType(cxrResult);
		harryCxrResult.setValueReferenceInternal("ABNORMAL");
		harryCxrResult.setUuid("2efe1af7-47c2-11e8-943c-40b034c3cfee");
		
		harryRadiologistRemarks = new LabTestAttribute(5);
		harryRadiologistRemarks.setLabTest(harryCxr);
		harryRadiologistRemarks.setAttributeType(radiologistRemarks);
		harryRadiologistRemarks.setValueReferenceInternal("Not just abnormal, but paranormal");
		harryRadiologistRemarks.setUuid("2f9066fb-47c2-11e8-943c-40b034c3cfee");
		
		hermioneCartridgeId = new LabTestAttribute(6);
		hermioneCartridgeId.setLabTest(hermioneGxp);
		hermioneCartridgeId.setAttributeType(cartridgeId);
		hermioneCartridgeId.setValueReferenceInternal("201805071325");
		hermioneCartridgeId.setUuid("b46ad728-51f0-11e8-b60d-080027ea421d");
		
		hermioneMtbResult = new LabTestAttribute(7);
		hermioneMtbResult.setLabTest(hermioneGxp);
		hermioneMtbResult.setAttributeType(mtbResult);
		hermioneMtbResult.setValueReferenceInternal("NOT DETECTED");
		hermioneMtbResult.setUuid("acbf3ff5-51f0-11e8-b60d-080027ea421d");
		
		Order harryGxpOrder = Context.getOrderService().getOrder(100);
		Order harryCxrOrder = Context.getOrderService().getOrder(200);
		Order hermioneGxpOrder = Context.getOrderService().getOrder(300);
		
		harryGxp = new LabTest(harryGxpOrder);
		harryGxp.setLabTestType(geneXpert);
		harryGxp.addLabTestSample(harrySample);
		harryGxp.setLabReferenceNumber("HARRY-GXP-1");
		harryGxpResults = new HashSet<>();
		harryGxpResults.addAll(Arrays.asList(harryCartridgeId, harryMtbResult, harryRifResult));
		harryGxp.setAttributes(harryGxpResults);
		harryGxp.setUuid("d175e92e-47bf-11e8-943c-40b034c3cfee");
		
		harryCxr = new LabTest(harryCxrOrder);
		harryCxr.setLabTestType(chestXRay);
		harryCxr.setLabReferenceNumber("HARRY-CXR-1");
		harryCxrResults = new HashSet<>();
		harryCxrResults.addAll(Arrays.asList(harryCxrResult, harryRadiologistRemarks));
		harryCxr.setAttributes(harryCxrResults);
		harryCxr.setUuid("d23c2576-47bf-11e8-943c-40b034c3cfee");
		
		hermioneGxp = new LabTest(hermioneGxpOrder);
		hermioneGxp.setLabTestType(geneXpert);
		hermioneGxp.addLabTestSample(hermioneSample);
		hermioneGxp.setLabReferenceNumber("HERMIONE-GXP-1");
		hermioneGxpResults = new HashSet<>();
		hermioneGxpResults.addAll(Arrays.asList(harryCartridgeId, harryMtbResult, harryRifResult));
		hermioneGxp.setAttributes(hermioneGxpResults);
		hermioneGxp.setUuid("d175e92e-dc93-11e8-d298-40b034c3cfee");
	}
	
}
