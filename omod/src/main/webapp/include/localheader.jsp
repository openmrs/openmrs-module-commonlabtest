<%--
  The contents of this file are subject to the OpenMRS Public License
  Version 1.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://license.openmrs.org
  Software distributed under the License is distributed on an "AS IS"
  basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
  License for the specific language governing rights and limitations
  under the License.
  Copyright (C) OpenMRS, LLC.  All Rights Reserved.
  --%>

<spring:htmlEscape defaultHtmlEscape="true" />
<ul id="menu">
	<li class="first"><a
		href="${pageContext.request.contextPath}/admin"><spring:message
				code="admin.title.short" /></a>
	</li>
	<li
		<c:if test='<%=request.getRequestURI().contains("/commonlabtest")%>'>class="active"</c:if>>
		<a href="${pageContext.request.contextPath}/module/commonlabtest/commonlabtest.form"><spring:message
				code="commonlabtest.general.about" /></a>
	</li>
	<li
		<c:if test='<%=request.getRequestURI().contains("/managetesttypes")%>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/commonlabtest/managetesttypes.form"><spring:message
				code="commonlabtest.admin.managetesttypes" /></a>
	</li>
	<li
		<c:if test='<%=request.getRequestURI().contains("/managetestattributetypes")%>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/commonlabtest/managetestattributetypes.form"><spring:message
				code="commonlabtest.admin.managetestattributetypes" /></a>
	</li>
	<li
		<c:if test='<%=request.getRequestURI().contains("/addlabtest")%>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/commonlabtest/addlabtest.form"><spring:message
				code="commonlabtest.admin.addlabtest" /></a>
	</li>
	<li
		<c:if test='<%=request.getRequestURI().contains("/addlabtestsample")%>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/commonlabtest/addlabtestsample.form"><spring:message
				code="commonlabtest.admin.addlabtestsample" /></a>
	</li>
	<li
		<c:if test='<%=request.getRequestURI().contains("/managelabtestsamples")%>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/commonlabtest/managelabtestsamples.form"><spring:message
				code="commonlabtest.admin.managelabtestsamples" /></a>
	</li>
	<li
		<c:if test='<%=request.getRequestURI().contains("/managelabtests")%>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/commonlabtest/managelabtests.form"><spring:message
				code="commonlabtest.admin.managelabtests" /></a>
	</li>

	<!-- Add further links here -->
</ul>