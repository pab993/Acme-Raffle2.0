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


<display:table name="codes" pagesize="5" class="displaytag"
	requestURI="${requestURI}" id="row">

	<spring:message code="code.name" var="headerTag" />
	<display:column property="name" title="${headerTag}" />
	
	<spring:message code="code.win" var="headerTag" />
	<display:column property="winner" title="${headerTag}" />

<security:authorize access="hasRole('USER')">
	<spring:message code="code.moment" var="headerTag" />
	<display:column property="moment" title="${headerTag}"
		format="{0,date,dd/MM/yyyy HH:mm}" />
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${row.prize.raffle.publicationTime > now}">
		<display:column>
			<a href="code/edit.do?codeId=${row.id}"> <spring:message
						code="code.edit"></spring:message></a>
		</display:column>
	</jstl:if>
	
</security:authorize>

</display:table>