<%--
 * index.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tib" tagdir="/WEB-INF/tags"%>
<tib:layout pageName="master.page.home">
	<h2>
		<spring:message code="master.page.home" />
	</h2>
	<div class="row">
		<div class="col-md-12">
			<spring:url value="/resources/images/champs.jpg" htmlEscape="true"
				var="champsImage" />
			<img class="img-responsive" src="${champsImage}" />
		</div>
	</div>
</tib:layout>
