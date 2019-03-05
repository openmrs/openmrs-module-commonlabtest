package org.openmrs.module.commonlabtest.utility;

import java.text.SimpleDateFormat;

public class Consts {

	// data type String
	public static final String DATATYPE_BOOLEAN_OPENMRS = "org.openmrs.customdatatype.datatype.BooleanDatatype";

	public static final String BOOLEAN = "Boolean";

	public static final String DATATYOE_FREE_TEXT_OPENMRS = "org.openmrs.customdatatype.datatype.FreeTextDatatype";

	public static final String DATATYOE_TEXT = "Text";

	public static final String DATATYPE_CONCEPT_OPENMRS = "org.openmrs.customdatatype.datatype.ConceptDatatype";

	public static final String DATATYPE_CODED = "Coded";

	public static final String DATATYPE_LOCATION_OPENMRS = "org.openmrs.customdatatype.datatype.LocationDatatype";

	public static final String DATATYPE_DATETIME_OPENMRS = "org.openmrs.customdatatype.datatype.DateDatatype";

	public static final String DATATYPE_DATETIME = "Datetime";

	public static final String DATATYPE_DATE = "Datetime";

	public static final String DATATYPE_FLOAT_OPENMRS = "org.openmrs.customdatatype.datatype.FloatDatatype";

	public static final String DATATYPE_NUMERIC = "Numeric";

	public static final String DATATYPE_REGEX_OPENMRS = "org.openmrs.customdatatype.datatype.RegexValidatedTextDatatype";

	public static final String DATATYPE_REGEX = "Regex";

	public static final String DATATYPE_LONGTEXT_OPENMRS = "org.openmrs.customdatatype.datatype.LongFreeTextDatatype";

	public static final String DATATYPE_TEXTAREA = "TextArea";

	public static final String NA = "N/A";

	// ERROR MESSAGES STRING

	public static final String ERROR_VOID_RESULT = "Could not void Lab Test Result";

	public static final String ERROR_TESTORDER_DOESNOT_EXIST = "Test Order does not exist";

	public static final String ERROR_TESTRESULT_NOT_SAVE = "Could not save Lab Test Result";

	// RESPONSE MESSAGES STRING
	public static final String SUCCESSFULLY_VOIDED_RESULT = "Test Result voided successfully";

	public static final String TESTORDER_VOIDED = "Test Order is voided";

	public static final String ATTRIBUTE_NO_LONGER_REQUIRED = "Attribute no longer required.";

	public static final String ATTRIBUTE_VALUE_CHANGED = "Attribute value changed.";

	public static final String TEST_RESULT_SAVED = "Test Result saved successfully";

	// RESULT FORM STRINGS
	public static final String TESTTYPE_NAME = "testTypeName";

	public static final String UPDATE = "update";

	public static final String FILE_PATH = "filepath";

	public static final String ATTRIBUTE_TYPE_LIST = "attributeTypeList";

	public static final String TEST_ORDER_ID = "testOrderId";

	public static final String PATIENT_ID = "patientId";

	public static final String FILE_EXTENSIONS = "fileExtensions";

	public static final String ENCOUNT_DATE = "encounterdate";

	// LAB ATTRIBUTE TYPE STRINGS
	public static final String NAME = "name";

	public static final String MIN_OCCURS = "minOccurs";

	public static final String MAX_OCCURS = "maxOccurs";

	public static final String SORT_WEIGHT = "sortWeight";

	public static final String CONFIG = "config";

	public static final String TEST_ATTRIBUTE_ID = "testAttributeId";

	public static final String ID = "id";

	public static final String CONCEPT_NAME = "conceptName";

	public static final String CONCEPT_ID = "conceptId";

	public static final String CONCEPT_OPTIONS = "conceptOptions";

	public static final String DATA_TYPE = "dataType";

	public static final String HINT = "hint";

	public static final String VALUE = "value";

	public static final String GROUP_NAME = "groupName";

	public static final String SUB_GROUP_NAME = "subGroupName";

	public static final String SUB_DETAILS = "subDetails";

	public static final String DETAILS = "details";

	public static final String AVAILABLE = "available";

	public static final String LIST_TEST_TYPE = "listTestType";

	public static final String ATTRIBUTE_TYPE = "attributeType";

	public static final String ERROR = "error";

	public static final String INVALID_LAB_TESTTYPE = "Invalid Lab Test Type concept Id entered";

	public static final String TEST_ATTRIBUTE_UUID_MESSAGE = "Lab Test Attribute with Uuid :";

	public static final String NOT_SAVE_TEST_ATTRIBUTE_TYPE_MESSAGE = "could not save Lab Test Attribute Type";

	public static final String SAVED = "save";

	public static final String COULD_NOT_RETIRE_TEST_ATTRIBUTE_TYPE_MESSAGE = "could not retire Lab Test Attribute Type";

	public static final String COULD_NOT_DELETE_ATTRIBUTE_TYPE_MESSAGE = "could not delete Lab Test Attribute Type";

	// LabTestOrderController Strings
	public static final String LAB_TEST = "labTest";

	public static final String PROVIDER = "provider";

	public static final String TEST_TYPES = "testTypes";

	public static final String ENCOUNTER = "encounters";

	public static final String COULD_NOT_SAVE_TEST_ORDER_MESSAGE = "could not save Lab Test Order";

	public static final String COULD_NOT_VOID_TEST_ORDER_MESSAGE = "could not void Lab Test Order";

	public static final String TEST_ORDER_SAVED_MESSAGE = "Test order saved successfully";

	public static final String TEST_ORDER_VOIDED_MESSAGE = "Test order voided successfully";

	// LabTestOrderPortletController Strings
	public static final String REQUIRED_SPECIMEN = "requiredSpecimen";

	public static final String TEST_TYPE_NAME = "testTypeName";

	public static final String LAB_REFERENCE_NUMBER = "labReferenceNumber";

	public static final String TEST_GROUP = "testGroup";

	public static final String DATE_CREATED = "dateCreated";

	public static final String CREATED_BY = "createdBy";

	public static final String ENCOUNTER_TYPE = "encounterType";

	public static final String CHANGED_BY = "changedBy";

	public static final String UUID = "uuid";

	public static final String RESULT_FILLED = "resultFilled";

	public static final String RESULT_DATE = "resultDate";

	public static final String TEST_ORDER = "testOrder";

	// LabTestRequestController Strings
	public static final String UNKNOWN = "UNKNOWN";

	public static final String TEST_TYPE_ID = "testTypeId";

	public static final String TEST_TYPE = "testType";

	public static final String LAB_TEST_TYPES = "labTestTypes";

	public static final String ENCOUNTER_ID = "encounterId";

	public static final String LAB_INSTRUCTION = "labInstructions";

	public static final String COULD_NOT_SAVE_TEST_REQUEST_MESSAGE = "could not save Lab Test Request";

	public static final String TEST_REQUEST_SAVED_MESSAGE = "Test Request saved successfully";

	// LabTestResultViewController String
	public static final String SPECIMENT_TYPE = "specimenType";

	public static final String SPECIMENT_SITE = "specimenSite";

	public static final String STATUS = "status";

	public static final String SAMPLE = "sample";

	public static final String RESULT = "result";

	public static final String ATTRIBUTE_TYPE_NAME = "attributeTypeName";

	public static final String MULTISET_NAME = "multisetName";

	public static final String SORT_WEIGHT_LIST = "sortweightlist";

	public static final String GROUP = "groups";

	public static final String QUESTIONS = "question";

	public static final String VALUE_REFERENCE = "valuesReference";

	public static final String VOID = "void";

	// LabTestSampleController Strings
	public static final String TEST_SAMPLE = "testSample";

	public static final String ORDER_ENC_DATE = "orderEncDate";

	public static final String ORDER_ID = "orderId";

	public static final String LAB_TEST_SAMPLE_UUID_MESSAGE = "Lab Test Sample with Uuid :";

	public static final String COULD_NOT_SAVE_TEST_SAMPLE_MESSAGE = "could not save Lab Test Sample";

	public static final String COULD_NOT_VOID_TEST_SAMPLE_MESSAGE = "could not void Lab Test Sample";

	public static final String TEST_UNIT = "testUnits";

	public static final String TEST_ORDER_DOES_NOT_EXIST_MESSAGE = "Test Order does not exist";

	public static final String TEST_ORDER_VOIED_MESSAGE = "Test Order is voided";

	// LabTestTypeController Strings labTestType

	public static final String TEST = "Test";

	public static final String LAB_TEST_TYPE = "labTestType";

	public static final String SHORT_NAME = "shortName";

	public static final String DESCRIPTION = "description";

	public static final String CONCEPT_JSON = "conceptsJson";

	public static final String INVALID_REFERENCE_MESSAGE = "Invalid Reference concept Id entered";

	public static final String LAB_TEST_TYPE_UUID_MESSAGE = "Lab Test Type with Uuid :";

	public static final String COULD_NOT_SAVE_TEST_TYPE_MESAGE = "could not save Lab Test Type.";

	public static final String COULD_NOT_RETIRE_TEST_TYPE_MESSAGE = "could not retire Lab Test Type.";

	public static final String PERMANENTLY_DELETED_MESSAGE = "is permanently deleted!";

	public static final String PERMANENTLY_RETIRED_MESSAGE = "is permanently retired";

	public static final String COULD_NOT_DELETE_TEST_TYPE = "could not delete Lab Test Type.";

	// ManageLabTestAttributeTypesController Strings
	public static final String LAB_TEST_ATTRIBUTE_TYPES = "labTestAttributeTypes";

	// ManageLabTestSampleController Strings
	public static final String TEST_ORDER_NOT_FOUND_MESSAGE = "Test Order is not found";

	public static final String SAMPLE_PROCESSED = "sampleProcessed";

	public static final String LAB_SAMPLE_TEST = "labSampleTest";

	public static final String LAB_TEST_SAMPLE_UPDATED_MESSAGE = "Lab Test Sample is updated";

	public static final String COULD_NOT_SAVE_LAB_TEST_SAMPLE = "could not save Lab Test Sample.";

	// ManageLabTestTypeController Strings

	// Date format
	public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private Consts() {
	}
}
