package org.openmrs.module.commonlabtest.api;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.api.APIException;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.LabTestType.LabTestGroup;

public interface CommonLabTestService {
	
	/**
	 * Returns list of all LabTestAttributeType objects
	 * 
	 * @param includeRetired
	 * @return
	 * @throws APIException
	 */
	List<LabTestAttributeType> getAllLabTestAttributeTypes(boolean includeRetired) throws APIException;
	
	/**
	 * Returns list of all objects of LabTestType
	 * 
	 * @param includeRetired
	 * @return
	 * @throws APIException
	 */
	List<LabTestType> getAllLabTestTypes(boolean includeRetired) throws APIException;
	
	/**
	 * Returns first LabTest object by given Patient
	 * 
	 * @param patient
	 * @return
	 * @throws APIException
	 */
	LabTest getEarliestLabTest(Patient patient) throws APIException;
	
	/**
	 * Returns first LabTestSample object by given Patient and LabTestSampleStatus
	 * 
	 * @param patient
	 * @param status
	 * @return
	 * @throws APIException
	 */
	LabTestSample getEarliestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException;
	
	/**
	 * Returns a LabTest object by generated ID
	 * 
	 * @param labTestId
	 * @return
	 * @throws APIException
	 */
	LabTest getLabTest(Integer labTestId) throws APIException;
	
	/**
	 * Returns a LabTestAttribute object by its generated ID
	 * 
	 * @param labTestAttributeTypeId
	 * @return
	 * @throws APIException
	 */
	LabTestAttribute getLabTestAttribute(Integer labTestAttributeId) throws APIException;
	
	/**
	 * Returns a LabTestAttribute object by uuid. It can be called by any authenticated user. It is
	 * fetched in read only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	LabTestAttribute getLabTestAttributeByUuid(String uuid) throws APIException;
	
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
	List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, Patient patient,
	        String valueReference, Date from, Date to, boolean includeVoided) throws APIException;
	
	/**
	 * @see List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType, Patient, String, Date,
	 *      Date, boolean)
	 */
	List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, boolean includeVoided)
	        throws APIException;
	
	/**
	 * @see List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType, Patient, String, Date,
	 *      Date, boolean)
	 */
	List<LabTestAttribute> getLabTestAttributes(Patient patient, boolean includeVoided) throws APIException;
	
	/**
	 * @see List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType, Patient, String, Date,
	 *      Date, boolean)
	 */
	List<LabTestAttribute> getLabTestAttributes(Patient patient, LabTestAttributeType labTestAttributeType,
	        boolean includeVoided) throws APIException;
	
	/**
	 * Returns a LabTestAttributeType object by its generated ID
	 * 
	 * @param labTestAttributeTypeId
	 * @return
	 * @throws APIException
	 */
	LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId) throws APIException;
	
	/**
	 * Returns a LabTestAttributeType object by uuid. It can be called by any authenticated user. It
	 * is fetched in read only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	LabTestAttributeType getLabTestAttributeTypeByUuid(String uuid) throws APIException;
	
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
	List<LabTestAttributeType> getLabTestAttributeTypes(String name, String datatypeClassname, boolean includeRetired)
	        throws APIException;
	
	/**
	 * Returns a LabTest object by uuid. It can be called by any authenticated user. It is fetched
	 * in read only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	LabTest getLabTestByUuid(String uuid) throws APIException;
	
	/**
	 * Returns a LabTest object by Encounter object in LabTest Order object
	 * 
	 * @param orderEncounter
	 * @return
	 * @throws APIException
	 */
	LabTest getLabTest(Encounter orderEncounter) throws APIException;
	
	/**
	 * @see LabTest getLabTest(Encounter orderEncounter)
	 */
	LabTest getLabTest(Order order) throws APIException;
	
	/**
	 * Returns a LabTestSample object by its generated ID
	 * 
	 * @param labTestSampleId
	 * @return
	 * @throws APIException
	 */
	LabTestSample getLabTestSample(Integer labTestSampleId) throws APIException;
	
	/**
	 * Returns a LabTestSample object by uuid. It can be called by any authenticated user. It is
	 * fetched in read only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	LabTestSample getLabTestSampleByUuid(String uuid) throws APIException;
	
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
	List<LabTestSample> getLabTestSamples(LabTest labTest, Patient patient, LabTestSampleStatus status,
	        String labSampleIdentifier, String orderNumber, String labReferenceNumber, String specimenName,
	        Provider collector, Date from, Date to, boolean includeVoided) throws APIException;
	
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
	List<LabTestSample> getLabTestSamples(String labSampleIdentifier, String orderNumber, String labReferenceNumber,
	        boolean includeVoided) throws APIException;
	
	/**
	 * @see List<LabTestSample> getLabTestSamples(LabTest, Patient, LabTestSampleStatus, String,
	 *      String, String, String, Provider, Date, Date, boolean)
	 */
	List<LabTestSample> getLabTestSamples(LabTest labTest, boolean includeVoided) throws APIException;

	/**
	 * @see List<LabTestSample> getLabTestSamples(LabTest, Patient, LabTestSampleStatus, String,
	 *      String, String, String, Provider, Date, Date, boolean)
	 */
	List<LabTestSample> getLabTestSamples(Patient patient, boolean includeVoided) throws APIException;
	
	/**
	 * @see List<LabTestSample> getLabTestSamples(LabTest, Patient, LabTestSampleStatus, String,
	 *      String, String, String, Provider, Date, Date, boolean)
	 */
	List<LabTestSample> getLabTestSamples(Provider collector, boolean includeVoided) throws APIException;
	
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
	List<LabTestSample> getLabTestSamples(LabTestSampleStatus status, Date from, Date to, boolean includeVoided)
	        throws APIException;
	
	/**
	 * Returns LabTestType object by its generated ID
	 * 
	 * @param labTestTypeId
	 * @return
	 * @throws APIException
	 */
	LabTestType getLabTestType(Integer labTestTypeId) throws APIException;
	
	/**
	 * Returns LabTestType object by uuid. It can be called by any authenticated user. It is fetched
	 * in read only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	LabTestType getLabTestTypeByUuid(String uuid) throws APIException;
	
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
	List<LabTestType> getLabTestTypes(String name, String shortName, LabTestGroup testGroup, Boolean isSpecimenRequired,
	        Concept referenceConcept, boolean includeRetired) throws APIException;
	
	/**
	 * Get a list of LabTest objects using various parameters available. This is similar to
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
	List<LabTest> getLabTests(LabTestType labTestType, Patient patient, String orderNumber, String referenceNumber,
	        Concept orderConcept, Provider orderer, Date from, Date to, boolean includeVoided) throws APIException;
	
	/**
	 * Returns a list of LabTest objects by LabTestType
	 * 
	 * @param labTestType
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	List<LabTest> getLabTests(LabTestType labTestType, boolean includeVoided) throws APIException;
	
	/**
	 * Returns a list of LabTest objects by matching Concept object in LabTest Order
	 * 
	 * @param orderConcept
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	List<LabTest> getLabTests(Concept orderConcept, boolean includeVoided) throws APIException;
	
	/**
	 * Returns a list of LabTest objects by LabTest Order Provider object
	 * 
	 * @param orderer
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	List<LabTest> getLabTests(Provider orderer, boolean includeVoided) throws APIException;
	
	/**
	 * Returns a list of LabTest objects by given Patient
	 * 
	 * @param patient
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	List<LabTest> getLabTests(Patient patient, boolean includeVoided) throws APIException;
	
	/**
	 * Returns a list of LabTest objects by matching reference number
	 * 
	 * @param referenceNumber
	 * @return
	 * @throws APIException
	 */
	List<LabTest> getLabTests(String referenceNumber, boolean includeVoided) throws APIException;
	
	/**
	 * Returns most recent LabTest object by given Patient
	 * 
	 * @param patient
	 * @return
	 * @throws APIException
	 */
	LabTest getLatestLabTest(Patient patient) throws APIException;
	
	/**
	 * Returns most recent LabTestSample object by given Patient and LabTestSampleStatus (optional,
	 * set null to skip)
	 * 
	 * @param patient
	 * @param status
	 * @return
	 * @throws APIException
	 */
	LabTestSample getLatestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException;
	
	/**
	 * Saves a LabTest objects
	 * 
	 * @param labTest
	 * @return
	 * @throws APIException
	 */
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
	LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException;
	
	/**
	 * Saves a list of LabTestAttribute objects. Call this when a set of attributes are being saved
	 * as a group
	 * 
	 * @param labTestAttributes
	 * @return
	 * @throws APIException
	 */
	List<LabTestAttribute> saveLabTestAttributes(List<LabTestAttribute> labTestAttributes) throws APIException;
	
	/**
	 * Saves a LabTestAttributeType object. Sets the owner to superuser, if it is not set. It can be
	 * called by users with this module's privilege. It is executed in a transaction.
	 * 
	 * @param labTestAttributeType
	 * @return
	 * @throws APIException
	 */
	LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException;
	
	/**
	 * Saves a LabTestSample objects
	 * 
	 * @param labTestSample
	 * @return
	 * @throws APIException
	 */
	LabTestSample saveLabTestSample(LabTestSample labTestSample) throws APIException;
	
	/**
	 * Saves a LabTestType object. Sets the owner to superuser, if it is not set. It can be called
	 * by users with this module's privilege. It is executed in a transaction.
	 * 
	 * @param labTestType
	 * @return
	 * @throws APIException
	 */
	LabTestType saveLabTestType(LabTestType labTestType) throws APIException;
	
	/**
	 * Retires a LabTestType object
	 * 
	 * @param labTestType
	 * @param cascade
	 * @throws APIException
	 */
	void retireLabTestType(LabTestType labTestType, String retireReason) throws APIException;
	
	/**
	 * Retires a LabTestAttribute object
	 * 
	 * @param labTestAttributeType
	 * @throws APIException
	 */
	void retireLabTestAttributeType(LabTestAttributeType labTestAttributeType, String retireReason) throws APIException;
	
	/**
	 * Unretires a LabTestAttribute object
	 * 
	 * @param labTestAttributeType
	 * @throws APIException
	 */
	void unretireLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException;
	
	/**
	 * Unretires a LabTestType object
	 * 
	 * @param labTestType
	 * @param cascade
	 * @throws APIException
	 */
	void unretireLabTestType(LabTestType labTestType) throws APIException;
	
	/**
	 * Voids a LabTest object and all LabTestSample and LabTestAttribute objects associated with it
	 * 
	 * @param labTest
	 * @throws APIException
	 */
	void voidLabTest(LabTest labTest, String voidReason) throws APIException;
	
	/**
	 * Voids a LabTestAttribute object
	 * 
	 * @param labTestAttribute
	 * @throws APIException
	 */
	void voidLabTestAttribute(LabTestAttribute labTestAttribute, String voidReason) throws APIException;
	
	/**
	 * Deletes a LabTestSample object
	 * 
	 * @param labTestSample
	 * @throws APIException
	 */
	void voidLabTestSample(LabTestSample labTestSample, String voidReason) throws APIException;
	
	/**
	 * Unvoids a LabTest object along with all LabTestSample and LabTestAttribute objects, which
	 * were voided with the same reason on same time. This way, the dependent objects which were
	 * manually void will stay voided.
	 * 
	 * @param labTest
	 * @throws APIException
	 */
	void unvoidLabTest(LabTest labTest) throws APIException;
	
	/**
	 * Unvoids a LabTestAttribute object
	 * 
	 * @param labTestAttribute
	 * @throws APIException
	 */
	void unvoidLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException;
	
	/**
	 * Unvoids a LabTestSample object
	 * 
	 * @param labTestSample
	 * @throws APIException
	 */
	void unvoidLabTestSample(LabTestSample labTestSample) throws APIException;
	
	/**
	 * Deletes a LabTest object
	 * 
	 * @param labTest
	 * @throws APIException
	 */
	void deleteLabTest(LabTest labTest) throws APIException;
	
	/**
	 * Deletes a LabTestAttribute object
	 * 
	 * @param labTestAttribute
	 * @throws APIException
	 */
	void deleteLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException;
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestAttributeType(LabTestAttributeType,
	 *      boolean)
	 */
	void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException;
	
	/**
	 * Deletes a LabTestAttributeType object. Enabling cascade will delete all the dependent objects
	 * and then purge this object
	 * 
	 * @param labTestType
	 * @param cascade
	 * @throws APIException
	 */
	void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType, boolean cascade) throws APIException;
	
	/**
	 * Deletes a LabTestSample object
	 * 
	 * @param labTestSample
	 * @throws APIException
	 */
	void deleteLabTestSample(LabTestSample labTestSample) throws APIException;
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestType(org.openmrs.module.commonlabtest.LabTestType)
	 */
	void deleteLabTestType(LabTestType labTestType) throws APIException;
	
	/**
	 * Deletes a LabTestType object. Enabling cascade will delete all the dependent objects and then
	 * purge this object
	 * 
	 * @param labTestType
	 * @param cascade
	 * @throws APIException
	 */
	void deleteLabTestType(LabTestType labTestType, boolean cascade) throws APIException;
	
}
