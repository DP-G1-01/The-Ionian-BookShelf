<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="Edit actor">
	<form:form action="/${role}/edit" modelAttribute="actor">

		Properties with a * are required
		<br />

		<form:hidden path="id" />

		<jstl:if test="${role eq 'summoner'}">
			<jstl:choose>
				<jstl:when test="${actor.id eq null }">
					Main champs:
					<select id="champsId" name="champsId" multiple>
						<jstl:forEach var="champ" items="${champs}">
							<option value="${champ.id}">${champ.name}</option>
						</jstl:forEach>
					</select>
				</jstl:when>
				<jstl:otherwise>
					<form:hidden path="mains" />
				</jstl:otherwise>
			</jstl:choose>
			<form:hidden path="league" />
		</jstl:if>

		<petclinic:inputField label="Email*" name="email" />

		<jstl:choose>
			<jstl:when test="${actor.id == null }">
				<petclinic:inputField label="Username*" name="user.username" />
			</jstl:when>
			<jstl:otherwise>
				<form:hidden path="user.username" />
			</jstl:otherwise>
		</jstl:choose>

		<petclinic:password label="Password*" name="user.password" />
		<input type="submit" name="save" value="Save" class="btn btn-default" />
		<a href="/" class="btn btn-default">Cancel</a>
	</form:form>
</petclinic:layout>