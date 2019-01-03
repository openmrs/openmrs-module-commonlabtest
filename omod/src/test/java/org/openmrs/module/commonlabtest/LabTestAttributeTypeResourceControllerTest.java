package org.openmrs.module.commonlabtest;

import org.junit.Before;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.web.resource.LabTestAttributeTypeResourceController;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest;

public class LabTestAttributeTypeResourceControllerTest extends BaseDelegatingResourceTest<LabTestAttributeTypeResourceController, LabTestAttributeType> {
	
	@Before
	public void before() throws Exception {
		executeDataSet("CommonLabTestService-initialData.xml");
	}
	
	@Override
	public String getDisplayProperty() {
		return "Cartridge ID";
	}
	
	@Override
	public String getUuidProperty() {
		
		return "ecf166e5-478e-11e8-943c-40b034c3cfee";
	}
	
	@Override
	public LabTestAttributeType newObject() {
		return Context.getService(CommonLabTestService.class).getLabTestAttributeTypeByUuid(getUuidProperty());
	}
	
}
