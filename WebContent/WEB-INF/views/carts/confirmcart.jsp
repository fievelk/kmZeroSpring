<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<script type="text/javascript">
	$(document).ready(createModalCart());
	
	var mindate = new Date();
	var maxdate = new Date();
	
	var minday = mindate.getDate() + 3;
	var maxday = maxdate.getDate() + 12;
	
	mindate.setDate(minday);
	maxdate.setDate(maxday);
	
	$(function() {
		$( "#cartpicker" ).datepicker({
			minDate: mindate,
			maxDate: maxdate,
			changeMonth: true,
			changeYear: true,
			dateFormat: 'dd/mm/yy',
			showAnim: "slideDown"
		});
	});
</script>

<%-- <security:authorize access="!isAuthenticated()">
<div class="items">
	<div class="container">
 	   <div class="row">
			<div class="span6">
			  <div class="formy well">
			    <h4 class="title">Login to Your Account</h4>
			    <div class="form">
			         <form name="loginform" action="${pageContext.request.contextPath}/j_spring_security_check" method="post" class="navbar-form pull-right">
			           <input class="span2" type="text" placeholder="Username" name="j_username">
			           <input class="span2" type="password" placeholder="Password" name="j_password">
			           <button type="submit" class="btn"><spring:message code="common.signin"/></button>
			         </form>
			        <hr />
			        <h5>New Account</h5>
			               Don't have an Account? <a href="${pageContext.request.contextPath}/users/create">Register</a>
			    </div> 
			  </div>
			</div>
		</div>
	</div>
</div>
</security:authorize>

<security:authorize access="isAuthenticated()"> --%>
<div class="span9">
	<h5 class="title">Carrello</h5>
	<div class="form form-small">
		<form:form modelAttribute="cart" cssClass="form-horizontal" action="${pageContext.request.contextPath}${requestScope.action}" method="POST">
		<form:hidden path="id"/>
		<form:hidden path="name"/>
		<form:hidden path="surname"/>
		<form:hidden path="address"/>
		<table class="table table-striped tcart" id="tablecart">
          <thead>
            <tr>
              <th>Name</th>
              <th>Quantity</th>
              <th>Price</th>
              <th>Delete</th>
            </tr>
          </thead>
          	<tbody id="cartlines">
          	</tbody>
		</table>
		<div class="row">
			<div class="span4 offset5">
				<div class="pull-right">
					<span><h6 class="">Scegli una data di consegna</h6></span>
					<form:input  class="pull-right" id="cartpicker" path="delivery_date"/></br>
					<form:errors class="pull-right" path="delivery_date"/>
				</div>
				
			</div>
		</div>
		<div class="row">
	        <div class="span9">
				<button type="submit" class="btn btn-danger pull-right" id="button_checkout">Conferma il carrello</button>
			</div>
        </div>
        
		</form:form>
	</div>
</div>
<%-- </security:authorize> --%>