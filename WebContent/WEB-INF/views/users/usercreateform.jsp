<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<script>
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
				<h5 class="title"><spring:message code="user.add"/></h5>
				<div class="form form-small">
					<form:form modelAttribute="user" styleClass="form-horizontal" action="${pageContext.request.contextPath}${requestScope.action}">
					<form:hidden path="id"/>
					<div class="control-group">
					    <label class="control-label" for="name"><spring:message code="user.name"/></label>
					    <div class="controls">
					    	<form:input id="name" path="name"/>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label" for="surname"><spring:message code="user.surname"/></label>
					    <div class="controls">
					    	<form:input id="surname" path="surname"/>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label" for="email"><spring:message code="user.email"/></label>
					    <div class="controls">
					    	<form:input id="email" path="email"/>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label" for="password"><spring:message code="user.password"/></label>
					    <div class="controls">
					    	<form:password id="password" path="password"/>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label" for="confirm_password"><spring:message code="user.confirm_password"/></label>
					    <div class="controls">
					    	<input type="password" name="confirm_password" id="confirm_password"/>
					    </div>
					</div>
					<%-- <div class="control-group">
					    <label class="control-label" for="confirm_password"><bean:message key="user.confirm_password"/></label>
					    <div class="controls">
					    	<html:text styleId="confirm_password" property="confirm_password"/>
					    </div>
					</div> --%>
					<%-- <div class="control-group">
					    <label class="control-label" for="created"><bean:message key="user.created"/></label>
					    <div class="controls">
							<html:text styleId="created_data" property="created_data" />
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label" for="date_of_birth"><bean:message key="user.date_of_birth"/></label>
					    <div class="controls">
							<html:text styleId="date_of_birth_data" property="date_of_birth_data"/>
					    </div>
					</div> --%>
					
					<div class="control-group">
					    <label class="control-label" for="date_of_birth"><spring:message code="user.date_of_birth"/></label>
						<div class="controls">
							<form:input id="datepicker" path="date_of_birth"/>
						</div>
					</div>
					
					<div class="control-group">
					    <label class="control-label" for="address"><spring:message code="user.address"/></label>
					    <div class="controls">
							<form:input id="address" path="address"/>
					    </div>
					</div>
					
					<div class="control-group">
					    <div class="controls">
					      <button class="btn" type="submit">Invia</button>
						</div>
					</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>