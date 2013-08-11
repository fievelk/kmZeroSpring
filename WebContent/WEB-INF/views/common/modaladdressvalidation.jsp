<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script src="${pageContext.request.contextPath}/resources/custom/js/kmzGMaps.js"></script>

		<div id="modalDialogAddress" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
		  <div class="modal-header">
		    <button id="modalDialogAddress_dismiss" type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h5 class="title">Address Validation test</h5>
		  </div>
		  <div class="modal-body">
			
			<div class="span12">
				
				<div id="googleMap" style="height:200px;"></div>				
				
				<div class="form offset3 span6">
					<form class="form-horizontal" action="" method="POST">
					
					<div class="control-group">
					    <label class="control-label" for="address"><spring:message code="user.address" /></label>
					    
					    <div class="controls">
							<input id="address_autocompleted"/><br />
					    </div>
					    
					    <p id="addressDistanceError"></p>
					
						<div class="controls">
					      <button id="submitIfValidAddress" type="submit" class="btn">Add to cart</button>
					    </div>
					
					</div>
					
					<div class="control-group">
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>