<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="View labTestResult" otherwise="/login.htm" redirect="/module/commonlabtest/addLabTestResult.form" />
<openmrs:portlet url="patientHeader" id="patientDashboardHeader" patientId="${patientId}"/>

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
hr.style-three {
	height: 30px;
	border-style: solid;
	border-color: black;
	border-width: 1px 0 0 0;
	border-radius: 20px;
}
hr.style-three:before {
	display: block;
	content: "";
	height: 30px;
	margin-top: -31px;
	border-style: solid;
	border-color: black;
	border-width: 0 0 1px 0;
	border-radius: 20px;
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


</style>

<body>
	
<div class="container"> 

	 <fieldset  class="scheduler-border" >
	 
	 	<c:choose>
	         <c:when test = "${update == false}">
	         	  <legend  class="scheduler-border"><spring:message code="commonlabtest.result.add" /></legend>        
	         </c:when>
	         <c:otherwise>
	            <legend  class="scheduler-border"><spring:message code="commonlabtest.result.edit" /></legend>
	         </c:otherwise>
        </c:choose>
		   <div id="resultContainer">
		   
		   </div> 
		
	 </fieldset>
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
var local_source;
var testOrder ;
var update;
var filepath ="";
var resultComments="";

var testTypeName="";
$(document).ready(function () {
	local_source = getAttributeTypes();
	testOrder = ${testOrderId};
	testTypeName = '${testTypeName}';
	filepath = '${filepath}';
	resultComments = '${resultComments}';
     update ='${update}'
     console.log("Values : "+update);
	if(local_source.length > 0){
		 populateResultForm();
	}	
	

});

//get all concepts
function getAttributeTypes(){
   	return JSON.parse(JSON.stringify(${attributeTypeList}));
   }
//get all concepts
function getTestOrderId(){
   	return ${testOrderId};
   }
function navigatedToPatientDashboard(){
	  window.location.href ="${pageContext.request.contextPath}/patientDashboard.form?patientId=${patientId}";  
}
   
   function populateResultForm(){
	   
     var resultsItems ="";
  
     resultsItems = resultsItems.concat('<form method="post" id="entryForm" action="${pageContext.request.contextPath}/module/commonlabtest/addLabTestResult.form" enctype="multipart/form-data">');
     resultsItems = resultsItems.concat('<input hidden="true" id="testOrderId" name ="testOrderId" value="'+testOrder+'" />');  
     resultsItems = resultsItems.concat('<center><h4>'+testTypeName+'</h4></center><hr class="style-three">');   
     resultsItems = resultsItems.concat('<input  hidden="true" id="update" name ="update" value="'+update+'" />'); 

     jQuery(local_source).each(function() {
    	   
    	    if(this.testAttributeId != 'undefined')
    	    	{
                 resultsItems = resultsItems.concat('<input  hidden="true" id="testAttributeId.'+this.id+'" name ="testAttributeId.'+this.id+'" value="'+this.testAttributeId+'" />'); 
    	    	}
    	    else{
                 resultsItems = resultsItems.concat('<input  hidden="true" id="testAttributeId.'+this.id+'" name ="testAttributeId.'+this.id+'" value="" />'); 
    	      }
			 if(this.dataType == 'coded'){
				  	 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
					 resultsItems = resultsItems.concat(' <label class="control-label">'+this.name+'</label>');
					 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
					 resultsItems = resultsItems.concat('<select class="form-control" id="concept.'+this.id+'" name="concept.'+this.id+'" ><options />');
					 jQuery(this.conceptOptions).each(function() {
						 if(this.value == 'undefined'){
							  resultsItems =resultsItems.concat( '<option value="'+this.conceptId+'">'+this.conceptName+'</option>'); 
						 }
						 else{
							  resultsItems =resultsItems.concat( '<option value="'+this.conceptId+'">'+this.conceptName+'</option>');
						 }
					  });
					 resultsItems =resultsItems.concat('</select></div></div>');
			 }
			 else if(this.dataType == 'Text'){
				 
					 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
					 resultsItems = resultsItems.concat(' <label class="control-label">'+this.name+'</label>');
					 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
					 if(this.value == 'undefined'){
						 resultsItems = resultsItems.concat('<input class="form-control" type="text" size="100" id="valueText.'+this.id+'" name="valueText.'+this.id+'" value="" required  data-error-msg="This field cannot be empty" />');
					 }else{
						 resultsItems = resultsItems.concat('<input class="form-control" type="text" size="100" id="valueText.'+this.id+'" name="valueText.'+this.id+'" value="'+this.value+'" required  data-error-msg="This field cannot be empty" />'); 
					 }
					 resultsItems =resultsItems.concat('</div></div>');
			 }
			 else if(this.dataType == 'Numeric'){
				 
					 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
					 resultsItems = resultsItems.concat(' <label class="control-label">'+this.name+'</label>');
					 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
					 if(this.value == 'undefined'){
						 resultsItems = resultsItems.concat('<input class="form-control" type="number" id="float.'+this.id+'" name="float.'+this.id+'" value="" required />');
					 }
					 else{
						 resultsItems = resultsItems.concat('<input class="form-control" type="number" id="float.'+this.id+'" name="float.'+this.id+'" value="'+this.value+'" required />'); 
					 }
					 resultsItems =resultsItems.concat('</div></div>');
		 	}
			 else if(this.dataType == 'Boolean'){
				 
					 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
					 resultsItems = resultsItems.concat('<label class="control-label">'+this.name+'</label>');
					 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
					 resultsItems = resultsItems.concat('<select class="form-control" id="bool.'+this.id+'" name="bool.'+this.id+'">');
					 if(this.value == 'undefined'){
							 resultsItems = resultsItems.concat('<option value="true" selected>Yes</option>');
							 resultsItems = resultsItems.concat('<option value="false" >No</option>');
					 }else{
						 
						 if(this.value == "true"){
							  resultsItems = resultsItems.concat('<option value="true" selected>Yes</option>');
							  resultsItems = resultsItems.concat('<option value="false" >No</option>'); 
						 }else{
						      resultsItems = resultsItems.concat('<option value="true" >Yes</option>');
							  resultsItems = resultsItems.concat('<option value="false" selected >No</option>'); 
						 }	 
					 }
				 resultsItems =resultsItems.concat('</select></div></div>');
					
		   }else if(this.dataType == 'Date'){
			     resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
				 resultsItems = resultsItems.concat('<label class="control-label">'+this.name+'</label>');
				 resultsItems = resultsItems.concat('</div><div class ="col-md-4">');
				 if(this.value == 'undefined'){
					 resultsItems = resultsItems.concat('<input  id="date.'+this.id+'" name="date.'+this.id+'" type="date" value="">');
				 }else{
					 resultsItems = resultsItems.concat('<input id="date.'+this.id+'" name="date.'+this.id+'" type="date" value="'+this.value+'" required>');
				 }
				 resultsItems =resultsItems.concat('</div></div>');
		   } 
	     });            
					resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
					resultsItems = resultsItems.concat('<label class="control-label">Comments</label>');
					resultsItems = resultsItems.concat('</div><div class="col-md-4">');
					  if(resultComments != ""){
						  resultsItems = resultsItems.concat('<textarea class="form-control"  maxlength="512"  name="resultComments" id="resultComments" value ="'+resultComments+'" >'+resultComments+'</textarea>');
					  }else {
						  resultsItems = resultsItems.concat('<textarea  class="form-control"  maxlength="512"  name="resultComments" id="resultComments" value ="" />'); 
					  }
					resultsItems = resultsItems.concat('</div></div>');
     				//file attachment	
	   				 resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3">');
	   				 resultsItems = resultsItems.concat('<label class="control-label">Attachment</label>');
	   				 resultsItems = resultsItems.concat('</div><div class="col-md-4">');
	   				 resultsItems = resultsItems.concat('<input type="file" name="documentTypeFile" id="documentTypeFile" accept="image/*,audio/*,video/*,application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document" />');
	   				 if(filepath != ""){
	   					 resultsItems = resultsItems.concat('</div><div class="col-md-4">');
		   				 resultsItems = resultsItems.concat('<a style="text-decoration:none"  href="'+filepath+'" target="_blank" title="Image" class="hvr-icon-grow" ><i class="fa fa-paperclip hvr-icon"></i> Attached report file</a>'); 					 
	   				 }
	   			     resultsItems = resultsItems.concat('</div></div>');
	   			     resultsItems = resultsItems.concat('<div class="row"><div class="col-md-3"></div><div class="col-md-6">');
	   				 resultsItems = resultsItems.concat('<label class="control-label text-danger" id="documenttypefile"></label>');
	   			     resultsItems = resultsItems.concat('</div></div>');
				     resultsItems = resultsItems.concat('<div class="row"><div class="col-md-2">');
				     resultsItems = resultsItems.concat('<input type="submit" value="Save Test Result" id="submitBttn" ></input>');
				     resultsItems = resultsItems.concat('</div><div class="col-md-2">');
				     resultsItems = resultsItems.concat('<input type="button" onclick="navigatedToPatientDashboard();" value="Cancel"></input>');
				     resultsItems = resultsItems.concat('</div></div></form>');
		   			 //voided Form
				  /*  if(update == true){
					   resultsItems = resultsItems.concat('<fieldset  class="scheduler-border">');
					   resultsItems = resultsItems.concat('<legend  class="scheduler-border"><spring:message code="commonlabtest.result.void" /></legend>');
					  		resultsItems = resultsItems.concat('<form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/module/commonlabtest/retirelabtestattributetype.form" onsubmit="return retireValidate()" >');
						  		resultsItems = resultsItems.concat(' <div class="row"><div class="col-md-2">');
							  		resultsItems = resultsItems.concat('<input value="${testAttributeType.uuid}" hidden="true"  id="uuid" name="uuid"></input>');
							  		resultsItems = resultsItems.concat('<label  class="control-label" ><spring:message code="general.voidReason" /><span class="text-danger required">*</span></label>');
						  		resultsItems = resultsItems.concat('</div><div class="col-md-6">');
						  			resultsItems = resultsItems.concat('<input class="form-control" value="" id="voidReason" name="voidReason" >');
						  		resultsItems = resultsItems.concat('</div></div>');
						  		resultsItems = resultsItems.concat('<div  class="row"><div class="col-md-2">');
						  			resultsItems = resultsItems.concat('<input type="submit" value="<spring:message code="commonlabtest.result.void" />"></input>');
						  		resultsItems = resultsItems.concat('</div></div>');
						  	resultsItems = resultsItems.concat('</form>');	
					  	
				   } */
		  
				     

	    $("#resultContainer").append(resultsItems);
	   
   }
   function setValue(checkboxElem) {
	   $(checkboxElem).val(checkboxElem.checked ? true : false);
	 }
   
   function textValidation(textElem){
	   if($(textElem).val() == "" && $(textElem).val() == null){
		   console.log("Text Input : "+$(textElem).val()); 
	   }
   }
   
   var _validFileExtensions = [".exe",".zip",".msi",".sql"];   
   $(function() {
	     $("input:file").change(function (){
	    	 var fileName = $(this).val();
	    	 if (fileName.length > 0) {
                 var blnValid = false;
                 for (var j = 0; j < _validFileExtensions.length; j++) {
                     var sCurExtension = _validFileExtensions[j];
                     if (fileName.substr(fileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                    	 blnValid = true;
                         break;
                     }
                 }
                 
                 if (blnValid) {
         			    document.getElementById("documenttypefile").style.display= 'block';
         				document.getElementById('documenttypefile').innerHTML = "Sorry,"+ fileName + " is invalid, not allowed extensions are: " + _validFileExtensions.join(", ");
         				$( "#documentTypeFile" ).val("");
         				//document.getElementById('submitBttn').disabled = true;
                 }else if(!blnValid){
         			   document.getElementById("documenttypefile").style.display= 'none';	
         			  // document.getElementById('submitBttn').disabled = false;
                 }
             }
	     });
	  });
   
</script>


<%@ include file="/WEB-INF/template/footer.jsp"%>