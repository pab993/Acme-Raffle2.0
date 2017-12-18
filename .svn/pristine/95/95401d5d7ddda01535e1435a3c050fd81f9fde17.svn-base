<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<security:authorize access="hasRole('ADMIN')">

	<div>
		<jstl:if test="${errorMessage != null}">
			<br />
			<span class="message" style="color: red"><spring:message
					code="${errorMessage}" /></span>
		</jstl:if>
	</div>

	<display:table name="managers" pagesize="5" class="displaytag"
		requestURI="${requestURI}" id="row">

		<spring:message code="manager.name" var="headerTag" />
		<display:column property="name" title="${headerTag}" />

		<spring:message code="manager.surname" var="headerTag" />
		<display:column property="surname" title="${headerTag}" />

		<spring:message code="manager.email" var="headerTag" />
		<display:column property="email" title="${headerTag}" />

		<spring:message code="manager.phone" var="headerTag" />
		<display:column property="phone" title="${headerTag}" />

		<spring:message code="manager.enabled" var="headerTag" />
		<display:column property="userAccount.enabled" title="${headerTag}" />

		<display:column>
			<jstl:choose>
				<jstl:when test="${row.userAccount.enabled eq true}">
					<a href="admin/manager/ban.do?managerId=${row.id}"> <spring:message
							code="manager.ban"></spring:message></a>
				</jstl:when>
				<jstl:otherwise>
					<a href="admin/manager/unban.do?managerId=${row.id}"> <spring:message
							code="manager.unban"></spring:message></a>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</display:table>

</security:authorize>