<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
				    url:contextPath+"/carts/updateCartLineRating?id=" + cartlineId + "&r=" + score,
				    success: $(this).raty({
				    	readOnly: true,
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

<style>
 .tableFixed { table-layout: fixed; }
 .tableFixed th, table td { overflow: hidden; }
 .tableFixed th{ text-align: center; }
 .tableFixed td{ text-align: center; }
 .tableFixed .star{ margin-left:auto; margin-right:auto; }
 .tableFixed .starBlocked{ margin-left:auto; margin-right:auto; }
 .tableFixed th#product {width: 35%; text-align:left;}
 .tableFixed th#quantity {width: 20%;}
 .tableFixed th#price {width: 20%;}
 .tableFixed th#rating {width: 25%;}
 .tableFixed td#productId {text-align:left;}
</style>


<!-- Main content -->
      <div class="span9">
      	<h5 class="title">TITOLO I TUOI CARRELLI</h5>
		
		<c:forEach items="${requestScope.carts}" var="cart">
		<ul>
		<li>		
			<b>Carrello #${cart.id}, pagato il ${cart.paid}, destinazione: ${cart.address} </b>
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

