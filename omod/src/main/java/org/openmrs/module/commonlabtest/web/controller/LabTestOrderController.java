package org.openmrs.module.commonlabtest.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.Provider;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.CommonLabTestConfig;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LabTestOrderController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private final String SUCCESS_ADD_FORM_VIEW = "/module/commonlabtest/addLabTestOrder";
	
	CommonLabTestService commonLabTestService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/addLabTestOrder.form")
	public String showForm(@RequestParam(required = true) Integer patientId,
	        @RequestParam(required = false) Integer testOrderId, @RequestParam(required = false) String error,
	        ModelMap model) {
		commonLabTestService = Context.getService(CommonLabTestService.class);
		LabTest labTest;
		if (testOrderId == null) {
			labTest = new LabTest();
		} else {
			labTest = commonLabTestService.getLabTest(testOrderId);
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
		List<LabTestType> testType = commonLabTestService.getAllLabTestTypes(Boolean.FALSE);
		List<LabTestType> labTestTypeHavingAttributes = new ArrayList<LabTestType>();
		for (LabTestType labTestTypeIt : testType) {
			if (commonLabTestService.getLabTestAttributeTypes(labTestTypeIt, Boolean.FALSE).size() > 0) {
				labTestTypeHavingAttributes.add(labTestTypeIt);
			}
		}
		model.addAttribute("labTest", labTest);
		model.addAttribute("patientId", patientId);
		model.addAttribute("testTypes", labTestTypeHavingAttributes);
		model.addAttribute("error", error);
		Collection<Provider> providers = Context.getProviderService()
		        .getProvidersByPerson(Context.getAuthenticatedUser().getPerson(), false);
		if (providers == null || providers.isEmpty()) {} else {
			model.addAttribute("provider", Context.getProviderService()
			        .getProvidersByPerson(Context.getAuthenticatedUser().getPerson(), false).iterator().next());
		}
		// show only first 10 encounters
		if (encounterList.size() > 10) {
			model.addAttribute("encounters", encounterList.subList(0, encounterList.size() - 1));
		} else {
			model.addAttribute("encounters", encounterList);
		}
		return SUCCESS_ADD_FORM_VIEW;
	}
	
	@Authorized(CommonLabTestConfig.ADD_LAB_TEST_PRIVILEGE)
	@RequestMapping(method = RequestMethod.POST, value = "/module/commonlabtest/addLabTestOrder.form")
	public String onSubmit(ModelMap model, HttpSession httpSession,
	        @ModelAttribute("anyRequestObject") Object anyRequestObject, HttpServletRequest request,
	        @ModelAttribute("labTest") LabTest labTest, BindingResult result) {
		commonLabTestService = Context.getService(CommonLabTestService.class);
		String status = "";
		if (Context.getAuthenticatedUser() == null) {
			return "redirect:../../login.htm";
		}
		try {
			if (result.hasErrors()) {
				
			} else {
				if (labTest.getTestOrderId() == null) {
					Order testParentOrder = labTest.getOrder();
					testParentOrder.setDateActivated(labTest.getOrder().getEncounter().getEncounterDatetime());
					labTest.setOrder(testParentOrder);
				}
				commonLabTestService.saveLabTest(labTest);
			}
		}
		catch (Exception e) {
			status = "could not save Lab Test Order";
			e.printStackTrace();
			model.addAttribute("error", status);
			if (labTest.getTestOrderId() == null) {
				return "redirect:addLabTestOrder.form?patientId=" + labTest.getOrder().getPatient().getPatientId();
			} else {
				return "redirect:addLabTestOrder.form?patientId=" + labTest.getOrder().getPatient().getPatientId()
				        + "&testOrderId=" + labTest.getTestOrderId();
			}
		}
		request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Test order saved successfully");
		return "redirect:../../patientDashboard.form?patientId=" + labTest.getOrder().getPatient().getPatientId();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/module/commonlabtest/voidlabtestorder.form")
	public String onVoid(ModelMap model, HttpSession httpSession, HttpServletRequest request,
	        @RequestParam("uuid") String uuid, @RequestParam("voidReason") String voidReason) {
		commonLabTestService = Context.getService(CommonLabTestService.class);
		LabTest labTest = commonLabTestService.getLabTestByUuid(uuid);
		String status = "";
		// if user not login the redirect to login page...
		if (Context.getAuthenticatedUser() == null) {
			return "redirect:../../login.htm";
		}
		try {
			commonLabTestService.voidLabTest(labTest, voidReason);
		}
		catch (Exception e) {
			status = "could not void Lab Test Order";
			e.printStackTrace();
			model.addAttribute("error", status);
			if (labTest.getTestOrderId() == null) {
				return "redirect:addLabTestOrder.form?patientId=" + labTest.getOrder().getPatient().getPatientId();
			} else {
				return "redirect:addLabTestOrder.form?patientId=" + labTest.getOrder().getPatient().getPatientId()
				        + "&testOrderId=" + labTest.getTestOrderId();
			}
		}
		int patientId = labTest.getOrder().getPatient().getPatientId();
		request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Test order retire successfully");
		return "redirect:../../patientDashboard.form?patientId=" + patientId;
	}
	
}
