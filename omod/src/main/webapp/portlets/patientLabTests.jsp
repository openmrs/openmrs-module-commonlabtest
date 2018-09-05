<%@ include file="/WEB-INF/template/include.jsp"%>

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
</style>

<body>
	<br>
	<c:set var="testOrder" scope="session" value="${model.testOrder}" />
	<div>
	 <a style="text-decoration:none" href="${pageContext.request.contextPath}/module/commonlabtest/addLabTestOrder.form?patientId=${model.patient.patientId}" class="hvr-icon-grow"><i class="fa fa-plus hvr-icon"></i> <spring:message code="commonlabtest.order.add" /> </a>
	</div>
	<%-- <c:if test="${not empty status}">
		<div class="alert alert-success">
			 <a href="#" class="close" data-dismiss="alert">&times;</a>
	 		<strong>Success!</strong> <c:out value="${status}" />
		</div>
	</c:if> --%>
		<!--  <div class="alert alert-info" hidden ="true" id="specimenalert">
	      <a href="#" class="close" data-dismiss="alert">&times;</a>
	      <p id="info-message">This test order is not required test sample...</p>
   		 </div>  -->
   		 <div id="alert_placeholder"></div>
	<br>
	<!--List of Test Order  -->
	<div class=" boxHeader" style="background-color: #1aac9b">
			<span></span> <b><spring:message code="commonlabtest.order.list" /></b>
	 </div>
	 <div class="box">
		 <table id="testOrderTable" class="table table-striped table-bordered" style="width:100%">
	        <thead>
	            <tr>
	            	<th hidden="true"></th>
	            	<th hidden="true"></th>
					<th>Test Type</th>
					<th>Lab Reference Number</th>
					<th>Edit</th>
					<th>View</th>
					<th>Manage Test Sample</th>
					<th>Test Result</th>
					
				</tr>
	        </thead>
	        <tbody>
		        <c:forEach var="order" items="${model.testOrder}">
		       	 <c:if test="${! empty model.testOrder}">
							<tr id = "mainRow">
							     <td hidden ="true" class ="orderId">${order.testOrderId}</td>
							     <td hidden ="true" class ="rspecimen">${order.labTestType.requiresSpecimen}</td>
								 <td>${order.labTestType.name}</td>
								 <td>${order.labReferenceNumber}</td>
								 <td> <span class="table-edit hvr-icon-grow" onclick="editTestOrder(this)"><i class="fa fa-edit fa-2x hvr-icon"></i></span></td>
					             <td> <span class="table-view hvr-icon-grow" onclick="viewTestOrder(this)"><i class="fa fa-eye fa-2x hvr-icon" aria-hidden="true"></i></span></td>
					             <td> <span class="table-sample hvr-icon-grow" onclick="samplesTestOrder(this)"><img class="manImg hvr-icon" src="/openmrs/moduleResources/commonlabtest/img/testSample.png"></img></span></span></td>
					             <td> <span class="table-result hvr-icon-grow" onclick="resultsTestOrder(this)"><img class="manImg hvr-icon" src="/openmrs/moduleResources/commonlabtest/img/testResult.png"></img></span></span></td>
					             
							</tr>
					</c:if>		
				 </c:forEach>
	        </tbody>
	    </table>
	 </div>
	 
    <!-- View Modal -->
	<div class="modal fade" id="viewModal" tabindex="-1" role="dialog" aria-labelledby="viewModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header text-center">
                <h4 class="modal-title w-100 font-weight-bold">View Test Order</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
            
               <fieldset  class="scheduler-border">
      	 		  <legend  class="scheduler-border"><spring:message code="commonlabtest.order.detail" /></legend>
	     			 <div id="orderContainer">
	     			 
	     			 </div>
	            </fieldset>    

               <!--Test Sample Details -->
               <fieldset  class="scheduler-border">
      	 		  <legend  class="scheduler-border"><spring:message code="commonlabtest.labtestsample.detail" /></legend>
			       <div id="sampleDetailContainer">
			       
			       </div>
       			 </fieldset>
       			  <!--Test Sample Details -->
               <fieldset  class="scheduler-border">
      	 		  <legend  class="scheduler-border"><spring:message code="commonlabtest.result.detail" /></legend>
       			  	 <div id ="resultDetailContainer"></div>	
       			 </fieldset>
                
            </div>
        </div>
    </div>
  </div>

</body>

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


<script type="text/javascript">	
 var testOrderArray ;
 var isStatusAccepted = false;
$(document).ready(function () {
		
	
	$('#testOrderTable').dataTable({
		 "bPaginate": true
	  });
	  $('.dataTables_length').addClass('bs-select');
	  
	 // testOrderArray = '${model.testOrder}'; //getTestOrderList();
	  fillTestOrder();
	  console.log("Array: "+ testOrderArray);
	  
});
	function showalert(message,alerttype) {
		//alertType : .alert-success, .alert-info, .alert-warning & .alert-danger
	    $('#alert_placeholder').append('<div id="alertdiv" class="alert ' +  alerttype + '"><a class="close" data-dismiss="alert">×</a><span>'+message+'</span></div>')
	     autoHide();
	   /*  setTimeout(function() { // this will automatically close the alert and remove this if the users doesnt close it in 5 secs
	      $("#alertdiv").remove();
	
	    }, 5000);*/
	  } 

 function fillTestOrder(){
	 testOrderArray = new Array();
     <c:if test="${not empty model.testOrder}">
	        <c:forEach var="testOrder" items="${model.testOrder}" varStatus="status">
	       			 testOrderArray.push({requiresSpecimen:"${testOrder.labTestType.requiresSpecimen}",id:"${testOrder.testOrderId}", name :"${testOrder.labTestType.name}" ,testGroup:"${testOrder.labTestType.testGroup}",labReferenceNumber:"${testOrder.labReferenceNumber}",dateCreated:"${testOrder.labTestType.dateCreated}",createdBy:"${testOrder.creator.username}",changedBy:"${testOrder.changedBy}",uuid:"${testOrder.uuid}"});
	        </c:forEach>
     </c:if>   
	 
	 return testOrderArray;
 }
function getTestOrderList(){
   	return JSON.parse(JSON.stringify('${model.testOrder}'));
   }

function autoHide(){
	   $("#alertdiv").fadeTo(5000, 500).slideUp(500, function(){
	           $("#alertdiv").slideUp(500);
	           $("#alertdiv").remove();
            }); 	
}

	/*Edit Test Order  */
	function editTestOrder(editEle){
		var testOrderId = $(editEle).closest("tr")
							          .find(".orderId") 
						          .text(); 
		if(testOrderId == ""){		  
		}
		else{
			window.location = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestOrder.form?patientId="+${model.patient.patientId}+"&testOrderId="+testOrderId; //+"&testOrderId="+2;  
		}
	}
	
	/* View Test Order  */
	function viewTestOrder(viewEl){

		 var testOrderId = $(viewEl).closest("tr")
							        .find(".orderId") 
						            .text(); 
		 var newArray = testOrderArray.find(o => o.id == testOrderId);
		  populateTestOrder(newArray);
		  populateTest(testOrderId);
		// populateTestResult(testOrderId);
		 
	}
	/* samples Test Order */
	function samplesTestOrder(sampleEl){
		  var requiresSpecimen = $(sampleEl).closest("tr")  
									          .find(".rspecimen")   
									          .text(); 

		var testOrderId = $(sampleEl).closest("tr")
							      .find(".orderId") 
							      .text(); 
		if(requiresSpecimen == 'false'){
			showalert("This test order is not required test sample...","alert-info");
		}
		else{
			window.location = "${pageContext.request.contextPath}/module/commonlabtest/manageLabTestSamples.form?patientId="+${model.patient.patientId}+"&testOrderId="+testOrderId; 
		}
	}
	/* results Test Order */
	function resultsTestOrder(resultEl){
		var testOrderId = $(resultEl).closest("tr")
						        .find(".orderId") 
						        .text(); 
		 var requiresSpecimen = $(resultEl).closest("tr")  
									         .find(".rspecimen")   
									         .text();
		 
		 checkTestSampleStatus(testOrderId);
		if(testOrderId == "" || testOrderId == null ){}
		else if(requiresSpecimen == 'true'){
			console.log("Called");
		    if(isStatusAccepted == true){
				window.location = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestResult.form?patientId="+${model.patient.patientId}+"&testOrderId="+testOrderId;  
			}
			else {
				showalert("To enter a test result, one sample with ACCEPTED status should exist","alert-info");
			}
		}
		else {
			window.location = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestResult.form?patientId="+${model.patient.patientId}+"&testOrderId="+testOrderId;
		}
		
	}
	
	function checkTestSampleStatus(testOrderId){
		 $.ajax({
				type : "GET",
				contentType : "application/json",
				url : '${pageContext.request.contextPath}/module/commonlabtest/getTestSampleStatus.form?testOrderId='+testOrderId,
				async:false,
				dataType : "json",
				success : function(data) {
				   console.log("success  : " + data);
				   isStatusAccepted = data;
				},
				error : function(data) {
					 isStatusAccepted = false;
					  console.log("fail  : " + data);
				},
				done : function(e) {
					console.log("DONE");
				}
		});
		
	}
	
	function populateTestOrder(testOrderArr){
	
	     var resultsItems = "";
	     console.log("resultsItems : "+resultsItems);
	     resultsItems = resultsItems.concat(' <form id="form">');
		     resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="commonlabtest.order.id" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.id+'</sub></font></label>');			 
			 resultsItems =resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.testGroup" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.testGroup+'</sub></font></label>');
			 resultsItems =resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.testType" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.name+'</sub></font></label>');
			 resultsItems =resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="commonlabtest.order.labReferenceNo" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.labReferenceNumber+'</sub></font></label>');
			 resultsItems =resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.requiresSpecimen" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.requiresSpecimen+'</sub></font></label>');
			 resultsItems =resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.createdBy" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.createdBy+'</sub></font></label>');
			 resultsItems =resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.changedBy" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.changedBy+'</sub></font></label>');
			 resultsItems =resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.uuid" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-8">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.uuid+'</sub></font></label>');
			 resultsItems =resultsItems.concat('</div></div>');
		
	     resultsItems = resultsItems.concat('</form>');
	     
		 document.getElementById("orderContainer").innerHTML = resultsItems;
	   }
	
	function populateTest(testOrderId){
		
		 $.ajax({
					type : "GET",
					contentType : "application/json",
					url : '${pageContext.request.contextPath}/module/commonlabtest/getTestResults.form?testOrderId='+testOrderId,
					dataType : "json",
					success : function(data) {
					   console.log("success  : " + data);
					    parsData(data);
					},
					error : function(data) {
						  console.log("fail  : " + data);
					},
					done : function(e) {
						console.log("DONE");
					}
			});
	 } 
	
	function parsData(data){
		  console.log(JSON.stringify(data));
		 // resultArray =data.result;
		  renderTestSample(data.sample);
		  renderTestResult(data.result)
		  $('#viewModal').modal('show'); 
	}
	
	
	function renderTestSample(sampleArray){
	       var resultsItems = "";
				resultsItems = resultsItems.concat('<table  class="table table-striped table-responsive-md btn-table table-hover mb-0" id="tb-test-type">');
				resultsItems = resultsItems.concat('<thead><tr>');
					resultsItems = resultsItems.concat('<th><a>Test Order</a></th>');
					resultsItems = resultsItems.concat('<th><a>Spacimen Type</a></th>');
					resultsItems = resultsItems.concat('<th><a>Spacimen Site</a></th>');
					resultsItems = resultsItems.concat('<th><a>Status</a></th>');
					jQuery(sampleArray).each(function() {
						console.log("testOrderId : "+this.testOrderId);
						resultsItems = resultsItems.concat('<tbody><tr>'); 
						resultsItems = resultsItems.concat('<td>'+this.testOrderId+'</td>');
						resultsItems = resultsItems.concat('<td>'+this.specimenType+'</td>');
						resultsItems = resultsItems.concat('<td>'+this.specimenSite+'</td>');
						resultsItems = resultsItems.concat('<td>'+this.status+'</td>');
						resultsItems = resultsItems.concat('</tr></tbody>'); 
						 
					 });
				resultsItems = resultsItems.concat('</tr></thead>');
				resultsItems = resultsItems.concat('</table>');
		   
		   console.log("Sample Container : "+ resultsItems);
		   document.getElementById("sampleDetailContainer").innerHTML = resultsItems;
	}
	
	function renderTestResult(resultArray){
	      var resultsItems = "";
			resultsItems = resultsItems.concat('<table  class="table table-striped table-responsive-md btn-table table-hover mb-0" id="tb-test-type">');
			resultsItems = resultsItems.concat('<thead><tr>');
				resultsItems = resultsItems.concat('<th><a>Question</a></th>');
				resultsItems = resultsItems.concat('<th><a>Value</a></th>');
				jQuery(resultArray).each(function() {
					console.log("testOrderId : "+this.testOrderId);
					resultsItems = resultsItems.concat('<tbody><tr>'); 
					resultsItems = resultsItems.concat('<td>'+this.question+'</td>');
					resultsItems = resultsItems.concat('<td>'+this.valuesReference+'</td>');
					resultsItems = resultsItems.concat('</tr></tbody>'); 
				 });
			resultsItems = resultsItems.concat('</tr></thead>');
			resultsItems = resultsItems.concat('</table>');
	   
	   console.log("result Container : "+ resultsItems);
	   document.getElementById("resultDetailContainer").innerHTML = resultsItems;
	}
	  

</script>
