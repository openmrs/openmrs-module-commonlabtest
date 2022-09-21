package org.openmrs.module.commonlabtest.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestGroup;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class LabTestRequestController {

	protected final Log log = LogFactory.getLog(getClass());

	private final String SUCCESS_ADD_FORM_VIEW = "/module/commonlabtest/addLabTestRequest";

	CommonLabTestService commonLabTestService;

	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/addLabTestRequest.form")
	public String showForm(HttpServletRequest request, @RequestParam(required = false) String error,
	        @RequestParam(required = false) Integer patientId, ModelMap model) {
		commonLabTestService = Context.getService(CommonLabTestService.class);
		if (Context.getAuthenticatedUser() == null) {
			return "redirect:../../login.htm";
		}
		JsonArray testParentArray = new JsonArray();
		List<LabTestGroup> labTestGroupList = Arrays.asList(LabTestGroup.values());
		Collections.sort(labTestGroupList, new Comparator<LabTestGroup>() {

			@Override
			public int compare(LabTestGroup o1, LabTestGroup o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});

		for (LabTestGroup labTestGroup : labTestGroupList) {
			JsonObject labTestGroupObj = new JsonObject();
			JsonArray jsonChildArray = new JsonArray();
			List<LabTestType> labTestTypeList = commonLabTestService.getLabTestTypes(null, null, labTestGroup, null, null,
			    Boolean.FALSE);
			if (!(labTestTypeList.size() > 0) || labTestTypeList.equals("") || labTestTypeList.isEmpty()) {
				continue; // skip the current iteration.
			} else if (labTestTypeList.size() == 1 && labTestGroup.equals(LabTestGroup.OTHER)) {
				continue;
			}
			if (labTestTypeList.size() > 0) {
				Collections.sort(labTestTypeList, new Comparator<LabTestType>() {

					@Override
					public int compare(LabTestType s1, LabTestType s2) {
						return s1.getName().compareToIgnoreCase(s2.getName());
					}
				});
			}

			labTestGroupObj.addProperty("testGroup", labTestGroup.name());
			for (LabTestType labTestType : labTestTypeList) {
				if (labTestType.getShortName().equals("UNKNOWN")) {
					continue;
				}
				JsonObject labTestTyeChild = new JsonObject();
				labTestTyeChild.addProperty("testTypeId", labTestType.getId());
				labTestTyeChild.addProperty("testTypeName", labTestType.getName());
				jsonChildArray.add(labTestTyeChild);
			}
			labTestGroupObj.add("testType", jsonChildArray);
			testParentArray.add(labTestGroupObj);
		}
		List<Encounter> encounterList = Context.getEncounterService().getEncountersByPatientId(patientId);
		if (encounterList.size() > 0) {
			Collections.sort(encounterList, new Comparator<Encounter>() {

				@Override
				public int compare(Encounter o1, Encounter o2) {
					return o2.getEncounterDatetime().compareTo(o1.getEncounterDatetime());
				}
			});
		}

		if (encounterList.size() > 10) {
			model.addAttribute("encounters", encounterList.subList(0, encounterList.size() - 1));
		} else {
			model.addAttribute("encounters", encounterList);
		}
		model.addAttribute("labTestTypes", testParentArray);
		model.addAttribute("patientId", patientId);

		return SUCCESS_ADD_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/module/commonlabtest/addLabTestRequest.form")
	@ResponseBody
	public boolean onSubmit(ModelMap model, HttpSession httpSession, HttpServletRequest request, @RequestBody String json,
	        @RequestParam(required = false) Integer patientId) {
		commonLabTestService = Context.getService(CommonLabTestService.class);
		String status = "";
		boolean boolStatus = Boolean.TRUE;
		try {
			JsonArray arry = (JsonArray) new JsonParser().parse(json);
			List<LabTest> labTestArray = new ArrayList<LabTest>();
			for (int i = 0; i < arry.size(); i++) {
				LabTest labTest = new LabTest();
				JsonObject jsonObject = arry.get(i).getAsJsonObject();
				Order order = new Order();
				/* These attributes were added in v2.x */
				// order.setCareSetting(Context.getOrderService().getCareSetting(1));
				// order.setAction(Action.NEW);
				Encounter encounter = Context.getEncounterService().getEncounter(jsonObject.get("encounterId").getAsInt());
				order.setEncounter(encounter);
				order.setOrderer(Context.getAuthenticatedUser());
				order.setOrderType(Context.getOrderService().getOrderType(3));
				order.setStartDate(encounter.getEncounterDatetime());
				order.setPatient(Context.getPatientService().getPatient(patientId));
				Concept concept = Context.getConceptService().getConcept(jsonObject.get("testTypeId").getAsInt());
				order.setConcept(concept);
				labTest.setOrder(order);
				labTest.setLabInstructions(jsonObject.get("labInstructions").getAsString());
				labTest.setLabReferenceNumber(jsonObject.get("labReferenceNumber").getAsString());
				LabTestType labTestType = commonLabTestService.getLabTestType(jsonObject.get("testTypeId").getAsInt());
				labTest.setLabTestType(labTestType);
				labTestArray.add(labTest);
			}
			for (LabTest labTest : labTestArray) {
				commonLabTestService.saveLabTest(labTest);
			}
		}
		catch (Exception e) {
			status = "could not save Lab Test Request";
			e.printStackTrace();
			model.addAttribute("error", status);
			boolStatus = Boolean.FALSE;
		}
		if (boolStatus) {
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Test Request saved successfully");
		}
		return boolStatus; // "redirect:../../patientDashboard.form?patientId="
		                   // + patientId;
	}

}
