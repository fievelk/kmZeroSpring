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
	    	<li><a href="${pageContext.request.contextPath}/sellers/upgrade_start">Upgrade a venditore</a></li>
	    	</security:authorize>
	    	<security:authorize access="hasRole('seller')"> 
		      <li><a href="${pageContext.request.contextPath}/sellers/update_start">Profilo</a></li> 
		      <li class="has_sub"><a href="#"><spring:message code="sellercontent.views"/></a>
		      <!-- Submenu -->
                <ul>
		      		 <li><a href="${pageContext.request.contextPath}/sellers/pagecontents">Contenuti Pagina</a>
		      		 <li><a href="${pageContext.request.contextPath}/sellers/<security:authentication property="principal.id"/>/<security:authentication property="principal.company"/>">Preview</a></li>
		      	</ul>	
		      </li>
		      <li><a href="${pageContext.request.contextPath}/products/viewsforsellers">Prodotti</a></li>
		      <li><a href="">Ordini <span style="color:red">- NIY</span></a></li>
			</security:authorize>
			<security:authorize access="hasRole('admin')">
		      <li><a href="${pageContext.request.contextPath}/products/viewsforsellers">Prodotti</a></li>
		      <li><a href="${pageContext.request.contextPath}/products/viewCategories">Categorie</a></li>
		      <li><a href="${pageContext.request.contextPath}/products/viewMeasures">Misure</a></li>
		      <li><a href="${pageContext.request.contextPath}/users/admin/views">Utenti</a></li>
		      <li class="has_sub"><a href="#">Venditori</a>
		      <!-- Submenu -->
                <ul>
					<li><a href="${pageContext.request.contextPath}/sellers/admin/viewsEnabled">Abilitati</a></li>
					<li><a href="${pageContext.request.contextPath}/sellers/admin/viewsToEnable">Da Abilitare</a></li>  
		      	</ul>	
		      </li>
		      <li><a href="${pageContext.request.contextPath}/users/edit_start_password">Modifica Password</a></li> 
			  <li><a href="${pageContext.request.contextPath}/sellers/admin/viewWarehouses">Warehouse</a></li>
		      <li><a href="">Ordini <span style="color:red">- NIY</span></a></li>
		      <li><a href="${pageContext.request.contextPath}/sellers/admin/usersdeliverymap">Consegne da effettuare</a></li>
			</security:authorize>
			  <li><a href="${pageContext.request.contextPath}/users/userorderview"> Ordini effettuati<span style="color:red">- WIP</span></a></li>
	     </security:authorize>
	    </ul>
</div>