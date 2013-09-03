<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="modalCart" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-header">
    <button id="modalCart_dismiss" type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4><spring:message code="cart.cart"/></h4>
  </div>
  <div class="modal-body">
	
	<table class="table table-striped tcart" id="tablecart">
          <thead>
            <tr>
              <th><spring:message code="product.name"/></th>
              <th><spring:message code="product.quantity"/></th>
              <th><spring:message code="cartline.price"/></th>
              <th><spring:message code="common.delete"/></th>
            </tr>
          </thead>
          <tbody id="cartlines">
          </tbody>
        </table>

  </div>
  
  <!-- Gestire i pulsanti per il checkout e per continuare lo shopping -->
  <div class="modal-footer">
  	<a id="emptycart" href="#" class="btn">Svuota il Carrello</a>
    <a href="" class="btn" data-dismiss="modal" aria-hidden="true"><spring:message code="common.backToShopping"/></a>
    <a id="checkout" href="${pageContext.request.contextPath}/carts/confirmcart" class="btn btn-danger"><spring:message code="common.checkout"/></a>
  </div>
</div>