<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script src="${pageContext.request.contextPath}/resources/custom/js/kmzGMaps.js"></script>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var del = "${requestScope.delete}"; 
		if (del == "true" ) {
			$(":input[type='text']").each(function () { $(this).attr('readonly',true); });				
		}		
	});
	
	$(function() {
		$( "#datepicker" ).datepicker({
			defaultDate: "1/1/1960",
			changeMonth: true,
			changeYear: true,
			dateFormat: 'dd/mm/yy',
			yearRange: '1920:2012',
			showAnim: "slideDown"
			/* minDate: (new Date(1920, 1, 1)),
			maxDate: (new Date(2010, 1, 1)) */
		});
	});
</script>
<div class="span9">
	<div class="row">      		
		<!-- Main content -->
		<div class="span9">
			<h5 class="title">
				<c:choose>
		      		<c:when test="${requestScope.delete}">
						<spring:message code="user.delete"/>
		      		</c:when>
		      		<c:when test="${requestScope.create}">
						<spring:message code="user.create"/>
		      		</c:when>
		      		<c:when test="${requestScope.update}">
		      			<spring:message code="user.edit"/>
		   			</c:when>
		      	</c:choose>	
			</h5>
		</div>
		<div class="span5">
			<div class="form">
				<form:form modelAttribute="user" cssClass="form-horizontal" action="${pageContext.request.contextPath}${requestScope.action}" method="POST">
				<form:hidden path="id"/>
				<form:errors path="id"/>
				<div id="identity"></div>
				<div class="control-group">
				    <label class="control-label" for="name"><spring:message code="user.name"/></label>
				    <div class="controls">
				    	<form:input id="name" path="name"/><br />
				    	<form:errors path="name"/>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="surname"><spring:message code="user.surname"/></label>
				    <div class="controls">
				    	<form:input id="surname" path="surname"/><br />
				    	<form:errors path="surname"/>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="email"><spring:message code="user.email"/></label>
				    <div class="controls">
				    	<form:input id="email" path="email"/><br />
				    	<form:errors path="email"/>
				    </div>
				</div>
				
				<c:choose>
					<c:when test="${requestScope.create}">
						<div class="control-group">
						    <label class="control-label" for="password"><spring:message code="user.password"/></label>
						    <div class="controls">
						    	<form:password id="password" path="password.password"/><br />
						    	<form:errors path="password.password"/>
						    </div>
						</div>
						<div class="control-group">
						    <label class="control-label" for="confirmPassword"><spring:message code="user.confirmPassword"/></label>
						    <div class="controls">
						    	<form:password id="confirmPassword" path="password.confirmPassword"/><br />
						    	<form:errors path="password.confirmPassword"/>
						    </div>
						</div>
					</c:when>
				</c:choose>
				
				<div class="control-group">
				    <label class="control-label" for="dateOfBirth"><spring:message code="user.dateOfBirth"/></label>
					<div class="controls">
						<form:input id="datepicker" path="dateOfBirth"/><br />
						<form:errors path="dateOfBirth"/>
					</div>
				</div>
				
				<div class="control-group">
				    <label class="control-label" for="address"><spring:message code="user.address"/></label>
				    <div class="controls">
						<form:input id="address_autocompleted" path="address"/><br />
						<form:errors path="address"/>
						<p id="addressDistanceError"></p>
				    </div>
				</div>
				
				<div class="control-group">
				    <div class="controls">
				      <button type="submit" class="btn">
				      	<c:choose>
				      		<c:when test="${!requestScope.delete}">
								<spring:message code="common.submit"/>
				      		</c:when>
				      		<c:otherwise>
				      			<spring:message code="common.delete"/>
				      		</c:otherwise>
				      	</c:choose>	
				      	
				      </button>
				    </div>
				</div>
				</form:form>
			</div>
		</div>
	   	<div class="span4">
			<div class="mapbox profileMap" id="googleMap" style="height:380px;"></div>
		</div>
	</div>
</div>
						







