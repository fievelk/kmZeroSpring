<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="span12">
	<div class="flex-image flexslider">
		<ul class="slides">
		    <c:forEach var="seller" items="${sellers}">
		    	<li>
					<img src="${pageContext.request.contextPath}/seller/image/<c:out value="${seller.images[0].id}"/>/<c:out value="${seller.images[0].name}"/>" alt="<c:out value="${seller.images[0].altName}"/>" />
                  <div class="flex-caption">
                     <!-- Title -->
                     <h3>${seller.name} - <span class="color">${seller.company}</span></h3>
                     <!-- Para -->
                     <p>${fn:substring(seller.contents[0].description, 0, 100)}...</p>
                     <div class="button">
                      <a href="${pageContext.request.contextPath}/sellers/${seller.id}/${seller.company}"><spring:message code="common.buyNow"/></a>
                     </div>
                  </div>                  
                </li>
		    </c:forEach>    
		</ul>
	</div>  
	
</div>
<div class="recent-posts">
    <div class="row">
    <div class="container">
      <div class="span12">
        <div class="bor"></div>
        <h4 class="title"><spring:message code="product.recent"/></h4>
        <div class="carousel_nav pull-right">
          <!-- Navigation -->
          <a href="#" id="car_prev" class="prev" style="display: inline;"><i class="icon-chevron-left"></i></a>
          <a href="#" id="car_next" class="next" style="display: inline;"><i class="icon-chevron-right"></i></a>
        </div>
        <div class="clearfix"></div>
        <div class="caroufredsel_wrapper" style="display: block; text-align: start; float: none; position: relative; top: auto; right: auto; bottom: auto; left: auto; z-index: auto; width: 940px; height: 255px; margin: 0px; overflow: hidden;">
        <ul class="rps-carousel" style="text-align: left; float: none; position: absolute; top: 0px; right: auto; bottom: auto; left: 0px; margin: 0px; width: 3760px; height: 255px;">
            <!-- Recent items #1 -->
            <!-- Each item should be enclosed inside "li"  tag. -->
           <c:forEach var="product" items="${products}">
						  <li style="width: 180px;">
							<div class="rp-item"> 
						           <div class="rp-image">        
						             <a href="${pageContext.request.contextPath}/products/${product.id}/${product.name}">	
									<img src="${pageContext.request.contextPath}/product/image/<c:out value="${product.images[0].id}" />/<c:out value="${product.images[0].name}" />" alt="<c:out value="${product.images[0].altName}" />" width="100" height="100"/>
						          	</a>
						       	</div>
								<div class="rp-details">
								  <!-- Title and para -->
								  <h5><a href="${pageContext.request.contextPath}/products/${product.id}/${product.name}">${product.name}<span class="price pull-right">€ ${product.price}</span></a></h5>
								  <div class="clearfix"></div>
								  <p>
								  	<c:if test="${product.description ne null }">
								  		${fn:substring(product.description, 0, 25)}...
								  	</c:if>
								  </p>         
								</div>                
							</div>        
						  </li>
						  </c:forEach>                                                                                                  
        </ul></div>
      </div>
    </div>
  </div>
 </div>