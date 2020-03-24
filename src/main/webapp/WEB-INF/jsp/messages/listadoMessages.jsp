<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="messages">
    <h2>Messages</h2>

    <table id="messagesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Text</th>
            <th style="width: 100px;">Moment</th>
            <th style="width: 120px;">Summoner</th>
            <th style="width: 120px;">Actions</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${messages}" var="message">
            <tr>
                <td>
                <c:out value="${message.text}"/>
                </td>
                <td>
                    <c:out value="${message.moment}"/>
                </td>
                <td>
                    <c:out value="${message.summoner.userAccount.username}"/>
                </td>
                <td>
                <spring:url value="messages/{messageId}/remove" var="messageRemoveUrl">
                <spring:param name="messageId" value="${message.id}"></spring:param>
                <spring:param name="threadId" value="${message.thread.id}"></spring:param>
                </spring:url>
                <a href= "${fn:escapeXml(messageRemoveUrl)}">Remove Message</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
           <spring:url value="{threadId}/messages/new" var="addMessageUrl">
           <spring:param name="threadId" value="${threadId}"/>
           </spring:url>
    	<a href="${fn:escapeXml(addMessageUrl)}" class="btn btn-default">Add New Message on this Thread</a>
    
</petclinic:layout>
