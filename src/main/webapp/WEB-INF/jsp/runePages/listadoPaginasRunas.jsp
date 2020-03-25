<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="runePages">
    <h2>RunePages</h2>

	<spring:url value="/runePages/new" var="runePageNewUrl">
    </spring:url>
    <a style="background-color: #000000 ; padding: 10px; border-radius:20px; float:right;" href="${fn:escapeXml(runePageNewUrl)}">Add New Rune Page</a><br><br>
    <br>
    <table id="runePageTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 120px">Main Branch</th>
            <th style="width: 400px;">Key Rune</th>
            <th>Main Rune 1</th>
            <th style="width: 100px">Main Rune 2</th>
            <th style="width: 120px">Main Rune 3</th>
            <th style="width: 120px">Secondary Branch</th>
            <th style="width: 120px">Secondary Rune 1</th>
            <th style="width: 120px">Secondary Rune 3</th>
            <th style="width: 120px">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${runePages}" var="runePage">
            <tr>
                <td>
                   <c:out value="${runePage.name}"/>
                </td>
                <td>
                   <c:out value="${runePage.mainBranch.name}"/>
                </td>
                <td>
                    <c:out value="${runePage.keyRune.name}"/>
                </td>
                <td>
                    <c:out value="${runePage.mainRune1.name}"/>
                </td>
                <td>
                 <c:out value="${runePage.mainRune2.name}"/>
                </td>
                <td>
                 <c:out value="${runePage.mainRune3.name}"/>
                </td>
                <td>
                   <c:out value="${runePage.secondaryBranch.name}"/>
                </td>
                <td>
                 <c:out value="${runePage.secRune1.name}"/>
                </td>
                <td>
                 <c:out value="${runePage.secRune2.name}"/>
                </td>
               <td>
               	<spring:url value="/runePages/{runePageId}/remove" var="runePageRemoveUrl">
                        <spring:param name="runePageId" value="${runePage.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(runePageRemoveUrl)}">Remove rune page</a>
                </td>
    
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>