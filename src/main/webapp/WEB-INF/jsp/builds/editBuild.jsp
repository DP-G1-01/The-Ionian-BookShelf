<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="Build" onload="updateForm()">
	<h2>
		<c:if test="${build['new']}">New </c:if>
		Build
	</h2>
	
	<form:form modelAttribute="build" class="form-horizontal"
		id="build-form" action="/mine/builds/save">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Title" name="title" />
			<petclinic:inputField label="Description" name="description" />
			<c:choose>
            <c:when test="${build['new'] || show != true}">
			<div class="control-group">
                    <petclinic:selectField name="champion" label="Champion" names="${champions}" size="1" />
            </div>
            <div class="control-group">
                    <petclinic:selectField name="runePage" label="Rune Page" names="${runePages}" size="1" />
            </div>
			<div class="control-group">
                    <petclinic:selectField name="items[0]" label="Items" names="${items}" size="6" />
            </div>
            </c:when>
            <c:otherwise>
			<petclinic:inputField label="Champion" name="champion.name" />
			<petclinic:inputField label="Rune Page" name="runePage.name" />
			<div class="form-group ">
        	<label class="col-sm-2 control-label">Items</label>
        	<div class="col-sm-10">
            <input id="items" name="items" class="form-control" type="text" value="${sitems}">
        	</div>
   			</div>
            </c:otherwise>
            </c:choose>
            <c:choose>
            <c:when test="${!(build['new']) && build.visibility == true}">
            <div style="float:right">
            <input type="checkbox" id="visibility" name="visibility" value="true" checked disabled>
			<label for="visibility">Visibilidad</label>
			<input type="hidden" name="visibility" value="${build.visibility}" />
			<input type="hidden" name="thread" value="${build.thread.id}" />
			</div>
            </c:when>
            <c:when test="${!(build['new'])}">
            <div style="float:right">
            <input type="checkbox" id="visibility" name="visibility" value="true">
			<label for="visibility">Visibilidad</label>
			</div>
            </c:when>
            </c:choose>
		</div>
		<div class="form-group">
			<input type="hidden" name="id" value="${build.id}" />
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${build['new']}">
						<input type="hidden" name="summoner" value="${summonerId}" />
						<button class="btn btn-default" type="submit">Add build</button>
					</c:when>
					<c:when test="${show == true}">
					<a href="/threads/${build.thread.id}">URL del Thread</a>
					</c:when>
					<c:otherwise>
						<input type="hidden" name="summoner" value="${build.summoner.id}" />
						<button class="btn btn-default" type="submit">Update build</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</petclinic:layout>

<script>
function updateForm() {
	var url = window.location.href;
	console.log(url);
	if(url.includes('edit')) {
		document.getElementById("build-form").action = url;
	}
}
</script>