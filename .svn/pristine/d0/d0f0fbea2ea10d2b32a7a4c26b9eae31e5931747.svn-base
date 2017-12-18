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

<form:form action="code/enter.do" modelAttribute="codeForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="idPrize" />
	

	<fieldset >
		<legend><b> <spring:message code="code.enter" /></b> </legend>
		
		<acme:textbox code="code.name" path="name"/>
		<br />
			
	</fieldset>
	
	<p>
		<acme:submit name="save" code="code.submit"/>
		<acme:cancel url="prize/list.do?raffleId=${raffleId }" code="code.cancel"/>
	</p>
</form:form>