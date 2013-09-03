<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="span9">
        <!-- Product details -->
        <div class="product-main">
          <div class="row">
            <div class="span4">

              <!-- Image. Flex slider -->
                <div class="product-slider">
                  <div class="product-image-slider flexslider">
                    <ul class="slides">
					<c:forEach var="image" items="${product.images}"> 
                      <li style="width: 100%; float: left; margin-right: -100%; position: relative; display: none;" class="">
                        <!-- Image -->
                        <img src="${pageContext.request.contextPath}/product/image/${image.id}/${image.name}+'" alt="${image.name}" />
                       
                      </li>
                    </c:forEach> 
                    </ul>
                  <ul class="flex-direction-nav"><li><a href="#" class="flex-prev">Previous</a></li><li><a href="#" class="flex-next">Next</a></li></ul></div>
              </div>

            </div>
            <div class="span5">
              <!-- Title -->
                <h4 class="title">${product.name}</h4>
                <h5 class="item-price">&euro; ${product.price}/${product.measure.shortName}</h5>
                <p><spring:message code="product.category" />: ${product.category.name}</p>
                <p><spring:message code="product.seller" />: <a href="${pageContext.request.contextPath}/sellers/${product.seller.id}/${product.seller.company}">${product.seller.company}</a>
                                 
                <!-- Quantity and add to cart button -->
                        <div class="input-append cart-quantity">
                          <input id="${product.id}" type="text" value="2" class="input-mini">
                          <button type="button" class="btn" onclick="existCart(${product.id});"><spring:message code="product.addToCart" /></button>      
                        </div>

                        <!-- Share button -->
                <!-- AddThis Button BEGIN -->
                <div class="addthis_toolbox addthis_default_style ">
                <a class="addthis_button_preferred_1 addthis_button_facebook at300b" title="Facebook" href="#"><span class="at16nc at300bs at15nc at15t_facebook at16t_facebook"><span class="at_a11y">Share on facebook</span></span></a>
                <a class="addthis_button_preferred_2 addthis_button_twitter at300b" title="Tweet" href="#"><span class="at16nc at300bs at15nc at15t_twitter at16t_twitter"><span class="at_a11y">Share on twitter</span></span></a>
                <a class="addthis_button_preferred_3 addthis_button_email at300b" target="_blank" title="E-mail" href="#"><span class="at16nc at300bs at15nc at15t_email at16t_email"><span class="at_a11y">Share on email</span></span></a>
                <a class="addthis_button_preferred_4 addthis_button_print at300b" title="Stampa" href="#"><span class="at16nc at300bs at15nc at15t_print at16t_print"><span class="at_a11y">Share on print</span></span></a>
                <a class="addthis_button_compact at300m" href="#"><span class="at16nc at300bs at15nc at15t_compact at16t_compact"><span class="at_a11y">More Sharing Services</span></span></a>
                <a class="addthis_counter addthis_bubble_style" style="display: block;" href="#"><a class="addthis_button_expanded" target="_blank" title="Più..." href="#">0</a><a class="atc_s addthis_button_compact"><span></span></a></a>
                <div class="atclear"></div></div>
                <script src="//s7.addthis.com/js/300/addthis_widget.js#pubid=xa-50e06de237d5cb9d" type="text/javascript"></script><div style="visibility: hidden; height: 1px; width: 1px; position: absolute; z-index: 100000;" id="_atssh"><iframe id="_atssh849" title="AddThis utility frame" style="height: 1px; width: 1px; position: absolute; z-index: 100000; border: 0px none; left: 0px; top: 0px;" src="//ct1.addthis.com/static/r07/sh134.html#"></iframe></div><script type="text/javascript" src="http://ct1.addthis.com/static/r07/core089.js"></script>
                <!-- AddThis Button END -->
            </div>
          </div>
        </div>

<br>
        
        <!-- Description, specs and review -->

        <ul class="nav nav-tabs">
          <!-- Use uniqe name for "href" in below anchor tags -->
          <li class="active"><a data-toggle="tab" href="#tab1"><spring:message code="product.description" /></a></li>
          <li><a data-toggle="tab" href="#tab3"><spring:message code="product.comments" /></a></li> 
        </ul>

        <!-- Tab Content -->
        <div class="tab-content">
          <!-- Description -->
          <div id="tab1" class="tab-pane active">
            <h5>${product.name}</h5>
            <p>${product.description}</p>
          </div>

          <!-- Review -->
          <div id="tab3" class="tab-pane">
            <h5><spring:message code="product.comments" /></h5>
            <c:forEach items="${feedbacks}" var="feedback">
            <div class="item-review">
              <h5>${feedback.cartLine.cart.user.name} ${feedback.cartLine.cart.user.surname} - <span class="color">${feedback.cartLine.rating}/5</span></h5>
              <p class="rmeta"><fmt:formatDate pattern="dd-MM-yyyy" value="${feedback.cartLine.cart.paid}" /></p>
              <p>${feedback.feedbackContent}</p>
            </div>
			</c:forEach>
            <hr>
          </div>

        </div>

      </div>