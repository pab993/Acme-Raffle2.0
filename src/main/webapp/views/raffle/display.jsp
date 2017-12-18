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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<img height="65" width="65" src="${raffle.getLogo()}" />
<br>

<spring:message code="raffle.title" />
:
<jstl:out value="${raffle.title}"></jstl:out>
<br>
<spring:message code="raffle.description" />
:
<jstl:out value="${raffle.description}"></jstl:out>
<br>
<spring:message code="raffle.publicationTime" />
:
<fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value="${raffle.publicationTime}"/>
<br>
<spring:message code="raffle.deadline" />
:
<fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value="${raffle.deadline}"/>
<br>
<br>

<security:authorize access="isAuthenticated()">
	<h3>
		<a href="comment/postComment.do?comentableId=${raffle.id }"> <spring:message
				code="postComment" />
		</a>
	</h3>
</security:authorize>

<br>

<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="comentable.ratioStars" /></b> <br /> RATIO:
		${ratioStars[0]}<br />
	</div>
</fieldset>
<br />

<display:table name="comments" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<spring:message code="comment.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" />

	<spring:message code="comment.stars" var="scoreHeader" />
	<display:column property="score" title="${scoreHeader}" />

	<security:authorize access="isAuthenticated()">
		<display:column>
			<a href="actor/seeProfile.do?actorId=${row.actor.id}"> <spring:message
					code="display.actor" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
	<spring:message code="comment.delete" var="headerTag" />
	<display:column>
		<a href="admin/comment/delete.do?commentId=${row.id}"> <spring:message
				code="comment.delete"></spring:message></a>
	</display:column>
	</security:authorize>

</display:table>