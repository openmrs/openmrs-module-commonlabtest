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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Concept;

/**
 * This entity represents types of Laboratory tests. A lab test type object is prerequisite for
 * LabTest, LabTestAttributeType and LabTestSample objects
 * 
 * @author owais.hussain@ihsinformatics.com
 */
@Entity(name = "commonlabtest.LabTestType")
@Table(name = "commonlabtest_type")
public class LabTestType extends BaseOpenmrsMetadata {
	
	public enum LabTestGroup {
		SEROLOGY, CARDIOLOGY, OPHTHALMOLOGY, BACTERIOLOGY, BIOCHEMISTRY, BLOOD_BANK, CYTOLOGY, HEMATOLOGY, IMMUNOLOGY, MICROBIOLOGY, RADIOLOGY, SONOLOGY, URINALYSIS, OTHER
	}
	
	private static final long serialVersionUID = -4734826044571156784L;
	
	public static final String UNKNOWN_TEST_UUID = "ee9b140e-9a29-11e8-a296-40b034c3cfee";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "test_type_id")
	private Integer labTestTypeId;
	
	@Field
	@Column(name = "short_name", length = 50)
	private String shortName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "test_group", length = 50)
	private LabTestGroup testGroup;
	
	@Field
	@Column(name = "requires_specimen", nullable = false)
	private Boolean requiresSpecimen = Boolean.FALSE;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "reference_concept_id")
	private Concept referenceConcept;
	
	/**
	 * Default constructor
	 */
	public LabTestType() {
	}
	
	public LabTestType(Integer id) {
		this.labTestTypeId = id;
	}
	
	@Override
	public Integer getId() {
		return labTestTypeId;
	}
	
	public Integer getLabTestTypeId() {
		return labTestTypeId;
	}
	
	public void setLabTestTypeId(Integer id) {
		setId(id);
	}
	
	@Override
	public void setId(Integer id) {
		this.labTestTypeId = id;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public LabTestGroup getTestGroup() {
		return testGroup;
	}
	
	public void setTestGroup(LabTestGroup testGroup) {
		this.testGroup = testGroup;
	}
	
	public Boolean getRequiresSpecimen() {
		return requiresSpecimen;
	}
	
	public void setRequiresSpecimen(Boolean requiresSpecimen) {
		this.requiresSpecimen = requiresSpecimen;
	}
	
	public Concept getReferenceConcept() {
		return referenceConcept;
	}
	
	public void setReferenceConcept(Concept referenceConcept) {
		this.referenceConcept = referenceConcept;
	}
	
	@Override
	public String toString() {
		return labTestTypeId + ", " + shortName + ", " + testGroup + ", " + referenceConcept + ", " + getName() + ", "
		        + getUuid();
	}
	
}
