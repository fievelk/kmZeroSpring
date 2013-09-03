<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
	$(document).ready(checkoutCart());
</script>


			
			<div class="span9">
				<h5 class="title"><spring:message code="cart.cart" /></h5>
				<div class="scontact well">
					<form:form modelAttribute="cart" cssClass="form-horizontal" action="${requestScope.action}" method="POST">
					<form:hidden path="id"/>
					<%-- <p><spring:message code="user.name"/> : <form:label id="name" path="name">${cart.name}</form:label></p> 
					<p><spring:message code="user.surname"/> : <form:label id="surname" path="surname">${cart.surname}</form:label></p>
					<p><spring:message code="cart.address"/> : <form:label id="addresscart" path="address">${cart.address}</form:label></p>
					<p><spring:message code="cart.deliveryDate"/> : <form:label id="deliveryDate" path="deliveryDate"><fmt:formatDate pattern="dd-MM-yyyy" value="${cart.deliveryDate}"/></form:label></p> --%>
					<p><spring:message code="user.name"/> : ${cart.name}</p> 
					<p><spring:message code="user.surname"/> : ${cart.surname}</p>
					<p><spring:message code="cart.address"/> : ${cart.address}</p>
					<p><spring:message code="cart.deliveryDate"/> : <fmt:formatDate pattern="dd-MM-yyyy" value="${cart.deliveryDate}"/></p>
					<table class="table table-striped tcart">
			          <thead>
			            <tr>
			              <th><spring:message code="product.name"/></th>
			              <th><spring:message code="product.quantity"/></th>
			              <th><spring:message code="cartline.price"/></th>
			            </tr>
			          </thead>
			          	<tbody id="cartlinesconfirmed">
			          	</tbody>
					</table>
					
					<input type="image" src="${pageContext.request.contextPath}/resources/custom/img/paga-adesso.png" border="0" name="submit" alt="PayPal - Il metodo rapido, affidabile e innovativo per pagare e farsi pagare.">
					
					<input type="hidden" name="cmd" value="_xclick">
					<input type="hidden" name="return" value="${pageContext.request.contextPath}/carts/paid">
					<input type="hidden" name="business" value="km_seller@email.it">
					<input type="hidden" name="currency_code" value="EUR">
					<input type="hidden" name="item_name" value="Km Zero & C">
					<input type="hidden" id="totpaypal"  name="amount">
					<input type="hidden" name="custom" value="${cart.id}">
					</form:form>
				</div>
			</div>
