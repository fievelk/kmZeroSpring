<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script src="${pageContext.request.contextPath}/resources/custom/js/kmzGMaps.js"></script>
        
        
        
<p id="stampadata"></p>
<!-- Importante: per visualizzare correttamente la mappa bisogna modificare i css di bootstrap modificando img { max-width: } da 100% a "none" -->

<div class="items">
  <div class="container">
    <div class="row">

		<!-- Sidebar menu -->    
          <div class="span5 side-menu">
          
		 	<div>
				<label for="startpoint"><strong>Warehouse:</strong></label>
				<p>${warehouseAddress}</p>
<%-- 				<p id="start">${warehouseAddress}</p> --%>
<%-- 				<input type="hidden" id="end" value="${warehouseAddress}">${warehouseAddress} --%>
			</div>
		<input type="submit" value="Calcola percorso" onclick="calcRoute();">
				
		<!-- Waypoints table -->
		
		<table class="table table-striped tcart">
		    <thead>
		    	<tr>
		    		<th></th>
				    <th>ID</th>
				    <th>INDIRIZZO</th>
		    	</tr>
		    </thead>
		    <tbody>	
			<c:forEach items="${requestScope.cartsToDeliver}" var="cart">
			<tr>
				<td><input type="checkbox" name="waypoints" value="${cart.address}"></td>
				<td>${cart.id}</td>
				<td>${cart.address}</td>
			</tr>
			</c:forEach>
			</tbody>
		</table>
		
		<!-- End of Waypoints table -->
		
      </div>
	
		<!-- Main map div -->
	    <div class="span7">

			<div id="googleMap" style="width:500px;height:380px;"></div>

		</div>	


    </div>
  </div>
</div>