<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include
	file="/WEB-INF/view/module/medicationlog/include/localHeader.jsp"%>

<h2>
	<spring:message code="commonlabtest.labtesttypes.manage" />
</h2>

<a href="manageLabTestTypes.form"><spring:message
		code="commonlabtest.labtesttype.add" /></a>
<br/>
<div>
	<spring:message code="commonlabtest.labtesttype.list" />
</div>
<div>
	<table id="labtesttypess" style="width: 100%; padding: 5px;)">
		<thead>
			<tr>
				<th>Name</th>
				<th>Short Name</th>
				<th>Test Group</th>
				<th>Reference Concept</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tt" items="${labTestTypes}">
				<tr>
					<td>${tt.name}</td>
					<td>${tt.shortName}</td>
					<td>${tt.testGroup}</td>
					<td>${tt.referenceConcept.name}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>