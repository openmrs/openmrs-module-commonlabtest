package org.openmrs.module.commonlabtest.web.controller;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.web.controller.PortletController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author engrmahmed14@gmail.com
 */
@Controller
@RequestMapping("**/patientLabTests.portlet")
public class LabTestOrderPortletController extends PortletController {
	
	/*	@Autowired
		CommonLabTestService commonLabTestService;*/
	
	/*@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	        IOException {
		Object uri = request.getAttribute("javax.servlet.include.servlet_path");
		String portletPath = "";
		
		portletPath = uri.toString();
		
		// Allowable extensions are '' (no extension) and '.portlet'
		if (portletPath.endsWith("portlet")) {
			portletPath = portletPath.replace(".portlet", "");
		} else if (portletPath.endsWith("jsp")) {
			throw new ServletException(
			        "Illegal extension used for portlet: '.jsp'. Allowable extensions are '' (no extension) and '.portlet'");
		}
		
		log.debug("Loading portlet: " + portletPath);
		Map<String, Object> model = null;
		{
			HttpSession session = request.getSession();
			String uniqueRequestId = (String) request.getAttribute(WebConstants.INIT_REQ_UNIQUE_ID);
			String lastRequestId = (String) session.getAttribute(WebConstants.OPENMRS_PORTLET_LAST_REQ_ID);
			if (uniqueRequestId.equals(lastRequestId)) {
				model = (Map<String, Object>) session.getAttribute(WebConstants.OPENMRS_PORTLET_CACHED_MODEL);
				
				// remove cached parameters
				List<String> parameterKeys = (List<String>) model.get("parameterKeys");
				if (parameterKeys != null) {
					for (String key : parameterKeys) {
						model.remove(key);
					}
				}
			}
			if (model == null) {
				log.debug("creating new portlet model");
				model = new HashMap<String, Object>();
				session.setAttribute(WebConstants.OPENMRS_PORTLET_LAST_REQ_ID, uniqueRequestId);
				session.setAttribute(WebConstants.OPENMRS_PORTLET_CACHED_MODEL, model);
			}
		}
		
		String patientId = request.getParameter("patientId");
		System.out.println("Patient ID :: " + patientId);
		
		//populateModel(request, model);
		if (patientId != null) {
			int id = Integer.parseInt(patientId);
			Patient patient = Context.getPatientService().getPatient(id);
			if (patient != null) {
				System.out.println("Patient  :: " + patient);
				List<LabTest> testList = Context.getService(CommonLabTestServiceImpl.class).getLabTests(patient, false);
				//List<LabTest> testList = commonLabTestService.getLabTests(patient, false);
				System.out.println("test order list " + testList.size());
				
				model.put("testOrder", testList);
			}
		}
		return new ModelAndView(portletPath, "model", model);
		//return super.handleRequest(request, response);
	}*/
	
	@Override
	protected void populateModel(HttpServletRequest request, Map<String, Object> model) {
		
		String patientId = request.getParameter("patientId");
		System.out.println("Patient ID :: " + patientId);
		if (patientId != null) {
			int id = Integer.parseInt(patientId);
			Patient patient = Context.getPatientService().getPatient(id);
			if (patient != null) {
				System.out.println("Patient  :: " + patient);
				System.out.println("in test order portlet");
				System.out.println("===========================================================");
				List<LabTest> testList = Context.getService(CommonLabTestService.class).getLabTests(patient, false);
				//List<LabTest> testList = commonLabTestService.getLabTests(patient, false);
				System.out.println("TestOrder Size ::: " + testList.size());
				System.out.println("Order ::: " + (testList.size() > 0 ? testList.get(0).getOrder() : "zero"));
				System.out.println("=========================================================");
				model.put("testOrder", testList);
			}
		}
		
	}
	
	@RequestMapping(value = "/testSampleList/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getAuthentication(@PathVariable("id") int id) {
		
		return "abc";
	}
	
}
