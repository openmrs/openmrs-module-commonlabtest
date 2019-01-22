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
package org.openmrs.module.commonlabtest;

import org.openmrs.attribute.Attribute;
import org.openmrs.attribute.BaseAttribute;

/**
 * This class represents attribtues of a LabTest object; it extends BaseAttribute class and
 * implements Attributable interface. Results of a lab test should be stored as attributes.
 * 
 * @author owais.hussain@ihsinformatics.com
 */
public class LabTestAttribute extends BaseAttribute<LabTestAttributeType, LabTest> implements Attribute<LabTestAttributeType, LabTest> {

	private static final long serialVersionUID = 22986710762598701L;

	private Integer labTestAttributeId;

	/**
	 * Default constructor
	 */
	public LabTestAttribute() {
	}

	public Integer getLabTestAttributeId() {
		return labTestAttributeId;
	}

	public void setLabTestAttributeId(Integer labTestAttributeId) {
		this.labTestAttributeId = labTestAttributeId;
	}

	/**
	 * @return {@link LabTest} object
	 */
	public LabTest getLabTest() {
		return getOwner();
	}

	/**
	 * @param labTest the {@link LabTest} object
	 */
	public void setLabTest(LabTest labTest) {
		setOwner(labTest);
	}

	@Override
	public Integer getId() {
		return getLabTestAttributeId();
	}

	@Override
	public void setId(Integer id) {
		setLabTestAttributeId(id);
	}

	@Override
	public String toString() {
		return labTestAttributeId + ", " + getOwner().getDisplayString() + ", " + getAttributeType() + ", "
		        + getValueReference();
	}
}
