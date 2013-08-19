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

<div class="items">
	<div class="container">
		<div class="row">

	    	<div class="span3 side-menu">
	
				<!-- Sidebar navigation -->
				<h5 class="title">Menu</h5>
				<!-- Sidebar navigation -->
				  <nav>
				    <ul id="navi">
				      <li><a href="myaccount.html">Gestione Ordini</a></li>
				      <li><a href="wish-list.html">Storico Ordini</a></li>
				      <li><a href="order-history.html">Gestione Utenti</a></li>
				      <li><a href="edit-profile.html">Gestione Venditori</a></li>
				    </ul>
				  </nav>
			</div>
			
			<!-- Main content -->
			
			<div class="span9">
				<h5 class="title">Carrello</h5>
				<div class="form form-small">
					<form:form modelAttribute="cart" cssClass="form-horizontal" action="${requestScope.action}" method="POST">
					<form:hidden path="id"/>
					<div class="control-group">
					    <label class="control-label" for="name"><spring:message code="user.name"/></label>
					    <div class="controls">
					    	<form:label id="name" path="name">${cart.name}</form:label>
					    </div>
					</div>
					<div class="control-group">
						<label class="control-label" for="surname"><spring:message code="user.surname"/></label>
					    <div class="controls">
					    	<form:label id="surname" path="surname">${cart.surname}</form:label>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label" for="address"><spring:message code="cart.address"/></label>
					    <div class="controls">
					    	<form:label id="addresscart" path="address">${cart.address}</form:label>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label" for="delivery_date"><spring:message code="cart.delivery_date"/></label>
					    <div class="controls">
					    	<form:label id="delivery_date" path="delivery_date"><fmt:formatDate pattern="dd-MM-yyyy" value="${cart.delivery_date}"/></form:label>
					    </div>
					</div>
					<table class="table table-striped tcart">
			          <thead>
			            <tr>
			              <th>Name</th>
			              <th>Quantity</th>
			              <th>Price</th>
			            </tr>
			          </thead>
			          	<tbody id="cartlinesconfirmed">
			          	</tbody>
					</table>
					<div class="control-group">
					    <div class="controls">
						  <input type="hidden" name="cmd" value="_xclick">
						  <input type="hidden" name="return" value="${pageContext.request.contextPath}/carts/payed.do">
						  <input type="hidden" name="business" value="km_seller@email.it">
						  <input type="hidden" name="currency_code" value="EUR">
						  <input type="hidden" name="item_name" value="Km Zero & C">
						  <input id="totpaypal" type="hidden" name="amount">
						  <input type="hidden" name="custom" value="${cart.id}">
						  <input type="image" src="${pageContext.request.contextPath}/resources/custom/img/paga-adesso.png" border="0" name="submit" alt="PayPal - Il metodo rapido, affidabile e innovativo per pagare e farsi pagare.">
						  <!-- <input type="image" src="https://www.sandbox.paypal.com/it_IT/IT/i/btn/btn_buynowCC_LG.gif" border="0" name="submit" alt="PayPal - Il metodo rapido, affidabile e innovativo per pagare e farsi pagare."> -->
					    </div>
					</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>