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

<form:form action="prize/regenerateCode.do" modelAttribute="prize">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="raffle" />
	<form:hidden path="codes"/>
	<form:hidden path="properties"/>
	<form:hidden path="name"/>
	<form:hidden path="description"/>
	
	<acme:textbox code="prize.codesGenerated" path="codesGenerated"/>
	<br />
	
	<acme:textbox code="prize.winnerCodes" path="winnerCodes"/>
	<br />
	
	<acme:submit name="save" code="prize.regenerateCode"/>
	
	<acme:cancel url="prize/list.do?raffleId=${prize.raffle.id}" code="prize.cancel"/>
	
	<br />

</form:form>