<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="actor/registerAuditor.do" modelAttribute="actorRegisterForm" onsubmit="return validateForm()">
	
	<fieldset > 
	
	<legend><b> <spring:message code="actorRegister.accountData" /> </b></legend>
	
		<acme:textbox code="actorRegister.username" path="username" mandatory="true"/>
		<br/>
	
		<acme:password code="actorRegister.password" path="password" mandatory="true"/>
		<br/>
	
		<acme:password code="actorRegister.repeatPassword" path="repeatPassword" mandatory="true"/>
	
	</fieldset>
	
	
	<fieldset > 
	
		<legend><b> <spring:message code="actorRegister.personalData" /></b> </legend>
	
	
		<acme:textbox code="actorRegister.name" path="name" mandatory="true"/>
		<br />
			
		<acme:textbox code="actorRegister.surname" path="surname" mandatory="true"/>
		<br />
		
<%-- 		<acme:textbox id="phoneId" code="actorRegister.phone" path="phone"/>
		<br /> --%>
		
		<form:label path="phone">
			<spring:message code="actorRegister.phone" />
		</form:label>
		<form:input id="phoneId" path="phone" />
		<form:errors cssClass="error" path="phone" />
		<br />
		<br />
		
		<acme:textbox code="actorRegister.email" path="email" mandatory="true"/>
		<br />
		
		<acme:textbox code="actorRegister.postalAddress" path="postalAddress"/>
		<br/>
		
		<acme:textbox code="actorRegister.city" path="city"/>
		<br/>
		
		<acme:textbox code="actorRegister.country" path="country"/>
		<br/>
	
	</fieldset>
	
	
	<div>
	<form:checkbox id="myCheck" onclick="comprobar();" path="check"/>
		<form:label path="check">
			<spring:message code="actorRegister.accept" />
			<a href="termAndCondition/termAndCondition.do"><spring:message code="actorRegister.lopd"/></a>
		</form:label>
	</div>
	

	<acme:submit id="submitButton" name="save" code="actorRegister.submit" />
	<%-- <input type="submit" name="save" value="<spring:message code="actorRegister.submit" />" onclick="validateForm()"/> --%>

</form:form>

<script type="text/javascript">

document.getElementById("submitButton").disabled = true;
document.getElementById("myCheck").checked = false;

function comprobar() {
	
	var x = document.getElementById("myCheck").checked;
	
	if(x == true){
		document.getElementById("submitButton").disabled = false;
	}
	else{
		document.getElementById("submitButton").disabled = true;
	}
}


function validateForm() {
	<spring:message code="phone.ask" var="ask"/>
    var x = document.getElementById("phoneId").value;
    var patt = new RegExp("^[+][a-zA-Z]{2}([(][0-9]{1,3}[)])?[0-9]{4,25}$");
    if(x != "" && !patt.test(x)){
        return confirm('<jstl:out value="${ask}"/>');
    } 

}


</script>