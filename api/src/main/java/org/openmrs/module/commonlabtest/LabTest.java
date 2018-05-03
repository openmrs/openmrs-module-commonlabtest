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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.openmrs.Attributable;
import org.openmrs.BaseCustomizableData;
import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;

/**
 * This entity represents main Lab test, which manages test order. A Lab Test has a LabTestType, has
 * a reference in LabTestSample object and defines its results as a set of attributes of type
 * LabTestAttribute. This class extends BaseCustomizableData to inherit the properties of Attributes
 * 
 * @author owais.hussain@ihsinformatics.com
 */
@Entity(name = "commonlabtest.LabTest")
@Table(name = "commonlabtest_test")
public class LabTest extends BaseCustomizableData<LabTestAttribute> implements java.io.Serializable, Attributable<LabTest> {
	
	private static final long serialVersionUID = 2561859108258402721L;
	
	@OneToOne(optional = false)
	@JoinColumn(name = "test_order_id")
	private Order order;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "test_type_id")
	private LabTestType labTestType;
	
	@Field
	@Column(name = "lab_reference_number", length = 255)
	private String labReferenceNumber;
	
	@ContainedIn
	private Set<LabTestSample> labTestSamples = new HashSet<>(0);
	
	/**
	 * @see org.openmrs.Order#getOrderId()
	 */
	@Override
	public Integer getId() {
		return order.getId();
	}
	
	/**
	 * @see org.openmrs.Order#setOrderId(java.lang.Integer)
	 */
	@Override
	public void setId(Integer id) {
		order.setId(id);
	}
	
	/**
	 * Finds a list of LabTest objects from Lab reference number. Also @see
	 * org.openmrs.Attributable#findPossibleValues(java.lang.String)
	 */
	@Override
	public List<LabTest> findPossibleValues(String referenceNumber) {
		try {
			return Context.getService(CommonLabTestService.class).getLabTests(referenceNumber, false);
		}
		catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public String getDisplayString() {
		return new ToStringBuilder(toString()).append("reference", getLabReferenceNumber()).build();
	}
	
	/**
	 * Always returns an empty list because returning complete list of all LabTests will be
	 * burdensome. Also @see org.openmrs.Attributable#getPossibleValues()
	 */
	@Override
	public List<LabTest> getPossibleValues() {
		return Collections.emptyList();
	}
	
	/**
	 * Searches LabTest object by given UUID and returns the matching object. If the object is not
	 * found, a new object is returned. Also @see org.openmrs.Attributable#hydrate(java.lang.String)
	 */
	@Override
	public LabTest hydrate(String uuid) {
		try {
			CommonLabTestService labTestService = Context.getService(CommonLabTestService.class);
			LabTest labTest = labTestService.getLabTestByUuid(uuid);
			if (labTest == null) {
				throw new Exception();
			}
			return labTest;
		}
		catch (Exception e) {
			return new LabTest();
		}
	}
	
	/**
	 * Searches LabTest object by given Id and returns the matching object. If the object is not
	 * found, a new object is returned.
	 */
	public LabTest hydrate(Integer labTestId) {
		try {
			CommonLabTestService labTestService = Context.getService(CommonLabTestService.class);
			LabTest labTest = labTestService.getLabTest(labTestId);
			if (labTest == null) {
				throw new Exception();
			}
			return labTest;
		}
		catch (Exception e) {
			return new LabTest();
		}
	}
	
	/**
	 * @see org.openmrs.Attributable#serialize()
	 */
	@Override
	public String serialize() {
		if (getId() != null) {
			return "" + getId();
		} else {
			return "";
		}
	}
	
	public Order getOrder() {
		return order;
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
	
	/**
	 * @return the labTestSamples
	 */
	public Set<LabTestSample> getLabTestSamples() {
		return labTestSamples;
	}
	
	/**
	 * @param labTestSamples the labTestSamples to set
	 */
	public void setLabTestSamples(Set<LabTestSample> labTestSamples) {
		this.labTestSamples = labTestSamples;
	}
	
	/**
	 * Add labTestSample to existing set. A duplicate object will be skipped without any exception
	 * 
	 * @param labTestSample
	 */
	public void addLabTestSample(LabTestSample labTestSample) {
		if (labTestSample != null) {
			// First ensure that the object passed belongs to this LabTest object
			labTestSample.setLabTest(this);
			// The collection is a set, it should reject duplicates
			if (labTestSamples.contains(labTestSample)) {
				return;
			}
		}
		if (labTestSamples == null) {
			labTestSamples = new HashSet<>();
		}
		labTestSamples.add(labTestSample);
	}
	
	/**
	 * Remove labTestSample object from the existing set
	 * 
	 * @param labTestSample
	 */
	public void removeLabTestSample(LabTestSample labTestSample) {
		if (labTestSamples != null && labTestSample != null) {
			labTestSamples.remove(labTestSample);
		}
	}
	
	/**
	 * Returns first non-voided object in labTestSamples matching the given status, or null.
	 * 
	 * @param status
	 * @return
	 */
	public LabTestSample getLabTestSample(LabTestSampleStatus status) {
		for (LabTestSample labTestSample : labTestSamples) {
			if (labTestSample.getStatus().equals(status) && !labTestSample.getVoided()) {
				return labTestSample;
			}
		}
		return null;
	}
}
