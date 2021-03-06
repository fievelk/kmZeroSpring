<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script>
/*--------SETUP READONLY FIELDS IF DELETING - START--------*/

$(document).ready(function() {
	var del = "${requestScope.delete}"; 
	if (del == "true" ) {
		$(":input[type='text'],select[id='measureId']").each(function () { $(this).attr('readonly','readonly'); });				
	}		
});

/*--------SETUP READONLY FIELDS IF DELETING - END--------*/
 
</script>
      
<!-- Main content -->

<div class="span9">
	<h5 class="title">
		<c:choose>
      		<c:when test="${requestScope.delete eq 'true'}">
				<spring:message code="measure.delete"/>
      		</c:when>
      		<c:when test="${requestScope.create eq 'true'}">
				<spring:message code="measure.create"/>
      		</c:when>
      		<c:when test="${requestScope.update eq 'true'}">
      			<spring:message code="measure.update"/>
   			</c:when>
      	</c:choose>	
   	</h5>
	<div class="form form-small">

	
	
	  <form:form modelAttribute="measure" action="${pageContext.request.contextPath}${requestScope.action}">
	  
       <div class="span4">
		<form:hidden path="id"/>
		<div>
		    <label for="name"><spring:message code="measure.name"/></label>
		    <div>
		    	<form:input id="name" path="name"/>
		    </div>
		</div>
		<div>
		    <label for="shortName"><spring:message code="measure.shortName"/></label>
		    <div>
		    	<form:input id="shortName" path="shortName"/>
		    </div>
		</div>
		
		
		<div class="control-group">
		    <div class="controls">
		      <button type="submit" class="btn">
		      	<c:choose>
		      		<c:when test="${requestScope.delete eq 'true'}">
						<spring:message code="common.delete"/>
		      		</c:when>
		      		<c:otherwise>
		      			<spring:message code="common.submit"/>
		      		</c:otherwise>
      			</c:choose>	
      		  </button>
			</div>
		</div>
		</div>

		
	  </form:form>
	</div>
	
</div>