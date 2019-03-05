package org.openmrs.module.commonlabtest.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.utility.Consts;
import org.openmrs.web.controller.PortletController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author engrmahmed14@gmail.com
 */
@Controller
@RequestMapping("**/patientLabTests.portlet")
public class LabTestOrderPortletController extends PortletController {

	@Override
	protected void populateModel(HttpServletRequest request, Map<String, Object> model) {

		String patientId = request.getParameter(Consts.PATIENT_ID);
		if (patientId != null) {
			int id = Integer.parseInt(patientId);
			Patient patient = Context.getPatientService().getPatient(id);
			JsonArray orderJsonArray = new JsonArray();
			if (patient != null) {
				List<LabTest> testList = Context.getService(CommonLabTestService.class).getLabTests(patient, false);
				if (testList != null && !testList.isEmpty()) {
					for (LabTest labTest : testList) {
						JsonObject childJsonObject = new JsonObject();
						childJsonObject.addProperty(Consts.ID, labTest.getTestOrderId());
						childJsonObject.addProperty(Consts.REQUIRED_SPECIMEN,
						    labTest.getLabTestType().getRequiresSpecimen());
						childJsonObject.addProperty(Consts.TEST_TYPE_NAME, labTest.getLabTestType().getName());
						childJsonObject.addProperty(Consts.LAB_REFERENCE_NUMBER, labTest.getLabReferenceNumber());
						childJsonObject.addProperty(Consts.TEST_GROUP, labTest.getLabTestType().getTestGroup().name());
						childJsonObject.addProperty(Consts.DATE_CREATED, labTest.getDateCreated().toString());
						childJsonObject.addProperty(Consts.CREATED_BY, labTest.getCreator().getUsername());
						childJsonObject.addProperty(Consts.ENCOUNTER_TYPE,
						    labTest.getOrder().getEncounter().getEncounterType().getName().toString());
						childJsonObject.addProperty(Consts.CHANGED_BY,
						    (labTest.getChangedBy() == null) ? "" : labTest.getChangedBy().getName());
						childJsonObject.addProperty(Consts.UUID, labTest.getUuid());
						List<LabTestAttribute> labTestAttribute = Context.getService(CommonLabTestService.class)
						        .getLabTestAttributes(labTest.getTestOrderId());

						if (labTestAttribute != null && labTestAttribute.size() > 0) {
							boolean isVoided = false;
							LabTestAttribute labTestAttributeCurrentVal = null;
							for (int i = 0; i < labTestAttribute.size(); i++) {
								if (!labTestAttribute.get(i).getVoided()) {
									labTestAttributeCurrentVal = labTestAttribute.get(i);
									isVoided = true;
									break;
								}
							}
							if (isVoided && labTestAttributeCurrentVal != null) {
								childJsonObject.addProperty(Consts.RESULT_FILLED, Boolean.TRUE);
								childJsonObject.addProperty(Consts.RESULT_DATE,
								    labTestAttributeCurrentVal.getDateCreated().toString());
							} else {
								childJsonObject.addProperty(Consts.RESULT_FILLED, Boolean.FALSE);
								childJsonObject.addProperty(Consts.RESULT_DATE, "");
							}
						} else {
							childJsonObject.addProperty(Consts.RESULT_FILLED, Boolean.FALSE);
							childJsonObject.addProperty(Consts.RESULT_DATE, "");
						}
						orderJsonArray.add(childJsonObject);
					}
				}
				model.put(Consts.TEST_ORDER, orderJsonArray);
			}
		}

	}

}
