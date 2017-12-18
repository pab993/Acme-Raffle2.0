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

<form:form action="prize/editPrize.do" modelAttribute="prize">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="raffle" />
	<form:hidden path="codes"/>
	<form:hidden path="properties"/>
	<form:hidden path="codesGenerated"/>
	<form:hidden path="winnerCodes"/>

	<acme:textbox code="prize.name" path="name"/>
	<br />

	<acme:textbox code="prize.description" path="description"/>
	<br />

	<acme:submit name="save" code="prize.save"/>
	<jstl:if test = "${prize.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="prize.delete" />" 
		onclick="return confirm('<spring:message code = "prize.confirm.delete"/>')"/>
	</jstl:if>
	
	<acme:cancel url="prize/list.do?raffleId=${prize.raffle.id}" code="prize.cancel"/>
	
	<br />

</form:form>