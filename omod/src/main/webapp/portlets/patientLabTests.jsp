<%@ include file="/WEB-INF/template/include.jsp"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

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
 /*Patient Lab Tests  */
#tb-test-type {
  table-layout: fixed;
}
#tb-test-type td { word-wrap: break-word; }

tbody.collapse.in {
  display: table-row-group;
}
</style>
<!--<i class="fa fa-plus hvr-icon"></i>  -->


<body>
	<br>
<%-- 	<c:set var="testOrder" scope="session" value="${model.testOrder}" />
 --%>	
   <div class="row">
	<openmrs:hasPrivilege privilege="Add CommonLabTest Orders">
		  <div class="col-sm-4 col-md-2">
		 	 <a style="text-decoration:none" href="${pageContext.request.contextPath}/module/commonlabtest/addLabTestOrder.form?patientId=${model.patient.patientId}" class="hvr-icon-grow"><img class="manImg hvr-icon" src="/openmrs/moduleResources/commonlabtest/img/plus.png"> <span> </span> <spring:message code="commonlabtest.order.add" /> </a>
		  </div>
		   <div class="col-sm-4 col-md-2">
		   	 <a style="text-decoration:none" href="${pageContext.request.contextPath}/module/commonlabtest/addLabTestRequest.form?patientId=${model.patient.patientId}" class="hvr-icon-grow"><img class="manImg hvr-icon" src="/openmrs/moduleResources/commonlabtest/img/plus.png"> <span> </span>  <spring:message code="commonlabtest.request.add" /> </a>
		  </div>
	  </openmrs:hasPrivilege>
	</div>
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
					<th>View</th>
					<openmrs:hasPrivilege privilege="Edit CommonLabTest Orders">
					  <th>Edit</th>
					</openmrs:hasPrivilege>
					<openmrs:hasPrivilege privilege="View CommonLabTest Samples">
					  <th>Manage Test Sample</th>
					</openmrs:hasPrivilege>
					 <openmrs:hasPrivilege privilege="View CommonLabTest results">
					 <th>Test Result</th>
					 <th>Result Date</th>
					</openmrs:hasPrivilege>
					
				</tr>
	        </thead>
	        <tbody id="orderContainer">
	        </tbody>
	    </table>
	 </div>
	 <!--<i class="fa fa-eye fa-2x hvr-icon" aria-hidden="true">  <i class="fa fa-edit fa-2x hvr-icon"></i> -->
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
	     			 <div id="orderContainerView" style="overflow: auto">
	     			 
	     			 </div>
	            </fieldset>    
  			 <openmrs:hasPrivilege privilege="View CommonLabTest Samples">
               <!--Test Sample Details -->
               <fieldset  class="scheduler-border">
      	 		  <legend  class="scheduler-border"><spring:message code="commonlabtest.labtestsample.detail" /></legend>
			       <div id="sampleDetailContainer">
			       
			       </div>
       			 </fieldset>
       		</openmrs:hasPrivilege>	
       		<openmrs:hasPrivilege privilege="View CommonLabTest results">	 
       			  <!--Test Result Details -->
               <fieldset  class="scheduler-border">
      	 		  <legend  class="scheduler-border"><spring:message code="commonlabtest.result.detail" /></legend>
       			  	 <div id ="resultDetailContainer"></div>	
       			 </fieldset>
          	</openmrs:hasPrivilege>	 
                
            </div>
        </div>
    </div>
  </div>

</body>

<!--JAVA SCRIPT  -->
 <%--<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/bootstrap/js/jquery-3.3.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/bootstrap/js/popper.min.js"></script> --%>
<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/bootstrap/js/bootstrap.min.js"></script>
 <%--<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/js/jquery-ui.min.js"></script> --%>
 <script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/js/jquery.dataTables.min.js"></script> 
<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/js/dataTables.bootstrap4.min.js"></script>

<script type="text/javascript">	
 var testOrderArray ;
 var isStatusAccepted = false;
jQuery(document).ready(function () {
	  testOrderArray = ${model.testOrder};
	 // console.log("Array: "+ JSON.stringify(testOrderArray));
	   generateOrderTable(testOrderArray);
	   
	jQuery('#testOrderTable').dataTable({
		 "bPaginate": true
	  });
	  jQuery('.dataTables_length').addClass('bs-select');
	
	 // fillTestOrder();
	
	  
});
	function showalert(message,alerttype) {
		//alertType : .alert-success, .alert-info, .alert-warning & .alert-danger
	    jQuery('#alert_placeholder').append('<div id="alertdiv" class="alert ' +  alerttype + '"><a class="close" data-dismiss="alert">×</a><span>'+message+'</span></div>')
	     autoHide();
	  } 
	function getTestOrderList(){
	   	return JSON.parse(JSON.stringify('${model.testOrder}'));
	   }
	function generateOrderTable(localSource){
		  var resultsItems = "";
		  jQuery(localSource).each(function () {
		   /*  if(this.resultFilled){
			    	  resultsItems = resultsItems.concat('<tr id = "mainRow" style="background-color:#1aac9b;">');
			    }else{
			    	  resultsItems = resultsItems.concat('<tr id = "mainRow">');
			    } */
			    console.log("rspecimen :: "+this.requiredSpecimen);
			    console.log("orderId :: "+this.id);
			    resultsItems = resultsItems.concat('<tr id = "mainRow">');
		        resultsItems = resultsItems.concat('<td hidden ="true" class ="orderId">'+this.id+'</td>');
		        resultsItems = resultsItems.concat('<td hidden ="true" class ="rspecimen">'+this.requiredSpecimen+'</td>');
		        resultsItems = resultsItems.concat('<td>'+this.testTypeName+'</td>');
		        resultsItems = resultsItems.concat(' <td>'+this.labReferenceNumber+'</td>');
		        resultsItems = resultsItems.concat('<td> <span class="table-view hvr-icon-grow" onclick="viewTestOrder(this)"><img class="manImg hvr-icon" src="/openmrs/moduleResources/commonlabtest/img/view.png"></span></td>');
		        resultsItems = resultsItems.concat('<openmrs:hasPrivilege privilege="Edit CommonLabTest Orders">');
		        resultsItems = resultsItems.concat('<td> <span class="table-edit hvr-icon-grow" onclick="editTestOrder(this)"><img class="manImg hvr-icon" src="/openmrs/moduleResources/commonlabtest/img/edit.png"></span></td>');
		        resultsItems = resultsItems.concat('</openmrs:hasPrivilege>');
		        resultsItems = resultsItems.concat('<openmrs:hasPrivilege privilege="View CommonLabTest Samples">');
		        resultsItems = resultsItems.concat('<td> <span class="table-sample hvr-icon-grow" onclick="samplesTestOrder(this)"><img class="manImg hvr-icon" src="/openmrs/moduleResources/commonlabtest/img/testSample.png"></img></span></span></td>');
		        resultsItems = resultsItems.concat('</openmrs:hasPrivilege>');
		        resultsItems = resultsItems.concat('<openmrs:hasPrivilege privilege="View CommonLabTest results">');
		        if(this.resultFilled){
		          resultsItems = resultsItems.concat('<td> <span class="table-result hvr-icon-grow" onclick="resultsTestOrder(this)"><img class="manImg hvr-icon" src="/openmrs/moduleResources/commonlabtest/img/testResult.png"></img></span></span><span style="margin-left: 35px">  </span><img class="manImg hvr-icon" src="/openmrs/moduleResources/commonlabtest/img/greenchecked.png"></img></td>');
		        }else{
			    resultsItems = resultsItems.concat('<td> <span class="table-result hvr-icon-grow" onclick="resultsTestOrder(this)"><img class="manImg hvr-icon" src="/openmrs/moduleResources/commonlabtest/img/testResult.png"></img></span></span></td>');
		        }
			    resultsItems = resultsItems.concat('<td>'+this.resultDate+'</td>');
		        resultsItems = resultsItems.concat('</openmrs:hasPrivilege>');
		        resultsItems = resultsItems.concat('</tr>');
		  	});    
		   // console.log("Resultan String : " + resultsItems);
		    document.getElementById("orderContainer").innerHTML = resultsItems;
		   // $("#orderContainer").append(resultsItems);
	}	
	
	//old
	
	
function autoHide(){
	   jQuery("#alertdiv").fadeTo(5000, 500).slideUp(500, function(){
	           jQuery("#alertdiv").slideUp(500);
	           jQuery("#alertdiv").remove();
            }); 	
}

	/*Edit Test Order  */
	function editTestOrder(editEle){
		var testOrderId = jQuery(editEle).closest("tr")
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

		 var testOrderId = jQuery(viewEl).closest("tr")
							        .find(".orderId") 
						            .text(); 
		 var newArray = testOrderArray.find(o => o.id == testOrderId);
		  populateTestOrder(newArray);
		  populateTest(testOrderId);
		// populateTestResult(testOrderId);
		 
	}
	/* samples Test Order */
	function samplesTestOrder(sampleEl){
		  var requiresSpecimen = jQuery(sampleEl).closest("tr")  
									          .find(".rspecimen")   
									          .text(); 

		var testOrderId = jQuery(sampleEl).closest("tr")
							      .find(".orderId") 
							      .text(); 
		if(requiresSpecimen == 'false'){
			showalert("This test order is does not required test sample...","alert-info");
		}
		else{
			window.location = "${pageContext.request.contextPath}/module/commonlabtest/manageLabTestSamples.form?patientId="+${model.patient.patientId}+"&testOrderId="+testOrderId; 
		}
	}
	/* results Test Order */
	function resultsTestOrder(resultEl){
		var testOrderId = jQuery(resultEl).closest("tr")
						        .find(".orderId") 
						        .text(); 
		 var requiresSpecimen = jQuery(resultEl).closest("tr")  
									         .find(".rspecimen")   
									         .text();
		 console.log("requiresSpecimen :: "+requiresSpecimen);
		 checkTestSampleStatus(testOrderId);
		if(testOrderId == "" || testOrderId == null ){}
		else if(requiresSpecimen == 'true'){
			//console.log("Called");
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
		 jQuery.ajax({
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
	    // console.log("resultsItems : "+resultsItems);
	     resultsItems = resultsItems.concat(' <form id="form">');
		     resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="commonlabtest.order.id" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.id+'</sub></font></label>');			 
			 resultsItems = resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.testGroup" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.testGroup+'</sub></font></label>');
			 resultsItems = resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.testType" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.testTypeName+'</sub></font></label>');
			 resultsItems = resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.encounterType" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.encounterType+'</sub></font></label>');
			 resultsItems = resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="commonlabtest.order.labReferenceNo" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.labReferenceNumber+'</sub></font></label>');
			 resultsItems = resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.requiresSpecimen" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.requiredSpecimen+'</sub></font></label>');
			 resultsItems = resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.createdBy" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.createdBy+'</sub></font></label>');
			 resultsItems = resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.changedBy" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.changedBy+'</sub></font></label>');
			 resultsItems = resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.dateCreated" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.dateCreated+'</sub></font></label>');
			 resultsItems = resultsItems.concat('</div></div>');
			 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
			 resultsItems = resultsItems.concat('<label ><font color="#17202A"><sub><spring:message code="general.uuid" /></sub></font></label>');
			 resultsItems = resultsItems.concat('</div><div class ="col-md-8">');
			 resultsItems = resultsItems.concat('<label ><font color="#5D6D7E"><sub>'+testOrderArr.uuid+'</sub></font></label>');
			 resultsItems =resultsItems.concat('</div></div>');
		
	     resultsItems = resultsItems.concat('</form>');
	     
		 document.getElementById("orderContainerView").innerHTML = resultsItems;
	   }
	
	function populateTest(testOrderId){
		
		 jQuery.ajax({
				  type : "GET",
				  contentType: "application/json;charset=ISO-8859-1",
				  url : '${pageContext.request.contextPath}/module/commonlabtest/getTestResults.form?testOrderId='+testOrderId,
					success : function(data) {
						console.log("testing");
					    console.log(data);
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
		  var parseData = JSON.parse(data)
			  <openmrs:hasPrivilege privilege="View CommonLabTest Samples">
			  		renderTestSample(parseData['sample']);
			  </openmrs:hasPrivilege>
			<openmrs:hasPrivilege privilege="View CommonLabTest results">
		       // renderTestResult(parseData['result'])
		        renderResult(parseData['result']);
		    </openmrs:hasPrivilege>
		  jQuery('#viewModal').modal('show'); 
	}
	
	
	function renderTestSample(sampleArray){
	       var resultsItems = "";
				resultsItems = resultsItems.concat('<table  class="table table-striped table-responsive-md table-hover mb-0" id= "tb-test-type">');
				resultsItems = resultsItems.concat('<thead><tr>');
					resultsItems = resultsItems.concat('<th><a>Test Order</a></th>');
					resultsItems = resultsItems.concat('<th><a>Specimen Type</a></th>');
					resultsItems = resultsItems.concat('<th><a>Specimen Site</a></th>');
					resultsItems = resultsItems.concat('<th><a>Status</a></th>');
					jQuery(sampleArray).each(function() {
						//console.log("testOrderId : "+this.testOrderId);
						resultsItems = resultsItems.concat('<tbody><tr>'); 
						resultsItems = resultsItems.concat('<td><label>'+this.testOrderId+'</label></td>');
						resultsItems = resultsItems.concat('<td><label>'+this.specimenType+'</label></td>');
						resultsItems = resultsItems.concat('<td><label>'+this.specimenSite+'</label></td>');
						resultsItems = resultsItems.concat('<td><label>'+this.status+'</label></td>');
						resultsItems = resultsItems.concat('</tr></tbody>'); 
						 
					 });
				resultsItems = resultsItems.concat('</tr></thead>');
				resultsItems = resultsItems.concat('</table>');
		   
		   //console.log("Sample Container : "+ resultsItems);
		   document.getElementById("sampleDetailContainer").innerHTML = resultsItems;
	}

	function renderResult(resultArray){
		//console.log("result Array :: "+ JSON.stringify(resultArray));
	      var resultsItems = "";
			resultsItems = resultsItems.concat('<table  class="table table-striped table-responsive-md table-hover mb-0" id="tb-test-type">');
			resultsItems = resultsItems.concat('<thead><tr>');
			    resultsItems = resultsItems.concat('<th><a>Group Name</a></th>');
				resultsItems = resultsItems.concat('<th><a>Question</a></th>');
				resultsItems = resultsItems.concat('<th><a>Value</a></th>');
				resultsItems = resultsItems.concat('</tr></thead>');
				jQuery(resultArray).each(function() {
					
				     if(this.groups != undefined && this.groups.length > 0){
					 	    let groupIden = this.groupName.split(" ").join("");
						    let groupIdentity =groupIden.replace(/[&\/\\#,+()$~%.:*?<>{}]/g, '');
					    		resultsItems = resultsItems.concat('<tbody><tr class="clickable" data-toggle="collapse" data-target="#group-of-rows-'+groupIdentity+'" aria-expanded="false" aria-controls="group-of-rows-'+groupIdentity+'">'); 
								resultsItems = resultsItems.concat('<td style="color: #1aac9b"><img class="manImg hvr-icon" src="/openmrs/moduleResources/commonlabtest/img/add.png"><label style="margin-left: 20px">'+this.groupName+'</label></td>');
					    		resultsItems = resultsItems.concat('<td></td>');
								resultsItems = resultsItems.concat('<td><label></label></td>');
								resultsItems = resultsItems.concat('</tr></tbody>'); 
			        	    resultsItems = resultsItems.concat(generateTestGroup(this.groups ,groupIdentity)); //generate all the groups 
			        }else{
			        	 if(this.void === true){
								resultsItems = resultsItems.concat('<tbody><tr>'); 
								resultsItems = resultsItems.concat('<td><label></label></td>');
								resultsItems = resultsItems.concat('<td><font color="#FF0000"><label>'+this.question+'</label></font></td>');
								resultsItems = resultsItems.concat('<td><font color="#FF0000"><label>'+this.valuesReference+'</label></font></td>');
								resultsItems = resultsItems.concat('</tr></tbody>'); 
						 }else {
								resultsItems = resultsItems.concat('<tbody><tr>'); 
								resultsItems = resultsItems.concat('<td><label></label></td>');
								resultsItems = resultsItems.concat('<td><label>'+this.question+'</label></td>');
								resultsItems = resultsItems.concat('<td><label>'+this.valuesReference+'</label></td>');
								resultsItems = resultsItems.concat('</tr></tbody>'); 
						 } 	
			        }
				});
				resultsItems = resultsItems.concat('</table>');
		   
		    // console.log("result Container : "+ resultsItems);
		   document.getElementById("resultDetailContainer").innerHTML = resultsItems;				
	}
	/*Generate Group List  */
	function generateTestGroup(localTestGroups,groupName) {
	    var resultsItems = "";
	    resultsItems = resultsItems.concat('<tbody id="group-of-rows-'+groupName+'" class="collapse">');
	        jQuery(localTestGroups).each(function () {
	        	resultsItems = resultsItems.concat('<tr>'); 
	        	resultsItems = resultsItems.concat('<td><label></label></td>');
				resultsItems = resultsItems.concat('<td><label>'+this.question+'</label></td>');
				resultsItems = resultsItems.concat('<td><label>'+this.valuesReference+'</label></td>');
				resultsItems = resultsItems.concat('</tr>'); 
	        });
	   resultsItems = resultsItems.concat('</tbody>');    
	  return resultsItems;
	}

</script>
