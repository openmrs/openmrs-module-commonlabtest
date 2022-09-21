package org.openmrs.module.commonlabtest;

import org.apache.commons.lang.StringUtils;

public enum LabTestGroup {

	SEROLOGY("SEROLOGY"),
	CARDIOLOGY("CARDIOLOGY"),
	OPHTHALMOLOGY("OPHTHALMOLOGY"),
	BACTERIOLOGY("BACTERIOLOGY"),
	BIOCHEMISTRY("BIOCHEMISTRY"),
	BLOOD_BANK("BLOOD_BANK"),
	CYTOLOGY("CYTOLOGY"),
	HEMATOLOGY("HEMATOLOGY"),
	IMMUNOLOGY("IMMUNOLOGY"),
	MICROBIOLOGY("MICROBIOLOGY"),
	RADIOLOGY("RADIOLOGY"),
	SONOLOGY("SONOLOGY"),
	URINALYSIS("URINALYSIS"),
	OTHER("OTHER");

	String name;

	LabTestGroup(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static final LabTestGroup getByName(String name) {

		for (LabTestGroup field : LabTestGroup.values()) {
			if (StringUtils.equals(name, field.getName())) {
				return field;
			}
		}

		return null;
	}
}
