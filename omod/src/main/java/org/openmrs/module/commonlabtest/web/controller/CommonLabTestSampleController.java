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

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.api.APIException;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * This class configured as controller using annotation and mapped with the URL of
 * 'module/${rootArtifactid}/${rootArtifactid}Link.form'.
 */
@Controller("${rootrootArtifactid}.CommonLabTestController")
public class CommonLabTestSampleController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	CommonLabTestService labTestService;
	
	protected LabTestSample getLabTestSample(String uuid) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestSample> getLabTestSamples(LabTest labTest, Boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestSample getEarliestLabTestSample(Patient patient) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestSample> getLabTestSamples(Provider collector, Boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestSample> getLabTestSamples(Order order, Boolean includeVoided) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List<LabTestSample> getLabTestSamples(LabTestSampleStatus status, Date from, Date to, Boolean includeVoided)
	        throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestSample getLatestLabTestSample(Patient patient) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected LabTestSample saveLabTestSample(LabTestSample labTestSample) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void voidLabTestSample(LabTestSample labTestSample) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void unvoidLabTestSample(LabTestSample labTestSample) throws APIException {
		// TODO Auto-generated method stub
	}
	
	protected void deleteLabTestSample(LabTestSample labTestSample) throws APIException {
		// TODO Auto-generated method stub
	}
}
