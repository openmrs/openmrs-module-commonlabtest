package org.openmrs.module.commonlabtest.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTestType;
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
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/commonlab/labtesttype", supportedClass = LabTestType.class, supportedOpenmrsVersions = { "1.9.*" })
public class LabTestTypeResourceController extends MetadataDelegatingCrudResource<LabTestType> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private CommonLabTestService commonLabTestService = Context.getService(CommonLabTestService.class);

	@Override
	public LabTestType getByUniqueId(String s) {
		return commonLabTestService.getLabTestTypeByUuid(s);
	}

	@Override
	public LabTestType newDelegate() {
		return new LabTestType();
	}

	@Override
	public LabTestType save(LabTestType labTestType) {
		return commonLabTestService.saveLabTestType(labTestType);
	}

	@Override
	public void purge(LabTestType labTestType, RequestContext requestContext) throws ResponseException {
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
			description.addProperty("testGroup");
			description.addProperty("shortName");
			description.addProperty("requiresSpecimen");
			description.addProperty("name");
			description.addProperty("description");
			return description;
		} else if (representation instanceof FullRepresentation) {
			description.addProperty("testGroup");
			description.addProperty("shortName");
			description.addProperty("requiresSpecimen");
			description.addProperty("referenceConcept");
			description.addProperty("name");
			description.addProperty("description");
			description.addProperty("auditInfo");
			return description;
		} else if (representation instanceof RefRepresentation) {
			description.addProperty("name");
			description.addProperty("testGroup");
			return description;
		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription delegatingResourceDescription = new DelegatingResourceDescription();
		delegatingResourceDescription.addProperty("name");
		delegatingResourceDescription.addProperty("testGroup");
		delegatingResourceDescription.addProperty("labTestTypeId");
		delegatingResourceDescription.addProperty("shortName");
		delegatingResourceDescription.addProperty("requiresSpecimen");
		delegatingResourceDescription.addProperty("referenceConcept");
		delegatingResourceDescription.addProperty("labTestSamples");
		delegatingResourceDescription.addProperty("order");
		delegatingResourceDescription.addProperty("description");
		return delegatingResourceDescription;
	}

	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<LabTestType> list = commonLabTestService.getAllLabTestTypes(false);
		return new NeedsPaging<LabTestType>(list, context);
	}

	/**
	 * @param labTestType the {@link LabTestType} object
	 * @return description as Display
	 */
	@Override
	@PropertyGetter("display")
	public String getDisplayString(LabTestType labTestType) {
		if (labTestType == null)
			return "";
		return labTestType.getDescription();
	}
}
