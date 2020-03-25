<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="Display summoner details">
	Email: <jstl:out value="${summoner.email}" />
	<br />

	Username: <jstl:out value="${summoner.user.username}" />
	<br />

	Mains: 
	<jstl:forEach items="${summoner.mains}" var="${main}">
		Champion name: 
		<jstl:out value="${main.name}" />
		<petclinic:button url="champion/display.do?championId=${main.id}"
			code="Display champ" />
	</jstl:forEach>
	<br />

	League: 
		<jstl:out value="${summoner.league.name}" />
	<petclinic:button
		url="league/display.do?leagueId=${summoner.league.id}"
		code="Display league" />
	<br />

	<petclinic:button url="summoner/edit.do" code="Edit" />

	<petclinic:button url="/" code="Welcome page" />
</petclinic:layout>