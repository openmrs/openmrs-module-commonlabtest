package org.openmrs.module.commonlabtest;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.commonlabtest.LabTestType.LabTestGroup;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMethod;

public class LabTestTypeResourceControllerTest extends MainResourceControllerTest {
	
	@Autowired
	CommonLabTestService commonLabTestService;
	
	@Before
	public void setUp() throws Exception {
		executeDataSet("CommonLabTestService-initialData.xml");
	}
	
	@Override
	public long getAllCount() {
		return 4;
	}
	
	@Override
	public String getURI() {
		return "commonlab/labtesttype";
	}
	
	@Override
	public String getUuid() {
		return "ee9b140e-9a29-11e8-a296-40b034c3cfee";
	}
	
	@Test
	public void shouldSave() throws Exception {
		String uri = getURI();
		SimpleObject labTestType = new SimpleObject();
		labTestType.add("name", "Test LabTestType");
		labTestType.add("shortName", "TLTT");
		labTestType.add("testGroup", LabTestGroup.CARDIOLOGY.toString());
		labTestType.add("referenceConcept", "a8102d6d-c528-477a-80bd-acc38ebc6252");
		labTestType.add("description", "Only for testing");
		
		MockHttpServletRequest newPostRequest = newPostRequest(uri, labTestType);
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
