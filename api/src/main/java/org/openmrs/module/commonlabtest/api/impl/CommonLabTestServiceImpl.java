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

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OrderService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.commonlabtest.CommonLabTestConfig;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestGroup;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.dao.CommonLabTestDAO;
import org.springframework.transaction.annotation.Transactional;

public class CommonLabTestServiceImpl extends BaseOpenmrsService {
	
	CommonLabTestDAO dao;
	
	OrderService orderService;
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(CommonLabTestDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	/**
	 * Returns list of all LabTestAttributeType objects
	 * 
	 * @param includeRetired
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttributeType> getAllLabTestAttributeTypes(boolean includeRetired) throws APIException {
		return dao.getAllLabTestAttributeTypes(includeRetired);
	}
	
	/**
	 * Returns list of all objects of LabTestType
	 * 
	 * @param includeRetired
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestType> getAllLabTestTypes(boolean includeRetired) throws APIException {
		return dao.getAllLabTestTypes(includeRetired);
	}
	
	/**
	 * Returns first LabTest object by given Patient
	 * 
	 * @param patient
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTest getEarliestLabTest(Patient patient) throws APIException {
		List<LabTest> labTests = dao.getNLabTests(patient, 1, true, false, false);
		if (!labTests.isEmpty()) {
			return labTests.get(0);
		}
		return null;
	}
	
	/**
	 * Returns first LabTestSample object by given Patient and LabTestSampleStatus
	 * 
	 * @param patient
	 * @param status
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestSample getEarliestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException {
		List<LabTestSample> labTestSamples = dao.getNLabTestSamples(patient, 1, true, false, false);
		if (!labTestSamples.isEmpty()) {
			return labTestSamples.get(0);
		}
		return null;
	}
	
	/**
	 * Returns a LabTest object by generated ID
	 * 
	 * @param labTestId
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTest getLabTest(Integer labTestId) throws APIException {
		return dao.getLabTest(labTestId);
	}
	
	/**
	 * Returns a LabTestAttribute object by its generated ID
	 * 
	 * @param labTestAttributeTypeId
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestAttribute getLabTestAttribute(Integer labTestAttributeId) throws APIException {
		return dao.getLabTestAttribute(labTestAttributeId);
	}
	
	/**
	 * Returns a LabTestAttribute object by uuid. It can be called by any authenticated user. It is
	 * fetched in read only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestAttribute getLabTestAttributeByUuid(String uuid) throws APIException {
		return dao.getLabTestAttributeByUuid(uuid);
	}
	
	/**
	 * Get a list of LabTestAttribute objects using various parameters available. This is similar to
	 * getByExample(...) methods, except that instead of passing similar object, properties are
	 * passed as parameters
	 * 
	 * @param labTestAttributeType
	 * @param patient
	 * @param valueReference
	 * @param from
	 * @param to
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, Patient patient,
	        String valueReference, Date from, Date to, boolean includeVoided) throws APIException {
		return dao.getLabTestAttributes(labTestAttributeType, null, patient, valueReference, from, to, includeVoided);
	}
	
	/**
	 * @see List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType, Patient, String, Date,
	 *      Date, boolean)
	 */
	public List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, boolean includeVoided)
	        throws APIException {
		return getLabTestAttributes(labTestAttributeType, null, null, null, null, includeVoided);
	}
	
	/**
	 * @see List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType, Patient, String, Date,
	 *      Date, boolean)
	 */
	public List<LabTestAttribute> getLabTestAttributes(Patient patient, boolean includeVoided) throws APIException {
		return getLabTestAttributes(null, patient, null, null, null, includeVoided);
	}
	
	/**
	 * @see List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType, Patient, String, Date,
	 *      Date, boolean)
	 */
	public List<LabTestAttribute> getLabTestAttributes(Patient patient, LabTestAttributeType labTestAttributeType,
	        boolean includeVoided) throws APIException {
		return getLabTestAttributes(labTestAttributeType, patient, null, null, null, includeVoided);
	}
	
	/**
	 * Returns a LabTestAttributeType object by its generated ID
	 * 
	 * @param labTestAttributeTypeId
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId) throws APIException {
		return dao.getLabTestAttributeType(labTestAttributeTypeId);
	}
	
	/**
	 * Returns a LabTestAttributeType object by uuid. It can be called by any authenticated user. It
	 * is fetched in read only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestAttributeType getLabTestAttributeTypeByUuid(String uuid) throws APIException {
		return dao.getLabTestAttributeTypeByUuid(uuid);
	}
	
	/**
	 * Get a list of LabTestAttributeType objects using various parameters available. This is
	 * similar to getByExample(...) methods, except that instead of passing similar object,
	 * properties are passed as parameters
	 * 
	 * @param name
	 * @param datatypeClassname
	 * @param includeRetired
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttributeType> getLabTestAttributeTypes(String name, String datatypeClassname, boolean includeRetired)
	        throws APIException {
		return dao.getLabTestAttributeTypes(name, datatypeClassname, includeRetired);
	}
	
	/**
	 * Returns a LabTest object by uuid. It can be called by any authenticated user. It is fetched
	 * in read only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTest getLabTestByUuid(String uuid) throws APIException {
		return dao.getLabTestByUuid(uuid);
	}
	
	/**
	 * Returns a LabTest object by Encounter object in LabTest Order object
	 * 
	 * @param orderEncounter
	 * @return
	 * @throws APIException
	 */
	public LabTest getLabTest(Encounter orderEncounter) throws APIException {
		return dao.getLabTest(orderEncounter);
	}
	
	/**
	 * @see LabTest getLabTest(Encounter orderEncounter)
	 */
	public LabTest getLabTest(Order order) throws APIException {
		return getLabTest(order.getEncounter());
	}
	
	/**
	 * Returns a LabTestSample object by its generated ID
	 * 
	 * @param labTestSampleId
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestSample getLabTestSample(Integer labTestSampleId) throws APIException {
		return dao.getLabTestSample(labTestSampleId);
	}
	
	/**
	 * Returns a LabTestSample object by uuid. It can be called by any authenticated user. It is
	 * fetched in read only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestSample getLabTestSampleByUuid(String uuid) throws APIException {
		return dao.getLabTestSampleByUuid(uuid);
	}
	
	/**
	 * Get a list of LabTestSample objects using various parameters available. This is similar to
	 * getByExample(...) methods, except that instead of passing similar object, properties are
	 * passed as parameters
	 * 
	 * @param labTest
	 * @param patient
	 * @param labSampleIdentifier
	 * @param orderNumber
	 * @param labReferenceNumber
	 * @param specimenName
	 * @param status
	 * @param from
	 * @param to
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestSample> getLabTestSamples(LabTest labTest, Patient patient, LabTestSampleStatus status,
	        String labSampleIdentifier, String orderNumber, String labReferenceNumber, String specimenName,
	        Provider collector, Date from, Date to, boolean includeVoided) throws APIException {
		// TODO
		return null;
	}
	
	/**
	 * Returns a list of LabTestSample objects by matching given identifier and/or order number
	 * and/or lab reference number
	 * 
	 * @param labSampleIdentifier
	 * @param orderNumber
	 * @param labReferenceNumber
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestSample> getLabTestSamples(String labSampleIdentifier, String orderNumber, String labReferenceNumber,
	        boolean includeVoided) throws APIException {
		return getLabTestSamples(null, null, null, labSampleIdentifier, orderNumber, labReferenceNumber, null, null, null,
		    null, includeVoided);
	}
	
	/**
	 * @see List<LabTestSample> getLabTestSamples(LabTest, Patient, LabTestSampleStatus, String,
	 *      String, String, String, Provider, Date, Date, boolean)
	 */
	public List<LabTestSample> getLabTestSamples(LabTest labTest, boolean includeVoided) throws APIException {
		return getLabTestSamples(labTest, null, null, null, null, null, null, null, null, null, includeVoided);
	}
	
	/**
	 * @see List<LabTestSample> getLabTestSamples(LabTest, Patient, LabTestSampleStatus, String,
	 *      String, String, String, Provider, Date, Date, boolean)
	 */
	public List<LabTestSample> getLabTestSamples(Provider collector, boolean includeVoided) throws APIException {
		return getLabTestSamples(null, null, null, null, null, null, null, collector, null, null, includeVoided);
	}
	
	/**
	 * Returns list of LabTestSample objects by matching given order with associated LabTest Order
	 * object
	 * 
	 * @param order
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	public List<LabTestSample> getLabTestSamples(Order order, boolean includeVoided) throws APIException {
		return dao.getLabTestSamples(order, includeVoided);
	}
	
	/**
	 * Returns list of LabTestSample objects by matching status property and date range. This can be
	 * used to get samples which are yet to be processed
	 * 
	 * @param status
	 * @param from
	 * @param to
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	public List<LabTestSample> getLabTestSamples(LabTestSampleStatus status, Date from, Date to, boolean includeVoided)
	        throws APIException {
		return getLabTestSamples(null, null, status, null, null, null, null, null, from, to, includeVoided);
	}
	
	/**
	 * Returns LabTestType object by its generated ID
	 * 
	 * @param labTestTypeId
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestType getLabTestType(Integer labTestTypeId) throws APIException {
		return dao.getLabTestType(labTestTypeId);
	}
	
	/**
	 * Returns LabTestType object by uuid. It can be called by any authenticated user. It is fetched
	 * in read only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestType getLabTestTypeByUuid(String uuid) throws APIException {
		return dao.getLabTestTypeByUuid(uuid);
	}
	
	/**
	 * Get a list of LabTestType objects using various parameters available. This is similar to
	 * getByExample(...) methods, except that instead of passing similar object, properties are
	 * passed as parameters
	 * 
	 * @param name
	 * @param shortName
	 * @param testGroup
	 * @param isSpecimenRequired
	 * @param referenceConcept
	 * @param includeRetired
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestType> getLabTestTypes(String name, String shortName, LabTestGroup testGroup,
	        final Boolean isSpecimenRequired, Concept referenceConcept, boolean includeRetired) throws APIException {
		List<LabTestType> labTestTypes = dao.getAllLabTestTypes(name, shortName, testGroup, referenceConcept,
		    includeRetired);
		if (isSpecimenRequired != null) {
			for (Iterator<LabTestType> iterator = labTestTypes.iterator(); iterator.hasNext();) {
				LabTestType labTestType = iterator.next();
				if (!labTestType.isSpecimenRequired().equals(isSpecimenRequired)) {
					iterator.remove();
				}
			}
		}
		return labTestTypes;
	}
	
	/**
	 * Get a list of LabTest objects using various parameters available. This is similar to
	 * getByExample(...) methods, except that instead of passing similar object, properties are
	 * passed as parameters
	 * 
	 * @param labTestType
	 * @param patient
	 * @param sample
	 * @param referenceNumber
	 * @param from
	 * @param to
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(LabTestType labTestType, Patient patient, LabTestSample sample, String orderNumber,
	        String referenceNumber, Concept orderConcept, Provider orderer, Date from, Date to, boolean includeVoided)
	        throws APIException {
		return dao.getLabTests(labTestType, patient, sample, orderNumber, referenceNumber, orderConcept, orderer, from, to,
		    includeVoided);
	}
	
	/**
	 * Returns a list of LabTest objects by LabTestType
	 * 
	 * @param labTestType
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(LabTestType labTestType, boolean includeVoided) throws APIException {
		return getLabTests(labTestType, null, null, null, null, null, null, null, null, includeVoided);
	}
	
	/**
	 * Returns a list of LabTest objects by matching Concept object in LabTest Order
	 * 
	 * @param orderConcept
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(Concept orderConcept, boolean includeVoided) throws APIException {
		return getLabTests(null, null, null, null, null, orderConcept, null, null, null, includeVoided);
	}
	
	/**
	 * Returns a list of LabTest objects by LabTest Order Provider object
	 * 
	 * @param orderer
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(Provider orderer, boolean includeVoided) throws APIException {
		return getLabTests(null, null, null, null, null, null, orderer, null, null, includeVoided);
	}
	
	/**
	 * Returns a list of LabTest objects by given Patient
	 * 
	 * @param patient
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(Patient patient, boolean includeVoided) throws APIException {
		return getLabTests(null, patient, null, null, null, null, null, null, null, includeVoided);
	}
	
	/**
	 * Returns a list of LabTest objects by matching reference number
	 * 
	 * @param referenceNumber
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(String referenceNumber, boolean includeVoided) throws APIException {
		if (referenceNumber.length() < 4) {
			throw new APIException("Reference number to search should at least be 4 character long.");
		}
		return getLabTests(null, null, null, null, referenceNumber, null, null, null, null, includeVoided);
	}
	
	/**
	 * Returns most recent LabTest object by given Patient
	 * 
	 * @param patient
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTest getLatestLabTest(Patient patient) throws APIException {
		List<LabTest> labTests = dao.getNLabTests(patient, 1, false, true, false);
		if (!labTests.isEmpty()) {
			return labTests.get(0);
		}
		return null;
	}
	
	/**
	 * Returns most recent LabTestSample object by given Patient and LabTestSampleStatus
	 * 
	 * @param patient
	 * @param status
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestSample getLatestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Saves a LabTest objects
	 * 
	 * @param labTest
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	public LabTest saveLabTest(LabTest labTest) throws APIException {
		return dao.saveLabTest(labTest);
	}
	
	/**
	 * Saves a LabTestSample objects
	 * 
	 * @param labTest
	 * @param labTestSample
	 * @param labTestAttributes
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	public LabTest saveLabTest(LabTest labTest, LabTestSample labTestSample, Collection<LabTestAttribute> labTestAttributes)
	        throws APIException {
		LabTest savedLabTest = saveLabTest(labTest);
		dao.saveLabTestSample(labTestSample);
		Set<LabTestAttribute> attributes = new HashSet<LabTestAttribute>();
		for (LabTestAttribute labTestAttribute : labTestAttributes) {
			attributes.add(dao.saveLabTestAttribute(labTestAttribute));
		}
		savedLabTest.setAttributes(attributes);
		return savedLabTest;
	}
	
	/**
	 * Saves a LabTestAttribute object. Sets the owner to superuser, if it is not set. It can be
	 * called by users with this module's privilege. It is executed in a transaction.
	 * 
	 * @param labTestAttribute
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	public LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		return dao.saveLabTestAttribute(labTestAttribute);
	}
	
	/**
	 * Saves a list of LabTestAttribute objects. Call this when a set of attributes are being saved
	 * as a group
	 * 
	 * @param labTestAttributes
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	public List<LabTestAttribute> saveLabTestAttributes(List<LabTestAttribute> labTestAttributes) throws APIException {
		for (LabTestAttribute labTestAttribute : labTestAttributes) {
			saveLabTestAttribute(labTestAttribute);
		}
		return labTestAttributes;
	}
	
	/**
	 * Saves a LabTestAttributeType object. Sets the owner to superuser, if it is not set. It can be
	 * called by users with this module's privilege. It is executed in a transaction.
	 * 
	 * @param labTestAttributeType
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		return dao.saveLabTestAttributeType(labTestAttributeType);
	}
	
	/**
	 * Saves a LabTestSample objects
	 * 
	 * @param labTestSample
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	public LabTestSample saveLabTestSample(LabTestSample labTestSample) throws APIException {
		return dao.saveLabTestSample(labTestSample);
	}
	
	/**
	 * Saves a LabTestType object. Sets the owner to superuser, if it is not set. It can be called
	 * by users with this module's privilege. It is executed in a transaction.
	 * 
	 * @param labTestType
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public LabTestType saveLabTestType(LabTestType labTestType) throws APIException {
		return dao.saveLabTestType(labTestType);
	}
	
	/**
	 * Retires a LabTestType object
	 * 
	 * @param labTestType
	 * @param cascade
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.PURGE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void retireLabTestType(LabTestType labTestType) throws APIException {
		// TODO
	}
	
	/**
	 * Retires a LabTestAttribute object
	 * 
	 * @param labTestAttributeType
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.PURGE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void retireLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		// TODO
	}
	
	/**
	 * Unretires a LabTestAttribute object
	 * 
	 * @param labTestAttributeType
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.PURGE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void unretireLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		// TODO
	}
	
	/**
	 * Unretires a LabTestType object
	 * 
	 * @param labTestType
	 * @param cascade
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.PURGE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void unretireLabTestType(LabTestType labTestType) throws APIException {
		// TODO
	}
	
	/**
	 * Voids a LabTest object
	 * 
	 * @param labTest
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void voidLabTest(LabTest labTest) throws APIException {
		// TODO
	}
	
	/**
	 * Voids a LabTestAttribute object
	 * 
	 * @param labTestAttribute
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void voidLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		// TODO
	}
	
	/**
	 * Deletes a LabTestSample object
	 * 
	 * @param labTestSample
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void voidLabTestSample(LabTestSample labTestSample) throws APIException {
		// TODO
	}
	
	/**
	 * Unvoids a LabTest object
	 * 
	 * @param labTest
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void unvoidLabTest(LabTest labTest) throws APIException {
		// TODO
	}
	
	/**
	 * Unvoids a LabTestAttribute object
	 * 
	 * @param labTestAttribute
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void unvoidLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		// TODO
	}
	
	/**
	 * Unvoids a LabTestSample object
	 * 
	 * @param labTestSample
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void unvoidLabTestSample(LabTestSample labTestSample) throws APIException {
		// TODO
	}
	
	/**
	 * Deletes a LabTest object
	 * 
	 * @param labTest
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void deleteLabTest(LabTest labTest) throws APIException {
		dao.purgeLabTest(labTest);
	}
	
	/**
	 * Deletes a LabTestAttribute object
	 * 
	 * @param labTestAttribute
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void deleteLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		dao.purgeLabTestAttribute(labTestAttribute);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestAttributeType(LabTestAttributeType,
	 *      boolean)
	 */
	@Authorized(CommonLabTestConfig.PURGE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		deleteLabTestAttributeType(labTestAttributeType, false);
	}
	
	/**
	 * Deletes a LabTestAttributeType object. Enabling cascade will delete all the dependent objects
	 * and then purge this object
	 * 
	 * @param labTestType
	 * @param cascade
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.PURGE_LAB_TEST_METADATA_PRIVILEGE)
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
	
	/**
	 * Deletes a LabTestSample object
	 * 
	 * @param labTestSample
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void deleteLabTestSample(LabTestSample labTestSample) throws APIException {
		dao.purgeLabTestSample(labTestSample);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestType(org.openmrs.module.commonlabtest.LabTestType)
	 */
	public void deleteLabTestType(LabTestType labTestType) throws APIException {
		deleteLabTestType(labTestType, false);
	}
	
	/**
	 * Deletes a LabTestType object. Enabling cascade will delete all the dependent objects and then
	 * purge this object
	 * 
	 * @param labTestType
	 * @param cascade
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.PURGE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void deleteLabTestType(LabTestType labTestType, boolean cascade) throws APIException {
		if (cascade) {
			List<LabTest> labTests = getLabTests(labTestType, true);
			for (LabTest labTest : labTests) {
				dao.purgeLabTest(labTest);
			}
		}
		dao.purgeLabTestType(labTestType);
	}
}
