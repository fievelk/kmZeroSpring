var directionsDisplayModal;
var directionsServiceModal = new google.maps.DirectionsService();
var mapModal;
var geocoderModal = new google.maps.Geocoder();

/* Chiamata AJAX per indirizzo del centro di distribuzione, preso dalla classe Warehouse */

google.maps.event.addDomListener(window, 'load', function(){
	
	$.ajax({
		type:"POST",
	    url:contextPath+"/products/findWarehouseAddress",  
	    success:function(data){
	    			warehouseAddressModal = data;
	    			geocoderModal.geocode({'address': warehouseAddressModal }, function(results) {
	    				warehouse = results[0].geometry.location;
	    				initializeModal();
	    			});
	    }
	});
});

function initializeModal() {
	
//	var warehouse = new google.maps.LatLng(42.348395, 14.108963);

	/* Address Autocompletion */
	
	var inputModal = (document.getElementById('address_autocompletedModal'));
	var autocomplete_optionsModal = {
			  componentRestrictions: {country: 'it'} // Nel caso dell'iscrizione si potrebbero eliminare le restrizioni
			};

	
	var autocompleteModal = new google.maps.places.Autocomplete(inputModal, autocomplete_optionsModal);

	google.maps.event.addListener(autocompleteModal, 'place_changed', function() {
        var placeModal = autocompleteModal.getPlace();
        console.log(placeModal.address_components);
    }); 
	
	/* End of address Autocompletion */
	
	/* Map */
	
	
	directionsDisplayModal = new google.maps.DirectionsRenderer();
	
	// Enable the visual refresh
	google.maps.visualRefresh = true;
	
	var mapModalOptions = {
	  center: warehouse,
	  zoom:10,
	  disableDefaultUI: true,
	  mapTypeId:google.maps.MapTypeId.ROADMAP
	  };
	
	var map_canvasModal = document.getElementById("googleMapModal");
	
	mapModal = new google.maps.Map(map_canvasModal, mapModalOptions);
	directionsDisplayModal.setMap(mapModal);
	
	
	autocompleteModal.bindTo('bounds', mapModal);

	var infowindow = new google.maps.InfoWindow();
	var marker = new google.maps.Marker({
    map: mapModal,
  });
	
	  google.maps.event.addListener(autocompleteModal, 'place_changed', function() {
		    infowindow.close();
		    marker.setVisible(false);
		    marker.setAnimation(google.maps.Animation.BOUNCE);
		    inputModal.className = '';
		    var placeModal = autocompleteModal.getPlace();
		    if (!placeModal.geometry) {
		      // Inform the user that the place was not found and return.
		      inputModal.className = 'notfound';
		      return;
		    }

		    // If the place has a geometry, then present it on a map.
		    if (placeModal.geometry.viewport) {
		      mapModal.fitBounds(placeModal.geometry.viewport);
		    } else {
		      mapModal.setCenter(placeModal.geometry.location);
		      mapModal.setZoom(17);  // Why 17? Because it looks good.
		    }
		    marker.setIcon(/** @type {google.maps.Icon} */({
		      url: placeModal.icon,
		      size: new google.maps.Size(71, 71),
		      origin: new google.maps.Point(0, 0),
		      anchor: new google.maps.Point(17, 34),
		      scaledSize: new google.maps.Size(35, 35)
		    }));
		    marker.setPosition(placeModal.geometry.location);
		    marker.setVisible(true);
		    marker.setAnimation(google.maps.Animation.BOUNCE);

		    var address = '';
		    if (placeModal.addrplaceModalomponents) {
		      address = [
		        (placeModal.address_components[1] && placeModal.address_components[1].short_name || ''),
		        (placeModal.address_components[3] && placeModal.address_components[3].short_name || ''),
		        (placeModal.address_components[4] && placeModal.address_components[4].short_name || ''),
		      ].join(', ');
		    }

		    infowindow.setContent('<div class="googleMapInfowindow"><strong>' + placeModal.name + '</strong><br>' + address);
		    infowindow.open(mapModal, marker);
		  });

		  /* CALCOLO DISTANZA */

		  google.maps.event.addListener(autocompleteModal, 'place_changed', function() {
		    var start = warehouse;
		    var end = document.getElementById("address_autocompletedModal").value;
		    var request = {
		      origin: start,
		      destination: end,
		      travelMode: google.maps.TravelMode.DRIVING
		    };
		         

		    directionsServiceModal.route(request, function(response, status) {
		        marker.setAnimation(google.maps.Animation.BOUNCE);
		        
		      if (status == google.maps.DirectionsStatus.OK) {  
		    	  /* rimuove eventuali errori o immagini di ricerche precedenti */
		    	  if (document.getElementById("successimg")) {
		    		  document.getElementById("successimg").parentNode.removeChild(document.getElementById("successimg"));
		    	  }
		    	  if (document.getElementById("addressDistanceErrorModal")) {
		    		  document.getElementById("addressDistanceErrorModal").innerHTML="";
		    	  }
	    	  
		    	  /* validazione della distanza */
		        var distanceFromStart = response.routes[0].legs[0].distance.value;
		        var distanceFromStart = (distanceFromStart / 1000).toFixed(2); // conversion from meters to kilometers
		        
		        if (distanceFromStart < 50) {
		        	if (document.getElementById("submitIfValidAddressModal")) {
		        		document.getElementById("submitIfValidAddressModal").disabled=false;
		        	}
		        	var okpng = "/resources/custom/img/ok.png";
		        	var imgtag = document.createElement("img");
		        	imgtag.setAttribute("id", "successimgModal");
		        	imgtag.setAttribute("src",contextPath+okpng);
		        	$("#address_autocompletedModal").after(imgtag);
		        } else {
		        	if (document.getElementById("submitIfValidAddressModal")) {
		        		document.getElementById("submitIfValidAddressModal").disabled=true;
		        	}
//		        	document.getElementById("addressDistanceError").innerHTML="<spring:message code='error.addressDistance'/>";
		        	document.getElementById("addressDistanceErrorModal").innerHTML="L'indirizzo risulta al di fuori del range di consegna di KmZero.";
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
	directionsServiceModal.route(request, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplayModal.setDirections(response);
		}
	});
}
	
//google.maps.event.addDomListener(window, 'load', initializeModal);