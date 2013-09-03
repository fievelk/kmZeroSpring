<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>


			<!-- Main content -->
			
			<div class="span9">
				<h5 class="title"><spring:message code="password.edit"/></h5>
				<div class="form form-small">
					<form:form modelAttribute="user" cssClass="form-horizontal" action="${pageContext.request.contextPath}${requestScope.action}" method="POST">
					<form:hidden path="id"/>
					<security:authorize access="hasAnyRole('user','seller')">
					<div class="control-group">
					    <label class="control-label" for="oldPassword"><spring:message code="password.oldPass"/></label>
					    <div class="controls">
					    	<form:password id="oldPassword" path="password.oldPassword"/>
					    	<form:errors path="password.oldPassword"/>
					    </div>
					</div>
					</security:authorize>
					<div class="control-group">
					    <label class="control-label" for="password"><spring:message code="password.newPass"/></label>
					    <div class="controls">
					    	<form:password id="password" path="password.password"/>
					    	<form:errors path="password.password"/>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label" for="confirmPassword"><spring:message code="password.confirmPass"/></label>
					    <div class="controls">
					    	<form:password id="confirmPassword" path="password.confirmPassword"/>
					    	<form:errors path="password.confirmPassword"/>
					    </div>
					</div>
					
					<div class="control-group">
					    <div class="controls">
					      <button type="submit" class="btn">
					      	<spring:message code="common.submit"/>
					      </button>
					    </div>
					</div>
					</form:form>
				</div>
			</div>
