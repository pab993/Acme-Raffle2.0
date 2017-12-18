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

<display:table name="audits" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
	<spring:message code="audit.moment" var="headerTag" />
	<display:column property="moment" title="${headerTag}" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="audit.text" var="headerTag" />
	<display:column property="text" title="${headerTag}"/>
	
	<spring:message code="audit.raffle" var="headerTag" />
	<display:column title="${headerTag}">
		<jstl:out value="${row.raffle.title }"></jstl:out>
	</display:column>
	
	<security:authorize access="hasRole('AUDITOR')">
			<display:column>
				<jstl:if test="${row.isDraft eq true}">
					<a href="audit/edit.do?auditId=${row.id}"> <spring:message
							code="audit.edit"></spring:message></a>
				</jstl:if>
			</display:column>
			
			<display:column>
				<jstl:if test="${row.isDraft eq true}">
					<a href="audit/notDraft.do?auditId=${row.id}"> <spring:message
							code="audit.changeDraft"></spring:message></a>
				</jstl:if>
			</display:column>
	</security:authorize>
</display:table>