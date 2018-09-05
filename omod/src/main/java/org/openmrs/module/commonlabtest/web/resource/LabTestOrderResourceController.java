package org.openmrs.module.commonlabtest.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/commonlab/labtestorder", supportedClass = LabTest.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })
public class LabTestOrderResourceController extends DataDelegatingCrudResource<LabTest> {
	
	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	/*	@Autowired
		CommonLabTestService commonLabTestService;*/
	
	private CommonLabTestService commonLabTestService = Context.getService(CommonLabTestService.class);
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		System.out.println("DO Search ");
		String pId = context.getRequest().getParameter("patientId");
		Patient patient = Context.getPatientService().getPatient(Integer.parseInt(pId));
		//PageableResult pr=PageableResult;
		
		System.out.println("Patient Id " + pId);
		return new NeedsPaging<LabTest>(commonLabTestService.getLabTests(patient, false), context);//super.doSearch(context);
	}
	
	@Override
	public LabTest getByUniqueId(String s) {
		return commonLabTestService.getLabTestByUuid(s);
	}
	
	@Override
	protected void delete(LabTest labTest, String s, RequestContext requestContext) throws ResponseException {
		commonLabTestService.voidLabTest(labTest, s);
	}
	
	@Override
	public LabTest newDelegate() {
		return new LabTest();
	}
	
	@Override
	public LabTest save(LabTest labTest) {
		return commonLabTestService.saveLabTest(labTest);
	}
	
	@Override
	public void purge(LabTest labTest, RequestContext requestContext) throws ResponseException {
		throw new ResourceDoesNotSupportOperationException();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
		if (representation instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("testOrderId");
			description.addProperty("order");
			description.addProperty("labTestType");
			description.addProperty("labReferenceNumber");
			description.addProperty("labTestSamples");
			
			description.addSelfLink();
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			return description;
		} else if (representation instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("testOrderId");
			description.addProperty("order");
			description.addProperty("labTestType");
			description.addProperty("labReferenceNumber");
			description.addProperty("labTestSamples");
			description.addProperty("attributes");
			
			description.addProperty("creator");
			description.addProperty("dateCreated");
			
			description.addProperty("changedBy");
			description.addProperty("dateChanged");
			
			description.addProperty("voided");
			description.addProperty("dateVoided");
			description.addProperty("voidedBy");
			description.addProperty("voidReason");
			return description;
		} else if (representation instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("testOrderId");
			description.addProperty("order");
			description.addProperty("labTestType");
			description.addProperty("labReferenceNumber");
			description.addProperty("labTestSamples");
			
			description.addSelfLink();
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			return description;
		}
		return null;
	}
	
}
