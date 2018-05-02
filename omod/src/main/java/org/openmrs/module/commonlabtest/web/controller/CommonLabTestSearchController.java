/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.commonlabtest.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.api.APIException;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestGroup;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class configured as controller using annotation and mapped with the URL of
 * 'module/${rootArtifactid}/${rootArtifactid}Link.form'.
 */
@Controller("${rootrootArtifactid}.CommonLabTestController")
@RequestMapping(value = "module/${rootArtifactid}/${rootArtifactid}.form")
public class CommonLabTestSearchController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	CommonLabTestService labTestService;
	
	protected List<LabTestAttribute> searchLabTestAttributes(LabTestAttributeType labTestAttributeType, Patient patient,
	        String valueReference, Date from, Date to, Boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestAttribute> searchLabTestAttributes(Patient patient, LabTestAttributeType labTestAttributeType,
	        Boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestSample> searchLabTestSamples(LabTest labTest, Patient patient, LabTestSampleStatus status,
	        String labSampleIdentifier, String orderNumber, String labReferenceNumber, String specimenName,
	        Provider collector, Date from, Date to, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestType> searchLabTestTypes(String name, String shortName, LabTestGroup testGroup,
	        Boolean isSpecimenRequired, Concept referenceConcept, boolean includeRetired) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTest> searchLabTests(LabTestType labTestType, Patient patient, LabTestSample sample,
	        String orderNumber, String referenceNumber, Concept orderConcept, Provider orderer, Date from, Date to,
	        boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
}
