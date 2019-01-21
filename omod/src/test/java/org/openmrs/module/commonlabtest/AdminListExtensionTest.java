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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.openmrs.module.Extension;
import org.openmrs.module.commonlabtest.extension.html.AdminList;

/**
 * This test validates the AdminList extension class
 */
public class AdminListExtensionTest {

	/**
	 * Get the links for the extension class
	 */
	@Test
	public void testValidatesLinks() {
		AdminList ext = new AdminList();
		Map<String, String> links = ext.getLinks();
		assertNotNull(links);
		assertFalse(links.isEmpty());
	}

	/**
	 * Check the media type of this extension class
	 */
	@Test
	public void testMediaTypeIsHtml() {
		AdminList ext = new AdminList();
		assertEquals(ext.getMediaType(), Extension.MEDIA_TYPE.html);
	}

}
