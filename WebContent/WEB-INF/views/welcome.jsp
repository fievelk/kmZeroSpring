<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<div class="span9">
	<h2><spring:message code="common.welcome"/> <security:authentication property="principal.name"/> !</h2>
	<p><spring:message code="common.yourDetails"/>:</p>
	<ul>
	<li><b><spring:message code="user.email"/></b>: <security:authentication property="principal.email"/></li>
	<li><b><spring:message code="user.name"/></b>: <security:authentication property="principal.name"/></li>
	<li><b><spring:message code="user.surname"/></b>: <security:authentication property="principal.surname"/></li>
	<li><b><spring:message code="user.address"/></b>: <security:authentication property="principal.address"/></li>
	<security:authorize access="hasRole('seller')">
	<li><b><spring:message code="seller.p_iva"/></b>: <security:authentication property="principal.p_Iva"/></li>
	</security:authorize>
	</ul>
</div>

   
