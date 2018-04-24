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
 * @author owais.hussain@ihsinformatics.com
 */
public class LabTestAttributeType extends BaseAttributeType<LabTest> implements AttributeType<LabTest> {
	
	private Integer labTestAttributeTypeId;
	
	private Integer sortWeight;
	
	@Override
	public Integer getId() {
		return labTestAttributeTypeId;
	}
	
	@Override
	public void setId(Integer id) {
		labTestAttributeTypeId = id;
	}
	
	public Integer getSortWeight() {
		return sortWeight;
	}
	
	public void setSortWeight(Integer sortWeight) {
		this.sortWeight = sortWeight;
	}
}
