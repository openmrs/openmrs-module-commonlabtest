package org.openmrs.module.commonlabtest.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.TestOrder;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class LabTestOrderController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private final String SUCCESS_ADD_FORM_VIEW = "/module/commonlabtest/addLabTestOrder";
	
	@Autowired
	CommonLabTestService commonLabTestService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/addLabTestOrder.form")
	public String showForm(@RequestParam(required = true) Integer patientId,
	        @RequestParam(required = false) Integer testOrderId, @RequestParam(required = false) String error, ModelMap model) {
		
		LabTest labTest;
		if (testOrderId == null) {
			labTest = new LabTest();
		} else {
			labTest = commonLabTestService.getLabTest(testOrderId);
		}
		//Patient patient =Context.getPatientService().getPatient(patientId);
		
		List<Encounter> list = Context.getEncounterService().getEncountersByPatientId(patientId);
		//list.get(0).getEncounterType().getName();
		if (list.size() > 0) {
			Collections.sort(list, new Comparator<Encounter>() {
				
				@Override
				public int compare(Encounter o1, Encounter o2) {
					return o2.getEncounterDatetime().compareTo(o1.getEncounterDatetime());
				}
			});
			/*for (Encounter e : list.subList(0, list.size() - 1)) {
				System.out.println(e.getEncounterDatetime());
			}*/
		}
		List<LabTestType> testType = commonLabTestService.getAllLabTestTypes(Boolean.FALSE);
		List<LabTestType> labTestTypeHaveAttributes = new ArrayList<LabTestType>();
		for (LabTestType labTestTypeIt : testType) {
			if (commonLabTestService.getLabTestAttributeTypes(labTestTypeIt, Boolean.FALSE).size() > 0) {
				labTestTypeHaveAttributes.add(labTestTypeIt);
			}
		}
		
		model.addAttribute("labTest", labTest);
		model.addAttribute("patientId", patientId);
		model.addAttribute("testTypes", labTestTypeHaveAttributes);
		model.addAttribute("error", error);
		model.addAttribute("provider",
		    Context.getProviderService().getProvidersByPerson(Context.getAuthenticatedUser().getPerson(), false).iterator()
		            .next());
		if (list.size() > 10) {
			model.addAttribute("encounters", list.subList(0, list.size() - 1));
		} else {
			model.addAttribute("encounters", list);
		}
		
		return SUCCESS_ADD_FORM_VIEW;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/module/commonlabtest/addLabTestOrder.form")
	public String onSubmit(ModelMap model, HttpSession httpSession,
	        @ModelAttribute("anyRequestObject") Object anyRequestObject, HttpServletRequest request,
	        @ModelAttribute("labTest") LabTest labTest, BindingResult result) {
		
		String status = "";
		try {
			if (result.hasErrors()) {
				
			} else {
				/*	LabTestType lbTestType = commonLabTestService.getLabTestType(labTest.getLabTestType().getLabTestTypeId());
				Concept referConcept = lbTestType.getReferenceConcept();
				
				TestOrder testOrder;
				if (labTest.getOrder().getId() != null) {
					testOrder = (TestOrder) labTest.getOrder();
				} else {
					
					testOrder = new TestOrder();
				}
				
				// execute this when order and lab test are null
				testOrder.setCareSetting(labTest.getOrder().getCareSetting());
				testOrder.setConcept(referConcept);
				testOrder.setEncounter(labTest.getOrder().getEncounter());
				testOrder.setPatient(labTest.getOrder().getPatient());
				testOrder.setOrderer(labTest.getOrder().getOrderer());
				testOrder.setOrderType(labTest.getOrder().getOrderType());
				testOrder.setDateActivated(new java.util.Date());
				testOrder.setOrderId(labTest.getOrder().getOrderId());
				
				Order testParentOrder = testOrder;
				labTest.setOrder(testParentOrder);*/
				if (labTest.getTestOrderId() == null) {
					Order testParentOrder = labTest.getOrder();
					testParentOrder.setDateActivated(new java.util.Date());
					labTest.setOrder(testParentOrder);
				}
				commonLabTestService.saveLabTest(labTest);
				StringBuilder sb = new StringBuilder();
				sb.append("Lab Test Order with Uuid :");
				sb.append(labTest.getUuid());
				sb.append(" is  saved!");
				status = sb.toString();
			}
		}
		catch (Exception e) {
			status = "Error! could not save Lab Test Order";
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
		//model.addAttribute("status", status);
		return "redirect:../../patientDashboard.form?patientId=" + labTest.getOrder().getPatient().getPatientId();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/module/commonlabtest/voidlabtestorder.form")
	public String onVoid(ModelMap model, HttpSession httpSession, HttpServletRequest request,
	        @RequestParam("uuid") String uuid, @RequestParam("voidReason") String voidReason) {
		LabTest labTest = commonLabTestService.getLabTestByUuid(uuid);
		String status;
		try {
			commonLabTestService.voidLabTest(labTest, voidReason);
			StringBuilder sb = new StringBuilder();
			sb.append("Lab Test order with Uuid :");
			sb.append(labTest.getUuid());
			sb.append(" is  retired!");
			status = sb.toString();
		}
		catch (Exception e) {
			status = "Error! could not save Lab Test Order";
			e.printStackTrace();
			if (labTest.getTestOrderId() == null) {
				return "redirect:addLabTestOrder.form?patientId=" + labTest.getOrder().getPatient().getPatientId();
			} else {
				return "redirect:addLabTestOrder.form?patientId=" + labTest.getOrder().getPatient().getPatientId()
				        + "&testOrderId=" + labTest.getTestOrderId();
			}
		}
		model.addAttribute("status", status);
		//  model.addAttribute("patientId", labTest.get);
		//model.addAttribute("status", status);
		int patientId = labTest.getOrder().getPatient().getPatientId();
		request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Test order retire successfully");
		return "redirect:../../patientDashboard.form?patientId=" + patientId;
		
	}
	
}
