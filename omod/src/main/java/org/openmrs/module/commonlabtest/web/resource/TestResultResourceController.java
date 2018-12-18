package org.openmrs.module.commonlabtest.web.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.openmrs.GlobalProperty;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@RequestMapping("/data/rest/commonlab/testresult.json")
public class TestResultResourceController {
	
	/*public static final String DATE_FORMAT = "YYYY-MM-DD[T]HH:mm:ss.SSSZZ";
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
	
	@Autowired
	CommonLabTestService commonLabTestService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String getResult(@RequestParam Integer id) {
		List<LabTestAttribute> attributes = commonLabTestService.getLabTestAttributes(id);
		LabTest labtest = commonLabTestService.getLabTest(id);
		List<LabTestSample> samples = commonLabTestService.getLabTestSamples(labtest, true);
		JsonObject result = new JsonObject();
		result.addProperty("testOrderId", labtest.getTestOrderId());
		result.addProperty("labReferenceNumber", labtest.getLabReferenceNumber());
		result.addProperty("labInstructions", labtest.getLabInstructions());
		result.addProperty("resultComments", labtest.getResultComments());
		result.addProperty("careSetting", labtest.getOrder().getCareSetting().getName());
		result.addProperty("encounterId", labtest.getOrder().getEncounter().getUuid());
		result.addProperty("orderId", labtest.getOrder().getUuid());
		result.addProperty("action", labtest.getOrder().getAction().name());
		JsonArray attributeJsonArray = new JsonArray();
		for (LabTestAttribute lta : attributes) {
			JsonObject att = new JsonObject();
			
			att.addProperty("attributeId", lta.getLabTestAttributeId());
			att.addProperty("uuid", lta.getUuid());
			att.addProperty("valueReference", lta.getValueReference());
			att.addProperty("voided", lta.getVoided());
			// att.addProperty("voidReason", lta.getVoidReason());
			// att.addProperty("dateChanged", lta.getDateChanged().toString());
			// att.addProperty("dateCreated", lta.getDateCreated().toString());
			
			attributeJsonArray.add(att);
			
		}
		
		JsonArray samplesJsonArray = new JsonArray();
		for (LabTestSample sample : samples) {
			JsonObject sam = new JsonObject();
			
			sam.addProperty("attributeId", sample.getLabTestSampleId());
			sam.addProperty("uuid", sample.getComments());
			sam.addProperty("units", sample.getUnits());
			sam.addProperty("sampleIdentifier", sample.getSampleIdentifier());
			sam.addProperty("quantity", sample.getQuantity());
			sam.addProperty("expirable", sample.getExpirable());
			// sam.addProperty("processedDate", sample.getProcessedDate().toString());
			sam.addProperty("specimentSite", sample.getSpecimenSite().getDisplayString());
			sam.addProperty("specimenType", sample.getSpecimenType().getDisplayString());
			sam.addProperty("status", sample.getStatus().name());
			sam.addProperty("voided", sample.getVoided());
			
			samplesJsonArray.add(sam);
			
		}
		
		result.addProperty("testOrderUuid", labtest.getUuid());
		result.addProperty("action", labtest.getOrder().getAction().name());
		result.add("attributes", attributeJsonArray);
		result.add("samples", samplesJsonArray);
		
		return result.toString();
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String setResult(@RequestParam String json) throws ParseException {
		
		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(json);
		
		JsonObject object = jsonElement.getAsJsonObject();
		String labTestUuid = object.get("labtest").toString();
		LabTest labTest = commonLabTestService.getLabTestByUuid(labTestUuid);
		JsonArray attributeJsonArray = object.getAsJsonArray("attributes");
		JsonArray samplesJsonArray = object.getAsJsonArray("samples");
		List<LabTestSample> sampleList = new ArrayList<LabTestSample>();
		List<LabTestAttribute> attributeList = new ArrayList<LabTestAttribute>();
		
		for (int i = 0; i < samplesJsonArray.size(); i++) {
			LabTestSample sample = new LabTestSample();
			JsonObject sampleJsonObject = (JsonObject) samplesJsonArray.get(i);
			
			sample.setLabTest(labTest);
			sample.setUnits(sampleJsonObject.get("units") != null ? sampleJsonObject.get("units").getAsString() : null);
			sample.setStatus(LabTestSampleStatus.valueOf(sampleJsonObject.get("status").getAsString()));
			
			sample.setSpecimenType(sampleJsonObject.get("specimenType") != null ? Context.getConceptService()
			        .getConceptByUuid(sampleJsonObject.get("specimenType").getAsString()) : null);
			sample.setSampleIdentifier(sampleJsonObject.get("sampleIdentifier") != null ? sampleJsonObject.get(
			    "sampleIdentifier").getAsString() : null);
			sample.setCollectionDate(sampleJsonObject.get("collectionDate") != null ? simpleDateFormat
			        .parse(sampleJsonObject.get("collectionDate").getAsString()) : null);
			sample.setQuantity(sampleJsonObject.get("quantity") != null ? sampleJsonObject.get("quantity").getAsDouble()
			        : null);
			sample.setComments(sampleJsonObject.get("comments") != null ? sampleJsonObject.get("comments").getAsString()
			        : null);
			sample.setProcessedDate(sampleJsonObject.get("processedDate") != null ? simpleDateFormat.parse(sampleJsonObject
			        .get("processedDate").getAsString()) : null);
			sample.setDateChanged(sampleJsonObject.get("dateChanged") != null ? simpleDateFormat.parse(sampleJsonObject.get(
			    "dateChanged").getAsString()) : null);
			sample.setDateCreated(sampleJsonObject.get("dateCreated") != null ? simpleDateFormat.parse(sampleJsonObject.get(
			    "dateCreated").getAsString()) : null);
			sample.setDateVoided(sampleJsonObject.get("dateVoided") != null ? simpleDateFormat.parse(sampleJsonObject.get(
			    "dateVoided").getAsString()) : null);
			sample.setVoided(sampleJsonObject.get("voided") != null ? sampleJsonObject.get("voided").getAsBoolean() : null);
			sample.setVoided(sampleJsonObject.get("voided") != null ? sampleJsonObject.get("voided").getAsBoolean() : null);
			sample.setVoidReason(sampleJsonObject.get("voidReason") != null ? sampleJsonObject.get("voidReason")
			        .getAsString() : null);
			if (sampleJsonObject.get("changedBy") != null) {
				User user = new User();
				user.setUuid(sampleJsonObject.get("changedBy").getAsString());
				sample.setChangedBy(user);
				
			}
			
			if (sampleJsonObject.get("voidedBy") != null) {
				User user = new User();
				user.setUuid(sampleJsonObject.get("voidedBy").getAsString());
				sample.setVoidedBy(user);
				
			}
			
			if (sampleJsonObject.get("creator") != null) {
				User user = new User();
				user.setUuid(sampleJsonObject.get("creator").getAsString());
				sample.setCreator(user);
				
			}
			sampleList.add(sample);
		}
		
		for (int i = 0; i < attributeJsonArray.size(); i++) {
			LabTestAttribute attribute = new LabTestAttribute();
			JsonObject attributeJsonObject = (JsonObject) attributeJsonArray.get(i);
			
			attribute.setLabTest(labTest);
			
			//	attribute.setAttributeType(commonLabTestService.getLabTestAttributeTypeByUuid(attributeJsonObject.get("")));
			attributeList.add(attribute);
		}
		
		return null;
		
	}*/
}
