<%--
 * list.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.findUsersOrderByValidCodes"/></b>
		<br/>
		<jstl:forEach items="${findUsersOrderByValidCodes}" var="item">
			<h4><jstl:out value="${item.userAccount.username}"/></h4>
		</jstl:forEach>
	</div>
</fieldset>
<br/>

<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.findManagersOrderByUnpaidBills"/></b>
		<br/>
		<jstl:forEach items="${findManagersOrderByUnpaidBills}" var="item">
			<h4><jstl:out value="${item.userAccount.username}"/></h4>
		</jstl:forEach>
	</div>
</fieldset>
<br/>

<jstl:if test="${!(ratioBanUnBanUsers[0] eq null)}">
<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.ratioBanUnBanUsers" /></b>
		<br /> 
		<h4><jstl:out value=" RATIO: "/><jstl:out value="${ratioBanUnBanUsers[0]}"/> <br /></h4>
	</div>
</fieldset>
<br />
</jstl:if>

<jstl:if test="${!(ratioDebtorManagers[0] eq null)}">
<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.ratioDebtorManagers" /></b>
		<br /> 
		<h4><jstl:out value=" RATIO: "/><jstl:out value="${ratioDebtorManagers[1]}"/> <br /></h4>
	</div>
</fieldset>
<br />
</jstl:if>

<jstl:if test="${!(minMaxAvgStddevOfPrizesPerRaffle[0] eq null)}">
<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.minMaxAvgStddevOfPrizesPerRaffle" /></b>
		<br /> 
		<h4><jstl:out value=" MIN: "/><jstl:out value="${minMaxAvgStddevOfPrizesPerRaffle[0]}"/> <br /></h4>
		<h4><jstl:out value=" MAX: "/><jstl:out value="${minMaxAvgStddevOfPrizesPerRaffle[1]}"/> <br /> </h4>
		<h4><jstl:out value=" AVG: "/><jstl:out value="${minMaxAvgStddevOfPrizesPerRaffle[2]}"/> <br /></h4>
		<h4><jstl:out value=" STDDEV: "/><jstl:out value="${minMaxAvgStddevOfPrizesPerRaffle[3]}"/> <br /></h4>
	</div>
</fieldset>
</jstl:if>
<br />

<jstl:if test="${!(minMaxAvgStddevOfCodesPerPrize[0] eq null)}">
<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.minMaxAvgStddevOfCodesPerPrize" /></b>
		<br /> 
		<h4><jstl:out value=" MIN: "/><jstl:out value="${minMaxAvgStddevOfCodesPerPrize[0]}"/> <br /> </h4>
		<h4><jstl:out value=" MAX: "/><jstl:out value="${minMaxAvgStddevOfCodesPerPrize[1]}"/> <br /> </h4>
		<h4><jstl:out value=" AVG: "/><jstl:out value="${minMaxAvgStddevOfCodesPerPrize[2]}"/> <br /></h4>
		<h4><jstl:out value=" STDDEV: "/><jstl:out value="${minMaxAvgStddevOfCodesPerPrize[3]}"/> <br /></h4>
	</div>
</fieldset>
</jstl:if>
<br />

<jstl:if test="${!(minMaxAvgStddevOfValidCodesPerUser[0] eq null)}">
<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.minMaxAvgStddevOfValidCodesPerUser" /></b>
		<br /> 
		<h4><jstl:out value=" MIN: "/><jstl:out value="${minMaxAvgStddevOfValidCodesPerUser[0]}"/> <br /></h4>
		<h4><jstl:out value=" MAX: "/><jstl:out value="${minMaxAvgStddevOfValidCodesPerUser[1]}"/> <br /> </h4>
		<h4><jstl:out value=" AVG: "/><jstl:out value="${minMaxAvgStddevOfValidCodesPerUser[2]}"/> <br /></h4>
		<h4><jstl:out value=" STDDEV: "/><jstl:out value="${minMaxAvgStddevOfValidCodesPerUser[3]}"/> <br /></h4>
	</div>
</fieldset>
</jstl:if>
<br />

<jstl:if test="${!(minMaxAvgOfBillsPerManager[0] eq null)}">
<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.minMaxAvgOfBillsPerManager" /></b>
		<br /> 
		<h4><jstl:out value=" MIN: "/><jstl:out value="${minMaxAvgOfBillsPerManager[0]}"/> <br /></h4>
		<h4><jstl:out value=" MAX: "/><jstl:out value="${minMaxAvgOfBillsPerManager[1]}"/> <br /> </h4>
		<h4><jstl:out value=" AVG: "/><jstl:out value="${minMaxAvgOfBillsPerManager[2]}"/> <br /></h4>
	</div>
</fieldset>
</jstl:if>
<br />

<jstl:if test="${!(minMaxAvgOfUnpaidBillsPerManager[0] eq null)}">
<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.minMaxAvgOfUnpaidBillsPerManager" /></b>
		<br /> 
		<h4><jstl:out value=" MIN: "/><jstl:out value="${minMaxAvgOfUnpaidBillsPerManager[0]}"/> <br /></h4>
		<h4><jstl:out value=" MAX: "/><jstl:out value="${minMaxAvgOfUnpaidBillsPerManager[1]}"/> <br /> </h4>
		<h4><jstl:out value=" AVG: "/><jstl:out value="${minMaxAvgOfUnpaidBillsPerManager[2]}"/> <br /></h4>
	</div>
</fieldset>
</jstl:if>
<br />

<jstl:if test="${!(minMaxAvgOfPaidBillsPerManager[0] eq null)}">
<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.minMaxAvgOfPaidBillsPerManager" /></b>
		<br /> 
		<h4><jstl:out value=" MIN: "/><jstl:out value="${minMaxAvgOfPaidBillsPerManager[0]}"/> <br /></h4>
		<h4><jstl:out value=" MAX: "/><jstl:out value="${minMaxAvgOfPaidBillsPerManager[1]}"/> <br /> </h4>
		<h4><jstl:out value=" AVG: "/><jstl:out value="${minMaxAvgOfPaidBillsPerManager[2]}"/> <br /></h4>
	</div>
</fieldset>
</jstl:if>
<br />

<jstl:if test="${!(avgStddevOfCommentsPerComentable[0] eq null)}">
<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.avgStddevOfCommentsPerComentable" /></b>
		<br /> 
		<h4><jstl:out value=" AVG: "/><jstl:out value="${avgStddevOfCommentsPerComentable[0]}"/> <br /></h4>
		<h4><jstl:out value=" STDDEV: "/><jstl:out value="${avgStddevOfCommentsPerComentable[1]}"/> <br /></h4>
	</div>
</fieldset>
</jstl:if>
<br />

<jstl:if test="${!(avgStddevOfStarsPerComment[0] eq null)}">
<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.avgStddevOfStarsPerComment" /></b>
		<br /> 
		<h4><jstl:out value=" AVG: "/><jstl:out value="${avgStddevOfStarsPerComment[0]}"/> <br /></h4>
		<h4><jstl:out value=" STDDEV: "/><jstl:out value="${avgStddevOfStarsPerComment[1]}"/> <br /></h4>
	</div>
</fieldset>
<br />

<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.avgStarsPerActorGroupByCountry" /></b>
		<br /> 
		<%-- AVG: ${avgStarsPerActorGroupByCountry[0]}<br /> --%>
		<jstl:forEach items="${avgStarsPerActorGroupByCountry}" var="item">
			<h4><jstl:out value="${item[0]}"/><jstl:out value=" : "/><jstl:out value="${item[1]}"/></h4>
		</jstl:forEach>
	</div>
</fieldset>
</jstl:if>
<br />

<fieldset class="panel panel-default">
	<div class="panel-body">
		<b><spring:message code="dashboard.avgStarsPerActorGroupByCity" /></b>
		<br /> 
		<%-- AVG: ${avgStarsPerActorGroupByCity[0]}<br /> --%>
		<jstl:forEach items="${avgStarsPerActorGroupByCity}" var="item">
			<h4><jstl:out value="${item[0]}"/><jstl:out value=" : "/><jstl:out value="${item[1]}"/></h4>
		</jstl:forEach>
	</div>
</fieldset>
