package org.openmrs.module.commonlabtest.web.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.SimpleConcept;
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
        + "/commonlab/concept", supportedClass = SimpleConcept.class, supportedOpenmrsVersions = { "1.9.*" })
public class LabTestTypeConceptResourceController extends MetadataDelegatingCrudResource<SimpleConcept> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private CommonLabTestService commonLabTestService = Context.getService(CommonLabTestService.class);

	@Override
	public SimpleConcept getByUniqueId(String s) {
		throw new ResourceDoesNotSupportOperationException();
	}

	@Override
	public SimpleConcept newDelegate() {
		throw new ResourceDoesNotSupportOperationException();
	}

	@Override
	public SimpleConcept save(SimpleConcept c) {
		throw new ResourceDoesNotSupportOperationException();
	}

	@Override
	public void purge(SimpleConcept c, RequestContext requestContext) throws ResponseException {
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
			description.addProperty("name");
			description.addProperty("description");
			description.addProperty("datatype");
			description.addProperty("conceptClass");
			return description;
		} else if (representation instanceof FullRepresentation) {
			description.addProperty("name");
			description.addProperty("description");
			description.addProperty("datatype");
			description.addProperty("conceptClass");
			description.addProperty("creator");
			description.addProperty("dateCreated");
			description.addProperty("changedBy");
			description.addProperty("dateChanged");
			description.addProperty("retired");
			description.addProperty("dateRetired");
			description.addProperty("retiredBy");
			description.addProperty("retireReason");
			return description;
		} else if (representation instanceof RefRepresentation) {
			description.addProperty("name");
			return description;
		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		return new DelegatingResourceDescription();
	}

	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		throw new ResourceDoesNotSupportOperationException();
	}

	@Override
	protected PageableResult doSearch(RequestContext context) {
		String type = context.getParameter("type");
		List<Concept> concepts = new ArrayList<Concept>();
		// Lab test type concepts
		if ("labtesttype".equalsIgnoreCase(type) || "labtesttypes".equalsIgnoreCase(type)) {
			concepts = commonLabTestService.getLabTestConcepts();
		}
		// Specimen type concepts
		else if ("specimentype".equalsIgnoreCase(type) || "specimentypes".equalsIgnoreCase(type)) {
			concepts = commonLabTestService.getSpecimenTypeConcepts();
		}
		// Specimen site concepts
		else if ("specimensite".equalsIgnoreCase(type) || "specimensites".equalsIgnoreCase(type)) {
			concepts = commonLabTestService.getSpecimenSiteConcepts();
		}
		List<SimpleConcept> list = new ArrayList<SimpleConcept>();
		for (Concept c : concepts) {
			SimpleConcept sc = new SimpleConcept();
			sc.convert(c);
			list.add(sc);
		}
		return new NeedsPaging<SimpleConcept>(list, context);
	}

	/**
	 * @param c the {@link LabTestType} object
	 * @return description as Display
	 */
	@Override
	@PropertyGetter("display")
	public String getDisplayString(SimpleConcept c) {
		if (c == null)
			return "";
		return c.getDescription();
	}
}
