package org.openmrs.module.commonlabtest.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.web.controller.PortletController;
import org.springframework.beans.factory.annotation.Autowired;
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
		
		String patientId = request.getParameter("patientId");
		if (patientId != null) {
			int id = Integer.parseInt(patientId);
			Patient patient = Context.getPatientService().getPatient(id);
			JsonArray orderJsonArray = new JsonArray();
			if (patient != null) {
				List<LabTest> testList = Context.getService(CommonLabTestService.class).getLabTests(patient, false);
				if (testList != null && !testList.isEmpty()) {
					for (LabTest labTest : testList) {
						JsonObject childJsonObject = new JsonObject();
						childJsonObject.addProperty("id", labTest.getTestOrderId());
						childJsonObject.addProperty("requiredSpecimen", labTest.getLabTestType().getRequiresSpecimen());
						childJsonObject.addProperty("testTypeName", labTest.getLabTestType().getName());
						childJsonObject.addProperty("labReferenceNumber", labTest.getLabReferenceNumber());
						childJsonObject.addProperty("testGroup", labTest.getLabTestType().getTestGroup().name());
						childJsonObject.addProperty("dateCreated", labTest.getDateCreated().toString());
						childJsonObject.addProperty("createdBy", labTest.getCreator().getUsername());
						childJsonObject.addProperty("encounterType", labTest.getOrder().getEncounter().getEncounterType()
						        .getName().toString());
						childJsonObject.addProperty("changedBy", (labTest.getChangedBy() == null) ? "" : labTest
						        .getChangedBy().getName());
						childJsonObject.addProperty("uuid", labTest.getUuid());
						List<LabTestAttribute> labTestAttribute = Context.getService(CommonLabTestService.class)
						        .getLabTestAttributes(labTest.getTestOrderId());
						if (labTestAttribute != null && labTestAttribute.size() > 0) {
							childJsonObject.addProperty("resultFilled", Boolean.TRUE);
							childJsonObject.addProperty("resultDate", labTestAttribute.get(0).getDateCreated().toString());
						} else {
							childJsonObject.addProperty("resultFilled", Boolean.FALSE);
							childJsonObject.addProperty("resultDate", "");
						}
						orderJsonArray.add(childJsonObject);
					}
				}
				System.out.println("Totatl Resullt SET ::" + orderJsonArray);
				model.put("testOrder", orderJsonArray);
				//generalObj.add(testList);
				//model.put("testOrder", testList);
			}
		}
		
	}
	
}
