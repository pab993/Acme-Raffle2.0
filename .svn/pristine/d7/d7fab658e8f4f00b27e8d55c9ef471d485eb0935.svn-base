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

<form:form action="${requestURI}" modelAttribute="socialIdentity">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="actor"/>

	<acme:textbox code="socialIdentity.nick" path="nick"/>
	<br />
	
	<acme:textbox code="socialIdentity.link" path="profile"/>
	<br />
	
	<acme:submit name="save" code="socialIdentity.save"/>

	<acme:cancel url="socialIdentity/list.do" code="socialIdentity.cancel"/>
	
	<br />

</form:form>