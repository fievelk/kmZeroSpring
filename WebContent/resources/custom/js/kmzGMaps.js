var directionsDisplay;
var directionsService = new google.maps.DirectionsService();
var map;
var geocoder = new google.maps.Geocoder();

/* Chiamata AJAX per indirizzo del centro di distribuzione, preso dalla classe Warehouse */

google.maps.event.addDomListener(window, 'load', function(){
	
	$.ajax({
		type:"POST",
	    url:contextPath+"/products/findWarehouseAddress",  
	    success:function(data){
	    			warehouseAddress = data;
	    			geocoder.geocode({'address': warehouseAddress }, function(results) {
	    				warehouse = results[0].geometry.location;
	    				initialize();
	    			});
	    }
	});
});

function initialize() {
	
//	var warehouse = new google.maps.LatLng(42.348395, 14.108963);

	/* Address Autocompletion */
	
	var input = (document.getElementById('address_autocompleted'));
	var autocomplete_options = {
			  componentRestrictions: {country: 'it'} // Nel caso dell'iscrizione si potrebbero eliminare le restrizioni
			};

	
	var autocomplete = new google.maps.places.Autocomplete(input, autocomplete_options);

	google.maps.event.addListener(autocomplete, 'place_changed', function() {
        var place = autocomplete.getPlace();
        console.log(place.address_components);
    }); 
	
	/* End of address Autocompletion */
	
	/* Map */
	
	
	directionsDisplay = new google.maps.DirectionsRenderer();
	
	// Enable the visual refresh
	google.maps.visualRefresh = true;
	
	var mapOptions = {
	  center: warehouse,
	  zoom:10,
	  disableDefaultUI: true,
	  mapTypeId:google.maps.MapTypeId.ROADMAP
	  };
	
	var map_canvas = document.getElementById("googleMap");
	
	map = new google.maps.Map(map_canvas, mapOptions);
	directionsDisplay.setMap(map);
	
	
	autocomplete.bindTo('bounds', map);

	var infowindow = new google.maps.InfoWindow();
	var marker = new google.maps.Marker({
    map: map,
  });
	
	  google.maps.event.addListener(autocomplete, 'place_changed', function() {
		    infowindow.close();
		    marker.setVisible(false);
		    marker.setAnimation(google.maps.Animation.BOUNCE);
		    input.className = '';
		    var place = autocomplete.getPlace();
		    if (!place.geometry) {
		      // Inform the user that the place was not found and return.
		      input.className = 'notfound';
		      return;
		    }

		    // If the place has a geometry, then present it on a map.
		    if (place.geometry.viewport) {
		      map.fitBounds(place.geometry.viewport);
		    } else {
		      map.setCenter(place.geometry.location);
		      map.setZoom(17);  // Why 17? Because it looks good.
		    }
		    marker.setIcon(/** @type {google.maps.Icon} */({
		      url: place.icon,
		      size: new google.maps.Size(71, 71),
		      origin: new google.maps.Point(0, 0),
		      anchor: new google.maps.Point(17, 34),
		      scaledSize: new google.maps.Size(35, 35)
		    }));
		    marker.setPosition(place.geometry.location);
		    marker.setVisible(true);
		    marker.setAnimation(google.maps.Animation.BOUNCE);

		    var address = '';
		    if (place.address_components) {
		      address = [
		        (place.address_components[1] && place.address_components[1].short_name || ''),
		        (place.address_components[3] && place.address_components[3].short_name || ''),
		        (place.address_components[4] && place.address_components[4].short_name || ''),
		      ].join(', ');
		    }

		    infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + address);
		    infowindow.open(map, marker);
		  });

		  /* CALCOLO DISTANZA */

		  google.maps.event.addListener(autocomplete, 'place_changed', function() {
		    var start = warehouse;
		    var end = document.getElementById("address_autocompleted").value;
		    var request = {
		      origin: start,
		      destination: end,
		      travelMode: google.maps.TravelMode.DRIVING
		    };
		         

		    directionsService.route(request, function(response, status) {
		        marker.setAnimation(google.maps.Animation.BOUNCE);
		        
		      if (status == google.maps.DirectionsStatus.OK) {  
		    	  /* rimuove eventuali errori o immagini di ricerche precedenti */
		    	  if (document.getElementById("successimg")) {
		    		  document.getElementById("successimg").parentNode.removeChild(document.getElementById("successimg"));
		    	  }
		    	  if (document.getElementById("addressDistanceError")) {
		    		  document.getElementById("addressDistanceError").innerHTML="";
		    	  }
	    	  
		    	  /* validazione della distanza */
		        var distanceFromStart = response.routes[0].legs[0].distance.value;
		        var distanceFromStart = (distanceFromStart / 1000).toFixed(2); // conversion from meters to kilometers
		        
		        if (distanceFromStart < 50) {
		        	if (document.getElementById("submitIfValidAddress")) {
		        		document.getElementById("submitIfValidAddress").disabled=false;
		        	}
		        	var okpng = "/resources/custom/img/ok.png";
		        	var imgtag = document.createElement("img");
		        	imgtag.setAttribute("id", "successimg");
		        	imgtag.setAttribute("src",contextPath+okpng);
		        	$("#address_autocompleted").after(imgtag);
		        } else {
		        	if (document.getElementById("submitIfValidAddress")) {
		        		document.getElementById("submitIfValidAddress").disabled=true;
		        	}
//		        	document.getElementById("addressDistanceError").innerHTML="<spring:message code='error.addressDistance'/>";
		        	document.getElementById("addressDistanceError").innerHTML="L'indirizzo risulta al di fuori del range di consegna di KmZero.";
		        }
		      } 
		    });

		  });
		  
		  /* Fine CALCOLO DISTANZA */
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
	
//google.maps.event.addDomListener(window, 'load', initialize);