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
	
	
	  <form:form modelAttribute="user" action="${pageContext.request.contextPath}${requestScope.action}">
		<form:hidden path="id"/>
		<div>
		    <label for="name">Name</label>
		    <div>
		    	<form:input id="name" path="name"/>
		    </div>
		</div>
		
		 <div>
		    <label for="surname">Surname</label>
		    <div>
		    	<form:input id="surname" path="surname"/>
		    </div>
		</div>
		
		
		<div>
		    <label for="address">Address</label>
		    <div>
		    	<form:input id="address" path="address"/>
		    </div>
		</div>
		
		<div>
		    <label for="Role">Role</label>
		    <div>
		    	<%-- <form:radiobuttons path="roles" items="${ruoli}"/> --%>
				<form:checkboxes path="roles" items="${ruoli}"/>
		    </div>
		</div> 
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