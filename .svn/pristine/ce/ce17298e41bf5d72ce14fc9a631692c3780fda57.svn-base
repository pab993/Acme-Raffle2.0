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

	<display:table name="comments" pagesize="5" class="displaytag"
		requestURI="${requestURI}" id="row">

		<spring:message code="comment.text" var="headerTag" />
		<display:column property="text" title="${headerTag}" />

		<spring:message code="comment.delete" var="headerTag" />
		<display:column>
			<a href="admin/comment/delete.do?commentId=${row.id}"> <spring:message
					code="comment.delete"></spring:message></a>
		</display:column>

	</display:table>
</security:authorize>