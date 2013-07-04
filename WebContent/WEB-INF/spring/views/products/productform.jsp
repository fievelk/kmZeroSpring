<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- <%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%> --%>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var del = "${requestScope.delete}"; 
		if (del == "true" ) {
			$(":input[type='text'],select[id='categoryId']").each(function () { $(this).attr('disabled','disabled'); });				
		}		
	});
	
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
	<h5 class="title">insert or modify product </h5>
	
	<div class="form form-small">
	
	  <form:form modelAttribute="product" action="${pageContext.request.contextPath}${requestScope.action}">
		<form:hidden path="oid"/>
		<div>
		    <label for="name">Nome</label>
		    <div>
		    	<form:input id="name" path="name"/>
		    </div>
		</div>
		
		<div>
		    <label for="description">Descrizione</label>
		    <div>
		    	<form:input id="description" path="description"/>
		    </div>
		</div>
		
		
		<div>
		    <label for="price">Prezzo</label>
		    <div>
		    	<form:input id="price" path="price"/>
		    </div>
		</div>
		
		<div>
		    <label for="CategoryId">Categoria</label>
		    <div>
				<form:select id="categoryId" path="category.oid">
					<form:options items="${categories}" itemValue="oid" itemLabel="name"/>
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
		
		
<%-- 		<input type="hidden" id="from" name="date_in" value="<fmt:formatDate value="${product.date_in}" pattern="MM/dd/yyyy"/>" />
		<input type="hidden" id="to" name="date_out" value="<fmt:formatDate value="${product.date_out}" pattern="MM/dd/yyyy"/>"/>
		
		<div>
		    <label for="from_fake">Data di inizio fittizia</label>
			    <input type="text" id="from_fake" name="date_in_fake" readonly=readonly value="<fmt:formatDate value="${product.date_in}" pattern="dd/MM/yyyy"/>" />
		</div>
		
		<div>
		    <label for="to_fake">Data di inizio fittizia</label>
			    <input type="text" id="to_fake" name="date_out_fake" readonly=readonly value="<fmt:formatDate value="${product.date_out}" pattern="dd/MM/yyyy"/>" />
		</div> --%>
		
 		<div>
		    <label for="date_in">Data di inizio</label>
		    <div>
		    	<form:input id="from" path="date_in"/>
		    </div>
		</div> 
		
 		<div>
		    <label for="date_out">Data di fine</label>
		    <div>
		    	<form:input id="to" path="date_out"/>
		    </div>
		</div>		

		
<%--   		<div>
		    <label for="from">Data di inizio</label>
			    <input type="text" id="from" name="date_in" value="${requestScope.date_in}"/>
		    <label for="to">Data di fine</label>
			    <input type="text" id="to" name="date_out" value="${requestScope.date_out}"/>
		</div> --%>
		
		<!-- fine DATEPICKER -->
		
		<div class="control-group">
		    <div class="controls">
		      <button type="submit">
		      	<c:choose>
		      		<c:when test="${requestScope.delete eq 'true'}">
						DELETE
		      		</c:when>
		      		<c:otherwise>
		      			SUBMIT
		      		</c:otherwise>
      			</c:choose>	
      		  </button>
			</div>
		</div>
	  </form:form>
	</div>
</div>
</div>
</div>
</div>