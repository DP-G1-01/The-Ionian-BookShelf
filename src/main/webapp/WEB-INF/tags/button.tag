<%@ tag language="java" body-content="empty"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tib" tagdir="/WEB-INF/tags"%>

<%-- Attributes --%>
<%@ attribute name="code" required="true"%>
<%@ attribute name="url" required="true"%>


<%-- Definition --%>

<button class="btn btn-default" type="button"
	onclick="javascript: relativeRedir('${url}')">
	<spring:message code="${code}" />
</button>

