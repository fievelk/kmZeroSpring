<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<div class="span9">
	<h2><spring:message code="common.welcome"/> <security:authentication property="principal.name"/> !</h2>
	<p>Your Details are:</p>
	<ul>
	<li>Email: <security:authentication property="principal.email"/></li>
	<li>Name: <security:authentication property="principal.name"/></li>
	<li>Surname: <security:authentication property="principal.surname"/></li>
	<li>Address: <security:authentication property="principal.address"/></li>
	<security:authorize access="hasRole('seller')">
	<li>Piva: <security:authentication property="principal.p_Iva"/></li>
	</security:authorize>
	</ul>
</div>

   
