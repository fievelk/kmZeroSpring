<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script src="${pageContext.request.contextPath}/resources/custom/js/jquery.raty.js"></script>

<!-- rating script -->
<script>
$(document).ready(function () {

	$('.starBlocked').raty({ 
			path: '${pageContext.request.contextPath}/resources/custom/img/rating',
			number: function(){
						return $(this).attr('id');
					},
			score: function(){
				return $(this).attr('id');
			},
			hints: ['1', '2', '3', '4', '5'],
			readOnly: true,
			noRatedMsg: "The user rated this cartLine!"
		});
		
});
</script>
<!-- end of rating script -->

<!-- Main content -->
      <div class="span9">
      	<h5 class="title"><spring:message code="seller.receivedOrders"/></h5>
		
		<ul>
		<li>		
			<table class="table table-striped tcart tableFixed">
			
			    <thead>
			    	<tr>
					    <th id="product"><spring:message code="product.product"/></th>
					    <th id="quantity"><spring:message code="product.quantity"/></th>
					    <th id="quantity"><spring:message code="cart.delivery_date"/></th>
					    <th id="price"><spring:message code="product.price"/></th>
					    <th id="rating"><spring:message code="product.rating"/></th>
			    	</tr>
			    </thead>
			    <tbody>	
				<c:forEach items="${requestScope.cartLines}" var="cartLine">
					<tr>
						<td id="productId">${cartLine.product.name}</td>
						<td>${cartLine.quantity}</td>
						<td><fmt:formatDate pattern="dd-MM-yyyy" value="${cartLine.cart.delivery_date}" /></td>
						<td>${cartLine.lineTotal} &euro;</td>
						<c:choose>
							<c:when test="${cartLine.rating == 0}">
							<td><spring:message code="product.notRatedYet"/></td>
							</c:when>
							<c:otherwise>
							<td><div class="starBlocked" id="${cartLine.rating}"></div></td>
							</c:otherwise>
						</c:choose>
					</tr>
					<c:if test="${cartLine.feedback ne null}">
							<tr><td colspan="5"><b><spring:message code="product.feedback"/>:</b> ${cartLine.feedback.feedbackContent}</td></tr>
							</c:if>
				</c:forEach>	
				</tbody>
			</table>
		</li>
		</ul>
			
		
      </div>

