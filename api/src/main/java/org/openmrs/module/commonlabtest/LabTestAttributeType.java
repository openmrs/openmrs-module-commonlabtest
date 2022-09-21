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

import org.openmrs.attribute.AttributeType;
import org.openmrs.attribute.BaseAttributeType;

/**
 * This entity represents attribute types associated with each type of test. This class extends
 * BaseAttributeType of type LabTest.
 * 
 * @author owais.hussain@ihsinformatics.com
 */
public class LabTestAttributeType extends BaseAttributeType<LabTest> implements AttributeType<LabTest> {

	private Integer labTestAttributeTypeId;

	private LabTestType labTestType;

	private Double sortWeight;

	private String hint;

	private String multisetName;

	private String groupName;

	public LabTestAttributeType() {
	}

	public LabTestAttributeType(Integer id) {
		setId(id);
	}

	public Integer getLabTestAttributeTypeId() {
		return this.labTestAttributeTypeId;
	}

	public void setLabTestAttributeTypeId(Integer id) {
		this.labTestAttributeTypeId = id;
	}

	@Override
	public Integer getId() {
		return labTestAttributeTypeId;
	}

	@Override
	public void setId(Integer id) {
		labTestAttributeTypeId = id;
	}

	public LabTestType getLabTestType() {
		return labTestType;
	}

	public void setLabTestType(LabTestType labTestType) {
		this.labTestType = labTestType;
	}

	public Double getSortWeight() {
		return sortWeight;
	}

	public void setSortWeight(Double sortWeight) {
		this.sortWeight = sortWeight;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getMultisetName() {
		return multisetName;
	}

	public void setMultisetName(String multisetName) {
		this.multisetName = multisetName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return labTestAttributeTypeId + ", " + labTestType + ", " + groupName + ", " + getName();
	}
}
