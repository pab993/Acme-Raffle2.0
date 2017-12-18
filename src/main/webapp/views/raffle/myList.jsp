<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table name="raffles" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
	<spring:message code="raffle.logo" var="headerTag" />
	<display:column title="${headerTag}">
		<img height="65" width="65" src="${row.getLogo()}" />
	</display:column>
	
	<spring:message code="raffle.title" var="headerTag" />
	<display:column property="title" title="${headerTag}"/>
	
	<spring:message code="raffle.description" var="headerTag" />
	<display:column property="description" title="${headerTag}"/>
	
	<spring:message code="raffle.publicationTime" var="headerTag" />
	<display:column property="publicationTime" title="${headerTag}" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="raffle.deadline" var="headerTag" />
	<display:column property="deadline" title="${headerTag}" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<display:column>
		<a href="raffle/display.do?raffleId=${row.id}"> <spring:message
					code="raffle.display"></spring:message></a>
	</display:column>	
	
	<display:column>
			<a href="prize/myList.do?raffleId=${row.id}"> <spring:message
					code="listPrizeByRaffle"></spring:message></a>
	</display:column>
	
			
		
</display:table>