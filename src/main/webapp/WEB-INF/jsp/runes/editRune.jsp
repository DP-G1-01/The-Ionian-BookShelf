<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="runes">
	<h2>
		<c:if test="${rune['new']}">New </c:if>
		Rune
	</h2>
	<form:form modelAttribute="rune" class="form-horizontal"
		id="add-rune-form" action="/runes/save">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Name" name="name" />
			<petclinic:inputField label="Description" name="description" />
			<div class="control-group">
				<petclinic:selectField name="branch" label="Branch "
					names="${branch}" size="5" />
			</div>
			<petclinic:inputField label="Node" name="node" />
		</div>
		<div class="form-group">
			<input type="hidden" name="id" value="${rune.id}" />
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${rune['new']}">
						<button class="btn btn-default" type="submit">Add Rune</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Update
							Rune</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</petclinic:layout>