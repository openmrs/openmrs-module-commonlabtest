/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.commonlabtest.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.commonlabtest.CommonLabTestActivator;
import org.openmrs.module.commonlabtest.CommonLabTestConfig;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestGroup;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.api.dao.CommonLabTestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonLabTestServiceImpl extends BaseOpenmrsService implements CommonLabTestService {

	@Autowired
	CommonLabTestDAO dao;

	/**
	 * Injected in moduleApplicationContext.xml
	 * 
	 * @param dao the {@link CommonLabTestDAO} object
	 */
	public void setDao(CommonLabTestDAO dao) {
		this.dao = dao;
	}

	/*
	 * 
	 * 
	 * @see CommonLabTestService#getAllLabTestAttributeTypes(boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttributeType> getAllLabTestAttributeTypes(boolean includeRetired) throws APIException {
		return dao.getAllLabTestAttributeTypes(includeRetired);
	}

	/*
	 * 
	 * 
	 * @see CommonLabTestService#getAllLabTestTypes(boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestType> getAllLabTestTypes(boolean includeRetired) throws APIException {
		return dao.getAllLabTestTypes(includeRetired);
	}

	/*
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#
	 * getSpecimenTypeConcepts()
	 */
	// TODO: Add unit test for this method
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_SAMPLE_PRIVILEGE)
	public List<Concept> getSpecimenTypeConcepts() {
		String uuid = Context.getAdministrationService()
		        .getGlobalProperty(CommonLabTestActivator.SPECIMEN_TYPE_CONCEPT_UUID);
		Concept concept = Context.getConceptService().getConceptByUuid(uuid);
		Collection<ConceptAnswer> answers = concept.getAnswers();
		List<Concept> members = new ArrayList<Concept>();
		for (Iterator<ConceptAnswer> iter = answers.iterator(); iter.hasNext();) {
			members.add(iter.next().getAnswerConcept());
		}
		return members;
	}

	/*
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#
	 * getSpecimenSiteConcepts()
	 */
	// TODO: Add unit test for this method
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_SAMPLE_PRIVILEGE)
	public List<Concept> getSpecimenSiteConcepts() {
		String uuid = Context.getAdministrationService()
		        .getGlobalProperty(CommonLabTestActivator.SPECIMEN_SITE_CONCEPT_UUID);
		Concept concept = Context.getConceptService().getConceptByUuid(uuid);
		Collection<ConceptAnswer> answers = concept.getAnswers();
		List<Concept> members = new ArrayList<Concept>();
		for (Iterator<ConceptAnswer> iter = answers.iterator(); iter.hasNext();) {
			members.add(iter.next().getAnswerConcept());
		}
		return members;
	}

	/*
	 * @see CommonLabTestService#getEarliestLabTest(org.openmrs.Patient)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTest getEarliestLabTest(Patient patient) throws APIException {
		List<LabTest> labTests = dao.getNLabTests(patient, 1, true, false, false);
		if (!labTests.isEmpty()) {
			return labTests.get(0);
		}
		return null;
	}

	/*
	 * @see CommonLabTestService#getEarliestLabTestSample(Patient,
	 * LabTestSampleStatus)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_SAMPLE_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestSample getEarliestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException {
		List<LabTestSample> labTestSamples = dao.getNLabTestSamples(patient, status, 1, true, false, false);
		if (!labTestSamples.isEmpty()) {
			return labTestSamples.get(0);
		}
		return null;
	}

	/*
	 * @see CommonLabTestService#getLabTest(Integer)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTest getLabTest(Integer labTestId) throws APIException {
		LabTest labTest = dao.getLabTest(labTestId);
		labTest.setOrder(Context.getOrderService().getOrder(labTest.getTestOrderId()));
		return labTest;
	}

	/*
	 * @see CommonLabTestService#getLabTestAttribute(java.lang.Integer)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestAttribute getLabTestAttribute(Integer labTestAttributeId) throws APIException {
		return dao.getLabTestAttribute(labTestAttributeId);
	}

	/*
	 * @see CommonLabTestService#getLabTestAttributeByUuid(String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestAttribute getLabTestAttributeByUuid(String uuid) throws APIException {
		return dao.getLabTestAttributeByUuid(uuid);
	}

	/*
	 * @see CommonLabTestService#getLabTestAttributes(LabTestAttributeType, String,
	 * Date, Date, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, String valueReference,
	        Date from, Date to, boolean includeVoided) throws APIException {
		return dao.getLabTestAttributes(labTestAttributeType, valueReference, from, to, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTestAttributes(LabTestAttributeType, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, boolean includeVoided)
	        throws APIException {
		return getLabTestAttributes(labTestAttributeType, null, null, null, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTestAttributes(LabTestAttributeType, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttribute> getLabTestAttributes(Integer testOrderId) throws APIException {
		return dao.getLabTestAttributes(testOrderId);
	}

	/*
	 * @see CommonLabTestService#getLabTestAttributes(org.openmrs.Patient, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttribute> getLabTestAttributes(Patient patient, boolean includeVoided) throws APIException {
		return dao.getLabTestAttributes(patient, null, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTestAttributes(Patient, LabTestAttributeType,
	 * boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttribute> getLabTestAttributes(Patient patient, LabTestAttributeType labTestAttributeType,
	        boolean includeVoided) throws APIException {
		return dao.getLabTestAttributes(patient, labTestAttributeType, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTestAttributeType(Integer)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId) throws APIException {
		return dao.getLabTestAttributeType(labTestAttributeTypeId);
	}

	/*
	 * @see CommonLabTestService#getLabTestAttributeTypeByUuid(String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestAttributeType getLabTestAttributeTypeByUuid(String uuid) throws APIException {
		return dao.getLabTestAttributeTypeByUuid(uuid);
	}

	/*
	 * @see CommonLabTestService#getLabTestAttributeTypes(String, String, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttributeType> getLabTestAttributeTypes(String name, String datatypeClassname, boolean includeRetired)
	        throws APIException {
		return dao.getLabTestAttributeTypes(name, datatypeClassname, includeRetired);
	}

	/*
	 * @see CommonLabTestService#getLabTestAttributeTypes(LabTestType, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttributeType> getLabTestAttributeTypes(LabTestType labTestType, boolean includeRetired)
	        throws APIException {
		return dao.getLabTestAttributeTypes(labTestType, includeRetired);
	}

	/*
	 * @see CommonLabTestService#getLabTestByUuid(String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTest getLabTestByUuid(String uuid) throws APIException {
		return dao.getLabTestByUuid(uuid);
	}

	/*
	 * @see CommonLabTestService#getLabTest(org.openmrs.Order)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTest getLabTest(Order order) throws APIException {
		return dao.getLabTest(order);
	}

	/*
	 * @see CommonLabTestService#getLabTestSample(Integer)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_SAMPLE_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestSample getLabTestSample(Integer labTestSampleId) throws APIException {
		return dao.getLabTestSample(labTestSampleId);
	}

	/*
	 * @see CommonLabTestService#getLabTestSampleByUuid(String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_SAMPLE_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestSample getLabTestSampleByUuid(String uuid) throws APIException {
		return dao.getLabTestSampleByUuid(uuid);
	}

	/*
	 * @see CommonLabTestService#getLabTestSamples(LabTest, Patient,
	 * LabTestSampleStatus, String, Provider, Date, Date, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_SAMPLE_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestSample> getLabTestSamples(LabTest labTest, Patient patient, LabTestSampleStatus status,
	        String labSampleIdentifier, Provider collector, Date from, Date to, boolean includeVoided) throws APIException {
		// TODO
		return null;
	}

	/*
	 * @see CommonLabTestService#getLabTestSamples(String, String, String, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_SAMPLE_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestSample> getLabTestSamples(String labSampleIdentifier, String orderNumber, String labReferenceNumber,
	        boolean includeVoided) throws APIException {
		return getLabTestSamples(null, null, null, labSampleIdentifier, null, null, null, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTestSamples(LabTest, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_SAMPLE_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestSample> getLabTestSamples(LabTest labTest, boolean includeVoided) throws APIException {
		return dao.getLabTestSamples(labTest, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTestSamples(Patient, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_SAMPLE_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestSample> getLabTestSamples(Patient patient, boolean includeVoided) throws APIException {
		return dao.getLabTestSamples(patient, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTestSamples(Provider, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_SAMPLE_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestSample> getLabTestSamples(Provider collector, boolean includeVoided) throws APIException {
		return dao.getLabTestSamples(collector, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTestSamples(LabTestSampleStatus, Date, Date,
	 * boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_SAMPLE_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestSample> getLabTestSamples(LabTestSampleStatus status, Date from, Date to, boolean includeVoided)
	        throws APIException {
		return getLabTestSamples(null, null, status, null, null, from, to, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTestType(Integer)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestType getLabTestType(Integer labTestTypeId) throws APIException {
		return dao.getLabTestType(labTestTypeId);
	}

	/*
	 * @see CommonLabTestService#getLabTestTypeByUuid(String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestType getLabTestTypeByUuid(String uuid) throws APIException {
		return dao.getLabTestTypeByUuid(uuid);
	}

	/*
	 * @see CommonLabTestService#getLabTestTypes(String, String, LabTestGroup,
	 * Boolean, Concept, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestType> getLabTestTypes(String name, String shortName, LabTestGroup testGroup,
	        final Boolean isSpecimenRequired, Concept referenceConcept, boolean includeRetired) throws APIException {
		List<LabTestType> labTestTypes = dao.getLabTestTypes(name, shortName, testGroup, referenceConcept, includeRetired);
		if (isSpecimenRequired != null) {
			for (Iterator<LabTestType> iterator = labTestTypes.iterator(); iterator.hasNext();) {
				LabTestType labTestType = iterator.next();
				if (!labTestType.getRequiresSpecimen().equals(isSpecimenRequired)) {
					iterator.remove();
				}
			}
		}
		return labTestTypes;
	}

	/*
	 * @see CommonLabTestService#getLabTests(LabTestType, Patient, String, String,
	 * Concept, Provider, Date, Date, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(LabTestType labTestType, Patient patient, String orderNumber, String referenceNumber,
	        Concept orderConcept, Provider orderer, Date from, Date to, boolean includeVoided) throws APIException {
		return dao.getLabTests(labTestType, patient, orderNumber, referenceNumber, orderConcept, orderer, from, to,
		    includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTests(org.openmrs.module.commonlabtest.
	 * LabTestType, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(LabTestType labTestType, boolean includeVoided) throws APIException {
		return getLabTests(labTestType, null, null, null, null, null, null, null, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTests(org.openmrs.Concept, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(Concept orderConcept, boolean includeVoided) throws APIException {
		return getLabTests(null, null, null, null, orderConcept, null, null, null, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTests(org.openmrs.Provider, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(Provider orderer, boolean includeVoided) throws APIException {
		return getLabTests(null, null, null, null, null, orderer, null, null, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTests(org.openmrs.Patient, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(Patient patient, boolean includeVoided) throws APIException {
		return dao.getLabTests(null, patient, null, null, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLabTests(String, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(String referenceNumber, boolean includeVoided) throws APIException {
		if (referenceNumber.length() < 4) {
			throw new APIException("Reference number to search should at least be 4 character long.");
		}
		return getLabTests(null, null, null, referenceNumber, null, null, null, null, includeVoided);
	}

	/*
	 * @see CommonLabTestService#getLatestLabTest(Patient)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTest getLatestLabTest(Patient patient) throws APIException {
		List<LabTest> labTests = dao.getNLabTests(patient, 1, false, true, false);
		if (!labTests.isEmpty()) {
			return labTests.get(0);
		}
		return null;
	}

	/*
	 * @see CommonLabTestService#getLatestLabTestSample(Patient,
	 * LabTestSampleStatus)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestSample getLatestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException {
		List<LabTestSample> labTestSamples = dao.getNLabTestSamples(patient, status, 1, false, true, false);
		if (!labTestSamples.isEmpty()) {
			return labTestSamples.get(0);
		}
		return null;
	}

	/*
	 * @see CommonLabTestService#saveLabTest(LabTest)
	 */
	@Override
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	public LabTest saveLabTest(LabTest labTest) throws APIException {
		return saveLabTest(labTest, null, null);
	}

	/*
	 * @see CommonLabTestService#saveLabTest(LabTest, LabTestSample, Collection)
	 */
	@Override
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	public LabTest saveLabTest(LabTest labTest, LabTestSample labTestSample, Collection<LabTestAttribute> labTestAttributes)
	        throws APIException {
		// Check mandatory fields
		if (labTest.getOrder() == null) {
			throw new APIException("org.openmrs.Order");
		}
		if (labTest.getOrder().getEncounter() == null) {
			throw new APIException("org.openmrs.Encounter");
		}
		if (labTest.getOrder().getConcept() == null) {
			throw new APIException("org.openmrs.Concept");
		}
		if (labTest.getOrder().getOrderer() == null) {
			throw new APIException("org.openmrs.Orderer");
		}
		// Order object is saved via DAO object
		// Save Order
		LabTest savedLabTest = dao.saveLabTest(labTest);
		// Save sample if present
		if (labTestSample != null) {
			LabTestSample saveLabTestSample = saveLabTestSample(labTestSample);
			labTest.removeLabTestSample(labTestSample);
			labTest.addLabTestSample(saveLabTestSample);
		}
		// Save attributes if present
		if (labTestAttributes != null) {
			Set<LabTestAttribute> attributes = new HashSet<LabTestAttribute>();
			for (LabTestAttribute labTestAttribute : labTestAttributes) {
				attributes.add(saveLabTestAttribute(labTestAttribute));
			}
			savedLabTest.setAttributes(attributes);
		}
		return savedLabTest;
	}

	/*
	 * @see CommonLabTestService#saveLabTestAttribute(LabTestAttribute)
	 */
	@Override
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	public LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		return dao.saveLabTestAttribute(labTestAttribute);
	}

	/*
	 * @see CommonLabTestService#saveLabTestAttributes(List)
	 */
	@Override
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	public List<LabTestAttribute> saveLabTestAttributes(List<LabTestAttribute> labTestAttributes) throws APIException {
		for (LabTestAttribute labTestAttribute : labTestAttributes) {
			saveLabTestAttribute(labTestAttribute);
		}
		return labTestAttributes;
	}

	/*
	 * @see CommonLabTestService#saveLabTestAttributeType(LabTestAttributeType)
	 */
	@Override
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		return dao.saveLabTestAttributeType(labTestAttributeType);
	}

	/*
	 * @see CommonLabTestService#saveLabTestSample(LabTestSample)
	 */
	@Override
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_SAMPLE_PRIVILEGE)
	@Transactional
	public LabTestSample saveLabTestSample(LabTestSample labTestSample) throws APIException {
		return dao.saveLabTestSample(labTestSample);
	}

	/*
	 * @see CommonLabTestService#saveLabTestType(LabTestType)
	 */
	@Override
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public LabTestType saveLabTestType(LabTestType labTestType) throws APIException {
		handleUnknownTestTypeOperation(labTestType);
		return dao.saveLabTestType(labTestType);
	}

	/**
	 * This method disallows any modification in Unknown {@link LabTestType}. This method must be called
	 * in before DML operations in any {@link LabTestType} object
	 * 
	 * @param labTestType
	 */
	private void handleUnknownTestTypeOperation(LabTestType labTestType) {
		if (labTestType.getUuid().equals(LabTestType.UNKNOWN_TEST_UUID)) {
			throw new APIException(
			        "The LabTestType: UNKNOWN " + LabTestType.UNKNOWN_TEST_UUID + " is mandatory, and cannot be altered.");
		}
	}

	/*
	 * @see CommonLabTestService#retireLabTestType(LabTestType, String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void retireLabTestType(LabTestType labTestType, String retireReason) throws APIException {
		handleUnknownTestTypeOperation(labTestType);
		if (labTestType.getRetired()) {
			throw new APIException("Object has alread been retired.");
		}
		labTestType.setRetired(Boolean.TRUE);
		labTestType.setRetiredBy(Context.getAuthenticatedUser());
		labTestType.setRetireReason(retireReason);
		dao.saveLabTestType(labTestType);
	}

	/*
	 * @see CommonLabTestService#retireLabTestAttributeType(LabTestAttributeType,
	 * String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void retireLabTestAttributeType(LabTestAttributeType labTestAttributeType, String retireReason)
	        throws APIException {
		if (labTestAttributeType.getRetired()) {
			throw new APIException("Object has alread been retired.");
		}
		labTestAttributeType.setRetired(Boolean.TRUE);
		labTestAttributeType.setRetiredBy(Context.getAuthenticatedUser());
		labTestAttributeType.setRetireReason(retireReason);
		dao.saveLabTestAttributeType(labTestAttributeType);
	}

	/*
	 * @see CommonLabTestService#unretireLabTestType(LabTestType)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void unretireLabTestType(LabTestType labTestType) throws APIException {
		labTestType.setRetired(Boolean.FALSE);
		labTestType.setRetireReason("Previously retired for reason: " + labTestType.getRetireReason());
		handleUnknownTestTypeOperation(labTestType);
		dao.saveLabTestType(labTestType);
	}

	/*
	 * @see CommonLabTestService#unretireLabTestAttributeType(LabTestAttributeType)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void unretireLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		labTestAttributeType.setRetired(Boolean.FALSE);
		labTestAttributeType.setRetireReason("Previously retired for reason: " + labTestAttributeType.getRetireReason());
		dao.saveLabTestAttributeType(labTestAttributeType);
	}

	/*
	 * @see CommonLabTestService#voidLabTest(LabTest, String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void voidLabTest(LabTest labTest, String voidReason) throws APIException {
		List<LabTestSample> labTestSamples = dao.getLabTestSamples(labTest, Boolean.FALSE);
		if (labTestSamples != null) {
			for (LabTestSample sample : labTestSamples) {
				voidLabTestSample(sample, voidReason);
			}
		}
		List<LabTestAttribute> labTestAttributes = dao.getLabTestAttributes(labTest.getId());
		if (labTestAttributes != null) {
			for (LabTestAttribute attribute : labTestAttributes) {
				voidLabTestAttribute(attribute, voidReason);
			}
		}
		labTest.setVoided(true);
		labTest.setVoidedBy(Context.getAuthenticatedUser());
		labTest.setVoidReason(voidReason);
		Context.getOrderService().voidOrder(labTest.getOrder(), voidReason);
		dao.saveLabTest(labTest);
	}

	/*
	 * @see CommonLabTestService#voidLabTestAttribute(LabTestAttribute, String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void voidLabTestAttribute(LabTestAttribute labTestAttribute, String voidReason) throws APIException {
		labTestAttribute.setVoided(true);
		labTestAttribute.setVoidedBy(Context.getAuthenticatedUser());
		labTestAttribute.setVoidReason(voidReason);
		labTestAttribute.setDateVoided(new Date());
		dao.saveLabTestAttribute(labTestAttribute);
	}

	/*
	 * @see CommonLabTestService#voidLabTestAttributes(LabTest, String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void voidLabTestAttributes(LabTest labTest, String voidReason) throws APIException {
		// Set the status of PROCESSED LabTestSample objects back to COLLECTED
		List<LabTestSample> labTestSamples = getLabTestSamples(labTest, true);
		for (LabTestSample labTestSample : labTestSamples) {
			if (labTestSample.getStatus() == LabTestSampleStatus.PROCESSED) {
				labTestSample.setStatus(LabTestSampleStatus.COLLECTED);
				labTestSample = dao.saveLabTestSample(labTestSample);
			}
		}
		// Void all attributes in the list
		List<LabTestAttribute> labTestAttributes = getLabTestAttributes(labTest.getTestOrderId());
		if (labTestAttributes != null) {
			for (LabTestAttribute labTestAttribute : labTestAttributes) {
				if (labTestAttribute.getVoided()) {
					continue;
				}
				voidLabTestAttribute(labTestAttribute, voidReason);
			}
		}
	}

	/*
	 * @see CommonLabTestService#voidLabTestSample(LabTestSample, String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_SAMPLE_PRIVILEGE)
	@Transactional
	public void voidLabTestSample(LabTestSample labTestSample, String voidReason) throws APIException {
		labTestSample.setVoided(true);
		labTestSample.setVoidedBy(Context.getAuthenticatedUser());
		labTestSample.setVoidReason(voidReason);
		labTestSample.setDateVoided(new Date());
		dao.saveLabTestSample(labTestSample);
	}

	/*
	 * @see CommonLabTestService#unvoidLabTest(LabTest)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void unvoidLabTest(LabTest labTest) throws APIException {

		List<LabTestSample> labTestSamples = dao.getLabTestSamples(labTest, Boolean.TRUE);
		for (LabTestSample sample : labTestSamples) {
			unvoidLabTestSample(sample);
		}

		List<LabTestAttribute> labTestAttributes = dao.getLabTestAttributes(labTest.getId());
		for (LabTestAttribute attribute : labTestAttributes) {
			unvoidLabTestAttribute(attribute);
		}
		Context.getOrderService().unvoidOrder(labTest.getOrder());
		dao.saveLabTest(labTest);
	}

	/*
	 * @see CommonLabTestService#unvoidLabTestAttribute(LabTestAttribute)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void unvoidLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		labTestAttribute.setVoided(false);
		labTestAttribute.setVoidReason("Previously voided for reason: " + labTestAttribute.getVoidReason());
		dao.saveLabTestAttribute(labTestAttribute);
	}

	/*
	 * @see CommonLabTestService#unvoidLabTestSample(LabTestSample)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_SAMPLE_PRIVILEGE)
	@Transactional
	public void unvoidLabTestSample(LabTestSample labTestSample) throws APIException {
		labTestSample.setVoided(false);
		labTestSample.setVoidReason("Previously voided for reason: " + labTestSample.getVoidReason());
		dao.saveLabTestSample(labTestSample);
	}

	/*
	 * @see
	 * CommonLabTestService#deleteLabTest(org.openmrs.module.commonlabtest.LabTest)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void deleteLabTest(LabTest labTest) throws APIException {
		dao.purgeLabTest(labTest);
	}

	/*
	 * @see CommonLabTestService#deleteLabTestAttribute(LabTestAttribute)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void deleteLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		dao.purgeLabTestAttribute(labTestAttribute);
	}

	/*
	 * @see CommonLabTestService#deleteLabTestAttributeType(LabTestAttributeType)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		deleteLabTestAttributeType(labTestAttributeType, false);
	}

	/*
	 * @see CommonLabTestService#deleteLabTestAttributeType(LabTestAttributeType,
	 * boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType, boolean cascade) throws APIException {
		if (cascade) {
			List<LabTestAttribute> labTestAttributes = getLabTestAttributes(labTestAttributeType, true);
			for (LabTestAttribute labTestAttribute : labTestAttributes) {
				try {
					dao.purgeLabTestAttribute(labTestAttribute);
				}
				catch (Exception e) {
					throw new APIException(e.getMessage());
				}
			}
		}
		dao.purgeLabTestAttributeType(labTestAttributeType);
	}

	/*
	 * @see CommonLabTestService#deleteLabTestSample(LabTestSample)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void deleteLabTestSample(LabTestSample labTestSample) throws APIException {
		dao.purgeLabTestSample(labTestSample);
	}

	/*
	 * @see CommonLabTestService#deleteLabTestType(LabTestType)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void deleteLabTestType(LabTestType labTestType) throws APIException {
		deleteLabTestType(labTestType, null);
	}

	/*
	 * @see CommonLabTestService#deleteLabTestType(LabTestType, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	@Deprecated
	public void deleteLabTestType(LabTestType labTestType, boolean cascade) throws APIException {
		List<LabTest> labTests = getLabTests(labTestType, true);
		if (labTests == null) {
			dao.purgeLabTestType(labTestType);
		} else if (cascade) {
			for (LabTest labTest : labTests) {
				dao.purgeLabTest(labTest);
			}
			dao.purgeLabTestType(labTestType);
		} else {
			throw new APIException("Cannot delete LabTestType because of Foreign Key Violation.");
		}
	}

	/*
	 * @see CommonLabTestService#deleteLabTestType(LabTestType, LabTestType)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void deleteLabTestType(LabTestType labTestType, LabTestType newObjectForCascade) throws APIException {
		// Replace with Unknown Test Type object if null
		if (newObjectForCascade == null) {
			newObjectForCascade = getLabTestTypeByUuid(LabTestType.UNKNOWN_TEST_UUID);
		}
		StringBuilder message = new StringBuilder();
		message.append("Associated LabTestType: ");
		message.append(labTestType.getName());
		message.append("(");
		message.append(labTestType.getUuid());
		message.append(") was deleted on ");
		message.append(Context.getDateFormat().format(new Date()));
		// Replace LabTestType in dependencies
		handleLabTestTypeDependencies(labTestType, newObjectForCascade, message.toString());
		dao.purgeLabTestType(labTestType);
	}

	/**
	 * This method changes the {@link LabTestType} object in respective {@link LabTest} and
	 * {@link LabTestAttributeType} dependencies, and voides/retires them afterwards with given message
	 * 
	 * @param labTestType
	 * @param newObjectForCascade
	 * @param voidMessage
	 */
	private void handleLabTestTypeDependencies(LabTestType labTestType, LabTestType newObjectForCascade,
	        String voidMessage) {
		List<LabTest> labTests = getLabTests(labTestType, true);
		if (labTests != null) {
			for (LabTest labTest : labTests) {
				// Set new LabTestType
				labTest.setLabTestType(newObjectForCascade);
				saveLabTest(labTest);
				// Void with reason
				voidLabTest(labTest, voidMessage);
			}
		}
		List<LabTestAttributeType> testAttributeTypes = getLabTestAttributeTypes(labTestType, true);
		if (testAttributeTypes != null) {
			for (LabTestAttributeType attributeType : testAttributeTypes) {
				// Set new LabTestType
				attributeType.setLabTestType(newObjectForCascade);
				saveLabTestAttributeType(attributeType);
				// Retired with reason
				retireLabTestAttributeType(attributeType, voidMessage);
			}
		}
	}
}
