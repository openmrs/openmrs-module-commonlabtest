<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="/WEB-INF/view/module/commonlabtest/include/localHeader.jsp"%>
<openmrs:require privilege="View CommonLabTest Metadata" redirect="/module/commonlabtest/manageLabTestTypes.form" otherwise="/login.htm" />

<link type="text/css" rel="stylesheet" href="/openmrs/moduleResources/commonlabtest/css/commonlabtest.css" />
<link   href="/openmrs/moduleResources/commonlabtest/font-awesome/css/font-awesome.min.css" rel="stylesheet" /> 
<link   href="/openmrs/moduleResources/commonlabtest/css/bootstrap.min.css" rel="stylesheet" />
<link
	href="/openmrs/moduleResources/commonlabtest/css/dataTables.bootstrap4.min.css"
	rel="stylesheet" />
<link type="text/css" rel="stylesheet"
	href="/openmrs/moduleResources/commonlabtest/css/hover.css" />
<link type="text/css" rel="stylesheet"
	href="/openmrs/moduleResources/commonlabtest/css/hover-min.css" />
<link type="text/css" rel="stylesheet"
	href="/openmrs/moduleResources/commonlabtest/css/mdb.css" />
<link type="text/css" rel="stylesheet"
	href="/openmrs/moduleResources/commonlabtest/css/mdb.min.css" />	
	
<style>
body {
	font-size: 12px;
}
input[type=submit] {
	background-color: #1aac9b;
	color: white;
	padding: 8px 22px;
	border: none;
	border-radius: 2px;
	cursor: pointer;
	
}
fieldset.scheduler-border {
    border: 1px groove #ddd !important;
    padding: 0 1.4em 1.4em 1.4em !important;
    margin: 0 0 1.5em 0 !important;
    -webkit-box-shadow:  0px 0px 0px 0px #1aac9b;
            box-shadow:  0px 0px 0px 0px #1aac9b;
}

legend.scheduler-border {
        font-size: 1.2em !important;
        font-weight: bold !important;
        text-align: left !important;
        width:auto;
        padding:0 10px;
        border-bottom:none;
    }
.row{
 margin-bottom:15px;
 
 }
 .modal-body{
 	    height: 500px;
		overflow-y:scroll;
}
 
table.display tbody tr:nth-child(even):hover td{
    background-color: #1aac9b !important;
}

</style>

	<!-- Heading -->
	<div>
		<h2>
			<b><spring:message code="commonlabtest.labtesttype.manage" /></b>
		</h2>
	</div>
	<br>
	<c:if test="${not empty status}">
		<div class="alert alert-success" id="success-alert">
			 <a href="#" class="close" data-dismiss="alert">&times;</a>
	 		<strong>Success!</strong> <c:out value="${status}" />
		</div>
	</c:if>
	<div>
	<openmrs:hasPrivilege privilege="Add CommonLabTest Metadata">
	   <a style="text-decoration:none" href="addLabTestType.form" class="hvr-icon-grow" ><i class="fa fa-plus hvr-icon"></i> <spring:message code="commonlabtest.labtesttype.add" /> </a>
	</openmrs:hasPrivilege>
	</div>
	<br>
	<div class="boxHeader" style="background-color: #1aac9b">
			<span><i class="fa fa-list"></i> </span> <b><spring:message code="commonlabtest.labtesttype.list" /></b>
	</div>
	 <div class="box">
		 <table id="manageTestTypeTable" class="table table-striped table-bordered" style="width:100%">
	        <thead>
	            <tr>
	            	<th hidden="true"></th>
	            	<th hidden="true"></th>
	            	<openmrs:hasPrivilege privilege="Edit CommonLabTest Metadata">
					  <th>Name</th>
					</openmrs:hasPrivilege>
					<th>Short Name</th>
					<th>Test Group</th>
					<th>Reference Concept</th>
					<th>Linked Attribute Types</th>
				</tr>
	        </thead>
	        <tbody>
		       <c:forEach var="tt" items="${labTestTypes}">
						<tr>
							<td hidden="true" class="uuid">${tt.uuid}</td>
							<td hidden="true" class="testTypeId">${tt.labTestTypeId}</td>
							<openmrs:hasPrivilege privilege="Edit CommonLabTest Metadata">
							   <td><a style="text-decoration:none" href="${pageContext.request.contextPath}/module/commonlabtest/addLabTestType.form?uuid=${tt.uuid}" class="hvr-icon-grow"><span><i class="fa fa-edit hvr-icon"></i></span> ${tt.name}</a></td>
							</openmrs:hasPrivilege>
							<td>${tt.shortName}</td>
							<td>${tt.testGroup}</td>
							<td>${tt.referenceConcept.name}</td>
						    <td> <span class="table-edit hvr-icon-grow" onclick="editTestOrder(this)"><i class="fa fa-eye fa-2x hvr-icon"></i></span></td>
						</tr>
					</c:forEach>
	        </tbody>
	    </table>
	 </div>
		<!-- Full Height Modal Right -->
	<div class="modal fade left modal-scrolling" id="sortWeightModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="false" style="display: none;" aria-hidden="true">
    <div class="modal-dialog modal-side modal-top-left modal-notify modal-info" role="document">
	      <div class="modal-content">
	        <div class="modal-header" style="background-color:#1aac9b">
	          <p class="heading lead white-text">Sort Weight Order</p>
		          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	            <span aria-hidden="true" class="white-text">×</span>
	          </button>
       		</div>
	        <div class="modal-body" >
	          <div id="sortweightList"></div>
	        </div>
	      </div>
	    </div>
	  </div>
			
			

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
	function editTestOrder(ele){
		 var testTypeId = $(ele).closest("tr")  
							         .find(".testTypeId")   
							         .text(); 
		 if(testTypeId != ""){
			    getTestAttributeType(testTypeId);
		  }
		 else{
			 
		 }
	}

$(document).ready(function() {
	//status auto closs..		
	   $("#success-alert").fadeTo(2000, 500).slideUp(500, function(){
            $("#success-alert").slideUp(500);
             }); 
	
	$('#manageTestTypeTable').dataTable({
		 "bPaginate": true
	  });
	  $('.dataTables_length').addClass('bs-select');

});


function getTestAttributeType(testTypeId){
	console.log("Type : "+testTypeId);
	 $.ajax({
			type : "GET",
			contentType : "application/json",
			url : '${pageContext.request.contextPath}/module/commonlabtest/getTestAttributeTypeSortWeight.form?testTypeId='+testTypeId,
			async:false,
			dataType : "json",
			success : function(data) {
			   console.log("success  : " + data);
			   renderSortWeight(data.sortweightlist);
			},
			error : function(data) {
				  console.log("fail  : " + data);
			},
			done : function(e) {
				console.log("DONE");
			}
	});
	
}
function renderSortWeight(array){
	  var resultsItems = "";
				resultsItems = resultsItems.concat('<table  class="table table-striped table-responsive-md btn-table table-hover mb-0" id="tb-test-type">');
				resultsItems = resultsItems.concat('<thead><tr>');
					resultsItems = resultsItems.concat('<th><a>Test Type</a></th>');
					resultsItems = resultsItems.concat('<th><a>Attribute Type Name</a></th>');
					resultsItems = resultsItems.concat('<th><a>Sort Weight</a></th>');
					resultsItems = resultsItems.concat('<th><a>Group Id</a></th>');
					resultsItems = resultsItems.concat('<th><a>Group Name</a></th>');
					jQuery(array).each(function() {
						resultsItems = resultsItems.concat('<tbody><tr>'); 
						resultsItems = resultsItems.concat('<td>'+this.testTypeId+'</td>');
						resultsItems = resultsItems.concat('<td>'+this.attributeTypeName+'</td>');
						resultsItems = resultsItems.concat('<td>'+this.sortWeight+'</td>');
						resultsItems = resultsItems.concat('<td>'+this.groupId+'</td>');
						resultsItems = resultsItems.concat('<td>'+this.groupName+'</td>');
						resultsItems = resultsItems.concat('</tr></tbody>'); 
					 });
				resultsItems = resultsItems.concat('</tr></thead>');
				resultsItems = resultsItems.concat('</table>');
		   console.log("Sample Container : "+ resultsItems);
		   document.getElementById("sortweightList").innerHTML = resultsItems;
	   //show the module
	   $('#sortWeightModal').modal('show'); 
}


</script>