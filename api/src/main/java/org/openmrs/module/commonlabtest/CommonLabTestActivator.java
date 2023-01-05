/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.commonlabtest;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.OpenmrsUtil;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class CommonLabTestActivator extends BaseModuleActivator {

	private Log log = LogFactory.getLog(this.getClass());

	public static final String SPECIMEN_TYPE_CONCEPT_UUID = "commonlabtest.specimenTypeConceptUuid";

	public static final String SPECIMEN_SITE_CONCEPT_UUID = "commonlabtest.specimenSiteConceptUuid";

	public static final String TEST_UNITS_CONCEPT_UUID = "commonlabtest.testunitsConceptUuid";

	public static final String UPLOAD_FILE_DIRECTORY = "commonlabtest.fileDirectory";

	public static final String UPLOAD_FILE_EXTENSIONS = "commonlabtest.fileExtensions";

	public static final String FILE_EXTENSIONS_NAMES = ".bmp ,.jpg ,.jpeg,.jfif,.GIF,.png,.bat,.BPG,.FLV,.AVI,.MOV,.M4P,.MPG,.WMV,.3gp,.RM,.SWF,.3GP,.ACT,.AIFF,.MP3,.WAV,.OGG,.FLAC,.AU,.RAW,.docx,.docm,.dotx,.docb,.dotm,.pdf";

	public static final String LAB_ORDER_TYPE_UUID = "commonlabtest.labOrderTypeUuid";

	ConceptService conceptService;

	File dir = OpenmrsUtil.getDirectoryInApplicationDataDirectory(
	    Context.getAdministrationService().getGlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_COMPLEX_OBS_DIR));

	String path = dir.getPath() + "/commonLabTestFiles";

	/**
	 * @see #started()
	 */
	public void started() {
		log.info("Started Common Lab Test");
		contextRefreshed();
	}

	private void setGlobalProperty(AdministrationService service, String prop, String val, String desc) {

		conceptService = Context.getConceptService();
		File f = new File(path);
		if (!f.exists()) {
			try {
				f.mkdirs();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		AdministrationService administrationService = Context.getAdministrationService();
		setGlobalProperty(administrationService, UPLOAD_FILE_DIRECTORY, path);
		setGlobalProperty(administrationService, SPECIMEN_TYPE_CONCEPT_UUID, "162476AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		setGlobalProperty(administrationService, SPECIMEN_SITE_CONCEPT_UUID, "159959AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		setGlobalProperty(administrationService, TEST_UNITS_CONCEPT_UUID, "5db4f53e-6218-4ae0-ae4e-5e0343b5d301");
		setGlobalProperty(administrationService, UPLOAD_FILE_EXTENSIONS, FILE_EXTENSIONS_NAMES);
	}

	private static void setGlobalProperty(AdministrationService service, String prop, String val) {
		GlobalProperty gp = service.getGlobalPropertyObject(prop);
		if (gp == null) {
			service.saveGlobalProperty(new GlobalProperty(prop, val));
		} else if (StringUtils.isEmpty(gp.getPropertyValue())) {
			gp.setPropertyValue(val);
			service.saveGlobalProperty(gp);
		}
	}

	/**
	 * @see #shutdown()
	 */
	public void shutdown() {
		log.info("Shutdown Common Lab Test");
	}

	public void contextRefreshed() {
		log.info("========================== Common Lab Test Lab contextRefreshed called ======");
		conceptService = Context.getConceptService();

		File path = new File(dir.getPath() + "/commonLabTestFiles");

		if (!(path.exists() && path.isDirectory())) {
			try {
				path.mkdir();
			}
			catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		AdministrationService administrationService = Context.getAdministrationService();
		setGlobalProperty(administrationService, UPLOAD_FILE_DIRECTORY, path.toString());
		setGlobalProperty(administrationService, SPECIMEN_TYPE_CONCEPT_UUID, "162476AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
		    "The UUID of a concept representing a group or set of different types of specimen, e.g. Saliva, Blood, Pus, etc.");
		setGlobalProperty(administrationService, SPECIMEN_SITE_CONCEPT_UUID, "159959AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
		    "The UUID of a concept representing a group or set of anatomical source site from where the specimen is obtained, e.g. Bone, Tissue, etc.");
		setGlobalProperty(administrationService, TEST_UNITS_CONCEPT_UUID, "162384AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
		    "The UUID of a concept representing a group or set of various measurement units (also used to measure dosage quantity).");
		setGlobalProperty(administrationService, UPLOAD_FILE_EXTENSIONS, FILE_EXTENSIONS_NAMES);
		setGlobalProperty(administrationService, LAB_ORDER_TYPE_UUID, "33ccfcc6-0370-102d-b0e3-001ec94a0cc1",
		    "The UUID of the Order type representing a Lab Test Order.");
	}
}
