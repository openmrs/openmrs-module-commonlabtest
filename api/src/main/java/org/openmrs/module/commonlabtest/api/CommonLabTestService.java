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

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.commonlabtest.CommonLabTestConfig;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestGroup;
import org.openmrs.module.commonlabtest.LabTestType;
import org.springframework.transaction.annotation.Transactional;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface CommonLabTestService extends OpenmrsService {
	
	/**
	 * Returns an object by uuid. It can be called by any authenticated user. It is fetched in read
	 * only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTestType getLabTestTypeByUuid(String uuid) throws APIException;
	
	/**
	 * Returns an object by its ID
	 * 
	 * @param labTestTypeId
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTestType getLabTestType(Integer labTestTypeId) throws APIException;
	
	/**
	 * Returns list of all objects
	 * 
	 * @param includeRetired
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTestType> getAllLabTestTypes(boolean includeRetired) throws APIException;
	
	/**
	 * Find a list of objects using various parameters available. This is similar to
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
	 * Saves an object. Sets the owner to superuser, if it is not set. It can be called by users
	 * with this module's privilege. It is executed in a transaction.
	 * 
	 * @param labTestType
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	LabTestType saveLabTestType(LabTestType labTestType) throws APIException;
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#purgeLabTestType(LabTestType,
	 *      boolean)
	 */
	@Authorized(CommonLabTestConfig.PURGE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	void purgeLabTestType(LabTestType labTestType) throws APIException;
	
	/**
	 * Deletes an object. Enabling cascade will delete all the dependent objects and then purge this
	 * object
	 * 
	 * @param labTestType
	 * @param cascade
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.PURGE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	void purgeLabTestType(LabTestType labTestType, boolean cascade) throws APIException;
	
	/**
	 * Returns an object by uuid. It can be called by any authenticated user. It is fetched in read
	 * only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTestAttributeType getLabTestAttributeTypeByUuid(String uuid) throws APIException;
	
	/**
	 * Returns an object by its ID
	 * 
	 * @param labTestAttributeTypeId
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId) throws APIException;
	
	/**
	 * Returns list of all objects
	 * 
	 * @param includeRetired
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTestAttributeType> getAllLabTestAttributeTypes(boolean includeRetired) throws APIException;
	
	/**
	 * Find a list of objects using various parameters available. This is similar to
	 * getByExample(...) methods, except that instead of passing similar object, properties are
	 * passed as parameters
	 * 
	 * @param name
	 * @param datatypeClassname
	 * @param includeRetired
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTestAttributeType> findLabTestAttributeTypes(String name, String datatypeClassname, boolean includeRetired)
	        throws APIException;
	
	/**
	 * Saves an object. Sets the owner to superuser, if it is not set. It can be called by users
	 * with this module's privilege. It is executed in a transaction.
	 * 
	 * @param labTestAttributeType
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException;
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#purgeLabTestType(LabTestType,
	 *      boolean)
	 */
	@Authorized(CommonLabTestConfig.PURGE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	void purgeLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException;
	
	/**
	 * Deletes an object. Enabling cascade will delete all the dependent objects and then purge this
	 * object
	 * 
	 * @param labTestType
	 * @param cascade
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.PURGE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	void purgeLabTestAttributeType(LabTestAttributeType labTestAttributeType, boolean cascade) throws APIException;
	
	/**
	 * Returns an object by uuid. It can be called by any authenticated user. It is fetched in read
	 * only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTestAttribute getLabTestAttributeByUuid(String uuid) throws APIException;
	
	/**
	 * Returns an object by its ID
	 * 
	 * @param labTestAttributeTypeId
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	LabTestAttribute getLabTestAttribute(Integer labTestAttributeId) throws APIException;
	
	/**
	 * Returns list of all objects by given patient
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
	 * Returns list of all objects by given patient
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
	 * Find a list of objects using various parameters available. This is similar to
	 * getByExample(...) methods, except that instead of passing similar object, properties are
	 * passed as parameters
	 * 
	 * @param labTestAttributeType
	 * @param valueReference
	 * @param includeVoided
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	List<LabTestAttribute> findLabTestAttributes(LabTestAttributeType labTestAttributeType, Patient patient,
	        String valueReference, boolean includeVoided) throws APIException;
	
	/**
	 * Saves an object. Sets the owner to superuser, if it is not set. It can be called by users
	 * with this module's privilege. It is executed in a transaction.
	 * 
	 * @param labTestAttribute
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException;
	
	/**
	 * Saves a list of objects. Call this when a set of attribtues are being saved as a group
	 * 
	 * @param labTestAttributes
	 * @return
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	LabTestAttribute saveLabTestAttributes(List<LabTestAttribute> labTestAttributes) throws APIException;
	
	/**
	 * Deletes an object. Enabling cascade will delete all the dependent objects and then purge this
	 * object
	 * 
	 * @param labTestType
	 * @param cascade
	 * @throws APIException
	 */
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	void purgeLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException;
	
}
