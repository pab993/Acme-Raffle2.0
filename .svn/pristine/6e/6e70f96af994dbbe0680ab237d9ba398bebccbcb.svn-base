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

<security:authorize access="hasRole('MANAGER')">
<jstl:if test="${raffle.publicationTime > now}">
	<div>
		<h3>
			<a href="prize/create.do?raffleId=${raffle.id}"> <spring:message
						code="createPrize"></spring:message></a>
		</h3>
	</div>
</jstl:if>
</security:authorize>

<display:table name="prizes" pagesize="5" class="displaytag"
	requestURI="${requestURI}" id="row">

	<spring:message code="prize.name" var="headerTag" />
	<display:column property="name" title="${headerTag}" />

	<spring:message code="prize.description" var="headerTag" />
	<display:column property="description" title="${headerTag}" />

	<display:column>
		<a href="prize/display.do?prizeId=${row.id}"> <spring:message
				code="prize.display"></spring:message></a>
	</display:column>

	<display:column>
		<a href="property/listByPrize.do?prizeId=${row.id}"> <spring:message
				code="prize.properties"></spring:message></a>
	</display:column>

	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${row.raffle.publicationTime > now}">

			<display:column>
				<a href="manager/property/listPrize.do?prizeId=${row.id}"> <spring:message
						code="prize.addProperties"></spring:message></a>
			</display:column>

			<display:column>
				<a href="prize/editPrize.do?prizeId=${row.id}"> <spring:message
						code="prize.edit"></spring:message></a>
			</display:column>

			<display:column>
				<a href="prize/regenerateCode.do?prizeId=${row.id}"> <spring:message
						code="prize.regenerateCode"></spring:message></a>
			</display:column>

		</jstl:if>

		<jstl:if test="${principal.id eq row.raffle.manager.id}">
			<display:column>
				<a href="code/listByPrize.do?prizeId=${row.id}"> <spring:message
						code="prize.codes"></spring:message></a>
			</display:column>
		</jstl:if>

	</security:authorize>
	
	<security:authorize access="hasRole('USER')">
		<display:column>
			<jstl:if
				test="${row.raffle.publicationTime lt now and row.raffle.deadline gt now }">
				<a href="code/enter.do?prizeId=${row.id}"> <spring:message
						code="prize.raffle.enterCode"></spring:message></a>
			</jstl:if>
		</display:column>
	</security:authorize>

</display:table>