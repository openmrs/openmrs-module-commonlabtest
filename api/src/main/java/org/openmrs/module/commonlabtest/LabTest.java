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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openmrs.Attributable;
import org.openmrs.BaseCustomizableData;
import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;

/**
 * This entity represents main Lab test, which manages test order. A Lab Test has a LabTestType, has
 * a reference in LabTestSample object and defines its results as a set of attributes of type
 * LabTestAttribute. This class extends BaseCustomizableData to inherit the properties of Attributes
 * 
 * @author owais.hussain@ihsinformatics.com
 */
public class LabTest extends BaseCustomizableData<LabTestAttribute> implements java.io.Serializable, Attributable<LabTest> {

	private static final long serialVersionUID = 1L;

	private Integer testOrderId;

	private Order order;

	private LabTestType labTestType;

	private String labReferenceNumber;

	private String labInstructions;

	private String filePath;

	private String resultComments;

	private transient Set<LabTestSample> labTestSamples = new HashSet<LabTestSample>(0);

	/**
	 * Default constructor
	 */
	public LabTest() {
	}

	/**
	 * @param order since {@link LabTest} has one-to-one identifying relationship with {@link Order}
	 *            class
	 */
	public LabTest(Order order) {
		setTestOrderId(order.getId());
		setOrder(order);
	}

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
		setTestOrderId(id);
		order.setId(getTestOrderId());
	}

	public String getResultComments() {
		return resultComments;
	}

	public void setResultComments(String resultComments) {
		this.resultComments = resultComments;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Finds a list of LabTest objects from Lab reference number. Also @see
	 * org.openmrs.Attributable#findPossibleValues(java.lang.String)
	 * 
	 * @param referenceNumber the reference number
	 * @return {@link LabTest} object(s)
	 * @deprecated Data provided by this method can be better achieved from appropriate service at point
	 *             of use.
	 */
	@Override
	@Deprecated
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
	 * Always returns an empty list because returning complete list of all LabTests will be burdensome.
	 * Also @see org.openmrs.Attributable#getPossibleValues()
	 * 
	 * @return {@link LabTest} object(s)
	 * @deprecated Data provided by this method can be better achieved from appropriate service at point
	 *             of use.
	 */
	@Override
	@Deprecated
	public List<LabTest> getPossibleValues() {
		return Collections.emptyList();
	}

	/**
	 * Searches LabTest object by given UUID and returns the matching object. If the object is not
	 * found, a new object is returned. Also @see org.openmrs.Attributable#hydrate(java.lang.String)
	 * 
	 * @param uuid the unique Id
	 * @return {@link LabTest} object
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
	 * Searches LabTest object by given Id and returns the matching object. If the object is not found,
	 * a new object is returned.
	 * 
	 * @param labTestId the Id
	 * @return {@link LabTest} object
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

	/**
	 * Returns the first non-voided commonlabtest attribute matching a commonlabtest attribute type.
	 * <br>
	 * <br>
	 * Returns null if this commonlabtest has no non-voided {@link LabTestAttribute} with the given
	 * {@link LabTestAttributeType}, the given {@link LabTestAttributeType} is null, or this
	 * commonlabtest has no attributes.
	 * 
	 * @param lat the LabTestAttributeType to look for {@link LabTestAttributeType#equals(Object)}
	 * @return LabTestAttribute that matches the given type
	 */
	public LabTestAttribute getAttribute(LabTestAttributeType lat) {
		if (lat != null) {
			for (LabTestAttribute attribute : getAttributes()) {
				if (lat.equals(attribute.getAttributeType()) && !attribute.getVoided()) {
					return attribute;
				}
			}
		}
		return null;
	}

	public Integer getTestOrderId() {
		return testOrderId;
	}

	public void setTestOrderId(Integer testOrderId) {
		this.testOrderId = testOrderId;
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

	public Set<LabTestSample> getLabTestSamples() {
		return labTestSamples;
	}

	public void setLabTestSamples(Set<LabTestSample> labTestSamples) {
		this.labTestSamples = labTestSamples;
	}

	/**
	 * Add a {@link LabTestSample} to existing set. A duplicate object will be skipped without any
	 * exception
	 * 
	 * @param labTestSample the {@link LabTestSample} object
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
			labTestSamples = new HashSet<LabTestSample>();
		}
		labTestSamples.add(labTestSample);
	}

	public void removeLabTestSample(LabTestSample labTestSample) {
		if (labTestSamples != null && labTestSample != null) {
			labTestSamples.remove(labTestSample);
		}
	}

	public String getLabInstructions() {
		return labInstructions;
	}

	public void setLabInstructions(String labInstructions) {
		this.labInstructions = labInstructions;
	}

	public void removeLabTestAttribute(LabTestAttribute labTestAttribute) {
		if (getAttributes() != null && labTestAttribute != null) {
			getAttributes().remove(labTestAttribute);
		}
	}

	/**
	 * @param status the {@link LabTestSampleStatus} object
	 * @return the {@link LabTestSample} object
	 */
	public LabTestSample getLabTestSample(LabTestSampleStatus status) {
		for (LabTestSample labTestSample : labTestSamples) {
			if (labTestSample.getStatus().equals(status) && !labTestSample.getVoided()) {
				return labTestSample;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return testOrderId + ", " + labTestType + ", " + labReferenceNumber;
	}
}
