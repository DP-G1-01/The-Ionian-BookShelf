<%@ tag trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="tib" tagdir="/WEB-INF/tags"%>

<%@ attribute name="pageName" required="true"%>
<%@ attribute name="customScript" required="false" fragment="true"%>

<!doctype html>
<html>
<tib:htmlHeader htmlHeader="${pageName}" />

<body>
	<tib:bodyHeader menuName="${pageName}" />

	<div class="container-fluid">
		<div class="container xd-container">

			<jsp:doBody />

			<tib:pivotal />
		</div>
	</div>
	<tib:footer />
	<jsp:invoke fragment="customScript" />

</body>

</html>
