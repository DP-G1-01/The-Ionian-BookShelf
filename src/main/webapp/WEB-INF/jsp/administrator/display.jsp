<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tib" tagdir="/WEB-INF/tags"%>
<tib:layout pageName="administrator.display">


	<spring:message code="actor.email" />: <jstl:out
		value="${administrator.email}" />
	<br />

	<spring:message code="actor.userAccount.username" />: <jstl:out
		value="${administrator.userAccount.username}" />
	<br />

	<tib:button url="administrator/edit.do" code="actor.edit" />

	<tib:button url="/" code="actor.back.welcome" />
</tib:layout>