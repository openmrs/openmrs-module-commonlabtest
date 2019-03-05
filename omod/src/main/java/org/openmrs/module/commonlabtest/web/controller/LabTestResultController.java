package org.openmrs.module.commonlabtest.web.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.utility.Consts;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class LabTestResultController {

	protected final Log log = LogFactory.getLog(getClass());

	private final String SUCCESS_ADD_FORM_VIEW = "/module/commonlabtest/addLabTestResult";

	CommonLabTestService commonLabTestService;

	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/addLabTestResult.form")
	public String showForm(HttpServletRequest request, @RequestParam(required = false) Integer testOrderId,
	        @RequestParam(required = false) Integer patientId, ModelMap model) {

		commonLabTestService = Context.getService(CommonLabTestService.class);
		LabTest labTest = commonLabTestService.getLabTest(testOrderId);

		if (labTest == null) {
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Consts.ERROR_TESTORDER_DOESNOT_EXIST);
			return "redirect:../../patientDashboard.form?patientId=" + patientId;
		} else if (labTest.getVoided()) {
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Consts.TESTORDER_VOIDED);
			return "redirect:../../patientDashboard.form?patientId=" + patientId;
		}

		List<LabTestAttributeType> attributeTypeList = new ArrayList<LabTestAttributeType>();
		attributeTypeList = commonLabTestService.getLabTestAttributeTypes(labTest.getLabTestType(), Boolean.FALSE);
		Collections.sort(attributeTypeList, new Comparator<LabTestAttributeType>() {

			@Override
			public int compare(LabTestAttributeType labTestAttributeTypeFirst,
			        LabTestAttributeType labTestAttributeTypeSecond) {
				return labTestAttributeTypeFirst.getSortWeight().compareTo(labTestAttributeTypeSecond.getSortWeight());
			}
		});

		List<LabTestAttribute> labTestAttributes = commonLabTestService.getLabTestAttributes(testOrderId);
		JsonArray resultantAttributeTypeList = getAttributeTypeList(attributeTypeList, testOrderId, labTestAttributes);

		if (!labTestAttributes.isEmpty() && !attributeTypeList.isEmpty()) {
			boolean updateMode = false;
			for (LabTestAttribute labTestAttribute : labTestAttributes) {
				if (!labTestAttribute.getVoided()) {
					updateMode = true;
					break;
				}
			}
			model.addAttribute(Consts.TESTTYPE_NAME, "" + attributeTypeList.get(0).getLabTestType().getName());
			if (updateMode) {
				model.addAttribute(Consts.UPDATE, Boolean.TRUE);
				model.addAttribute(Consts.FILE_PATH, labTest.getFilePath());
			} else {
				model.addAttribute(Consts.UPDATE, Boolean.FALSE);
				model.addAttribute(Consts.FILE_PATH, "");
				model.addAttribute(Consts.TESTTYPE_NAME, "");
			}
		} else {
			model.addAttribute(Consts.UPDATE, Boolean.FALSE);
			model.addAttribute(Consts.FILE_PATH, "");
			model.addAttribute(Consts.TESTTYPE_NAME, "");
		}
		String fileExtensions = Context.getAdministrationService().getGlobalProperty("commonlabtest.fileExtensions");

		model.addAttribute(Consts.ATTRIBUTE_TYPE_LIST, resultantAttributeTypeList);
		model.addAttribute(Consts.FILE_EXTENSIONS, fileExtensions);
		model.addAttribute(Consts.TEST_ORDER_ID, testOrderId);
		model.addAttribute(Consts.PATIENT_ID, labTest.getOrder().getPatient().getPatientId());
		model.addAttribute(Consts.ENCOUNT_DATE, Consts.simpleDateFormat.format(labTest.getOrder().getDateActivated()));

		return SUCCESS_ADD_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/module/commonlabtest/addLabTestResult.form")
	public String onSubmit(ModelMap model, HttpServletRequest request, @RequestParam(required = true) Integer testOrderId,
	        @RequestParam(required = false) Integer testAttributeId,
	        @RequestParam(required = false) MultipartFile documentTypeFile, @RequestParam(required = false) Boolean update) {

		commonLabTestService = Context.getService(CommonLabTestService.class);

		if (Context.getAuthenticatedUser() == null) {
			return "redirect:../../login.htm";
		}
		LabTest labTest = commonLabTestService.getLabTest(testOrderId);
		List<LabTestAttributeType> attributeTypeList = Context.getService(CommonLabTestService.class)
		        .getLabTestAttributeTypes(labTest.getLabTestType(), false);

		List<LabTestAttribute> existingLabTestAttributes = commonLabTestService.getLabTestAttributes(testOrderId);
		String testAtrrId;
		String status;
		try {
			List<LabTestAttribute> labTestAttributes = new ArrayList<LabTestAttribute>();

			for (LabTestAttributeType labTestAttributeType : attributeTypeList) {
				LabTestAttribute testAttribute = new LabTestAttribute();
				String dataTypeName = labTestAttributeType.getDatatypeClassname();
				String valueReference = "";
				testAtrrId = request.getParameter("testAttributeId." + labTestAttributeType.getId());

				if (update && testAtrrId != null && !testAtrrId.equals("undefined") && !testAtrrId.isEmpty()) {
					testAttribute = commonLabTestService.getLabTestAttribute(Integer.parseInt(testAtrrId));
				} else {
					testAttribute.setLabTest(labTest);
					testAttribute.setAttributeType(labTestAttributeType);
				}
				if (dataTypeName.equals(Consts.DATATYPE_CONCEPT_OPENMRS) || dataTypeName.equals(Consts.DATATYPE_CODED)) {
					valueReference = request.getParameter("concept." + labTestAttributeType.getId());
					LabTestAttribute existAttributeinner = getExistingAttributeValue(existingLabTestAttributes,
					    labTestAttributeType.getId());
					if (existAttributeinner == null && (valueReference == null || valueReference.isEmpty())) {
						continue;
					} else if (existAttributeinner != null && (valueReference == null || valueReference.isEmpty())) {
						existAttributeinner.setVoided(Boolean.TRUE);
						existAttributeinner.setVoidReason(Consts.ATTRIBUTE_NO_LONGER_REQUIRED);
						labTestAttributes.add(existAttributeinner);
					} else if (existAttributeinner == null && (valueReference != null && !valueReference.isEmpty())) {
						testAttribute.setValueReferenceInternal(valueReference);
						labTestAttributes.add(testAttribute);
					} else if (existAttributeinner.getValueReference().equalsIgnoreCase(valueReference)) {
						continue;
					} else {
						labTestAttributes.add(existingTestAttribute(existAttributeinner));
						testAttribute.setValueReferenceInternal(valueReference);
						labTestAttributes.add(testAttribute);
					}
				} else if (dataTypeName.equals(Consts.DATATYPE_BOOLEAN_OPENMRS) || dataTypeName.equals(Consts.BOOLEAN)) {
					valueReference = (request.getParameter("bool." + labTestAttributeType.getId()) == null) ? "false"
					        : request.getParameter("bool." + labTestAttributeType.getId());
					testAttribute.setValueReferenceInternal(valueReference);
					labTestAttributes.add(testAttribute);

				} else if (dataTypeName.equals(Consts.DATATYOE_FREE_TEXT_OPENMRS)
				        || dataTypeName.equals(Consts.DATATYOE_TEXT)
				        || dataTypeName.equals(Consts.DATATYPE_LONGTEXT_OPENMRS)) {

					valueReference = request.getParameter("valueText." + labTestAttributeType.getId());
					LabTestAttribute existAttributeinner = getExistingAttributeValue(existingLabTestAttributes,
					    labTestAttributeType.getId());
					if (existAttributeinner == null && (valueReference == null || valueReference.isEmpty())) {
						continue;
					} else if (existAttributeinner != null && (valueReference == null || valueReference.isEmpty())) {
						existAttributeinner.setVoided(Boolean.TRUE);
						labTestAttributes.add(existAttributeinner);
					} else if (existAttributeinner == null && (valueReference != null && !valueReference.isEmpty())) {
						testAttribute.setValueReferenceInternal(valueReference);
						labTestAttributes.add(testAttribute);
					} else if (existAttributeinner.getValueReference().equalsIgnoreCase(valueReference)) {
						continue;
					} else {
						labTestAttributes.add(existingTestAttribute(existAttributeinner));
						testAttribute.setValueReferenceInternal(valueReference);
						labTestAttributes.add(testAttribute);
					}
				} else if (dataTypeName.equals(Consts.DATATYPE_FLOAT_OPENMRS)
				        || dataTypeName.equals(Consts.DATATYPE_NUMERIC)) {
					valueReference = request.getParameter("float." + labTestAttributeType.getId());
					LabTestAttribute existAttributeinner = getExistingAttributeValue(existingLabTestAttributes,
					    labTestAttributeType.getId());
					if (existAttributeinner == null && (valueReference == null || valueReference.isEmpty())) {
						continue;
					} else if (existAttributeinner != null && (valueReference == null || valueReference.isEmpty())) {
						existAttributeinner.setVoided(Boolean.TRUE);
						labTestAttributes.add(existAttributeinner);
					} else if (existAttributeinner == null && (valueReference != null && !valueReference.isEmpty())) {
						testAttribute.setValueReferenceInternal(valueReference);
						labTestAttributes.add(testAttribute);
					} else if (existAttributeinner.getValueReference().equalsIgnoreCase(valueReference)) {
						continue;
					} else {
						labTestAttributes.add(existingTestAttribute(existAttributeinner));
						testAttribute.setValueReferenceInternal(valueReference);
						labTestAttributes.add(testAttribute);
					}
				} else if (dataTypeName.endsWith(Consts.DATATYPE_DATETIME_OPENMRS)
				        || dataTypeName.equals(Consts.DATATYPE_DATE) || dataTypeName.equals(Consts.DATATYPE_DATETIME)) {
					valueReference = request.getParameter("date." + labTestAttributeType.getId());
					LabTestAttribute existAttributeinner = getExistingAttributeValue(existingLabTestAttributes,
					    labTestAttributeType.getId());
					if (existAttributeinner == null && (valueReference == null || valueReference.isEmpty())) {
						continue;
					} else if (existAttributeinner != null && (valueReference == null || valueReference.isEmpty())) {
						existAttributeinner.setVoided(Boolean.TRUE);
						labTestAttributes.add(existAttributeinner);
					} else if (existAttributeinner == null && (valueReference != null && !valueReference.isEmpty())) {
						testAttribute.setValueReferenceInternal(valueReference);
						labTestAttributes.add(testAttribute);
					} else if (existAttributeinner.getValueReference().equalsIgnoreCase(valueReference)) {
						continue;
					} else {
						labTestAttributes.add(existingTestAttribute(existAttributeinner));
						testAttribute.setValueReferenceInternal(valueReference);
						labTestAttributes.add(testAttribute);
					}
				} else if (dataTypeName.equals(Consts.DATATYPE_REGEX_OPENMRS)) {
					valueReference = request.getParameter("regex." + labTestAttributeType.getId());
					LabTestAttribute existAttributeinner = getExistingAttributeValue(existingLabTestAttributes,
					    labTestAttributeType.getId());
					if (existAttributeinner == null && (valueReference == null || valueReference.isEmpty())) {
						continue;
					} else if (existAttributeinner != null && (valueReference == null || valueReference.isEmpty())) {
						existAttributeinner.setVoided(Boolean.TRUE);
						labTestAttributes.add(existAttributeinner);
					} else if (existAttributeinner == null && (valueReference != null && !valueReference.isEmpty())) {
						testAttribute.setValueReferenceInternal(valueReference);
						labTestAttributes.add(testAttribute);
					} else if (existAttributeinner.getValueReference().equalsIgnoreCase(valueReference)) {
						continue;
					} else {
						labTestAttributes.add(existingTestAttribute(existAttributeinner));
						testAttribute.setValueReferenceInternal(valueReference);
						labTestAttributes.add(testAttribute);
					}
				}

			}
			// save the file
			if (documentTypeFile == null || documentTypeFile.isEmpty()) {} else {
				try {
					String fileDirectory = Context.getAdministrationService()
					        .getGlobalProperty("commonlabtest.fileDirectory");
					FileCopyUtils.copy(documentTypeFile.getBytes(), new FileOutputStream(
					        fileDirectory + "/" + documentTypeFile.getOriginalFilename().replace(" ", "-")));
					String name = documentTypeFile.getOriginalFilename().replace(" ", "-");
					labTest.setFilePath(fileDirectory + "/" + name);
					Context.getService(CommonLabTestService.class).saveLabTest(labTest); // need to review this lines
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			// change the sample status ...
			List<LabTestSample> labTestSampleList = commonLabTestService.getLabTestSamples(labTest, Boolean.FALSE);
			for (LabTestSample labTestSample : labTestSampleList) {
				if (labTestSample.getStatus().equals(LabTestSampleStatus.ACCEPTED)) {
					labTestSample.setStatus(LabTestSampleStatus.PROCESSED);
					labTestSample.setProcessedDate(new Date());
					Context.getService(CommonLabTestService.class).saveLabTestSample(labTestSample);
				}
			}

			commonLabTestService.saveLabTestAttributes(labTestAttributes);
		}
		catch (Exception e) {
			status = Consts.ERROR_TESTRESULT_NOT_SAVE;
			model.addAttribute("error", status);
		}

		request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, Consts.TEST_RESULT_SAVED);
		return "redirect:../../patientDashboard.form?patientId=" + labTest.getOrder().getPatient().getPatientId();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/module/commonlabtest/voidlabtestresult.form")
	public String onVoid(ModelMap model, HttpSession httpSession, HttpServletRequest request,
	        @RequestParam("testOrderId") Integer testOrderId, @RequestParam("patientId") Integer patientId,
	        @RequestParam("voidReason") String voidReason) {

		commonLabTestService = Context.getService(CommonLabTestService.class);
		String status;
		if (Context.getAuthenticatedUser() == null) {
			return "redirect:../../login.htm";
		}
		try {
			LabTest labTest = commonLabTestService.getLabTest(testOrderId);
			commonLabTestService.voidLabTestAttributes(labTest, voidReason);
		}
		catch (Exception e) {
			status = Consts.ERROR_VOID_RESULT;
			model.addAttribute("error", status);
		}

		request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, Consts.SUCCESSFULLY_VOIDED_RESULT);
		return "redirect:../../patientDashboard.form?patientId=" + patientId;

	}

	public String getDataType(String dataTypeName) {

		if (dataTypeName.equals(Consts.DATATYPE_BOOLEAN_OPENMRS) || dataTypeName.equals(Consts.BOOLEAN)) {
			return Consts.BOOLEAN;
		} else if (dataTypeName.equals(Consts.DATATYOE_FREE_TEXT_OPENMRS) || dataTypeName.equals(Consts.DATATYOE_TEXT)) {
			return Consts.DATATYOE_TEXT;
		} else if (dataTypeName.equals(Consts.DATATYPE_CONCEPT_OPENMRS) || dataTypeName.equals(Consts.DATATYPE_CODED)) {
			return Consts.DATATYPE_CODED;
		} else if (dataTypeName.equals(Consts.DATATYPE_LOCATION_OPENMRS)) {
			return "location";
		} else if (dataTypeName.endsWith(Consts.DATATYPE_DATETIME_OPENMRS) || dataTypeName.equals(Consts.DATATYPE_DATE)
		        || dataTypeName.equals(Consts.DATATYPE_DATETIME)) {
			return Consts.DATATYPE_DATE;
		} else if (dataTypeName.equals(Consts.DATATYPE_FLOAT_OPENMRS) || dataTypeName.equals(Consts.DATATYPE_NUMERIC)) {
			return Consts.DATATYPE_NUMERIC;
		} else if (dataTypeName.equals(Consts.DATATYPE_REGEX_OPENMRS)) {
			return Consts.DATATYPE_REGEX;
		} else if (dataTypeName.equals(Consts.DATATYPE_LONGTEXT_OPENMRS)) {
			return Consts.DATATYPE_TEXTAREA;
		}

		return Consts.NA;
	}

	public JsonObject getAttributeTypeJsonObj(LabTestAttributeType labTestAttributeType,
	        List<LabTestAttribute> testAttributes) {
		JsonObject objAttrType = new JsonObject();
		objAttrType.addProperty(Consts.NAME, labTestAttributeType.getName());
		objAttrType.addProperty(Consts.MIN_OCCURS, labTestAttributeType.getMinOccurs());
		objAttrType.addProperty(Consts.MAX_OCCURS, labTestAttributeType.getMaxOccurs());
		objAttrType.addProperty(Consts.SORT_WEIGHT, labTestAttributeType.getSortWeight());
		objAttrType.addProperty(Consts.CONFIG, labTestAttributeType.getDatatypeConfig());
		objAttrType.addProperty(Consts.HINT, (labTestAttributeType.getHint() == null) ? "" : labTestAttributeType.getHint());

		if (testAttributes.size() > 0) {
			for (LabTestAttribute labTestAttribute : testAttributes) {
				if (!labTestAttribute.getVoided()) {
					if (labTestAttribute.getAttributeType().getLabTestAttributeTypeId() == labTestAttributeType
					        .getLabTestAttributeTypeId()) {
						objAttrType.addProperty(Consts.VALUE, labTestAttribute.getValueReference());
						objAttrType.addProperty(Consts.TEST_ATTRIBUTE_ID, labTestAttribute.getId());
					}
				}
			}
		} else {
			objAttrType.addProperty(Consts.VALUE, "");
			objAttrType.addProperty(Consts.TEST_ATTRIBUTE_ID, "");
		}
		objAttrType.addProperty(Consts.ID, labTestAttributeType.getId());
		if (labTestAttributeType.getDatatypeClassname().equalsIgnoreCase(Consts.DATATYPE_CONCEPT_OPENMRS)) {
			if (labTestAttributeType.getDatatypeConfig() != null && labTestAttributeType.getDatatypeConfig() != ""
			        && !labTestAttributeType.getDatatypeConfig().isEmpty()) {
				Concept concept = Context.getConceptService()
				        .getConcept(Integer.parseInt(labTestAttributeType.getDatatypeConfig()));

				if (concept.getDatatype().getName().equals(Consts.DATATYPE_CODED)) {
					JsonArray codedArray = new JsonArray();
					Collection<ConceptAnswer> ans = concept.getAnswers();
					for (ConceptAnswer ca : ans) {
						JsonObject jo = new JsonObject();
						jo.addProperty(Consts.CONCEPT_NAME, ca.getAnswerConcept().getName().getName());
						jo.addProperty(Consts.CONCEPT_ID, ca.getAnswerConcept().getConceptId());
						codedArray.add(jo);
					}
					objAttrType.add(Consts.CONCEPT_OPTIONS, codedArray);
					objAttrType.addProperty(Consts.DATA_TYPE, getDataType(labTestAttributeType.getDatatypeClassname()));

				} else {
					objAttrType.addProperty(Consts.DATA_TYPE, getDataType(concept.getDatatype().getName()));
				}
			}
		} else {
			objAttrType.addProperty(Consts.DATA_TYPE, getDataType(labTestAttributeType.getDatatypeClassname()));
		}
		return objAttrType;
	}

	public JsonArray getAttributeTypeList(List<LabTestAttributeType> labTestAttributeTypeList, int testOrderId,
	        List<LabTestAttribute> labTestAttributes) {

		List<String> holderGroupIdList = new ArrayList<String>();
		JsonArray parentJsonArray = new JsonArray();
		JsonObject labTestGroupObj;
		for (LabTestAttributeType labTestAttributeType : labTestAttributeTypeList) {
			labTestGroupObj = new JsonObject();
			JsonArray jsonChildArray = new JsonArray();
			String groupName = labTestAttributeType.getGroupName();
			if (groupName != null && !groupName.isEmpty()) {
				if (holderGroupIdList.contains(groupName)) {
					continue;
				}
				holderGroupIdList.add(labTestAttributeType.getGroupName());
				labTestGroupObj.addProperty(Consts.GROUP_NAME, labTestAttributeType.getGroupName());
				List<LabTestAttributeType> childLabTestATList = getFilterAttributeTypes(labTestAttributeType,
				    labTestAttributeTypeList);
				JsonObject labTestSubGroupObj;
				List<String> holderSubGroupIdList = new ArrayList<String>();
				for (LabTestAttributeType labTestAttributeTypechld : childLabTestATList) {
					labTestSubGroupObj = new JsonObject();
					JsonArray jsonSubGroupArray = new JsonArray();
					String subGroupName = labTestAttributeTypechld.getMultisetName();
					if (subGroupName != null && !subGroupName.isEmpty()) {
						if (holderSubGroupIdList.contains(subGroupName)) {
							continue;
						}
						holderSubGroupIdList.add(subGroupName);
						labTestSubGroupObj.addProperty(Consts.SUB_GROUP_NAME, subGroupName);
						List<LabTestAttributeType> subGroupLabTestATList = getFilterSubGroupAttributeTypes(subGroupName,
						    childLabTestATList);
						for (LabTestAttributeType labTestAttributeTypeSb : subGroupLabTestATList) {
							jsonSubGroupArray.add(getAttributeTypeJsonObj(labTestAttributeTypeSb, labTestAttributes));
						}
						labTestSubGroupObj.add(Consts.SUB_DETAILS, jsonSubGroupArray);
						jsonChildArray.add(labTestSubGroupObj);
					} else {
						jsonChildArray.add(getAttributeTypeJsonObj(labTestAttributeTypechld, labTestAttributes));
					}
				}
				labTestGroupObj.add(Consts.DETAILS, jsonChildArray);
				parentJsonArray.add(labTestGroupObj);

			} else {
				parentJsonArray.add(getAttributeTypeJsonObj(labTestAttributeType, labTestAttributes));
			}
		}
		return parentJsonArray;
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

	private List<LabTestAttributeType> getFilterSubGroupAttributeTypes(String multisetName,
	        List<LabTestAttributeType> listLabTestAttributeType) {
		List<LabTestAttributeType> filterLabTestAttributeTypes = new ArrayList<LabTestAttributeType>();

		if (!listLabTestAttributeType.isEmpty()) {
			for (LabTestAttributeType filterLabTestAttributeType : listLabTestAttributeType) {
				String multisetNames = filterLabTestAttributeType.getMultisetName();
				if (multisetNames != null && !multisetNames.isEmpty()) {
					if (multisetNames.equals(multisetName)) {
						filterLabTestAttributeTypes.add(filterLabTestAttributeType);
					}
				}
			}
		}
		return filterLabTestAttributeTypes;
	}

	private LabTestAttribute getExistingAttributeValue(List<LabTestAttribute> labTestAttributes, int attributeTypeId) {
		if (labTestAttributes != null && labTestAttributes.size() > 0) {
			for (int i = 0; i < labTestAttributes.size(); i++) {
				if (!labTestAttributes.get(i).getVoided()
				        && labTestAttributes.get(i).getAttributeType().getId() == attributeTypeId) {
					return labTestAttributes.get(i);
				}
			}
		}
		return null;
	}

	private LabTestAttribute existingTestAttribute(LabTestAttribute labTestAttribute) {
		LabTestAttribute existAttribute = new LabTestAttribute();

		existAttribute.setLabTest(labTestAttribute.getLabTest());
		existAttribute.setAttributeType(labTestAttribute.getAttributeType());
		existAttribute.setValueReferenceInternal(labTestAttribute.getValueReference());
		existAttribute.setVoided(Boolean.TRUE);
		existAttribute.setVoidReason(Consts.ATTRIBUTE_VALUE_CHANGED);

		return existAttribute;
	}

}
