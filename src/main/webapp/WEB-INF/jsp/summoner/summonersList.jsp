<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Summoners">
    <h2>Items</h2>

    <table id="summonerTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 150px;">Banned</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${summoners}" var="summoner">
            <tr>
                <td>
                   <c:out value="${summoner.user.username}"/>
                </td>
                <td>
                    <c:out value="${summoner.banned}"/>
                </td>
               <td>
               <c:if test="${summoner.banned == false}">
               	<spring:url value="/summoner/{summonerId}/ban" var="summonerBanURL">
                        <spring:param name="summonerId" value="${summoner.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(summonerBanURL)}">Ban user</a>
                    </c:if>
                    
               <c:if test="${summoner.banned == true}">
                <spring:url value="/summoner/{summonerId}/desban" var="summonerDesbanURL">
                        <spring:param name="summonerId" value="${summoner.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(summonerDesbanURL)}">Desban user</a>
                </c:if>
                </td>
    
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>