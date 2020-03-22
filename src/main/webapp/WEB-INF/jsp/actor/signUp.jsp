<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="tib" tagdir="/WEB-INF/tags"%>
<tib:layout pageName="master.page.signUp">

	<h1>
		<spring:message code="actor.signUp.subtitle" />
	</h1>
	<br />
	<h2>
		<spring:message code="actor.signUp.help" />
	</h2>
	<div>
		<div>
			<b><spring:message code="actor.signUp.summoner" /></b>
		</div>
		<span> <a href="createSummoner"> <img
				alt="<spring:message code="actor.signUp.summoner" />"
				src="<spring:url value="/resources/images/summoner-signup-icon.png" htmlEscape="true" />"
				width="30%" height="30%">
		</a>
		</span> <br /> <br />
		<div>
			<b><spring:message code="actor.signUp.reviewer" /></b>
		</div>
		<span> <a href="createReviewer"> <img
				alt="<spring:message code="actor.signUp.reviewer" />"
				src="<spring:url value="/resources/images/reviewer-signup-icon.png" htmlEscape="true" />"
				width="20%" height="20%">
		</a>
		</span> <br />
	</div>

	<br />
	<tib:button url="security/login" code="actor.cancel" />
</tib:layout>