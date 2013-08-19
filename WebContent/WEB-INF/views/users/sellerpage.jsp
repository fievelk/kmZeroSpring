<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="span9">
	<div class="row">
		<div class="span9 gmap">
		    <!-- Google Maps. Replace the below iframe with your Google Maps embed code -->
		    <iframe height="200" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="http://maps.google.co.in/maps?f=q&amp;source=s_q&amp;hl=en&amp;geocode=&amp;q=Google+India+Bangalore,+Bennigana+Halli,+Bangalore,+Karnataka&amp;aq=0&amp;oq=google+&amp;sll=9.930582,78.12303&amp;sspn=0.192085,0.308647&amp;ie=UTF8&amp;hq=Google&amp;hnear=Bennigana+Halli,+Bangalore,+Bengaluru+Urban,+Karnataka&amp;t=m&amp;ll=12.993518,77.660294&amp;spn=0.012545,0.036006&amp;z=15&amp;output=embed"></iframe>
		 </div>
		<c:set var="alternate" value="1"/>
		<c:forEach var="content" items="${seller.contents}">
			<c:choose>
			    <c:when test="${alternate == 1}">
						<div class="span9"><h4 class="title">${content.title}</h4></div>
						<div class="span6">
							${content.description}
						</div>
						<div class="span3">
							<img src="${pageContext.request.contextPath}/selr_content/image/${content.image.id}/${content.image.name}" alt="${content.image.name}" />
						</div>
			       <c:set var="alternate" value="0"/>
			    </c:when>
			    <c:when test="${alternate == 0}">
						<div class="span9"><h4 class="title">${content.title}</h4></div>
						<div class="span3">
							<img src="${pageContext.request.contextPath}/selr_content/image/${content.image.id}/${content.image.name}" alt="${content.image.name}" />
						</div>
						<div class="span6" >
							${content.description}
						</div>
			        <c:set var="alternate" value="1"/>
			    </c:when>
			</c:choose>
		</c:forEach>
	</div>
</div>
<br/><br/>
<div class="recent-posts">
    <div class="row">
    <div class="container">
      <div class="span12">
        <div class="bor"></div>
        <h4 class="title"><spring:message code="product.ourproducts"/></h4>
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
           <c:forEach var="product" items="${seller.products}">
						  <li style="width: 180px;">
							<div class="rp-item"> 
						           <div class="rp-image">        
						             <a href="single-item.html">	
									<img src="${pageContext.request.contextPath}/prod/image/<c:out value="${product.images[0].id}" />/<c:out value="${product.images[0].name}" />" alt="<c:out value="${product.images[0].altName}" />" />
						          	</a>
						       	</div>
								<div class="rp-details">
								  <!-- Title and para -->
								  <h5><a href="single-item.html">${product.name}<span class="price pull-right">$255</span></a></h5>
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
