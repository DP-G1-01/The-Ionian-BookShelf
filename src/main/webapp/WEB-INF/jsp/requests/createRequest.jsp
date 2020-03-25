<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="requests">
	<h2>
		<c:if test="${request['new']}">New </c:if>
		Change Request
	</h2>
	<form:form modelAttribute="request" class="form-horizontal"
		id="add-change-request-form" action="/requests/saveChangeRequest">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Title" name="title" />
			<petclinic:inputField label="Description" name="description" />
			<c:if test="${request.item!=null}">
			<petclinic:inputField label="Item" name="item.title"/>
			</c:if>	
			<c:if test="${itemId != null || request.item!=null}">
			<petclinic:inputField label="Attribute1" name="changeItem[0]"  />
			<petclinic:inputField label="Attribute2" name="changeItem[1]"  />
			<petclinic:inputField label="Attribute3" name="changeItem[2]" />
			</c:if>
			<c:if test="${request.champion!=null}">
			<petclinic:inputField label="Champion" name="champion.name"/>
			</c:if>	
			<c:if test="${championId != null || request.champion!=null}">
			<petclinic:inputField label="Health" name="changeChamp[0]"  />
			<petclinic:inputField label="Mana" name="changeChamp[1]"  />
			<petclinic:inputField label="Energy" name="changeChamp[2]" />
			<petclinic:inputField label="Attack" name="changeChamp[3]"  />
			<petclinic:inputField label="Speed" name="changeChamp[4]" />
			</c:if>
		</div>
		<div class="form-group">
			<input type="hidden" name="id" value="${request.id}" />
			<input type="hidden" name="status" value="${request.status}" />
			<input type="hidden" name="item" value="${itemId}" />
			<input type="hidden" name="champion" value="${championId}" />
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${request['new']}">
						<button class="btn btn-default" type="submit">Add request</button>
					</c:when>

				</c:choose>
			</div>
		</div>
	</form:form>
</petclinic:layout>