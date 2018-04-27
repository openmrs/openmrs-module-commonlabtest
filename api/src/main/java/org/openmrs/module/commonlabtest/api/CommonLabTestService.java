/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.commonlabtest.api;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.commonlabtest.CommonLabTestConfig;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestGroup;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType;
import org.springframework.transaction.annotation.Transactional;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface CommonLabTestService extends OpenmrsService {
	
	/**
	 * Saves a LabTest objects
	 * 
	 * @param labTest
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	LabTest saveLabTest(LabTest labTest) throws APIException;
	
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
	LabTest saveLabTest(LabTest labTest, LabTestSample labTestSample, Collection<LabTestAttribute> labTestAttributes)
	        throws APIException;
	
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
	LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException;
	
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
	List<LabTestAttribute> saveLabTestAttributes(List<LabTestAttribute> labTestAttributes) throws APIException;
	
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
	LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException;
	
	/**
	 * Saves a LabTestSample objects
	 * 
	 * @param labTestSample
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	LabTestSample saveLabTestSample(LabTestSample labTestSample) throws APIException;
	
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
	LabTestType saveLabTestType(LabTestType labTestType) throws APIException;
	
	/**
	 * Deletes a LabTestSample object
	 * 
	 * @param labTestSample
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	void deleteLabTest(LabTest labTest) throws APIException;
	
	/**
	 * Deletes a LabTestAttribute object
	 * 
	 * @param labTestAttribute
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	void deleteLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException;
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestAttributeType(LabTestAttributeType,
	 *      boolean)
	 */
	@Authorized(CommonLabTestConfig.PURGE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException;
	
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
	void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType, boolean cascade) throws APIException;
	
	/**
	 * Deletes a LabTestSample object
	 * 
	 * @param labTestSample
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	void deleteLabTestSample(LabTestSample labTestSample) throws APIException;
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestType(LabTestType,
	 *      boolean)
	 */
	@Authorized(CommonLabTestConfig.PURGE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	void deleteLabTestType(LabTestType labTestType) throws APIException;
	
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
	void deleteLabTestType(LabTestType labTestType, boolean cascade) throws APIException;
	
	/**
	 * Find a list of LabTestAttribute objects using various parameters available. This is similar
	 * to getByExample(...) methods, except that instead of passing similar object, properties are
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
	List<LabTestAttribute> findLabTestAttributes(LabTestAttributeType labTestAttributeType, Patient patient,
	        String valueReference, Date from, Date to, boolean includeVoided) throws APIException;
	
	/**
	 * Find a list of LabTestAttributeType objects using various parameters available. This is
	 * similar to getByExample(...) methods, except that instead of passing similar object,
	 * properties are passed as parameters
	 * 
	 * @param name
	 * @param datatypeClassname
	 * @param from
	 * @param to
	 * @param includeRetired
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTestAttributeType> findLabTestAttributeTypes(String name, Date from, Date to, String datatypeClassname,
	        boolean includeRetired) throws APIException;
	
	/**
	 * Find a list of LabTest objects using various parameters available. This is similar to
	 * getByExample(...) methods, except that instead of passing similar object, properties are
	 * passed as parameters
	 * 
	 * @param labTestType
	 * @param patient
	 * @param referenceNumber
	 * @param from
	 * @param to
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTest> findLabTests(LabTestType labTestType, Patient patient, String orderNumber, String referenceNumber,
	        Date from, Date to, boolean includeVoided) throws APIException;
	
	/**
	 * Find a list of LabTestSample objects using various parameters available. This is similar to
	 * getByExample(...) methods, except that instead of passing similar object, properties are
	 * passed as parameters
	 * 
	 * @param labTest
	 * @param patient
	 * @param labSampleIdentifier
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
	List<LabTestSample> findLabTestSamples(LabTest labTest, Patient patient, String labSampleIdentifier, String specimenName,
	        LabTestSampleStatus status, Date from, Date to, boolean includeVoided) throws APIException;
	
	/**
	 * Find a list of LabTestType objects using various parameters available. This is similar to
	 * getByExample(...) methods, except that instead of passing similar object, properties are
	 * passed as parameters
	 * 
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
	List<LabTestType> findLabTestTypes(String name, String shortName, LabTestGroup testGroup, Boolean isSpecimenRequired,
	        Concept referenceConcept, boolean includeRetired) throws APIException;
	
	/**
	 * Returns list of all LabTestAttributeType objects
	 * 
	 * @param includeRetired
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTestAttributeType> getAllLabTestAttributeTypes(boolean includeRetired) throws APIException;
	
	/**
	 * Returns list of all objects of LabTestType
	 * 
	 * @param includeRetired
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTestType> getAllLabTestTypes(boolean includeRetired) throws APIException;
	
	/**
	 * Returns first LabTestAttribute object by given Patient and LabTestAttributeType
	 * 
	 * @param patient
	 * @param labTestAttributeType
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTestAttribute getEarliestLabTestAttribute(Patient patient, LabTestAttributeType labTestAttributeType)
	        throws APIException;
	
	/**
	 * Returns first LabTest object by given Patient
	 * 
	 * @param patient
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTest getEarliestLabTestByPatient(Patient patient) throws APIException;
	
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
	LabTestSample getEarliestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException;
	
	/**
	 * Returns a LabTest object by generated ID
	 * 
	 * @param labTestId
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTest getLabTest(Integer labTestId) throws APIException;
	
	/**
	 * Returns a LabTestAttribute object by its generated ID
	 * 
	 * @param labTestAttributeTypeId
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTestAttribute getLabTestAttribute(Integer labTestAttributeId) throws APIException;
	
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
	LabTestAttribute getLabTestAttributeByUuid(String uuid) throws APIException;
	
	/**
	 * Returns list of all LabTestAttribute objects by given LabTest object and Patient object
	 * 
	 * @param labTestAttributeType
	 * @param patient
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTestAttribute> getLabTestAttributesByAttributeTypeAndPatient(LabTestAttributeType labTestAttributeType,
	        Patient patient, boolean includeVoided) throws APIException;
	
	/**
	 * Returns list of all LabTestAttribute objects by given LabTest object
	 * 
	 * @param labTest
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTestAttribute> getLabTestAttributesByLabTest(LabTest labTest, boolean includeVoided) throws APIException;
	
	/**
	 * Returns a LabTestAttributeType object by its generated ID
	 * 
	 * @param labTestAttributeTypeId
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId) throws APIException;
	
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
	LabTestAttributeType getLabTestAttributeTypeByUuid(String uuid) throws APIException;
	
	/**
	 * Returns a LabTest object by Encounter object in LabTest Order
	 * 
	 * @param orderEncounter
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTest getLabTestByOrderEncounter(Encounter orderEncounter) throws APIException;
	
	/**
	 * Returns a LabTest object by order number in LabTest Order
	 * 
	 * @param orderNumber
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTest getLabTestByOrderNumber(String orderNumber) throws APIException;
	
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
	LabTest getLabTestByUuid(String uuid) throws APIException;
	
	/**
	 * Returns a LabTestSample object by its generated ID
	 * 
	 * @param labTestSampleId
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTestSample getLabTestSample(Integer labTestSampleId) throws APIException;
	
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
	LabTestSample getLabTestSampleByUuid(String uuid) throws APIException;
	
	/**
	 * Returns a list of LabTestSample objects by matching given identifier
	 * 
	 * @param labSampleIdentifier
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTestSample> getLabTestSamplesByIdentifier(String labSampleIdentifier) throws APIException;
	
	/**
	 * Returns a list of LabTestSample objects by given LabTest object
	 * 
	 * @param labTest
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTestSample> getLabTestSamplesByLabTest(LabTest labTest, boolean includeVoided) throws APIException;
	
	/**
	 * Returns a list of LabTestSample objects by matching given encounter in Order object
	 * 
	 * @param encounter
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTestSample> getLabTestSamplesByOrderEncounter(Encounter encounter) throws APIException;
	
	/**
	 * Returns a list of LabTestSample objects by matching given order number in Order object
	 * 
	 * @param orderNumber
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTestSample> getLabTestSamplesByOrderNumber(String orderNumber) throws APIException;
	
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
	List<LabTest> getLabTestsByOrderConcept(Concept orderConcept, boolean includeVoided) throws APIException;
	
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
	List<LabTest> getLabTestsByOrderer(Provider orderer, boolean includeVoided) throws APIException;
	
	/* LAB TEST METHODS  */
	
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
	List<LabTest> getLabTestsByPatient(Patient patient, boolean includeVoided) throws APIException;
	
	/**
	 * Returns a list of LabTest objects by matching reference number
	 * 
	 * @param referenceNumber
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTest> getLabTestsByReferenceNumber(String referenceNumber) throws APIException;
	
	/**
	 * Returns LabTestType object by its generated ID
	 * 
	 * @param labTestTypeId
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTestType getLabTestType(Integer labTestTypeId) throws APIException;
	
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
	LabTestType getLabTestTypeByUuid(String uuid) throws APIException;
	
	/**
	 * Returns most recent LabTestAttribute object by given Patient and LabTestAttributeType
	 * 
	 * @param patient
	 * @param labTestAttributeType
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTestAttribute getLatestLabTestAttribute(Patient patient, LabTestAttributeType labTestAttributeType)
	        throws APIException;
	
	/**
	 * Returns most recent LabTest object by given Patient
	 * 
	 * @param patient
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTest getLatestLabTestByPatient(Patient patient) throws APIException;
	
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
	LabTestSample getLatestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException;
}
