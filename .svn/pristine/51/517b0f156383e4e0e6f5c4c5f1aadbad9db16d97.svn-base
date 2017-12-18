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

<form:form action="bill/edit.do" modelAttribute="bill">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="name"/>
	<form:hidden path="price"/>
	<form:hidden path="paid"/>
	<form:hidden path="raffle"/>
	
	<acme:textbox code="bill.payMoment" path="text"/>
	<br />
	
	
	<acme:submit name="save" code="bill.save"/>
	
	<acme:cancel url="bill/listAll.do" code="bill.cancel"/>
	
	<br />

</form:form>