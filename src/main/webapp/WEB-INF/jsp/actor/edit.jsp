<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tib" tagdir="/WEB-INF/tags"%>

<tib:layout pageName="actor.edit">
	<form:form action="${role}/edit.do" modelAttribute="actor">

		<spring:message code="actor.required" />
		<br />

		<form:hidden path="id" />
		<form:hidden path="userAccount.authorities" />

		<jstl:if test="${role eq 'summoner'}">
			<form:hidden path="mains" />
			<form:hidden path="league" />
		</jstl:if>

		<tib:inputField label="actor.email" name="email" />

		<jstl:choose>
			<jstl:when test="${actor.id == 0 }">
				<tib:inputField label="actor.userAccount.username"
					name="userAccount.username" />
			</jstl:when>
			<jstl:otherwise>
				<form:hidden path="userAccount.id" />
				<form:hidden path="userAccount.username" />
			</jstl:otherwise>
		</jstl:choose>

		<tib:password label="actor.userAccount.password"
			name="userAccount.password" />


		<input type="submit" name="save"
			value="<spring:message code="actor.save" />" class="btn btn-default" />
		<tib:button url="/" code="actor.cancel" />

	</form:form>
</tib:layout>