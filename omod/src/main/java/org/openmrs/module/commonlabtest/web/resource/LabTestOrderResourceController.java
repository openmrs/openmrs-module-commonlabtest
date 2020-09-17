package org.openmrs.module.commonlabtest.web.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
import org.openmrs.module.webservices.rest.web.annotation.PropertySetter;
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

@Resource(name = RestConstants.VERSION_1
        + "/commonlab/labtestorder", supportedClass = LabTest.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })
public class LabTestOrderResourceController extends DataDelegatingCrudResource<LabTest> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private CommonLabTestService commonLabTestService = Context.getService(CommonLabTestService.class);

	@Override
	public LabTest getByUniqueId(String s) {
		LabTest labTest = commonLabTestService.getLabTestByUuid(s);
		labTest.setLabTestSamples(new HashSet<LabTestSample>(commonLabTestService.getLabTestSamples(labTest, false)));
		labTest.setAttributes(
		    new HashSet<LabTestAttribute>(commonLabTestService.getLabTestAttributes(labTest.getTestOrderId())));
		return labTest;
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
			LabTestSample labTestSample = null;
			for (LabTestSample sample : labTest.getLabTestSamples()) {
				if (!sample.getVoided().booleanValue()) {
					labTestSample = sample;
					break;
				}
			}
			List<LabTestAttribute> labTestAttributes = null;
			if (!labTest.getAttributes().isEmpty()) {
				labTestAttributes = new ArrayList<LabTestAttribute>();
				for (LabTestAttribute attribute : labTest.getAttributes()) {
					labTestAttributes.add(attribute);
				}
			}
			// See if the order already exists
			Order existing = Context.getOrderService().getOrderByUuid(labTest.getOrder().getUuid());
			if (existing != null) {
				labTest.setOrder(existing);
			} else {
				Order order = Context.getOrderService().saveOrder(labTest.getOrder(), null);
				labTest.setOrder(order);
			}
			return commonLabTestService.saveLabTest(labTest, labTestSample, labTestAttributes);
		}
		catch (Exception e) {
			throw new ResourceDoesNotSupportOperationException("Test Order was not saved", e);
		}
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
			description.addProperty("display");
			description.addProperty("order");
			description.addProperty("labTestType", Representation.REF);
			description.addProperty("labReferenceNumber");
			description.addProperty("labTestSamples");
			description.addProperty("attributes", Representation.REF);
			description.addSelfLink();
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			return description;
		} else if (representation instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("display");
			description.addProperty("order");
			description.addProperty("labTestType");
			description.addProperty("labReferenceNumber");
			description.addProperty("labTestSamples");
			description.addProperty("attributes", Representation.DEFAULT);
			description.addProperty("auditInfo");
			description.addSelfLink();
			return description;
		} else if (representation instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("order");
			description.addProperty("labReferenceNumber");
			return description;
		}
		return null;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
		DelegatingResourceDescription delegatingResourceDescription = new DelegatingResourceDescription();
		delegatingResourceDescription.addRequiredProperty("order");
		delegatingResourceDescription.addRequiredProperty("labTestType");
		delegatingResourceDescription.addRequiredProperty("labReferenceNumber");
		delegatingResourceDescription.addProperty("labInstructions");
		delegatingResourceDescription.addProperty("resultComments");
		delegatingResourceDescription.addProperty("labTestSamples");
		delegatingResourceDescription.addProperty("attributes");
		return delegatingResourceDescription;
	}

	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getUpdatableProperties()
	 */
	@Override
	public DelegatingResourceDescription getUpdatableProperties() throws ResourceDoesNotSupportOperationException {
		return getCreatableProperties();
	}

	/**
	 * @param labTest the {@link LabTest} object
	 * @return labReferenceNumber as Display
	 */
	@PropertyGetter("display")
	public String getDisplayString(LabTest labTest) {
		if (labTest == null)
			return "";
		return labTest.getLabReferenceNumber();
	}

	@PropertySetter("attributes")
	public static void setAttributes(LabTest instance, List<LabTestAttribute> attributes) {
		for (LabTestAttribute attribute : attributes) {
			instance.addAttribute(attribute);
		}
	}

	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getPropertiesToExposeAsSubResources()
	 */
	@Override
	public List<String> getPropertiesToExposeAsSubResources() {
		return Arrays.asList("attributes");
	}

	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		throw new ResourceDoesNotSupportOperationException();
	}

	@Override
	protected PageableResult doSearch(RequestContext context) {
		String uuid = context.getRequest().getParameter("patient");
		Patient patient = Context.getPatientService().getPatientByUuid(uuid);
		return new NeedsPaging<LabTest>(commonLabTestService.getLabTests(patient, false), context);
	}
}
