<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="builds">
    <h2>Builds</h2>

	<spring:url value="/builds/new" var="buildNewUrl">
    </spring:url>
    <a style="background-color: #000000 ; padding: 10px; border-radius:20px; float:right;" href="${fn:escapeXml(buildNewUrl)}">Add New Item</a><br><br>
    <br>
    <table id="buildTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Title</th>
            <th style="width: 400px;">Description</th>
            <th style="width: 150px">Champion</th>
            <th style="width: 120px">Rune Page</th>
            <th style="width: 300px">Items</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${builds}" var="build">
            <tr>
                <td>
                   <c:out value="${build.title}"/>
                </td>
                <td>
                    <c:out value="${build.description}"/>
                </td>
                <td>
                    <c:out value="${build.champion.name}"/>
                </td>
                <td>
                    <c:out value="${build.runePage.name}"/>
                </td>
                <td>
                  <c:forEach items="${build.items}" var="item">
                  	<c:out value="${item.title}"/>
                  </c:forEach>
                </td> 
               <td>
               	<spring:url value="/builds/{buildId}/remove" var="buildRemoveUrl">
                        <spring:param name="buildId" value="${build.id}"/>
                    </spring:url>
                <spring:url value="/builds/{buildId}/newChangeRequest" var="buildRequestUrl">
                        <spring:param name="buildId" value="${build.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(buildRemoveUrl)}">Remove build</a>
                </td>
    
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>