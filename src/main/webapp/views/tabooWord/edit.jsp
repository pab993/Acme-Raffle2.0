<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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


<form:form action="tabooWord/edit.do" modelAttribute="tabooWord">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="tabooWord.keywords" path="name" mandatory="true" />

	<br>

	<acme:submit name="save" code="tabooWord.save" />
	<jstl:if test="${tabooWord.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="tabooWord.delete" />"
			onclick="return confirm('<spring:message code="tabooWord.confirm" />')" />
	</jstl:if>
	<acme:cancel code="tabooWord.cancel" url="tabooWord/list.do" />

</form:form>
