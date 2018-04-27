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
import java.util.List;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.api.APIException;
import org.openmrs.api.OrderService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestGroup;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.api.dao.CommonLabTestDAO;

public class CommonLabTestServiceImpl extends BaseOpenmrsService implements CommonLabTestService {
	
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
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTest(org.openmrs.module.commonlabtest.LabTest)
	 */
	@Override
	public void deleteLabTest(LabTest labTest) throws APIException {
		dao.purgeLabTest(labTest);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)
	 */
	@Override
	public void deleteLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		dao.purgeLabTestAttribute(labTestAttribute);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)
	 */
	@Override
	public void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		deleteLabTestAttributeType(labTestAttributeType, false);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#deleteLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType,
	 *      boolean)
	 */
	@Override
	public void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType, boolean cascade) throws APIException {
		if (cascade) {
			List<LabTest> labTests = dao.getLabTestsByAttributeType(labTestAttributeType);
			for (LabTest labTest : labTests) {
				try {
					orderService.discontinueOrder(labTest.getOrder(), "Lab Test deleted", new Date(),
					    labTest.getOrder().getOrderer(), labTest.getOrder().getEncounter());
					dao.purgeLabTest(labTest);
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
	public void deleteLabTestType(LabTestType labTestType, boolean cascade) throws APIException {
		if (cascade) {
			List<LabTest> labTests = dao.getLabTestsByType(labTestType);
			for (LabTest labTest : labTests) {
				dao.purgeLabTest(labTest);
			}
		}
		dao.purgeLabTestType(labTestType);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTest(org.openmrs.module.commonlabtest.LabTest)
	 */
	@Override
	public LabTest saveLabTest(LabTest labTest) throws APIException {
		return dao.saveLabTest(labTest);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTest(org.openmrs.module.commonlabtest.LabTest,
	 *      org.openmrs.module.commonlabtest.LabTestSample, java.util.Collection)
	 */
	@Override
	public LabTest saveLabTest(LabTest labTest, LabTestSample labTestSample,
	        Collection<LabTestAttribute> labTestAttributes) throws APIException {
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
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)
	 */
	@Override
	public LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute) throws APIException {
		return dao.saveLabTestAttribute(labTestAttribute);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTestAttributes(java.util.List)
	 */
	@Override
	public List<LabTestAttribute> saveLabTestAttributes(List<LabTestAttribute> labTestAttributes) throws APIException {
		for (LabTestAttribute labTestAttribute : labTestAttributes) {
			labTestAttribute = saveLabTestAttribute(labTestAttribute);
		}
		return labTestAttributes;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)
	 */
	@Override
	public LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		return dao.saveLabTestAttributeType(labTestAttributeType);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)
	 */
	@Override
	public LabTestSample saveLabTestSample(LabTestSample labTestSample) throws APIException {
		return dao.saveLabTestSample(labTestSample);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#saveLabTestType(org.openmrs.module.commonlabtest.LabTestType)
	 */
	@Override
	public LabTestType saveLabTestType(LabTestType labTestType) throws APIException {
		return dao.saveLabTestType(labTestType);
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#findLabTestAttributes(org.openmrs.module.commonlabtest.LabTestAttributeType,
	 *      org.openmrs.Patient, java.lang.String, java.util.Date, java.util.Date, boolean)
	 */
	@Override
	public List<LabTestAttribute> findLabTestAttributes(LabTestAttributeType labTestAttributeType, Patient patient,
	        String valueReference, Date from, Date to, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#findLabTestAttributeTypes(java.lang.String,
	 *      java.util.Date, java.util.Date, java.lang.String, boolean)
	 */
	@Override
	public List<LabTestAttributeType> findLabTestAttributeTypes(String name, Date from, Date to, String datatypeClassname,
	        boolean includeRetired) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#findLabTests(org.openmrs.module.commonlabtest.LabTestType,
	 *      org.openmrs.Patient, java.lang.String, java.lang.String, java.util.Date, java.util.Date,
	 *      boolean)
	 */
	@Override
	public List<LabTest> findLabTests(LabTestType labTestType, Patient patient, String orderNumber, String referenceNumber,
	        Date from, Date to, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#findLabTestSamples(org.openmrs.module.commonlabtest.LabTest,
	 *      org.openmrs.Patient, java.lang.String, java.lang.String,
	 *      org.openmrs.module.commonlabtest.LabTestSampleStatus, java.util.Date, java.util.Date,
	 *      boolean)
	 */
	@Override
	public List<LabTestSample> findLabTestSamples(LabTest labTest, Patient patient, String labSampleIdentifier,
	        String specimenName, LabTestSampleStatus status, Date from, Date to, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#findLabTestTypes(java.lang.String,
	 *      java.lang.String, org.openmrs.module.commonlabtest.LabTestGroup, java.lang.Boolean,
	 *      org.openmrs.Concept, boolean)
	 */
	@Override
	public List<LabTestType> findLabTestTypes(String name, String shortName, LabTestGroup testGroup,
	        Boolean isSpecimenRequired, Concept referenceConcept, boolean includeRetired) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getAllLabTestAttributeTypes(boolean)
	 */
	@Override
	public List<LabTestAttributeType> getAllLabTestAttributeTypes(boolean includeRetired) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getAllLabTestTypes(boolean)
	 */
	@Override
	public List<LabTestType> getAllLabTestTypes(boolean includeRetired) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getEarliestLabTestAttribute(org.openmrs.Patient,
	 *      org.openmrs.module.commonlabtest.LabTestAttributeType)
	 */
	@Override
	public LabTestAttribute getEarliestLabTestAttribute(Patient patient, LabTestAttributeType labTestAttributeType)
	        throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getEarliestLabTestByPatient(org.openmrs.Patient)
	 */
	@Override
	public LabTest getEarliestLabTestByPatient(Patient patient) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getEarliestLabTestSample(org.openmrs.Patient,
	 *      org.openmrs.module.commonlabtest.LabTestSampleStatus)
	 */
	@Override
	public LabTestSample getEarliestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTest(java.lang.Integer)
	 */
	@Override
	public LabTest getLabTest(Integer labTestId) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttribute(java.lang.Integer)
	 */
	@Override
	public LabTestAttribute getLabTestAttribute(Integer labTestAttributeId) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttributeByUuid(java.lang.String)
	 */
	@Override
	public LabTestAttribute getLabTestAttributeByUuid(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttributesByAttributeTypeAndPatient(org.openmrs.module.commonlabtest.LabTestAttributeType,
	 *      org.openmrs.Patient, boolean)
	 */
	@Override
	public List<LabTestAttribute> getLabTestAttributesByAttributeTypeAndPatient(LabTestAttributeType labTestAttributeType,
	        Patient patient, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttributesByLabTest(org.openmrs.module.commonlabtest.LabTest,
	 *      boolean)
	 */
	@Override
	public List<LabTestAttribute> getLabTestAttributesByLabTest(LabTest labTest, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttributeType(java.lang.Integer)
	 */
	@Override
	public LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestAttributeTypeByUuid(java.lang.String)
	 */
	@Override
	public LabTestAttributeType getLabTestAttributeTypeByUuid(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestByOrderEncounter(org.openmrs.Encounter)
	 */
	@Override
	public LabTest getLabTestByOrderEncounter(Encounter orderEncounter) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestByOrderNumber(java.lang.String)
	 */
	@Override
	public LabTest getLabTestByOrderNumber(String orderNumber) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestByUuid(java.lang.String)
	 */
	@Override
	public LabTest getLabTestByUuid(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSample(java.lang.Integer)
	 */
	@Override
	public LabTestSample getLabTestSample(Integer labTestSampleId) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSampleByUuid(java.lang.String)
	 */
	@Override
	public LabTestSample getLabTestSampleByUuid(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSamplesByIdentifier(java.lang.String)
	 */
	@Override
	public List<LabTestSample> getLabTestSamplesByIdentifier(String labSampleIdentifier) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSamplesByLabTest(org.openmrs.module.commonlabtest.LabTest,
	 *      boolean)
	 */
	@Override
	public List<LabTestSample> getLabTestSamplesByLabTest(LabTest labTest, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSamplesByOrderEncounter(org.openmrs.Encounter)
	 */
	@Override
	public List<LabTestSample> getLabTestSamplesByOrderEncounter(Encounter encounter) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestSamplesByOrderNumber(java.lang.String)
	 */
	@Override
	public List<LabTestSample> getLabTestSamplesByOrderNumber(String orderNumber) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestsByOrderConcept(org.openmrs.Concept,
	 *      boolean)
	 */
	@Override
	public List<LabTest> getLabTestsByOrderConcept(Concept orderConcept, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestsByOrderer(org.openmrs.Provider,
	 *      boolean)
	 */
	@Override
	public List<LabTest> getLabTestsByOrderer(Provider orderer, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestsByPatient(org.openmrs.Patient,
	 *      boolean)
	 */
	@Override
	public List<LabTest> getLabTestsByPatient(Patient patient, boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestsByReferenceNumber(java.lang.String)
	 */
	@Override
	public List<LabTest> getLabTestsByReferenceNumber(String referenceNumber) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestType(java.lang.Integer)
	 */
	@Override
	public LabTestType getLabTestType(Integer labTestTypeId) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLabTestTypeByUuid(java.lang.String)
	 */
	@Override
	public LabTestType getLabTestTypeByUuid(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLatestLabTestAttribute(org.openmrs.Patient,
	 *      org.openmrs.module.commonlabtest.LabTestAttributeType)
	 */
	@Override
	public LabTestAttribute getLatestLabTestAttribute(Patient patient, LabTestAttributeType labTestAttributeType)
	        throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLatestLabTestByPatient(org.openmrs.Patient)
	 */
	@Override
	public LabTest getLatestLabTestByPatient(Patient patient) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.module.commonlabtest.api.CommonLabTestService#getLatestLabTestSample(org.openmrs.Patient,
	 *      org.openmrs.module.commonlabtest.LabTestSampleStatus)
	 */
	@Override
	public LabTestSample getLatestLabTestSample(Patient patient, LabTestSampleStatus status) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
}
