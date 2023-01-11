package org.openmrs.module.commonlabtest;

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
