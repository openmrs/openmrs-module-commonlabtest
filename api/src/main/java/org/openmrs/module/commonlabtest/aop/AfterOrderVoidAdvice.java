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

import java.lang.reflect.Method;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.module.commonlabtest.LabTest;
import org.openmrs.module.commonlabtest.api.CommonLabTestService;
import org.springframework.aop.AfterReturningAdvice;

/**
 * @author tahira.niazi@ihsinformatics.com
 */

public class AfterOrderVoidAdvice implements AfterReturningAdvice {

	private Log log = LogFactory.getLog(this.getClass());

	/*
	 * (non-Javadoc) * @see
	 * org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang.
	 * Object, java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 * 
	 * depends on org.openmrs.api.impl.OrderServiceImpl
	 */
	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

		Logger.getAnonymousLogger().info("======== in After Advice " + method.getName() + "========");
		CommonLabTestService commonLabTestService;

		if (method.getName().equalsIgnoreCase("voidEncounter")) {
			if (returnValue != null) {

				Encounter encounter = (Encounter) returnValue;
				Set<Order> orders = (Set<Order>) encounter.getOrders();

				for (Order o : orders) {
					if (o.getVoided()) {
						Logger.getAnonymousLogger().info(" ==== > Order # " + o.getOrderId() + " Voided: " + o.getVoided());
						// void corresponding LabTest entity that has one to one mapping to this Order
						commonLabTestService = Context.getService(CommonLabTestService.class);
						LabTest labTest = commonLabTestService.getLabTest(o.getOrderId());
						if (labTest != null) {
							commonLabTestService.voidLabTest(labTest, o.getVoidReason());
							Logger.getAnonymousLogger().info(
							    " ==== > Lab Test # " + labTest.getTestOrderId() + " Voided: " + labTest.getVoided());
						}
					}
				}
			}

		} else if (method.getName().equalsIgnoreCase("unvoidEncounter")) {
			if (returnValue != null) {

				Encounter encounter = (Encounter) returnValue;
				Set<Order> orders = (Set<Order>) encounter.getOrders();

				for (Order o : orders) {
					if (!o.getVoided()) {
						Logger.getAnonymousLogger().info(" ==== > Order # " + o.getOrderId() + " Voided: " + o.getVoided());
						// unvoid corresponding LabTest entity that has one to one mapping to this Order
						commonLabTestService = Context.getService(CommonLabTestService.class);
						LabTest labTest = commonLabTestService.getLabTest(o.getOrderId());
						if (labTest != null) {
							commonLabTestService.unvoidLabTest(labTest);
							Logger.getAnonymousLogger().info(
							    " ==== > Lab Test # " + labTest.getTestOrderId() + " Voided: " + labTest.getVoided());
						}
					}
				}
			}
		}
	}
}
