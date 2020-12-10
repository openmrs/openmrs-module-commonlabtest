/**
 * 
 */
package org.openmrs.module.commonlabtest.extension.html;

import org.openmrs.module.commonlabtest.CommonLabTestConfig;
import org.openmrs.module.web.extension.PatientDashboardTabExt;

/**
 * @author owais.hussain@ihsinformatics.com
 */
public class CommonLabTestExt extends PatientDashboardTabExt {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.module.web.extension.PatientDashboardTabExt#getTabName()
	 */
	@Override
	public String getTabName() {
		return "Lab Tests";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.module.web.extension.PatientDashboardTabExt#getTabId()
	 */
	@Override
	public String getTabId() {
		return "commonlabtest";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openmrs.module.web.extension.PatientDashboardTabExt#getRequiredPrivilege(
	 * )
	 */
	@Override
	public String getRequiredPrivilege() {
		return CommonLabTestConfig.VIEW_LAB_TEST_PRIVILEGE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.module.web.extension.PatientDashboardTabExt#getPortletUrl()
	 */
	@Override
	public String getPortletUrl() {
		return "patientLabTests";
	}

}
