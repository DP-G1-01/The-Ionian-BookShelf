<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="items">
    <h2>Items</h2>

	<spring:url value="/items/new" var="itemNewUrl">
    </spring:url>
    <a style="background-color: #000000 ; padding: 10px; border-radius:20px; float:right;" href="${fn:escapeXml(itemNewUrl)}">Add New Item</a><br><br>
    <br>
    <table id="itemTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Title</th>
            <th style="width: 400px;">Description</th>
            <th style="width: 100px">Attributes</th>
            <th style="width: 120px">Roles</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${items}" var="item">
            <tr>
                <td>
                   <c:out value="${item.title}"/>
                </td>
                <td>
                    <c:out value="${item.description}"/>
                </td>
                <td>
                    <c:out value="${item.attributes}"/>
                </td>
                <td>
                  <c:forEach items="${item.roles}" var="rol">
                  	<c:out value="${rol.name}"/>
                  </c:forEach>
                </td> 
               <td>
               	<spring:url value="/items/{itemId}/remove" var="itemRemoveUrl">
                        <spring:param name="itemId" value="${item.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(itemRemoveUrl)}">Remove item</a>
                </td>
    
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>