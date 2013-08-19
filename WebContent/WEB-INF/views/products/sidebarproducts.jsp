<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

 <!-- Sidebar -->
<div class="span3">
	<h5 class="title">Filter by Category</h5>
	        <!-- Sidebar navigation -->
	        <nav id="categ">
	            <ul id="nav">
	            <li><a id="cat_" href="#"><spring:message code="category.all"/></a></li>
					<tag:categoriesPrinter categoryTree="${categoryTree}"/> 
				</ul>
	        </nav>
<br />
	        
	<h5 class="title">Filter by Seller</h5>
		        <!-- Sidebar navigation -->
		        <nav id="seller">
		            <ul id="nav">
		            <li><a id="seller_" href="#"><spring:message code="category.all"/></a></li>
					<c:forEach var="seller" items="${sellers}">
		 				<li><a id="seller_${seller.id}" href="#">${seller.company}</a></li>
					</c:forEach> 
					</ul>
		        </nav>

<br />

	<div class="sidebar-items">
	<h5 class="title">Featured Items</h5>   
	<c:forEach var="product" items="${products}">   
		<div class="sitem">
			<div class="onethree-left">
				<a href="${pageContext.request.contextPath}/products/${product.id}/${product.name}">
					<img src="${pageContext.request.contextPath}/prod/image/<c:out value="${product.images[0].id}"/>/<c:out value="${product.images[0].name}"/>" alt="<c:out value="${product.images[0].altName}"/>" />
				</a>
	        </div>
			<div class="onethree-right">
				<a href="${pageContext.request.contextPath}/products/${product.id}/${product.name}">${product.name}</a>
				<c:if test="${product.description ne null}">
					<p>${fn:substring(product.description, 0, 40)}...</p>
				</c:if>
				<p class="bold">€ ${product.price}</p>
			</div>
			<div class="clearfix"></div>          
		</div> 
	</c:forEach> 
	</div>

</div>
    
      
      
      
      
   