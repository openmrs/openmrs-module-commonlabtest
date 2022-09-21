package org.openmrs.module.commonlabtest;

import org.apache.commons.lang.StringUtils;

/**
 * Enumerated type to represent status of sample
 */
public enum LabTestSampleStatus {
	COLLECTED("COLLECTED"), // First state of sample, when received by sample collector
	ACCEPTED("ACCEPTED"), // When lab accepts the sample for processing
	REJECTED("REJECTED"), // Set when lab does not accept the sample due to any reason (contamination,
    // insufficient quantity, etc.)
	PROCESSED("PROCESSED"); // After the sample has been processed by lab

	String name;

	LabTestSampleStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static final LabTestSampleStatus getByName(String name) {

		for (LabTestSampleStatus field : LabTestSampleStatus.values()) {
			if (StringUtils.equals(name, field.getName())) {
				return field;
			}
		}

		return null;
	}
}
