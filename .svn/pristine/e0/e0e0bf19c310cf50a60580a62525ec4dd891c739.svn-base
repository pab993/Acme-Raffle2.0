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
	<div>
		<jstl:if test="${errorMessage != null}">
			<br />
			<span class="message" style="color: red"><spring:message
					code="${errorMessage}" /></span>
		</jstl:if>
	</div>
</security:authorize>

<display:table name="properties" pagesize="5" class="displaytag"
	id="row">

	<spring:message code="property.name" var="headerTag" />
	<display:column property="name" title="${headerTag}" />

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="property/edit.do?propertyId=${row.id}"> <spring:message
					code="property.edit" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<jstl:if test="${!propertiesPrize.contains(row) && propertiesPrize != null}">

				<a
					href="manager/property/addProperty.do?propertyId=${row.id}&prizeId=${prizeId}">
					<spring:message code="add.property"></spring:message>
				</a>

			</jstl:if>
		</display:column>

		<display:column>
			<jstl:if test="${propertiesPrize.contains(row)}">

				<a
					href="manager/property/deleteProperty.do?propertyId=${row.id}&prizeId=${prizeId}">
					<spring:message code="delete.property"></spring:message>
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<%-- <jstl:if test="${row.raffle.publicationTime > now}">
			<display:column>
				<a href="admin/prize/deleteProperty.do?propertyId=${row.id}&prizeId=${prize.id}"> <spring:message
						code="delete.property"></spring:message></a>
			</display:column>
		</jstl:if> --%>

</display:table>

<security:authorize access="hasRole('ADMIN')">
	<a href="property/create.do"> <spring:message
			code="property.create" />
	</a>
</security:authorize>