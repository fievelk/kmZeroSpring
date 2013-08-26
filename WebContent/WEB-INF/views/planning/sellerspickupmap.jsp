<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script src="${pageContext.request.contextPath}/resources/custom/js/kmzGMaps.js"></script>
        
<script>
// $(document).ready(function() {
// 	$('.childOrder').toggle();
// 	$('.parentOrder').click(function(){
// 	    $(this).children('.childOrder').slideToggle("fast"); 
// 	});
// });
</script>        
        
<!-- Main content -->
      <div class="span9">
      	<h5 class="title"><spring:message code="planning.sellerspickup"/></h5>
		<div class="row-fluid">
			<a href="#googleMap"><button class="btn" type="submit" onclick="calcRoute();"><spring:message code="planning.getDirections"/></button></a>
		</div>
		<br />
					<!-- Waypoints table -->
		
 		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12 withBorder">
					<div class="span1"></div>
					<div class="span1 centeredText"><b><spring:message code="cart.id"/></b></div>
					<div class="span3 centeredText"><b><spring:message code="product.name"/></b></div>
					<div class="span1 centeredText"><b><spring:message code="product.quantity"/></b></div>
					<div class="span2 centeredText"><b><spring:message code="cart.delivery_date"/></b></div>
					<div class="span4"><b><spring:message code="seller.company"/></b></div>
				</div>
			</div>
			<c:forEach items="${requestScope.cartLinesToDeliver}" var="cartline">
			<div class="row-fluid">
				<div class="span12 withBorder coloredDiv">
					<div class="span1 centeredText"><input type="checkbox" name="waypoints" value="${cartline.product.seller.address}"></div>
					<div class="span1 centeredText">${cartline.cart.id}</div>
					<div class="span3 centeredText">${cartline.product.name}</div>
					<div class="span1 centeredText">${cartline.quantity}</div>
					<div class="span2 centeredText"><fmt:formatDate pattern="dd-MM-yyyy" value="${cartline.cart.delivery_date}" /></div>
					<div class="span4">${cartline.product.seller.company}</div>
				</div>
			</div>		
			
			</c:forEach>
		</div>

		<!-- End of Waypoints table -->
		<br />
		<!-- Main map div -->
	    <div class="span9">
			<div class="mapbox" id="googleMap" style="height:380px;"></div>
			<div id="directions-panel"></div>
		</div>	
		
		
		
      </div>