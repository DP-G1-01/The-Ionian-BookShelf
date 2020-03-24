<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="threads">
    <h2>
        <c:if test="${thread['new']}">New </c:if> Thread
    </h2>
    <form:form modelAttribute="thread" class="form-horizontal" id="add-thread-form" action="/threads/save">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Title" name="title"/>
            <petclinic:inputField label="Description" name="description"/>
        </div>
        <div class="form-group">
        	<input type="hidden" name="id" value="${threadId}"/> 
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${thread['new']}">
                        <button class="btn btn-default" type="submit">Add Thread</button>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
