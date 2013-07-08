<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<h2><spring:message code="common.welcome"/> <security:authentication property="principal.name"/> !</h2>
<p>Your Details are</p>
<ul>
<li>Password:<security:authentication property="principal.password"/></li>
<li>Email:<security:authentication property="principal.email"/></li>
<li>Name<security:authentication property="principal.name"/></li>
<li>Surname<security:authentication property="principal.surName"/></li>
<li>Address<security:authentication property="principal.address"/></li>
</ul>

   