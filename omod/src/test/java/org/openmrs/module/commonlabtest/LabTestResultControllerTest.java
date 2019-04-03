package org.openmrs.module.commonlabtest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.web.controller.LabTestResultController;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

public class LabTestResultControllerTest extends BaseModuleWebContextSensitiveTest {

	@Autowired
	CommonLabTestService commonLabTestService;

	@Test
	public void shouldSaveLabTestResult() throws Exception {

		executeDataSet("CommonLabTestService-initialData.xml");
		LabTestResultController controller = new LabTestResultController();

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("testAttributeId.2", "2");
		request.setParameter("concept.3", "500");
		request.setParameter("valueText.1", "Test Data");
		request.setParameter("update", "false");

		LabTest labTest = commonLabTestService.getLabTest(100);
		Assert.assertNotNull(labTest);

		List<LabTestAttributeType> attributeTypeList = commonLabTestService
		        .getLabTestAttributeTypes(labTest.getLabTestType(), Boolean.FALSE);
		Assert.assertNotEquals(0, attributeTypeList.size());

		controller.onSubmit(null, request, labTest.getId(), null, null, true);

		List<LabTestAttribute> labTestAttributes = commonLabTestService.getLabTestAttributes(labTest.getId());
		Assert.assertNotNull(labTestAttributes);

	}

	@Test
	public void shouldVoid() throws Exception {

		executeDataSet("CommonLabTestService-initialData.xml");
		LabTestResultController controller = new LabTestResultController();

		MockHttpServletRequest request = new MockHttpServletRequest();

		LabTest labTest = commonLabTestService.getLabTest(100);
		Assert.assertNotNull(labTest);

		List<LabTestAttributeType> attributeTypeList = commonLabTestService
		        .getLabTestAttributeTypes(labTest.getLabTestType(), Boolean.FALSE);
		Assert.assertNotEquals(0, attributeTypeList.size());

		List<LabTestAttribute> beforLabTestAttributes = commonLabTestService.getLabTestAttributes(attributeTypeList.get(0),
		    Boolean.FALSE);
		Assert.assertNotEquals(0, beforLabTestAttributes.size());

		Patient patient = Context.getPatientService().getPatient(1000);

		controller.onVoid(null, null, request, labTest.getId(), patient.getId(), "Test Case  Running..");

		List<LabTestAttribute> afterLabTestAttributes = commonLabTestService.getLabTestAttributes(attributeTypeList.get(0),
		    Boolean.FALSE);
		Assert.assertEquals(0, afterLabTestAttributes.size());

	}

}
