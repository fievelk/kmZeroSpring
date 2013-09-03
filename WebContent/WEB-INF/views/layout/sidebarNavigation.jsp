<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="span3 side-menu">
	<!-- Sidebar navigation -->
	<h5 class="title">Menu</h5>
	<!-- Sidebar navigation -->

	    <ul id="nav">
	    <security:authorize access="isAuthenticated()">
	    	<security:authorize access="hasRole('user')">
	    	<li><a href="${pageContext.request.contextPath}/sellers/upgrade_start"><spring:message code="seller.upgrade"/></a></li>
	    	</security:authorize>
	    	<security:authorize access="hasRole('seller')"> 
		      <li><a href="${pageContext.request.contextPath}/sellers/update_start"><spring:message code="menu.profile"/></a></li> 
		      <li class="has_sub"><a href="#"><spring:message code="sellercontent.views"/></a>
		      <!-- Submenu -->
                <ul>
		      		 <li><a href="${pageContext.request.contextPath}/sellers/pagecontents?id=<security:authentication property="principal.id"/>"><spring:message code="menu.contents"/></a>
		      		 <li><a href="${pageContext.request.contextPath}/sellers/<security:authentication property="principal.id"/>/<security:authentication property="principal.company"/>"><spring:message code="menu.preview"/></a></li>
		      	</ul>	
		      </li>
		      <li><a href="${pageContext.request.contextPath}/users/edit_start_password"><spring:message code="password.edit"/></a></li> 
		      <li><a href="${pageContext.request.contextPath}/products/viewsforsellers"><spring:message code="menu.products"/></a></li>
		      <li><a href="${pageContext.request.contextPath}/sellers/sellerreceivedorders"><spring:message code="seller.receivedOrders"/></a></li>
			</security:authorize>
			<security:authorize access="hasRole('admin')">
		      <li><a href="${pageContext.request.contextPath}/products/viewsforsellers"><spring:message code="menu.products"/></a></li>
		      <li><a href="${pageContext.request.contextPath}/products/viewCategories"><spring:message code="category.list"/></a></li>
		      <li><a href="${pageContext.request.contextPath}/products/viewMeasures"><spring:message code="menu.measures"/></a></li>
		      <li><a href="${pageContext.request.contextPath}/users/admin/views"><spring:message code="menu.users"/></a></li>
		      <li class="has_sub"><a href="#"><spring:message code="menu.sellers"/></a>
		      <!-- Submenu -->
                <ul>
					<li><a href="${pageContext.request.contextPath}/sellers/admin/viewsEnabled"><spring:message code="seller.enabled"/></a></li>
					<li><a href="${pageContext.request.contextPath}/sellers/admin/viewsToEnable"><spring:message code="seller.toenable"/></a></li>  
		      	</ul>	
		      </li>
		      <li><a href="${pageContext.request.contextPath}/users/edit_start_password"><spring:message code="password.edit"/></a></li> 
			  <li><a href="${pageContext.request.contextPath}/sellers/admin/viewWarehouses"><spring:message code="warehouse.view"/></a></li>
			  <li class="has_sub"><a href="#"><spring:message code="menu.manageOrders"/></a>
		      <!-- Submenu -->
                <ul>
					<li><a href="${pageContext.request.contextPath}/sellers/admin/usersdeliverymap"><spring:message code="planning.usersdelivery"/></a></li>
		      		<li><a href="${pageContext.request.contextPath}/sellers/admin/sellerspickupmap"><spring:message code="planning.sellerspickup"/></a></li>
		      	</ul>	
		      </li>
			</security:authorize>
			  <li><a href="${pageContext.request.contextPath}/users/userordersview"><spring:message code="cart.yourCarts"/></a></li>
	     </security:authorize>
	    </ul>
</div>
