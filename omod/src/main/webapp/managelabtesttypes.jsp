<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include
	file="/WEB-INF/view/module/medicationlog/include/localheader.jsp"%>

<openmrs:htmlInclude
	file="/scripts/jquery/dataTables/css/dataTables_jui.css" />
<openmrs:htmlInclude
	file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery('#orderSetTable').dataTable({
			"bPaginate" : true,
			"bLengthChange" : false,
			"bFilter" : false,
			"bSort" : true,
			"bInfo" : false,
			"bAutoWidth" : false,
			"bSortable" : true,
			"aoColumns" : [ {
				"iDataSort" : 1
			}, {
				"sType" : "html"
			}, null ]
		});
	});
</script>

<h2>
	<spring:message code="commonlabtest.admin.managetesttypes" />
</h2>

<a href="managelabtesttypes.form"><spring:message
		code="commonlabtest.view.addtesttype" /></a>
<br />
<br />
<div>
	<spring:message code="commonlabtest.view.listtesttype" />
</div>
<div>
	<table id="labtests" style="width: 100%; padding: 5px;)">
		<thead>
			<tr>
				<th>Name</th>
				<th>Short Name</th>
				<th>Test Group</th>
				<th>Reference Concept</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tt" items="${testtypes}">
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