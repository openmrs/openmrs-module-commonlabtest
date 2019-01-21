package org.openmrs.module.commonlabtest.web.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class LabTestTypeController {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private final String SUCCESS_ADD_FORM_VIEW = "/module/commonlabtest/addLabTestType";

	CommonLabTestService commonLabTestService;

	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/addLabTestType.form")
	public String showForm(ModelMap model, @RequestParam(value = "uuid", required = false) String uuid,
			@RequestParam(value = "error", required = false) String error) {
		commonLabTestService = Context.getService(CommonLabTestService.class);
		LabTestType testType;
		if (uuid == null || uuid.equalsIgnoreCase("")) {
			testType = new LabTestType();
		} else {
			testType = commonLabTestService.getLabTestTypeByUuid(uuid);
		}
		ConceptClass conceptClass = Context.getConceptService().getConceptClassByName("Test");
		List<Concept> conceptlist = Context.getConceptService().getConceptsByClass(conceptClass);
		model.addAttribute("labTestType", testType);
		JsonArray jsonArray = new JsonArray();
		for (Concept c : conceptlist) {
			if (c.getRetired())
				continue;
			JsonObject json = new JsonObject();
			json.addProperty("id", c.getId() + "");
			json.addProperty("name", c.getName() != null ? c.getName().getName() : "");
			json.addProperty("shortName",
					c.getShortNameInLocale(Locale.ENGLISH) != null
							? c.getShortNameInLocale(Locale.ENGLISH).getName()
							: "");
			json.addProperty("description", c.getDescription() != null ? c.getDescription().getDescription() : "");
			jsonArray.add(json);
		}
		model.addAttribute("conceptsJson", jsonArray);
		model.addAttribute("error", error);
		return SUCCESS_ADD_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/module/commonlabtest/addLabTestType.form")
	public String onSubmit(ModelMap model, HttpSession httpSession,
			@ModelAttribute("anyRequestObject") Object anyRequestObject, HttpServletRequest request,
			@ModelAttribute("labTestType") LabTestType labTestType, BindingResult result) {
		commonLabTestService = Context.getService(CommonLabTestService.class);
		String status = "";
		if (Context.getAuthenticatedUser() == null) {
			return "redirect:../../login.htm";
		}
		if (result.hasErrors()) {
			status = "Invalid Reference concept Id entered";
			model.addAttribute("error", status);
			if (labTestType.getLabTestTypeId() == null) {
				return "redirect:addLabTestType.form";
			} else {
				return "redirect:addLabTestType.form?uuid=" + labTestType.getUuid();
			}
		} else {
			try {
				commonLabTestService.saveLabTestType(labTestType);
				StringBuilder sb = new StringBuilder();
				sb.append("Lab Test Type with Uuid :");
				sb.append(labTestType.getUuid());
				sb.append(" is  saved!");
				status = sb.toString();
			} catch (Exception e) {
				status = "could not save Lab Test Type.";
				e.printStackTrace();
				model.addAttribute("error", status);
				if (labTestType.getLabTestTypeId() == null) {
					return "redirect:addLabTestType.form";
				} else {
					return "redirect:addLabTestType.form?uuid=" + labTestType.getUuid();
				}
			}
		}
		model.addAttribute("save", status);
		return "redirect:manageLabTestTypes.form";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/module/commonlabtest/retirelabtesttype.form")
	public String onRetire(ModelMap model, HttpSession httpSession, HttpServletRequest request,
			@RequestParam("uuid") String uuid, @RequestParam("retireReason") String retireReason) {
		commonLabTestService = Context.getService(CommonLabTestService.class);
		LabTestType labTestType = commonLabTestService.getLabTestTypeByUuid(uuid);
		String status;
		if (Context.getAuthenticatedUser() == null) {
			return "redirect:../../login.htm";
		}
		try {
			commonLabTestService.retireLabTestType(labTestType, retireReason);
			StringBuilder sb = new StringBuilder();
			sb.append("Lab Test Type with Uuid :");
			sb.append(labTestType.getUuid());
			sb.append(" is permanently retired!");
			status = sb.toString();
		} catch (Exception exception) {
			status = "could not retire Lab Test Type.";
			exception.printStackTrace();
			model.addAttribute("error", status);
			if (labTestType.getLabTestTypeId() == null) {
				return "redirect:addLabTestType.form";
			} else {
				return "redirect:addLabTestType.form?uuid=" + labTestType.getUuid();
			}
		}
		model.addAttribute("save", status);
		return "redirect:manageLabTestTypes.form";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/module/commonlabtest/deletelabtesttype.form")
	public String onDelete(ModelMap model, HttpSession httpSession, HttpServletRequest request,
			@RequestParam("uuid") String uuid) {
		LabTestType labTestType = Context.getService(CommonLabTestService.class).getLabTestTypeByUuid(uuid);
		commonLabTestService = Context.getService(CommonLabTestService.class);
		String status;
		try {
			commonLabTestService.deleteLabTestType(labTestType, true);
			StringBuilder sb = new StringBuilder();
			sb.append("Lab Test Type with Uuid :");
			sb.append(labTestType.getUuid());
			sb.append(" is permanently deleted!");
			status = sb.toString();
		} catch (Exception exception) {
			status = "could not delete Lab Test Type.";
			exception.printStackTrace();
			model.addAttribute("error", status);
			if (labTestType.getLabTestTypeId() == null) {
				return "redirect:addLabTestType.form";
			} else {
				return "redirect:addLabTestType.form?uuid=" + labTestType.getUuid();
			}
		}
		model.addAttribute("save", status);
		return "redirect:manageLabTestTypes.form";

	}
}
