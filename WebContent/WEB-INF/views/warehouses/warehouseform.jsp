<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- Main content -->

<div class="span9">
	<h5 class="title">
      			<spring:message code="warehouse.update"/>
   	</h5>
	<div class="form form-small">

	
	
	  <form:form modelAttribute="warehouse" action="${pageContext.request.contextPath}${requestScope.action}">
	  
       <div class="span4">
		<form:hidden path="id"/>
		<div>
		    <label for="name"><spring:message code="warehouse.name"/></label>
		    <div>
		    	<form:input id="name" path="name"/>
		    </div>
		</div>
		
		<div>
		    <label for="name"><spring:message code="warehouse.address"/></label>
		    <div>
		    	<form:input id="address" path="address"/>
		    </div>
		</div>
		
		
		<div class="control-group">
		    <div class="controls">
		      <button type="submit" class="btn">
		      			<spring:message code="common.submit"/>
      		  </button>
			</div>
		</div>
		</div>

		
	  </form:form>
	</div>
	
</div>