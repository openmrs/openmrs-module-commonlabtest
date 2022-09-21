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
package org.openmrs.module.commonlabtest.api.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestAttributeType;
import org.openmrs.module.commonlabtest.LabTestGroup;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.LabTestSampleStatus;
import org.openmrs.module.commonlabtest.LabTestType;
import org.openmrs.module.commonlabtest.api.dao.CommonLabTestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author owais.hussain@ihsinformatics.com
 */
@Component
public class CommonLabTestDAOImpl implements CommonLabTestDAO {

	private static final int MAX_FETCH_LIMIT = 100;

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @see CommonLabTestDAO#getAllLabTestAttributeTypes(boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LabTestAttributeType> getAllLabTestAttributeTypes(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestAttributeType.class);
		criteria.addOrder(Order.asc("name"));
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}
		return criteria.list();
	}

	/**
	 * @see CommonLabTestDAO#getAllLabTestTypes(boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LabTestType> getAllLabTestTypes(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestType.class);
		criteria.addOrder(Order.asc("name"));
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}
		return criteria.list();
	}

	/**
	 * @see CommonLabTestDAO#getLabTestTypes(java.lang.String, java.lang.String,
	 *      org.openmrs.module.commonlabtest.LabTestType.LabTestGroup, org.openmrs.Concept, boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LabTestType> getLabTestTypes(String name, String shortName, LabTestGroup testGroup, Concept referenceConcept,
	        boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestType.class);
		if (name != null) {
			criteria.add(Restrictions.ilike("name", name, MatchMode.START));
		}
		if (shortName != null) {
			criteria.add(Restrictions.ilike("shortName", name, MatchMode.START));
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

	/**
	 * @see CommonLabTestDAO#getLabTest(org.openmrs.Order)
	 */
	@Override
	public LabTest getLabTest(org.openmrs.Order order) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class);
		criteria.add(Restrictions.eq("testOrderId", order.getId()));
		return (LabTest) criteria.uniqueResult();
	}

	/**
	 * @see CommonLabTestDAO#getLabTest(java.lang.Integer)
	 */
	@Override
	public LabTest getLabTest(Integer labTestId) {
		return (LabTest) sessionFactory.getCurrentSession().get(LabTest.class, labTestId);
	}

	/**
	 * @see CommonLabTestDAO#getLabTestAttribute(java.lang.Integer)
	 */
	@Override
	public LabTestAttribute getLabTestAttribute(Integer labTestAttributeId) {
		return (LabTestAttribute) sessionFactory.getCurrentSession().get(LabTestAttribute.class, labTestAttributeId);
	}

	/**
	 * @see CommonLabTestDAO#getLabTestAttributeByUuid(java.lang.String)
	 */
	@Override
	public LabTestAttribute getLabTestAttributeByUuid(String uuid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestAttribute.class);
		criteria.add(Restrictions.eq("uuid", uuid.toLowerCase()));
		return (LabTestAttribute) criteria.uniqueResult();
	}

	/**
	 * @see CommonLabTestDAO#getLabTestAttributes(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LabTestAttribute> getLabTestAttributes(Integer testOrderId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestAttribute.class);
		criteria.add(Restrictions.eq("labTest.testOrderId", testOrderId));
		return criteria.list();
	}

	/**
	 * @see CommonLabTestDAO#getLabTestAttributes(org.openmrs.module.commonlabtest.LabTestAttributeType,
	 *      java.lang.String, java.util.Date, java.util.Date, boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LabTestAttribute> getLabTestAttributes(LabTestAttributeType labTestAttributeType, String valueReference,
	        Date from, Date to, boolean includeVoided) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestAttribute.class);
		if (labTestAttributeType != null) {
			criteria.add(Restrictions.or(Restrictions.isNull("attributeType.labTestAttributeTypeId"),
			    Restrictions.eq("attributeType.labTestAttributeTypeId", labTestAttributeType.getId())));
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
		criteria.addOrder(Order.asc("labTestAttributeId")).addOrder(Order.asc("voided"));
		return criteria.list();
	}

	/**
	 * @see CommonLabTestDAO#getLabTestAttributes(Patient, LabTestAttributeType, boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LabTestAttribute> getLabTestAttributes(Patient patient, LabTestAttributeType labTestAttributeType,
	        boolean includeVoided) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("from LabTestAttribute lta where lta.labTest.order.patient.patientId = :patientId");
		queryString.append(labTestAttributeType == null ? ""
		        : " and lta.labTestAttributeType.labTestAttributeTypeId = :labTestAttributeType");
		queryString.append(includeVoided ? "" : " and lta.voided = :voided");
		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		query.setInteger("patientId", patient.getPatientId());
		if (labTestAttributeType != null) {
			query.setInteger("labTestAttributeTypeId", labTestAttributeType.getId());
		}
		if (!includeVoided) {
			query.setBoolean("voided", false);
		}
		return query.list();
	}

	/**
	 * @see CommonLabTestDAO#getLabTestAttributeType(java.lang.Integer)
	 */
	@Override
	public LabTestAttributeType getLabTestAttributeType(Integer labTestAttributeTypeId) {
		return (LabTestAttributeType) sessionFactory.getCurrentSession().get(LabTestAttributeType.class,
		    labTestAttributeTypeId);
	}

	/**
	 * @see CommonLabTestDAO#getLabTestAttributeTypeByUuid(java.lang.String)
	 */
	@Override
	public LabTestAttributeType getLabTestAttributeTypeByUuid(String uuid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestAttributeType.class);
		criteria.add(Restrictions.eq("uuid", uuid.toLowerCase()));
		return (LabTestAttributeType) criteria.uniqueResult();
	}

	/**
	 * @see CommonLabTestDAO#getLabTestAttributeTypes(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LabTestAttributeType> getLabTestAttributeTypes(String name, String datatypeClassname,
	        boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestAttributeType.class);
		if (name != null) {
			criteria.add(Restrictions.ilike("name", name, MatchMode.START));
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

	/**
	 * @see CommonLabTestDAO#getLabTestAttributeTypes(org.openmrs.module.commonlabtest.LabTestType,
	 *      boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LabTestAttributeType> getLabTestAttributeTypes(LabTestType labTestType, boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestAttributeType.class);
		criteria.add(Restrictions.eq("labTestType", labTestType));
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}
		criteria.addOrder(Order.asc("sortWeight")).addOrder(Order.asc("retired")).list();
		return criteria.list();
	}

	/**
	 * @see CommonLabTestDAO#getLabTestByUuid(java.lang.String)
	 */
	@Override
	public LabTest getLabTestByUuid(String uuid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class);
		criteria.add(Restrictions.eq("uuid", uuid.toLowerCase()));
		return (LabTest) criteria.uniqueResult();
	}

	/**
	 * @see CommonLabTestDAO#getLabTests(org.openmrs.module.commonlabtest.LabTestType,
	 *      org.openmrs.Patient, java.lang.String, java.lang.String, org.openmrs.Concept,
	 *      org.openmrs.Provider, java.util.Date, java.util.Date, boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LabTest> getLabTests(LabTestType labTestType, Patient patient, String orderNumber, String referenceNumber,
	        Concept orderConcept, Provider orderer, Date from, Date to, boolean includeVoided) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class);
		if (labTestType != null) {
			criteria.add(Restrictions.eq("labTestType", labTestType));
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
		criteria.addOrder(Order.asc("dateCreated")).addOrder(Order.asc("voided")).list();
		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LabTest> getLabTests(LabTestType labTestType, Patient patient, Date from, Date to, boolean includeVoided) {
		if (patient == null) {
			return null;
		}
		List<org.openmrs.Order> ordersByPatient = Context.getOrderService().getOrdersByPatient(patient);
		if (ordersByPatient.size() == 0) {
			return new ArrayList<LabTest>();
		}
		List<Integer> orderIds = new ArrayList<Integer>();
		for (org.openmrs.Order order : ordersByPatient) {
			orderIds.add(order.getOrderId());
		}
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class);
		criteria.add(Restrictions.in("testOrderId", orderIds));
		if (labTestType != null) {
			criteria.add(Restrictions.eq("labTestType", labTestType));
		}
		if (from != null && to != null) {
			criteria.add(Restrictions.between("dateCreated", from, to));
		}
		if (!includeVoided) {
			criteria.add(Restrictions.eq("voided", false));
		}
		// criteria.addOrder(Order.asc("dateCreated")).addOrder(Order.asc("voided")).list();
		return criteria.list();
	}

	/**
	 * @see CommonLabTestDAO#getLabTestSample(java.lang.Integer)
	 */
	@Override
	public LabTestSample getLabTestSample(Integer labTestSampleId) {
		return (LabTestSample) sessionFactory.getCurrentSession().get(LabTestSample.class, labTestSampleId);
	}

	/**
	 * @see CommonLabTestDAO#getLabTestSampleByUuid(java.lang.String)
	 */
	@Override
	public LabTestSample getLabTestSampleByUuid(String uuid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestSample.class);
		criteria.add(Restrictions.eq("uuid", uuid.toLowerCase()));
		return (LabTestSample) criteria.uniqueResult();
	}

	/**
	 * @see CommonLabTestDAO#getLabTestSamples(org.openmrs.module.commonlabtest.LabTest, boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LabTestSample> getLabTestSamples(LabTest labTest, boolean includeVoided) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestSample.class);
		criteria.add(Restrictions.eq("labTest.testOrderId", labTest.getId()));
		if (!includeVoided) {
			criteria.add(Restrictions.eq("voided", false));
		}
		criteria.addOrder(Order.asc("dateCreated")).list();
		return criteria.list();
	}

	/**
	 * @see CommonLabTestDAO#getLabTestSamples(org.openmrs.Patient, boolean)
	 */
	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<LabTestSample> getLabTestSamples(Patient patient, boolean includeVoided) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestSample.class);

		criteria.createAlias("labTest", "labTest", CriteriaSpecification.INNER_JOIN).setFetchMode("labTest", FetchMode.JOIN)
		        // .add(Restrictions.eq("labTest.order.patient.personId",
		        // patient.getPatientId()))
		        .createAlias("labTest.order", "order", CriteriaSpecification.INNER_JOIN)
		        .setFetchMode("order", FetchMode.JOIN)
		        .add(Restrictions.eq("order.patient.personId", patient.getPatientId()));
		// .createAlias("labTest", "labTest",
		// CriteriaSpecification.INNER_JOIN).setFetchMode("labTest", FetchMode.JOIN);
		// criteria.add(Restrictions.eq("order.patient.patientId",
		// patient.getPatientId()));
		if (!includeVoided) {
			criteria.add(Restrictions.eq("voided", false));
		}
		criteria.addOrder(Order.asc("sampleIdentifier")).addOrder(Order.asc("voided")).list();
		return criteria.list();
	}

	/**
	 * @see CommonLabTestDAO#getLabTestSamples(org.openmrs.Patient, boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LabTestSample> getLabTestSamples(Provider collector, boolean includeVoided) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestSample.class);
		criteria.add(Restrictions.eq("collector.providerId", collector.getProviderId()));
		if (!includeVoided) {
			criteria.add(Restrictions.eq("voided", false));
		}
		criteria.addOrder(Order.asc("sampleIdentifier")).addOrder(Order.asc("voided")).list();
		return criteria.list();
	}

	/**
	 * @see CommonLabTestDAO#getLabTestType(java.lang.Integer)
	 */
	@Override
	public LabTestType getLabTestType(Integer labTestTypeId) {
		return (LabTestType) sessionFactory.getCurrentSession().get(LabTestType.class, labTestTypeId);
	}

	/**
	 * @see CommonLabTestDAO#getLabTestTypeByUuid(java.lang.String)
	 */
	@Override
	public LabTestType getLabTestTypeByUuid(String uuid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestType.class);
		criteria.add(Restrictions.eq("uuid", uuid.toLowerCase()));
		return (LabTestType) criteria.uniqueResult();
	}

	/**
	 * @see CommonLabTestDAO#getNLabTests(org.openmrs.Patient, int, boolean, boolean, boolean)
	 */
	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
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
			criteria.createAlias("order", "o", CriteriaSpecification.INNER_JOIN).setFetchMode("o", FetchMode.JOIN)
			        .add(Restrictions.eq("o.patient.personId", patient.getPatientId()));
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

	/**
	 * @see CommonLabTestDAO#getNLabTestSamples(org.openmrs.Patient,
	 *      org.openmrs.module.commonlabtest.LabTestSample.LabTestSampleStatus, int, boolean, boolean,
	 *      boolean)
	 */
	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<LabTestSample> getNLabTestSamples(Patient patient, LabTestSampleStatus status, int n, boolean firstNObjects,
	        boolean lastNObjects, boolean includeVoided) {
		// Disallow fetching more than 100 records per query
		if (n > MAX_FETCH_LIMIT) {
			n = MAX_FETCH_LIMIT;
		}
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestSample.class);
		List<LabTestSample> firstN = null;
		List<LabTestSample> lastN = null;
		criteria.createAlias("labTest", "labTest", CriteriaSpecification.INNER_JOIN).setFetchMode("labTest", FetchMode.JOIN)
		        .createAlias("labTest.order", "order", CriteriaSpecification.INNER_JOIN)
		        .setFetchMode("order", FetchMode.JOIN)
		        .add(Restrictions.eq("order.patient.personId", patient.getPatientId()));
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
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

	/**
	 * @see CommonLabTestDAO#purgeLabTest(org.openmrs.module.commonlabtest.LabTest)
	 */
	@Override
	public void purgeLabTest(LabTest labTest) {
		sessionFactory.getCurrentSession().delete(labTest);
	}

	/**
	 * @see CommonLabTestDAO#purgeLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)
	 */
	@Override
	public void purgeLabTestAttribute(LabTestAttribute labTestAttribute) {
		sessionFactory.getCurrentSession().delete(labTestAttribute);
	}

	/**
	 * @see CommonLabTestDAO#purgeLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)
	 */
	@Override
	public void purgeLabTestAttributeType(LabTestAttributeType labTestAttributeType) {
		sessionFactory.getCurrentSession().delete(labTestAttributeType);
	}

	/**
	 * @see CommonLabTestDAO#purgeLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)
	 */
	@Override
	public void purgeLabTestSample(LabTestSample labTestSample) {
		sessionFactory.getCurrentSession().delete(labTestSample);
	}

	/**
	 * @see CommonLabTestDAO#purgeLabTestType(org.openmrs.module.commonlabtest.LabTestType)
	 */
	@Override
	public void purgeLabTestType(LabTestType labTestType) {
		sessionFactory.getCurrentSession().delete(labTestType);
	}

	/**
	 * Detects whether it's a new order or existing one. In case the order already exits, it is NOT
	 * overridden because Order objects are immutable
	 * 
	 * @param order the {@link org.openmrs.Order} object to save
	 * @return saved {@link org.openmrs.Order} object
	 */
	public org.openmrs.Order saveLabTestOrder(org.openmrs.Order order) {
		OrderType expectedOrderType = order.getOrderType();
		// Set the right order type
		List<OrderType> orderTypes = Context.getOrderService().getAllOrderTypes();
		for (OrderType orderType : orderTypes) {
			if (orderType.getName().equalsIgnoreCase("Lab Order"))
				;
			order.setOrderType(orderType);
		}
		boolean createNew = order.getId() == null;
		if (!createNew) {
			// See if the given ID actually exists or not
			createNew = Context.getOrderService().getOrder(order.getId()) == null;
		}
		if (createNew) {
			order.setId(null);
			return Context.getOrderService().saveOrder(order);
		}
		// Do nothing
		return order;
	}

	/**
	 * @see CommonLabTestDAO#saveLabTest(org.openmrs.module.commonlabtest.LabTest)
	 */
	@Override
	public LabTest saveLabTest(LabTest labTest) {
		org.openmrs.Order savedOrder = saveLabTestOrder(labTest.getOrder());
		labTest.setOrder(savedOrder);
		labTest.setTestOrderId(savedOrder.getOrderId());
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(labTest);
		return labTest;
	}

	/**
	 * @see CommonLabTestDAO#saveLabTestAttribute(org.openmrs.module.commonlabtest.LabTestAttribute)
	 */
	@Override
	public LabTestAttribute saveLabTestAttribute(LabTestAttribute labTestAttribute) {

		sessionFactory.getCurrentSession().saveOrUpdate(labTestAttribute);
		return labTestAttribute;
	}

	/**
	 * @see CommonLabTestDAO#saveLabTestAttributeType(org.openmrs.module.commonlabtest.LabTestAttributeType)
	 */
	@Override
	public LabTestAttributeType saveLabTestAttributeType(LabTestAttributeType labTestAttributeType) {
		sessionFactory.getCurrentSession().saveOrUpdate(labTestAttributeType);
		return labTestAttributeType;
	}

	/**
	 * @see CommonLabTestDAO#saveLabTestSample(org.openmrs.module.commonlabtest.LabTestSample)
	 */
	@Override
	public LabTestSample saveLabTestSample(LabTestSample labTestSample) {
		sessionFactory.getCurrentSession().saveOrUpdate(labTestSample);
		return labTestSample;
	}

	/**
	 * @see CommonLabTestDAO#saveLabTestType(org.openmrs.module.commonlabtest.LabTestType)
	 */
	@Override
	public LabTestType saveLabTestType(LabTestType labTestType) {
		sessionFactory.getCurrentSession().saveOrUpdate(labTestType);
		return labTestType;
	}
}
