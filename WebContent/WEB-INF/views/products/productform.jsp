<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var del = "${requestScope.delete}"; 
		if (del == "true" ) {
			$(":input[type='text'],select[id='categoryId']").each(function () { $(this).attr('readonly','readonly'); });				
		}		
	});
	
	function doAjaxDeleteImg(image_id){
		url = "${pageContext.request.contextPath}/products/image/delete/"+image_id;
		$.ajax({
			type: "POST",
			url: url,
			success: function(data) {
						if(data == true){
							$("#image_"+image_id).remove();
						}
					}
		});		
		return false;
	}
	
</script>

<div class="items">
	<div class="container">
		<div class="row">

     <div class="span3 side-menu">

        <!-- Sidebar navigation -->
        <h5 class="title">Menu</h5>
        <!-- Sidebar navigation -->
          <nav>
            <ul id="navi">
              <li><a href="myaccount.html">Gestione Ordini</a></li>
              <li><a href="wish-list.html">Storico Ordini</a></li>
              <li><a href="order-history.html">Gestione Utenti</a></li>
              <li><a href="edit-profile.html">Gestione Venditori</a></li>
            </ul>
          </nav>

      </div>
      
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
		<form:hidden path="active"/> <!-- senza questo campo, l'ACTIVE del prodotto viene passato come "false" al controller da Spring -->
		<div>
		    <label for="name"><spring:message code="product.name"/></label>
		    <div>
		    	<form:input id="name" path="name"/>
		    </div>
		</div>
		
		<div>
		    <label for="description"><spring:message code="product.description"/></label>
		    <div>
		    	<form:input id="description" path="description"/>
		    </div>
		</div>
		
		
		<div>
		    <label for="price"><spring:message code="product.price"/></label>
		    <div>
		    	<form:input id="price" path="price"/>
		    </div>
		</div>
		
		<div>
		    <label for="CategoryId"><spring:message code="product.category"/></label>
		    <div>
				<form:select id="categoryId" path="category.id">
					<form:options items="${categories}" itemValue="id" itemLabel="name"/>
				</form:select>
		    </div>
		</div>
		
		<!-- inizio DATEPICKER from-to -->
		
		 <script>
		$(function() {
			$( "#from" ).datepicker({
				defaultDate: "+1w",
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
				defaultDate: "+1w",
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
		</script>
		
 		<div>
		    <label for="date_in"><spring:message code="product.date_in"/></label>
		    <div>
		    	<form:input id="from" path="date_in"/>
		    </div>
		</div> 
		
 		<div>
		    <label for="date_out"><spring:message code="product.date_out"/></label>
		    <div>
		    	<form:input id="to" path="date_out"/>
		    </div>
		</div>		
		
		<!-- fine DATEPICKER -->
		
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
		<div class="span4 addImages">
			<div class="row-fluid">
				<a class="btn" href="#addImages" role="button" data-toggle="modal">Add Images...</a>
			</div>
			<div id="productImages">
		  	<c:forEach var="image" items="${product.images}">
		  			<span id="image_${image.id}">
			       		<img src="${pageContext.request.contextPath}/products/image/${image.id}" alt="${image.name}">
			       		<a class="icon-remove-circle" onclick="doAjaxDeleteImg('${image.id}')" href="#"></a>
		       		</span>	
	    	</c:forEach>
	    	</div>
      	</div>	
	  </form:form>
	</div>
	
</div>
</div>
</div>
</div>