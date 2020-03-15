<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
    <h2>Owners</h2>

    <table id="runeTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Description</th>
            <th>Node</th>
            <th style="width: 120px">Branch</th>
            <th style="width: 120px">Actions</th>
            <th>Id</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${runes}" var="rune">
            <tr>
                <td>
                   <c:out value="${rune.name}"/>
                </td>
                <td>
                    <c:out value="${rune.description}"/>
                </td>
                <td>
                    <c:out value="${rune.node}"/>
                </td>
                <td>
                 <c:out value="${rune.branch.name}"/>
                </td>
               <td>
               	<spring:url value="/runes/{runeId}/remove" var="runeRemoveUrl">
                        <spring:param name="runeId" value="${rune.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(runeRemoveUrl)}">Remove owner</a>
                </td>
                <td>
                 <c:out value="${rune.id}"/>
                </td>
    
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>