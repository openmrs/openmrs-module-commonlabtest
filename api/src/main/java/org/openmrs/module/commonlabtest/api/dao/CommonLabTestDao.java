package org.openmrs.module.commonlabtest.api.dao;

import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.LabTestType.LabTestGroup;

public interface CommonLabTestDao {
	
	/**
	 * Returns list of {@link LabTestAttributeType} objects
	 * 
	 * @param includeRetired
	 * @return
	 */
	List<LabTestAttributeType> getAllLabTestAttributeTypes(boolean includeRetired);
	
	/**
	 * Returns list of {@link LabTestType} objects
	 * 
	 * @param includeRetired
	 * @return
	 */
	List<LabTestType> getAllLabTestTypes(boolean includeRetired);
	
	/**
	 * Returns list of {@link LabTestType} objects matching all non-null parameters given
	 * 
	 * @param name
	 * @param shortName
	 * @param testGroup
	 * @param referenceConcept
	 * @param includeRetired
	 * @return
	 */
	List<LabTestType> getLabTestTypes(String name, String shortName, LabTestGroup testGroup, Concept referenceConcept,
	        boolean includeRetired);
	
	/**
	 * Returns {@link LabTest} object by matching given {@link Order} object
	 * 
	 * @param order
	 * @return
	 */
	LabTest getLabTest(Order order);
	
	/**
	 * Returns {@link LabTest} object by generated ID
	 * 
	 * @param labTestId
	 * @return
	 */
	LabTest getLabTest(Integer labTestId);
	
	/**
	 * Returns {@link LabTestAttribute} object by generated ID
	 * 
	 * @param labTestId
	 * @return
	 */
	LabTestAttribute getLabTestAttribute(Integer labTestAttributeId);
	
	/**
	 * Returns {@link LabTestAttribute} object by generated ID
	 * 
	 * @param labTestId
	 * @return
	 */
	LabTestAttribute getLabTestAttributeByUuid(String uuid);
	
	/**
	 * Returns list of {@link LabTestAttribute} objects by matching given non-null parameters
	 * 
	 * @param labTestId
	 * @return
	 */
	List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, LabTest labTest, Patient patient,
	        String valueReference, Date from, Date to, boolean includeVoided);
	
	/**
	 * Returns {@link LabTestAttributeType} object by generated Id
	 * 
	 * @param labTestAttributeTypeId
	 * @return
	 */
	LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId);
	
	/**
	 * Returns {@link LabTestAttributeType} object by UUID
	 * 
	 * @param uuid
	 * @return
	 */
	LabTestAttributeType getLabTestAttributeTypeByUuid(String uuid);
	
	/**
	 * Returns list of {@link LabTestAttributeType} objects by matching non-null parameters
	 * 
	 * @param name
	 * @param datatypeClassname
	 * @param includeRetired
	 * @return
	 */
	List<LabTestAttributeType> getLabTestAttributeTypes(String name, String datatypeClassname, boolean includeRetired);
	
	/**
	 * Returns {@link LabTest} object by UUID
	 * 
	 * @param uuid
	 * @return
	 */
	LabTest getLabTestByUuid(String uuid);
	
	/**
	 * Returns list of {@link LabTest} objects by matching non-null parameters
	 * 
	 * @param labTestType
	 * @param patient
	 * @param orderNumber
	 * @param referenceNumber
	 * @param orderConcept
	 * @param orderer
	 * @param from
	 * @param to
	 * @param includeVoided
	 * @return
	 */
	List<LabTest> getLabTests(LabTestType labTestType, Patient patient, String orderNumber, String referenceNumber,
	        Concept orderConcept, Provider orderer, Date from, Date to, boolean includeVoided);
	
	/**
	 * Returns {@link LabTestSample} object by generated Id
	 * 
	 * @param labTestSampleId
	 * @return
	 */
	LabTestSample getLabTestSample(Integer labTestSampleId);
	
	/**
	 * Returns {@link LabTestSample} object by UUID
	 * 
	 * @param uuid
	 * @return
	 */
	LabTestSample getLabTestSampleByUuid(String uuid);
	
	/**
	 * Returns list of {@link LabTestSample} objects by given {@link LabTest}
	 * 
	 * @param labTest
	 * @param includeVoided
	 * @return
	 */
	List<LabTestSample> getLabTestSamples(LabTest labTest, boolean includeVoided);
	
	/**
	 * Returns list of {@link LabTestSample} objects by given {@link Provider} object as collector
	 * 
	 * @param collector
	 * @param includeVoided
	 * @return
	 */
	List<LabTestSample> getLabTestSamples(Provider collector, boolean includeVoided);
	
	/**
	 * Returns list of {@link LabTestSample} objects by given {@link Patient} object
	 * 
	 * @param patient
	 * @param includeVoided
	 * @return
	 */
	List<LabTestSample> getLabTestSamples(Patient patient, boolean includeVoided);
	
	/**
	 * Returns {@link LabTestType} object by generated Id
	 * 
	 * @param labTestTypeId
	 * @return
	 */
	LabTestType getLabTestType(Integer labTestTypeId);
	
	/**
	 * Returns {@link LabTestType} object by UUID
	 * 
	 * @param uuid
	 * @return
	 */
	LabTestType getLabTestTypeByUuid(String uuid);
	
	/**
	 * Returns a list of 'n' number of {@link LabTest} objects. If firstNObjects is true, then
	 * earliest 'n' objects are returned; if lastNObjects is true, then latest 'n' objects are
	 * returned. If both a true, then a union of both results is returned. Maximum number of objects
	 * to return is limited by MAX_FETCH_LIMIT
	 * 
	 * @param patient
	 * @param n
	 * @param firstNObjects
	 * @param lastNObjects
	 * @param includeVoided
	 * @return
	 */
	List<LabTest> getNLabTests(Patient patient, int n, boolean firstNObjects, boolean lastNObjects, boolean includeVoided);
	
	/**
	 * Returns a list of 'n' number of {@link LabTestSample} objects by matching {@link Patient} and
	 * {@link LabTestSampleStatus} (optional, pass null to ignore). If firstNObjects is true, then
	 * earliest 'n' objects are returned; if lastNObjects is true, then latest 'n' objects are
	 * returned. If both a true, then a union of both results is returned. Maximum number of objects
	 * to return is limited by MAX_FETCH_LIMIT
	 * 
	 * @param patient
	 * @param status
	 * @param n
	 * @param firstNObjects
	 * @param lastNObjects
	 * @param includeVoided
	 * @return
	 */
	List<LabTestSample> getNLabTestSamples(Patient patient, LabTestSampleStatus status, int n, boolean firstNObjects,
	        boolean lastNObjects, boolean includeVoided);
	
	/**
	 * Permanently delete {@link LabTest}
	 * 
	 * @param labTest
	 */
	void purgeLabTest(LabTest labTest);
	
	/**
	 * Permanently delete {@link LabTestAttribute}
	 * 
	 * @param labTestAttribute
	 */
	void purgeLabTestAttribute(LabTestAttribute labTestAttribute);
	
	/**
	 * Permanently delete {@link LabTestAttributeType}
	 * 
	 * @param labTestAttributeType
	 */
	void purgeLabTestAttributeType(LabTestAttributeType labTestAttributeType);
	
	/**
	 * Permanently delete {@link LabTestSample}
	 * 
	 * @param labTestSample
	 */
	void purgeLabTestSample(LabTestSample labTestSample);
	
	/**
	 * Permanently delete {@link LabTestType}
	 * 
	 * @param labTestType
	 */
	void purgeLabTestType(LabTestType labTestType);
	
	/**
	 * Persists {@link LabTest} in database
	 * 
	 * @param labTest
	 * @return
	 */
	LabTest saveLabTest(LabTest labTest);
	
	/**
	 * Persists {@link LabTestAttribute} in database
	 * 
	 * @param labTestAttribute
	 * @return
	 */
	LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute);
	
	/**
	 * Persists {@link LabTestAttributeType} in database
	 * 
	 * @param labTestAttributeType
	 * @return
	 */
	LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType);
	
	/**
	 * Persists {@link LabTestSample} in database
	 * 
	 * @param labTestSample
	 * @return
	 */
	LabTestSample saveLabTestSample(LabTestSample labTestSample);
	
	/**
	 * Persists {@link LabTestType} in database
	 * 
	 * @param labTestType
	 * @return
	 */
	LabTestType saveLabTestType(LabTestType labTestType);
	
}
