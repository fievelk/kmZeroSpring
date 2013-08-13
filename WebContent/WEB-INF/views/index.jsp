<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="span12">
	<div class="flex-image flexslider">
		<ul class="slides">
		    <c:forEach var="seller" items="${sellers}">
		    	<li>
				<c:forEach var="image" items="${seller.images}">
					<img src="${pageContext.request.contextPath}/selr/image/${image.id}/${image.name}" alt="${image.name}" />
				</c:forEach>
                 
                  <div class="flex-caption">
                     <!-- Title -->
                     <h3>${seller.name } - <span class="color">Just $149</span></h3>
                     <!-- Para -->
                     <p>Ut commodo ullamcorper risus nec mattis. Fusce imperdiet ornare dignissim. Donec aliquet convallis tortor, et placerat quam posuere posuere. Morbi tincidunt posuere turpis eu laoreet. </p>
                     <div class="button">
                      <a href="single-item.html">Buy Now</a>
                     </div>
                  </div>                  
                </li>
		    </c:forEach>    
		</ul>
	</div>
</div>
      <div class="span12">
<div class="recent-posts">
  <div class="container">
    

        <div class="bor"></div>
        <h4 class="title">Recent Items</h4>
        <div class="carousel_nav pull-right">
          <!-- Navigation -->
          <a class="prev" id="car_prev" href="#"><i class="icon-chevron-left"></i></a>
          <a class="next" id="car_next" href="#"><i class="icon-chevron-right"></i></a>
        </div>
        <div class="clearfix"></div>
		        <ul class="rps-carousel">
		            <!-- Recent items #1 -->
		            <!-- Each item should be enclosed inside "li"  tag. -->
		             <c:forEach var="product" items="${products}">
		            <li>
		                <div class="rp-item"> 
		                  <div class="rp-image">    
		                  <c:forEach var="image" items="${product.images}">       
                    		<a href="single-item.html">
                    	 		<img src="${pageContext.request.contextPath}/prod/image/${image.id}/${image.name}" alt="${image.name}" />
                    		</a>
                    	  </c:forEach>
		                  </div>
		                  <div class="rp-details">
		                    <!-- Title and para -->
		                    <h5><a href="single-item.html">${product.name}<span class="price pull-right">${product.price}</span></a></h5>
		                    <div class="clearfix"></div>
		                    <!-- Description -->
		                    <p>${product.description}</p>         
		                  </div>                
		                </div>        
		            </li>
		           </c:forEach>
		        </ul>
			</div>
		</div>
	</div>
</div>
 