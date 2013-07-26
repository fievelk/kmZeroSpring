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
		$(":input[type='text'],select[id='categoryId']").each(function () { $(this).attr('readonly','readonly'); });				
	}		
});

/*--------SETUP READONLY FIELDS IF DELETING - END--------*/
 
/*--------DATE PICKER - START--------*/

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

/*--------DATE PICKER END--------*/
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
		<c:if test="${requestScope.update eq 'true'}">
		<div class="span4 addImages">
			<div class="row-fluid">
				<a class="btn" href="#addImages" role="button" data-toggle="modal">Add Images...</a>
			</div>
			<div id="productImages">
		  	<c:forEach var="image" items="${product.images}">
		  			<span id="image_${image.id}">
			       		<img src="${pageContext.request.contextPath}/products/image/${image.id}/${image.name}" alt="${image.name}">
			       		<a href="#dialog" class="icon-remove-circle"  role="button" data-toggle="modal" onclick="dialog('${image.id}','${product.id}')"></a>	
		       		</span>	
	    	</c:forEach>
	    	</div>
      	</div>	
   		</c:if>
		
	  </form:form>
	</div>
	
</div>
</div>
</div>
</div>