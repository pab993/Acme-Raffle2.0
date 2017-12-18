<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-Raffle2.0 Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv" href="welcome/index.do"><spring:message
						code="master.page.home" /></a></li>
			<li><a class="fNiv" href="raffle/browse.do"><spring:message
						code="master.page.browse.raffle" /></a></li>
			<li><a class="fNiv" href="raffle/myList.do"><spring:message
						code="master.page.myListRaffle" /></a></li>
			<li><a class="fNiv" href="bill/list.do"><spring:message
						code="master.page.bill" /></a></li>
			<li><a class="fNiv" href="creditCard/edit.do"><spring:message
						code="master.page.creditCard.edit" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv" href="welcome/index.do"><spring:message
						code="master.page.home" /></a></li>
			<li><a class="fNiv" href="raffle/browse.do"><spring:message
						code="master.page.browse.raffle" /></a></li>
			<li><a class="fNiv" href="audit/myList.do"><spring:message
						code="master.page.myList.audit" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv" href="welcome/index.do"><spring:message
						code="master.page.home" /></a></li>
			<li><a class="fNiv" href="bill/listAll.do"><spring:message
						code="master.page.bill" /></a></li>
			<li><a class="fNiv" href="raffle/browse.do"><spring:message
						code="master.page.browse.raffle" /></a></li>
			<li><a class="fNiv" href="admin/user/browse.do"><spring:message
						code="master.page.browse.user" /></a></li>
			<li><a class="fNiv" href="admin/manager/browse.do"><spring:message
						code="master.page.browse.manager.debtor" /></a></li>
			<li><a class="fNiv" href="property/list.do"><spring:message
						code="master.page.property" /></a></li>
			<li><a class="fNiv" href="admin/comment/list.do"><spring:message
						code="master.page.comment.inappropiate" /></a></li>
			<li><a class="fNiv" href="dashboard/list.do"><spring:message
						code="master.page.dashboard" /></a></li>
			<li><a href="tabooWord/list.do"><spring:message
						code="master.page.tabooWord" /></a></li>
			<li><a href="configurationSystem/edit.do"><spring:message
						code="configurationSystem" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv" href="welcome/index.do"><spring:message
						code="master.page.home" /></a></li>
			<li><a class="fNiv" href="raffle/browse.do"><spring:message
						code="master.page.browse.raffle" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/action-1.do"><spring:message
								code="master.page.customer.action.1" /></a></li>
					<li><a href="customer/action-2.do"><spring:message
								code="master.page.customer.action.2" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv" href="welcome/index.do"><spring:message
						code="master.page.home" /></a></li>
			<li><a class="fNiv" href="raffle/browse.do"><spring:message
						code="master.page.browse.raffle" /></a></li>
			<li><a class="fNiv" href="raffle/myListUser.do"><spring:message
						code="master.page.listRaffleByUser" /></a></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="welcome/index.do"><spring:message
						code="master.page.home" /></a></li>
			<li><a class="fNiv" href="raffle/browse.do"><spring:message
						code="master.page.browse.raffle" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv" href="actor/registerManager.do"><spring:message
						code="master.page.register.actorRegisterManager" /></a></li>
			<li><a class="fNiv" href="actor/registerUser.do"><spring:message
						code="master.page.register.actorRegisterUser" /></a></li>
			<li><a class="fNiv" href="actor/registerAuditor.do"><spring:message
						code="master.page.register.actorRegisterAuditor" /></a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/seeProfile.do"><spring:message
								code="master.page.actor.profile" /></a></li>
					<li><a href="socialIdentity/list.do"><spring:message
								code="master.page.socialIdentity" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

