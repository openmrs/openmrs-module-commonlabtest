
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include
	file="/WEB-INF/view/module/commonlabtest/include/localHeader.jsp"%>
<openmrs:require privilege="View CommonLabTest Metadata"
	redirect="/module/commonlabtest/manageLabTestTypes.form"
	otherwise="/login.htm" />

<link type="text/css" rel="stylesheet"
	href="/openmrs/moduleResources/commonlabtest/css/commonlabtest.css" />
<link
	href="/openmrs/moduleResources/commonlabtest/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" />
<link
	href="/openmrs/moduleResources/commonlabtest/css/bootstrap.min.css"
	rel="stylesheet" />

<link type="text/css" rel="stylesheet"
	href="/openmrs/moduleResources/commonlabtest/css/commonlabtest.css" />
<link
	href="/openmrs/moduleResources/commonlabtest/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" />
<link
	href="/openmrs/moduleResources/commonlabtest/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="/openmrs/moduleResources/commonlabtest/css/dataTables.bootstrap4.min.css"
	rel="stylesheet" />

<link type="text/css" rel="stylesheet"
	href="/openmrs/moduleResources/commonlabtest/css/hover.css" />
<link type="text/css" rel="stylesheet"
	href="/openmrs/moduleResources/commonlabtest/css/hover-min.css" />


<style>
body {
	font-size: 12px;
}

.btn {
	background-color: #1aac9b;
	border: none;
	color: white;
	padding: 12px 16px;
	font-size: 16px;
	cursor: pointer;
}

.btn:hover {
	background-color: #1aac9be6;
}

.table-striped tbody tr:nth-of-type(odd) {
	background-color: #E2E4FF;
}

.table-striped tbody tr:hover {
	background-color: #ddd;
}

.container {
	margin: 0 auto;
	max-width: 100%
}
</style>
<body>
	<div>
		<h2>
			<b><spring:message
					code="commonlabtest.labtestattributetype.manage" /></b>
		</h2>
	</div>
	<br>
	<c:if test="${not empty status}">
		<div class="alert alert-success" id="success-alert">
			<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>Success!</strong>
			<c:out value="${status}" />
		</div>
	</c:if>
	<openmrs:hasPrivilege privilege="Add CommonLabTest Metadata">
		<div>
			<a style="text-decoration: none" href="addLabTestAttributeType.form"
				class="hvr-icon-grow"><i class="fa fa-plus hvr-icon"></i> <spring:message
					code="commonlabtest.labtestattributetype.add" /> </a>
		</div>
	</openmrs:hasPrivilege>
	<br>
	<div class="boxHeader" style="background-color: #1aac9b">
		<span><i class="fa fa-list"></i> </span> <b><spring:message
				code="commonlabtest.labtestattributetype.list" /></b>
	</div>
	<div class="box">
		<table id="manageTestAttributeTypeTable"
			class="table table-striped table-bordered" style="width: 100%">
			<thead>
				<tr>
					<th hidden="true"></th>
					<openmrs:hasPrivilege privilege="Edit CommonLabTest Metadata">
						<th>Name</th>
					</openmrs:hasPrivilege>
					<th>Description</th>
					<th>Lab Test Type</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tt" items="${labTestAttributeTypes}">
					<tr>
						<td hidden="true" id="uuid">${tt.uuid}</td>
						<openmrs:hasPrivilege privilege="Edit CommonLabTest Metadata">
							<td><a style="text-decoration: none"
								href="${pageContext.request.contextPath}/module/commonlabtest/addLabTestAttributeType.form?uuid=${tt.uuid}"
								class="hvr-icon-grow"><span><i
										class="fa fa-edit hvr-icon"></i></span> ${tt.name}</a></td>
						</openmrs:hasPrivilege>
						<td>${tt.description}</td>
						<td>${tt.labTestType.name}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
<%@ include file="/WEB-INF/template/footer.jsp"%>

<!--JAVA SCRIPT  -->
<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/bootstrap/js/jquery-3.3.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/bootstrap/js/popper.min.js"></script>
<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/bootstrap/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/js/jquery-ui.min.js"></script>
<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/js/jquery.dataTables.min.js"></script>
<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/js/dataTables.bootstrap4.min.js"></script>


<script>
	$(document)
			.ready(
					function() {
						//status auto closs..		
						$("#success-alert").fadeTo(2000, 1000).slideUp(1000,
								function() {
									$("#success-alert").slideUp(1000);
								});

						$('#manageTestAttributeTypeTable').dataTable({
							"bPaginate" : true
						});
						$('.dataTables_length').addClass('bs-select');

						jQuery(function() {

							if (performance.navigation.type == 1) {
								window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/manageLabTestAttributeTypes.form";
							}

							jQuery("body")
									.keydown(
											function(e) {

												if (e.which == 116) {
													window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/manageLabTestAttributeTypes.form";
												}

											});
						});
					});
</script>