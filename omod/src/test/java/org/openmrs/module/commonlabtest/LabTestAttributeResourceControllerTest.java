package org.openmrs.module.commonlabtest;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.webservices.rest.SimpleObject;
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
		return 2;
	}
	
	@Override
	public String getURI() {
		return "commonlab/labtestattribute";
	}
	
	@Override
	public String getUuid() {
		return "2c9737d9-47c2-11e8-943c-40b034c3cfee";
	}
	
	@Test
	public void shouldSave() throws Exception {
		String uri = getURI();
		SimpleObject labTestAttributeObj = new SimpleObject();
		labTestAttributeObj.add("labTest", "4bf46c09-46e9-11e8-943c-40b034c3cfee");
		labTestAttributeObj.add("attributeType", "ecf166e5-478e-11e8-943c-40b034c3cfee");
		labTestAttributeObj.add("valueReference", "12345");
		
		MockHttpServletRequest newPostRequest = newPostRequest(uri, labTestAttributeObj);
		MockHttpServletResponse handle = handle(newPostRequest);
		SimpleObject objectCreated = deserialize(handle);
		Assert.assertNotNull(objectCreated);
		
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
}
