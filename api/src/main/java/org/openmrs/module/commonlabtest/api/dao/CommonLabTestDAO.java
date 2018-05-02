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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestGroup;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestType;

/**
 * @author owais.hussain@ihsinformatics.com
 */
public class CommonLabTestDAO {
	
	private static final int MAX_FETCH_LIMIT = 100;
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
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
	public List<LabTestType> getAllLabTestTypes(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestType.class);
		criteria.addOrder(Order.asc("name"));
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}
		return criteria.list();
	}
	
	public LabTest getLabTest(Integer labTestId) {
		return (LabTest) sessionFactory.getCurrentSession().get(LabTest.class, labTestId);
	}
	
	public LabTestAttribute getLabTestAttribute(Integer labTestAttributeId) {
		return (LabTestAttribute) sessionFactory.getCurrentSession().get(LabTestAttribute.class, labTestAttributeId);
	}
	
	public LabTestAttribute getLabTestAttributeByUuid(String uuid) {
		return (LabTestAttribute) sessionFactory.getCurrentSession()
		        .createQuery("from LabTestAttribute l where l.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, LabTestType labTestType,
	        Patient patient, String valueReference, Date from, Date to, boolean includeVoided) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestAttribute.class);
		if (labTestAttributeType != null) {
			// TODO:
		}
		if (labTestType != null) {
			// TODO:
		}
		if (patient != null) {
			// TODO:
		}
		if (valueReference != null) {
			criteria.add(Restrictions.ilike("valueReference", valueReference, MatchMode.START));
		}
		if (from != null && to != null) {
			criteria.add(Restrictions.between("dateCreated", from, to));
		}
		if (!includeVoided) {
			criteria.add(Restrictions.eq("voided", false));
		}
		criteria.addOrder(Order.asc("testOrderId")).addOrder(Order.asc("voided")).list();
		return criteria.list();
	}
	
	public LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId) {
		return (LabTestAttributeType) sessionFactory.getCurrentSession().get(LabTestAttributeType.class,
		    labTestAttributeTypeId);
	}
	
	public LabTestAttributeType getLabTestAttributeTypeByUuid(String uuid) {
		return (LabTestAttributeType) sessionFactory.getCurrentSession()
		        .createQuery("from LabTestAttributeType l where l.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<LabTestAttributeType> getLabTestAttributeTypes(String name, String datatypeClassname, boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestAttributeType.class);
		if (name != null) {
			criteria.add(Restrictions.ilike("name", name));
		}
		if (datatypeClassname != null) {
			criteria.add(Restrictions.eq("datatypeClassname", datatypeClassname));
		}
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}
		criteria.addOrder(Order.asc("name")).addOrder(Order.asc("retired")).list();
		return criteria.list();
	}
	
	public LabTest getLabTestByUuid(String uuid) {
		return (LabTest) sessionFactory.getCurrentSession().createQuery("from LabTest l where l.uuid = :uuid")
		        .setString("uuid", uuid).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<LabTest> getLabTests(LabTestType labTestType, Patient patient, LabTestSample sample, String orderNumber,
	        String referenceNumber, Concept orderConcept, Provider orderer, Date from, Date to, boolean includeVoided) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class);
		criteria.createAlias("order", "o");
		if (labTestType != null) {
			criteria.add(Restrictions.eq("labTestType", labTestType));
		}
		if (patient != null) {
			criteria.add(Restrictions.eq("o.patient", patient));
		}
		if (sample != null) {
			// TODO:
		}
		if (orderNumber != null) {
			criteria.add(Restrictions.ilike("o.orderReference", orderNumber, MatchMode.START));
		}
		if (orderConcept != null) {
			criteria.add(Restrictions.eq("o.concept", orderConcept));
		}
		if (orderer != null) {
			criteria.add(Restrictions.eq("o.orderer", orderer));
		}
		if (referenceNumber != null) {
			criteria.add(Restrictions.ilike("referenceNumber", referenceNumber, MatchMode.START));
		}
		if (from != null && to != null) {
			criteria.add(Restrictions.between("dateCreated", from, to));
		}
		if (!includeVoided) {
			criteria.add(Restrictions.eq("voided", false));
		}
		criteria.addOrder(Order.asc("testOrderId")).addOrder(Order.asc("voided")).list();
		return criteria.list();
	}
	
	public LabTestSample getLabTestSample(Integer labTestSampleId) {
		return (LabTestSample) sessionFactory.getCurrentSession().get(LabTestSample.class, labTestSampleId);
	}
	
	public LabTestSample getLabTestSampleByUuid(String uuid) {
		return (LabTestSample) sessionFactory.getCurrentSession().createQuery("from LabTestSample l where l.uuid = :uuid")
		        .setString("uuid", uuid).uniqueResult();
	}
	
	public List<LabTestSample> getLabTestSamples(Patient patient, Provider collector, boolean includeVoided) {
		return null;
	}
	
	public LabTestType getLabTestType(Integer labTestTypeId) {
		return (LabTestType) sessionFactory.getCurrentSession().get(LabTestType.class, labTestTypeId);
	}
	
	public LabTestType getLabTestTypeByUuid(String uuid) {
		return (LabTestType) sessionFactory.getCurrentSession().createQuery("from LabTestType l where l.uuid = :uuid")
		        .setString("uuid", uuid).uniqueResult();
	}
	
	public void purgeLabTest(LabTest labTest) {
		sessionFactory.getCurrentSession().delete(labTest);
	}
	
	public void purgeLabTestAttribute(LabTestAttribute labTestAttribute) {
		sessionFactory.getCurrentSession().delete(labTestAttribute);
	}
	
	public void purgeLabTestAttributeType(LabTestAttributeType labTestAttributeType) {
		sessionFactory.getCurrentSession().delete(labTestAttributeType);
	}
	
	public void purgeLabTestSample(LabTestSample labTestSample) {
		sessionFactory.getCurrentSession().delete(labTestSample);
	}
	
	public void purgeLabTestType(LabTestType labTestType) {
		sessionFactory.getCurrentSession().delete(labTestType);
	}
	
	public LabTest saveLabTest(LabTest labTest) {
		sessionFactory.getCurrentSession().save(labTest);
		return labTest;
	}
	
	public LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute) {
		sessionFactory.getCurrentSession().save(labTestAttribute);
		return labTestAttribute;
	}
	
	public LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) {
		sessionFactory.getCurrentSession().save(labTestAttributeType);
		return labTestAttributeType;
	}
	
	public LabTestSample saveLabTestSample(LabTestSample labTestSample) {
		sessionFactory.getCurrentSession().save(labTestSample);
		return labTestSample;
	}
	
	public LabTestType saveLabTestType(LabTestType labTestType) {
		sessionFactory.getCurrentSession().save(labTestType);
		return labTestType;
	}
	
	public LabTest getLabTest(Encounter orderEncounter) {
		// TODO
		return null;
	}
	
	public List<LabTestSample> getLabTestSamples(org.openmrs.Order order, boolean includeVoided) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<LabTestType> getAllLabTestTypes(String name, String shortName, LabTestGroup testGroup,
	        Concept referenceConcept, boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestType.class);
		if (name != null) {
			criteria.add(Restrictions.ilike("name", name));
		}
		if (shortName != null) {
			criteria.add(Restrictions.ilike("shortName", name));
		}
		if (testGroup != null) {
			criteria.add(Restrictions.eq("testGroup", testGroup));
		}
		if (referenceConcept != null) {
			criteria.add(Restrictions.ilike("referenceConcept", referenceConcept));
		}
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}
		criteria.addOrder(Order.asc("name")).addOrder(Order.asc("retired")).list();
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<LabTest> getNLabTests(Patient patient, int n, boolean firstNObjects, boolean lastNObjects,
	        boolean includeVoided) {
		// Disallow fetching more than 100 records per query
		if (n > MAX_FETCH_LIMIT) {
			n = MAX_FETCH_LIMIT;
		}
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class);
		List<LabTest> firstN = null;
		List<LabTest> lastN = null;
		if (patient != null) {
			criteria.add(Restrictions.eq("order.patient", patient));
		}
		if (!includeVoided) {
			criteria.add(Restrictions.eq("voided", false));
		}
		if (firstNObjects) {
			criteria.addOrder(Order.asc("dateCreated"));
			firstN = criteria.list();
		}
		if (lastNObjects) {
			criteria.addOrder(Order.desc("dateCreated"));
			lastN = criteria.list();
		}
		criteria.setMaxResults(n);
		List<LabTest> list = new ArrayList<LabTest>();
		if (firstN != null) {
			list.addAll(firstN);
		}
		if (lastN != null) {
			list.addAll(lastN);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<LabTestSample> getNLabTestSamples(Patient patient, int n, boolean firstNObjects, boolean lastNObjects,
	        boolean includeVoided) {
		// Disallow fetching more than 100 records per query
		if (n > MAX_FETCH_LIMIT) {
			n = MAX_FETCH_LIMIT;
		}
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestSample.class);
		List<LabTestSample> firstN = null;
		List<LabTestSample> lastN = null;
		if (patient != null) {
			criteria.add(Restrictions.eq("labTest.order.patient", patient));
		}
		if (!includeVoided) {
			criteria.add(Restrictions.eq("voided", false));
		}
		if (firstNObjects) {
			criteria.addOrder(Order.asc("dateCreated"));
			firstN = criteria.list();
		}
		if (lastNObjects) {
			criteria.addOrder(Order.desc("dateCreated"));
			lastN = criteria.list();
		}
		criteria.setMaxResults(n);
		List<LabTestSample> list = new ArrayList<LabTestSample>();
		if (firstN != null) {
			list.addAll(firstN);
		}
		if (lastN != null) {
			list.addAll(lastN);
		}
		return list;
	}
}
