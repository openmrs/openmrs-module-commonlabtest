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
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.commonlabtest.CommonLabTestActivator;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestGroup;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author owais.hussain@ihsinformatics.com
 */
@Transactional
public interface CommonLabTestService extends OpenmrsService {

	/**
	 * Returns list of all {@link LabTestAttributeType} objects
	 * 
	 * @param includeRetired include retired objects
	 * @return {@link LabTestAttributeType} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTestAttributeType> getAllLabTestAttributeTypes(boolean includeRetired) throws APIException;

	/**
	 * Returns list of all objects of {@link LabTestType}
	 * 
	 * @param includeRetired include retired objects
	 * @return {@link LabTestType} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTestType> getAllLabTestTypes(boolean includeRetired) throws APIException;

	/**
	 * Returns list of objects of {@link Concept} with class/type as LabSet
	 * 
	 * @return
	 * @throws APIException
	 */
	List<Concept> getLabTestConcepts() throws APIException;

	/**
	 * Return all set member concepts defined in the set of Specimen Type concept see
	 * {@link CommonLabTestActivator.SPECIMEN_TYPE_CONCEPT_UUID}
	 * 
	 * @return {@link Concept} object(s)
	 */
	List<Concept> getSpecimenTypeConcepts();

	/**
	 * Return all set member concepts defined in the set of Specimen Site concept see
	 * {@link CommonLabTestActivator.SPECIMEN_SITE_CONCEPT_UUID}
	 * 
	 * @return {@link Concept} object(s)
	 */
	List<Concept> getSpecimenSiteConcepts();

	/**
	 * Returns first {@link LabTest} object by given {@link Patient}
	 * 
	 * @param patient the {@link Patient} object
	 * @return {@link LabTestType} object(s)
	 * @throws APIException on Exception
	 */
	LabTest getEarliestLabTest(Patient patient) throws APIException;

	/**
	 * Returns first {@link LabTestSample} object by given {@link Patient} and
	 * {@link LabTestSampleStatus}
	 * 
	 * @param patient the {@link Patient} object
	 * @param status the {@link LabTestSampleStatus} object
	 * @return {@link LabTestSample} object(s)
	 * @throws APIException on Exception
	 */
	LabTestSample getEarliestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException;

	/**
	 * Returns a {@link LabTest} object by generated ID
	 * 
	 * @param labTestId the Id
	 * @return {@link LabTest} object(s)
	 * @throws APIException on Exception
	 */
	LabTest getLabTest(Integer labTestId) throws APIException;

	/**
	 * Returns a {@link LabTestAttribute} object by its generated ID
	 * 
	 * @param labTestAttributeId the Id
	 * @return {@link LabTestAttribute} object(s)
	 * @throws APIException on Exception
	 */
	LabTestAttribute getLabTestAttribute(Integer labTestAttributeId) throws APIException;

	/**
	 * Returns a {@link LabTestAttribute} object by uuid. It can be called by any authenticated user. It
	 * is fetched in read only transaction.
	 * 
	 * @param uuid the UUID
	 * @return {@link LabTestAttribute} object(s)
	 * @throws APIException on Exception
	 */
	LabTestAttribute getLabTestAttributeByUuid(String uuid) throws APIException;

	/**
	 * @see org.openmrs.module.commonlabtest.api.dao.CommonLabTestDAO#getLabTestAttributes(Integer)
	 * @param testOrderId the order Id
	 * @return {@link LabTestAttribute} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTestAttribute> getLabTestAttributes(Integer testOrderId) throws APIException;

	/**
	 * Get a list of {@link LabTestAttribute} objects using various parameters available. This is
	 * similar to getByExample(...) methods, except that instead of passing similar object, properties
	 * are passed as parameters
	 * 
	 * @param labTestAttributeType the {@link LabTestAttributeType} object
	 * @param valueReference the value of reference
	 * @param from the {@link Date} object
	 * @param to the {@link Date} object
	 * @param includeVoided include voided objects
	 * @return {@link LabTestAttribute} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, String valueReference, Date from,
	        Date to, boolean includeVoided) throws APIException;

	/**
	 * @see #getLabTestAttributes(LabTestAttributeType, String, Date, Date, boolean)
	 * @param labTestAttributeType the {@link LabTestAttributeType} object
	 * @param includeVoided include voided objects
	 * @return {@link LabTestAttribute} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, boolean includeVoided)
	        throws APIException;

	/*
	 * @see CommonLabTestDAO#getLabTestAttributes(Patient, LabTestAttributeType,
	 * boolean)
	 */
	List<LabTestAttribute> getLabTestAttributes(Patient patient, boolean includeVoided) throws APIException;

	/*
	 * @see CommonLabTestDAO#getLabTestAttributes(Patient, LabTestAttributeType,
	 * boolean)
	 */
	List<LabTestAttribute> getLabTestAttributes(Patient patient, LabTestAttributeType labTestAttributeType,
	        boolean includeVoided) throws APIException;

	/**
	 * Returns a {@link LabTestAttributeType} object by its generated ID
	 * 
	 * @param labTestAttributeTypeId the generated Id
	 * @return {@link LabTestAttributeType} object(s)
	 * @throws APIException on Exception
	 */
	LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId) throws APIException;

	/**
	 * Returns a {@link LabTestAttributeType} object by uuid. It can be called by any authenticated
	 * user. It is fetched in read only transaction.
	 * 
	 * @param uuid the unique Id
	 * @return {@link LabTestAttributeType} object(s)
	 * @throws APIException on Exception
	 */
	LabTestAttributeType getLabTestAttributeTypeByUuid(String uuid) throws APIException;

	/**
	 * Get a list of {@link LabTestAttributeType} objects using various parameters available. This is
	 * similar to getByExample(...) methods, except that instead of passing similar object, properties
	 * are passed as parameters
	 * 
	 * @param name the name of attribute type
	 * @param datatypeClassname fully specified data type class name
	 * @param includeRetired include retired objects
	 * @return {@link LabTestAttributeType} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTestAttributeType> getLabTestAttributeTypes(String name, String datatypeClassname, boolean includeRetired)
	        throws APIException;

	/**
	 * Get a list of {@link LabTestAttributeType} objects against {@link LabTestType} object
	 * 
	 * @param labTestType the {@link LabTestType} object
	 * @param includeRetired include retired objects
	 * @return {@link LabTestAttributeType} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTestAttributeType> getLabTestAttributeTypes(LabTestType labTestType, boolean includeRetired) throws APIException;

	/**
	 * Returns a {@link LabTest} object by uuid. It can be called by any authenticated user. It is
	 * fetched in read only transaction.
	 * 
	 * @param uuid the unique Id
	 * @return {@link LabTest} object(s)
	 * @throws APIException on Exception
	 */
	LabTest getLabTestByUuid(String uuid) throws APIException;

	/*
	 * @see LabTest getLabTest(Encounter orderEncounter)
	 */
	LabTest getLabTest(Order order) throws APIException;

	/**
	 * Returns a {@link LabTestSample} object by its generated ID
	 * 
	 * @param labTestSampleId the Id
	 * @return {@link LabTestSample} object(s)
	 * @throws APIException on Exception
	 */
	LabTestSample getLabTestSample(Integer labTestSampleId) throws APIException;

	/**
	 * Returns a {@link LabTestSample} object by uuid. It can be called by any authenticated user. It is
	 * fetched in read only transaction.
	 * 
	 * @param uuid the unique Id
	 * @return {@link LabTestSample} object(s)
	 * @throws APIException on Exception
	 */
	LabTestSample getLabTestSampleByUuid(String uuid) throws APIException;

	/**
	 * Get a list of {@link LabTestSample} objects using various parameters available. This is similar
	 * to getByExample(...) methods, except that instead of passing similar object, properties are
	 * passed as parameters
	 * 
	 * @param labTest the {@link LabTest} object
	 * @param patient the {@link Patient} object
	 * @param status the {@link LabTestSampleStatus} object
	 * @param labSampleIdentifier the lab sample Id
	 * @param collector the {@link Provider} object
	 * @param from the start {@link Date} object
	 * @param to the end {@link Date} object
	 * @param includeVoided include voided objects
	 * @return {@link LabTestSample} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTestSample> getLabTestSamples(LabTest labTest, Patient patient, LabTestSampleStatus status,
	        String labSampleIdentifier, Provider collector, Date from, Date to, boolean includeVoided) throws APIException;

	/**
	 * Returns a list of {@link LabTestSample} objects by matching given identifier and/or order number
	 * and/or lab reference number
	 * 
	 * @param labSampleIdentifier the lab sample Id
	 * @param orderNumber the order number
	 * @param labReferenceNumber the reference number
	 * @param includeVoided include voided objects
	 * @return {@link LabTestSample} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTestSample> getLabTestSamples(String labSampleIdentifier, String orderNumber, String labReferenceNumber,
	        boolean includeVoided) throws APIException;

	/*
	 * @see CommonLabTestDAO#getLabTestSamples(LabTest, boolean)
	 */
	List<LabTestSample> getLabTestSamples(LabTest labTest, boolean includeVoided) throws APIException;

	/*
	 * @see CommonLabTestDAO#getLabTestSamples(Patient, boolean)
	 */
	List<LabTestSample> getLabTestSamples(Patient patient, boolean includeVoided) throws APIException;

	/*
	 * @see CommonLabTestDAO#getLabTestSamples(Provider, boolean)
	 */
	List<LabTestSample> getLabTestSamples(Provider collector, boolean includeVoided) throws APIException;

	/**
	 * Returns list of {@link LabTestSample} objects by matching status property and date range. This
	 * can be used to get samples which are yet to be processed
	 * 
	 * @param status the {@link LabTestSampleStatus} object
	 * @param from the start {@link Date} object
	 * @param to the end {@link Date} object
	 * @param includeVoided include voided objects
	 * @return {@link LabTestSample} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTestSample> getLabTestSamples(LabTestSampleStatus status, Date from, Date to, boolean includeVoided)
	        throws APIException;

	/**
	 * @param labTestTypeId the generated Id
	 * @return {@link LabTestType} object(s)
	 * @throws APIException on Exception
	 */
	LabTestType getLabTestType(Integer labTestTypeId) throws APIException;

	/**
	 * @param uuid the unique Id
	 * @return {@link LabTestType} object(s)
	 * @throws APIException on Exception
	 */
	LabTestType getLabTestTypeByUuid(String uuid) throws APIException;

	/**
	 * Get a list of {@link LabTestType} objects using various parameters available. This is similar to
	 * getByExample(...) methods, except that instead of passing similar object, properties are passed
	 * as parameters
	 * 
	 * @param name the name
	 * @param shortName the short name
	 * @param testGroup the {@link LabTestGroup} object
	 * @param isSpecimenRequired whether specimen is required
	 * @param referenceConcept the {@link Concept} object
	 * @param includeRetired include retired objects
	 * @return {@link LabTestType} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTestType> getLabTestTypes(String name, String shortName, LabTestGroup testGroup, Boolean isSpecimenRequired,
	        Concept referenceConcept, boolean includeRetired) throws APIException;

	/**
	 * Get a list of {@link LabTest} objects using various parameters available. This is similar to
	 * getByExample(...) methods, except that instead of passing similar object, properties are passed
	 * as parameters
	 * 
	 * @param labTestType the {@link LabTestType} object
	 * @param patient the {@link Patient} object
	 * @param orderNumber the order number
	 * @param referenceNumber the reference number
	 * @param orderConcept the {@link Concept} object
	 * @param orderer the {@link Provider} object
	 * @param from the start {@link Date} object
	 * @param to the end {@link Date} object
	 * @param includeVoided include voided objects
	 * @return {@link LabTest} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTest> getLabTests(LabTestType labTestType, Patient patient, String orderNumber, String referenceNumber,
	        Concept orderConcept, Provider orderer, Date from, Date to, boolean includeVoided) throws APIException;

	/**
	 * Returns a list of {@link LabTest} objects by {@link LabTestType}
	 * 
	 * @param labTestType the {@link LabTestType} object
	 * @param includeVoided include voided objects
	 * @return {@link LabTest} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTest> getLabTests(LabTestType labTestType, boolean includeVoided) throws APIException;

	/**
	 * Returns a list of {@link LabTest} objects by {@link Concept} object in {@link LabTest} Order
	 * 
	 * @param orderConcept the {@link Concept} object
	 * @param includeVoided include voided objects
	 * @return {@link LabTest} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTest> getLabTests(Concept orderConcept, boolean includeVoided) throws APIException;

	/**
	 * Returns a list of {@link LabTest} objects by {@link LabTest} Order {@link Provider} object
	 * 
	 * @param orderer the {@link Provider} object
	 * @param includeVoided include voided objects
	 * @return {@link LabTest} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTest> getLabTests(Provider orderer, boolean includeVoided) throws APIException;

	/**
	 * Returns a list of {@link LabTest} objects by {@link Patient}
	 * 
	 * @param patient the {@link Patient} object
	 * @param includeVoided include voided objects
	 * @return {@link LabTest} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTest> getLabTests(Patient patient, boolean includeVoided) throws APIException;

	/**
	 * Returns a list of {@link LabTest} objects by matching reference number
	 * 
	 * @param referenceNumber the reference number
	 * @param includeVoided to include voided objects
	 * @return {@link LabTest} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTest> getLabTests(String referenceNumber, boolean includeVoided) throws APIException;

	/**
	 * Returns most recent {@link LabTest} object by given {@link Patient}
	 * 
	 * @param patient the {@link Patient} object
	 * @return {@link LabTest} object(s)
	 * @throws APIException on Exception
	 */
	LabTest getLatestLabTest(Patient patient) throws APIException;

	/**
	 * Returns most recent {@link LabTestSample} object by given {@link Patient} and
	 * {@link LabTestSampleStatus}
	 * 
	 * @param patient the {@link Patient} object
	 * @param status the {@link LabTestSampleStatus} object (optional, set null to skip)
	 * @return {@link LabTestSample} object(s)
	 * @throws APIException on Exception
	 */
	LabTestSample getLatestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException;

	/**
	 * @param labTest the {@link LabTest} object to save
	 * @return saved {@link LabTest} object
	 * @throws APIException on Exception
	 */
	LabTest saveLabTest(LabTest labTest) throws APIException;

	/**
	 * Saves a {@link LabTest} object along with its properties {@link LabTestSample} and
	 * {@link LabTestAttribute} object(s)
	 * 
	 * @param labTest the {@link LabTest} object to save
	 * @param labTestSample the {@link LabTestSample} object to save
	 * @param labTestAttributes tye {@link LabTestAttribute} object(s) to save
	 * @return saved {@link LabTest} object
	 * @throws APIException on Exception
	 */
	LabTest saveLabTest(LabTest labTest, LabTestSample labTestSample, Collection<LabTestAttribute> labTestAttributes)
	        throws APIException;

	/**
	 * @param labTestAttribute the {@link LabTestAttribute} object to save
	 * @return saved {@link LabTestAttribute} object
	 * @throws APIException on Exception
	 */
	LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException;

	/**
	 * Saves a list of {@link LabTestAttribute} objects. Call this when a set of attributes are being
	 * saved as a group
	 * 
	 * @param labTestAttributes the {@link LabTestAttributeType} object(s) to save
	 * @return saved {@link LabTestAttribute} object(s)
	 * @throws APIException on Exception
	 */
	List<LabTestAttribute> saveLabTestAttributes(List<LabTestAttribute> labTestAttributes) throws APIException;

	/**
	 * @param labTestAttributeType the {@link LabTestAttributeType} object to save
	 * @return saved {@link LabTestAttributeType} object
	 * @throws APIException on Exception
	 */
	LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException;

	/**
	 * @param labTestSample the {@link LabTestSample} object to save
	 * @return saved {@link LabTestSample} object
	 * @throws APIException on Exception
	 */
	LabTestSample saveLabTestSample(LabTestSample labTestSample) throws APIException;

	/**
	 * Saves a {@link LabTestType} object (except mandatory Unknown record). Sets the owner to
	 * superuser, if it is not set. It can be called by users with this module's privilege. It is
	 * executed in a transaction.
	 * 
	 * @param labTestType the {@link LabTestType} object to save
	 * @return saved {@link LabTestType} object
	 * @throws APIException on Exception
	 */
	LabTestType saveLabTestType(LabTestType labTestType) throws APIException;

	/**
	 * @param labTestType the {@link LabTestType} object to retire
	 * @param retireReason the reason to retire
	 * @throws APIException on Exception
	 */
	void retireLabTestType(LabTestType labTestType, String retireReason) throws APIException;

	/**
	 * @param labTestAttributeType the {@link LabTestAttributeType} object to retire
	 * @param retireReason the reason to retire
	 * @throws APIException on Exception
	 */
	void retireLabTestAttributeType(LabTestAttributeType labTestAttributeType, String retireReason) throws APIException;

	/**
	 * @param labTestAttributeType the {@link LabTestAttributeType} object to unretire
	 * @throws APIException on Exception
	 */
	void unretireLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException;

	/**
	 * @param labTestType the {@link LabTestType} object to unretire
	 * @throws APIException on Exception
	 */
	void unretireLabTestType(LabTestType labTestType) throws APIException;

	/**
	 * Voids a {@link LabTest} object and all {@link LabTestSample} and {@link LabTestAttribute} objects
	 * associated with it
	 * 
	 * @param labTest the {@link LabTest} object to void
	 * @param voidReason the reason to void
	 * @throws APIException on Exception
	 */
	void voidLabTest(LabTest labTest, String voidReason) throws APIException;

	/**
	 * @param labTestAttribute the {@link LabTestAttribute} object to void
	 * @param voidReason the reason to void
	 * @throws APIException on Exception
	 */
	void voidLabTestAttribute(LabTestAttribute labTestAttribute, String voidReason) throws APIException;

	/**
	 * Voids list of {@link LabTestAttribute} objects by {@link LabTest} and set the
	 * {@link LabTestSampleStatus} of PROCESSED {@link LabTestSample} objects to COLLECTED
	 * 
	 * @param labTest the {@link LabTest} object to void
	 * @param voidReason the reason to void
	 * @throws APIException on Exception
	 */
	void voidLabTestAttributes(LabTest labTest, String voidReason) throws APIException;

	/**
	 * @param labTestSample the {@link LabTestSample} object to void
	 * @param voidReason the reason to void
	 * @throws APIException on Exception
	 */
	void voidLabTestSample(LabTestSample labTestSample, String voidReason) throws APIException;

	/**
	 * Unvoids a {@link LabTest} object along with all {@link LabTestSample} and
	 * {@link LabTestAttribute} objects, which were voided with the same reason on same time. This way,
	 * the dependent objects which were manually void will stay voided.
	 * 
	 * @param labTest the {@link LabTest} object to unvoid
	 * @throws APIException on Exception
	 */
	void unvoidLabTest(LabTest labTest) throws APIException;

	/**
	 * @param labTestAttribute the {@link LabTestAttribute} object to unvoid
	 * @throws APIException on Exception
	 */
	void unvoidLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException;

	/**
	 * @param labTestSample the {@link LabTestSample} object to unvoid
	 * @throws APIException on Exception
	 */
	void unvoidLabTestSample(LabTestSample labTestSample) throws APIException;

	/**
	 * @param labTest the {@link LabTest} object to delete
	 * @throws APIException on Exception
	 */
	void deleteLabTest(LabTest labTest) throws APIException;

	/**
	 * @param labTestAttribute the {@link LabTestAttribute} object to delete
	 * @throws APIException on Exception
	 */
	void deleteLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException;

	/**
	 * @see #deleteLabTestAttributeType(LabTestAttributeType, boolean)
	 * @param labTestAttributeType the {@link LabTestAttributeType} object
	 * @throws APIException on Exception
	 */
	void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException;

	/**
	 * @param labTestAttributeType the {@link LabTestAttributeType} object to delete
	 * @param cascade whether to cascade delete dependent objects or not
	 * @throws APIException on Exception
	 */
	void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType, boolean cascade) throws APIException;

	/**
	 * @param labTestSample the {@link LabTestSample} object to delete
	 * @throws APIException on Exception
	 */
	void deleteLabTestSample(LabTestSample labTestSample) throws APIException;

	/**
	 * @see #deleteLabTestType(LabTestType, LabTestType)
	 * @param labTestType the {@link LabTestType} object to delete
	 * @throws APIException on Exception
	 */
	void deleteLabTestType(LabTestType labTestType) throws APIException;

	/**
	 * @param labTestType the {@link LabTestType} object to delete
	 * @param cascade whether to cascade delete dependent objects or not
	 * @throws APIException on Exception
	 * @deprecated because deleting dependent objects is a risk. Use the alternate overloaded method
	 *             <code>deleteLabTestType(LabTestType, LabTestType)</code>
	 */
	@Deprecated
	void deleteLabTestType(LabTestType labTestType, boolean cascade) throws APIException;

	/**
	 * Deletes a LabTestType object and sets {@link LabTestType} of dependent objects to second
	 * parameter
	 * 
	 * @param labTestType the {@link LabTestType} object to delete
	 * @param newObjectForCascade the {@link LabTestType} set to cascaded dependent objects
	 * @throws APIException on Exception
	 */
	void deleteLabTestType(LabTestType labTestType, LabTestType newObjectForCascade) throws APIException;
}
