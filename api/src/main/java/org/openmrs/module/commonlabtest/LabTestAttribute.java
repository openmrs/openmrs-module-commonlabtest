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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.openmrs.attribute.Attribute;
import org.openmrs.attribute.BaseAttribute;

/**
 * This
 * 
 * @author owais.hussain@ihsinformatics.com
 */
@Entity(name = "commonlabtest.LabTestAttribute")
@Table(name = "commonlabtest_attribute")
public class LabTestAttribute extends BaseAttribute<LabTestAttributeType, LabTest> implements Attribute<LabTestAttributeType, LabTest> {
	
	private static final long serialVersionUID = 22986710762598701L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "test_attribute_id")
	private Integer labTestAttributeId;
	
	@Override
	public Integer getId() {
		return labTestAttributeId;
	}
	
	@Override
	public void setId(Integer id) {
		this.labTestAttributeId = id;
	}
	
	/**
	 * @return the labTest
	 */
	public LabTest getLabTest() {
		return getOwner();
	}
	
	/**
	 * @param labTest the object to set
	 */
	public void setLabTest(LabTest labTest) {
		setOwner(labTest);
	}
}
