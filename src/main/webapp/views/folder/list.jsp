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

<%-- <div>
	<jstl:if test="${message != null}">
		<br />
		<span class="message" style="color:red"><spring:message code="${message}" /></span>
	</jstl:if>	
</div> --%>
<div>
	<a href="message/actor/create.do">
		<spring:message code="message.sendAMessage" />
	</a>
</div>
<br />
<display:table name="folders" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	<spring:message code="messageFolder.name" var="headerTag" />
	<display:column property="name" title="${headerTag}"/>
	<spring:message code="messageFolder.viewMessages" var="headerTag" />
	<display:column title="${headerTag}">
		<a href="message/actor/list.do?folderId=${row.id}">
			<spring:message code="messageFolder.viewMessages" />
		</a>
	</display:column>
	<spring:message code="messageFolder.edit" var="headerTag" />
	<display:column title="${headerTag}">
		<jstl:if test="${!row.isSystem}">
			<a href="folder/actor/edit.do?folderId=${row.id}">
				<spring:message code="messageFolder.edit" />
			</a>
		</jstl:if>
	</display:column>
	<spring:message code="messageFolder.delete" var="headerTag" />
	<display:column title="${headerTag}">
		<jstl:if test="${!row.isSystem}">
			<a href="folder/actor/delete.do?folderId=${row.id}">
				<input type="submit" name="delete"
				value="<spring:message code="messageFolder.delete" />"
				onclick="return confirm('<spring:message code="messageFolder.confirm.delete" />')" />&nbsp;
			</a>
		</jstl:if>
	</display:column>	
</display:table>

<div>
	<a href="folder/actor/create.do">
		<spring:message	code="messageFolder.create" />
	</a>
</div>