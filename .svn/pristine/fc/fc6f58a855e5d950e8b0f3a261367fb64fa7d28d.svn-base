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

<div>
	<jstl:if test="${errorMessage != null}">
		<br />
		<span class="message" style="color: red"><spring:message
				code="${errorMessage}" /></span>
	</jstl:if>
</div>

<security:authorize access="hasRole('ADMIN')">
	<div>
		<%-- <br />
		<button onclick="window.location.href='bill/compute.do'">
			<span><spring:message code="bill.compute" /></span>	
		</button> --%>

		<a href="bill/compute.do"> <spring:message code="bill.compute"></spring:message></a>

	</div>
</security:authorize>

<display:table name="bills" pagesize="5" class="displaytag"
	requestURI="${requestURI}" id="row">

	<spring:message code="bill.name" var="headerTag" />
	<display:column property="name" title="${headerTag}" />

	<spring:message code="bill.payMoment" var="headerTag" />
	<display:column property="payMoment" title="${headerTag}"
		format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="bill.price" var="headerTag" />
	<display:column property="price" title="${headerTag}" />

	<spring:message code="bill.paid" var="headerTag" />
	<%-- <display:column property="paid" title="${headerTag}" /> --%>
	<display:column title="${headerTag }">
		<jstl:choose>
			<jstl:when test="${row.paid == true }">
				<spring:message code="bill.paid"/>
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="bill.not.paid"/>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>

	<spring:message code="bill.raffle" var="headerTag" />
	<display:column property="raffle.title" title="${headerTag}" />

	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<jstl:if
				test="${(row.paid eq false)  && (row.payMoment != null) && (row.raffle.publicationTime lt now) }">
				<a href="bill/pay.do?billId=${row.id}"
					onclick="return confirm('<spring:message code = "bill.sure.pay"/>')">
					<spring:message code="pay.bill"></spring:message>
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="actor/seeProfile.do?actorId=${row.raffle.manager.id}">
				<spring:message code="bill.manager"></spring:message>
			</a>
		</display:column>
	</security:authorize>

</display:table>