package org.openmrs.module.commonlabtest;

import org.junit.Before;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.web.resource.LabTestSampleResourceController;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest;

public class LabTestSampleResourceControllerTest extends BaseDelegatingResourceTest<LabTestSampleResourceController, LabTestSample> {
	
	@Before
	public void before() throws Exception {
		executeDataSet("CommonLabTestService-initialData.xml");
	}
	
	@Override
	public String getDisplayProperty() {
		
		return "GXP-IRS12345-1";
		
	}
	
	@Override
	public String getUuidProperty() {
		
		return "f4bffc2f-5343-11e8-9c7c-40b034c3cfee";
	}
	
	@Override
	public LabTestSample newObject() {
		
		return Context.getService(CommonLabTestService.class).getLabTestSampleByUuid(getUuidProperty());
	}
	
}
