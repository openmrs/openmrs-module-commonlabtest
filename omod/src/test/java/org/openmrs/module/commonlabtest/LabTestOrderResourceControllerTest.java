package org.openmrs.module.commonlabtest;

import org.junit.Before;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.web.resource.LabTestOrderResourceController;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest;

public class LabTestOrderResourceControllerTest extends BaseDelegatingResourceTest<LabTestOrderResourceController, LabTest> {
	
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
