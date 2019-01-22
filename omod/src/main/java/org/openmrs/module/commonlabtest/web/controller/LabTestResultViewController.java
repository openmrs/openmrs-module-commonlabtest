package org.openmrs.module.commonlabtest.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
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

	CommonLabTestService commonLabTestService;

	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/getTestResults.form")
	@ResponseBody
	public String getLabTestResult(@RequestParam Integer testOrderId) {
		commonLabTestService = Context.getService(CommonLabTestService.class);
		LabTest labTest = commonLabTestService.getLabTest(testOrderId);
		List<LabTestSample> testSample;
		List<LabTestAttribute> testAttributes = commonLabTestService.getLabTestAttributes(testOrderId);
		JsonObject testResultList = new JsonObject();
		JsonArray testSampleArray = new JsonArray();
		JsonArray testResultArray = new JsonArray();
		commonLabTestService = Context.getService(CommonLabTestService.class);
		try {
			for (LabTestAttributeType attribut : commonLabTestService.getLabTestAttributeTypes(labTest.getLabTestType(),
			    Boolean.TRUE)) {
				for (int i = 0; i < testAttributes.size(); i++) {
					if (!testAttributes.get(i).getVoided()) {
						if (testAttributes.get(i).getAttributeType().getLabTestAttributeTypeId() == attribut
						        .getLabTestAttributeTypeId()) {
							testAttributes.get(i).setAttributeType(attribut);
						}
					}
				}
			}
			testSample = commonLabTestService.getLabTestSamples(labTest, Boolean.FALSE);
			for (LabTestSample labTestSample : testSample) {
				JsonObject objTestSample = new JsonObject();
				objTestSample.addProperty("testOrderId", testOrderId);
				objTestSample.addProperty("specimenType", labTestSample.getSpecimenType().getName().getName());
				objTestSample.addProperty("specimenSite", labTestSample.getSpecimenSite().getName().getName());
				objTestSample.addProperty("status", labTestSample.getStatus().name());
				testSampleArray.add(objTestSample);
			}
			if (testAttributes != null && !testAttributes.isEmpty()) {
				LabTestAttributeType labTestAttributeType = testAttributes.get(0).getAttributeType();
				List<LabTestAttributeType> labTestAttributeTypes = commonLabTestService
				        .getLabTestAttributeTypes(labTestAttributeType.getLabTestType(), Boolean.FALSE);
				testResultArray = getAttributeTypeList(labTestAttributeTypes, testOrderId, testAttributes);
			}
		}
		catch (Exception e) {
			testResultList.add("sample", testSampleArray);
			testResultList.add("result", testResultArray);
		}
		testResultList.add("sample", testSampleArray);
		testResultList.add("result", testResultArray);
		return testResultList.toString();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/getTestSampleStatus.form")
	@ResponseBody
	public Boolean getLabTestSampleStatus(@RequestParam Integer testOrderId) {
		commonLabTestService = Context.getService(CommonLabTestService.class);
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
		commonLabTestService = Context.getService(CommonLabTestService.class);
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
		commonLabTestService = Context.getService(CommonLabTestService.class);
		LabTestType labTestType = commonLabTestService.getLabTestType(testTypeId);
		List<LabTestAttributeType> labTestAttributeType = commonLabTestService.getLabTestAttributeTypes(labTestType,
		    Boolean.FALSE);

		JsonObject testAttributeList = new JsonObject();
		JsonArray testAttributeArray = new JsonArray();
		for (LabTestAttributeType labTestAttributeTypeObj : labTestAttributeType) {
			JsonObject objTestSample = new JsonObject();
			objTestSample.addProperty("testTypeId", testTypeId);
			objTestSample.addProperty("attributeTypeName", labTestAttributeTypeObj.getName());
			objTestSample.addProperty("sortWeight", labTestAttributeTypeObj.getSortWeight());
			objTestSample.addProperty("multisetName", labTestAttributeTypeObj.getMultisetName());
			objTestSample.addProperty("groupName", labTestAttributeTypeObj.getGroupName());
			testAttributeArray.add(objTestSample);
		}
		testAttributeList.add("sortweightlist", testAttributeArray);
		return testAttributeList.toString();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/getConceptExist.form")
	@ResponseBody
	public Boolean conceptExist(@RequestParam Integer conceptId) {
		boolean isExist = false;
		try {
			if (conceptId != null) {
				Concept concept = Context.getConceptService().getConcept(conceptId);
				if (concept != null && !concept.getUuid().equals("")) {
					isExist = true;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return isExist;
		}

		return isExist;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		}
		catch (NumberFormatException e) {
			return false;
		}
		catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	public JsonArray getAttributeTypeList(List<LabTestAttributeType> labTestAttributeTypeList, int testOrderId,
	        List<LabTestAttribute> labTestAttributes) {
		List<String> holderGroupIdList = new ArrayList<String>();
		JsonArray parentJsonArray = new JsonArray();
		JsonObject labTestGroupObj;
		for (LabTestAttributeType labTestAttributeType : labTestAttributeTypeList) {
			labTestGroupObj = new JsonObject();
			JsonArray jsonGroupArray = new JsonArray();
			String groupName = labTestAttributeType.getGroupName();
			if (groupName != null && !groupName.isEmpty()) {
				if (holderGroupIdList.contains(groupName)) {
					continue;
				}
				holderGroupIdList.add(labTestAttributeType.getGroupName());
				labTestGroupObj.addProperty("groupName", labTestAttributeType.getGroupName());
				List<LabTestAttributeType> groupLabTestATList = getFilterAttributeTypes(labTestAttributeType,
				    labTestAttributeTypeList);
				for (LabTestAttributeType labTestAttributeTypeResults : groupLabTestATList) {
					LabTestAttribute labTestAttributeResult = getFilterAttribute(labTestAttributeTypeResults,
					    labTestAttributes);
					jsonGroupArray.add(getLabTestAttributeObj(labTestAttributeResult));
				}
				labTestGroupObj.add("groups", jsonGroupArray);
				parentJsonArray.add(labTestGroupObj);

			} else {
				LabTestAttribute labTestAttributeResult = getFilterAttribute(labTestAttributeType, labTestAttributes);
				parentJsonArray.add(getLabTestAttributeObj(labTestAttributeResult));
			}
		}
		return parentJsonArray;
	}

	private LabTestAttribute getFilterAttribute(LabTestAttributeType labTestAttributeType,
	        List<LabTestAttribute> LabTestAttribute) {
		LabTestAttribute labTestAttributeResult = new LabTestAttribute();
		if (!LabTestAttribute.isEmpty()) {
			for (LabTestAttribute filterLabTestAttribute : LabTestAttribute) {
				if (labTestAttributeType.getLabTestAttributeTypeId() == filterLabTestAttribute.getAttributeType()
				        .getLabTestAttributeTypeId()) {
					labTestAttributeResult = filterLabTestAttribute;
					break;
				}
			}
		}
		return labTestAttributeResult;
	}

	private List<LabTestAttributeType> getFilterAttributeTypes(LabTestAttributeType labTestAttributeType,
	        List<LabTestAttributeType> listLabTestAttributeType) {
		List<LabTestAttributeType> filterLabTestAttributeTypes = new ArrayList<LabTestAttributeType>();
		if (!listLabTestAttributeType.isEmpty()) {
			for (LabTestAttributeType filterLabTestAttributeType : listLabTestAttributeType) {
				String groupName = filterLabTestAttributeType.getGroupName();
				if (groupName != null && !groupName.isEmpty()) {
					if (groupName.equals(labTestAttributeType.getGroupName())) {
						filterLabTestAttributeTypes.add(filterLabTestAttributeType);
					}
				}
			}
		}
		return filterLabTestAttributeTypes;
	}

	private JsonObject getLabTestAttributeObj(LabTestAttribute labTestAttribute) {
		JsonObject objTestResult = new JsonObject();
		if (labTestAttribute != null) {
			if (labTestAttribute.getAttributeType().getDatatypeClassname()
			        .equals("org.openmrs.customdatatype.datatype.ConceptDatatype")) {
				objTestResult.addProperty("question", labTestAttribute.getAttributeType().getName());
				boolean isTrue = isInteger(labTestAttribute.getAttributeType().getDatatypeConfig());
				if (isTrue) {
					Concept conceptConfig = Context.getConceptService()
					        .getConcept(Integer.parseInt(labTestAttribute.getAttributeType().getDatatypeConfig()));
					if (conceptConfig != null) {
						if (conceptConfig.getDatatype().getName().equals("Coded")) {
							Concept concept = Context.getConceptService()
							        .getConcept(Integer.parseInt(labTestAttribute.getValueReference()));
							objTestResult.addProperty("valuesReference", concept.getName().getName());
						} else {
							objTestResult.addProperty("valuesReference", labTestAttribute.getValueReference());
						}
					}
				}
			} else {
				objTestResult.addProperty("question", labTestAttribute.getAttributeType().getName());
				objTestResult.addProperty("valuesReference", labTestAttribute.getValueReference());
			}
		}
		objTestResult.addProperty("void", labTestAttribute.getVoided());
		return objTestResult;
	}
}
