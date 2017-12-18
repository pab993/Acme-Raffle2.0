<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing table -->

<display:table name="tabooWords" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<spring:message code="tabooWord.keywords" var="nameHeader" />
	<display:column property="name" title="${nameHeader }" />


	<display:column>
		<a href="tabooWord/edit.do?tabooWordId=${row.id}"> <spring:message
				code="tabooWord.edit" />
		</a>
	</display:column>


</display:table>


<a href="tabooWord/create.do"> <spring:message
		code="tabooWord.create" />
</a>

<br/>
<br/>

<input type="button" name="cancel"
	value="<spring:message code="tabooWord.cancel" />"
	onclick="javascript: window.location.replace('<spring:url value='/' />')" />