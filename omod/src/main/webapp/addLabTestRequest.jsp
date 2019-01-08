<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:portlet url="patientHeader" id="patientDashboardHeader"
	patientId="${patientId}" />
<openmrs:require privilege="Add CommonLabTest Orders"
	otherwise="/login.htm"
	redirect="/module/commonlabtest/addLabTestRequest.form" />

<html>
<head>
<link type="text/css" rel="stylesheet"
	href="/openmrs/moduleResources/commonlabtest/css/commonlabtest.css" />
<link
	href="/openmrs/moduleResources/commonlabtest/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" />
<link
	href="/openmrs/moduleResources/commonlabtest/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="/openmrs/moduleResources/commonlabtest/css/style.css"
	rel="stylesheet" />
<link
	href="/openmrs/moduleResources/commonlabtest/css/dataTables.bootstrap4.min.css"
	rel="stylesheet" />

</head>
<style>
body {
	font-size: 12px;
	font-family: Verdana;
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
/* #testRequestTable {
table-layout: fixed;
}
#testRequestTable td { word-wrap: break-word; } */

/*Collapse  */
/* FANCY COLLAPSE PANEL STYLES */
.fancy-collapse-panel .panel-default>.panel-heading {
	padding: 0;
	size: 12px;
}

.fancy-collapse-panel .panel-heading a {
	padding: 10px 35px 10px 15px;
	display: inline-block;
	width: 100%;
	background-color: #1aac9b;
	color: white !important;
	position: relative;
	text-decoration: none;
	font-size: 16px;
}

.fancy-collapse-panel .panel-heading a:hover {
	color: white !important;
}

.fancy-collapse-panel .panel-heading a:after {
	font-family: "FontAwesome";
	content: "\f147";
	position: absolute;
	right: 20px;
	font-size: 20px;
	font-weight: 400;
	top: 50%;
	line-height: 1;
	color: white;
	margin-top: -10px;
}
</style>
<body>
	<br>
	<!-- Error message -->
	<c:if test="${not empty error}">
		<div class="alert alert-danger">
			<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>Error!</strong>
			<c:out value="${error}" />
		</div>
	</c:if>
	<div id="alert_placeholder"></div>
	<br>
	<fieldset class="scheduler-border" style="margin-top: 320px;">
		<legend class="scheduler-border">
			<spring:message code="commonlabtest.request.add" />
		</legend>
		<br>
		<!--     <form id="labTestForm" method="post" onsubmit="return submitAndValidate()"> -->
		<div class="row">
			<div class="col-md-2">
				<label class="control-label" path="encounter"> <spring:message
						code="general.encounter" /><span class=" text-danger required">*</span></label>
			</div>
			<div class="col-md-4">
				<select class="form-control" id="encounter_id">
					<c:if test="${not empty encounters}">
						<c:forEach var="encounter" items="${encounters}">
							<option value="${encounter.encounterId}">${encounter.getEncounterType().getName()}</option>
						</c:forEach>
					</c:if>
				</select> <span id="encounters" class="text-danger "></span>
			</div>
		</div>
		<br>
		<div>
			<div class="row">
				<div class="col-md-12 col-md-offset-12 col-sm-12col-sm-offset-12">
					<div class="fancy-collapse-panel">
						<div class="panel-group" id="accordion" role="tablist"
							aria-multiselectable="true">

							<div id="panel-container"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Submit ,Edit CommonLabTest Orders -->
		<div class="row">
			<div class="col-md-2">
				<openmrs:hasPrivilege privilege="Add CommonLabTest Orders">
					<input type="submit" onclick="return submitAndValidate()"
						value="Save Test Request"></input>
				</openmrs:hasPrivilege>
			</div>
			<div class="col-md-2">
				<input type="button"
					onclick="location.href = '${pageContext.request.contextPath}/patientDashboard.form?patientId=${patientId}';"
					value="Cancel"></input>
			</div>
		</div>
		<!-- </form> -->
	</fieldset>
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
var localTestRequest;
var patiendId;
$(document).ready(function () {


    $(function () {
        $("#accordion").accordion({
            header: "h3",
            heightStyle: "fill"
        });
    });

    patiendId = '${patientId}';
    localTestRequest = getTestRequestList();
    generateTestCategory();

    $("input[type='checkbox']").change(function () {
        var testTypeId = $(this).closest("tr")
            .find(".testTypeId")
            .text();
        if (this.checked) {
            console.log("Test Type Id :" + testTypeId);
            document.getElementById('labRef.' + testTypeId).value = (new Date).toISOString().replace(/z|t/gi, ' ').trim();
            document.getElementById("labRef." + testTypeId).disabled = false;
            document.getElementById("ins." + testTypeId).disabled = false;
        } else {
            console.log("else Test Type Id :" + testTypeId);
            document.getElementById('labRef.' + testTypeId).value = '';
            document.getElementById("labRef." + testTypeId + "").disabled = true;
            document.getElementById('ins.' + testTypeId).value = '';
            document.getElementById("ins." + testTypeId).disabled = true;

        }
    });
});

function getTestRequestList() {
    return JSON.parse(JSON.stringify(${ labTestTypes }));
}


function showalert(message, alerttype) {
    //alertType : .alert-success, .alert-info, .alert-warning & .alert-danger
    $('#alert_placeholder').append('<div id="alertdiv" class="alert ' + alerttype + '"><a class="close" data-dismiss="alert">×</a><span>' + message + '</span></div>')
    autoHide();
}
function autoHide() {
    $("#alertdiv").fadeTo(5000, 500).slideUp(500, function () {
        $("#alertdiv").slideUp(500);
        $("#alertdiv").remove();
    });
}

function submitAndValidate() {
    let isChecked = true;
    var check = $('input[type=checkbox]:checked').length;
    if (check > 0) {
        if (Validation()) {
            save(getTestTypes());
        } else {
            isChecked = false;
        }
    } else {
        showalert("Kindly select atleast One test type ", "alert-info");
        isChecked = false;
    }
    return isChecked;
}
function getTestTypes() {
    var TableData = new Array();
    let encounter = document.getElementById('encounter_id').value;
    $('#testRequestTable tr').each(function (row, tr) {
        console.log("Check : " + $(tr).find('td:eq(0) input').is(':checked'));
        var isTestTypeChecket = $(tr).find('td:eq(0) input').is(':checked');
        if (isTestTypeChecket) {
            TableData[row] = {
                "testTypeId": $(tr).find('td:eq(1)').text()
                , "testTypename": $(tr).find('td:eq(2)').text()
                , "labReferenceNumber": $(tr).find('td:eq(3) input').val()
                , "labInstructions": $(tr).find('td:eq(4) input').val()
                , "encounterId": encounter
            }
        }
    });

    TableData.shift();
    console.log("Table datae : " + JSON.stringify(TableData.filter(Boolean)));
    return TableData.filter(Boolean);
}
function Validation() {
    var encounter = document.getElementById('encounter_id');
    //let e =   encounter.options[encounter.selectedIndex].text; 
    var errorMessage = 'This field can not be empty';
    var isValidate = true;
    if (encounter.childElementCount == 0) {
        document.getElementById("encounters").style.display = 'block';
        document.getElementById('encounters').innerHTML = errorMessage;
        isValidate = false;
    } else {
        document.getElementById("encounters").style.display = 'none';
    }
    $('#testRequestTable tr').each(function (row, tr) {
        var isTestTypeChecket = $(tr).find('td:eq(0) input').is(':checked');
        if (isTestTypeChecket) {
            let valueReference = $(tr).find('td:eq(3) input').val();
            let testTypeId = $(tr).find('td:eq(1)').text();
            if (valueReference == "" || valueReference == null) {
                document.getElementById('labReference.' + testTypeId + '').style.display = 'block';
                document.getElementById('labReference.' + testTypeId + '').innerHTML = errorMessage;
                isValidate = false;
            } else {
                document.getElementById('labReference.' + testTypeId + '').style.display = 'none';
            }
        }
    });

    return isValidate;
}
function save(data) {
    var isTure = true;
    $.ajax({
        type: "POST",
        url: "${pageContext.request.contextPath}/module/commonlabtest/addLabTestRequest.form?patientId=" + patiendId,
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(data),//used without stringify();
        success: function (data) {
            console.log("success  : " + data);
            if (data == true) {
                window.location = "${pageContext.request.contextPath}/patientDashboard.form?patientId=${patientId}";
            } else {
                showalert("Error ! could not save Lab Test Request", "alert-warning");
            }
        },
        error: function (data) {
            isTure = true;
        },
        done: function (e) {
            console.log("DONE");
        }
    });

    return isTure;
}

/*Generate panel  */
function generateTestCategory() {
    var requestItems = "";
    jQuery(this.localTestRequest).each(function () {
        requestItems = requestItems.concat('<div class="panel panel-default">');
        requestItems = requestItems.concat('<div class="panel-heading" role="tab" id="heading' + this.testGroup + '">');
        requestItems = requestItems.concat('<h4 class="panel-title">');
        requestItems = requestItems.concat('<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse' + this.testGroup + '" aria-expanded="false" aria-controls="collapse' + this.testGroup + '">' + this.testGroup + '</a>');
        requestItems = requestItems.concat('</h4></div>');
        requestItems = requestItems.concat('<div id="collapse' + this.testGroup + '" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading' + this.testGroup + '">');
        requestItems = requestItems.concat('<div class="panel-body">');
        requestItems = requestItems.concat('<table id="testRequestTable" class="table table-striped table-bordered" style="width:100%"> ');
        requestItems = requestItems.concat('<thead><tr>');
        requestItems = requestItems.concat('<th></th>');
        requestItems = requestItems.concat('<th hidden="true"></th>');
        requestItems = requestItems.concat('<th>Test Type</th>');
        requestItems = requestItems.concat('<th>Lab Reference</th>');
        requestItems = requestItems.concat('<th>Instructions</th>');
        requestItems = requestItems.concat('</tr></thead>');
        requestItems = requestItems.concat('<tbody>');
        jQuery(this.testType).each(function () {
            requestItems = requestItems.concat('<tr>');
            requestItems = requestItems.concat('<td style="text-align:center;"><input class="form-check-input " type="checkbox" /></td>');
            requestItems = requestItems.concat('<td hidden ="true" class ="testTypeId">' + this.testTypeId + '</td>');
            requestItems = requestItems.concat('<td>' + this.testTypeName + '</td>');
            requestItems = requestItems.concat('<td><input class="form-control" disabled type="text" id="labRef.' + this.testTypeId + '"/><span id="labReference.' + this.testTypeId + '" class="text-danger "></span></td>');
            requestItems = requestItems.concat('<td><input class="form-control" disabled type="text" id="ins.' + this.testTypeId + '"/></td>');
            requestItems = requestItems.concat('</tr>');
        });
        requestItems = requestItems.concat('</tbody>');
        requestItems = requestItems.concat('</table>');
        requestItems = requestItems.concat('</div></div>');
        requestItems = requestItems.concat('</div>');
    });

    $("#panel-container").append(requestItems);
    // document.getElementById("sortweightList").innerHTML = resultsItems;
}	
	 

</script>
</html>

