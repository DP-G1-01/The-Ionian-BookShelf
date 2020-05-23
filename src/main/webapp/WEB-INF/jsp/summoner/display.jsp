<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="Display summoner details">
	Email: <jstl:out value="${summoner.email}" />
	<br />

	Username: <jstl:out value="${summoner.user.username}" />
	<br />

	Mains: 
	<jstl:forEach items="${summoner.mains}" var="main">
		<jstl:out value="${main.name}" /> |
	</jstl:forEach>
	<spring:url value="/champions" var="championsURL">
	</spring:url>
	<a href="${fn:escapeXml(championsURL)}">Display Champions List</a>

	<br />
	
	League: 
		<jstl:out value="${summoner.league.name}" />

	<spring:url value="/threads/{leagueThreadId}" var="leagueThreadURL">
		<spring:param name="leagueThreadId" value="${summoner.league.thread.id}" />
	</spring:url>
	<a href="${fn:escapeXml(leagueThreadURL)}">Show League Thread</a>

	<br />	
	<spring:url value="/summoner/edit" var="editSummonerURL">
	</spring:url>
	<a href="${fn:escapeXml(editSummonerURL)}">Edit</a>

	<spring:url value="/" var="welcomeURL">
	</spring:url>
	<a href="${fn:escapeXml(welcomeURL)}">Welcome Page</a>
</petclinic:layout>