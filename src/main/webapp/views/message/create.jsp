<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	<form:form action="${requestURI}" modelAttribute="sendMessage">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="send" />
		<form:hidden path="moment" />
		<form:hidden path="folder" />
	
		<div>
			<form:label path="priority">
				<spring:message code="message.priority" />
			</form:label>	
			<form:select class="form-control" id="priority" path="priority">
				<form:option value="LOW" label="LOW" />
				<form:option value="NEUTRAL" label="NEUTRAL" />
				<form:option value="HIGH" label="HIGH" />
			</form:select>
			<form:errors path="priority" cssClass="error" />
		</div><br/><br/>
	
		<acme:textbox code="message.subject" path="subject" />
		<br />
		
		<acme:textarea code="message.body" path="body" />
		<br />
		
		<acme:select code="message.recipient" path="receives" items="${recipients}" itemLabel="name" defaul="false"/>
		<br />
		
		<acme:submit code="message.send" name="send" />&nbsp;
		<br />
	
	</form:form>