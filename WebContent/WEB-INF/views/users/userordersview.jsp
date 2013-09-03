<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script src="${pageContext.request.contextPath}/resources/custom/js/jquery.raty.js"></script>



<!-- rating script -->
<script>
$(document).ready(function () {
		$('.star').raty({ 
			path: '${pageContext.request.contextPath}/resources/custom/img/rating',
			number: 5,
			width:false,
			hints: ['1', '2', '3', '4', '5'],
			click: function(score) {
				cartlineId=$(this).attr('id');
				$.ajax({
					type:"POST",
				    url:contextPath+"/carts/updateCartLineRating?id=" + cartlineId + "&rating=" + score,
				    success: $(this).raty({
				    	readOnly: true,
				    	number: score,
				    	score: score,
				    	width:false,
				    	noRatedMsg: "<spring:message code='product.alreadyRated' />",
				    	path: '${pageContext.request.contextPath}/resources/custom/img/rating',
				    })
				});
			}
		});
		
		$('.starBlocked').raty({ 
			path: '${pageContext.request.contextPath}/resources/custom/img/rating',
			number: function(){
						return $(this).attr('id');
					},
			hints: ['1', '2', '3', '4', '5'],
			readOnly: true,
			width:false,
			noRatedMsg: "<spring:message code='product.alreadyRated' />"
		});
	
		
		
	$(".submitFeedback").click(function() {
		feedId = $(this).attr('id');
		cartLineId = feedId.split("_").pop();
		feedbackString = ($("#feedText"+ cartLineId).val());
// 		alert($("#feedText"+ cartLineId).val());
		$.ajax({
			type:"POST",
		    url:contextPath+"/carts/createFeedback?id=" + cartLineId,
		    dataType: "text",
		    data: {"feedback" : feedbackString},
		    success: function() {
		    	$("#feedText"+ cartLineId).attr('readonly','readonly');
		    	$("#feed_"+cartLineId).attr('disabled','disabled');
		    },
		});
		return false;
	});		
		
	
	$('.childOrder').toggle();
	$('.parentOrder').click(function(){
	    $(this).children('.childOrder').slideDown("fast"); 
	});
});
</script>
<!-- end of rating script -->

<!-- Main content -->
      <div class="span9">
      	<h5 class="title"><spring:message code="cart.yourCarts" /></h5>
      	
      	<c:forEach items="${requestScope.carts}" var="cart">
 		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12 withBorder">
					<b><spring:message code="cart.paidOn" />:</b> <fmt:formatDate pattern="dd-MM-yyyy" value="${cart.paid}" />
				</div>
			</div>
			<div class="row-fluid">	
				<div class="span12 withBorder">
					<b><spring:message code="cart.address" />:</b> ${cart.address}
				</div>
			</div>
			<div class="row-fluid">	
				<div class="span12 withBorder row">
					<div class="span5 centeredText"><b><spring:message code="product.product"/></b></div>
					<div class="span2 centeredText"><b><spring:message code="product.quantity"/></b></div>
					<div class="span2 centeredText"><b><spring:message code="product.price"/></b></div>
					<div class="span3 centeredText"><b><spring:message code="product.rating"/></b></div>
				</div>
			</div>
			<c:forEach items="${cart.cartLines}" var="cartLine">
			<div class="row-fluid">
				<div class="span12 withBorder coloredDiv parentOrder">
					<div class="span5 centeredText">${cartLine.product.name}</div>
					<div class="span2 centeredText">${cartLine.quantity}</div>
					<div class="span2 centeredText">${cartLine.lineTotal} &euro;</div>
					
					<c:choose>
						<c:when test="${cartLine.rating == 0}">
							<div class="span3 star centeredText" id="${cartLine.id}"></div>
						</c:when>
						<c:otherwise>
							<div class="span3 starBlocked centeredText" id="${cartLine.rating}"></div>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${cartLine.feedback eq null}">
						<div class="withBorder childOrder">
							<div class="span12 centeredText">
							<textarea class="feedbackArea" placeholder="<spring:message code="product.leaveFeedback"/>" id="feedText${cartLine.id}"></textarea>
							<button class="btn overFeedback submitFeedback" type="submit" id="feed_${cartLine.id}"><spring:message code="product.leaveFeedback"/></button>
							</div>
						</div>	
						</c:when>
						<c:otherwise>
						<div class="span12 withBorder childOrder centeredText">
						${cartLine.feedback.feedbackContent}
						</div>
						</c:otherwise>
					</c:choose>
										
				</div>
			</div>
			</c:forEach>
		</div>      	
      	</c:forEach>
      </div>