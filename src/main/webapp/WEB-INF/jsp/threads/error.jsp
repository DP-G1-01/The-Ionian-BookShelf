<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="error">
	<h2>
		Lo sentimos, el thread no se puede eliminar porque est� vinculado a una liga o a una build.
	</h2>
	
	<spring:url value="/threads" var="threadList">
    </spring:url>
    <a style="background-color: #000000 ; padding: 10px; border-radius:20px; float:right;" href="${fn:escapeXml(threadList)}">Volver al listado de Threads</a><br><br>
    
</petclinic:layout>
