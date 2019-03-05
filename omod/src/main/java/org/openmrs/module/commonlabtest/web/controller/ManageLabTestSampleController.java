package org.openmrs.module.commonlabtest.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.utility.Consts;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManageLabTestSampleController {

	protected final Log log = LogFactory.getLog(getClass());

	private final String SUCCESS_ADD_FORM_VIEW = "/module/commonlabtest/manageLabTestSamples";

	CommonLabTestService commonLabTestService;

	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/manageLabTestSamples.form")
	public String showLabTestSample(HttpServletRequest request, @RequestParam(required = true) Integer patientId,
	        @RequestParam(required = false) Integer testOrderId, @RequestParam(required = false) String save,
	        ModelMap model) {
		commonLabTestService = Context.getService(CommonLabTestService.class);
		List<LabTestSample> testSample;
		if (testOrderId == null) {
			testSample = new ArrayList<LabTestSample>();
		} else {
			LabTest labTest = commonLabTestService.getLabTest(testOrderId);
			if (labTest == null) {
				request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Consts.TEST_ORDER_DOES_NOT_EXIST_MESSAGE);
				return "redirect:../../patientDashboard.form?patientId=" + patientId;
			} else if (labTest.getVoided()) {
				request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Consts.TEST_ORDER_NOT_FOUND_MESSAGE);
				return "redirect:../../patientDashboard.form?patientId=" + patientId;
			}
			testSample = commonLabTestService.getLabTestSamples(labTest, Boolean.FALSE);
		}

		for (LabTestSample labTestSample : testSample) {
			if (labTestSample.getStatus().equals(LabTestSampleStatus.PROCESSED)) {
				model.addAttribute(Consts.SAMPLE_PROCESSED, Boolean.TRUE);
				break;
			} else {
				model.addAttribute(Consts.SAMPLE_PROCESSED, Boolean.FALSE);
			}
		}
		model.addAttribute(Consts.LAB_SAMPLE_TEST, testSample);
		model.addAttribute(Consts.ORDER_ID, testOrderId);
		model.addAttribute(Consts.PATIENT_ID, patientId);
		model.addAttribute(Consts.STATUS, save);

		return SUCCESS_ADD_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/module/commonlabtest/statuslabtestsample.form")
	public String onStatuChange(ModelMap model, HttpSession httpSession, HttpServletRequest request,
	        @RequestParam("uuid") String uuid, @RequestParam("patientId") String patientId,
	        @RequestParam(value = "rejectedReason", required = false) String rejectedReason,
	        @RequestParam(value = "isAccepted", required = false) String isAccepted) {

		commonLabTestService = Context.getService(CommonLabTestService.class);
		LabTestSample labTestSample = commonLabTestService.getLabTestSampleByUuid(uuid);
		String status;
		try {
			if (isAccepted.equals("1")) {
				labTestSample.setStatus(LabTestSampleStatus.ACCEPTED);
				labTestSample.setComments("");

			} else {
				labTestSample.setStatus(LabTestSampleStatus.REJECTED);
				labTestSample.setComments(rejectedReason);
			}
			commonLabTestService.saveLabTestSample(labTestSample);
			StringBuilder satatusString = new StringBuilder();
			satatusString.append(Consts.LAB_TEST_SAMPLE_UPDATED_MESSAGE);
			status = satatusString.toString();
		}
		catch (Exception e) {
			status = Consts.COULD_NOT_SAVE_LAB_TEST_SAMPLE;
			model.addAttribute(Consts.ERROR, status);
			return "redirect:manageLabTestSamples.form?patientId=" + patientId + "&testOrderId="
			        + labTestSample.getLabTest().getTestOrderId();

		}
		model.addAttribute(Consts.SAVED, status);
		return "redirect:manageLabTestSamples.form?patientId=" + patientId + "&testOrderId="
		        + labTestSample.getLabTest().getTestOrderId();
	}

}
