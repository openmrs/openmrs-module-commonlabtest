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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Field;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Provider;

/**
 * This entity represents a test sample on which certain lab test is performed. A LabTestType has a
 * property requiresSpecimen, which determines if a LabTestSample object is needed in order to run
 * the test.
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
		REJECTED, // Set when lab does not accept the sample due to any reason (contamination, insufficient quantity, etc.)
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
	
	@Temporal(TemporalType.DATE)
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
	
	@Column(name = "is_expirable", nullable = false)
	@Field
	private Boolean expirable = Boolean.FALSE;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "expiry_date")
	private Date expiryDate;
	
	@Temporal(TemporalType.DATE)
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
	
	/**
	 * @param id
	 */
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
	
	/**
	 * @return the labTest
	 */
	public LabTest getLabTest() {
		return labTest;
	}
	
	/**
	 * @param labTest the labTest to set
	 */
	public void setLabTest(LabTest labTest) {
		this.labTest = labTest;
	}
	
	/**
	 * Concept which represents the specimen type
	 * 
	 * @return
	 */
	public Concept getSpecimenType() {
		return specimenType;
	}
	
	/**
	 * @param specimenType
	 */
	public void setSpecimenType(Concept specimenType) {
		this.specimenType = specimenType;
	}
	
	/**
	 * Concept which represents the body Site from which the specimen sample was taken
	 * 
	 * @return
	 */
	public Concept getSpecimenSite() {
		return specimenSite;
	}
	
	/**
	 * @param specimenSite
	 */
	public void setSpecimenSite(Concept specimenSite) {
		this.specimenSite = specimenSite;
	}
	
	/**
	 * Date of sample collection
	 * 
	 * @return
	 */
	public Date getCollectionDate() {
		return collectionDate;
	}
	
	/**
	 * @param collectionDate
	 */
	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}
	
	/**
	 * Who collected the sample. Usually a lab representative
	 * 
	 * @return
	 */
	public Provider getCollector() {
		return collector;
	}
	
	/**
	 * @param collector
	 */
	public void setCollector(Provider collector) {
		this.collector = collector;
	}
	
	/**
	 * @return the expirable
	 */
	public Boolean getExpirable() {
		return expirable;
	}
	
	/**
	 * @param expirable the expirable to set
	 */
	public void setExpirable(Boolean expirable) {
		this.expirable = expirable;
	}
	
	/**
	 * In case of non-expirable sample, this should be null
	 * 
	 * @return
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}
	
	/**
	 * @param expiryDate
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	/**
	 * Quantity of the sample
	 * 
	 * @return
	 */
	public Double getQuantity() {
		return quantity;
	}
	
	/**
	 * @param quantity
	 */
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Unit(s) of quantity
	 * 
	 * @return
	 */
	public String getUnits() {
		return units;
	}
	
	/**
	 * @param units
	 */
	public void setUnits(String units) {
		this.units = units;
	}
	
	/**
	 * Date on which the sample was processed
	 * 
	 * @return
	 */
	public Date getProcessedDate() {
		return processedDate;
	}
	
	/**
	 * @param processingDate
	 */
	public void setProcessedDate(Date processingDate) {
		this.processedDate = processingDate;
	}
	
	/**
	 * Status of the sample
	 * 
	 * @return
	 */
	public LabTestSampleStatus getStatus() {
		return status;
	}
	
	/**
	 * @param status
	 */
	public void setStatus(LabTestSampleStatus status) {
		this.status = status;
	}
	
	/**
	 * Sample identifier used by lab
	 * 
	 * @return
	 */
	public String getSampleIdentifier() {
		return sampleIdentifier;
	}
	
	/**
	 * @param sampleIdentifier
	 */
	public void setSampleIdentifier(String sampleIdentifier) {
		this.sampleIdentifier = sampleIdentifier;
	}
	
	/**
	 * Open text Comments
	 * 
	 * @return
	 */
	public String getComments() {
		return comments;
	}
	
	/**
	 * @param comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
}
