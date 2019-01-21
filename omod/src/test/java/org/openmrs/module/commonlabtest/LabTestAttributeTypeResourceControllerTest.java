package org.openmrs.module.commonlabtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class LabTestAttributeTypeResourceControllerTest extends MainResourceControllerTest {

	@Autowired
	CommonLabTestService commonLabTestService;

	@Before
	public void setUp() throws Exception {
		executeDataSet("CommonLabTestService-initialData.xml");
	}

	@Override
	public long getAllCount() {
		return 5;
	}

	@Override
	public String getURI() {
		return "commonlab/labtestattributetype";
	}

	@Override
	public String getUuid() {
		return "ecf166e5-478e-11e8-943c-40b034c3cfee";
	}

	@Test
	public void shouldSave() throws Exception {
		String uri = getURI();
		SimpleObject labTestAttributeType = new SimpleObject();
		labTestAttributeType.add("name", "Unknown");
		labTestAttributeType.add("description", "Unknown Attribute Type for Testing");
		labTestAttributeType.add("labTestType", "ee9b140e-9a29-11e8-a296-40b034c3cfee");
		labTestAttributeType.add("datatypeClassname", "org.openmrs.customdatatype.datatype.ConceptDatatype");
		labTestAttributeType.add("sortWeight", "1");
		labTestAttributeType.add("maxOccurs", "0");

		MockHttpServletRequest newPostRequest = newPostRequest(uri, labTestAttributeType);
		MockHttpServletResponse handle = handle(newPostRequest);
		SimpleObject objectCreated = deserialize(handle);
		Assert.assertNotNull(objectCreated);
	}
}
