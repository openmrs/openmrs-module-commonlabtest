package org.openmrs.module.commonlabtest.web.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/module/commonlabtest/manageLabTestTypes.form")
public class ManageLabTestTypeController {
	
	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/commonlabtest/manageLabTestTypes";
	
	/** Logger for this class */
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	CommonLabTestService commonLabTestService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showLabTestTypes(@RequestParam(required = false) String save, ModelMap model) {
		
		List<LabTestType> list = commonLabTestService.getAllLabTestTypes(Boolean.FALSE);
		
		model.put("labTestTypes", list);
		model.addAttribute("status", save);
		return SUCCESS_FORM_VIEW;
	}
	
}
