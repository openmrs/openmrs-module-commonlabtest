package org.openmrs.module.commonlabtest.web.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
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
	
	private CommonLabTestService commonLabTestService = Context.getService(CommonLabTestService.class);
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		
		String pId = context.getRequest().getParameter("patientId");
		Patient patient = Context.getPatientService().getPatient(Integer.parseInt(pId));
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
		try {
			LabTestSample ss = null;
			
			for (LabTestSample sample : labTest.getLabTestSamples()) {
				if (sample.getVoided().booleanValue() == false) {
					ss = sample;
				}
				
			}
			Set<LabTestAttribute> attributes = labTest.getAttributes();
			
			Order o = Context.getOrderService().saveOrder(labTest.getOrder(), null);
			labTest.setOrder(o);
			LabTest labTestSaved = commonLabTestService.saveLabTest(labTest);
			List<LabTestAttribute> resultAttributes = attributes.size() > 0 ? new ArrayList<LabTestAttribute>() : null;
			for (LabTestAttribute attribute : labTest.getAttributes()) {
				attribute.setLabTest(labTestSaved);
				resultAttributes.add(attribute);
			}
			
			if (ss != null) {
				ss.setLabTest(labTestSaved);
				ss = commonLabTestService.saveLabTestSample(ss);
			}
			if (resultAttributes != null) {
				commonLabTestService.saveLabTestAttributes(resultAttributes);
			}
			
			return labTestSaved;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ResourceDoesNotSupportOperationException("Test Order wasnot saved successfully", e);
		}
		
	}
	
	@Override
	public void purge(LabTest labTest, RequestContext requestContext) throws ResponseException {
		throw new ResourceDoesNotSupportOperationException();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");
		
		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("display");
		if (representation instanceof DefaultRepresentation) {
			description.addProperty("uuid");
			description.addProperty("testOrderId");
			description.addProperty("order");
			description.addProperty("labTestType");
			description.addProperty("labReferenceNumber");
			
			return description;
		} else if (representation instanceof FullRepresentation) {
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
			description.addProperty("uuid");
			description.addProperty("testOrderId");
			description.addProperty("order");
			description.addProperty("labTestType");
			description.addProperty("labReferenceNumber");
			
			return description;
		}
		return description;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<LabTest> list = commonLabTestService.getLabTests(
		    Context.getPatientService().getPatient(Integer.parseInt(context.getRequest().getParameter("patientId"))), true);
		
		return new NeedsPaging<LabTest>(list, context);
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
		DelegatingResourceDescription delegatingResourceDescription = new DelegatingResourceDescription();
		//delegatingResourceDescription.addProperty();
		
		delegatingResourceDescription.addProperty("labReferenceNumber");
		delegatingResourceDescription.addProperty("labInstructions");
		delegatingResourceDescription.addProperty("resultComments");
		delegatingResourceDescription.addProperty("labTestType");
		delegatingResourceDescription.addProperty("labTestSamples");
		delegatingResourceDescription.addProperty("order");
		delegatingResourceDescription.addProperty("patient");
		delegatingResourceDescription.addProperty("attributes");
		
		return delegatingResourceDescription;
	}
	
	/**
	 * @param LabTest
	 * @return getLabReferenceNumber as Display
	 */
	@PropertyGetter("display")
	public String getDisplayString(LabTest labTest) {
		if (labTest == null)
			return "";
		
		return labTest.getLabReferenceNumber();
	}
	
}
