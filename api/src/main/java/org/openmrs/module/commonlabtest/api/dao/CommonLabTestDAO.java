/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.commonlabtest.api.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestType;

/**
 * @author owais.hussain@ihsinformatics.com
 */
public class CommonLabTestDAO {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/* Lab Test Type */
	public LabTestType getLabTestTypeByUuid(String uuid) {
		// TODO
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<LabTestType> getAllLabTestTypes(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestType.class);
		criteria.addOrder(Order.asc("name"));
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}
		return criteria.list();
	}
	
	public LabTestType getLabTestType(Integer labTestTypeId) {
		return (LabTestType) sessionFactory.getCurrentSession().get(LabTestType.class, labTestTypeId);
	}
	
	public LabTestType saveLabTestType(LabTestType labTestType) {
		sessionFactory.getCurrentSession().save(labTestType);
		return labTestType;
	}
	
	public void purgeLabTestType(LabTestType labTestType) {
		sessionFactory.getCurrentSession().delete(labTestType);
	}
	
	/* Lab Test Attribute Type */
	public LabTestAttributeType getLabTestAttributeTypeByUuid(String uuid) {
		// TODO
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<LabTestAttributeType> getAllLabTestAttributeTypes(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestAttributeType.class);
		criteria.addOrder(Order.asc("name"));
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<LabTestAttributeType> getLabTestAttributesByTestType(Integer labTestTypeId, boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestAttributeType.class);
		criteria.createAlias("labTestType", "ltt").add(Restrictions.eq("ltt.labTestTypeId", labTestTypeId));
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}
		return criteria.list();
	}
	
	public LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId) {
		return (LabTestAttributeType) sessionFactory.getCurrentSession().get(LabTestAttributeType.class,
		    labTestAttributeTypeId);
	}
	
	public LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) {
		sessionFactory.getCurrentSession().save(labTestAttributeType);
		return labTestAttributeType;
	}
	
	public void purgeLabTestAttributeType(LabTestAttributeType labTestAttributeType) {
		sessionFactory.getCurrentSession().delete(labTestAttributeType);
	}
	
	/* Lab Test */
	public LabTest getLabTestByUuid(String uuid) {
		// TODO
		return null;
	}
	
	public LabTest getLabTest(Integer labTestId) {
		return (LabTest) sessionFactory.getCurrentSession().get(LabTest.class, labTestId);
	}
	
	public List<LabTest> getLabTestsByPatient(Integer patientId, boolean includeVoided) {
		// TODO:
		return null;
	}
	
	public List<LabTest> getLabTestsByPatientAndType(Integer patientId, Integer labTestTypeId, boolean includeVoided) {
		// TODO: when creating criteria, apply order clause on test type
		return null;
	}
	
	public List<LabTest> getLabTestsBySample(Integer labTestSampleId, boolean includeVoided) {
		// TODO: when creating criteria, apply order clause on test date
		return null;
	}
	
	public LabTest saveLabTest(LabTest labTest) {
		sessionFactory.getCurrentSession().save(labTest);
		return labTest;
	}
	
	public void purgeLabTest(LabTest labTest) {
		sessionFactory.getCurrentSession().delete(labTest);
	}
	
	/* Lab Test Attribute */
	public LabTestAttribute getLabTestAttributeByUuid(String uuid) {
		return null;
	}
	
	public LabTestAttributeType getLabTestAttribute(Integer labTestAttributeId) {
		// TODO:
		return null;
	}
	
	public List<LabTestAttributeType> getLabTestAttributesByAttributeType(Integer labTestAttributeTypeId,
	        boolean includeRetired) {
		// TODO:
		return null;
	}
	
	public List<LabTestAttributeType> getLabTestAttributesByPatient(Integer patientId, boolean includeRetired) {
		// TODO:
		return null;
	}
	
	public List<LabTestAttributeType> getLabTestAttributesByPatientAndAttributeType(Integer labTestAttributeTypeId,
	        Integer patientId, boolean includeRetired) {
		// TODO:
		return null;
	}
	
	public LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute) {
		sessionFactory.getCurrentSession().save(labTestAttribute);
		return labTestAttribute;
	}
	
	public void purgeLabTestAttribute(LabTestAttribute labTestAttribute) {
		sessionFactory.getCurrentSession().delete(labTestAttribute);
	}
	
	/* Lab Test Sample */
	public LabTestSample getLabTestSampleByUuid(String uuid) {
		// TODO
		return null;
	}
	
	public LabTestSample getLabTestSample(Integer labTestSampleId) {
		// TODO:
		return null;
	}
	
	public List<LabTestSample> getLabTestSamplesByPatient(Integer patientId, boolean includeVoided) {
		// TODO:
		return null;
	}
	
	public List<LabTestSample> getLabTestSamplesByCollector(Integer collectorId, boolean includeVoided) {
		// TODO:
		return null;
	}
	
	public LabTestSample saveLabTestSample(LabTestSample labTestSample) {
		sessionFactory.getCurrentSession().save(labTestSample);
		return labTestSample;
	}
	
	public void purgeLabTestSample(LabTestSample labTestSample) {
		sessionFactory.getCurrentSession().delete(labTestSample);
	}
	
	public List<LabTest> getLabTestsByAttributeType(LabTestAttributeType labTestAttributeType) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<LabTest> getLabTestsByType(LabTestType labTestType) {
		// TODO Auto-generated method stub
		return null;
	}
}
