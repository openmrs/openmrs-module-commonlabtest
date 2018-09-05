package org.openmrs.module.commonlabtest.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/commonlab/labtestattribute", supportedClass = LabTestAttribute.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })
public class LabTestAttributeResourceController extends DataDelegatingCrudResource<LabTestAttribute> {
	
	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	/*	@Autowired
		CommonLabTestService commonLabTestService;*/
	
	private CommonLabTestService commonLabTestService = Context.getService(CommonLabTestService.class);
	
	@Override
	public LabTestAttribute getByUniqueId(String s) {
		
		return commonLabTestService.getLabTestAttributeByUuid(s);
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
	public void purge(LabTestAttribute labTestAttribute, RequestContext requestContext) throws ResponseException {
		throw new ResourceDoesNotSupportOperationException();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
		if (representation instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("labTestAttributeId");
			description.addProperty("testOrderId");
			description.addProperty("attributeTypeId");
			description.addProperty("valueReference");
			
			description.addSelfLink();
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			return description;
		} else if (representation instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("labTestAttributeId");
			description.addProperty("testOrderId");
			description.addProperty("attributeTypeId");
			description.addProperty("valueReference");
			
			description.addProperty("creator");
			description.addProperty("dateCreated");
			
			description.addProperty("changedBy");
			description.addProperty("dateChanged");
			
			description.addProperty("voided");
			description.addProperty("dateVoided");
			description.addProperty("voidedBy");
			description.addProperty("voidReason");
			return description;
		}
		return null;
	}
}
