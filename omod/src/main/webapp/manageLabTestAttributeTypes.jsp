<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include
	file="/WEB-INF/view/module/medicationlog/include/localHeader.jsp"%>

<script type="text/javascript">
    jQuery(document).ready(function() {
    });
</script>

<h2>
	<spring:message code="commonlabtest.labtestattributetype.manage" />
</h2>

<a href="addLabTestAttributeType.form"><spring:message
		code="commonlabtest.view.addLabTestAttributeType" /></a>
<br />

<div>
	<spring:message code="commonlabtest.view.listLabTestAttributeTypes" />
</div>

<div>
	<table id="labTestAttributeTypeTable"
		style="width: 100%; padding: 5px;)">
		<thead>
			<tr>
				<th>Attribute Type:</th>
				<th>Datatype:</th>
				<th>Description:</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${labTestAttributeTypes}" var="at">
				<tr>
					<td>${at.name}</td>
					<td>${at.datatype}</td>
					<td>${at.description}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>


<%@ include file="/WEB-INF/template/footer.jsp"%>