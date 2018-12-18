/**
s * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.commonlabtest;

import org.springframework.stereotype.Component;

/**
 * Contains module's config.
 */
@Component("commonlabtest.CommonLabTestConfig")
public class CommonLabTestConfig {
	
	public static final String ADD_LAB_TEST_METADATA_PRIVILEGE = "Add CommonLabTest Metadata";
	
	public static final String EDIT_LAB_TEST_METADATA_PRIVILEGE = "Edit CommonLabTest Metadata";
	
	public static final String DELETE_LAB_TEST_METADATA_PRIVILEGE = "Delete CommonLabTest Metadata";
	
	public static final String VIEW_LAB_TEST_METADATA_PRIVILEGE = "View CommonLabTest Metadata";
	
	public static final String ADD_LAB_TEST_SAMPLE_PRIVILEGE = "Add CommonLabTest Samples";
	
	public static final String EDIT_LAB_TEST_SAMPLE_PRIVILEGE = "Edit CommonLabTest Samples";
	
	public static final String VIEW_LAB_TEST_SAMPLE_PRIVILEGE = "View CommonLabTest Samples";
	
	public static final String DELETE_LAB_TEST_SAMPLE_PRIVILEGE = "Delete CommonLabTest Samples";
	
	public static final String ADD_LAB_TEST_PRIVILEGE = "Add CommonLabTest Orders";
	
	public static final String EDIT_LAB_TEST_PRIVILEGE = "Edit CommonLabTest Orders";
	
	public static final String VIEW_LAB_TEST_PRIVILEGE = "View CommonLabTest Orders";
	
	public static final String DELETE_LAB_TEST_PRIVILEGE = "Delete CommonLabTest Orders";
	
	public static final String ADD_LAB_RESULT_PRIVILEGE = "Add CommonLabTest results";
	
	public static final String EDIT_LAB_RESULT_PRIVILEGE = "Edit CommonLabTest results";
	
	public static final String VIEW_LAB_RESULT_PRIVILEGE = "View CommonLabTest results";
	
	public static final String DELETE_LAB_RESULT_PRIVILEGE = "Delete CommonLabTest results";
	
	public static final Boolean AUTO_VOID_REJECTED_SAMPLES = Boolean.TRUE;
}
