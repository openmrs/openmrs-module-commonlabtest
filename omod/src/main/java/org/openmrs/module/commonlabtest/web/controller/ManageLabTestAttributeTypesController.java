package org.openmrs.module.commonlabtest.web.controller;

import java.util.List;

import javax.naming.Context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.CareSetting;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.TestOrder;
import org.openmrs.api.OrderContext;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/module/commonlabtest/manageLabTestAttributeTypes.form")
public class ManageLabTestAttributeTypesController {
	
	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/commonlabtest/manageLabTestAttributeTypes";
	
	/** Logger for this class */
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	CommonLabTestService commonLabTestService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showLabTestAttributeTypes(@RequestParam(required = false) String save, ModelMap model) {
		List<LabTestAttributeType> list = commonLabTestService.getAllLabTestAttributeTypes(Boolean.FALSE);
		//saveOrder();
		model.put("labTestAttributeTypes", list);
		model.addAttribute("status", save);
		return SUCCESS_FORM_VIEW;
	}
	
}
