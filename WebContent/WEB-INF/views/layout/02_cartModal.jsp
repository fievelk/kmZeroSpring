<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="modalCart" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-header">
    <button id="modalCart_dismiss" type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4>Carrello</h4>
  </div>
  <div class="modal-body">
	
	<table class="table table-striped tcart" id="tablecart">
          <thead>
            <tr>
              <th>Name</th>
              <th>Quantity</th>
              <th>Price</th>
              <th>Delete</th>
            </tr>
          </thead>
          <tbody id="cartlines">
          </tbody>
        </table>

  </div>
  
  <!-- Gestire i pulsanti per il checkout e per continuare lo shopping -->
  <div class="modal-footer">
    <a href="" class="btn" data-dismiss="modal" aria-hidden="true">Torna allo Shopping</a>
    <a id="checkout" href="${pageContext.request.contextPath}/carts/confirmcart.do" class="btn btn-danger">Vai alla cassa</a>
  </div>
</div>