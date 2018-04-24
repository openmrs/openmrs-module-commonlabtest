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

import java.util.List;

import org.openmrs.Attributable;
import org.openmrs.BaseCustomizableData;
import org.openmrs.Order;

/**
 * @author owais.hussain@ihsinformatics.com
 */
public class LabTest extends BaseCustomizableData<LabTestAttribute> implements java.io.Serializable, Attributable<LabTest> {
	
	private static final long serialVersionUID = 2561859108258402721L;
	
	private Order order;
	
	private LabTestType labTestType;
	
	private String labReferenceNumber;
	
	@Override
	public Integer getId() {
		return order.getId();
	}
	
	@Override
	public void setId(Integer id) {
		order.setId(id);
	}
	
	@Override
	public List<LabTest> findPossibleValues(String str) {
		return null;
	}
	
	@Override
	public String getDisplayString() {
		return null;
	}
	
	@Override
	public List<LabTest> getPossibleValues() {
		return null;
	}
	
	@Override
	public LabTest hydrate(String str) {
		return null;
	}
	
	@Override
	public String serialize() {
		return null;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public LabTestType getLabTestType() {
		return labTestType;
	}
	
	public void setLabTestType(LabTestType labTestType) {
		this.labTestType = labTestType;
	}
	
	public String getLabReferenceNumber() {
		return labReferenceNumber;
	}
	
	public void setLabReferenceNumber(String labReferenceNumber) {
		this.labReferenceNumber = labReferenceNumber;
	}
}
