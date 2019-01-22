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

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Provider;

/**
 * This entity represents a test sample on which certain lab test is performed.
 * A LabTestType has a property requiresSpecimen, which determines if a
 * LabTestSample object is needed in order to run the test.
 * 
 * @author owais.hussain@ihsinformatics.com
 */
@Entity(name = "commonlabtest.LabTestSample")
@Table(name = "commonlabtest_sample")
public class LabTestSample extends BaseOpenmrsData {

	/**
	 * Enumerated type to represent status of sample
	 */
	public enum LabTestSampleStatus {
		COLLECTED, // First state of sample, when received by sample collector
		ACCEPTED, // When lab accepts the sample for processing
		REJECTED, // Set when lab does not accept the sample due to any reason (contamination,
		// insufficient quantity, etc.)
		PROCESSED // After the sample has been processed by lab
	}

	private static final long serialVersionUID = 1169373793251683587L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "test_sample_id")
	private Integer labTestSampleId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "test_order_id")
	private LabTest labTest;

	@ManyToOne(optional = false)
	@JoinColumn(name = "specimen_type")
	private Concept specimenType;

	@ManyToOne
	@JoinColumn(name = "specimen_site")
	private Concept specimenSite;

	// @Temporal(TemporalType.DATE)
	@Column(name = "collection_date")
	private Date collectionDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "collector")
	private Provider collector;

	@Field
	@Column(name = "quantity")
	private Double quantity;

	@Basic
	@Column(name = "units")
	private String units;

	private transient Boolean expirable = Boolean.FALSE;

	// @Temporal(TemporalType.DATE)
	@Column(name = "expiry_date")
	private Date expiryDate;

	// @Temporal(TemporalType.DATE)
	@Column(name = "processed_date")
	private Date processedDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 50)
	private LabTestSampleStatus status = LabTestSampleStatus.COLLECTED;

	@Basic
	@Column(name = "lab_sample_identifier", length = 255)
	private String sampleIdentifier;

	@Basic
	@Column(name = "comments", length = 255)
	private String comments;

	/**
	 * Default constructor
	 */
	public LabTestSample() {
	}

	public LabTestSample(Integer id) {
		setId(id);
	}

	/**
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	@Override
	public Integer getId() {
		return labTestSampleId;
	}

	/**
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	@Override
	public void setId(Integer id) {
		this.labTestSampleId = id;
	}

	public LabTest getLabTest() {
		return labTest;
	}

	public void setLabTest(LabTest labTest) {
		this.labTest = labTest;
	}

	/**
	 * @return the {@link Concept} object which represents the specimen type
	 */
	public Concept getSpecimenType() {
		return specimenType;
	}

	/**
	 * @param specimenType
	 *            the {@link Concept} object which represents the specimen type
	 */
	public void setSpecimenType(Concept specimenType) {
		this.specimenType = specimenType;
	}

	/**
	 * @return the {@link Concept} object which represents the source of specimen
	 *         sample
	 */
	public Concept getSpecimenSite() {
		return specimenSite;
	}

	/**
	 * @param specimenSite
	 *            {@link Concept} object which represents the source of specimen
	 *            sample
	 */
	public void setSpecimenSite(Concept specimenSite) {
		this.specimenSite = specimenSite;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public Provider getCollector() {
		return collector;
	}

	public void setCollector(Provider collector) {
		this.collector = collector;
	}

	public Boolean getExpirable() {
		return expirable;
	}

	private void setExpirable(Boolean expirable) {
		this.expirable = expirable;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
		setExpirable(getExpiryDate() != null);
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Date getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processingDate) {
		this.processedDate = processingDate;
	}

	public LabTestSampleStatus getStatus() {
		return status;
	}

	public void setStatus(LabTestSampleStatus status) {
		this.status = status;
	}

	public String getSampleIdentifier() {
		return sampleIdentifier;
	}

	public void setSampleIdentifier(String sampleIdentifier) {
		this.sampleIdentifier = sampleIdentifier;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getLabTestSampleId() {
		return labTestSampleId;
	}

	public void setLabTestSampleId(Integer id) {
		this.labTestSampleId = id;
	}

	@Override
	public String toString() {
		return labTestSampleId + ", " + labTest + ", " + specimenType + ", " + status + ", " + sampleIdentifier;
	}
}
