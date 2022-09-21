<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form"%>

<%@ include
	file="/WEB-INF/view/module/commonlabtest/include/localHeader.jsp"%>
<!-- <openmrs:require anyPrivilege="Add CommonLabTest Metadata, Edit CommonLabTest Metadata" otherwise="/login.htm" redirect="/module/commonlabtest/addLabTestType.form" />
 -->
<link type="text/css" rel="stylesheet"
	href="/openmrs/moduleResources/commonlabtest/css/commonlabtest.css" />
<link
	href="/openmrs/moduleResources/commonlabtest/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" />
<link
	href="/openmrs/moduleResources/commonlabtest/css/bootstrap.min.css"
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
</style>
<body>
	<div class="container">
		<c:if test="${not empty error}">
			<div class="alert alert-danger">
				<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>Error!</strong>
				<c:out value="${error}" />
			</div>
		</c:if>
		<c:set var="testType" scope="session" value="${labTestType}" />

		<fieldset class="scheduler-border">
			<c:if test="${empty testType.referenceConcept.conceptId}">
				<openmrs:require privilege="Add CommonLabTest Metadata"
					otherwise="/login.htm"
					redirect="/module/commonlabtest/addLabTestType.form" />
				<legend class="scheduler-border">
					<spring:message code="commonlabtest.labtesttype.add" />
				</legend>
			</c:if>
			<c:if test="${not empty testType.referenceConcept.conceptId}">
				<openmrs:require privilege="Edit CommonLabTest Metadata"
					otherwise="/login.htm"
					redirect="/module/commonlabtest/addLabTestType.form" />
				<legend class="scheduler-border">
					<spring:message code="commonlabtest.labtesttype.edit" />
				</legend>
			</c:if>
			<springform:form commandName="labTestType" id="testTypeForm"
				onsubmit='return validate(this);'>
				<!-- Concept Reference -->
				<div class="row">
					<div class="col-md-3">
						<springform:label path="labTestTypeId">
							<spring:message code="general.id" />
							<span class="text-danger font-weight-bold">*</span>
						</springform:label>
					</div>
					<div class="col-md-5">
						<springform:label path="labTestTypeId" id="labTestTypeId"></springform:label>
					</div>
				</div>
				<div class="row">
					<%-- <div class="col-md-6">
						<springform:input id="conceptSuggestBox" path="referenceConcept"></springform:input>
						<datalist class="lowercase" id="conceptOptions"></datalist>
						<span id="referenceconcept" class="text-danger "> </span>
					</div> --%>
					<div class="col-md-3">
						<springform:label path="referenceConcept">
							<spring:message code="general.referenceConcept" />
							<span class="text-danger font-weight-bold">*</span>
						</springform:label>
					</div>
					<div class="col-md-5">
						<springform:select path="referenceConcept" id="conceptBox">
							<c:forEach var="concept" items="${labTestConcepts}">
								<option value="${concept['key']}">${concept['value']}</option>
							</c:forEach>
						</springform:select>
					</div>
				</div>
				<!-- Test Name -->
				<div class="row">
					<div class="col-md-3">
						<springform:label path="name">
							<spring:message code="commonlabtest.labtest.testName" />
							<span class="text-danger font-weight-bold">*</span>
						</springform:label>
					</div>
					<div class="col-md-5">
						<springform:input maxlength="50" path="name"
							id="name"></springform:input>
						<span id="testname" class="text-danger"> </span>
					</div>
				</div>
				<!-- Short Name -->
				<div class="row">
					<div class="col-md-3">
						<springform:label path="shortName">
							<spring:message code="general.shortName" />
						</springform:label>
					</div>
					<div class="col-md-5">
						<springform:input maxlength="20" path="shortName"
							id="short_name"></springform:input>
						<span id="shortname" class="text-danger"> </span>

					</div>
				</div>
				<!-- Description -->
				<div class="row">
					<div class="col-md-3">
						<springform:label path="description">
							<spring:message code="general.description" />
						</springform:label>
					</div>
					<div class="col-md-5">
						<springform:textarea path="description" id="description" rows="5"></springform:textarea>
					</div>
				</div>
				<!-- Test Group -->
				<div class="row">
					<div class="col-md-3">
						<springform:label path="testGroup">
							<spring:message code="commonlabtest.labtest.testGroup" />
						</springform:label>
					</div>
					<div class="col-md-5">
						<springform:select path="testGroup" id="testGroup">
							<springform:options items="${LabTestGroup}" />
							<c:forEach items="${LabTestGroup}">
								<option value="${LabTestGroup}">${LabTestGroup}</option>
							</c:forEach>
						</springform:select>
					</div>
				</div>
				<!-- Specimen -->
				<div class="row">
					<div class="col-md-3">
						<springform:label path="requiresSpecimen">
							<spring:message code="commonlabtest.labtest.requiresSpecimen" />
						</springform:label>
					</div>
					<div class="col-md-5">
						<span style="margin-right: 25px"></span>
						<springform:radiobutton path="requiresSpecimen"
							value="true" />
						Yes <span style="margin-right: 25px"></span>
						<springform:radiobutton path="requiresSpecimen"
							value="false" />
						No
					</div>
				</div>
				<c:if test="${not empty testType.referenceConcept.conceptId}">
					<!-- Date Create -->
					<div class="row">
						<div class="col-md-3">
							<springform:label path="creator">
								<spring:message code="general.createdBy" />
							</springform:label>
						</div>
						<div class="col-md-5">
							<c:out value="${testType.creator.personName}" />
							-
							<c:out value="${testType.dateCreated}" />
						</div>
					</div>
					<!-- UUID -->
					<div class="row">
						<div class="col-md-3">
							<font color="#D0D0D0"><sub> <spring:message
										code="general.uuid" /></sub></font>
						</div>
						<div class="col-md-5">
							<font color="#D0D0D0"><sub> <c:out
										value="${testType.uuid}" /></sub></font>
						</div>
					</div>
				</c:if>
				<!-- Save -->
				<div class="row">
					<div class="col-md-2">
						<input type="submit" value="Save Test Type"></input>
					</div>
					<div class="col-md-2">
						<input type="button"
							onclick="location.href = '${pageContext.request.contextPath}/module/commonlabtest/manageLabTestTypes.form';"
							value="Cancel"></input>
					</div>
				</div>
			</springform:form>

		</fieldset>
		<br>
		<openmrs:hasPrivilege privilege="Delete CommonLabTest Metadata">
			<c:if test="${not empty testType.referenceConcept.conceptId}">

				<fieldset class="scheduler-border">
					<legend class="scheduler-border">
						<spring:message code="commonlabtest.labtesttype.void" />
					</legend>
					<form method="post"
						action="${pageContext.request.contextPath}/module/commonlabtest/retirelabtesttype.form"
						onsubmit="return retireValidate()">
						<!-- UUID -->
						<div class="row">
							<div class="col-md-2">
								<input value="${labTestType.uuid}" hidden="true" id="uuid"
									name="uuid"></input> <label class="control-label"
									path="retireReason"> <spring:message
										code="general.retireReason" /><span class="required">*</span></label>
							</div>
							<div class="col-md-6">
								<input class="form-control" value="${labTestType.retireReason}"
									id="retireReason" name="retireReason"> <span
									id="retirereason" class="text-danger "> </span>

							</div>
						</div>
						<!-- Retire -->
						<div class="row">
							<div class="col-md-2">
								<input type="submit" value="Retire Test Type"></input>
							</div>
						</div>
					</form>
				</fieldset>
			</c:if>
		</openmrs:hasPrivilege>
	</div>

</body>

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
jQuery(document).ready(function () {

    $('#name').on('input', function () {
        var input = $(this);
        var is_name = input.val();
        if (is_name) { input.removeClass("invalid").addClass("valid"); }
        else { input.removeClass("valid").addClass("invalid"); }
    });

    localSource = getConcepts();
    var datalist = document.getElementById("conceptOptions");
    var dataListLength = datalist.options.length;
    if (dataListLength > 0) {
        jQuery("#conceptOptions option").remove();
    }

    if (localSource.length > 0) {
        conceptObject = {};

        jQuery(localSource).each(function () {
            var conceptName = toTitleCase(this.name.toLowerCase());
            conceptOption = "<option value=\"" + this.id + "\">" + conceptName + "</option>";
            jQuery('#conceptOptions').append(conceptOption);
            conceptId = this.id;
            conceptObject[conceptId] = conceptName;
        });
    }

    jQuery('#conceptSuggestBox').on('input', function () {
        //refresh();
        var val = this.value;
        if (jQuery('#conceptOptions option').filter(function () {
            return this.value === val;
        }).length > 2) {
            var datalist = document.getElementById("conceptOptions");
            var options = datalist.options;
            var conceptId = jQuery(this).val();
            var concepts = localSource.find(o => o.id == conceptId);
            jQuery("#name").val(concepts.name.toLowerCase());
            jQuery("#short_name").val(concepts.shortName.toLowerCase());
            jQuery("#description").val(concepts.description.toLowerCase());
        }
    });
});

function toTitleCase(str) {
    return str.replace(/(?:^|\s)\w/g, function (match) {
        return match.toUpperCase();
    });
}

//get all concepts
function getConcepts() {
    return JSON.parse(JSON.stringify(${ conceptsJson }));
}

/*  */
function confirmDelete() {
    //onsubmit="return confirmDelete()"
    if (confirm("Are you sure you want to Delete this Test Type? It will be permanently removed from the system.")) {
        return true;
    } else {
        return false;
    }
}

function validate(form) {

    var testName = document.getElementById('name').value;
    var testShortName = document.getElementById('short_name').value;
    var referenceConcept = document.getElementById('conceptSuggestBox').value;
    var reText = new RegExp("^[A-Za-z][ A-Za-z0-9_() .%\\-]*$");
    var isValidate = true;
    var regErrorMesssage = "Text contains Invalid characters.Test name only accepts alphabets with _().% - special charaters";
    var regShortNameErrorMesssage = "Text contains Invalid characters.Short name only accepts alphabets with _().% - special characters";
    var numericErrorMessage = "Only interger values are allowed";
    var emptyErorMessage = 'This field cannot be empty';

    if (referenceConcept == "") {
        document.getElementById("referenceconcept").style.display = 'block';
        document.getElementById('referenceconcept').innerHTML = emptyErorMessage;
        isValidate = false;
    }
    else if (isNaN(referenceConcept)) {
        document.getElementById("referenceconcept").style.display = 'block';
        document.getElementById('referenceconcept').innerHTML = "Only the autosearch reference concept Id is accepted";
        isValidate = false;
    }
    else {
        document.getElementById("referenceconcept").style.display = 'none';
    }


    if (testName == "") {
        document.getElementById("testname").style.display = 'block';
        document.getElementById('testname').innerHTML = emptyErorMessage;
        isValidate = false;
    }
    else if (!isNaN(testName)) {
        document.getElementById("testname").style.display = 'block';
        document.getElementById('testname').innerHTML = numericErrorMessage;
        isValidate = false;
    }
    else if (!reText.test(testName)) {
        document.getElementById("testname").style.display = 'block';
        document.getElementById('testname').innerHTML = regErrorMesssage;
        isValidate = false;
    }
    else {
        document.getElementById("testname").style.display = 'none';
    }

    if (!reText.test(testShortName) && testShortName != "") {
        document.getElementById("shortname").style.display = 'block';
        document.getElementById('shortname').innerHTML = regShortNameErrorMesssage;
        isValidate = false;
    }
    else {
        document.getElementById("shortname").style.display = 'none';
    }

    return isValidate;
}

function retireValidate() {
    var retireReason = document.getElementById('retireReason').value;
    var isValidate = true;
    var emptyErorMessage = 'This field cannot be empty';
    if (retireReason == "") {
        document.getElementById('retirereason').innerHTML = emptyErorMessage;
        isValidate = false;
    }
    else if (!isNaN(retireReason)) {
        document.getElementById('retirereason').innerHTML = "Only characters are allowed";
        isValidate = false;
    }

    return isValidate;
}


jQuery(function () {
    var uuid = '${testType.uuid}';
    var labReferenceId = '${testType.referenceConcept.conceptId}'
    if (performance.navigation.type == 1) {
        if (labReferenceId == null || labReferenceId == "") {
            window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestType.form";
        } else {
            window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestType.form?uuid=" + uuid;
        }
    }

    jQuery("body").keydown(function (e) {

        if (e.which == 116) {
            if (labReferenceId == null || labReferenceId == "") {
                window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestType.form";
            } else {
                window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestType.form?uuid=" + uuid;
            }
        }

    });
});	 

</script>

<%@ include file="/WEB-INF/template/footer.jsp"%>
