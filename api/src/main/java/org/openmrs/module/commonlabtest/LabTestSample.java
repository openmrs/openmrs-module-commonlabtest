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

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Provider;

/**
 * @author owais.hussain@ihsinformatics.com
 */
public class LabTestSample extends BaseOpenmrsData {
	
	private Integer labTestSampleId;
	
	private LabTest labTest;
	
	private Concept specimenType;
	
	private Concept specimenSite;
	
	private Date collectionDate;
	
	private Provider collector;
	
	private Double quantity;
	
	private String unit;
	
	private Date expiryDate;
	
	private Date processedDate;
	
	private LabTestSampleStatus status;
	
	private String comments;
	
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
	 * Unit of quantity
	 * 
	 * @return
	 */
	public String getUnit() {
		return unit;
	}
	
	/**
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
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
