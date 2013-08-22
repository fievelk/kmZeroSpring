<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script src="${pageContext.request.contextPath}/resources/custom/js/kmzGMaps.js"></script>
        
<script>
$(document).ready(function() {
	$('.childOrder').toggle();
	$('.parentOrder').click(function(){
	    $(this).children('.childOrder').slideToggle("fast"); 
	});
});
</script>        
        
<!-- Main content -->
      <div class="span9">
      	<h5 class="title"><spring:message code="planning.usersdelivery"/></h5>
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
					<div class="span3 centeredText"><b><spring:message code="cart.delivery_date"/></b></div>
					<div class="span7"><b><spring:message code="cart.address"/></b></div>
				</div>
			</div>
			<c:forEach items="${requestScope.cartsToDeliver}" var="cart">
			<div class="row-fluid parentOrder">
				<div class="span12 withBorder coloredDiv">
					<div class="span1"><input type="checkbox" name="waypoints" value="${cart.address}"></div>
					<div class="span1 centeredText">${cart.id}</div>
					<div class="span3">${cart.delivery_date}</div>
					<div class="span7">${cart.address}</div>
				</div>
				<c:forEach items="${cart.cartLines}" var="cartLine">
					<div class="row-fluid childOrder">
						<div class="span12 withBorder">
							<div class="span2"></div>
							<div class="span1 centeredText">Qt. <b>${cartLine.quantity}</b></div>
							<div class="span9">${cartLine.product.name}</div>
						</div>
					</div>
				</c:forEach>
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