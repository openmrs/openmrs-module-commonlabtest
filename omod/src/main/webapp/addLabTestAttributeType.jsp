<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include
	file="/WEB-INF/view/module/commonlabtest/include/localHeader.jsp"%>
<!-- <openmrs:require anyPrivilege ="Add CommonLabTest Metadata , Edit CommonLabTest Metadata" otherwise="/login.htm" redirect="/module/commonlabtest/addLabTestAttributeType.form" />
 -->

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
	-webkit-box-shadow: 0px 0px 0px 0px #1aac9b;
	box-shadow: 0px 0px 14px 0px #1aac9b61;
}

legend.scheduler-border {
	font-size: 1.2em !important;
	font-weight: bold !important;
	text-align: left !important;
	width: auto;
	padding: 0 10px;
	border-bottom: none;
}

.row {
	margin-bottom: 15px;
}

.modal-body {
	height: 500px;
	overflow-y: scroll;
}
</style>

<body>


	<div class="container">

		<c:if test="${not empty error}">
			<div class="alert alert-danger">
				<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>Error!</strong>
				<c:out value="${error}" />
			</div>
		</c:if>
		<c:set var="testAttributeType" scope="session"
			value="${attributeType}" />
		<fieldset class="scheduler-border">
			<c:if test="${empty testAttributeType.name}">
				<openmrs:require privilege="Add CommonLabTest Metadata"
					otherwise="/login.htm"
					redirect="/module/commonlabtest/addLabTestAttributeType.form" />
				<legend class="scheduler-border">
					<spring:message code="commonlabtest.labtestattributetype.add" />
				</legend>
			</c:if>
			<c:if test="${not empty testAttributeType.name}">
				<openmrs:require privilege="Edit CommonLabTest Metadata"
					otherwise="/login.htm"
					redirect="/module/commonlabtest/addLabTestAttributeType.form" />
				<legend class="scheduler-border">
					<spring:message code="commonlabtest.labtestattributetype.edit" />
				</legend>
			</c:if>
			<form:form commandName="attributeType"
				onsubmit='return validate(this);'>
				<div class="row">
					<div class="col-md-2">
						<form:input path="labTestAttributeTypeId" hidden="true"
							id="labTestAttributeTypeId"></form:input>
						<form:label path="labTestType.labTestTypeId" class="control-label">
							<spring:message code="general.labTestType" />
							<span class="text-danger required">*</span>
						</form:label>
					</div>
					<div class="col-md-6">
						<form:input class="form-control" id="testTypeSuggestBox"
							path="labTestType.labTestTypeId" list="testTypeOptions"
							placeholder="Search Test Type..."></form:input>
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
						<form:label path="name" class="control-label">
							<spring:message code="general.name" />
							<span class="text-danger required">*</span>
						</form:label>
					</div>
					<div class="col-md-6">
						<form:input class="form-control" maxlength="50" path="name"
							id="name"></form:input>
						<span id="testatrname" class="text-danger "> </span>
					</div>
				</div>
				<!-- Description -->
				<div class="row">
					<div class="col-md-2">
						<form:label path="description" class="control-label">
							<spring:message code="general.description" />
							<span class="text-danger required">*</span>
						</form:label>
					</div>
					<div class="col-md-6">
						<form:textarea class="form-control" maxlength="255"
							path="description" id="description"></form:textarea>
						<span id="atrdescription" class="text-danger "> </span>
					</div>
				</div>
				<div class="row">
					<div class="col-md-2">
						<form:label path="multisetName" class="control-label">
							<spring:message
								code="commonlabtest.labtestattributetype.multisetName" />
						</form:label>
					</div>
					<div class="col-md-6">
						<form:input class="form-control" maxlength="255"
							path="multisetName" id="multisetName"></form:input>
						</td> <span id="multisetname" class="text-danger "> </span>
					</div>
				</div>
				<!-- Group Name-->
				<div class="row">
					<div class="col-md-2">
						<form:label path="groupName" class="control-label">
							<spring:message
								code="commonlabtest.labtestattributetype.groupName" />
						</form:label>
					</div>
					<div class="col-md-6">
						<form:input class="form-control" maxlength="255" path="groupName"
							id="group_name"></form:input>
						</td> <span id="groupname" class="text-danger "> </span>
					</div>
				</div>
				<!-- Min Ocurance -->
				<div class="row">
					<div class="col-md-2">
						<form:label path="minOccurs" class="control-label">
							<spring:message code="general.minOccurs" />
							<span class="text-danger required">*</span>
						</form:label>
					</div>
					<div class="col-md-6">
						<form:input class="form-control" maxlength="2" path="minOccurs"
							id="min_occurs" onkeypress="return isNumber(event)"></form:input>
						</td> <span id="minoccurs" class="text-danger "> </span>
					</div>
				</div>
				<!-- max occurs -->
				<div class="row">
					<div class="col-md-2">
						<form:label path="maxOccurs" class="control-label">
							<spring:message code="general.maxOccurs" />
							<span class="text-danger required">*</span>
						</form:label>
					</div>
					<div class="col-md-6">
						<form:input class="form-control" maxlength="2" path="maxOccurs"
							id="max_occurs" onkeypress="return isNumber(event)"></form:input>
						<span id="maxoccurs" class="text-danger "> </span>
					</div>
				</div>
				<!-- sort Weight -->
				<div class="row">
					<div class="col-md-2">
						<form:label path="sortWeight" class="control-label">
							<spring:message code="general.sortWeight" />
							<span class="text-danger required">*</span>
						</form:label>
					</div>
					<div class="col-md-6">
						<form:input class="form-control" maxlength="2" path="sortWeight"
							id="sortWeight" onkeypress="return isNumber(event)"></form:input>
						<span id="sortweight" class="text-danger "> </span>
					</div class="col-md-4">
					<a style="text-decoration: none"
						onclick="showSortWeightList(this);" id="addTestSamples"
						class="hvr-icon-grow"><i class="fa fa-eye hvr-icon"></i> Sort
						Weight hierarchy</a>
					<div></div>
				</div>

				<!-- datatypeClassname -->
				<div class="row">
					<div class="col-md-2">
						<form:label path="datatypeClassname" class="control-label">
							<spring:message code="general.dataType" />
						</form:label>
					</div>
					<div class="col-md-6">

						<c:if test="${available != true}">

							<form:select class="form-control" path="datatypeClassname"
								id="data_type_name">
								<form:options />
								<c:forEach items="${datatypes}" var="datatype">
									<form:option item="${datatype}" value="${datatype}">
										<c:out value="${datatype}.name" />
									</form:option>
								</c:forEach>
							</form:select>
						</c:if>
						<c:if test="${available == true}">
							<form:select class="form-control" disabled="true"
								path="datatypeClassname" id="data_type_name">
								<form:options />
								<c:forEach items="${datatypes}" var="datatype">
									<form:option item="${datatype}" value="${datatype}">
										<c:out value="${datatype}.name" />
									</form:option>
								</c:forEach>
							</form:select>
						</c:if>
					</div>
					<div class="col-md-4">
						<font color="#D0D0D0"><span id="datatypeDescription"></span></font>
					</div>
				</div>
				<div class="row" id="radioOptions">
					<div class="col-md-2"></div>
					<div class="col-sm-6 col-md-6 col-lg-6">
						<c:if test="${available != true}">
							<label class="radio-inline"><input type="radio"
								name="optradio" onclick="showOptions()" id="regex">Regex</label>
							<label class="radio-inline"><input type="radio"
								name="optradio" onclick="showOptions()" id="length">Length</label>
							<label class="radio-inline"><input type="radio"
								name="optradio" onclick="showOptions()" id="range">Range</label>
						</c:if>
						<c:if test="${available == true}">
							<label class="radio-inline"><input disabled type="radio"
								name="optradio" onclick="showOptions()" id="regex">Regex</label>
							<label class="radio-inline"><input disabled type="radio"
								name="optradio" onclick="showOptions()" id="length">Length</label>
							<label class="radio-inline"><input disabled type="radio"
								name="optradio" onclick="showOptions()" id="range">Range</label>
						</c:if>

					</div>
				</div>
				<!-- datatypeConfig -->
				<div class="row">
					<div class="col-sm-2 col-md-2 col-lg-2">
						<form:label path="datatypeConfig" class="control-label">
							<spring:message code="general.datatypeConfiguration" />
						</form:label>
					</div>
					<div class="col-sm-6 col-md-6 col-lg-6">
						<c:if test="${available != true}">
							<form:textarea class="form-control" path="datatypeConfig"
								id="datatypeConfig"></form:textarea>
							<span id="datatypeconfig" class="text-danger "> </span>
						</c:if>
						<c:if test="${available == true}">
							<form:textarea class="form-control" disabled="true"
								path="datatypeConfig" id="datatypeConfig"></form:textarea>
							<span id="datatypeconfig" class="text-danger "> </span>
						</c:if>
					</div>
				</div>
				<!--Regex Hints -->
				<div class="row" id="hint_field">
					<div class="col-md-2">
						<form:label path="hint" class="control-label">
							<spring:message code="general.hint" />
						</form:label>
					</div>
					<div class="col-md-6">
						<form:input class="form-control" path="hint" id="hint"
							maxlength="50"></form:input>
						<span id="hints" class="text-danger "> </span>
					</div>
				</div>

				<!-- preferredHandlerClassname -->
				<div class="row">
					<div class="col-md-2">
						<form:label path="preferredHandlerClassname" class="control-label">
							<spring:message code="general.preferredHandler" />
						</form:label>
					</div>
					<div class="col-md-6">
						<form:select class="form-control" path="preferredHandlerClassname"
							id="preferred_handler_name">
							<option value=""><openmrs:message code="general.default" /></option>
							<c:forEach items="${handlers}" var="handler">
								<%-- 											<option value="${handler}" <c:if test="${handler == status.value}">selected</c:if>><spring:message code="${handler}.name"/></option>
 --%>
								<form:option item="${handler}" value="${handler}">
									<c:out value="${handler}.name" />
								</form:option>
							</c:forEach>
						</form:select>
					</div>
					<div class="col-md-4">
						<font color="#D0D0D0"><span id="handlerDescription"></span></font>
					</div>
				</div>
				<!-- handlerConfig-->
				<div class="row">
					<div class="col-md-2">
						<form:label class="control-label" path="handlerConfig">
							<spring:message code="general.handlerConfiguration" />
						</form:label>
					</div>
					<div class="col-md-6">
						<form:textarea class="form-control" path="handlerConfig"
							id="handlerConfig"></form:textarea>
					</div>
				</div>
				<c:if test="${not empty testAttributeType.name}">
					<div class="row">
						<div class="col-md-2">
							<form:label path="creator">
								<spring:message code="general.createdBy" />
							</form:label>
						</div>
						<div class="col-md-6">
							<c:out value="${testAttributeType.creator.personName}" />
							-
							<c:out value="${testAttributeType.dateCreated}" />
						</div>
					</div>
					<div class="row">
						<div class="col-md-2">
							<font color="#D0D0D0"><sub><spring:message
										code="general.uuid" /></sub></font>
						</div>
						<div class="col-md-6">
							<font color="#D0D0D0"><sub><c:out
										value="${testAttributeType.uuid}" /></sub></font>
						</div>
					</div>
				</c:if>
				<!-- Save -->
				<div class="row">
					<div class="col-md-3">
						<input type="submit"
							value="<spring:message code="commonlabtest.labtestattributetype.save" />"></input>
					</div>
					<div class="col-md-2">
						<input type="button"
							onclick="location.href = '${pageContext.request.contextPath}/module/commonlabtest/manageLabTestAttributeTypes.form';"
							value="Cancel"></input>
					</div>
				</div>
			</form:form>
		</fieldset>
		<br>
		<openmrs:hasPrivilege privilege="Delete CommonLabTest Metadata">
			<c:if test="${not empty testAttributeType.name}">
				<fieldset class="scheduler-border">
					<legend class="scheduler-border">
						<spring:message code="general.test.retire" />
					</legend>
					<form class="form-horizontal" method="post"
						action="${pageContext.request.contextPath}/module/commonlabtest/retirelabtestattributetype.form"
						onsubmit="return retireValidate()">
						<!-- UUID -->
						<div class="row">
							<div class="col-md-2">
								<input value="${testAttributeType.uuid}" hidden="true" id="uuid"
									name="uuid"></input> <label class="control-label"
									path="retireReason"><spring:message
										code="general.retireReason" /><span
									class="text-danger required">*</span></label>
							</div>
							<div class="col-md-6">
								<input class="form-control"
									value="${testAttributeType.retireReason}" id="retireReason"
									name="retireReason"> <span id="retirereason"
									class="text-danger "> </span>

							</div>
						</div>
						<!-- Retire -->
						<div class="row">
							<div class="col-md-2">
								<input type="submit"
									value="<spring:message code="general.test.retire" />"></input>
							</div>
						</div>
					</form>
				</fieldset>
			</c:if>
		</openmrs:hasPrivilege>
	</div>
	<div class="modal fade right modal-scrolling" id="sortWeightModal"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		data-backdrop="false" style="display: none;" aria-hidden="true">
		<div
			class="modal-dialog modal-side modal-top-right modal-notify modal-info"
			role="document">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #1aac9b">
					<p class="heading lead white-text">Sort Weight Order</p>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true" class="white-text">×</span>
					</button>
				</div>
				<div class="modal-body">
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
	var localSource;

	jQuery(document).ready(function() {
	
		localSource = new Array();
	        <c:if test="${not empty listTestType}">
		        <c:forEach var="testType" items="${listTestType}" varStatus="status">
		        localSource.push({name:"${testType.name}",id:"${testType.labTestTypeId}"});
		        </c:forEach>
	        </c:if>     
	      
	        var datalist = document.getElementById("testTypeOptions");
			var dataListLength = datalist.options.length;
			if(dataListLength > 0 ) {
				jQuery("#testTypeOptions option").remove();
			}
			
			if(localSource.length > 0) {
				testTypeObject = {};
				jQuery(localSource).each(function() {
					var testTypeName = toTitleCase(this.name.toLowerCase());
			            testTypeOption = "<option value=\"" + this.id + "\">" +testTypeName+ "</option>";
			            jQuery('#testTypeOptions').append(testTypeOption);
			            testTypeId = this.id; 
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
			
      let available = '${available}';
      if(!available){   	  
    	  let dataType = document.getElementById('data_type_name');
          if(dataType.childElementCount != 0){
   		  	 let dataTypeName =   dataType.options[dataType.selectedIndex].text;
         		 if( dataTypeName == "org.openmrs.customdatatype.datatype.LongFreeTextDatatype.name" 
   			     || dataTypeName == "org.openmrs.customdatatype.datatype.FreeTextDatatype.name"){
   	  		        document.getElementById("range").disabled=true;
   	         }else{
   	        	   document.getElementById("range").disabled=false; 
   	         } 
          } 
      }
      
	   if(hint()){
			$("#hint_field").show();
			$("#radioOptions").show();
		}else{
			$("#hint_field").hide();
			$("#radioOptions").hide();
		}		
	        
	});
	
	function showOptions(){
		 	console.log("showOps");
		    let regex = document.getElementById("regex");
	        let range = document.getElementById("range");
	        let length = document.getElementById("length");	
	       // $('datatypeConfig').val('');
	    if(regex.checked){
	    	document.getElementById('datatypeConfig').value  = '';
			}else if(range.checked){
				document.getElementById('datatypeConfig').value  = '';
			}
			else if(length.checked){
				document.getElementById('datatypeConfig').value  = '';
			} 
	}
	
	function hint(){	
		console.log("HInt called ");
	  let dataTypeName = document.getElementById('data_type_name').value;	
	   if(dataTypeName != "" && dataTypeName != null){
		   if(dataTypeName == "org.openmrs.customdatatype.datatype.RegexValidatedTextDatatype" ||
				   dataTypeName == "org.openmrs.customdatatype.datatype.FreeTextDatatype" ||
				   dataTypeName == "org.openmrs.customdatatype.datatype.FloatDatatype" ||
				   dataTypeName == "org.openmrs.customdatatype.datatype.LongFreeTextDatatype"){
			   return true;
			   console.log("HInt called  true");
		   }else{
			   return false;
				console.log("HInt called  false");
		   }
	   }
	}
	
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
	//when dataType change 
	jQuery('#data_type_name').on('input', function(){
		  let dataType = document.getElementById('data_type_name');
		  if(dataType.childElementCount != 0){
			  	 let dataTypeName =   dataType.options[dataType.selectedIndex].text;
			  	 console.log("dataTypeName : "+ dataTypeName);
		  		$('input:radio[name=optradio]').each(function () { $(this).prop('checked', false); });
		  		 document.getElementById('datatypeConfig').value  = '';	
			  	 if(dataTypeName == "org.openmrs.customdatatype.datatype.RegexValidatedTextDatatype.name"
			  			 || dataTypeName == "org.openmrs.customdatatype.datatype.FloatDatatype.name" 
			  			 || dataTypeName == "org.openmrs.customdatatype.datatype.LongFreeTextDatatype.name" 
			  			 || dataTypeName == "org.openmrs.customdatatype.datatype.FreeTextDatatype.name"){
			  		if(!$('#radioOptions').is(':visible'))
			  		{  
			  			 $("#radioOptions").show();
			  		}
			  	 }else{
			  		     $("#radioOptions").hide();
			  	 }
			  	 //range show and hide 
			  	 if( dataTypeName == "org.openmrs.customdatatype.datatype.LongFreeTextDatatype.name" 
		  			 || dataTypeName == "org.openmrs.customdatatype.datatype.FreeTextDatatype.name"){
			  		    document.getElementById("range").disabled=true;
		  	         }else{
		  	        	 document.getElementById("range").disabled=false; 
		  	         }

			     if(hint()){
			    	 $("#hint_field").show();
			     }else{
			    	 $("#hint_field").hide();
			     }
		  }
	});

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
		var testAttributeName = document.getElementById('name').value.trim();
		var labTestType = document.getElementById('testTypeSuggestBox').value;
		var minOccurs = document.getElementById('min_occurs').value;
		var maxOccurs = document.getElementById('max_occurs').value;
		var sortWeight = document.getElementById('sortWeight').value;
		var hintVal = document.getElementById('hint').value;
		var datatypeConfigVal = document.getElementById('datatypeConfig').value;
		var dataType = document.getElementById('data_type_name');
		
		///error message and regex
		var  reText = new RegExp("^[A-Za-z][ A-Za-z0-9_()?/µ.%\\-]*$");
		var regInt =new RegExp("^[0-9]+$");
        var regErrorMesssage ="Text contains Invalid characters. Test Attribute name only accepts Alpha-Numeric value with _ -().% special characters. Please remove if there are any white spaces at the start of name";
		var numericErrorMessage ="Only interger values are allowed";
        var alphabetsCharacter ="Numeric values and special characters are not allowed";
		var numericNotErrorMessage ="Numeric input are not allowed";
		var integerErrorMessage ="Only interger values are allowed";
		var emptyErrorMessage ="This field cannot be empty";
		var regexFormat = "/[^$]/";
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
			 else {
					document.getElementById("sortweight").style.display= 'none';	
				}
		
			 var dataTypeOp = dataType.options[dataType.selectedIndex].value;
			 
			 if(dataTypeOp != ""){
				 var dataTypeConfig = document.getElementById('datatypeConfig').value;
				     if(dataTypeOp == "org.openmrs.customdatatype.datatype.ConceptDatatype"){
				    	 if(dataTypeConfig != "" && dataTypeConfig != null ){
				        		if(checkObjectExist(dataTypeConfig)){
				        			 document.getElementById("datatypeconfig").style.display= 'none';
				        		 }else{	        			 
				        			  document.getElementById("datatypeconfig").style.display= 'block';	
				      				  document.getElementById('datatypeconfig').innerHTML = "Either concept does not exist in concept dictionary or please enter valid concept UUID";
				      				  isValidate = false;
				        		 }
				          }
				     }else if(dataTypeOp == "org.openmrs.customdatatype.datatype.RegexValidatedTextDatatype"){
						 console.log("dataTypeConfig : "+dataTypeConfig);
				    	 if(dataTypeConfig != "" && dataTypeConfig != null ){
				    		  document.getElementById("datatypeconfig").style.display= 'none';
				    		
				    	 }else{
				    		  document.getElementById("datatypeconfig").style.display= 'block';	
		      				  document.getElementById('datatypeconfig').innerHTML = "Please enter your regex for this attribute type.";
		      				  isValidate = false; 
				    	 }
				     }
			 }else{
        	     document.getElementById("datatypeconfig").style.display= 'none';
	          }
			 console.log("datatype : "+isValidate);
			 if(hint()){
				   if(!checkOptions()){
					   isValidate = false;  
				   } //this check the 
			      console.log(" config Values : "+getConfigVal(datatypeConfigVal)); 
			 	  if(getConfigVal(datatypeConfigVal) != "" && getConfigVal(datatypeConfigVal) != null){ ///user type anthing the hint is mandatory 
	
					  if(hintVal == "" || hintVal == null){
		    			  document.getElementById("hints").style.display= 'block';	
	     				  document.getElementById('hints').innerHTML = emptyErrorMessage;
	     				  isValidate = false; 
		    		 }else{
	   		    	     document.getElementById("hints").style.display= 'none';
		    		 } 
				 }	   
			 }
			 
			 console.log("hint : "+isValidate);
			 
			  if(isValidate ==true){
				document.getElementById("data_type_name").disabled = false;
				document.getElementById("datatypeConfig").disabled = false;
				 document.getElementById("range").disabled=false;
			}

		console.log("isValidate : "+isValidate);	  
		return isValidate;
	}
	
	function checkOptionAlreadyAppend(value,parentType){
			   var resultConfig = [];
			   let index = value.indexOf("=");  //Split the type (Length ,Regex and range)and value
			   let type = value.substr(0, index); // Type 
			   console.log("Confi Append : "+ type);
			   if(parentType === type){
				   return true;
			   }else if(type != "" && type != null ){
				    if(type != parentType){
	     				 document.getElementById("datatypeconfig").style.display= 'block';	
	     				 document.getElementById('datatypeconfig').innerHTML = "The first part of string should be equal to '"+parentType+"='";
	     				return false; 
	     		  }else{
	     				document.getElementById('datatypeConfig').value = parentType+'='+value;
	     				document.getElementById("datatypeconfig").style.display= 'none';
	     				return true;
	     		  } 
			   }else{
					document.getElementById('datatypeConfig').value = parentType+'='+value;
     				document.getElementById("datatypeconfig").style.display= 'none';
     				return true;
			   } 
	}
	
	function checkOptions(){

	    let regex = document.getElementById("regex");
        let range = document.getElementById("range");
        let length = document.getElementById("length");	
        let dataTypeConfig = document.getElementById('datatypeConfig').value;
    	let integerErrorMessage ="Length should be in interger";
    	let regInt =new RegExp("^[0-9]+$");
    	let regRange =new RegExp("^[\\d.]+-[\\d.]+$");
    
        let isValidate =true;
        if(regex.checked){
        	if(dataTypeConfig !="" && dataTypeConfig != null){
        		if(getConfigVal(dataTypeConfig) == "" || getConfigVal(dataTypeConfig) == null ){
        			  document.getElementById("datatypeconfig").style.display= 'block';	
     				  document.getElementById('datatypeconfig').innerHTML = "Regex expression required.";
     				  isValidate = false; 
        		}else{	
        			if(!checkOptionAlreadyAppend(dataTypeConfig,'Regex')){
        				 isValidate = false; 
        			 }else{
         				document.getElementById("datatypeconfig").style.display= 'none';
        			 }
        		}
			}else{
				  document.getElementById("datatypeconfig").style.display= 'block';	
 				  document.getElementById('datatypeconfig').innerHTML = "Regex expression required.";
 				  isValidate = false;  
			}
		}else if(range.checked){
			if(dataTypeConfig !="" && dataTypeConfig != null){
					
					 if(getConfigVal(dataTypeConfig) == "" || getConfigVal(dataTypeConfig) == null ){
		      			  document.getElementById("datatypeconfig").style.display= 'block';	
		   				  document.getElementById('datatypeconfig').innerHTML = "Range required.";
		   				  isValidate = false; 
						}
		      		 else{
			      			 //check the given range in proper formate or not 
			      			let rangVal =  getConfigVal(dataTypeConfig).trim();
			      			let index = rangVal.indexOf("-");
			  			    let startPoint = rangVal.substr(0, index); 
			  			    let endPoint = rangVal.substr(index + 1);
			  			      console.log("rangVal : "+regRange.test(rangVal));
			  			    if(!checkOptionAlreadyAppend(dataTypeConfig ,'Range')){
			  			    	isValidate = false; 
							} 
				  			 if(!regRange.test(rangVal)){
			      				  document.getElementById("datatypeconfig").style.display= 'block';	
			      				  document.getElementById('datatypeconfig').innerHTML = "Invalide range pattern (startDigit - endDigit)";
			      				  isValidate = false; 
			      			 } else if(parseInt(startPoint, 10) > parseInt(endPoint, 10)){
			      				  document.getElementById("datatypeconfig").style.display= 'block';	
			    				  document.getElementById('datatypeconfig').innerHTML = "In range startNumber should be greater then endNumber";
			    				  isValidate = false;  
			      			 }else{
			       				document.getElementById("datatypeconfig").style.display= 'none';
			      			 }
	      	    	}	 
				}else{
					  document.getElementById("datatypeconfig").style.display= 'block';	
	  				  document.getElementById('datatypeconfig').innerHTML = "Range required.";
	  				  isValidate = false; 
				}
		}
		else if(length.checked){
			if(dataTypeConfig !="" && dataTypeConfig != null){
					if(getConfigVal(dataTypeConfig) == "" || getConfigVal(dataTypeConfig) == null ){
		      			  document.getElementById("datatypeconfig").style.display= 'block';	
		   				  document.getElementById('datatypeconfig').innerHTML = "Length required.";
		   				  isValidate = false; 
		      		}
					else if (!regInt.test(getConfigVal(dataTypeConfig))){
					 	document.getElementById("datatypeconfig").style.display= 'block';	
						document.getElementById('datatypeconfig').innerHTML = integerErrorMessage;
						isValidate = false;
				   }else{
					    if(!checkOptionAlreadyAppend(dataTypeConfig ,'Length')){
					    	isValidate = false;
						   }else{
		     				document.getElementById("datatypeconfig").style.display= 'none'; 
		     			 }
			
				   }
			}else{
				  document.getElementById("datatypeconfig").style.display= 'block';	
  				  document.getElementById('datatypeconfig').innerHTML = "Length required.";
  				  isValidate = false; 
			}
        }
        return isValidate;
     }
	
	function getConfigVal(dataTypeConfig){		
		 let index = dataTypeConfig.indexOf("=");  //Split the type blc we need to 
		  let valueConfig = dataTypeConfig.substr(index + 1); 
		 return valueConfig;
	}
	function getConfigType(dataTypeConfig){		
		 let index = dataTypeConfig.indexOf("=");  //Split the type blc we need to 
		 let type = dataTypeConfig.substr(0,index); 
		 return type;
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
		 if(testTypeId != "" && isInt(testTypeId)){
			    getTestAttributeType(testTypeId);
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
						resultsItems = resultsItems.concat('<th><a>Test Type</a></th>');
						resultsItems = resultsItems.concat('<th><a>Attribute Type Name</a></th>');
						resultsItems = resultsItems.concat('<th><a>Sort Weight</a></th>');
						resultsItems = resultsItems.concat('<th><a>Multiset Name</a></th>');
						resultsItems = resultsItems.concat('<th><a>Group Name</a></th>');
						jQuery(array).each(function() {
							resultsItems = resultsItems.concat('<tbody><tr>'); 
							resultsItems = resultsItems.concat('<td>'+this.testTypeId+'</td>');
							resultsItems = resultsItems.concat('<td>'+this.attributeTypeName+'</td>');
							resultsItems = resultsItems.concat('<td>'+this.sortWeight+'</td>');
							resultsItems = resultsItems.concat('<td>'+this.multisetName+'</td>');
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
	
	//CHECK THE CONCEPT EXIST
	function checkObjectExist(uuid){
		var isExist =false;
		 $.ajax({
				type : "GET",
				contentType : "application/json",
				url : '${pageContext.request.contextPath}/module/commonlabtest/getConceptExist.form?conceptUuid='+uuid,
				async:false,
				dataType : "json",
				success : function(data) {
				   console.log("success  : " + data);	
				   isExist = data;
				},
				error : function(data) {
					  console.log("fail  : " + data);
					  isExist =false;
				},
				done : function(e) {
					console.log("DONE");
					isExist = false;
				}
		});
	  return isExist;	
	}

 

</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>