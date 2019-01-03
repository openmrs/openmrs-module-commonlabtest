package org.openmrs.module.commonlabtest;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMethod;
import org.openmrs.module.webservices.rest.test.Util;

public class LabTestOrderResourceControllerTest extends MainResourceControllerTest {
	
	@Autowired
	CommonLabTestService commonLabTestService;
	
	@Before
	public void before() throws Exception {
		executeDataSet("CommonLabTestService-initialData.xml");
	}
	
	@Override
	public long getAllCount() {
		return 0;
	}
	
	@Override
	public String getURI() {
		return "commonlab/labtestorder";
	}
	
	@Override
	public String getUuid() {
		//Lf:GXP-IRS12345
		return "d175e92e-47bf-11e8-943c-40b034c3cfee";
	}
	
	@Test
	public void shouldGetFullByUuid() throws Exception {
		
		MockHttpServletRequest request = request(RequestMethod.GET, getURI() + "/" + getUuid());
		request.addParameter("v", "full");
		SimpleObject result = null;
		result = deserialize(handle(request));
		Assert.assertNotNull(result);
		Assert.assertEquals(getUuid(), PropertyUtils.getProperty(result, "uuid"));
		
	}
	
	@Test
	public void shouldGetRefByUuid() throws Exception {
		MockHttpServletRequest request = request(RequestMethod.GET, getURI() + "/" + getUuid());
		request.addParameter("v", "ref");
		SimpleObject result = deserialize(handle(request));
		
		Assert.assertNotNull(result);
		Assert.assertEquals(getUuid(), PropertyUtils.getProperty(result, "uuid"));
	}
	
	@Test
	public void shouldGetAll() throws Exception {
		SimpleObject result = deserialize(handle(request(RequestMethod.GET, getURI())));
		
		Assert.assertNotNull(result);
		Assert.assertEquals(getAllCount(), Util.getResultsSize(result));
	}
	
	@Test
	public void shouldGetDefaultByUuid() throws Exception {
		MockHttpServletResponse response = handle(request(RequestMethod.GET, getURI() + "/" + getUuid()));
		SimpleObject result = deserialize(response);
		
		Assert.assertNotNull(result);
		Assert.assertEquals(getUuid(), PropertyUtils.getProperty(result, "uuid"));
	}
	
	@Test
	public void shouldSave() throws Exception {
		
		/*	SimpleObject orderObject = new SimpleObject();
			orderObject.add("order_id", "200");
			orderObject.add("uuid", "8acaf3b9-51e8-11e8-b60d-080027ea421d");
			orderObject.add("action", "NEW");
			orderObject.add("patient", "1f6959e5-d15a-4025-bb48-340ee9e2c58d");
			orderObject.add("concept", "a8102d6d-c528-477a-80bd-acc38ebc6252");
			orderObject.add("instructions", "test data");
			orderObject.add("encounter", "84aa0e76-52c0-11e8-b60d-080027ea421d");
			orderObject.add("careSetting", "c365e560-c3ec-11e3-9c1a-0800200c9a66");
			orderObject.add("sortWeight", 1);
			orderObject.add("type", "testorder");
			orderObject.add("urgency", "ROUTINE");
			orderObject.add("orderer", "e90a12be-85f7-4821-9360-2f5a2816279e");
			
			SimpleObject labTestOrder = new SimpleObject();
			labTestOrder.add("labReferenceNumber", "123");
			labTestOrder.add("labInstructions", "test data");
			labTestOrder.add("resultComments", "test Comments");
			labTestOrder.add("labTestType", "a277edf4-46ea-11e8-943c-40b034c3cfee");
			labTestOrder.add("order", "8acaf3b9-51e8-11e8-b60d-080027ea421d");
			
			MockHttpServletRequest newPostRequest = newPostRequest(getURI(), labTestOrder);
			MockHttpServletResponse handle = handle(newPostRequest);
			SimpleObject objectCreated = deserialize(handle);
			Assert.assertNotNull(objectCreated);*/
		
	}
	
}

/*extends BaseDelegatingResourceTest<LabTestOrderResourceController, LabTest> {
	
	@Before
	public void before() throws Exception {
		executeDataSet("CommonLabTestService-initialData.xml");
	}
	
	@Override
	public String getDisplayProperty() {
		// TODO Auto-generated method stub
		return "GXP-IRS12345";
	}
	
	@Override
	public String getUuidProperty() {
		
		return "d175e92e-47bf-11e8-943c-40b034c3cfee";
	}
	
	@Override
	public LabTest newObject() {
		// TODO Auto-generated method stub
		return Context.getService(CommonLabTestService.class).getLabTestByUuid(getUuidProperty());
	}
	
}
*/
