<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="runePages">
	<jsp:attribute name="customScript">
        <script>
            function getMainBranch() {
                var selectedMainBranch = mainBranch.options[mainBranch.selectedIndex];
            }
            function getSecondaryBranch() {
                var selectedSecondaryBranch = secondaryBranch.options[secondaryBranch.selectedIndex];
            }
        </script>
    </jsp:attribute>
    <jsp:body>
	<h2>
		<c:if test="${runePage['new']}">New </c:if>
		Rune Page
	</h2>
	<form:form modelAttribute="runePage" class="form-horizontal"
		id="add-rune-page-form" action="/runePages/save">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Name" name="name" />
			<div class="control-group">
                   <petclinic:selectField name="mainBranch" label="Main Branch" names="${branches}" size="3"/>
            </div>
            <div class="control-group">
                    <petclinic:selectField name="secondaryBranch" label="Secondary Branch" names="${branches}" size="3"/>
            </div>
            <c:forEach var="runeList" items="${runes}" varStatus="loop">
            	<c:if test="${(loop.index)%4==0}">
        		<petclinic:selectField id="${loop.index/4}" name="keyRune" label="keyRune branch ${loop.index/4}" names="${runeList}" size="3"/>
        		</c:if>
        		<c:if test="${(loop.index)%4==1}">
        		<petclinic:selectField name="mainRune1" label="mainRune1 branch ${loop.index/4}" names="${runeList}" size="3"/>
        		</c:if>
        		<c:if test="${(loop.index)%4==2}">
        		<petclinic:selectField name="mainRune2" label="mainRune2 branch ${loop.index/4}" names="${runeList}" size="3"/>
        		</c:if>
        		<c:if test="${(loop.index)%4==3}">
        		<petclinic:selectField name="mainRune3" label="mainRune3 branch ${loop.index/4}" names="${runeList}" size="3"/>
        		</c:if>
   			</c:forEach>
   			<c:forEach var="runeList" items="${secondaryRunes}" varStatus="loop">
        		<petclinic:selectField id="sec1 ${loop.index }" name="secRune1" label="secRune1 branch ${loop.index}" names="${runeList}" size="3"/>
        		<petclinic:selectField id="sec2 ${loop.index }" name="secRune2" label="secRune2 branch ${loop.index}" names="${runeList}" size="3"/>
        		
   			</c:forEach>
		</div>
		<div class="form-group">
			<input type="hidden" name="id" value="${runePage.id}" />
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${runePage['new']}">
						<button class="btn btn-default" type="submit">Add RunePage</button>
					</c:when>

				</c:choose>
			</div>
		</div>
	</form:form>
	</jsp:body>
</petclinic:layout>
