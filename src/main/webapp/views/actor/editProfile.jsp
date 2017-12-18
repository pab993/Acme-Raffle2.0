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


<form:form action="actor/editProfile.do" modelAttribute="actorForm" onsubmit="return validateForm()">
	
	<fieldset > 
	
		<legend><b> <spring:message code="actor.personalData" /></b> </legend>
	
	
		<acme:textbox code="actor.name" path="name" mandatory="true"/>
		<br />
			
		<acme:textbox code="actor.surname" path="surname" mandatory="true"/>
		<br />
			
<%-- 		<acme:textbox code="actor.phone" path="phone"/>
		<br /> --%>
		
		<form:label path="phone">
			<spring:message code="actor.phone" />
		</form:label>
		<form:input id="phoneId" path="phone" />
		<form:errors cssClass="error" path="phone" />
		<br />
		<br />
			
		<acme:textbox code="actor.email" path="email" mandatory="true"/>
		<br />
		
		
		<acme:textbox code="actor.postalAddress" path="postalAddress"/>
		<br/>
		
		<acme:textbox code="actor.city" path="city"/>
		<br/>
		
		<acme:textbox code="actor.country" path="country"/>
		<br/>
	
	</fieldset>
	

	<acme:submit id="submitButton" name="save" code="actor.submit"/>
	<acme:cancel code="actor.cancel" url="actor/seeProfile.do" />

</form:form>

<script type="text/javascript">

function validateForm() {
	<spring:message code="phone.ask" var="ask"/>
    var x = document.getElementById("phoneId").value;
    var patt = new RegExp("^[+][a-zA-Z]{2}([(][0-9]{1,3}[)])?[0-9]{4,25}$");
    if(x != "" && !patt.test(x)){
        return confirm('<jstl:out value="${ask}"/>');
    } 

}

</script>