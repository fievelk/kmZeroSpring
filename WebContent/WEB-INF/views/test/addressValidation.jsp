<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script src="${pageContext.request.contextPath}/resources/custom/js/kmzGMapsModal.js"></script>

			<!-- Main content -->
			
			<div class="span12">
				<h5 class="title">Address Validation test</h5>
				<div id="googleMapModal" style="height:200px;"></div>				
				
				<div class="form offset3 span6">
					<form class="form-horizontal" action="" method="POST">
					
					<div class="control-group">
					    <label class="control-label" for="address"><spring:message code="user.address" /></label>
					    
					    <div class="controls">
							<input id="address_autocompletedModal"/><br />
					    </div>
					    
					    <p id="addressDistanceErrorModal"></p>
					
						<div class="controls">
					      <button id="submitIfValidAddressModal" type="submit" class="btn">Add to cart</button>
					    </div>
					
					</div>
					
					<div class="control-group">
					</div>
					</form>
				</div>
			</div>