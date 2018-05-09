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
 * This class represents attribtues of a LabTest object; it extends BaseAttribute class and
 * implements Attributable interface. Results of a lab test should be stored as attributes.
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
	
	/* These three objects are only to map base class attributes for hibernate (this approach avoids adding hibernate mapping XML file). 
	Base class is not annotated, neither is defined in API's mapped files.
	*/
	@Column(name = "test_order_id")
	private Integer testOrderId;
	
	@Column(name = "attribute_type_id")
	private Integer attributeTypeId;
	
	@Column(name = "value_reference")
	private String valueReference;
	
	/**
	 * Default constructor
	 */
	public LabTestAttribute() {
	}
	
	/**
	 * @param id
	 */
	public LabTestAttribute(Integer id) {
		setId(id);
	}
	
	@Override
	public Integer getId() {
		return labTestAttributeId;
	}
	
	@Override
	public void setId(Integer id) {
		this.labTestAttributeId = id;
	}
	
	/**
	 * @return the testOrderId
	 */
	public Integer getTestOrderId() {
		return getLabTest().getId();
	}
	
	/**
	 * @param testOrderId the testOrderId to set
	 * @deprecated use <code>setLabTest</code> instead
	 */
	@Deprecated
	public void setTestOrderId(Integer testOrderId) {
		getLabTest().setId(testOrderId);
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
	
	/**
	 * @return the attributeTypeId
	 */
	public Integer getAttributeTypeId() {
		return attributeTypeId;
	}
	
	/**
	 * @param attributeTypeId the attributeTypeId to set
	 * @deprecated use <code>setAttributeType</code> instead
	 */
	@Deprecated
	public void setAttributeTypeId(Integer attributeTypeId) {
		getAttributeType().setId(attributeTypeId);
	}
	
	/**
	 * @return the valueReference
	 */
	@Override
	public String getValueReference() {
		return valueReference;
	}
	
	/**
	 * @param valueReference the valueReference to set
	 */
	public void setValueReference(String valueReference) {
		this.valueReference = valueReference;
		super.setValueReferenceInternal(getValueReference());
	}
}
