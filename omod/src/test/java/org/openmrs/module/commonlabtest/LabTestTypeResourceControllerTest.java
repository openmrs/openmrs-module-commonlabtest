package org.openmrs.module.commonlabtest;

import org.junit.Before;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.commonlabtest.web.resource.LabTestTypeResourceController;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.util.SimpleObjectConverter;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest;

public class LabTestTypeResourceControllerTest extends BaseDelegatingResourceTest<LabTestTypeResourceController, LabTestType> {
	
	@Before
	public void before() throws Exception {
		executeDataSet("CommonLabTestService-initialData.xml");
	}
	
	@Override
	public String getDisplayProperty() {
		
		return "GeneXpert Test";
	}
	
	@Override
	public String getUuidProperty() {
		
		return "4bf46c09-46e9-11e8-943c-40b034c3cfee";
	}
	
	@Override
	public LabTestType newObject() {
		LabTestType object = Context.getService(CommonLabTestService.class).getLabTestTypeByUuid(getUuidProperty());
		System.out.println(object);
		return object;//Context.getService(CommonLabTestService.class).getLabTestTypeByUuid(getUuidProperty());
	}
	/*
	@Override
	public void asRepresentation_shouldReturnValidDefaultRepresentation() throws Exception {
		// TODO Auto-generated method stub
		//super.asRepresentation_shouldReturnValidDefaultRepresentation();
		assertPropPresent("uuid");
		//assertPropPresent("testGroup");
		//assertPropPresent("labTestTypeId");
		assertPropPresent("shortName");
		//assertPropPresent("requiresSpecimen");
		//assertPropPresent("referenceConcept");
		assertPropEquals("name", getObject().getName());
		
	}
	
	@Override
	public void validateFullRepresentation() throws Exception {
		//super.validateDefaultRepresentation();
		assertPropPresent("uuid");
		assertPropEquals("name", getObject().getName());
		//assertPropPresent("labTestTypeId");
		//assertPropPresent("requiresSpecimen");
		
	}
	
	@Override
	public void asRepresentation_shouldReturnValidRefRepresentation() throws Exception {
		// TODO Auto-generated method stub
		//super.asRepresentation_shouldReturnValidRefRepresentation();
	}
	
	@Override
	public void validateDefaultRepresentation() throws Exception {
		// TODO Auto-generated method stub
		//super.validateDefaultRepresentation();
		assertPropPresent("uuid");
		assertPropEquals("name", getObject().getName());
		assertPropPresent("labTestTypeId");
		assertPropPresent("referenceConcept");
	}
	*/
	/*	@Override
		public SimpleObject getRepresentation() {
			// TODO Auto-generated method stub
			//return super.getRepresentation();
			//Context.getService(CommonLabTestService.class).getLabTestTypeByUuid(getUuidProperty());
			SimpleObject simple = new SimpleObject();
			simple.add("uuid", getObject().getUuid());
			simple.add("name", getObject().getName());
			simple.add("shortName", getObject().getShortName());
			return simple;
			
		}
		*/
}
