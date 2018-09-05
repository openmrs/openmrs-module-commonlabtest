<%-- <%@ page import="org.openmrs.web.WebConstants" %>
<%
pageContext.setAttribute("redirect", session.getAttribute(WebConstants.OPENMRS_LOGIN_REDIRECT_HTTPSESSION_ATTR));
session.removeAttribute(WebConstants.OPENMRS_LOGIN_REDIRECT_HTTPSESSION_ATTR); 
%> --%>

<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include
	file="/WEB-INF/view/module/commonlabtest/include/localHeader.jsp"%>

<openmrs:require privilege="View labTestAttributeType" otherwise="/login.htm" redirect="/module/commonlabtest/addLabTestAttributeType.form" />


<link type="text/css" rel="stylesheet"
	href="/openmrs/moduleResources/commonlabtest/css/commonlabtest.css" />
<link
	href="/openmrs/moduleResources/commonlabtest/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" />
<link
	href="/openmrs/moduleResources/commonlabtest/css/bootstrap.min.css"
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
	font-size: 10px;
}
input[type=submit] {
	background-color: #1aac9b;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	
}
input[type=button] {
	background-color: #1aac9b;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	
}
#saveUpdateButton {
    text-align: center;
}
fieldset.scheduler-border {
    border: 1px groove #ddd !important;
    padding: 0 1.4em 1.4em 1.4em !important;
    margin: 0 0 1.5em 0 !important;
    -webkit-box-shadow:  0px 0px 0px 0px #1aac9b;
             box-shadow: 0px 0px 14px 0px #1aac9b61;
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
 
/* #testTypeName
{
    overflow: hidden;
    white-space:nowrap;
    text-overflow:ellipsis;
    width:150px;
    display:inline-block;
} */
</style>

<body>
	

 <div class="container">
 
     <c:if test="${not empty error}">
		<div class="alert alert-danger">
			 <a href="#" class="close" data-dismiss="alert">&times;</a>
	 		<strong>Error!</strong> <c:out value="${error}" />
		</div>
	</c:if>	 
	<c:set var="testAttributeType" scope="session" value="${attributeType}" />
	<fieldset  class="scheduler-border">
	   <c:if test="${empty testAttributeType.name}">
		 <legend  class="scheduler-border"><spring:message code="commonlabtest.labtestattributetype.add" /></legend>
	   </c:if>
	   <c:if test="${not empty testAttributeType.name}">
		 <legend  class="scheduler-border">	<spring:message code="commonlabtest.labtestattributetype.edit" /></legend>
	   </c:if>
 	   <form:form commandName="attributeType" onsubmit='return validate(this);'>
 	        		 <div class="row">
						   <div class="col-md-2">
						   		<form:input path="labTestAttributeTypeId"  hidden="true" id="labTestAttributeTypeId"></form:input>
								<form:label path="labTestType.labTestTypeId" class="control-label"><spring:message code="general.labTestType" /><span class="text-danger required">*</span></form:label>
							</div>
						   <div class="col-md-6">	
						   		<form:input class="form-control"  id="testTypeSuggestBox" path="labTestType.labTestTypeId" list="testTypeOptions" placeholder="Search Test Type..." ></form:input>
								<datalist class="lowercase" id="testTypeOptions"></datalist>
							    <span id="labtesttypeid" class="text-danger "> </span>
						   </div>
						   <div class="col-md-4">  
						      <font color="#D0D0D0"><span id="testTypeName"></span></font>
						   </div>
					  </div>
					  <!--Test Attribute Type Name  -->
					  <div class="row">
						   <div class="col-md-2">
						   		<form:label path="name" class="control-label"><spring:message code="general.name" /><span class="text-danger required">*</span></form:label>
						   	</div>
						   <div class="col-md-6">
						   		<form:input class="form-control" maxlength="50" path="name" id="name" ></form:input>
							    <span id="testatrname" class="text-danger "> </span>
						   	</div>
					  </div>
					  <!-- Description -->
					   <div class="row">
						   <div class="col-md-2">
					  			<form:label path="description" class="control-label"><spring:message code="general.description" /><span class="text-danger required">*</span></form:label>
						   	</div>
						   <div class="col-md-6">
								<form:textarea class="form-control" maxlength="255"  path="description" id="description" ></form:textarea>
							    <span id="atrdescription" class="text-danger "> </span>
						   	</div>
					  </div>
					  <!-- Min Ocurance -->
					    <div class="row">
						   <div class="col-md-2">
								<form:label path="minOccurs" class="control-label"><spring:message code="general.minOccurs" /><span class="text-danger required">*</span></form:label>
							</div>
						   <div class="col-md-6">
								<form:input class="form-control" maxlength="2"   path="minOccurs" id="min_occurs" onkeypress="return isNumber(event)"></form:input></td>
							    <span id="minoccurs" class="text-danger "> </span>
						   	</div>
					 	 </div>
					 	 <!-- max occurs -->
					 	  <div class="row">
							   <div class="col-md-2">
									<form:label path="maxOccurs" class="control-label"><spring:message code="general.maxOccurs" /><span class="text-danger required">*</span></form:label>
								</div>
							   <div class="col-md-6">
									<form:input class="form-control" maxlength="2"   path="maxOccurs" id="max_occurs" onkeypress="return isNumber(event)"></form:input>
								    <span id="maxoccurs" class="text-danger "> </span>
							   	</div>
					 	 </div>
					 	 <!-- sort Weight -->
					 	  <div class="row">
							   <div class="col-md-2">
					 	 			<form:label path="sortWeight" class="control-label"><spring:message code="general.sortWeight" /><span class="text-danger required">*</span></form:label>
								</div>
							   <div class="col-md-6">
									<form:input class="form-control" maxlength="2"  path="sortWeight" id="sortWeight"  onkeypress="return isNumber(event)"></form:input>
								    <span id="sortweight" class="text-danger "> </span>
							   	</div class="col-md-4">
							   		 <a style="text-decoration:none" onclick="showSortWeightList(this);" id="addTestSamples" class="hvr-icon-grow"><i class="fa fa-eye hvr-icon"></i> Sort Weight hierarchy</a>
							   	<div>
							   	
							   	</div>
					 	   </div>
					 	   
					 	   <!-- datatypeClassname -->
					 	    <div class="row">
							   <div class="col-md-2">
									<form:label path="datatypeClassname" class="control-label"><spring:message code="general.dataType" /></form:label>							
							   </div> 
							   <div class="col-md-6">
							      <c:if test = "${available != true}">
							   
								         		<form:select class="form-control" path="datatypeClassname" id="data_type_name">
													 <form:options  />
													<c:forEach items="${datatypes}"  var="datatype">
													    <form:option item ="${datatype}" value="${datatype}" ><c:out value="${datatype}.name" /></form:option>
													</c:forEach>
												</form:select>
								         </c:if>
								         <c:if test = "${available == true}">
												<form:select class="form-control" disabled="true" path="datatypeClassname" id="data_type_name">
													 <form:options  />
													<c:forEach items="${datatypes}"  var="datatype">
													    <form:option item ="${datatype}" value="${datatype}" ><c:out value="${datatype}.name" /></form:option>
													</c:forEach>
												</form:select>								        
										 </c:if>
							   	</div>
							   	<div class ="col-md-4">
							   		<font color="#D0D0D0"><span id="datatypeDescription"></span></font>
							   	</div>
					 	   </div>
					 	   <!-- datatypeConfig -->
							<div class="row">
							   <div class="col-md-2">
					 	  		   <form:label path="datatypeConfig" class="control-label"><spring:message code="general.datatypeConfiguration" /></form:label>
								</div>
							   <div class="col-md-6">
								         <c:if test = "${available != true}">
								         	<form:textarea class="form-control" path="datatypeConfig" id="datatypeConfig" ></form:textarea>
								         </c:if>
								        <c:if test = "${available == true}">
								          	<form:textarea class="form-control" disabled="true" path="datatypeConfig" id="datatypeConfig" ></form:textarea>
								        </c:if>   
							   	</div>
					 	   </div>
					 	     <!-- preferredHandlerClassname -->
					 	    <div class="row">
							   <div class="col-md-2">
									<form:label path="preferredHandlerClassname" class="control-label"><spring:message code="general.preferredHandler" /></form:label>								   </div> 
							   <div class="col-md-6">
									<form:select class="form-control" path="preferredHandlerClassname" id="preferred_handler_name">
										<option value=""><openmrs:message code="general.default"/></option>
										<c:forEach items="${handlers}"  var="handler">
<%-- 											<option value="${handler}" <c:if test="${handler == status.value}">selected</c:if>><spring:message code="${handler}.name"/></option>
 --%>									 
 											  <form:option item ="${handler}" value="${handler}" ><c:out value="${handler}.name" /></form:option>	
 										</c:forEach>
									</form:select>
							   	</div>
							   	<div class ="col-md-4">
							   		<font color="#D0D0D0"><span id="handlerDescription"></span></font>
							   	</div>
					 	   </div>
							<!-- handlerConfig-->
 	   						<div class="row">
							   <div class="col-md-2">
 	 				    			<form:label class="control-label" path="handlerConfig"><spring:message code="general.handlerConfiguration" /></form:label>
								</div>
							   <div class="col-md-6">
									<form:textarea class="form-control" path="handlerConfig" id="handlerConfig" ></form:textarea>
							   	</div>
					 	   </div>	
 	   						<c:if test="${not empty testAttributeType.name}">
								<div class="row">
									   <div class="col-md-2">
											<form:label path="creator"><spring:message code="general.createdBy" /></form:label>					
										</div>
									   <div class="col-md-6">
										    <c:out value="${testAttributeType.creator.personName}" /> - <c:out value="${testAttributeType.dateCreated}" />	
								       </div>
							    </div>	
							    <div class="row">
									   <div class="col-md-2">
											<font color="#D0D0D0"><sub><spring:message code="general.uuid" /></sub></font>		
										</div>
									   <div class="col-md-6">
											<font color="#D0D0D0"><sub><c:out value="${testAttributeType.uuid}" /></sub></font>	
										</div>
							    </div>	
							</c:if>
							  <!-- Save -->
							 <div class="row">
							   <div class="col-md-3">
									<input type="submit" value="<spring:message code="commonlabtest.labtestattributetype.save" />"  ></input>
							   </div>
							    <div class="col-md-2" >
									<input type="button" onclick="location.href = '${pageContext.request.contextPath}/module/commonlabtest/manageLabTestAttributeTypes.form';"  value="Cancel"></input>
								</div>
							 </div>	
 	   
		 </form:form>
    </fieldset>
	<br>
	<c:if test="${not empty testAttributeType.name}">
		 <fieldset  class="scheduler-border">
      	   <legend  class="scheduler-border"><spring:message code="general.test.retire" /></legend>
      	 		<form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/module/commonlabtest/retirelabtestattributetype.form" onsubmit="return retireValidate()" >
						 <!-- UUID -->
						 <div class="row">
						   <div class="col-md-2">
								<input value="${testAttributeType.uuid}" hidden="true"  id="uuid" name="uuid"></input>
								<label  class="control-label" path="retireReason"><spring:message code="general.retireReason" /><span class="text-danger required">*</span></label>
						   </div>
						   <div class="col-md-6">
						   		<input class="form-control" value="${testAttributeType.retireReason}" id="retireReason" name="retireReason" >
						 		 <span id="retirereason" class="text-danger "> </span>
						 
						   </div>
						 </div>
						 <!-- Retire -->
						 <div class="row">
						   <div class="col-md-2" >
						 		 <input type="submit" value="<spring:message code="general.test.retire" />"></input>
						   </div>
						 </div>
					<%-- 	<table>
							 <div class="form-group">
								<tr>
									<input value="${testAttributeType.uuid}" hidden="true"  id="uuid" name="uuid"></input>
									<td><label class="control-label" value="retireReason"><spring:message code="general.retireReason" /><span class="required">*</span></label></td>
									<td><input class="form-control" value="${testAttributeType.retireReason}" id="retireReason" name="retireReason" required="required"></input></td>
								</tr>
							</div>
							<tr>
								<td>
									<div id="retireButton" style="margin-top: 15px">
										<input type="submit" value="<spring:message code="general.test.retire" />"></input>
									</div>
								</td>
							</tr>
						</table> --%>
				</form>
        </fieldset>
	</c:if>

	<%-- <br>
    <c:if test="${not empty testAttributeType.name}">
		 <fieldset  class="scheduler-border">
      	   <legend  class="scheduler-border"><spring:message code="general.foreverDelete" /></legend>
				<form  method="post" action ="${pageContext.request.contextPath}/module/commonlabtest/deletelabtestattributetype.form" onsubmit="return confirmDelete()">
					 <!-- Delete -->
					 <div class="row">
					   <div class="col-md-2" >
					  		 <input value="${testAttributeType.uuid}" hidden="true"  id="uuid" name="uuid"></input>
					 		 <input type="submit" value="<spring:message code="general.foreverDelete" />" />
					   </div>
					 </div>	
					
					<table>
					<tr>
						<td>
							<input value="${testAttributeType.uuid}" hidden="true"  id="uuid" name="uuid"></input>
							<div id="delete" style="margin-top: 15px">
								<input type="submit" value="<spring:message code="general.foreverDelete" />" />
							</div>
						</td>
					</tr>
					</table>
				</form>
      </fieldset>
	</c:if> --%>
 </div>
 
 
	<div class="modal fade right modal-scrolling" id="sortWeightModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="false" style="display: none;" aria-hidden="true">
	    <div class="modal-dialog modal-side modal-top-right modal-notify modal-info" role="document">
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
 
</body>

<!--Java Script  -->
<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/bootstrap/js/jquery-3.3.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/bootstrap/js/popper.min.js"></script>
<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/bootstrap/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/js/jquery-ui.min.js"></script>


<script>
	var local_source;

	jQuery(document).ready(function() {
		 
		local_source = new Array();
	        <c:if test="${not empty listTestType}">
		        <c:forEach var="testType" items="${listTestType}" varStatus="status">
		        	local_source.push({name:"${testType.name}",id:"${testType.labTestTypeId}"});
		        </c:forEach>
	        </c:if>     
	      
	        var datalist = document.getElementById("testTypeOptions");
			var dataListLength = datalist.options.length;
			if(dataListLength > 0 ) {
				jQuery("#testTypeOptions option").remove();
			}
			
			if(local_source.length > 0) {
				testTypeObject = {};
				jQuery(local_source).each(function() {
					var testTypeName = toTitleCase(this.name.toLowerCase());
			            testTypeOption = "<option value=\"" + this.id + "\">" +testTypeName+ "</option>";
			            jQuery('#testTypeOptions').append(testTypeOption);
			            testTypeId = this.id; 
			            //testTypeObject = {testTypeId,name: testTypeName};
			            testTypeObject[testTypeId] = testTypeName;
				});
			}
			
			jQuery('#testTypeSuggestBox').on('input', function(){
				
				var val = this.value;
		
				if(jQuery('#testTypeOptions option').filter(function(){
			        return this.value === val;        
			    }).length) {
					var datalist = document.getElementById("testTypeOptions");
					var options = datalist.options; 
					document.getElementById('testTypeName').innerHTML = testTypeObject[val]; 
	
				}
			});   
	        
	        
   
	        
	});
	
	function isNumber(evt) {
	    evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	        return false;
	    }
	    return true;
	}
	
	
	function toTitleCase(str) {
	    return str.replace(/(?:^|\s)\w/g, function(match) {
	        return match.toUpperCase();
	    });
	}
	
	/* /*autocomplete ...  */
	/* $(function() {
		 $("#lab_test_type").autocomplete({
			 source : function(request, response) {
				response($.map(local_source, function(item) {
					return {
						value : item.value,
						id:item.id
					}
				}))
			},
			select : function(event, ui) {
				$(this).val(ui.item.value);
				$("#labTestType").val(ui.item.id);
			},
			minLength : 0,
			autoFocus : true
		});	     
	 }); */
	
	
	
	/*Confirmation  Dialog Box  */
	function confirmRetire() {
		//onsubmit="return confirmRetire()"
		if (confirm("Are you sure you want to retire this Test Type? It will be permanently removed from the system.")) {
			return true;
		} else {
			return false;
		}
	}
	
	/*  */
	function confirmDelete() {
		//onsubmit="return confirmDelete()"
		if (confirm("Are you sure you want to retire this Test Type? It will be permanently removed from the system.")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	$j(function() {
		$j('select[name="datatypeClassname"]').change(function() {
			$j('#datatypeDescription').load(openmrsContextPath + '/q/message.form', { key: $j(this).val() + '.description' });
		});
		$j('select[name="preferredHandlerClassname"]').change(function() {
			$j('#handlerDescription').load(openmrsContextPath + '/q/message.form', { key: $j(this).val() + '.description' });  
		});
		<c:if test="${ not empty attributeType.datatypeClassname }">
			$j('#datatypeDescription').load(openmrsContextPath + '/q/message.form', { key: '${ attributeType.datatypeClassname }.description' });
		</c:if>
		<c:if test="${ not empty attributeType.preferredHandlerClassname }">
			$j('#handlerDescription').load(openmrsContextPath + '/q/message.form', { key: '${ attributeType.preferredHandlerClassname }.description' });
		</c:if>
	});
   
	/* Validation */
	function validate(form){
		var testAttributeName = document.getElementById('name').value;
		var labTestType = document.getElementById('testTypeSuggestBox').value;
		var description = document.getElementById('description').value;
		var minOccurs = document.getElementById('min_occurs').value;
		var maxOccurs = document.getElementById('max_occurs').value;
		var sortWeight = document.getElementById('sortWeight').value;
		var  reText = new RegExp("^[A-Za-z][ A-Za-z0-9_().%\\-]*$");
		var regInt =new RegExp("^[0-9]+$");
        var regErrorMesssage ="Text contains Invalid characters.Test Attribute name only accepts alphabets with _ -().% special characters";
		var numericErrorMessage ="Only interger values are allowed";
        var alphabetsCharacter ="Numeric values and special characters are not allowed";
		var numericNotErrorMessage ="Numeric input are not allowed";
		var integerErrorMessage ="Only interger values are allowed";
		var emptyErrorMessage ="This field cannot be empty";
        var isValidate =true; 
		
		if(labTestType == ""){
			document.getElementById("labtesttypeid").style.display= 'block';	
			document.getElementById('labtesttypeid').innerHTML =emptyErrorMessage;
			isValidate = false;
		}
		else if(isNaN(labTestType)){
			document.getElementById("labtesttypeid").style.display= 'block';	
			document.getElementById('labtesttypeid').innerHTML ="Only the autosearch Lab Test Type Id is accepted";
			isValidate = false;
		}
		else {
			document.getElementById("labtesttypeid").style.display= 'none';	
		} 
		
		 if(testAttributeName == ""){
			    document.getElementById("testatrname").style.display= 'block';
				document.getElementById('testatrname').innerHTML =emptyErrorMessage;
				isValidate = false;
			}
		else if(!isNaN(testAttributeName)){
			    document.getElementById("testatrname").style.display= 'block';
				document.getElementById('testatrname').innerHTML ="Only Alphanumeric characters are allowed";
				isValidate = false;
			}
	
	   else if(!reText.test(testAttributeName)){
			   document.getElementById("testatrname").style.display= 'block';
				document.getElementById('testatrname').innerHTML =regErrorMesssage;
				isValidate = false;
		  }
		else {
			document.getElementById("testatrname").style.display= 'none';	
		} 
		 /*Description  */
		 if(description == ""){
			    document.getElementById("atrdescription").style.display= 'block';		
			    document.getElementById('atrdescription').innerHTML =emptyErrorMessage;
				isValidate = false;
			}
		else {
				document.getElementById("atrdescription").style.display= 'none';	
		} 
		 
		 /*Min Occurs  */
		 if(minOccurs == ""){
			  document.getElementById("minoccurs").style.display= 'block';	
				document.getElementById('minoccurs').innerHTML =emptyErrorMessage;
				isValidate = false;
			}
		 
		 else if(isNaN(minOccurs)){
			  document.getElementById("minoccurs").style.display= 'block';	
				document.getElementById('minoccurs').innerHTML =numericErrorMessage;
				isValidate = false;

			}
		 else if (!regInt.test(minOccurs)){
			 	document.getElementById("minoccurs").style.display= 'block';	
				document.getElementById('minoccurs').innerHTML = integerErrorMessage;
				isValidate = false;
		 }
			
			else {
				document.getElementById("minoccurs").style.display= 'none';	
			}
		 
		 /*Min Occurs  */
		 if(maxOccurs == ""){
			  document.getElementById("maxoccurs").style.display= 'block';	
				document.getElementById('maxoccurs').innerHTML =emptyErrorMessage;
				isValidate = false;
			}
		 
		 else if(isNaN(maxOccurs)){
			  document.getElementById("maxoccurs").style.display= 'block';	
				document.getElementById('maxoccurs').innerHTML = numericErrorMessage;
				isValidate = false;

			}
		 else if (!regInt.test(maxOccurs)){
			 	document.getElementById("maxoccurs").style.display= 'block';	
				document.getElementById('maxoccurs').innerHTML = integerErrorMessage;
				isValidate = false;
		 }
		 else if(minOccurs > maxOccurs){
				 document.getElementById("maxoccurs").style.display= 'block';	
				document.getElementById('maxoccurs').innerHTML ="Max Occurs should be greater then min Occurs";
				isValidate = false;
		 } 
		 
		 else {
			document.getElementById("maxoccurs").style.display= 'none';	
		} 
	
		/* sortWeight */
		 if(sortWeight == ""){
			   document.getElementById("sortweight").style.display= 'block';	
				document.getElementById('sortweight').innerHTML =emptyErrorMessage;
				isValidate = false;
			}
		 
		 else if(isNaN(sortWeight)){
			  document.getElementById("sortweight").style.display= 'block';	
				document.getElementById('sortweight').innerHTML = numericErrorMessage;
				isValidate = false;

			}
			 else if (!regInt.test(sortWeight)){
				 	document.getElementById("sortweight").style.display= 'block';	
					document.getElementById('sortweight').innerHTML = integerErrorMessage;
					isValidate = false;
			 }
			 else {
					document.getElementById("sortweight").style.display= 'none';	
				} 
			
		if(isValidate ==true){
			document.getElementById("data_type_name").disabled = false;
			document.getElementById("datatypeConfig").disabled = false;
		}
	
		return isValidate;
	}
	
	//Retire Validate 
	
	function retireValidate(){
		var retireReason = document.getElementById('retireReason').value;
		var isValidate= true;
		if(retireReason == ""){
			document.getElementById('retirereason').innerHTML ="Retire reason cannot be empty";
			isValidate = false;
		}
		else if(!isNaN(retireReason)){
			document.getElementById('retirereason').innerHTML = numericErrorMessage;
			isValidate = false;
		}

	
		return isValidate;
	}
	//check for integer..
	function isInt(value) {
		  return !isNaN(value) && (function(x) { return (x | 0) === x; })(parseFloat(value))
		}

	jQuery(function() {
		  var uuid ='${testAttributeType.uuid}';
          var testAttributeTypeName = '${testAttributeType.name}'
		 if (performance.navigation.type == 1) {
			 if(testAttributeTypeName ==null || testAttributeTypeName == ""){
				 window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestAttributeType.form";
			 	}
				 else{
					 window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestAttributeType.form?uuid="+uuid; 
				 }
			}

		 jQuery("body").keydown(function(e){

		 if(e.which==116){
			 if(testAttributeTypeName ==null || testAttributeTypeName == ""){
				 window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestAttributeType.form";
			 	}
				 else{
					 window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestAttributeType.form?uuid="+uuid; 
				 }		
		   }

		 });
	 });
	
	///Sort Weight Modal 
	
	function showSortWeightList(sortweight){
		var testTypeId = document.getElementById("testTypeSuggestBox").value;
		 if(testTypeId != ""){
			    getTestAttributeType(testTypeId);
		  }
		 else{
			 
		 }
	}
	
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
						resultsItems = resultsItems.concat('<th><a>Test Order</a></th>');
						resultsItems = resultsItems.concat('<th><a>Attribute Type Name</a></th>');
						resultsItems = resultsItems.concat('<th><a>Sort Weight</a></th>');
						jQuery(array).each(function() {
							resultsItems = resultsItems.concat('<tbody><tr>'); 
							resultsItems = resultsItems.concat('<td>'+this.testOrderId+'</td>');
							resultsItems = resultsItems.concat('<td>'+this.attributeTypeName+'</td>');
							resultsItems = resultsItems.concat('<td>'+this.sortWeight+'</td>');
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
<%@ include file="/WEB-INF/template/footer.jsp"%>