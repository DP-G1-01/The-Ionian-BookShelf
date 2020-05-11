<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="builds">
	<h2>Builds</h2>

	<spring:url value="/mine/builds/new" var="buildNewUrl">
	</spring:url>
	<a
		style="background-color: #000000; padding: 10px; border-radius: 20px; float: right;"
		href="${fn:escapeXml(buildNewUrl)}">Add New Build</a>
	<br>
	<br>
	<br>
	<table id="buildTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Title</th>
				<th style="width: 400px;">Description</th>
				<th style="width: 130px">Champion</th>
				<th style="width: 120px">Rune Page</th>
				<th style="width: 300px">Items</th>
				<th style="width: 300px">Punctuation</th>
				<security:authorize access="hasAnyAuthority('summoner')">
					<th style="width: 120px;">Vote</th>
				</security:authorize>
				<th style="width: 180px"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${builds}" var="build">
				<tr>
					<td><c:out value="${build.title}" /></td>
					<td><c:out value="${build.description}" /></td>
					<td><c:out value="${build.champion.name}" /></td>
					<td><c:out value="${build.runePage.name}" /></td>
					<td><c:forEach items="${build.items}" var="item">
							<c:out value="${item.title}" />
						</c:forEach></td>
					<td><c:out value="${build.punctuation}" /></td>
					<security:authorize access="hasAnyAuthority('summoner')">
						<td><spring:url value="/builds/{buildId}/upVote"
								var="buildUpVote">
								<spring:param name="buildId" value="${build.id}"></spring:param>
							</spring:url> <a href="${fn:escapeXml(buildUpVote)}">Up Vote Build</a> <br>
							<spring:url value="/builds/{buildId}/downVote"
								var="buildDownVote">
								<spring:param name="buildId" value="${build.id}"></spring:param>
							</spring:url> <a href="${fn:escapeXml(buildDownVote)}">Down Vote Build</a></td>
					</security:authorize>
					<td><spring:url
							value="${requestScope['javax.servlet.forward.request_uri']}/{buildId}"
							var="buildShowUrl">
							<spring:param name="buildId" value="${build.id}" />
						</spring:url> <a href="${fn:escapeXml(buildShowUrl)}">Show</a> <c:if
							test="${requestScope['javax.servlet.forward.request_uri'] == '/mine/builds'}">
							<spring:url value="/mine/builds/{buildId}/edit"
								var="buildEditUrl">
								<spring:param name="buildId" value="${build.id}" />
							</spring:url>
							<spring:url value="/mine/builds/{buildId}/remove"
								var="buildRemoveUrl">
								<spring:param name="buildId" value="${build.id}" />
							</spring:url>
                 / <a href="${fn:escapeXml(buildEditUrl)}">Edit</a>
                 / <a href="${fn:escapeXml(buildRemoveUrl)}">Remove</a>
						</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>