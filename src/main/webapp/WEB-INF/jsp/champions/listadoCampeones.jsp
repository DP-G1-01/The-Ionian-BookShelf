<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="champions">
    <h2>Champions</h2>

	<spring:url value="/champions/new" var="championNewUrl">
    </spring:url>
    <a style="background-color: #000000 ; padding: 10px; border-radius:20px; float:right;" href="${fn:escapeXml(championNewUrl)}">Add New Champion</a><br><br>
    <br>
    <table id="championTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 400px;">Description</th>
            <th >Health</th>
            <th >Mana</th>
            <th >Energy</th>
            <th >Attack</th>
            <th >Speed</th>
            <th >Role</th>
            <th >Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${champions}" var="champion">
            <tr>
                <td>
                   <c:out value="${champion.name}"/>
                </td>
                <td>
                    <c:out value="${champion.description}"/>
                </td>
                <td>
                    <c:out value="${champion.health}"/>
                </td>
                <td>
                    <c:out value="${champion.mana}"/>
                </td>
                <td>
                    <c:out value="${champion.energy}"/>
                </td>
                <td>
                    <c:out value="${champion.attack}"/>
                </td>
                <td>
                    <c:out value="${champion.speed}"/>
                </td>
                <td>
                 	<c:out value="${champion.role.name}"/>
                </td>
                
                
               <td>
               	<spring:url value="/champions/{championId}/remove"
							var="championRemoveUrl">
							<spring:param name="championId" value="${champion.id}" />
						</spring:url>
						<spring:url value="/champions/{championId}/edit" var="editUrl">
						<spring:param name="championId" value="${champion.id}" />
						</spring:url>
						<spring:url value="/champions/{championId}/newChangeRequest" var="championRequestUrl">
                        <spring:param name="championId" value="${champion.id}"/>
                    </spring:url>
					<a href="${fn:escapeXml(editUrl)}" >Edit</a> / 
                    <a href="${fn:escapeXml(championRemoveUrl)}">Remove</a><br>
                      <a href="${fn:escapeXml(championRequestUrl)}">New Change Request</a>
                </td>
    
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>