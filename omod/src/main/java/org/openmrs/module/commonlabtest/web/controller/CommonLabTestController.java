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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Order;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This class configured as controller using annotation and mapped with the URL of
 * 'module/${rootArtifactid}/${rootArtifactid}Link.form'.
 */
@Controller("${rootrootArtifactid}.CommonLabTestController")
public class CommonLabTestController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	CommonLabTestService labTestService;
	
	/** Success form view name */
	private final String VIEW = "/module/${rootArtifactid}/${rootArtifactid}";
	
	/**
	 * Initially called after the getUsers method to get the landing form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	protected String onGet() {
		return VIEW;
	}
	
	/**
	 * All the parameters are optional based on the necessity
	 * 
	 * @param httpSession
	 * @param anyRequestObject
	 * @param errors
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	protected String onPost(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject,
	        BindingResult errors) {
		if (errors.hasErrors()) {
			// return error view
		}
		return VIEW;
	}
	
	/**
	 * This class returns the form backing object. This can be a string, a boolean, or a normal java
	 * pojo. The bean name defined in the ModelAttribute annotation and the type can be just defined
	 * by the return type of this method
	 */
	@ModelAttribute("labtesttypes")
	protected List<LabTestType> getLabTestTypes() throws Exception {
		List<LabTestType> users = labTestService.getAllLabTestTypes(false);
		// this object will be made available to the jsp page under the variable name
		// that is defined in the @ModuleAttribute tag
		return users;
	}
	
	protected List<LabTestAttributeType> getAllLabTestAttributeTypes(Boolean includeRetired) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestType> getAllLabTestTypes(Boolean includeRetired) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTest getEarliestLabTest(Patient patient) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestSample getEarliestLabTestSample(Patient patient) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestSample getEarliestAcceptedLabTestSample(Patient patient) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestSample getEarliestCollectedLabTestSample(Patient patient) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestSample getEarliestProcessedLabTestSample(Patient patient) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestSample getEarliestRejectedLabTestSample(Patient patient) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestAttribute getLabTestAttribute(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, Patient patient,
	        String valueReference, Date from, Date to, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, boolean includeVoided)
	        throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestAttribute> getLabTestAttributes(Patient patient, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestAttribute> getLabTestAttributes(Patient patient, LabTestAttributeType labTestAttributeType,
	        boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestAttributeType getLabTestAttributeType(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestAttributeType> getLabTestAttributeTypes(String name, String datatypeClassname,
	        boolean includeRetired) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTest getLabTestByUuid(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTest getLabTest(Encounter orderEncounter) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTest getLabTest(Order order) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestSample getLabTestSample(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestSample> getLabTestSamples(LabTest labTest, Patient patient, LabTestSampleStatus status,
	        String labSampleIdentifier, String orderNumber, String labReferenceNumber, String specimenName,
	        Provider collector, Date from, Date to, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestSample> getLabTestSamples(String labSampleIdentifier, String orderNumber,
	        String labReferenceNumber, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestSample> getLabTestSamples(LabTest labTest, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestSample> getLabTestSamples(Provider collector, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestSample> getLabTestSamples(Order order, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestSample> getLabTestSamples(LabTestSampleStatus status, Date from, Date to, boolean includeVoided)
	        throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestType getLabTestType(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestType> getLabTestTypes(String name, String shortName, LabTestGroup testGroup,
	        Boolean isSpecimenRequired, Concept referenceConcept, boolean includeRetired) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTest> getLabTests(LabTestType labTestType, Patient patient, LabTestSample sample, String orderNumber,
	        String referenceNumber, Concept orderConcept, Provider orderer, Date from, Date to, boolean includeVoided)
	        throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTest> getLabTests(LabTestType labTestType, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTest> getLabTests(Concept orderConcept, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTest> getLabTests(Provider orderer, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTest> getLabTests(Patient patient, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTest> getLabTests(String referenceNumber, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTest getLatestLabTest(Patient patient) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestSample getLatestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTest saveLabTest(LabTest labTest) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTest saveLabTest(LabTest labTest, LabTestSample labTestSample,
	        Collection<LabTestAttribute> labTestAttributes) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestAttribute> saveLabTestAttributes(List<LabTestAttribute> labTestAttributes) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestSample saveLabTestSample(LabTestSample labTestSample) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestType saveLabTestType(LabTestType labTestType) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void retireLabTestType(LabTestType labTestType) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void retireLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void unretireLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void unretireLabTestType(LabTestType labTestType) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void voidLabTest(LabTest labTest) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void voidLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void voidLabTestSample(LabTestSample labTestSample) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void unvoidLabTest(LabTest labTest) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void unvoidLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void unvoidLabTestSample(LabTestSample labTestSample) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void deleteLabTest(LabTest labTest) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void deleteLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void deleteLabTestSample(LabTestSample labTestSample) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void deleteLabTestType(LabTestType labTestType) throws APIException {
		// TODO Auto-generated method stub
	}
	
}
