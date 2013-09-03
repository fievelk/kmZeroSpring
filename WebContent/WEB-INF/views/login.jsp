<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>


	<div class="span6">
	  <div class="formy well">
	    
	    <div class="form">
	    
			<security:authorize access="!isAuthenticated()">
			<h4 class="title"><spring:message code="common.loginTitle"/></h4>
		         <form name="loginform" action="${pageContext.request.contextPath}/j_spring_security_check" method="post" class="navbar-form pull-right">
		           <input class="span2" type="text" placeholder="Username" name="j_username">
		           <input class="span2" type="password" placeholder="Password" name="j_password">
		           <button type="submit" class="btn"><spring:message code="common.signin"/></button>
		         </form>
		         <hr />
	        <h5><spring:message code="common.newAccount"/></h5>
	            <a href="${pageContext.request.contextPath}/users/create_start"><spring:message code="common.signUp"/></a>
		    </security:authorize>
		    <security:authorize access="isAuthenticated()">
		    	<a href="${pageContext.request.contextPath}/j_spring_security_logout"><spring:message code="menu.logout"/></a>
		    </security:authorize>
	    </div> 
	  </div>
	</div>

