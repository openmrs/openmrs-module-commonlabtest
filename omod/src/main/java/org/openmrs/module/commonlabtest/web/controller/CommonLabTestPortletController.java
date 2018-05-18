/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.commonlabtest.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.web.controller.PortletController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author owais.hussain@ihsinformatics.com
 */
@Controller
@RequestMapping("**/patientLabTests.portlet")
public class CommonLabTestPortletController extends PortletController {
	
	private static final Log log = LogFactory.getLog(CommonLabTestPortletController.class);
	
	@Override
	protected void populateModel(HttpServletRequest request, Map<String, Object> model) {
		/*
		// order.durationUnitsConceptUuid > units
		List<Concept> durationUnits = Context.getOrderService().getDurationUnits();
		log.info("Duration unit concepts are " + durationUnits);
		model.put("durationUnits", durationUnits);
		
		// frequencies
		
		// order.drugRoutesConceptUuid > routes
		List<Concept> routes = Context.getOrderService().getDrugRoutes();
		log.info("Routes concepts are " + routes);
		model.put("routes", routes);
		
		// order.drugDosingUnitsConceptUuid > 162384 > dose units
		List<Concept> doseUnits = Context.getOrderService().getDrugDosingUnits();
		log.info("Dose unit concepts are " + doseUnits);
		model.put("doseUnits", doseUnits);
		
		// MEDICATION FREQUENCY > 160855 > 160855AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
		String frequencyUuid = Context.getAdministrationService()
		        .getGlobalProperty("medication.medicationFrequenciesConceptUuid");
		Concept frequencySet = Context.getConceptService().getConceptByUuid(frequencyUuid);
		if (frequencySet != null && frequencySet.getSetMembers().size() > 0) {
			List<Concept> frequencies = frequencySet.getSetMembers();
			log.info("Frequency concepts are " + frequencies);
			model.put("frequencies", frequencies);
		}
		
		String orderReasonUuid = Context.getAdministrationService()
		        .getGlobalProperty(MedicationLogActivator.MEDICATION_ORDER_REASON_CONCEPT_UUID);
		
		log.info("===================== Order reason UUid is " + orderReasonUuid);
		log.info("==============================================================");
		
		if (orderReasonUuid != null && !orderReasonUuid.isEmpty()) {
			Concept orderReason = Context.getConceptService().getConceptByUuid(orderReasonUuid);
			if (orderReason != null && orderReason.getAnswers(false).size() > 0) {
				Collection<ConceptAnswer> reasonCollection = orderReason.getAnswers(false);
				ArrayList<Concept> reasons = new ArrayList<Concept>();
				
				for (ConceptAnswer reason : reasonCollection) {
					reasons.add(reason.getAnswerConcept());
					
				}
				log.info("============ Order reason concepts are " + reasons);
				model.put("orderReasons", reasons);
			}
		}
		
		// reading drug set classes global property (it indicates the types of
		// concept sets required e.g LabSet, ConvSet). Get the comma-separated
		// values and fetch these concepts by class and then generate the final
		// list of the drug set concepts
		
		String drugClasses = Context.getAdministrationService()
		        .getGlobalProperty(MedicationLogActivator.MEDICATION_DRUG_SETS_PROPERTY);
		
		List<String> classes;
		List<Concept> drugSets = new ArrayList<Concept>();
		
		// search drug sets based on drug classes specified in advance settings
		if (drugClasses != null && !drugClasses.isEmpty()) {
			if (drugClasses.contains(",")) {
				classes = Arrays.asList(drugClasses.split(","));
			} else {
				classes = new ArrayList<String>();
				classes.add(drugClasses);
			}
			drugSets = getDrugSets(classes);
		}
		// else search concept sets for all Set classes like ConvSet, MedSet
		// etc.
		else {
			drugClasses = "LabSet, MedSet, ConvSet";
			classes = Arrays.asList(drugClasses.split(",").toString().trim());
			drugSets = getDrugSets(classes);
		}
		
		model.put("drugSets", drugSets);
		DrugOrder drugOrder = new DrugOrder();
		model.put("drugOrder", drugOrder);
		
		// put order sets in model
		*/
	}
	
}
