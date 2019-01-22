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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

	ConceptService conceptService;

	File dir = OpenmrsUtil.getDirectoryInApplicationDataDirectory(
	    Context.getAdministrationService().getGlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_COMPLEX_OBS_DIR));

	Path path = Paths.get(dir.getPath() + "/commonLabTestFiles");

	/**
	 * @see #started()
	 */
	public void started() {
		log.info("Started Common Lab Test");

		conceptService = Context.getConceptService();
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		AdministrationService administrationService = Context.getAdministrationService();
		setGlobalProperty(administrationService, UPLOAD_FILE_DIRECTORY, path.toString());
		setGlobalProperty(administrationService, SPECIMEN_TYPE_CONCEPT_UUID, "162476AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		setGlobalProperty(administrationService, SPECIMEN_SITE_CONCEPT_UUID, "159959AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		setGlobalProperty(administrationService, TEST_UNITS_CONCEPT_UUID, "5db4f53e-6218-4ae0-ae4e-5e0343b5d301");
		setGlobalProperty(administrationService, UPLOAD_FILE_EXTENSIONS, FILE_EXTENSIONS_NAMES);

	}

	private void setGlobalProperty(AdministrationService service, String prop, String val) {
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

		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		AdministrationService administrationService = Context.getAdministrationService();
		setGlobalProperty(administrationService, UPLOAD_FILE_DIRECTORY, path.toString());
		setGlobalProperty(administrationService, SPECIMEN_TYPE_CONCEPT_UUID, "162476AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		setGlobalProperty(administrationService, SPECIMEN_SITE_CONCEPT_UUID, "159959AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		setGlobalProperty(administrationService, TEST_UNITS_CONCEPT_UUID, "5db4f53e-6218-4ae0-ae4e-5e0343b5d301");
		setGlobalProperty(administrationService, UPLOAD_FILE_EXTENSIONS, FILE_EXTENSIONS_NAMES);
	}

}
