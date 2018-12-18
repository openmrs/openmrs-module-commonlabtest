package org.openmrs.module.commonlabtest.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.web.controller.PortletController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author engrmahmed14@gmail.com
 */
@Controller
@RequestMapping("**/patientLabTests.portlet")
public class LabTestOrderPortletController extends PortletController {
	
	@Override
	protected void populateModel(HttpServletRequest request, Map<String, Object> model) {
		
		String patientId = request.getParameter("patientId");
		if (patientId != null) {
			int id = Integer.parseInt(patientId);
			Patient patient = Context.getPatientService().getPatient(id);
			if (patient != null) {
				List<LabTest> testList = Context.getService(CommonLabTestService.class).getLabTests(patient, false);
				model.put("testOrder", testList);
			}
		}
		
	}
	
}
