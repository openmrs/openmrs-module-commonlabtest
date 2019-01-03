package org.openmrs.module.commonlabtest;

import java.util.List;

import org.openmrs.BaseOpenmrsData;

public class LabTestPackage extends BaseOpenmrsData {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4032570248987587995L;
	
	private int labTestId;
	
	private List<LabTestAttribute> listAttributes;
	
	@Override
	public Integer getId() {
		return labTestId;
	}
	
	@Override
	public void setId(Integer id) {
		this.labTestId = id;
	}
	
	public List<LabTestAttribute> getListAttributes() {
		return listAttributes;
	}
	
	public void setListAttributes(List<LabTestAttribute> listAttributes) {
		this.listAttributes = listAttributes;
	}
	
}
