<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="runes">
    <h2>Runes</h2>

	<spring:url value="/runes/new" var="runeNewUrl">
    </spring:url>
    <a style="background-color: #000000 ; padding: 10px; border-radius:20px; float:right;" href="${fn:escapeXml(runeNewUrl)}">Add New Rune</a><br><br>
    <br>
    <table id="runeTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 400px;">Description</th>
            <th>Node</th>
            <th style="width: 100px">Branch</th>
            <th style="width: 120px">Actions</th>
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
                    <a href="${fn:escapeXml(runeRemoveUrl)}">Remove rune</a>
                </td>
    
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>