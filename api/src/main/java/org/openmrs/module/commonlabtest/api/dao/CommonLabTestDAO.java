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

public interface CommonLabTestDAO {

	/**
	 * @param includeRetired include retired objects
	 * @return {@link LabTestAttributeType} objects
	 */
	List<LabTestAttributeType> getAllLabTestAttributeTypes(boolean includeRetired);

	/**
	 * @param includeRetired include retired objects
	 * @return {@link LabTestType} objects
	 */
	List<LabTestType> getAllLabTestTypes(boolean includeRetired);

	/**
	 * @param name the name of lab test type
	 * @param shortName the short name
	 * @param testGroup the {@link LabTestGroup} object
	 * @param referenceConcept the {@link Concept} object
	 * @param includeRetired include retired objects
	 * @return {@link LabTestType} objects
	 */
	List<LabTestType> getLabTestTypes(String name, String shortName, LabTestGroup testGroup, Concept referenceConcept,
	        boolean includeRetired);

	/**
	 * @param order the {@link Order} object
	 * @return {@link LabTest} object by matching given {@link Order} object
	 */
	LabTest getLabTest(Order order);

	/**
	 * @param labTestId the Id
	 * @return {@link LabTest} object
	 */
	LabTest getLabTest(Integer labTestId);

	/**
	 * @param labTestAttributeId the Id
	 * @return {@link LabTestAttribute} object
	 */
	LabTestAttribute getLabTestAttribute(Integer labTestAttributeId);

	/**
	 * @param uuid the unique Id
	 * @return {@link LabTestAttribute} object
	 */
	LabTestAttribute getLabTestAttributeByUuid(String uuid);

	/**
	 * @param testOrderId the Id
	 * @return {@link LabTestAttribute} object(s)
	 */
	List<LabTestAttribute> getLabTestAttributes(Integer testOrderId);

	/**
	 * @param labTestAttributeType the {@link LabTestAttributeType} object
	 * @param valueReference the reference value
	 * @param from the start {@link Date} object
	 * @param to the end {@link Date} object
	 * @param includeVoided include retired objects
	 * @return {@link LabTestAttribute} object(s)
	 */
	List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, String valueReference, Date from,
	        Date to, boolean includeVoided);

	/**
	 * @param patient the {@link Patient} object
	 * @param labTestAttributeType the {@link LabTestAttributeType} object
	 * @param includeVoided include retired objects
	 * @return {@link LabTestAttribute} object(s)
	 */
	List<LabTestAttribute> getLabTestAttributes(Patient patient, LabTestAttributeType labTestAttributeType,
	        boolean includeVoided);

	/**
	 * @param labTestAttributeTypeId the Id
	 * @return {@link LabTestAttributeType} object
	 */
	LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId);

	/**
	 * @param uuid the unique Id
	 * @return {@link LabTestAttributeType} object
	 */
	LabTestAttributeType getLabTestAttributeTypeByUuid(String uuid);

	/**
	 * @param name the name
	 * @param datatypeClassname the name of fully specified data type class
	 * @param includeRetired include retired objects
	 * @return {@link LabTestAttributeType} object(s)
	 */
	List<LabTestAttributeType> getLabTestAttributeTypes(String name, String datatypeClassname, boolean includeRetired);

	/**
	 * @param labTestType {@link LabTestType} object
	 * @param includeRetired include retired objects
	 * @return {@link LabTestAttributeType} object(s)
	 */
	List<LabTestAttributeType> getLabTestAttributeTypes(LabTestType labTestType, boolean includeRetired);

	/**
	 * @param uuid the unique Id
	 * @return {@link LabTest} object
	 */
	LabTest getLabTestByUuid(String uuid);

	/**
	 * @param labTestType the {@link LabTestType} object
	 * @param patient the {@link Patient} object
	 * @param orderNumber the order number
	 * @param referenceNumber the reference number
	 * @param orderConcept the {@link Order} concept object
	 * @param orderer the {@link Provider} object
	 * @param from the start {@link Date} object
	 * @param to the end {@link Date} object
	 * @param includeVoided include retired objects
	 * @return {@link LabTest} object(s)
	 */
	List<LabTest> getLabTests(LabTestType labTestType, Patient patient, String orderNumber, String referenceNumber,
	        Concept orderConcept, Provider orderer, Date from, Date to, boolean includeVoided);

	/**
	 * @param labTestSampleId the generated Id
	 * @return {@link LabTestSample} object
	 */
	LabTestSample getLabTestSample(Integer labTestSampleId);

	/**
	 * @param uuid the unique Id
	 * @return {@link LabTestSample} object
	 */
	LabTestSample getLabTestSampleByUuid(String uuid);

	/**
	 * @param labTest the {@link LabTest} object
	 * @param includeVoided include retired objects
	 * @return {@link LabTestSample} object(s)
	 */
	List<LabTestSample> getLabTestSamples(LabTest labTest, boolean includeVoided);

	/**
	 * @param collector the {@link Provider} object
	 * @param includeVoided include retired objects
	 * @return {@link LabTestSample} object(s)
	 */
	List<LabTestSample> getLabTestSamples(Provider collector, boolean includeVoided);

	/**
	 * @param patient the {@link Patient} object
	 * @param includeVoided include retired objects
	 * @return {@link LabTestSample} object(s)
	 */
	List<LabTestSample> getLabTestSamples(Patient patient, boolean includeVoided);

	/**
	 * @param labTestTypeId the generated Id
	 * @return {@link LabTestType} object
	 */
	LabTestType getLabTestType(Integer labTestTypeId);

	/**
	 * @param uuid the unique Id
	 * @return {@link LabTestType} object
	 */
	LabTestType getLabTestTypeByUuid(String uuid);

	/**
	 * Returns a list of 'n' number of {@link LabTest} objects. If firstNObjects is true, then earliest
	 * 'n' objects are returned; if lastNObjects is true, then latest 'n' objects are returned. If both
	 * a true, then a union of both results is returned. Maximum number of objects to return is limited
	 * by MAX_FETCH_LIMIT
	 * 
	 * @param patient the {@link Patient} object
	 * @param n the number of objects to return
	 * @param firstNObjects whether to return initial n objects
	 * @param lastNObjects whether to return last n objects
	 * @param includeVoided include retired objects
	 * @return {@link LabTest} object
	 */
	List<LabTest> getNLabTests(Patient patient, int n, boolean firstNObjects, boolean lastNObjects, boolean includeVoided);

	/**
	 * Returns a list of 'n' number of {@link LabTestSample} objects by matching {@link Patient} and
	 * {@link LabTestSampleStatus} (optional, pass null to ignore). If firstNObjects is true, then
	 * earliest 'n' objects are returned; if lastNObjects is true, then latest 'n' objects are returned.
	 * If both a true, then a union of both results is returned. Maximum number of objects to return is
	 * limited by MAX_FETCH_LIMIT
	 * 
	 * @param patient the {@link Patient} object
	 * @param status the {@link LabTestSampleStatus} object
	 * @param n the number of objects to return
	 * @param firstNObjects whether to return initial n objects
	 * @param lastNObjects whether to return last n objects
	 * @param includeVoided include retired objects
	 * @return {@link LabTestSample} object
	 */
	List<LabTestSample> getNLabTestSamples(Patient patient, LabTestSampleStatus status, int n, boolean firstNObjects,
	        boolean lastNObjects, boolean includeVoided);

	/**
	 * @param labTest the {@link LabTest} object to delete
	 */
	void purgeLabTest(LabTest labTest);

	/**
	 * @param labTestAttribute the {@link LabTestAttribute} object to delete
	 */
	void purgeLabTestAttribute(LabTestAttribute labTestAttribute);

	/**
	 * @param labTestAttributeType the {@link LabTestAttributeType} object to delete
	 */
	void purgeLabTestAttributeType(LabTestAttributeType labTestAttributeType);

	/**
	 * @param labTestSample the {@link LabTestSample} object to delete
	 */
	void purgeLabTestSample(LabTestSample labTestSample);

	/**
	 * @param labTestType the {@link LabTestType} object to delete
	 */
	void purgeLabTestType(LabTestType labTestType);

	/**
	 * Persists {@link LabTest} in database. This method also persists {@link Order} entity, because
	 * unlike {@link Order}, the {@link LabTest} is not hierarchical
	 * 
	 * @param labTest the {@link LabTest} object to save
	 * @return saved {@link LabTest} object
	 */
	LabTest saveLabTest(LabTest labTest);

	/**
	 * @param labTestAttribute the {@link LabTestAttribute} object to save
	 * @return saved {@link LabTestAttribute} object
	 */
	LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute);

	/**
	 * @param labTestAttributeType the {@link LabTestAttributeType} object to save
	 * @return saved {@link LabTestAttributeType} object
	 */
	LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType);

	/**
	 * @param labTestSample the {@link LabTestSample} object to save
	 * @return saved {@link LabTestSample} object
	 */
	LabTestSample saveLabTestSample(LabTestSample labTestSample);

	/**
	 * @param labTestType the {@link LabTestType} object to save
	 * @return saved {@link LabTestType} object
	 */
	LabTestType saveLabTestType(LabTestType labTestType);

}
