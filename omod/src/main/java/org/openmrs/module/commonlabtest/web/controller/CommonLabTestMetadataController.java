/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.commonlabtest.web.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * This class configured as controller using annotation and mapped with the URL of
 * 'module/${rootArtifactid}/${rootArtifactid}Link.form'.
 */
@Controller
public class CommonLabTestMetadataController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	CommonLabTestService labTestService;
	
	protected List<LabTestType> getAllLabTestTypes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestAttributeType> getAllLabTestAttributeTypes() throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestAttributeType getLabTestAttributeType(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestAttributeType> getLabTestAttributeTypes(String name, String datatypeClassname,
	        boolean includeRetired) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestType getLabTestType(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestType saveLabTestType(LabTestType labTestType) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void retireLabTestType(LabTestType labTestType) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void retireLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void unretireLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void unretireLabTestType(LabTestType labTestType) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void deleteLabTestAttributeType(LabTestAttributeType labTestAttributeType) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void deleteLabTestType(LabTestType labTestType) throws APIException {
		// TODO Auto-generated method stub
	}
	
}
