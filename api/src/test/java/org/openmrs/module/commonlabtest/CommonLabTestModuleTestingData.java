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
import java.util.List;
import java.util.Locale;

import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptName;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.module.commonlabtest.LabTestType.LabTestGroup;

/**
 * This class contains sample data used for testing the module
 * 
 * @author owais.hussain@ihsinformatics.com
 */
public abstract class CommonLabTestModuleTestingData {
	
	protected Patient harry;
	
	protected Patient hermione;
	
	protected LabTestType geneXpert;
	
	protected LabTestType chestXRay;
	
	protected List<LabTestType> labTestTypes;
	
	protected LabTestAttributeType cartridgeId;
	
	protected LabTestAttributeType gxpResult;
	
	protected LabTestAttributeType rifResult;
	
	protected LabTestAttributeType cxrResult;
	
	protected LabTestAttributeType radiologistRemarks;
	
	protected List<LabTestAttributeType> labTestAttributeTypes;
	
	protected LabTest harryGxp;
	
	protected LabTest harryCxr;
	
	protected LabTest hermioneGxp;
	
	protected List<LabTestAttribute> harryGxpResults;
	
	protected List<LabTestAttribute> harryCxrResults;
	
	protected List<LabTestAttribute> hermioneGxpResults;
	
	protected LabTestSample harrySample;
	
	protected LabTestSample hermioneSample;
	
	public void initTestData() {
		harry = new Patient(new Person(1));
		hermione = new Patient(new Person(2));
		
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
		
		labTestTypes = Arrays.asList(geneXpert, chestXRay);
		
		cartridgeId = new LabTestAttributeType(1);
		cartridgeId.setName("Cartridge ID");
		cartridgeId.setLabTestType(geneXpert);
		cartridgeId.setSortWeight(1);
		cartridgeId.setDatatypeClassname("org.openmrs.customdatatype.FreeTextDatatype");
		cartridgeId.setMinOccurs(1);
		cartridgeId.setMinOccurs(1);
		
		gxpResult = new LabTestAttributeType(2);
		gxpResult.setName("GeneXpert MTB Result");
		gxpResult.setLabTestType(geneXpert);
		gxpResult.setSortWeight(1);
		gxpResult.setDatatypeClassname("org.openmrs.customdatatype.ConceptDatatype");
		gxpResult.setMinOccurs(0);
		gxpResult.setMinOccurs(1);
		
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
		
		labTestAttributeTypes = Arrays.asList(cartridgeId, gxpResult, rifResult, cxrResult, radiologistRemarks);
		
	}
	
}
