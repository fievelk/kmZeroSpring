<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

 <!-- Sidebar -->
<div class="span3">

	<h5 class="title"><spring:message code="menu.sellers"/></h5>
		        <!-- Sidebar navigation -->
		        <nav id="seller">
		            <ul id="nav">
		            <li><a id="seller_" href="${pageContext.request.contextPath}/sellers"><spring:message code="category.all"/></a></li>
					<c:forEach var="seller" items="${sellers}">
		 				<li><a href="${pageContext.request.contextPath}/sellers/${seller.id}/${seller.company}">${seller.company}</a></li>
					</c:forEach> 
					</ul>
		        </nav>
	<br />
</div>