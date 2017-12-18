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

<form:form action="audit/edit.do" modelAttribute="audit">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="raffle"/>
	<form:hidden path="auditor"/>
	<form:hidden path="isDraft"/>
	<form:hidden path="moment"/>
	
	<acme:textbox code="audit.text" path="text"/>
	<br />
	
	
	<acme:submit name="save" code="audit.save"/>
	<jstl:if test = "${audit.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="audit.delete" />" 
		onclick="return confirm('<spring:message code = "audit.confirm.delete"/>')"/>
	</jstl:if>
	
	<acme:cancel url="audit/myList.do" code="audit.cancel"/>
	
	<br />

</form:form>