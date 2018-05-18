<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<script type="text/javascript">
    $j(document).ready(function() {
        $j('#labTestTypeList').dataTable({
            "bPaginate": false,
            "bLengthChange": false,
            "bFilter": false,
            "bSort": true,
            "bInfo": false,
            "bAutoWidth": true,
            "bSortable": true
        });
    });
</script>

<h2>
	<spring:message code="commonlabtest.title" />
</h2>

<br/>

<div class="box">
	<a href="addLabTestType"><spring:message code="commonlabtest.labtesttype.add"/></a>
	<br/><br/>
	<table cellpadding="2" cellspacing="1" id="labTestTypeList" class="dataTable" style="white-space:nowrap;">
	    <thead>
		    <tr>
		        <th>Test Type</th>
		        <th>Short Name</th>
		        <th>Group</th>
		        <th>Reference Concept ID</th>
		        <th style="width:100%;">Description</th>
		    </tr>
	    </thead>
	    <tbody>
		    <c:forEach items="${labTestTypes}" var="type">
		        <tr>
	                <td>${type.name}</td>
	                <td>${type.shortName}</td>
	                <td>${type.testGroup}</td>
	                <td>${type.referenceConcept.conceptId}</td>
	                <td>${form.description}</td>
		        </tr>
		    </c:forEach>
	    </tbody>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>
