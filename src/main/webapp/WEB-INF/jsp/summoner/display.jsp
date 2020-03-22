<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tib" tagdir="/WEB-INF/tags"%>

<tib:layout pageName="summoner.display">
	<spring:message code="actor.email" />: <jstl:out
		value="${summoner.email}" />
	<br />

	<spring:message code="actor.userAccount.username" />: <jstl:out
		value="${summoner.userAccount.username}" />
	<br />

	<spring:message code="summoner.mains" />: 
	<jstl:forEach items="${summoner.mains}" var="${main}">
		<spring:message code="champion.name" />: 
		<jstl:out value="${main.name}" />
		<tib:button url="champion/display.do?championId=${main.id}"
			code="champion.display" />
	</jstl:forEach>
	<br />

	<spring:message code="summoner.league" />: 
		<jstl:out value="${summoner.league.name}" />
	<tib:button url="league/display.do?leagueId=${summoner.league.id}"
		code="league.display" />
	<br />

	<tib:button url="summoner/edit.do" code="actor.edit" />

	<tib:button url="/" code="actor.back.welcome" />
</tib:layout>