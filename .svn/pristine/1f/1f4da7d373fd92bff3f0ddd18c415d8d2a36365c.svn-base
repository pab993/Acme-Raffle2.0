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

<form:form action="raffle/edit.do" modelAttribute="raffle">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="manager" />
	<form:hidden path="prizes"/>
	<form:hidden path="audits"/>

	<acme:textbox code="raffle.logo" path="logo"/>
	<br />
	
	<acme:textbox code="raffle.title" path="title"/>
	<br />
	
	<acme:textbox code="raffle.description" path="description"/>
	<br />
	
	<acme:textbox code="raffle.publicationTime" path="publicationTime"/>
	<br />
	
	<acme:textbox code="raffle.deadline" path="deadline"/>
	<br />
	
	<acme:submit name="save" code="raffle.save"/>
	<jstl:if test = "${raffle.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="raffle.delete" />" 
		onclick="return confirm('<spring:message code = "raffle.confirm.delete"/>')"/>
	</jstl:if>
	
	<acme:cancel url="raffle/myList.do" code="raffle.cancel"/>
	
	<br />

</form:form>