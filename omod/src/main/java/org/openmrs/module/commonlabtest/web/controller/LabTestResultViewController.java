package org.openmrs.module.commonlabtest.web.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class LabTestResultViewController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	CommonLabTestService commonLabTestService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/getTestResults.form")
	@ResponseBody
	public String getLabTestResult(@RequestParam Integer testOrderId) {
		LabTest labTest = commonLabTestService.getLabTest(testOrderId);
		List<LabTestSample> testSample;
		List<LabTestAttribute> testAttributes = commonLabTestService.getLabTestAttributes(testOrderId);
		
		for (LabTestAttributeType attribut : commonLabTestService.getLabTestAttributeTypes(labTest.getLabTestType(), false)) {
			for (int i = 0; i < testAttributes.size(); i++) {
				if (testAttributes.get(i).getAttributeTypeId() == attribut.getLabTestAttributeTypeId()) {
					testAttributes.get(i).setAttributeType(attribut);
				}
			}
		}
		
		testSample = commonLabTestService.getLabTestSamples(labTest, Boolean.FALSE);
		JsonObject testResultList = new JsonObject();
		
		JsonArray testSampleArray = new JsonArray();
		for (LabTestSample labTestSample : testSample) {
			JsonObject objTestSample = new JsonObject();
			objTestSample.addProperty("testOrderId", testOrderId);
			objTestSample.addProperty("specimenType", labTestSample.getSpecimenType().getName().getName());
			objTestSample.addProperty("specimenSite", labTestSample.getSpecimenSite().getName().getName());
			objTestSample.addProperty("status", labTestSample.getStatus().name());
			testSampleArray.add(objTestSample);
		}
		JsonArray testResultArray = new JsonArray();
		for (LabTestAttribute labTestResult : testAttributes) {
			JsonObject objTestResult = new JsonObject();
			if (labTestResult.getAttributeType() != null) {
				objTestResult.addProperty("question", labTestResult.getAttributeType().getName());
				objTestResult.addProperty("valuesReference", labTestResult.getValueReference());
				testResultArray.add(objTestResult);
			}
			
		}
		
		testResultList.add("sample", testSampleArray);
		testResultList.add("result", testResultArray);
		
		return testResultList.toString();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/getTestSampleStatus.form")
	@ResponseBody
	public Boolean getLabTestSampleStatus(@RequestParam Integer testOrderId) {
		
		LabTest labTest = commonLabTestService.getLabTest(testOrderId);
		List<LabTestSample> testSample;
		testSample = commonLabTestService.getLabTestSamples(labTest, Boolean.FALSE);
		for (LabTestSample labTestSample : testSample) {
			if (labTestSample.getStatus().equals(LabTestSampleStatus.ACCEPTED)
			        || labTestSample.getStatus().equals(LabTestSampleStatus.PROCESSED)) {
				return Boolean.TRUE;
			}
			
		}
		return Boolean.FALSE;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/getTestSampleAcceptedStatus.form")
	@ResponseBody
	public Boolean getLabTestSampleAcceptedStatus(@RequestParam Integer testOrderId) {
		
		LabTest labTest = commonLabTestService.getLabTest(testOrderId);
		List<LabTestSample> testSample;
		int count = 1;
		testSample = commonLabTestService.getLabTestSamples(labTest, Boolean.FALSE);
		for (LabTestSample labTestSample : testSample) {
			if (labTestSample.getStatus().equals(LabTestSampleStatus.ACCEPTED)) {
				count++;
			}
		}
		if (count > 1)
			return Boolean.FALSE;
		else
			return Boolean.TRUE;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/getTestAttributeTypeSortWeight.form")
	@ResponseBody
	public String getLabTestAttributeType(@RequestParam Integer testTypeId) {
		LabTestType labTestType = commonLabTestService.getLabTestType(testTypeId);
		List<LabTestAttributeType> labTestAttributeType = commonLabTestService.getLabTestAttributeTypes(labTestType,
		    Boolean.FALSE);
		
		JsonObject testAttributeList = new JsonObject();
		JsonArray testAttributeArray = new JsonArray();
		for (LabTestAttributeType labTestAttributeTypeObj : labTestAttributeType) {
			JsonObject objTestSample = new JsonObject();
			objTestSample.addProperty("testOrderId", testTypeId);
			objTestSample.addProperty("attributeTypeName", labTestAttributeTypeObj.getName());
			objTestSample.addProperty("sortWeight", labTestAttributeTypeObj.getSortWeight());
			testAttributeArray.add(objTestSample);
		}
		testAttributeList.add("sortweightlist", testAttributeArray);
		return testAttributeList.toString();
	}
	
}
