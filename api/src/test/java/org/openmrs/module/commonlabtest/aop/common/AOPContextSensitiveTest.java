/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.commonlabtest.aop.common;

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.aop.Advice;
import org.junit.After;
import org.junit.Before;
import org.openmrs.api.OpenmrsService;
import org.openmrs.api.context.Context;
import org.openmrs.module.AdvicePoint;
import org.openmrs.module.commonlabtest.CommonLabTestBase;
import org.springframework.aop.AfterReturningAdvice;

/**
 * @author tahira.niazi@ihsinformatics.com
 */
public abstract class AOPContextSensitiveTest extends CommonLabTestBase implements TestAOP {

	private Class<?> adviceClass;

	private AfterReturningAdvice afterAdvice;

	private Map<Class<?>, Advice> servicesMap = new HashMap<Class<?>, Advice>();

	@Before
	public void runBeforeEachTest() throws Exception {
		super.initTestData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openmrs.module.commonlabtest.aop.common.TestWithAOP#setAspect(java.lang.
	 * Class)
	 */
	@Override
	public void setAspect(Class<? extends AfterReturningAdvice> adviceClass) {
		this.adviceClass = adviceClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.module.commonlabtest.aop.common.TestWithAOP#setAspect(org.
	 * springframework.aop.AfterReturningAdvice)
	 */
	@Override
	public void setAspect(AfterReturningAdvice advice) {
		this.afterAdvice = advice;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openmrs.module.commonlabtest.aop.common.TestWithAOP#addService(java.lang.
	 * Class)
	 */
	@Override
	public void addService(Class<? extends OpenmrsService> serviceClass) {
		servicesMap.put(serviceClass, null);
	}

	abstract protected void setInterceptorAndServices(TestAOP testCase);

	@Before
	public void setupAOP() throws Exception {

		setInterceptorAndServices(this);

		for (Class<?> serviceClass : servicesMap.keySet()) {
			Advice advice;
			if (adviceClass != null) {
				advice = (Advice) (new AdvicePoint(serviceClass.getCanonicalName(),
				        Context.loadClass(adviceClass.getCanonicalName()))).getClassInstance();
			} else if (afterAdvice != null) {
				advice = afterAdvice;
			} else {
				throw new IllegalStateException("You must either set advice or advice class to setup the Context");
			}

			servicesMap.put(serviceClass, advice);
			Context.addAdvice(Context.loadClass(serviceClass.getCanonicalName()), advice);
		}
	}

	@After
	public void tearDownAOP() throws Exception {
		for (Class<?> serviceClass : servicesMap.keySet()) {
			Context.removeAdvice(Context.loadClass(serviceClass.getCanonicalName()), servicesMap.get(serviceClass));
		}
	}

}
