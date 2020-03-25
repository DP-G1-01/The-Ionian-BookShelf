<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="champions">
	<h2>
		<c:if test="${champion['new']}">New </c:if>
		Champion
	</h2>
	<form:form modelAttribute="champion" class="form-horizontal"
		id="add-champion-form" action="/champions/save">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Name" name="name" />
			<petclinic:inputField label="Description" name="description" />
			<petclinic:inputField label="Health" name="health" />
			<petclinic:inputField label="Mana" name="mana" />
			<petclinic:inputField label="Energy" name="energy" />
			<petclinic:inputField label="Attack" name="attack" />
			<petclinic:inputField label="Speed" name="speed" />
			<div class="control-group">
                    <petclinic:selectField name="role" label="Role " names="${role}" size="5"/>
            </div>
		</div>
		<div class="form-group">
			<input type="hidden" name="id" value="${champion.id}" />
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${champion['new']}">
						<button class="btn btn-default" type="submit">Add Champion</button>
					</c:when>

				</c:choose>
			</div>
		</div>
	</form:form>
</petclinic:layout>