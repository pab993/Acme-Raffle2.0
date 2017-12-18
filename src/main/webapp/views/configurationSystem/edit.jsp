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

<br>
<fieldset class="panel panel-default">
	<div class="panel-body">
		<fieldset>
			<b><spring:message code="payMoment" /></b> :
			<fmt:formatDate pattern="MM/yyyy"
				value="${configurationSystem.payMoment}" />
		</fieldset>
	</div>
</fieldset>

<fieldset class="panel panel-default">
	<div class="panel-body">
		<form:form action="configurationSystem/edit.do" modelAttribute="configurationSystem">

			<form:hidden path="id" />
			<form:hidden path="version" />

			<form:hidden path="payMoment" />

			<fieldset>
				<legend>
					<b> <spring:message code="configurationSystem.configure" /></b>
				</legend>

				<acme:textbox code="url" path="banner" />

				<acme:textbox code="taxes" path="taxes" />

			</fieldset>

			<br />

			<acme:submit name="save" code="submit" />
			<acme:cancel url="welcome/index.do" code="cancel" />

		</form:form>

	</div>
</fieldset>