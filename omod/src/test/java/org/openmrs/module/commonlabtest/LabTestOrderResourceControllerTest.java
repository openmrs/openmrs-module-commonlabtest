package org.openmrs.module.commonlabtest;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.test.Util;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMethod;

public class LabTestOrderResourceControllerTest extends MainResourceControllerTest {

	@Autowired
	CommonLabTestService commonLabTestService;

	@Before
	public void before() throws Exception {
		executeDataSet("CommonLabTestService-initialData.xml");
	}

	@Override
	public long getAllCount() {
		return 2;
	}

	@Override
	public String getURI() {
		return "commonlab/labtestorder";
	}

	@Override
	public String getUuid() {
		return "d175e92e-47bf-11e8-943c-40b034c3cfee";
	}

	@Override
	@Test(expected = ResourceDoesNotSupportOperationException.class)
	public void shouldGetAll() throws Exception {
		handle(request(RequestMethod.GET, getURI()));
		fail();
	}

	@Test
	public void shouldSearchByPatient() throws Exception {
		MockHttpServletRequest request = request(RequestMethod.GET, getURI());
		request.addParameter("patient", "993c46d2-5007-45e8-9512-969300717761");
		SimpleObject result = deserialize(handle(request));
		Assert.assertNotNull(result);
		Assert.assertEquals(getAllCount(), Util.getResultsSize(result));
	}

	@Test
	public void shouldSave() throws Exception {
		SimpleObject labTestOrder = new SimpleObject();
		labTestOrder.add("labReferenceNumber", "123");
		labTestOrder.add("labInstructions", "test data");
		labTestOrder.add("resultComments", "test Comments");
		labTestOrder.add("labTestType", "a277edf4-46ea-11e8-943c-40b034c3cfee");
		labTestOrder.add("order", "863c6448-51e8-11e8-b60d-080027ea421d");

		MockHttpServletRequest newPostRequest = newPostRequest(getURI(), labTestOrder);
		MockHttpServletResponse handle = handle(newPostRequest);
		SimpleObject objectCreated = deserialize(handle);
		Assert.assertNotNull(objectCreated);
	}

	@Test
	public void shouldSaveWithAttributes() throws Exception {
		SimpleObject scoreAttributeObj = new SimpleObject();
		scoreAttributeObj.add("attributeType", "ed8b4caf-478e-11e8-943c-40b034c3cfee");
		scoreAttributeObj.add("valueReference", "100.0");

		SimpleObject resultsAttributeObj = new SimpleObject();
		resultsAttributeObj.add("attributeType", "efeb9339-538d-11e8-9c7c-40b034c3cfee");
		resultsAttributeObj.add("valueReference", "NORMAL");

		List<SimpleObject> attributes = new ArrayList<SimpleObject>();
		attributes.add(scoreAttributeObj);
		attributes.add(resultsAttributeObj);

		SimpleObject labTestOrder = new SimpleObject();
		labTestOrder.add("labReferenceNumber", "123");
		labTestOrder.add("labInstructions", "test data");
		labTestOrder.add("resultComments", "test Comments");
		labTestOrder.add("labTestType", "a277edf4-46ea-11e8-943c-40b034c3cfee");
		labTestOrder.add("order", "863c6448-51e8-11e8-b60d-080027ea421d");
		labTestOrder.add("attributes", attributes);

		MockHttpServletRequest newPostRequest = newPostRequest(getURI(), labTestOrder);
		MockHttpServletResponse handle = handle(newPostRequest);
		SimpleObject objectCreated = deserialize(handle);
		Assert.assertNotNull(objectCreated);
	}
}
