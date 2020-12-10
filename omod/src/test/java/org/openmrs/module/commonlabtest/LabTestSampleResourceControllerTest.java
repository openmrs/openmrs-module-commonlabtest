/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.commonlabtest;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 */
public class LabTestSampleResourceControllerTest extends MainResourceControllerTest {

	@Autowired
	CommonLabTestService commonLabTestService;

	@Before
	public void setUp() throws Exception {
		executeDataSet("CommonLabTestService-initialData.xml");
	}

	@Override
	public long getAllCount() {
		return 3;
	}

	@Override
	public String getURI() {
		return "commonlab/labtestsample";
	}

	@Override
	public String getUuid() {
		return "f4bffc2f-5343-11e8-9c7c-40b034c3cfee";
	}

	@Test(expected = ResourceDoesNotSupportOperationException.class)
	@Override
	public void shouldGetAll() throws Exception {
		handle(request(RequestMethod.GET, getURI()));
		fail();
	}

	@Test
	public void shouldSave() throws Exception {
		String uri = getURI();
		SimpleObject labTestSample = new SimpleObject();
		labTestSample.add("labTest", "d175e92e-47bf-11e8-943c-40b034c3cfee");
		labTestSample.add("sampleIdentifier", "123456");
		labTestSample.add("specimenType", "deb16ee8-5344-11e8-9c7c-40b034c3cfee");
		labTestSample.add("specimenSite", "e721ec30-5344-11e8-9c7c-40b034c3cfee");
		labTestSample.add("collectionDate", "2019-01-01");
		labTestSample.add("collector", "1a61a0b5-d271-4b00-a803-5cef8b06ba8f");
		labTestSample.add("status", "COLLECTED");
		labTestSample.add("comments", "Only for testing");

		MockHttpServletRequest newPostRequest = newPostRequest(uri, labTestSample);
		MockHttpServletResponse handle = handle(newPostRequest);
		SimpleObject objectCreated = deserialize(handle);
		Assert.assertNotNull(objectCreated);
	}
}
