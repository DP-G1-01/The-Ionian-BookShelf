<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="messages">
	<h2>
		Messages from thread:
		<c:out value="${title}"></c:out>
	</h2>
	
	<table id="messagesTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 200px;">Text</th>
				<th style="width: 100px;">Moment</th>
				<th style="width: 120px;">Summoner</th>
				<th style="width: 120px;">Punctuation</th>
				<security:authorize access="hasAnyAuthority('summoner')">
					<th style="width: 120px;">Vote</th>
				</security:authorize>
				<security:authorize
					access="hasAnyAuthority('administrator','reviewer')">
					<th style="width: 120px;">Actions</th>
				</security:authorize>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${messages}" var="message">
				<tr>
					<td><c:out value="${message.text}" /></td>
					<td><c:out value="${message.moment}" /></td>
					<td><c:out value="${message.summoner.user.username}" /></td>
					<td><c:out value="${message.punctuation}" /></td>
					<security:authorize access="hasAnyAuthority('summoner')">
						<td><spring:url value="{threadId}/message/{messageId}/upVote"
								var="messageUpVote">
								<spring:param name="threadId" value="${message.thread.id}"></spring:param>
								<spring:param name="messageId" value="${message.id}"></spring:param>
								</spring:url> <a href="${fn:escapeXml(messageUpVote)}">Up Vote Message</a> <br>
							<spring:url value="{threadId}/message/{messageId}/downVote"
								var="messageDownVote">
								<spring:param name="threadId" value="${message.thread.id}"></spring:param>
								<spring:param name="messageId" value="${message.id}"></spring:param>
							</spring:url> <a href="${fn:escapeXml(messageDownVote)}">Down Vote message</a>
						</td>
					</security:authorize>

					<security:authorize
						access="hasAnyAuthority('administrator','reviewer')">
						<td><spring:url
								value="{threadId}/messages/{messageId}/remove"
								var="messageRemoveUrl">
								<spring:param name="messageId" value="${message.id}"></spring:param>
								<spring:param name="threadId" value="${message.thread.id}"></spring:param>
							</spring:url> <a href="${fn:escapeXml(messageRemoveUrl)}">Remove Message</a></td>
					</security:authorize>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<security:authorize access="hasAnyAuthority('summoner')">
		<spring:url value="{threadId}/messages/new" var="addMessageUrl">
			<spring:param name="threadId" value="${threadId}" />
		</spring:url>
		<a href="${fn:escapeXml(addMessageUrl)}" class="btn btn-default">Add
			New Message on this Thread</a>
	</security:authorize>

</petclinic:layout>
