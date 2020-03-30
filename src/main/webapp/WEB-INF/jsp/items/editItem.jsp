<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="items">
	<h2>
		<c:if test="${item['new']}">New </c:if>
		Item
	</h2>
	<form:form modelAttribute="item" class="form-horizontal"
		id="add-item-form" action="/items/save">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Title" name="title" />
			<petclinic:inputField label="Description" name="description" />
			<petclinic:inputField label="Attribute1" name="attributes[0]"  />
			<petclinic:inputField label="Attribute2" name="attributes[1]"  />
			<petclinic:inputField label="Attribute3" name="attributes[2]" />
			<div class="control-group">
                    <petclinic:selectField name="roles[0]" label="Role1" names="${role}" size="5" />
            </div>
	<%-- 		<div class="control-group">
                    <petclinic:selectField name="branch" label="Branch " names="${branch}" size="5"/>
            </div> 
			<petclinic:inputField label="Node" name="node" />
			--%>
		</div>
		<div class="form-group">
			<input type="hidden" name="id" value="${item.id}" />
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${item['new']}">
						<button class="btn btn-default" type="submit">Add item</button>
					</c:when>

				</c:choose>
			</div>
		</div>
	</form:form>
</petclinic:layout>