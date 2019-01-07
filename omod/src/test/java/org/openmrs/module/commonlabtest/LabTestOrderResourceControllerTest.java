package org.openmrs.module.commonlabtest;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.test.Util;
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
		return 8;
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
	@Test
	public void shouldGetFullByUuid() throws Exception {
		MockHttpServletRequest request = request(RequestMethod.GET, getURI() + "/" + getUuid());
		request.addParameter("v", "full");
		SimpleObject result = null;
		result = deserialize(handle(request));
		Assert.assertNotNull(result);
		Assert.assertEquals(getUuid(), PropertyUtils.getProperty(result, "uuid"));
	}
	
	@Override
	@Test
	public void shouldGetRefByUuid() throws Exception {
		MockHttpServletRequest request = request(RequestMethod.GET, getURI() + "/" + getUuid());
		request.addParameter("v", "ref");
		SimpleObject result = deserialize(handle(request));
		Assert.assertNotNull(result);
		Assert.assertEquals(getUuid(), PropertyUtils.getProperty(result, "uuid"));
	}
	
	@Override
	@Test
	public void shouldGetAll() throws Exception {
		SimpleObject result = deserialize(handle(request(RequestMethod.GET, getURI())));
		Assert.assertNotNull(result);
		Assert.assertEquals(getAllCount(), Util.getResultsSize(result));
	}
	
	@Override
	@Test
	public void shouldGetDefaultByUuid() throws Exception {
		MockHttpServletResponse response = handle(request(RequestMethod.GET, getURI() + "/" + getUuid()));
		SimpleObject result = deserialize(response);
		Assert.assertNotNull(result);
		Assert.assertEquals(getUuid(), PropertyUtils.getProperty(result, "uuid"));
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
		SimpleObject labTestOrder = new SimpleObject();
		labTestOrder.add("labReferenceNumber", "123");
		labTestOrder.add("labInstructions", "test data");
		labTestOrder.add("resultComments", "test Comments");
		labTestOrder.add("labTestType", "a277edf4-46ea-11e8-943c-40b034c3cfee");
		labTestOrder.add("order", "863c6448-51e8-11e8-b60d-080027ea421d");
		
		// Add attributes
		
		MockHttpServletRequest newPostRequest = newPostRequest(getURI(), labTestOrder);
		MockHttpServletResponse handle = handle(newPostRequest);
		SimpleObject objectCreated = deserialize(handle);
		Assert.assertNotNull(objectCreated);
	}
}
