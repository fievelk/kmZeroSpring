<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<h2><spring:message code="common.welcome"/>  <security:authentication property="password"/> !</h2>
   