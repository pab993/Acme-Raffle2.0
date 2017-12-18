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
<security:authorize access="hasRole('ADMIN')">	

<display:table name="users" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
	<spring:message code="user.name" var="headerTag" />
	<display:column property="name" title="${headerTag}"/>
	
	<spring:message code="user.surname" var="headerTag" />
	<display:column property="surname" title="${headerTag}"/>
	
	<spring:message code="user.email" var="headerTag" />
	<display:column property="email" title="${headerTag}"/>
	
	<display:column>
		<a href="actor/seeProfile.do?actorId=${row.id}"> <spring:message
					code="user.profile"></spring:message></a>
	</display:column>
	
	<display:column>
		<jstl:if test="${!userBan.contains(row)}">
		
			<a href="admin/user/ban.do?userId=${row.id}"> <spring:message
					code="user.ban"></spring:message></a>
		
		</jstl:if>
		</display:column>
		<display:column>
		<jstl:if test="${userBan.contains(row)}">
		
			<a href="admin/user/unban.do?userId=${row.id}"> <spring:message
					code="user.unban"></spring:message></a>
		</jstl:if>
		</display:column>
	

</display:table>

</security:authorize>