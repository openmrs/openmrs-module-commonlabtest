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

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.dao.CommonLabTestDAO;

public class CommonLabTestServiceImpl extends BaseOpenmrsService {
	
	CommonLabTestDAO dao;
	
	UserService userService;
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(CommonLabTestDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public LabTestType getLabTestTypeByUuid(String uuid) throws APIException {
		return dao.getLabTestTypeByUuid(uuid);
	}
	
	public List<LabTestType> getAllLabTestTypes() {
		return getAllLabTestTypes(false);
	}
	
	public List<LabTestType> getAllLabTestTypes(boolean includeRetired) {
		return dao.getAllLabTestTypes(includeRetired);
	}
	
	public LabTestType getLabTestType(LabTestType labTestType) {
		return dao.getLabTestType(labTestType.getId());
	}
	
	public LabTestType saveLabTestType(LabTestType labTestType) {
		return dao.saveLabTestType(labTestType);
	}
	
	public void deleteLabTestType(LabTestType labTestType) {
		// TODO: Check constraints here
		dao.purgeLabTestType(labTestType);
	}
	
	public List<LabTestAttributeType> getAllLabTestAttributeTypes() {
		return getAllLabTestAttributeTypes(false);
	}
	
	public List<LabTestAttributeType> getAllLabTestAttributeTypes(boolean includeRetired) {
		return dao.getAllLabTestAttributeTypes(includeRetired);
	}
	
	public List<LabTestAttributeType> getLabTestAttributesByTestType(LabTestType labTestType, boolean includeRetired) {
		return dao.getLabTestAttributesByTestType(labTestType.getId(), includeRetired);
	}
	
	public LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId) {
		return dao.getLabTestAttributeType(labTestAttributeTypeId);
	}
	
	public LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) {
		// TODO: See if previously exists
		return dao.saveLabTestAttributeType(labTestAttributeType);
	}
	
	public void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType) {
		// TODO: Check constraints here
		dao.purgeLabTestAttributeType(labTestAttributeType);
	}
	
	public LabTest getLabTest(Integer labTestId) {
		return dao.getLabTest(labTestId);
	}
	
	public List<LabTest> getLabTestsByPatient(Patient patient, boolean includeVoided) {
		return dao.getLabTestsByPatient(patient.getId(), includeVoided);
	}
	
	public List<LabTest> getLabTestsByPatientAndType(Patient patient, LabTestType labTestType, boolean includeVoided) {
		return dao.getLabTestsByPatientAndType(patient.getId(), labTestType.getId(), includeVoided);
	}
	
	public List<LabTest> getLabTestsBySample(Integer labTestSampleId, boolean includeVoided) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public LabTest saveLabTest(LabTest labTest) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void purgeLabTest(LabTest labTest) {
		// TODO Auto-generated method stub
		
	}
	
	public List<LabTestAttributeType> getLabTestAttributesByAttributeType(Integer labTestAttributeTypeId,
	        boolean includeRetired) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<LabTestAttributeType> getLabTestAttributesByPatient(Integer patientId, boolean includeRetired) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<LabTestAttributeType> getLabTestAttributesByPatientAndAttributeType(Integer labTestAttributeTypeId,
	        Integer patientId, boolean includeRetired) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public LabTestAttributeType getLabTestAttribute(Integer labTestAttributeId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void purgeLabTestAttribute(LabTestAttribute labTestAttribute) {
		// TODO Auto-generated method stub
	}
	
}
