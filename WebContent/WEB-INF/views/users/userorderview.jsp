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
				    	noRatedMsg: "You already rated this cartLine!",
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
			noRatedMsg: "You already rated this cartLine!"
		});
		
});
</script>
<!-- end of rating script -->

<!-- Main content -->
      <div class="span9">
      	<h5 class="title">TITOLO I TUOI CARRELLI</h5>
		
		<c:forEach items="${requestScope.carts}" var="cart">
		<ul>
		<li>		
			<b>Carrello #${cart.id}, pagato il <fmt:formatDate pattern="dd-MM-yyyy" value="${cart.paid}" />, destinazione: ${cart.address} </b>
			<table class="table table-striped tcart tableFixed">
			
			    <thead>
			    	<tr>
					    <th id="product">Prodotto</th>
					    <th id="quantity">Quantità</th>
					    <th id="price">Prezzo</th>
					    <th id="rating">Rating</th>
			    	</tr>
			    </thead>
			    <tbody>	
			    <c:forEach items="${cart.cartLines}" var="cartLine">
					<tr>
						<td id="productId">${cartLine.product.name}</td>
						<td>${cartLine.quantity}</td>
						<td>${cartLine.lineTotal} &euro;</td>
						<c:choose>
							<c:when test="${cartLine.rating == 0}">
							<td width=""><div class="star" id="${cartLine.id}" style="align:center"></div></td>
							</c:when>
							<c:otherwise>
							<td><div class="starBlocked" id="${cartLine.rating}"></div></td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>				
				</tbody>
			</table>
		</li>
		</ul>
			
		</c:forEach>
      </div>

