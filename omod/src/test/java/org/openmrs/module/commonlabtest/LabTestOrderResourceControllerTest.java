package org.openmrs.module.commonlabtest;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.test.Util;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
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
		return 2;
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
	public void shouldGetFullByUuid() throws Exception {
		MockHttpServletRequest request = request(RequestMethod.GET, getURI() + "/" + getUuid());
		request.addParameter("v", "full");
		SimpleObject result = deserialize(handle(request));
		Assert.assertNotNull(result);
		Assert.assertEquals(getUuid(), PropertyUtils.getProperty(result, "uuid"));
		Object samples = PropertyUtils.getProperty(result, "labTestSamples");
		Assert.assertNotNull(samples);
		Object attributes = PropertyUtils.getProperty(result, "attributes");
		Assert.assertNotNull(attributes);
	}

	@Override
	@Test(expected = ResourceDoesNotSupportOperationException.class)
	public void shouldGetAll() throws Exception {
		handle(request(RequestMethod.GET, getURI()));
		fail();
	}

	@Test
	public void shouldSearchByPatient() throws Exception {
		MockHttpServletRequest request = request(RequestMethod.GET, getURI());
		request.addParameter("patient", "993c46d2-5007-45e8-9512-969300717761");
		SimpleObject result = deserialize(handle(request));
		Assert.assertNotNull(result);
		Assert.assertEquals(getAllCount(), Util.getResultsSize(result));
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
		SimpleObject scoreAttributeObj = new SimpleObject();
		scoreAttributeObj.add("attributeType", "ed8b4caf-478e-11e8-943c-40b034c3cfee");
		scoreAttributeObj.add("valueReference", "100.0");

		SimpleObject resultsAttributeObj = new SimpleObject();
		resultsAttributeObj.add("attributeType", "efeb9339-538d-11e8-9c7c-40b034c3cfee");
		resultsAttributeObj.add("valueReference", "NORMAL");

		List<SimpleObject> attributes = new ArrayList<SimpleObject>();
		attributes.add(scoreAttributeObj);
		attributes.add(resultsAttributeObj);

		SimpleObject labTestOrder = new SimpleObject();
		labTestOrder.add("labReferenceNumber", "123");
		labTestOrder.add("labInstructions", "test data");
		labTestOrder.add("resultComments", "test Comments");
		labTestOrder.add("labTestType", "a277edf4-46ea-11e8-943c-40b034c3cfee");
		labTestOrder.add("order", "863c6448-51e8-11e8-b60d-080027ea421d");
		labTestOrder.add("attributes", attributes);

		MockHttpServletRequest newPostRequest = newPostRequest(getURI(), labTestOrder);
		MockHttpServletResponse handle = handle(newPostRequest);
		SimpleObject objectCreated = deserialize(handle);
		Assert.assertNotNull(objectCreated);
	}

	@Test
	public void shouldSaveNewButAvoidDuplicateAttributesForExistingOrder() throws Exception {

		SimpleObject firstAttribute = new SimpleObject();
		firstAttribute.add("attributeType", "ed8b4caf-478e-11e8-943c-40b034c3cfee");
		firstAttribute.add("valueReference", "100.0");

		SimpleObject secondAttribute = new SimpleObject();
		secondAttribute.add("attributeType", "efeb9339-538d-11e8-9c7c-40b034c3cfee");
		secondAttribute.add("valueReference", "NORMAL");

		SimpleObject newAttribute = new SimpleObject();
		newAttribute.add("attributeType", "f43de058-538d-11e8-9c7c-40b034c3cfee");
		newAttribute.add("valueReference", "UNSTABLE");

		List<SimpleObject> attributes = new ArrayList<SimpleObject>();
		attributes.add(firstAttribute);
		attributes.add(secondAttribute);
		attributes.add(newAttribute);

		SimpleObject labTestOrder = new SimpleObject();
		labTestOrder.add("attributes", attributes);

		// attaching uuid in URI for existing order
		MockHttpServletRequest newPostRequest = newPostRequest(getURI() + "/d175e92e-47bf-11e8-943c-40b034c3cfee",
		    labTestOrder);
		MockHttpServletResponse handle = handle(newPostRequest);
		SimpleObject objectCreated = deserialize(handle);
		Assert.assertNotNull(objectCreated);

		LabTest existingLabOrder = commonLabTestService.getLabTestByUuid("d175e92e-47bf-11e8-943c-40b034c3cfee");
		
		// ensuring new attribute is saved
		String newAttributeValue = existingLabOrder
		        .getAttribute(commonLabTestService.getLabTestAttributeTypeByUuid("f43de058-538d-11e8-9c7c-40b034c3cfee"))
		        .getValueReference();
		Assert.assertEquals("UNSTABLE", newAttributeValue);

		// ensuring no duplicate attributes are saved
		List<String> listAttrTypeUuids = new ArrayList<String>();
		for (LabTestAttribute attr : existingLabOrder.getActiveAttributes()) {
			listAttrTypeUuids.add(attr.getAttributeType().getUuid());
		}
		Assert.assertEquals(1, Collections.frequency(listAttrTypeUuids, "ed8b4caf-478e-11e8-943c-40b034c3cfee"));
	}

	@Test
	public void validateDefaultRepresentaion() throws Exception {
		LabTest labTest = commonLabTestService.getLabTestByUuid(getUuid());
		SimpleObject result = deserialize(handle(newGetRequest(getURI() + "/" + getUuid())));
		Assert.assertThat(getUuid(), Matchers.is(PropertyUtils.getProperty(result, "uuid")));
		Assert.assertThat(labTest.getLabReferenceNumber(),
		    Matchers.is(PropertyUtils.getProperty(result, "labReferenceNumber")));
		Object order = PropertyUtils.getProperty(result, "order");
		Assert.assertNotNull(order);
		Object labTestSamples = PropertyUtils.getProperty(result, "labTestSamples");
		Assert.assertNotNull(labTestSamples);
		Object attributes = PropertyUtils.getProperty(result, "attributes");
		Assert.assertNotNull(attributes);
		Object labTestType = PropertyUtils.getProperty(result, "labTestType");
		Assert.assertNotNull(labTestType);

	}

	@Test
	public void validateFullRepresentaion() throws Exception {
		LabTest labTest = commonLabTestService.getLabTestByUuid(getUuid());
		SimpleObject result = deserialize(handle(newGetRequest(getURI() + "/" + getUuid(), new Parameter("v", "full"))));
		Assert.assertThat(getUuid(), Matchers.is(PropertyUtils.getProperty(result, "uuid")));
		Assert.assertThat(labTest.getLabReferenceNumber(),
		    Matchers.is(PropertyUtils.getProperty(result, "labReferenceNumber")));
		Object order = PropertyUtils.getProperty(result, "order");
		Assert.assertNotNull(order);
		Object labTestSamples = PropertyUtils.getProperty(result, "labTestSamples");
		Assert.assertNotNull(labTestSamples);
		Object attributes = PropertyUtils.getProperty(result, "attributes");
		Assert.assertNotNull(attributes);
		Object labTestType = PropertyUtils.getProperty(result, "labTestType");
		Assert.assertNotNull(labTestType);

	}

}
