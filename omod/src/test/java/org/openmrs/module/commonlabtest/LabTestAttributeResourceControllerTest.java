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

public class LabTestAttributeResourceControllerTest extends MainResourceControllerTest {

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
		return "commonlab/labtestattribute";
	}

	@Override
	public String getUuid() {
		return "2c9737d9-47c2-11e8-943c-40b034c3cfee";
	}

	@Override
	@Test(expected = ResourceDoesNotSupportOperationException.class)
	public void shouldGetAll() throws Exception {
		deserialize(handle(request(RequestMethod.GET, getURI())));
		fail();
	}

	@Test
	public void shouldSave() throws Exception {
		String uri = getURI();
		SimpleObject labTestAttributeObj = new SimpleObject();
		// CAD4TB Score
		labTestAttributeObj.add("labTest", "d175e92e-dc93-11e8-d298-40b034c3cfee");
		labTestAttributeObj.add("attributeType", "ed8b4caf-478e-11e8-943c-40b034c3cfee");
		labTestAttributeObj.add("valueReference", "90.0");

		MockHttpServletRequest newPostRequest = newPostRequest(uri, labTestAttributeObj);
		MockHttpServletResponse handle = handle(newPostRequest);
		SimpleObject objectCreated = deserialize(handle);
		Assert.assertNotNull(objectCreated);
	}
}
