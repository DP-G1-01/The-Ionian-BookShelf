<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<petclinic:layout pageName="Display admin details">


	Email: <jstl:out value="${administrator.email}" />
	<br />

	Username: <jstl:out value="${administrator.user.username}" />
	<br />

	<petclinic:button url="administrator/edit.do" code="Edit" />

	<petclinic:button url="/" code="Welcome page" />
</petclinic:layout>