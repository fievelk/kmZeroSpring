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
          <input type="text" id="address_autocompleted">
		
			<p id="addressDistanceError"></p>
		  <p id="outputadd"></p>
          <p id="stampami"></p>
                    <script>
          function stampalo() {
          //var coordinates = '${warehouse}';
  		  //var warehouse = new google.maps.LatLng(coordinates);
          document.getElementById("stampami").innerHTML=coordinates;
          }
          </script>
          
          <input type="button" onclick="stampalo()" value="provacoordinatewarehouse">
          
          
          
          
          <script>
          function functiona() {
          var buttalo = document.getElementById("address_autocompleted").value;
          document.getElementById("outputadd").innerHTML=buttalo;
          }
          </script>
          
          <input type="submit" onclick="functiona()" value="prova">
          
		 	<div><p id="start">${warehouseAddress}</p>
				<label for="startpoint"><strong>Start point:</strong></label>
<!-- 				<select id="start"> -->
<%-- 				  <option value="${warehouseAddress}">Warehouse</option> --%>
<%-- 				  <option value="${requestScope.warehouse}">Warehouse</option> --%>
<!-- 				  <option value="chieti, it">Chieti</option> -->
<!-- 				  <option value="rome, it">Roma</option> -->
<!-- 				</select> -->
			</div>
			<div>
				<label for="startpoint"><strong>End point:</strong></label>
				<select id="end">
				  <option value="Via XIII Ottobre, Tagliacozzo, italy">Tagliacozzo</option>
				  <option value="via Vetoio, L'Aquila, italy">Università de L'Aquila</option>
				  <option value="chieti, it">Chieti</option>
				  <option value="milan, italy">Milano</option>
				  <option value="pescara, italy">Pescara</option>
				  <!-- <option value="(42.348395, 14.108963)">Chieti (con coordinate)</option> -->
				</select>
			</div>
				
		<!-- Waypoints table -->
		
		<table class="table table-striped tcart">
		    <thead>
		    	<tr>
		    		<th></th>
				    <th>ID</th>
				    <th>NOME</th>
				    <th>INDIRIZZO</th>
		    	</tr>
		    </thead>
		    <tbody>	
			<c:forEach items="${requestScope.users}" var="user">
			<tr>
				<td><input type="checkbox" name="waypoints" value="${user.address}"></td>
				<td>${user.id}</td>
				<td>${user.name}</td>
				<td>${user.address}</td>
			</tr>
			</c:forEach>
			</tbody>
		</table>
		
		<!-- End of Waypoints table -->
		
		<input type="submit" value="Calcola percorso" onclick="calcRoute();">
      
      </div>
	
		<!-- Main map div -->
	    <div class="span7">

			<div id="googleMap" style="width:500px;height:380px;"></div>

		</div>	


    </div>
  </div>
</div>