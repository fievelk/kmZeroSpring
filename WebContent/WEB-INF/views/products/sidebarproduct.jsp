<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

 <!-- Sidebar -->
<div class="span3">
    
	<div class="sidebar-items">
	<h5 class="title">Products in the Same category</h5>   
	<c:forEach var="product" items="${sameCategoryProducts}">   
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
    
      
      
      
      
   