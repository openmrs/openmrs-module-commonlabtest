<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<!-- <openmrs:require anyPrivilege="Add CommonLabTest Samples , Edit CommonLabTest Samples" otherwise="/login.htm" redirect="/module/commonlabtest/addLabTestSample.form" />
 -->
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
input[type=button] {
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

    <div class="container">

        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>Error!</strong>
                <c:out value="${error}" />
            </div>
        </c:if>
        <c:set var="testSample" scope="session" value="${testSample}" />
        <fieldset class="scheduler-border">
            <c:if test="${empty testSample.specimenType}">
             <openmrs:require privilege="Add CommonLabTest Samples" otherwise="/login.htm" redirect="/module/commonlabtest/addLabTestSample.form" />
                <legend class="scheduler-border">
                    <spring:message code="commonlabtest.labtestsample.add" />
                </legend>
            </c:if>
            <c:if test="${not empty testSample.specimenType}">
              <openmrs:require privilege="Edit CommonLabTest Samples" otherwise="/login.htm" redirect="/module/commonlabtest/addLabTestSample.form" />
                <legend class="scheduler-border">
                    <spring:message code="commonlabtest.labtestsample.edit" />
                </legend>
            </c:if>
            <form:form modelAttribute="testSample" id="testSampleform" onsubmit='return validate(this);'>

                <form:input path="labTest.order.patient" hidden="true" value="${patientId}"></form:input>
                <form:input path="collector.providerId" hidden="true" value="${provider.providerId}"></form:input>
                <form:input path="labTest.testOrderId" hidden="true" value="${orderId}"></form:input>
                <form:input path="labTestSampleId" hidden="true" value=""></form:input>
                <form:input path="status" hidden="true" value=""></form:input>
                <form:input path="comments" hidden="true" value=""></form:input>
                <div class="row">
                    <div class="col-md-2">
                        <form:label class="control-label" path="specimenType">
                            <spring:message code="general.specimenType" /><span class="text-danger required">*</span></form:label>
                    </div>
                    <div class="col-md-6">
                        <form:select class="form-control" path="specimenType" id="specimen_type">
                            <form:options />
                            <c:if test="${not empty specimenType}">
                                 <option></option>
                                <c:forEach var="spType" items="${specimenType}">
                                    <form:option item="${spType}" value="${spType}">${spType.name}</form:option>
                                </c:forEach>
                            </c:if>
                        </form:select>
                        <span id="specimentype" class="text-danger "> </span>
                        <form:errors path="specimenType" cssClass="error" />
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2">
                        <form:label class="control-label" path="specimenSite">
                            <spring:message code="general.specimenSite" /><span class="text-danger required">*</span></form:label>
                    </div>
                    <div class="col-md-6">
                        <form:select class="form-control" path="specimenSite" id="specimen_site">
                            <c:if test="${not empty specimenSite}">
                                <option></option>
                                <c:forEach var="spSite" items="${specimenSite}">
                                    <form:option item="${spSite.getAnswerConcept()}" value="${spSite.getAnswerConcept()}">${spSite.getAnswerConcept().getName()}</form:option>
                                </c:forEach>
                            </c:if>
                        </form:select>
                        <span id="specimensite" class="text-danger "> </span>
                        <form:errors path="specimenSite" cssClass="error" />
                    </div>
                </div>
                <!-- Quantity -->
                <div class="row">
                    <div class="col-md-2">
                        <form:label class="control-label" path="quantity">
                            <spring:message code="commonlabtest.labtestsample.quantity" />
                        </form:label>
                    </div>
                    <div class="col-md-6">
                        <form:input path="quantity" maxlength="4" class="form-control" id="quantity"></form:input>
                        <span id="quantityError" class="text-danger "> </span>
                        <form:errors path="quantity" cssClass="error" />
                    </div>
                </div>
                <!--Unit  -->
                <div class="row">
                    <div class="col-md-2">
                        <form:label class="control-label" path="units">
                            <spring:message code="commonlabtest.labtestsample.unit" />
                        </form:label>
                    </div>
                    <div class="col-md-6">
                        <form:select class="form-control" path="units" id="units">
                            <c:if test="${not empty testUnits}">
                                <c:forEach var="unit" items="${testUnits}">
                                    <form:option item="${unit.getAnswerConcept()}" value="${unit.getAnswerConcept()}">${unit.getAnswerConcept().getName()}</form:option>
                                </c:forEach>
                            </c:if>
                        </form:select>
                        <span id="unitsError" class="text-danger "> </span>
                        <form:errors path="units" cssClass="error" />
                    </div>
                </div>
                <!--Sample Identifier  -->
                <div class="row">
                    <div class="col-md-2">
                        <form:label class="control-label" path="sampleIdentifier">
                            <spring:message code="commonlabtest.labtestsample.sampleIdentifier" /><span class="text-danger required">*</span></form:label>
                    </div>
                    <div class="controls col-md-6">
                        <form:input class="form-control" path="sampleIdentifier" maxlength="50" id="sample_identifier"></form:input>
                        <span id="sampleidentifier" class="text-danger "> </span>
                        <form:errors path="sampleIdentifier" cssClass="error" />
                    </div>
                </div>
                <!--collectionDate  -->
                <div class="row">
                    <div class="col-md-2">
                        <form:label class="control-label" path="collectionDate">
                            <spring:message code="commonlabtest.labtestsample.collectionDate" /><span class="text-danger required">*</span></form:label>
                    </div>
                    <div class="controls col-md-6">
                        <form:input class="form-control" path="collectionDate" id="collectionDatePciker" autocomplete="off"
                            readonly="true"></form:input>
                        <span id="collectiondate" class="text-danger "> </span>
                        <form:errors path="collectionDate" cssClass="error" />
                    </div>
                </div>
                <!-- Save -->
                <div class="row" style="margin-top:30px;">
                    <div class="col-md-2"></div>
                    <div class="col-md-2">
                        <input type="submit" value="Save Test Sample"></input>
                    </div>
                    <div class="col-md-2">
                        <input type="button" onclick="goTo()" value="Cancel"></input>
                    </div>
                </div>
            </form:form>
        </fieldset>
        <br>
       <openmrs:hasPrivilege privilege="Delete CommonLabTest Samples">  
        <c:if test="${not empty testSample.specimenType}">

            <fieldset class="scheduler-border">
                <legend class="scheduler-border">
                    <spring:message code="commonlabtest.labtestsample.void" />
                </legend>
                <form method="post" action="${pageContext.request.contextPath}/module/commonlabtest/voidlabtestsample.form">
                    <!-- UUID -->
                    <div class="row">
                        <div class="col-md-2">
                            <input value="${testSample.uuid}" hidden="true" id="uuid" name="uuid"></input>
                            <label class="control-label" path="voidReason">
                                <spring:message code="general.reason" /><span class="required">*</span></label>
                        </div>
                        <div class="col-md-6">
                            <input class="form-control" value="" id="voidReason" name="voidReason" required="required">
                        </div>
                    </div>
                    <!-- Retire -->
                    <div class="row">
                        <div class="col-md-2">
                            <input type="submit" value="Void Test Sample"></input>
                        </div>
                    </div>
                </form>
            </fieldset>
        </c:if>
     </openmrs:hasPrivilege>   
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
var specimentTypeArray;
function isNumber(evt) {

    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function goTo() {
    window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/manageLabTestSamples.form?patientId=" + ${ patientId } +"&testOrderId=${orderId}";

}

$(document).ready(function () {
    console.log("Date : " + '${orderEncDate}');
    $("#collectionDatePciker").datepicker({
        dateFormat: 'yy-mm-dd',
        minDate: '${orderEncDate}',
        maxDate: new Date(),
        onSelect: function (datetext) {
            var d = new Date(); // for now
            var h = d.getHours();
            h = (h < 10) ? ("0" + h) : h;

            var m = d.getMinutes();
            m = (m < 10) ? ("0" + m) : m;

            var s = d.getSeconds();
            s = (s < 10) ? ("0" + s) : s;

            datetext = datetext + " " + h + ":" + m + ":" + s;
            $('#datepicker').val(datetext);
        },
    });

    //fill the array 
    specimentTypeArray = new Array();
    <c:if test="${not empty specimenType}">
        <c:forEach var="specimentype" items="${specimenType}" varStatus="status">
	         specimentTypeArray.push({name: "${specimentype.name}",id:"${specimentype.conceptId}"});
	        </c:forEach >
	    </c:if>
        //on change 
        $("#specimen_type").on("change", function () {
            let specimentTypeId = document.getElementById('specimen_type').value;
            setSpecimenTypeVal(specimentTypeId);
        });
    //initial value;
    console.log("specimen Type: " + document.getElementById('specimen_type').value);
    let specimentTypeId = document.getElementById('specimen_type').value;
    setSpecimenTypeVal(specimentTypeId);
});


function setSpecimenTypeVal(id) {
    document.getElementById('sample_identifier').value = (new Date).toISOString().replace(/z|t/gi, ' ').trim();
}


/* Validate */
function validate(form) {

    var specimenType = document.getElementById('specimen_type').value;
    var specimenSite = document.getElementById('specimen_site').value;
    var quantity = document.getElementById('quantity').value;
    var units = document.getElementById('units').value;
    var sampleIdentifier = document.getElementById('sample_identifier').value;
    var datepicker = document.getElementById('collectionDatePciker').value;

    var reText = new RegExp("^[A-Za-z][ A-Za-z0-9_().%]*$");
    var doubleErrorMessage = "The only value you can enter here is a double number and pattern should be like this (x,xx,xx.x)";
    var doubleReg = new RegExp("^\\d{0,2}(\\.\\d{0,2}){0,1}$");
    var regErrorMesssage = "Text contains Invalid characters.Units only accepts alphabets with _().% special characters";
    var emptyErorMessage = 'This field can not be empty';

    var isValidate = true;

    console.log("Double : " + doubleReg.test(quantity));


    if (specimenType == "") {
        document.getElementById("specimentype").style.display = 'block';
        document.getElementById('specimentype').innerHTML = emptyErorMessage;
        isValidate = false;
    }
    else {
        document.getElementById("specimentype").style.display = 'none';
    }
    /*Specimen Site  */
    if (specimenSite == "") {
        document.getElementById("specimensite").style.display = 'block';
        document.getElementById('specimensite').innerHTML = emptyErorMessage;
        isValidate = false;
    }
    else {
        document.getElementById("specimensite").style.display = 'none';
    }
    /* sampleIdentifier */
    if (sampleIdentifier == "") {
        document.getElementById("sampleidentifier").style.display = 'block';
        document.getElementById('sampleidentifier').innerHTML = emptyErorMessage;
        isValidate = false;
    }
    else {
        document.getElementById("sampleidentifier").style.display = 'none';
    }

    /*Quantity  */

    if (!doubleReg.test(quantity) && quantity != "") {
        document.getElementById("quantityError").style.display = 'block';
        document.getElementById('quantityError').innerHTML = doubleErrorMessage;
        isValidate = false;
    }
    else {
        document.getElementById("quantityError").style.display = 'none';
    }
   
    /* Collection Date */
    if (datepicker == "") {
        document.getElementById("collectiondate").style.display = 'block';
        document.getElementById('collectiondate').innerHTML = emptyErorMessage;
        isValidate = false;
    }
    else {
        document.getElementById("collectiondate").style.display = 'none';
    }
    return isValidate;
}
//On Refereshing the parameter value ...
jQuery(function () {

    if (performance.navigation.type == 1) {
        window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestSample.form?patientId=" + ${ patientId } +"&orderId=" + ${ orderId };
    }

    jQuery("body").keydown(function (e) {

        if (e.which == 116) {
            window.location.href = "${pageContext.request.contextPath}/module/commonlabtest/addLabTestSample.form?patientId=" + ${ patientId } +"&orderId=" + ${ orderId };
        }

    });
});	

</script>


<%@ include file="/WEB-INF/template/footer.jsp"%>