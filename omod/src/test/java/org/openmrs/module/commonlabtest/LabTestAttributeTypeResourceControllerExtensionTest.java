/**
 * Copyright(C) 2020 Interactive Health Solutions, Pvt. Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
 * You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
 * Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 * Contributors: Tahira Niazi
 */
package org.openmrs.module.commonlabtest;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.web.resource.LabTestAttributeTypeResourceController;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestUtil;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author tahira.niazi@ihsinformatics.com
 */
public class LabTestAttributeTypeResourceControllerExtensionTest extends BaseDelegatingResourceTest<LabTestAttributeTypeResourceController, LabTestAttributeType> {

	@Autowired
	CommonLabTestService commonLabTestService;

	@Before
	public void before() throws Exception {
		executeDataSet("CommonLabTestService-initialData.xml");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.
	 * BaseDelegatingResourceTest#getDisplayProperty()
	 */
	@Override
	public String getDisplayProperty() {
		return "Unique cartridge serial number.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.
	 * BaseDelegatingResourceTest#getUuidProperty()
	 */
	@Override
	public String getUuidProperty() {
		return "ecf166e5-478e-11e8-943c-40b034c3cfee";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.
	 * BaseDelegatingResourceTest#newObject()
	 */
	@Override
	public LabTestAttributeType newObject() {
		return commonLabTestService.getLabTestAttributeTypeByUuid(getUuidProperty());
	}

	@SuppressWarnings("unchecked")
	private void assertSearch(final String testTypeUuid, final int expectedResultCount) throws ResponseException {
		// input
		final MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("q", "");
		request.addParameter("testTypeUuid", testTypeUuid);
		final RequestContext context = RestUtil.getRequestContext(request, new MockHttpServletResponse());

		// search
		final SimpleObject simple = getResource().search(context);
		final List<SimpleObject> results = (List<SimpleObject>) simple.get("results");

		// verify
		final String errorMessage = "Number of results does not match for Lab Test Attribute Types by Test Type: testTypeUuid="
		        + testTypeUuid;
		Assert.assertEquals(errorMessage, expectedResultCount, results.size());
	}

	/**
	 * Test searching Attribute Types by Lab Test Type.
	 * 
	 * @see {@link https://issues.openmrs.org/browse/CLM-13}
	 * @throws Exception
	 */
	@Test
	public void testSearchingByLabTestTypeUuid() {
		assertSearch("4bf46c09-46e9-11e8-943c-40b034c3cfee", 3);

	}

}
