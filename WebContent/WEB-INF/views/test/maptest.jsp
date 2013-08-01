<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAxHFavr74ns3tBVjamE2MSxUszOe5gLyU&sensor=false">
</script>

<script>
var directionsDisplay;
var directionsService = new google.maps.DirectionsService();
var map;

function initialize() {
		
	directionsDisplay = new google.maps.DirectionsRenderer();
	
	var chieti = new google.maps.LatLng(42.348395, 14.108963);
	
	var mapOptions = {
	  center: chieti,
	  zoom:9,
	  mapTypeId:google.maps.MapTypeId.ROADMAP
	  };
	
	map = new google.maps.Map(document.getElementById("googleMap"), mapOptions);
	directionsDisplay.setMap(map);
	
}

function calcRoute() {
	var start = document.getElementById("start").value;
	var end = document.getElementById("end").value;
	var waypts = [];
	

	var checkboxArray = document.getElementsByName("waypoints");
	for (var i = 0; i < checkboxArray.length; i++) {
		if (checkboxArray[i].checked) {
			waypts.push({
				location:checkboxArray[i].value,
				stopover:true	
			});
		}
	}
	
	var request = {
			origin:start,
			destination:end,
			waypoints: waypts,
			optimizeWaypoints: true,
			travelMode: google.maps.TravelMode.DRIVING
	};
	directionsService.route(request, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
		}
	});
}

google.maps.event.addDomListener(window, 'load', initialize);
</script>

<!-- Importante: per visualizzare correttamente la mappa bisogna modificare i css di bootstrap modificando img { max-width: } da 100% a "none" -->

<div class="items">
  <div class="container">
    <div class="row">

		<!-- Sidebar menu -->    
          <div class="span5 side-menu">
          
		 	<div>
				<label for="startpoint"><strong>Start point:</strong></label>
				<select id="start">
				  <option value="l'aquila, it">L'Aquila</option>
				  <option value="chieti, it">Chieti</option>
				  <option value="rome, it">Roma</option>
				</select>
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