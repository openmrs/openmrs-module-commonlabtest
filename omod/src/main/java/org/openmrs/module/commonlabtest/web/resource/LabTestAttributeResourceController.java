package org.openmrs.module.commonlabtest.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.customdatatype.NotYetPersistedException;
import org.openmrs.module.commonlabtest.LabTestAttribute;
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
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/commonlab/labtestattribute", supportedClass = LabTestAttribute.class, supportedOpenmrsVersions = {
                "2.0.*,2.1.*" })
public class LabTestAttributeResourceController extends DataDelegatingCrudResource<LabTestAttribute> {

	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private CommonLabTestService commonLabTestService = Context.getService(CommonLabTestService.class);

	/**
	 * @see DelegatingCrudResource#getRepresentationDescription(Representation)
	 */
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");
		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		description.addProperty("display");
		if (representation instanceof DefaultRepresentation || representation instanceof RefRepresentation) {
			description.addProperty("labTest", Representation.REF);
			description.addProperty("attributeType", Representation.REF);
			description.addProperty("valueReference");
			return description;
		} else if (representation instanceof FullRepresentation) {
			description.addProperty("labTest");
			description.addProperty("attributeType");
			description.addProperty("valueReference");
			description.addProperty("auditInfo");
			return description;
		}
		return description;
	}

	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getCreatableProperties()
	 */
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addRequiredProperty("labTest");
		description.addRequiredProperty("attributeType");
		description.addRequiredProperty("valueReference");
		return description;
	}

	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getUpdatableProperties()
	 */
	@Override
	public DelegatingResourceDescription getUpdatableProperties() throws ResourceDoesNotSupportOperationException {
		return getCreatableProperties();
	}

	@Override
	protected void delete(LabTestAttribute labTestAttribute, String s, RequestContext requestContext)
	        throws ResponseException {
		commonLabTestService.voidLabTestAttribute(labTestAttribute, s);
	}

	@Override
	public LabTestAttribute newDelegate() {
		return new LabTestAttribute();
	}

	@Override
	public LabTestAttribute save(LabTestAttribute labTestAttribute) {
		return commonLabTestService.saveLabTestAttribute(labTestAttribute);
	}

	@Override
	public LabTestAttribute getByUniqueId(String uuid) {
		LabTestAttribute labTestAttribute = commonLabTestService.getLabTestAttributeByUuid(uuid);
		return labTestAttribute;
	}

	@Override
	public void purge(LabTestAttribute labTestAttribute, RequestContext requestContext) throws ResponseException {
		throw new ResourceDoesNotSupportOperationException();
	}

	@Override
	protected PageableResult doSearch(RequestContext context) {
		String testId = context.getRequest().getParameter("testOrderId");
		List<LabTestAttribute> attributes = commonLabTestService.getLabTestAttributes(Integer.parseInt(testId));
		return new NeedsPaging<LabTestAttribute>(attributes, context);
	}

	/**
	 * @param attribute the {@link LabTestAttribute} object
	 * @return valueReference as display
	 */
	@PropertyGetter("display")
	public String getDisplayString(LabTestAttribute attribute) {
		if (attribute == null)
			return "";
		return attribute.getValueReference();
	}

	/**
	 * @param attribute {@link LabTestAttribute} object
	 * @return valueReference
	 */
	@PropertyGetter("valueReference")
	public String getValueReference(LabTestAttribute attribute) {
		try {
			return attribute.getValueReference();
		}
		catch (NotYetPersistedException ex) {
			return null;
		}
	}

	/**
	 * @param instance the {@link LabTestAttribute} object
	 * @param valueReference the valueReference to set
	 */
	@PropertySetter("valueReference")
	public static void setValueReference(LabTestAttribute instance, String valueReference) {
		instance.setValueReferenceInternal(valueReference);
	}
}
