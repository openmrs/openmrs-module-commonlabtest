/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.commonlabtest.aop;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.api.EncounterService;
import org.openmrs.api.OrderService;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.LabTestAttribute;
import org.openmrs.module.commonlabtest.LabTestSample;
import org.openmrs.module.commonlabtest.aop.common.AOPContextSensitiveTest;
import org.openmrs.module.commonlabtest.aop.common.TestAOP;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;

/**
 * @author tahira.niazi@ihsinformatics.com
 */
public class OrderVoidAdvisorTest extends AOPContextSensitiveTest {

	private EncounterService encounterService;

	private OrderService orderService;

	private CommonLabTestService commonLabTestService;

	private static final int DEMO_ENCOUNTER_ID = 1000;

	private static final int DEMO_ORDER1_ID = 100;

	private static final int DEMO_ORDER2_ID = 200;

	private static final int DEMO_LAB_TEST_ID = 100;

	private static final int DEMO_LAB_TEST_SAMPLE_ID = 1;

	private static final int DEMO_LAB_TEST_ATTRIBUTE1_ID = 1;

	private static final int DEMO_LAB_TEST_ATTRIBUTE2_ID = 2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.module.commonlabtest.aop.AOPContextSensitiveTest#
	 * setInterceptorAndServices(org.openmrs.module.commonlabtest.aop.TestWithAOP)
	 */
	@Override
	protected void setInterceptorAndServices(TestAOP testCase) {
		testCase.setAspect(AfterOrderVoidAdvice.class);
		testCase.addService(EncounterService.class);
	}

	@Before
	public void setUp() throws Exception {
		encounterService = Context.getEncounterService();
		orderService = Context.getOrderService();
		commonLabTestService = Context.getService(CommonLabTestService.class);

	}

	@Test
	public void voidEncounter_voidingLinkedOrderShouldVoidLabTest() {

		Encounter encounter = encounterService.getEncounter(DEMO_ENCOUNTER_ID);
		Order order1 = orderService.getOrder(DEMO_ORDER1_ID);
		Order order2 = orderService.getOrder(DEMO_ORDER2_ID);
		LabTest labTest = commonLabTestService.getLabTest(DEMO_LAB_TEST_ID);
		LabTestSample labTestSample = commonLabTestService.getLabTestSample(DEMO_LAB_TEST_SAMPLE_ID);
		LabTestAttribute labTestAttribute1 = commonLabTestService.getLabTestAttribute(DEMO_LAB_TEST_ATTRIBUTE1_ID);
		LabTestAttribute labTestAttribute2 = commonLabTestService.getLabTestAttribute(DEMO_LAB_TEST_ATTRIBUTE2_ID);

		Encounter encounter1 = encounterService.voidEncounter(encounter, "Testing AOP");
		assertNotNull(encounter1);

		assertTrue(order1.getVoided());

		assertTrue(order2.getVoided());

		assertTrue(labTest.getVoided());

		assertTrue(labTestSample.getVoided());

		assertTrue(labTestAttribute1.getVoided());

		assertTrue(labTestAttribute2.getVoided());

	}

	@Test
	public void unvoidEncounter_unvoidingLinkedOrderShouldUnVoidLabTest() {

		Encounter encounter = encounterService.getEncounter(DEMO_ENCOUNTER_ID);
		Order order1 = orderService.getOrder(DEMO_ORDER1_ID);
		Order order2 = orderService.getOrder(DEMO_ORDER2_ID);
		LabTest labTest = commonLabTestService.getLabTest(DEMO_LAB_TEST_ID);
		LabTestSample labTestSample = commonLabTestService.getLabTestSample(DEMO_LAB_TEST_SAMPLE_ID);
		LabTestAttribute labTestAttribute1 = commonLabTestService.getLabTestAttribute(DEMO_LAB_TEST_ATTRIBUTE1_ID);
		LabTestAttribute labTestAttribute2 = commonLabTestService.getLabTestAttribute(DEMO_LAB_TEST_ATTRIBUTE2_ID);

		Encounter encounter1 = encounterService.unvoidEncounter(encounter);
		assertNotNull(encounter1);

		assertFalse(order1.getVoided());

		assertFalse(order2.getVoided());

		assertFalse(labTest.getVoided());

		assertFalse(labTestSample.getVoided());

		assertFalse(labTestAttribute1.getVoided());

		assertFalse(labTestAttribute2.getVoided());

	}

}
