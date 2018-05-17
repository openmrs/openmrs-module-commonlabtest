/**
w * This Source Code Form is subject to the terms of the Mozilla Public License,
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
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.commonlabtest.CommonLabTestConfig;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.LabTestType.LabTestGroup;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.api.dao.CommonLabTestDao;
import org.openmrs.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonLabTestServiceImpl extends BaseOpenmrsService implements CommonLabTestService {
	
	CommonLabTestDao dao;
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(CommonLabTestDao dao) {
		this.dao = dao;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getAllLabTestAttributeTypes(boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttributeType> getAllLabTestAttributeTypes(boolean includeRetired) throws APIException {
		return dao.getAllLabTestAttributeTypes(includeRetired);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getAllLabTestTypes(boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestType> getAllLabTestTypes(boolean includeRetired) throws APIException {
		return dao.getAllLabTestTypes(includeRetired);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getEarliestLabTest(org.openmrs.Patient)
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
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getEarliestLabTestSample(org.openmrs.Patient,
	 *      org.openmrs.module.commonlabtest.LabTestSampleStatus)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestSample getEarliestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException {
		List<LabTestSample> labTestSamples = dao.getNLabTestSamples(patient, status, 1, true, false, false);
		if (!labTestSamples.isEmpty()) {
			return labTestSamples.get(0);
		}
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTest(java.lang.Integer)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTest getLabTest(Integer labTestId) throws APIException {
		return dao.getLabTest(labTestId);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttribute(java.lang.Integer)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestAttribute getLabTestAttribute(Integer labTestAttributeId) throws APIException {
		return dao.getLabTestAttribute(labTestAttributeId);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttributeByUuid(java.lang.String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestAttribute getLabTestAttributeByUuid(String uuid) throws APIException {
		return dao.getLabTestAttributeByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttributes(org.openmrs.module.commonlabtest.LabTestAttributeType,
	 *      org.openmrs.Patient, java.lang.String, java.util.Date, java.util.Date, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, Patient patient,
	        String valueReference, Date from, Date to, boolean includeVoided) throws APIException {
		return dao.getLabTestAttributes(labTestAttributeType, null, patient, valueReference, from, to, includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttributes(org.openmrs.module.commonlabtest.LabTestAttributeType,
	 *      boolean)
	 */
	@Override
	public List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, boolean includeVoided)
	        throws APIException {
		return getLabTestAttributes(labTestAttributeType, null, null, null, null, includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttributes(org.openmrs.Patient,
	 *      boolean)
	 */
	@Override
	public List<LabTestAttribute> getLabTestAttributes(Patient patient, boolean includeVoided) throws APIException {
		return getLabTestAttributes(null, patient, null, null, null, includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttributes(org.openmrs.Patient,
	 *      org.openmrs.module.commonlabtest.LabTestAttributeType, boolean)
	 */
	@Override
	public List<LabTestAttribute> getLabTestAttributes(Patient patient, LabTestAttributeType labTestAttributeType,
	        boolean includeVoided) throws APIException {
		return getLabTestAttributes(labTestAttributeType, patient, null, null, null, includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttributeType(java.lang.Integer)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId) throws APIException {
		return dao.getLabTestAttributeType(labTestAttributeTypeId);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttributeTypeByUuid(java.lang.String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestAttributeType getLabTestAttributeTypeByUuid(String uuid) throws APIException {
		return dao.getLabTestAttributeTypeByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttributeTypes(java.lang.String,
	 *      java.lang.String, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestAttributeType> getLabTestAttributeTypes(String name, String datatypeClassname, boolean includeRetired)
	        throws APIException {
		return dao.getLabTestAttributeTypes(name, datatypeClassname, includeRetired);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestByUuid(java.lang.String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTest getLabTestByUuid(String uuid) throws APIException {
		return dao.getLabTestByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTest(org.openmrs.Order)
	 */
	@Override
	public LabTest getLabTest(Order order) throws APIException {
		return dao.getLabTest(order);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSample(java.lang.Integer)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestSample getLabTestSample(Integer labTestSampleId) throws APIException {
		return dao.getLabTestSample(labTestSampleId);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSampleByUuid(java.lang.String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestSample getLabTestSampleByUuid(String uuid) throws APIException {
		return dao.getLabTestSampleByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSamples(org.openmrs.module.commonlabtest.LabTest,
	 *      org.openmrs.Patient, org.openmrs.module.commonlabtest.LabTestSampleStatus,
	 *      java.lang.String, org.openmrs.Provider, java.util.Date, java.util.Date, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestSample> getLabTestSamples(LabTest labTest, Patient patient, LabTestSampleStatus status,
	        String labSampleIdentifier, Provider collector, Date from, Date to, boolean includeVoided) throws APIException {
		// TODO
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSamples(java.lang.String,
	 *      java.lang.String, java.lang.String, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTestSample> getLabTestSamples(String labSampleIdentifier, String orderNumber, String labReferenceNumber,
	        boolean includeVoided) throws APIException {
		return getLabTestSamples(null, null, null, labSampleIdentifier, null, null, null, includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSamples(org.openmrs.module.commonlabtest.LabTest,
	 *      boolean)
	 */
	@Override
	public List<LabTestSample> getLabTestSamples(LabTest labTest, boolean includeVoided) throws APIException {
		return dao.getLabTestSamples(labTest, includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSamples(org.openmrs.Patient,
	 *      boolean)
	 */
	@Override
	public List<LabTestSample> getLabTestSamples(Patient patient, boolean includeVoided) throws APIException {
		return dao.getLabTestSamples(patient, includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSamples(org.openmrs.Provider,
	 *      boolean)
	 */
	@Override
	public List<LabTestSample> getLabTestSamples(Provider collector, boolean includeVoided) throws APIException {
		return dao.getLabTestSamples(collector, includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSamples(org.openmrs.module.commonlabtest.LabTestSampleStatus,
	 *      java.util.Date, java.util.Date, boolean)
	 */
	@Override
	public List<LabTestSample> getLabTestSamples(LabTestSampleStatus status, Date from, Date to, boolean includeVoided)
	        throws APIException {
		return getLabTestSamples(null, null, status, null, null, from, to, includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestType(java.lang.Integer)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestType getLabTestType(Integer labTestTypeId) throws APIException {
		return dao.getLabTestType(labTestTypeId);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestTypeByUuid(java.lang.String)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional(readOnly = true)
	public LabTestType getLabTestTypeByUuid(String uuid) throws APIException {
		return dao.getLabTestTypeByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestTypes(java.lang.String,
	 *      java.lang.String, org.openmrs.module.commonlabtest.LabTestGroup, java.lang.Boolean,
	 *      org.openmrs.Concept, boolean)
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
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTests(org.openmrs.module.commonlabtest.LabTestType,
	 *      org.openmrs.Patient, org.openmrs.module.commonlabtest.LabTestSample, java.lang.String,
	 *      java.lang.String, org.openmrs.Concept, org.openmrs.Provider, java.util.Date,
	 *      java.util.Date, boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(LabTestType labTestType, Patient patient, String orderNumber, String referenceNumber,
	        Concept orderConcept, Provider orderer, Date from, Date to, boolean includeVoided) throws APIException {
		return dao.getLabTests(labTestType, patient, orderNumber, referenceNumber, orderConcept, orderer, from, to,
		    includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTests(org.openmrs.module.commonlabtest.LabTestType,
	 *      boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(LabTestType labTestType, boolean includeVoided) throws APIException {
		return getLabTests(labTestType, null, null, null, null, null, null, null, includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTests(org.openmrs.Concept,
	 *      boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(Concept orderConcept, boolean includeVoided) throws APIException {
		return getLabTests(null, null, null, null, orderConcept, null, null, null, includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTests(org.openmrs.Provider,
	 *      boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(Provider orderer, boolean includeVoided) throws APIException {
		return getLabTests(null, null, null, null, null, orderer, null, null, includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTests(org.openmrs.Patient,
	 *      boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE)
	@Transactional(readOnly = true)
	public List<LabTest> getLabTests(Patient patient, boolean includeVoided) throws APIException {
		return getLabTests(null, patient, null, null, null, null, null, null, includeVoided);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTests(java.lang.String,
	 *      boolean)
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
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLatestLabTest(org.openmrs.Patient)
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
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLatestLabTestSample(org.openmrs.Patient,
	 *      org.openmrs.module.commonlabtest.LabTestSampleStatus)
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
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTest(org.openmrs.module.commonlabtest.LabTest)
	 */
	@Override
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	public LabTest saveLabTest(LabTest labTest) throws APIException {
		return dao.saveLabTest(labTest);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTest(org.openmrs.module.commonlabtest.LabTest,
	 *      org.openmrs.module.commonlabtest.LabTestSample, java.util.Collection)
	 */
	@Override
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	public LabTest saveLabTest(LabTest labTest, LabTestSample labTestSample, Collection<LabTestAttribute> labTestAttributes)
	        throws APIException {
		LabTest savedLabTest = saveLabTest(labTest);
		LabTestSample saveLabTestSample = saveLabTestSample(labTestSample);
		labTest.removeLabTestSample(labTestSample);
		labTest.addLabTestSample(saveLabTestSample);
		Set<LabTestAttribute> attributes = new HashSet<LabTestAttribute>();
		for (LabTestAttribute labTestAttribute : labTestAttributes) {
			attributes.add(saveLabTestAttribute(labTestAttribute));
		}
		savedLabTest.setAttributes(attributes);
		return savedLabTest;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)
	 */
	@Override
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	public LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		return dao.saveLabTestAttribute(labTestAttribute);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTestAttributes(java.util.List)
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
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)
	 */
	@Override
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		return dao.saveLabTestAttributeType(labTestAttributeType);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)
	 */
	@Override
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@Transactional
	public LabTestSample saveLabTestSample(LabTestSample labTestSample) throws APIException {
		return dao.saveLabTestSample(labTestSample);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTestType(org.openmrs.module.commonlabtest.LabTestType)
	 */
	@Override
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public LabTestType saveLabTestType(LabTestType labTestType) throws APIException {
		return dao.saveLabTestType(labTestType);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#retireLabTestType(org.openmrs.module.commonlabtest.LabTestType)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void retireLabTestType(LabTestType labTestType, String retireReason) throws APIException {
		if (labTestType.getRetired()) {
			throw new APIException("Object has alread been retired.");
		}
		labTestType.setRetired(Boolean.TRUE);
		labTestType.setRetiredBy(Context.getAuthenticatedUser());
		labTestType.setRetireReason(retireReason);
		dao.saveLabTestType(labTestType);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#retireLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)
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
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#unretireLabTestType(org.openmrs.module.commonlabtest.LabTestType)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void unretireLabTestType(LabTestType labTestType) throws APIException {
		labTestType.setRetired(Boolean.FALSE);
		labTestType.setRetireReason("Previously retired for reason: " + labTestType.getRetireReason());
		dao.saveLabTestType(labTestType);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#unretireLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void unretireLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		labTestAttributeType.setRetired(Boolean.FALSE);
		labTestAttributeType.setRetireReason("Previously retired for reason: " + labTestAttributeType.getRetireReason());
		dao.saveLabTestAttributeType(labTestAttributeType);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#voidLabTest(org.openmrs.module.commonlabtest.LabTest)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void voidLabTest(LabTest labTest, String voidReason) throws APIException {
		for (LabTestSample sample : labTest.getLabTestSamples()) {
			voidLabTestSample(sample, voidReason);
		}
		for (LabTestAttribute attribute : labTest.getAttributes()) {
			voidLabTestAttribute(attribute, voidReason);
		}
		labTest.setVoided(true);
		labTest.setVoidedBy(Context.getAuthenticatedUser());
		labTest.setVoidReason(voidReason);
		dao.saveLabTest(labTest);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#voidLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void voidLabTestAttribute(LabTestAttribute labTestAttribute, String voidReason) throws APIException {
		labTestAttribute.setVoided(true);
		labTestAttribute.setVoidedBy(Context.getAuthenticatedUser());
		labTestAttribute.setVoidReason(voidReason);
		dao.saveLabTestAttribute(labTestAttribute);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#voidLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void voidLabTestSample(LabTestSample labTestSample, String voidReason) throws APIException {
		labTestSample.setVoided(true);
		labTestSample.setVoidedBy(Context.getAuthenticatedUser());
		labTestSample.setVoidReason(voidReason);
		dao.saveLabTestSample(labTestSample);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#unvoidLabTest(org.openmrs.module.commonlabtest.LabTest)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void unvoidLabTest(LabTest labTest) throws APIException {
		for (LabTestSample sample : labTest.getLabTestSamples()) {
			boolean reasonMatched = sample.getVoidReason().equals(labTest.getVoidReason());
			boolean dateMatched = DateUtil.truncateToSeconds(labTest.getDateVoided()).compareTo(sample.getDateVoided()) == 0;
			if (reasonMatched && dateMatched) {
				unvoidLabTestSample(sample);
			}
		}
		for (LabTestAttribute attribute : labTest.getAttributes()) {
			boolean reasonMatched = attribute.getVoidReason().equals(labTest.getVoidReason());
			boolean dateMatched = DateUtil.truncateToSeconds(labTest.getDateVoided()).compareTo(attribute.getDateVoided()) == 0;
			if (reasonMatched && dateMatched) {
				unvoidLabTestAttribute(attribute);
			}
		}
		dao.saveLabTest(labTest);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#unvoidLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void unvoidLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		labTestAttribute.setVoided(false);
		labTestAttribute.setVoidReason("Previously voided for reason: " + labTestAttribute.getVoidReason());
		dao.saveLabTestAttribute(labTestAttribute);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#unvoidLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void unvoidLabTestSample(LabTestSample labTestSample) throws APIException {
		labTestSample.setVoided(false);
		labTestSample.setVoidReason("Previously voided for reason: " + labTestSample.getVoidReason());
		dao.saveLabTestSample(labTestSample);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTest(org.openmrs.module.commonlabtest.LabTest)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void deleteLabTest(LabTest labTest) throws APIException {
		dao.purgeLabTest(labTest);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void deleteLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		dao.purgeLabTestAttribute(labTestAttribute);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
	public void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		deleteLabTestAttributeType(labTestAttributeType, false);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType,
	 *      boolean)
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
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_PRIVILEGE)
	@Transactional
	public void deleteLabTestSample(LabTestSample labTestSample) throws APIException {
		dao.purgeLabTestSample(labTestSample);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestType(org.openmrs.module.commonlabtest.LabTestType)
	 */
	@Override
	public void deleteLabTestType(LabTestType labTestType) throws APIException {
		deleteLabTestType(labTestType, false);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestType(org.openmrs.module.commonlabtest.LabTestType,
	 *      boolean)
	 */
	@Override
	@Authorized(CommonLabTestConfig.DELETE_LAB_TEST_METADATA_PRIVILEGE)
	@Transactional
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
}
