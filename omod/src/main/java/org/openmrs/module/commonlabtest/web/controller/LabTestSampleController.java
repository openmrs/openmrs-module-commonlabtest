package org.openmrs.module.commonlabtest.web.controller;

import java.util.ArrayList;
import java.util.Collection;
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
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.utility.Consts;
import org.openmrs.web.WebConstants;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LabTestSampleController {

	protected final Log log = LogFactory.getLog(getClass());

	private final String SUCCESS_ADD_FORM_VIEW = "/module/commonlabtest/addLabTestSample";

	CommonLabTestService commonLabTestService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(Consts.simpleDateFormat, true));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/module/commonlabtest/addLabTestSample.form")
	public String showForm(HttpServletRequest request, ModelMap model, @RequestParam(required = true) Integer patientId,
	        @RequestParam(required = false) Integer testSampleId, @RequestParam(required = false) Integer orderId,
	        @RequestParam(required = false) String error) {

		commonLabTestService = Context.getService(CommonLabTestService.class);
		String orderDate = "";
		if (orderId != null) {
			LabTest labTest = commonLabTestService.getLabTest(orderId);
			if (labTest == null) {
				request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Consts.TEST_ORDER_DOES_NOT_EXIST_MESSAGE);
				return "redirect:../../patientDashboard.form?patientId=" + patientId;
			} else if (labTest.getVoided()) {
				request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Consts.TEST_ORDER_VOIED_MESSAGE);
				return "redirect:../../patientDashboard.form?patientId=" + patientId;
			}
			orderDate = Consts.simpleDateFormat.format(labTest.getOrder().getEncounter().getEncounterDatetime());
		}

		LabTestSample labTestSample;
		if (testSampleId == null) {
			labTestSample = new LabTestSample();
		} else {
			labTestSample = commonLabTestService.getLabTestSample(testSampleId);
		}
		// get Specimen Type .
		String specimenTypeUuid = Context.getAdministrationService()
		        .getGlobalProperty("commonlabtest.specimenTypeConceptUuid");
		Concept specimenType = Context.getConceptService().getConceptByUuid(specimenTypeUuid);
		if (specimenType != null && specimenType.getSetMembers().size() > 0) {
			List<Concept> specimenTypeConcepts = specimenType.getSetMembers();
			model.put(Consts.SPECIMENT_TYPE, specimenTypeConcepts);
		}
		// get Specimen Site
		String specimenSiteUuid = Context.getAdministrationService()
		        .getGlobalProperty("commonlabtest.specimenSiteConceptUuid");
		Concept specimenSiteSet = Context.getConceptService().getConceptByUuid(specimenSiteUuid);
		if (specimenSiteSet != null && specimenSiteSet.getAnswers().size() > 0) {
			Collection<ConceptAnswer> specimenSiteConcepts = specimenSiteSet.getAnswers();
			List<ConceptAnswer> specimenSiteConceptlist;
			if (specimenSiteConcepts instanceof List)
				specimenSiteConceptlist = (List<ConceptAnswer>) specimenSiteConcepts;
			else
				specimenSiteConceptlist = new ArrayList<ConceptAnswer>(specimenSiteConcepts);

			model.put(Consts.SPECIMENT_SITE, specimenSiteConceptlist);
		}

		// get test units
		String testUnitsProperty = Context.getAdministrationService()
		        .getGlobalProperty("commonlabtest.testunitsConceptUuid");
		Concept testUnitsUuid = Context.getConceptService().getConceptByUuid(testUnitsProperty);
		if (testUnitsUuid != null && testUnitsUuid.getAnswers().size() > 0) {
			Collection<ConceptAnswer> testUnitsConcepts = testUnitsUuid.getAnswers();
			List<ConceptAnswer> testUnitsConceptlist;
			if (testUnitsConcepts instanceof List)
				testUnitsConceptlist = (List<ConceptAnswer>) testUnitsConcepts;
			else
				testUnitsConceptlist = new ArrayList<ConceptAnswer>(testUnitsConcepts);
			model.put(Consts.TEST_UNIT, testUnitsConceptlist);
		}

		model.addAttribute(Consts.TEST_SAMPLE, labTestSample);
		model.addAttribute(Consts.PATIENT_ID, patientId);
		model.addAttribute(Consts.ORDER_ENC_DATE, orderDate);
		model.addAttribute(Consts.ORDER_ID, orderId);
		model.addAttribute(Consts.ERROR, error);
		model.addAttribute(Consts.PROVIDER, Context.getProviderService()
		        .getProvidersByPerson(Context.getAuthenticatedUser().getPerson(), false).iterator().next());
		return SUCCESS_ADD_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/module/commonlabtest/addLabTestSample.form")
	public String onSubmit(ModelMap model, HttpSession httpSession,
	        @ModelAttribute("anyRequestObject") Object anyRequestObject, HttpServletRequest request,
	        @ModelAttribute("testSample") LabTestSample labTestSample, BindingResult result) {

		commonLabTestService = Context.getService(CommonLabTestService.class);
		String status = "";
		if (Context.getAuthenticatedUser() == null) {
			return "redirect:../../login.htm";
		}
		try {
			if (result.hasErrors()) {
				/// If we get any exception while binding it should be redirected to same page
				/// with binding error
				if (labTestSample.getLabTestSampleId() == null) {
					return "redirect:addLabTestSample.form?patientId="
					        + labTestSample.getLabTest().getOrder().getPatient().getPatientId() + "&orderId="
					        + labTestSample.getLabTest().getOrder().getId();
				} else {
					return "redirect:addLabTestSample.form?patientId="
					        + labTestSample.getLabTest().getOrder().getPatient().getPatientId() + "&testSampleId="
					        + labTestSample.getLabTest().getTestOrderId() + "&orderId="
					        + labTestSample.getLabTest().getOrder().getId();
				}
			} else {
				if (labTestSample.getId() == null)
					labTestSample.setStatus(LabTestSampleStatus.COLLECTED);
				commonLabTestService.saveLabTestSample(labTestSample);
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(Consts.LAB_TEST_SAMPLE_UUID_MESSAGE);
				stringBuilder.append(labTestSample.getUuid());
				stringBuilder.append(" is  saved!");
				status = stringBuilder.toString();
			}
		}
		catch (Exception e) {
			status = Consts.COULD_NOT_SAVE_TEST_SAMPLE_MESSAGE;
			e.printStackTrace();
			model.addAttribute(Consts.ERROR, status);
			if (labTestSample.getLabTestSampleId() == null) {
				return "redirect:addLabTestSample.form?patientId="
				        + labTestSample.getLabTest().getOrder().getPatient().getPatientId() + "&orderId="
				        + labTestSample.getLabTest().getOrder().getId();
			} else {
				return "redirect:addLabTestSample.form?patientId="
				        + labTestSample.getLabTest().getOrder().getPatient().getPatientId() + "&testSampleId="
				        + labTestSample.getLabTest().getTestOrderId() + "&orderId="
				        + labTestSample.getLabTest().getOrder().getId();
			}
		}
		model.addAttribute("save", status);
		return "redirect:manageLabTestSamples.form?patientId="
		        + labTestSample.getLabTest().getOrder().getPatient().getPatientId() + "&testOrderId="
		        + labTestSample.getLabTest().getTestOrderId();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/module/commonlabtest/voidlabtestsample.form")
	public String onVoid(ModelMap model, HttpSession httpSession, HttpServletRequest request,
	        @RequestParam("uuid") String uuid, @RequestParam("voidReason") String voidReason) {
		commonLabTestService = Context.getService(CommonLabTestService.class);
		LabTestSample labTestSample = commonLabTestService.getLabTestSampleByUuid(uuid);
		String status;
		if (Context.getAuthenticatedUser() == null) {
			return "redirect:../../login.htm";
		}
		try {
			commonLabTestService.voidLabTestSample(labTestSample, voidReason);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(Consts.LAB_TEST_SAMPLE_UUID_MESSAGE);
			stringBuilder.append(labTestSample.getUuid());
			stringBuilder.append(" is  voided!");
			status = stringBuilder.toString();
		}
		catch (Exception e) {
			status = Consts.COULD_NOT_VOID_TEST_SAMPLE_MESSAGE;
			model.addAttribute(Consts.ERROR, status);
			if (labTestSample.getLabTestSampleId() == null) {
				return "redirect:addLabTestSample.form?patientId="
				        + labTestSample.getLabTest().getOrder().getPatient().getPatientId();
			} else {
				return "redirect:addLabTestSample.form?patientId="
				        + labTestSample.getLabTest().getOrder().getPatient().getPatientId() + "&testSampleId="
				        + labTestSample.getLabTest().getTestOrderId();
			}
		}
		model.addAttribute("save", status);
		return "redirect:manageLabTestSamples.form?patientId="
		        + labTestSample.getLabTest().getOrder().getPatient().getPatientId() + "&testOrderId="
		        + labTestSample.getLabTest().getTestOrderId();
	}
}
