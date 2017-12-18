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

<fieldset class="panel panel-default">
	<div class="panel-body">
		<fieldset>
			<b><spring:message code="lastCompute" /></b> :
			<fmt:formatDate pattern="MM/yyyy" value="${billForm.payMomentCS}" />
		</fieldset>
	</div>
</fieldset>

<fieldset class="panel panel-default">
	<div class="panel-body">
		<fieldset>
			<form:form action="bill/compute.do" modelAttribute="billForm" onsubmit="return validatePayMoment()">

				<form:hidden path="id" />
				<form:hidden path="version" />

				<form:hidden id="payMomentCSId" path="payMomentCS" />
				
				<%-- <form:input id="computeDateId" path="computeDate" />
				<form:errors cssClass="error" path="computeDate" /> --%>

				<%-- <acme:textbox code="compute.input" path="computeDate" /> --%>
				<form:label path="computeDate">
					<spring:message code="compute.input" />
				</form:label>
				<form:input id="computeDateId" path="computeDate" />
				<form:errors cssClass="error" path="computeDate" />
				<br />
				<acme:submit name="save" code="bill.compute" />
				<acme:cancel url="bill/listAll.do" code="bill.cancell" />

			</form:form>
		</fieldset>
	</div>
</fieldset>

<script type="text/javascript">

function validatePayMoment() {
	<spring:message code="bill.payMoment.info" var="payMomentInfo"/>
    /* var computeDateString = document.getElementById("computeDateId").value; */
    var payMomentCSString = document.getElementById("payMomentCSId").value;
    var computeDateString = document.getElementById("computeDateId").value;
    
    /* var computeDate = new Date(computeDateString.value).getTime();
    var payMomentCS = new Date(payMomentCSString.value).getTime(); */ 
    var patt = new RegExp("^([1-9]|1[012])\/[0-9]{4}$");
    
    if(payMomentCSString == null || payMomentCSString == "" || computeDateString == null || computeDateString == ""){
    	
    }else{
    	if(patt.test(computeDateString)){
	    	var resPast = payMomentCSString.split("/");
	    	var resPastMes = parseInt(resPast[0]);
	    	var resPastYear = parseInt(resPast[1]);
	    	var resNow = computeDateString.split("/");
	    	var resNowMes = parseInt(resNow[0]);
	    	var resNowYear = parseInt(resNow[1]);
	    	if(resPastYear > resNowYear){
	    		return confirm('<jstl:out value="${payMomentInfo}"/>');
	    	}else if(resPastYear == resNowYear && resPastMes >= resNowMes){
	    		return confirm('<jstl:out value="${payMomentInfo}"/>');
	    	}
   		 }else{
   			 
   		 }
	}
}


</script> 