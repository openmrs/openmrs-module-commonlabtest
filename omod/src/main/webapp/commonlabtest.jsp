<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<h2><spring:message code="commonlabtest.title" /></h2>

<br/>
<table>
  <tr>
   <th>Lab Test Type:</th>
   <th>Short Name:</th>
   <th>Test Group:</th>
   <th>Description:</th>
  </tr>
  <c:forEach var="ltt" items="${labtesttypes}">
      <tr>
        <td>${ltt.name}</td>
        <td>${ltt.shortName}</td>
        <td>${ltt.testGroup}</td>
        <td>${ltt.description}</td>
      </tr>		
  </c:forEach>
</table>

<%@ include file="/WEB-INF/template/footer.jsp"%>
