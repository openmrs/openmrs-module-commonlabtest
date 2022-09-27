/**
 * 
 */
package org.openmrs.module.commonlabtest;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptName;

/**
 * @author owais.hussain@esquaredsystems.com
 */
public class SimpleConcept extends BaseOpenmrsMetadata implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer conceptId;

	private ConceptDatatype datatype;

	private ConceptClass conceptClass;

	private ConceptName name;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	@Override
	public Integer getId() {
		return conceptId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	@Override
	public void setId(Integer id) {
		conceptId = id;
	}

	/**
	 * @return the datatype
	 */
	public ConceptDatatype getDatatype() {
		return datatype;
	}

	/**
	 * @param datatype the datatype to set
	 */
	public void setDatatype(ConceptDatatype datatype) {
		this.datatype = datatype;
	}

	/**
	 * @return the conceptClass
	 */
	public ConceptClass getConceptClass() {
		return conceptClass;
	}

	/**
	 * @param conceptClass the conceptClass to set
	 */
	public void setConceptClass(ConceptClass conceptClass) {
		this.conceptClass = conceptClass;
	}

	/**
	 * @return the name
	 */
	public ConceptName getConceptName() {
		return name;
	}

	public void getname() {
		name.getName();
	}

	/**
	 * @param name the name to set
	 */
	public void setName(ConceptName name) {
		this.name = name;
	}

	public void convert(Concept concept) {
		setUuid(concept.getUuid());
		setId(concept.getConceptId());
		setDatatype(concept.getDatatype());
		setConceptClass(concept.getConceptClass());
		setName(concept.getName());
		super.setName(concept.getName().getName());
		if (concept.getDescription() != null) {
			setDescription(concept.getDescription().getDescription());
		}
		setCreator(concept.getCreator());
		setDateCreated(concept.getDateCreated());
		setChangedBy(concept.getChangedBy());
		setDateChanged(concept.getDateChanged());
		setRetired(concept.isRetired());
		setDateRetired(concept.getDateRetired());
		setRetiredBy(concept.getRetiredBy());
		setRetireReason(concept.getRetireReason());
	}
}
