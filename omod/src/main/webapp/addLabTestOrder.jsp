<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:portlet url="patientHeader" id="patientDashboardHeader"
	patientId="${patientId}" />
<!-- <openmrs:require anyPrivilege="Add CommonLabTest Orders,Edit CommonLabTest Orders" otherwise="/login.htm" redirect="/module/commonlabtest/addLabTestRequest.form" />
 -->
<link type="text/css" rel="stylesheet"
	href="/openmrs/moduleResources/commonlabtest/css/commonlabtest.css" />
<link
	href="/openmrs/moduleResources/commonlabtest/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" />
<link
	href="/openmrs/moduleResources/commonlabtest/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="/openmrs/moduleResources/commonlabtest/css/waitMe.min.css"
	rel="stylesheet" />

<style>
body {
	font-size: 12px;
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
	box-shadow: 0px 0px 0px 0px #1aac9b;
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
</style>

<body id="container">

	<c:if test="${not empty error}">
		<div class="alert alert-danger">
			<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>Error!</strong>
			<c:out value="${error}" />
		</div>
	</c:if>
	<c:set var="testOrder" scope="session" value="${labTest}" />

	<fieldset class="scheduler-border" style="margin-top: 320px;">
		<c:if test="${empty labTest.labReferenceNumber}">
			<openmrs:require privilege="Add CommonLabTest Orders"
				otherwise="/login.htm"
				redirect="/module/commonlabtest/addLabTestRequest.form" />
			<legend class="scheduler-border">
				<spring:message code="commonlabtest.order.add" />
			</legend>
		</c:if>
		<c:if test="${not empty labTest.labReferenceNumber}">
			<openmrs:require privilege="Edit CommonLabTest Orders"
				otherwise="/login.htm"
				redirect="/module/commonlabtest/addLabTestRequest.form" />
			<legend class="scheduler-border">
				<spring:message code="commonlabtest.order.edit" />
			</legend>
		</c:if>
		<form:form commandName="labTest" id="labTestForm"
			onsubmit="return validate()">
			<form:input path="order.patient" hidden="true" value="${patientId}"></form:input>
			<form:input path="order.concept.conceptId" hidden="true"
				id="conceptId"></form:input>
			<form:input path="order.orderer.providerId" hidden="true"
				value="${provider.providerId}"></form:input>
			<form:input path="order.orderType.orderTypeId" hidden="true"
				value="3"></form:input>
			<form:input path="order.orderId" hidden="true" id="orderId"></form:input>
			<form:input path="resultComments" hidden="true" id="resultComments"></form:input>
			<form:input path="filePath" hidden="true" id="filePath"></form:input>
			<div class="row">
				<div class="col-md-3">
					<form:label class="control-label" path="order.encounter">
						<spring:message code="general.encounter" />
						<span class=" text-danger required">*</span>
					</form:label>
				</div>
				<div class="col-md-6">
					<c:if test="${not empty labTest.labReferenceNumber}">
						<form:input class="form-control" path="order.encounter"
							id="encounter" hidden="true" name="encounter"></form:input>
						<form:label class="form-control" path="order.encounter"
							id="encounter" name="encounter">${labTest.order.encounter.getEncounterType().getName()}</form:label>
					</c:if>
					<c:if test="${empty labTest.labReferenceNumber}">
						<form:select class="form-control" path="order.encounter"
							id="encounter">
							<form:options />
							<c:if test="${not empty encounters}">
								<c:forEach var="encounter" items="${encounters}">
									<form:option item="${encounter}" value="${encounter}">${encounter.getEncounterType().getName()}</form:option>
								</c:forEach>
							</c:if>
						</form:select>
					</c:if>
					<span id="encounters" class="text-danger "></span>
				</div>
				<div class="col-md-3">
					<font color="#D0D0D0"><span id="encounterDate">${labTest.order.encounter.encounterDatetime}</span></font>
				</div>
			</div>
			<!-- Test Type -->
			<div class="row">
				<div class="col-md-3">
					<form:label class="control-label" path="labTestType.labTestTypeId">
						<spring:message code="general.testType" />
						<span class="text-danger required">*</span>
					</form:label>
				</div>
				<div class="col-md-6">
					<c:if test="${not empty labTest.labReferenceNumber}">
						<form:input class="form-control" path="labTestType.labTestTypeId"
							id="testType" hidden="true" name="testType"></form:input>
						<form:label class="form-control" path="labTestType.labTestTypeId"
							id="testType" name="testType">${labTest.labTestType.getName()}</form:label>
					</c:if>
					<c:if test="${empty labTest.labReferenceNumber}">
						<form:select class="form-control" path="labTestType.labTestTypeId"
							id="testType">
							<form:options />
							<c:if test="${not empty testTypes}">
								<c:forEach var="testType" items="${testTypes}">
									<form:option item="${testType.labTestTypeId}"
										value="${testType.labTestTypeId}">${testType.getName()}</form:option>
								</c:forEach>
							</c:if>
						</form:select>
						<span id="testtype" class="text-danger "> </span>
					</c:if>
				</div>
			</div>
			<!-- Lab Reference Number -->
			<div class="row">
				<div class="col-md-3">
					<form:label class="control-label" path="labReferenceNumber">
						<spring:message code="commonlabtest.order.labReferenceNo" />
						<span class="text-danger required">*</span>
					</form:label>
				</div>
				<div class="col-md-6">
					<form:input class="form-control" maxlength="100"
						path="labReferenceNumber" id="labReferenceNumber"
						name="labReferenceNumber"></form:input>
					<span id="labreferencenumber" class="text-danger "> </span>

				</div>
			</div>
			<!-- Care Setting-->
			<div class="row">
				<div class="col-md-3">
					<form:label class="control-label"
						path="order.CareSetting.careSettingId">
						<spring:message code="general.careSetting" />
					</form:label>
				</div>
				<div class="col-md-6">
					<c:if test="${not empty labTest.labReferenceNumber}">
						<form:radiobutton path="order.CareSetting.careSettingId" value="1"
							checked="checked" onclick="return false;" />OutPatient
                        <span style="margin-right: 25px"></span>
						<form:radiobutton path="order.CareSetting.careSettingId" value="2"
							onclick="return false;" />InPatient
                    </c:if>
					<c:if test="${empty labTest.labReferenceNumber}">
						<form:radiobutton path="order.CareSetting.careSettingId" value="1"
							checked="checked" />OutPatient
                        <span style="margin-right: 25px"></span>
						<form:radiobutton path="order.CareSetting.careSettingId" value="2" />InPatient
                    </c:if>
				</div>
			</div>
			<!-- Instruction -->
			<div class="row">
				<div class="col-md-3">
					<form:label class="control-label" path="labInstructions">
						<spring:message code="general.instructions" />
					</form:label>
				</div>
				<div class="col-md-6">
					<form:textarea class="form-control" maxlength="512"
						path="labInstructions" type="text" id="labInstructions"></form:textarea>
				</div>
			</div>
			<!-- Save -->
			<div class="row">
				<div class="col-md-2">
					<input type="submit" value="Save Test Order"></input>
				</div>
				<div class="col-md-2">
					<input type="button"
						onclick="location.href = '${pageContext.request.contextPath}/patientDashboard.form?patientId=${patientId}';"
						value="Cancel"></input>
				</div>
			</div>
		</form:form>

	</fieldset>
	<br>
	<openmrs:hasPrivilege privilege="Delete CommonLabTest Orders">
		<c:if test="${not empty testOrder.labReferenceNumber}">
			<fieldset class="scheduler-border">
				<legend class="scheduler-border">
					<spring:message code="commonlabtest.order.void" />
				</legend>
				<form method="post"
					action="${pageContext.request.contextPath}/module/commonlabtest/voidlabtestorder.form"
					onsubmit="return voidValidate()">
					<!-- UUID -->
					<div class="row">
						<div class="col-md-2">
							<input value="${testOrder.uuid}" hidden="true" id="uuid"
								name="uuid"></input> <label class="control-label"
								path="voidReason"> <spring:message code="general.reason" /><span
								class=" text-danger required">*</span></label>
						</div>
						<div class="col-md-6">
							<input class="form-control" value="" id="voidReason"
								name="voidReason"> <span id="voidreason"
								class="text-danger "> </span>

						</div>
					</div>
					<!-- Retire -->
					<div class="row">
						<div class="col-md-2">
							<input type="submit" id="submitbtn" value="Void Test Order"></input>
						</div>

					</div>
				</form>
			</fieldset>
		</c:if>
	</openmrs:hasPrivilege>

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

<script
	src="${pageContext.request.contextPath}/moduleResources/commonlabtest/js/waitMe.min.js"></script>


<script type="text/javascript">

var localSource;
var testTypeArray;

$(document).ready(function () {

    $('#conceptId').val(document.getElementById("testType").value);
    localSource = new Array();
    <c:if test="${not empty encounters}">
        <c:forEach var="encounter" items="${encounters}" varStatus="status">
              localSource.push({date: "${encounter.encounterDatetime}",id:"${encounter.encounterId}"});
            </c:forEach>
        </c:if>
        //fill the Test type ;
        testTypeArray = new Array();
    <c:if test="${not empty testTypes}">
        <c:forEach var="testType" items="${testTypes}" varStatus="status">
               testTypeArray.push({shortName: "${testType.shortName}",id:"${testType.labTestTypeId}"});
            </c:forEach>
        </c:if>

        $('#testType').change(function () {
            let testTypeId = document.getElementById("testType").value;
            $('#conceptId').val(testTypeId);
            setTestTypeVal(testTypeId);
        });

    $("#encounter").on("change", function () {
        console.log("Local source : " + localSource);
        var id = $("#encounter").find(":selected").val();
        var encounter = localSource.find(o => o.id == id);
        console.log(encounter.date);
        document.getElementById('encounterDate').innerHTML = formatDate(encounter.date);
    });

    //set lab Reference value 
    let labTestTypeId = document.getElementById("testType").value;
    setTestTypeVal(labTestTypeId);
});

function setTestTypeVal(id) {
    document.getElementById('labReferenceNumber').value = (new Date).toISOString().replace(/z|t/gi, ' ').trim();
}

function validate() {
    var referenceNumber = document.getElementById('labReferenceNumber').value;
    var testType = document.getElementById('testType').value;
    var encounter = document.getElementById('encounter').value
    var reText = new RegExp("^[A-Za-z0-9_(). %:#\\-\\/]*$");
    var regErrorMesssage = "Text contains Invalid characters.LabReference name only accepts alphanumeric characters with _-/:# special characters";
    var alphabetsCharacter = "Numeric values and special characters are not allowed";
    var isValidate = true;
    var errorMessage = 'This field can not be empty';

    if (referenceNumber === "") {
        document.getElementById("labreferencenumber").style.display = 'block';
        document.getElementById('labreferencenumber').innerHTML = errorMessage;
        isValidate = false;
    }
    else if (isBlank(referenceNumber)) {
        document.getElementById("labreferencenumber").style.display = 'block';
        document.getElementById('labreferencenumber').innerHTML = errorMessage;
        isValidate = false;
    }
    else if (!reText.test(referenceNumber)) {
        document.getElementById("labreferencenumber").style.display = 'block';
        document.getElementById('labreferencenumber').innerHTML = regErrorMesssage;
        isValidate = false;
    }
    else {
        document.getElementById("labreferencenumber").style.display = 'none';
    }

    if (encounter == "") {
        document.getElementById("encounters").style.display = 'block';
        document.getElementById('encounters').innerHTML = errorMessage;
        isValidate = false;
    } else {
        document.getElementById("encounters").style.display = 'none';
    }

    if (testType == "") {
        document.getElementById("testtype").style.display = 'block';
        document.getElementById('testtype').innerHTML = errorMessage;
        isValidate = false;
    }
    return isValidate;
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

function voidValidate() {

    var retireReason = document.getElementById('voidReason').value;
    var isValidate = true;
    if (retireReason == "") {
        document.getElementById('voidreason').innerHTML = " Void reason cannot be empty";
        isValidate = false;
    }
    else if (!isNaN(retireReason)) {
        document.getElementById('voidreason').innerHTML = alphabetsCharacter;
        isValidate = false;
    }
    return isValidate;
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}

//Refresh context if required
jQuery(function () {
    var patientId = ${ patientId };
    var OrderId = document.getElementById('orderId').value;

    if (performance.navigation.type == 1) {
        if (OrderId == null || OrderId == "") {
            window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestOrder.form?patientId=" + patientId;
        }
        else {
            window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestOrder.form?patientId=" + patientId + "&testOrderId=" + OrderId;
        }
    }

    jQuery("body").keydown(function (e) {
        if (e.which == 116) {
            if (OrderId == null || OrderId == "") {
                window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestOrder.form?patientId=" + patientId;
            }
            else {
                window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestOrder.form?patientId=" + patientId + "&testOrderId=" + OrderId;
            }
        }
    });
});	 
	
</script>

