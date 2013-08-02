<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAxHFavr74ns3tBVjamE2MSxUszOe5gLyU&sensor=false"></script>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=false"></script>
        
<script>
var directionsDisplay;
var directionsService = new google.maps.DirectionsService();
var map;

function initialize() {
		
	var chieti = new google.maps.LatLng(42.348395, 14.108963);
	
	/* Input text Autocompletion */
	
/* 	var defaultBounds = new google.maps.LatLngBounds(
  		new google.maps.LatLng(47.100045, 6.348610),
  		new google.maps.LatLng(36.279707,18.977966)); */

	
	var input = (document.getElementById('searchTextField'));
	var autocomplete_options = {
			  //bounds: defaultBounds,
			  componentRestrictions: {country: 'it'}
			};

	
	var autocomplete = new google.maps.places.Autocomplete(input, autocomplete_options);

	google.maps.event.addListener(autocomplete, 'place_changed', function() {
        var place = autocomplete.getPlace();
        console.log(place.address_components);
    }); 
	
	/* End of Autocompletion */
	
	/* Map */
	
	
	directionsDisplay = new google.maps.DirectionsRenderer();
	
	var mapOptions = {
	  center: chieti,
	  zoom:9,
	  mapTypeId:google.maps.MapTypeId.ROADMAP
	  };
	
	var map_canvas = document.getElementById("googleMap");
	
	map = new google.maps.Map(map_canvas, mapOptions);
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
          <input type="text" id="searchTextField">

		  <p id="outputadd"></p>
          
          <script>
          function functiona() {
          var buttalo = document.getElementById("searchTextField").value;
          document.getElementById("outputadd").innerHTML=buttalo;
          }
          </script>
          
          <input type="submit" onclick="functiona()" value="prova">
          
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