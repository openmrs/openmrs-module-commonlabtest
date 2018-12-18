package org.openmrs.module.commonlabtest;

import org.junit.Before;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.web.resource.LabTestAttributeResourceController;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest;

public class LabTestAttributeResourceControllerTest extends BaseDelegatingResourceTest<LabTestAttributeResourceController, LabTestAttribute> {
	
	@Before
	public void before() throws Exception {
		executeDataSet("CommonLabTestService-initialData.xml");
	}
	
	@Override
	public String getDisplayProperty() {
		// TODO Auto-generated method stub
		return "1301";
	}
	
	@Override
	public String getUuidProperty() {
		// TODO Auto-generated method stub
		return "2c9737d9-47c2-11e8-943c-40b034c3cfee";
	}
	
	@Override
	public LabTestAttribute newObject() {
		// TODO Auto-generated method stub
		return Context.getService(CommonLabTestService.class).getLabTestAttributeByUuid(getUuidProperty());
	}
	
}
