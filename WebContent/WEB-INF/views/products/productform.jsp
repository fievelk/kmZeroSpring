<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<script src="${pageContext.request.contextPath}/resources/custom/js/images.js"></script>
<script>
/*--------SETUP READONLY FIELDS IF DELETING - START--------*/

$(document).ready(function() {

	var del = "${requestScope.delete}"; 
	if (del == "true" ) {
		$(":input[type='text'],select[id='categoryId'],textarea").each(function () { $(this).attr('readonly','readonly'); });
		$('.fuelux div.spinner div.spinner-buttons').remove();
		$('.fuelux div.spinner').spinner({
				disabled: true
		});
		$(":input[type='text'],select[id='categoryId']").each(function () { $(this).attr('readonly','readonly'); });				
		$(":input[type='text'],select[id='measureId']").each(function () { $(this).attr('readonly','readonly'); });	
		
	}	
	$('.fuelux div.spinner').spinner({
				value: ${product.position}
	});	
});

/*--------SETUP READONLY FIELDS IF DELETING - END--------*/
 
/*--------DATE PICKER - START--------*/

$(function() {
	$( "#from" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		dateFormat: 'dd/mm/yy',
		//altField  : '#from',
	    //altFormat : 'mm/dd/yy',
		numberOfMonths: 3,
		onClose: function( selectedDate ) {
			$( "#to" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#to" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		dateFormat: 'dd/mm/yy',
		//altField  : '#to',
	    //altFormat : 'mm/dd/yy',
		numberOfMonths: 3,
		onClose: function( selectedDate ) {
			$( "#from" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
});

/*--------DATE PICKER END--------*/
</script>


      
<!-- Main content -->

		<div class="span9">
			<h5 class="title">
				<c:choose>
		      		<c:when test="${requestScope.delete eq 'true'}">
						<spring:message code="product.delete"/>
		      		</c:when>
		      		<c:when test="${requestScope.create eq 'true'}">
						<spring:message code="product.create"/>
		      		</c:when>
		      		<c:when test="${requestScope.update eq 'true'}">
		      			<spring:message code="product.update"/>
		   			</c:when>
		      	</c:choose>	
		   	</h5>
			<div class="form form-small">	
			  <form:form modelAttribute="product" action="${pageContext.request.contextPath}${requestScope.action}">
		      <div class="span4">
				<form:hidden path="id"/>
				<form:hidden path="rating.id"/>
<%-- 				<input type="hidden" id="ratingId" value="${ratingId}" name="ratingId"/> --%>
				
				<form:hidden path="active"/> <!-- senza questo campo, l'ACTIVE del prodotto viene passato come "false" al controller da Spring -->
				<div>
				    <label for="name"><spring:message code="product.name"/></label>
				    <div>
				    	<form:input id="name" path="name"/><br />
				    	<form:errors path="name"/>
				    </div>
				</div>
				<div>
				    <label for="description"><spring:message code="product.description"/></label>
				    <div class="text-area">
                    	<form:textarea path="description" id="description" rows="10"/>
                    	<form:errors path="description"/>
               		</div>
				</div>		
				<div>
				    <label for="price"><spring:message code="product.price"/></label>
				    <div>
				    	<form:input id="price" path="price"/><br />
				    	<form:errors path="price"/>
				    </div>
				</div>
				
				<div>
				    <label for="stock"><spring:message code="product.stock" /></label>
				    <div>
				    	<form:input id="stock" path="stock"/><br />
				    	<form:errors path="stock"/>
				    </div>
				</div>		
				
				<div>
				    <label for="Measure"><spring:message code="product.measure" /></label>
				    <div>
						<form:select id="measureId" path="measure.id">
							<form:options items="${measures}" itemValue="id" itemLabel="name"/>
						</form:select><br />
						<form:errors path="measure.id"/>
				    </div>
				</div>				
				
				<div>
				    <label for="CategoryId"><spring:message code="product.category"/></label>
				    <div>
						<form:select id="categoryId" path="category.id">
							<form:options items="${categories}" itemValue="id" itemLabel="name"/>
						</form:select><br />
						<form:errors path="category.id"/>
				    </div>
				</div>
				
				<security:authorize access="hasRole('admin')">
				<div>
				    <label for="Seller">Seller</label>
				    <div>
						<form:select id="sellerId" path="seller.id">
							<form:options items="${sellers}" itemValue="id" itemLabel="company"/>
						</form:select><br />
						<form:errors path="seller.id"/>
				    </div>
				</div>
				</security:authorize>
				
				
				<!-- inizio DATEPICKER from-to -->
				
		
				
		 		<div>
				    <label for="dateIn"><spring:message code="product.dateIn"/></label>
				    <div>
				    	<form:input id="from" path="dateIn"/><br />
				    	<form:errors path="dateIn"/>
				    </div>
				</div> 
				
		 		<div>
				    <label for="dateOut"><spring:message code="product.dateOut"/></label>
				    <div>
				    	<form:input id="to" path="dateOut"/><br />
				    	<form:errors path="dateOut"/>
				    </div>
				</div>		
				
				<!-- fine DATEPICKER -->
				
				<c:if test="${requestScope.create ne 'true'}">
				<!-- SPINNER -->
				
				<div class="fuelux row">
					<label for="position"><spring:message code="product.position"/></label>
					<div class="spinner">
						<form:input id="position" path="position" class="input-mini spinner-input" maxlength="3" />
						<div class="spinner-buttons	 btn-group btn-group-vertical">
							<button type="button" class="btn spinner-up">
								<i class="icon-chevron-up"></i>
							</button>
							<button type="button" class="btn spinner-down">
								<i class="icon-chevron-down"></i>
							</button>
						</div>
					</div>
				</div>
				<!-- END SPINNER -->
				</c:if>
				<div>
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
				<c:if test="${requestScope.update eq 'true'}">
				<div class="span4 productImages">
					<div class="row-fluid">
						<a class="btn" href="#modalWindow" role="button" data-toggle="modal" onclick="createModalWindow('addImages','product','${product.id}',null)">Add Images...</a>
					</div>
					<div id="km0Images">
				  	<c:forEach var="image" items="${product.images}">
					  	<div id="km0Image">
					  		<div>
					  			<a href="#modalWindow" class="icon-edit" role="button" data-toggle="modal" onclick="createModalWindow('updateImage','product','${product.id}','${image.id}')" ></a>	
						       	<a href="#modalWindow" class="icon-remove"  role="button" data-toggle="modal" onclick="createModalWindow('deleteImage','product','${product.id}','${image.id}')"></a>
					  		</div>
					  			<span id="image_${image.id}">
						       		<img src="${pageContext.request.contextPath}/product/image/${image.id}/${image.name}" alt="${image.name}" />
					       		</span>	
					    </div>
			    	</c:forEach>
			    	</div>
		      	</div>	
		      	</c:if>
			</div>
		</div>

