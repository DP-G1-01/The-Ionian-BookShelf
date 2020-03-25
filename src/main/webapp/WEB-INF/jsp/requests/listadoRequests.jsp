<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="requests">
    <h2>Change Requests</h2>

    <table id="requestTable" class="table table-striped">
        <thead>
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>State</th>
            <th>Champion / Item</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requests}" var="request">
            <tr>
                <td>
                   <c:out value="${request.title}"/>
                </td>
                <td>
                    <c:out value="${request.description}"/>
                </td>
                <td>
                    <c:out value="${request.status}"/>
                </td>
                <td>
                 <c:out value="${request.champion.name}"/>
                 <c:out value="${request.item.title}"/>
                </td>
                <td>
                <c:if test = "${request.status == 'PENDING'}">
               	<spring:url value="/requests/{requestId}/accept" var="requestAcceptUrl">
                        <spring:param name="requestId" value="${request.id}"/>
                </spring:url>
                <spring:url value="/requests/{requestId}/reject" var="requestRejectUrl">
                        <spring:param name="requestId" value="${request.id}"/>
                </spring:url><spring:url value="/requests/{requestId}/remove" var="requestRemoveUrl">
                        <spring:param name="requestId" value="${request.id}"/>
                    </spring:url>
                <a href="${fn:escapeXml(requestAcceptUrl)}">Accept</a> / 
                <a href="${fn:escapeXml(requestRejectUrl)}">Reject</a> / 
                <a href="${fn:escapeXml(requestRemoveUrl)}">Remove</a>
    			</c:if>
    			</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>