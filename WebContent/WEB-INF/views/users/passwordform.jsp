<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


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
				<h5 class="title"><spring:message code="password.edit"/></h5>
				<div class="form form-small">
					<form:form modelAttribute="user" cssClass="form-horizontal" action="${pageContext.request.contextPath}${requestScope.action}" method="POST">
					<form:hidden path="id"/>
					
					<div class="control-group">
					    <label class="control-label" for="old_password"><spring:message code="password.oldPass"/></label>
					    <div class="controls">
					    	<form:password id="old_password" path="password.old_password"/>
					    	<form:errors path="password.old_password"/>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label" for="password"><spring:message code="password.newPass"/></label>
					    <div class="controls">
					    	<form:password id="password" path="password.password"/>
					    	<form:errors path="password.password"/>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label" for="confirm_password"><spring:message code="password.confirmPass"/></label>
					    <div class="controls">
					    	<form:password id="confirm_password" path="password.confirm_password"/>
					    	<form:errors path="password.confirm_password"/>
					    </div>
					</div>
					
					<div class="control-group">
					    <div class="controls">
					      <button type="submit" class="btn">
					      	<spring:message code="common.submit"/>
					      </button>
					    </div>
					</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>