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

<form:form action="code/edit.do" modelAttribute="code">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="prize" />
	<form:hidden path="moment"/>
	<form:hidden path="user"/>

	<acme:textbox code="code.name" path="name"/>
	<br />

	<form:label path="winner" ><spring:message code="code.question.win"/></form:label>
			<form:select path="winner">
				<form:option value="false"><spring:message code="code.no.win"/></form:option>
				<form:option value="true"><spring:message code="code.yes.win"/></form:option>
	</form:select>
	
	<acme:submit name="save" code="code.save"/>
	<jstl:if test = "${code.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="code.delete" />" 
		onclick="return confirm('<spring:message code = "code.confirm.delete"/>')"/>
	</jstl:if>
	
	<acme:cancel url="code/listByPrize.do?prizeId=${code.prize.id}" code="code.cancel"/>
	
	<br />

</form:form>