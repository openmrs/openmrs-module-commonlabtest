/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
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
	
	public static final String ADD_LAB_TEST_METADATA_PRIVILEGE = "Add Common Lab Test Metadata";
	
	public static final String EDIT_LAB_TEST_METADATA_PRIVILEGE = "Edit Common Lab Test Metadata";
	
	public static final String DELETE_LAB_TEST_METADATA_PRIVILEGE = "Delete Common Lab Test Metadata";
	
	public static final String VIEW_LAB_TEST_METADATA_PRIVILEGE = "View Common Lab Test Metadata";
	
	public static final String ADD_LAB_TEST_PRIVILEGE = "Add Common Lab Tests";
	
	public static final String EDIT_LAB_TEST_PRIVILEGE = "Edit Common Lab Tests";
	
	public static final String VIEW_LAB_TEST_PRIVILEGE = "View Common Lab Tests";
	
	public static final String DELETE_LAB_TEST_PRIVILEGE = "Delete Common Lab Tests";
	
}
