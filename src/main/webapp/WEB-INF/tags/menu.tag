<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/" title="Home">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'champions'}"
					url="/champions/" title="champions">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Champions</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'runes'}" url="/runes"
					title="runes">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Runes</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'runePage'}"
					url="/runePages/mine" title="runes page">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Rune Page</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'items'}" url="/items"
					title="items">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Items</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'builds'}" url="/builds"
					title="builds">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Builds</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'threads'}" url="/threads"
					title="threads">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Threads</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'requests'}" url="/requests"
					title="requests">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Requests</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'error'}" url="/oups"
					title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
					<span>Error</span>
				</petclinic:menuItem>
				
				<sec:authorize access="hasAnyAuthority('summoner')">
					<petclinic:menuItem active="${name eq 'Display summoner details'}" url="/summoner/show"
					title="Show your profile">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Summoner details</span>
				</petclinic:menuItem>
				</sec:authorize>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="hasAnyAuthority('reviewer')">
					<li><a href="<c:url value="/summoner/all" />">All
							Summoners</a></li>
				</sec:authorize>
				<sec:authorize access="hasAnyAuthority('summoner')">
					<li><a href="<c:url value="/mine/builds" />">My Builds</a></li>
				</sec:authorize>
				<sec:authorize access="hasAnyAuthority('administrator')">
					<li><a
						href="<c:url value="/actor/administrator/createAdministrator" />">Register
							an admin</a></li>
					<li><a
						href="<c:url value="/actor/administrator/createReviewer" />">Register
							a Reviewer</a></li>

				</sec:authorize>
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/actor/signUp" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> 
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
