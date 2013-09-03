<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<script src="${pageContext.request.contextPath}/resources/custom/js/kmzGMaps.js"></script>
<script src="${pageContext.request.contextPath}/resources/custom/js/images.js"></script>
<script>
$(document).ready(function() {
	var del = "${requestScope.delete}"; 
	if (del == "true" ) {
		$(":input[type='text']").each(function () { $(this).attr('readonly',true); });	
		$("form input[type='checkbox']").attr('disabled',true);	
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
<security:authorize var="isAdmin" access="hasRole('admin')" />
<security:authorize var="isUser" access="hasRole('user')" />

<div class="span9">
<div class="row">
	<!-- Main content -->
	<div class="span9">
		<h5 class="title">
			<c:choose>
	      		<c:when test="${requestScope.upgrade}">
					<spring:message code="seller.upgrade"/>
	      		</c:when>
	      		<c:when test="${requestScope.update}">
	      			<spring:message code="seller.edit"/>
	   			</c:when>
	   			<c:when test="${requestScope.delete}">
					<spring:message code="seller.delete"/>
	      		</c:when>
	      	</c:choose>	
		</h5>
	</div>
	<div class="span5">
		<div class="form">
			<form:form modelAttribute="seller" cssClass="form-horizontal" action="${pageContext.request.contextPath}${requestScope.action}" method="POST">
				<form:hidden path="id"/>
				<div class="control-group">
				    <label class="control-label" for="name"><spring:message code="user.name"/></label>
				    <div class="controls">
				    	<form:input id="name" path="name"/>
				    	<form:errors path="name"/>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="surname"><spring:message code="user.surname"/></label>
				    <div class="controls">
				    	<form:input id="surname" path="surname"/>
				    	<form:errors path="surname"/>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="email"><spring:message code="user.email"/></label>
				    <div class="controls">
				    	<form:input id="email" path="email"/>
				    	<form:errors path="email"/>
				    </div>
				</div>
			     <c:if test="${requestScope.create}">
				<div class="control-group">
				    <label class="control-label" for="password"><spring:message code="user.password"/></label>
				    <div class="controls">
				    	<form:password id="password" path="password.password"/><br />
				    	<form:errors path="password.password"/>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="confirm_password"><spring:message code="user.confirm_password"/></label>
				    <div class="controls">
				    	<form:password id="confirm_password" path="password.confirm_password"/><br />
				    	<form:errors path="password.confirm_password"/>
				    </div>
				</div>
				</c:if>
				<div class="control-group">
				    <label class="control-label" for="date_of_birth"><spring:message code="user.date_of_birth"/></label>
					<div class="controls">
						<form:input id="datepicker" path="date_of_birth"/>
						<form:errors path="date_of_birth"/>
					</div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="url"><spring:message code="seller.url"/></label>
				    <div class="controls">
						<form:input id="url" path="url"/>
						<form:errors path="url"/>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="phone"><spring:message code="seller.phone"/></label>
				    <div class="controls">
						<form:input id="phone" path="phone"/>
						<form:errors path="phone"/>
				    </div>
				</div>
				
				
				<c:if test="${isAdmin || (isUser && requestScope.upgrade) || requestScope.create}">
						
						<div class="control-group">
						    <label class="control-label" for="address"><spring:message code="user.address"/></label>
						    <div class="controls">
								<form:input id="address_autocompleted" path="address"/><br />
								<form:errors path="address"/>
								<p id="addressDistanceError"></p>
						    </div>
						</div>
						<div class="control-group">
						    <label class="control-label" for="p_iva"><spring:message code="seller.p_iva"/></label>
						    <div class="controls">
								<form:input id="p_iva" path="p_iva"/>
								<form:errors path="p_iva"/>
						    </div>
						</div>
						<div class="control-group">
						    <label class="control-label" for="cod_fisc"><spring:message code="seller.cod_fisc"/></label>
						    <div class="controls">
								<form:input id="cod_fisc" path="cod_fisc"/>
								<form:errors path="cod_fisc"/>
						    </div>
						</div>
						<div class="control-group">
						    <label class="control-label" for="company"><spring:message code="seller.company"/></label>
						    <div class="controls">
								<form:input id="company" path="company"/>
								<form:errors path="company"/>
						    </div>
						</div>
				</c:if>
				<security:authorize access="hasRole('admin')">
				<div class="control-group">
				    <label class="control-label" for="enable"><spring:message code="seller.enable"/></label>
					<div class="controls">
						<form:checkbox id="enable" path="enable"/>
					</div>
				</div>
				</security:authorize>
				<div class="control-group">
				    <div class="controls">
				      <button type="submit" id="submitIfValidAddress" class="btn">
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
		<div class="mapbox profileMap" id="googleMap" style="height:200px;"></div>	
		<c:if test="${requestScope.update eq 'true'}">
			<div class="sellerImages">
				<div class="row-fluid">
					<a class="btn" href="#modalWindow" role="button" data-toggle="modal" onclick="createModalWindow('addImages','seller','${seller.id}',null)">Add Images...</a>
				</div>
				<div id="km0Images">
			  	<c:forEach var="image" items="${seller.images}">
				  	<div id="km0Image">
				  		<div>
				  			<a href="#modalWindow" class="icon-edit" role="button" data-toggle="modal" onclick="createModalWindow('updateImage','seller','${seller.id}','${image.id}')" ></a>	
					       	<a href="#modalWindow" class="icon-remove"  role="button" data-toggle="modal" onclick="createModalWindow('deleteImage','seller','${seller.id}','${image.id}')"></a>
				  		</div>
			  			<span id="image_${image.id}">
				       		<img src="${pageContext.request.contextPath}/seller/image/${image.id}/${image.name}" alt="${image.name}" />
			       		</span>	
				    </div>
		    	</c:forEach>
		    	</div>
	      	</div>	
  		</c:if>
		</div>			
	</div>
</div>
			

