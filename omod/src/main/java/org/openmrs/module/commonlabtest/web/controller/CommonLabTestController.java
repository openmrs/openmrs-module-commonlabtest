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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.module.commonlabtest.CommonLabTestConfig;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.LabTestType.LabTestGroup;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class configured as controller using annotation and mapped with the URL
 * of 'module/${rootArtifactid}/${rootArtifactid}Link.form'.
 */
@Controller
// ("${rootrootArtifactid}.CommonLabTestController")
@RequestMapping(value = "module/${rootArtifactid}/${rootArtifactid}/")
public class CommonLabTestController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	CommonLabTestService service;

	/** Success form view name */
	private final String VIEW = "/module/${rootArtifactid}/${rootArtifactid}";

	/**
	 * Initially called after the getUsers method to get the landing form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(value = "commonlabtest", method = RequestMethod.GET)
	public String onGet() {
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
	public String onPost(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject,
			BindingResult errors) {
		if (errors.hasErrors()) {
		}
		return VIEW;
	}

	/**
	 * Returns a list of {@link LabTestGroup} enum values as String list
	 * 
	 * @return
	 */
	@RequestMapping(value = "labTestGroups", method = RequestMethod.GET)
	public List<String> getLabTestGroups() {
		LabTestGroup[] values = LabTestGroup.values();
		List<String> list = new ArrayList<String>();
		for (LabTestGroup group : values) {
			list.add(group.toString());
		}
		return list;
	}

	/**
	 * Returns a list of {@link LabTestSampleStatus} enum values as String list
	 * 
	 * @return
	 */
	@RequestMapping(value = "labTestSampleStatus", method = RequestMethod.GET)
	public List<String> getLabTestSampleStatuses() {
		LabTestSampleStatus[] values = LabTestSampleStatus.values();
		List<String> list = new ArrayList<String>();
		for (LabTestSampleStatus group : values) {
			list.add(group.toString());
		}
		return list;
	}

	/******************************************************/
	/******************* LAB TEST TYPES *******************/
	/******************************************************/

	/**
	 * Returns list of active (unvoided) {@link LabTestType} objects
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "labTestTypes", method = RequestMethod.GET)
	protected List<LabTestType> getAllLabTestTypes() throws Exception {
		List<LabTestType> list = service.getAllLabTestTypes(Boolean.FALSE);
		return list;
	}

	/**
	 * Searches for {@link LabTestType} objects by given non-null parameters. At
	 * least one parameter must be defined
	 * 
	 * @param name
	 * @param group
	 * @param retired
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findLabTestTypes", method = RequestMethod.GET)
	protected List<LabTestType> getLabTestTypes(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "group", required = true) LabTestGroup group,
			@RequestParam(value = "retired", required = true) Boolean retired) throws Exception {
		List<LabTestType> list = service.getLabTestTypes(name, null, group, null, null, retired);
		return list;
	}

	/**
	 * Returns {@link LabTestType} objects by generated Id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "labTestType", method = RequestMethod.GET)
	protected LabTestType getLabTestType(@RequestParam(value = "id", required = true) Integer id) throws Exception {
		return service.getLabTestType(id);
	}

	/**
	 * Saves {@link LabTestType} object
	 * 
	 * @param labTestId
	 * @param sample
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "labTestType", method = RequestMethod.POST)
	protected LabTestSample saveLabTestType(@RequestParam(value = "sample") LabTestSample sample) throws Exception {
		return service.saveLabTestSample(sample);
	}

	/**
	 * Retires an active {@link LabTestType} object
	 * 
	 * @param labTestType
	 * @param reason
	 * @throws Exception
	 */
	@RequestMapping(value = "retireLabTestType", method = RequestMethod.POST)
	protected void retireLabTestType(@RequestParam(value = "labTestType", required = true) LabTestType labTestType,
			@RequestParam(value = "reason", required = true) String reason) throws Exception {
		if ("".equals(reason)) {
			throw new Exception("Cannot retire the object. Provide a reason.");
		}
		service.retireLabTestType(labTestType, reason);
	}

	/**
	 * Activates a retired {@link LabTestType} object
	 * 
	 * @param labTestType
	 * @param reason
	 * @throws Exception
	 */
	@RequestMapping(value = "unretireLabTestType", method = RequestMethod.POST)
	protected void unretireLabTestType(@RequestParam(value = "labTestType", required = true) LabTestType labTestType)
			throws Exception {
		service.unretireLabTestType(labTestType);
	}

	/**
	 * Deletes {@link LabTestType} object. Only a retired object can be deleted.
	 * 
	 * @param labTestType
	 * @throws Exception
	 */
	@RequestMapping(value = "labTestType", method = RequestMethod.DELETE)
	protected void deleteLabTestType(@RequestParam(value = "labTest", required = true) LabTestType labTestType)
			throws Exception {
		if (!labTestType.getRetired()) {
			throw new Exception("Unable to delete the object. Make sure that the object is retired/voided first.");
		}
		service.deleteLabTestType(labTestType);
	}

	/******************************************************/
	/*************** LAB TEST ATTRIBUTE TYPES *************/
	/******************************************************/

	/**
	 * Returns list of all {@link LabTestAttributeType} objects matching parameters
	 * 
	 * @param name
	 * @param includeRetired
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "labTestAttributeTypes", method = RequestMethod.GET)
	protected List<LabTestAttributeType> getLabTestAttributeTypes(@RequestParam(value = "name") String name,
			@RequestParam(value = "retired") Boolean includeRetired) throws Exception {
		if (name == null && includeRetired == null) {
			return service.getAllLabTestAttributeTypes(false);
		}
		service.getAllLabTestAttributeTypes(false);
		return service.getLabTestAttributeTypes(name, null, includeRetired);
	}

	/**
	 * Returns {@link LabTestAttributeType} objects by generated Id
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "labTestAttributeType", method = RequestMethod.GET)
	protected LabTestAttributeType getLabTestAttributeType(@RequestParam(value = "id", required = true) Integer id)
			throws Exception {
		return service.getLabTestAttributeType(id);
	}

	/**
	 * Saves {@link LabTestAttributeType} object
	 * 
	 * @param labTestId
	 * @param sample
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "labTestAttributeType", method = RequestMethod.POST)
	protected LabTestAttributeType saveLabTestAttributeType(
			@RequestParam(value = "labTestAttributeType", required = true) LabTestAttributeType labTestAttributeType)
			throws Exception {
		return service.saveLabTestAttributeType(labTestAttributeType);
	}

	/**
	 * Retires a {@link LabTestAttributeType} object
	 * 
	 * @param labTestAttributeType
	 * @param reason
	 * @throws Exception
	 */
	@RequestMapping(value = "retireLabTestAttributeType", method = RequestMethod.POST)
	protected void retireLabTestAttributeType(
			@RequestParam(value = "labTestAttributeType", required = true) LabTestAttributeType labTestAttributeType,
			@RequestParam(value = "reason", required = true) String reason) throws Exception {
		if ("".equals(reason)) {
			throw new Exception("Cannot retire the object. Provide a reason.");
		}
		service.retireLabTestAttributeType(labTestAttributeType, reason);
	}

	/**
	 * Revives back a retired {@link LabTestAttributeType} object
	 * 
	 * @param labTestAttributeType
	 * @param reason
	 * @throws Exception
	 */
	@RequestMapping(value = "unretireLabTestAttributeType", method = RequestMethod.POST)
	protected void unretireLabTestAttributeType(
			@RequestParam(value = "labTestAttributeType", required = true) LabTestAttributeType labTestAttributeType,
			@RequestParam(value = "reason", required = true) String reason) throws Exception {
		if ("".equals(reason)) {
			throw new Exception("Cannot retire the object. Provide a reason.");
		}
		service.unretireLabTestAttributeType(labTestAttributeType);
	}

	/**
	 * Deletes {@link LabTestAttributeType} object
	 * 
	 * @param labTestAttributeType
	 * @throws Exception
	 */
	@RequestMapping(value = "labTestAttributeType", method = RequestMethod.DELETE)
	protected void deleteLabTestAttributeType(
			@RequestParam(value = "labTestAttributeType", required = true) LabTestAttributeType labTestAttributeType)
			throws Exception {
		service.deleteLabTestAttributeType(labTestAttributeType);
	}

	/******************************************************/
	/****************** LAB TEST SAMPLES ******************/
	/******************************************************/

	/**
	 * Returns {@link LabTestSample} objects by generated Id
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "labTestSample", method = RequestMethod.GET)
	protected LabTestSample getLabTestSample(@RequestParam(value = "id", required = true) Integer id) throws Exception {
		return service.getLabTestSample(id);
	}

	/**
	 * Returns list of {@link LabTestSample} objects by given {@link LabTest} object
	 * 
	 * @param labTest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "labTestSamples", method = RequestMethod.GET)
	protected List<LabTestSample> getLabTestSamples(@RequestParam(value = "labTest", required = true) LabTest labTest)
			throws Exception {
		return service.getLabTestSamples(labTest, false);
	}

	/**
	 * Searches for {@link LabTestType} objects by given non-null parameters. At one
	 * parameter must be defined; parameters <code>status</code> must coexist with
	 * <code>from and to</code>
	 * 
	 * @param patient
	 * @param labTest
	 * @param status
	 * @param labSampleIdentifier
	 * @param collector
	 * @param from
	 * @param to
	 * @param includeVoided
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findLabTestSamples", method = RequestMethod.GET)
	protected List<LabTestSample> getLabTestSamples(@RequestParam(value = "patient") Patient patient,
			@RequestParam(value = "labTest") LabTest labTest,
			@RequestParam(value = "status") LabTestSampleStatus status,
			@RequestParam(value = "identifier") String labSampleIdentifier,
			@RequestParam(value = "collector") Provider collector, @RequestParam(value = "from") Date from,
			@RequestParam(value = "to") Date to, @RequestParam(value = "voided", required = true) Boolean includeVoided)
			throws Exception {
		boolean patientOnly = (patient != null) && (labTest == null) && (status == null)
				&& (labSampleIdentifier == null) && (collector == null) && (from == null);
		boolean labTestOnly = (labTest != null) && (patient == null) && (status == null)
				&& (labSampleIdentifier == null) && (collector == null) && (from == null);
		boolean collectorOnly = (collector != null) && (labTest == null) && (patient == null) && (status == null)
				&& (labSampleIdentifier == null) && (from == null);
		if (includeVoided == null) {
			includeVoided = Boolean.FALSE;
		}
		if (patientOnly) {
			return service.getLabTestSamples(labTest, includeVoided);
		}
		if (labTestOnly) {
			return service.getLabTestSamples(patient, includeVoided);
		}
		if (collectorOnly) {
			return service.getLabTestSamples(collector, includeVoided);
		}
		if (from != null || to != null) {
			if (from == null || to == null || status == null) {
				throw new Exception(
						"Unable to find LabTestSamples. When searching by date range, all 3 of the parameters: from, to and status must be provided.");
			}
			// Swap dates if difference is negative
			if (from.compareTo(to) > 0) {
				Date tmp = from;
				from = to;
				to = tmp;
			}
			return service.getLabTestSamples(status, from, to, includeVoided);
		}
		return service.getLabTestSamples(labTest, patient, status, labSampleIdentifier, collector, from, to,
				includeVoided);
	}

	/**
	 * Saves {@link LabTestSample} object
	 * 
	 * @param model
	 * @param labTestId
	 * @param sample
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "labTestSample", method = RequestMethod.POST)
	protected LabTestSample saveLabTestSample(@RequestParam(value = "labtestid", required = true) Integer labTestId,
			@RequestParam(value = "sample", required = true) LabTestSample sample) throws Exception {
		LabTest labTest = service.getLabTest(labTestId);
		if (labTest == null) {
			throw new Exception("Unable to find LabTest object matching given labTestId.");
		}
		if (labTest.getLabTestType().getRequiresSpecimen()) {
			throw new Exception("Cannot save the object. Associated LabTestType does not accept LabTestSample.");
		}
		return service.saveLabTestSample(sample);
	}

	/**
	 * Sets {@link LabTestSampleStatus} to ACCEPTED and saves the
	 * {@link LabTestSample} object
	 * 
	 * @param labTestSample
	 */
	@RequestMapping(value = "acceptLabTestSample", method = RequestMethod.POST)
	protected void acceptLabTestSample(@RequestParam(value = "sample") LabTestSample labTestSample) {
		labTestSample.setStatus(LabTestSampleStatus.ACCEPTED);
		service.saveLabTestSample(labTestSample);
	}

	/**
	 * Sets {@link LabTestSampleStatus} to REJECTED and saves the
	 * {@link LabTestSample} object. Also voids the object if
	 * AUTO_VOID_REJECTED_SAMPLES property is true in {@link CommonLabTestConfig}
	 * 
	 * @param labTestSample
	 */
	@RequestMapping(value = "rejectLabTestSample", method = RequestMethod.POST)
	protected void rejectLabTestSample(@RequestParam(value = "sample") LabTestSample labTestSample) {
		labTestSample.setStatus(LabTestSampleStatus.REJECTED);
		service.saveLabTestSample(labTestSample);
		if (CommonLabTestConfig.AUTO_VOID_REJECTED_SAMPLES) {
			service.voidLabTestSample(labTestSample, "Sample " + LabTestSampleStatus.REJECTED);
		}
	}

	/**
	 * Deletes {@link LabTestSample} object
	 * 
	 * @param labTestSample
	 */
	@RequestMapping(value = "labTest", method = RequestMethod.DELETE)
	protected void deleteLabTestSample(
			@RequestParam(value = "labTestSample", required = true) LabTestSample labTestSample) throws Exception {
		service.deleteLabTestSample(labTestSample);
	}

	/******************************************************/
	/********************** LAB TESTS *********************/
	/******************************************************/

	@RequestMapping(value = "labTest", method = RequestMethod.GET)
	protected LabTest getLabTest(Integer id) {
		// TODO
		return null;
	}

	@RequestMapping(value = "findLabTests", method = RequestMethod.GET)
	protected List<LabTest> getLabTests() {
		// TODO
		return null;
	}

	@RequestMapping(value = "labTest", method = RequestMethod.POST)
	protected LabTest saveLabTest() {
		// TODO
		return null;
	}

	@RequestMapping(value = "completeLabTest", method = RequestMethod.POST)
	protected LabTest completeLabTest() {
		// Set sample status to PROCESSED
		// Any other actions (e.g. closing Order)
		// TODO
		return null;
	}

	@RequestMapping(value = "labTest", method = RequestMethod.DELETE)
	protected void deleteLabTest() {
		// TODO
	}

	/******************************************************/
	/******************* LAB TEST RESULTS *****************/
	/******************************************************/

	@RequestMapping(value = "labTest", method = RequestMethod.GET)
	protected LabTestAttribute getLabTestResult(Integer id) {
		// TODO
		return null;
	}

	@RequestMapping(value = "labTest", method = RequestMethod.GET)
	protected List<LabTestAttribute> getLabTestResults(Integer labTestId) {
		// TODO
		return null;
	}

	@RequestMapping(value = "findLabTests", method = RequestMethod.GET)
	protected List<LabTestAttribute> getLabTestResults() {
		// TODO
		return null;
	}

	@RequestMapping(value = "labTest", method = RequestMethod.POST)
	protected LabTestAttribute saveLabTestResult() {
		// TODO
		return null;
	}

	@RequestMapping(value = "labTest", method = RequestMethod.POST)
	protected List<LabTestAttribute> saveLabTestResults(List<LabTestAttribute> labTestResults) {
		// TODO
		return null;
	}

	@RequestMapping(value = "labTest", method = RequestMethod.DELETE)
	protected void deleteLabTestResult() {
		// TODO
	}
}
