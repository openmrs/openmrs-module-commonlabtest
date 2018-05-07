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
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.openmrs.CareSetting;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptName;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.Provider;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType.LabTestGroup;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * This class contains sample data used for testing the module
 * 
 * @author owais.hussain@ihsinformatics.com
 */
public abstract class CommonLabTestModuleTestingData extends BaseModuleContextSensitiveTest {
	
	public void initTestData() {
		Patient harry;
		
		LabTestType geneXpert;
		
		LabTestType chestXRay;
		
		LabTestAttributeType cartridgeId;
		
		LabTestAttributeType mtbResult;
		
		LabTestAttributeType rifResult;
		
		LabTestAttributeType cxrResult;
		
		LabTestAttributeType radiologistRemarks;
		
		LabTest harryGxp = null;
		
		LabTest harryCxr = null;
		
		LabTest hermioneGxp = null;
		
		LabTestSample harrySample;
		
		LabTestSample hermioneSample;
		
		LabTestAttribute harryCartridgeId;
		
		LabTestAttribute harryMtbResult;
		
		LabTestAttribute harryRifResult;
		
		LabTestAttribute harryCxrResult;
		
		LabTestAttribute harryRadiologistRemarks;
		
		LabTestAttribute hermioneCartridgeId;
		
		LabTestAttribute hermioneMtbResult;
		
		Set<LabTestAttribute> harryGxpResults;
		
		Set<LabTestAttribute> harryCxrResults;
		
		Set<LabTestAttribute> hermioneGxpResults;
		
		Provider owais = new Provider(1);
		owais.setIdentifier("OWAIS");
		Provider tahira = new Provider(2);
		tahira.setIdentifier("TAHIRA");
		
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
		
		harry = new Patient(new Person(1));
		new Patient(new Person(2));
		
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
		
		OrderType testOrderType = Context.getOrderService().getOrderTypeByUuid(OrderType.TEST_ORDER_TYPE_UUID);
		
		Order harryGxpOrder = new Order(1);
		harryGxpOrder.setPatient(harry);
		harryGxpOrder.setOrderType(testOrderType);
		harryGxpOrder.setCareSetting(new CareSetting());
		harryGxpOrder.setConcept(gxpConcept);
		harryGxpOrder.setOrderer(owais);
		harryGxpOrder.setEncounter(new Encounter(1));
		harryGxpOrder.setOrderReasonNonCoded("Presumptive TB");
		harryGxp = new LabTest(harryGxpOrder);
		harryGxp.setLabTestType(geneXpert);
		harryGxp.addLabTestSample(harrySample);
		harryGxp.setLabReferenceNumber("HARRY-GXP-1");
		harryGxpResults = new HashSet<>();
		harryGxpResults.addAll(Arrays.asList(harryCartridgeId, harryMtbResult, harryRifResult));
		harryGxp.setAttributes(harryGxpResults);
		
		Order harryCxrOrder = new Order(2);
		harryCxrOrder.setPatient(harry);
		harryCxrOrder.setOrderType(testOrderType);
		harryCxrOrder.setCareSetting(new CareSetting());
		harryCxrOrder.setConcept(cxrConcept);
		harryCxrOrder.setOrderer(owais);
		harryCxrOrder.setEncounter(new Encounter(2));
		harryCxrOrder.setOrderReasonNonCoded("Presumptive TB");
		harryCxr = new LabTest(harryCxrOrder);
		harryCxr.setLabTestType(chestXRay);
		harryCxr.setLabReferenceNumber("HARRY-CXR-1");
		harryCxrResults = new HashSet<>();
		harryCxrResults.addAll(Arrays.asList(harryCxrResult, harryRadiologistRemarks));
		harryCxr.setAttributes(harryCxrResults);
		
		Order hermioneGxpOrder = new Order(3);
		hermioneGxpOrder.setPatient(harry);
		hermioneGxpOrder.setOrderType(testOrderType);
		hermioneGxpOrder.setCareSetting(new CareSetting());
		hermioneGxpOrder.setConcept(gxpConcept);
		hermioneGxpOrder.setOrderer(tahira);
		hermioneGxpOrder.setEncounter(new Encounter(3));
		hermioneGxpOrder.setOrderReasonNonCoded("Presumptive TB");
		hermioneGxp = new LabTest(hermioneGxpOrder);
		hermioneGxp.setLabTestType(geneXpert);
		hermioneGxp.addLabTestSample(hermioneSample);
		hermioneGxp.setLabReferenceNumber("HERMIONE-GXP-1");
		hermioneGxpResults = new HashSet<>();
		hermioneGxpResults.addAll(Arrays.asList(harryCartridgeId, harryMtbResult, harryRifResult));
		hermioneGxp.setAttributes(hermioneGxpResults);
		
	}
	
}
