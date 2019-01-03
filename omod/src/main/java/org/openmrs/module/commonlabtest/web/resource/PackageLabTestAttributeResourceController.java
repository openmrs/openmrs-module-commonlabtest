package org.openmrs.module.commonlabtest.web.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestPackage;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/commonlab/labtestpackage", supportedClass = LabTestPackage.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })
public class PackageLabTestAttributeResourceController extends DataDelegatingCrudResource<LabTestPackage> {
	
	/**
	 * Logger for this class
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	private CommonLabTestService commonLabTestService = Context.getService(CommonLabTestService.class);
	
	@Override
	public LabTestPackage newDelegate() {
		return new LabTestPackage();
	}
	
	@Override
	public LabTestPackage save(LabTestPackage labTestAttribute) {
		List<LabTestAttribute> listLabTestAttributes = commonLabTestService.saveLabTestAttributes(labTestAttribute
		        .getListAttributes());
		LabTestPackage labTestPackage = new LabTestPackage();
		labTestPackage.setListAttributes(listLabTestAttributes);
		return labTestPackage;
	}
	
	@Override
	public LabTestPackage getByUniqueId(String uniqueId) {
		return null;
	}
	
	@Override
	protected void delete(LabTestPackage delegate, String reason, RequestContext context) throws ResponseException {
		
	}
	
	@Override
	public void purge(LabTestPackage delegate, RequestContext context) throws ResponseException {
		
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		return null;
	}
	
}
