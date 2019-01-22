package org.openmrs.module.commonlabtest.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
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
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/commonlab/labtestsample", supportedClass = LabTestSample.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })
public class LabTestSampleResourceController extends DataDelegatingCrudResource<LabTestSample> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private CommonLabTestService commonLabTestService = Context.getService(CommonLabTestService.class);

	@Override
	public LabTestSample getByUniqueId(String s) {
		return commonLabTestService.getLabTestSampleByUuid(s);
	}

	@Override
	protected void delete(LabTestSample labTestSample, String s, RequestContext requestContext) throws ResponseException {
		commonLabTestService.voidLabTestSample(labTestSample, s);
	}

	@Override
	public LabTestSample newDelegate() {
		return new LabTestSample();
	}

	@Override
	public LabTestSample save(LabTestSample labTestSample) {
		return commonLabTestService.saveLabTestSample(labTestSample);
	}

	@Override
	public void purge(LabTestSample labTestSample, RequestContext requestContext) throws ResponseException {
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
			description.addProperty("labTest", Representation.REF);
			description.addProperty("specimenType", Representation.REF);
			description.addProperty("specimenSite", Representation.REF);
			description.addProperty("collectionDate");
			description.addProperty("collector");
			description.addProperty("quantity");
			description.addProperty("units");
			description.addProperty("expirable");
			description.addProperty("expiryDate");
			description.addProperty("processedDate");
			description.addProperty("status");
			description.addProperty("sampleIdentifier");
			description.addProperty("comments");
			return description;
		} else if (representation instanceof FullRepresentation) {
			description.addProperty("labTest");
			description.addProperty("specimenType");
			description.addProperty("specimenSite");
			description.addProperty("collectionDate");
			description.addProperty("collector");
			description.addProperty("quantity");
			description.addProperty("units");
			description.addProperty("expirable");
			description.addProperty("expiryDate");
			description.addProperty("processedDate");
			description.addProperty("status");
			description.addProperty("sampleIdentifier");
			description.addProperty("comments");
			description.addProperty("auditInfo");
			return description;
		} else if (representation instanceof RefRepresentation) {
			description.addProperty("labTest", Representation.REF);
			description.addProperty("specimenType", Representation.REF);
			description.addProperty("status");
			description.addProperty("sampleIdentifier");
		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addRequiredProperty("labTest");
		description.addRequiredProperty("sampleIdentifier");
		description.addProperty("specimenType");
		description.addProperty("specimenSite");
		description.addProperty("collectionDate");
		description.addProperty("collector");
		description.addProperty("quantity");
		description.addProperty("units");
		description.addProperty("expirable");
		description.addProperty("expiryDate");
		description.addProperty("processedDate");
		description.addProperty("status");
		description.addProperty("comments");
		return description;
	}

	/**
	 * @param sample the {@link LabTestSample} object
	 * @return getSampleIdentifier as Display
	 */
	@PropertyGetter("display")
	public String getDisplayString(LabTestSample sample) {
		if (sample == null)
			return "";

		return sample.getSampleIdentifier();
	}
}
