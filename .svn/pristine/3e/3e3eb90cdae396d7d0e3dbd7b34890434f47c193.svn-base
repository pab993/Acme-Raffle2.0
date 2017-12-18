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

	<security:authorize access="hasRole('USER')">
	
		<display:column>
			<a href="code/myList.do?prizeId=${row.id}"> <spring:message
					code="listCodeByPrizeAndUser"></spring:message></a>
		</display:column>

	</security:authorize>

</display:table>