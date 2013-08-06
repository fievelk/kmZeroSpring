<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<script>
$(function() {
	$( "#datepicker" ).datepicker({
		defaultDate: "1/1/1960",
		changeMonth: true,
		changeYear: true,
		dateFormat: 'dd/mm/yy',
		yearRange: '1920:2012',
		showAnim: "slideDown"
		/* minDate: (new Date(1920, 1, 1)),
		maxDate: (new Date(2010, 1, 1)) */
	});
});
</script>

<!-- Google Maps API scripts-->

<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=false"></script>
       
<script>
var map;

function initialize() {
		
	/* Coordinate del centro di distribuzione, prese dalla classe Warehouse */
	var warehouse = new google.maps.LatLng('${wareLat}','${wareLon}');
	
	/* Input text Autocompletion */
	
	var input = (document.getElementById('address_autocompleted'));
	var autocomplete_options = {
			  componentRestrictions: {country: 'it'} // si potrebbe eliminare la restrizione
			};

	
	var autocomplete = new google.maps.places.Autocomplete(input, autocomplete_options);

	//autocomplete.bindTo('bounds', map);


	google.maps.event.addListener(autocomplete, 'place_changed', function() {
        var place = autocomplete.getPlace();
        console.log(place.address_components);
    }); 
	
	/* End of Autocompletion */
	
	/* Map */
	
	// Enable the visual refresh
	google.maps.visualRefresh = true;
	
	
	var mapOptions = {
	  center: warehouse,
	  zoom:9,
	  disableDefaultUI: true,
	  mapTypeId:google.maps.MapTypeId.ROADMAP
	  };
	
	var map_canvas = document.getElementById("googleMap");
	
	map = new google.maps.Map(map_canvas, mapOptions);
	autocomplete.bindTo('bounds', map);

	var infowindow = new google.maps.InfoWindow();
	var marker = new google.maps.Marker({
    map: map,
    //animation: google.maps.Animation.BOUNCE,
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

  var directionsService = new google.maps.DirectionsService();

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
        var distanceFromStart = response.routes[0].legs[0].distance.value;
        var distanceFromStart = (distanceFromStart / 1000).toFixed(2); // conversion from meters to kilometers
        
        if (distanceFromStart < 50) {
        	document.getElementById("submitbutton").disabled=false;
            document.getElementById("addressDistanceError").innerHTML="<img src='${pageContext.request.contextPath}/resources/custom/img/ok.png'/>";
        } else {
        	document.getElementById("submitbutton").disabled=true;
        	document.getElementById("addressDistanceError").innerHTML="<spring:message code='error.addressDistance'/>";
        }
      } 
    });

  });
  
  /* Fine CALCOLO DISTANZA */
}

google.maps.event.addDomListener(window, 'load', initialize);
</script>

<!-- End of Google Maps API scripts -->

<div class="items">
	<div class="container">
		<div class="row">

	    	<div class="span2 side-menu">
	
				<!-- Sidebar navigation -->
				<h5 class="title">Menu</h5>
				<!-- Sidebar navigation -->
				  <nav>
				    <ul id="navi">
				      <li><a href="myaccount.html">Gestione Ordini</a></li>
				      <li><a href="wish-list.html">Storico Ordini</a></li>
				      <li><a href="order-history.html">Gestione Utenti</a></li>
				      <li><a href="edit-profile.html">Gestione Venditori</a></li>
				    </ul>
				  </nav>
			</div>
			
			<!-- Main content -->
			
			<div class="span5">
				<h5 class="title">
					<c:choose>
			      		<c:when test="${requestScope.upgrade}">
							<spring:message code="seller.upgrade"/>
			      		</c:when>
			      		<c:when test="${requestScope.update}">
			      			<spring:message code="seller.edit"/>
			   			</c:when>
			      	</c:choose>	
				</h5>
				<div class="form">
				
					<form:form modelAttribute="seller" cssClass="form-horizontal" action="${pageContext.request.contextPath}${requestScope.action}" method="POST">
					<div class="span4">
						<form:hidden path="id"/>
						<div class="control-group">
						    <label class="control-label" for="name"><spring:message code="user.name"/></label>
						    <div class="controls">
						    	<form:input id="name" path="name"/>
						    	<form:errors path="name"/>
						    </div>
						</div>
						<div class="control-group">
						    <label class="control-label" for="surname"><spring:message code="user.surname"/></label>
						    <div class="controls">
						    	<form:input id="surname" path="surname"/>
						    	<form:errors path="surname"/>
						    </div>
						</div>
						<div class="control-group">
						    <label class="control-label" for="email"><spring:message code="user.email"/></label>
						    <div class="controls">
						    	<form:input id="email" path="email"/>
						    	<form:errors path="email"/>
						    </div>
						</div>
						<div class="control-group">
						    <label class="control-label" for="date_of_birth"><spring:message code="user.date_of_birth"/></label>
							<div class="controls">
								<form:input id="datepicker" path="date_of_birth"/>
								<form:errors path="date_of_birth"/>
							</div>
						</div>
						
						<div class="control-group">
						    <label class="control-label" for="address"><spring:message code="user.address"/></label>
						    <div class="controls">
								<form:input id="address_autocompleted" path="address"/><br />
								<form:errors path="address"/>
								<p id="addressDistanceError"></p>
						    </div>
						</div>
						
						<c:choose>
							<c:when test="${requestScope.upgrade}">
								<div class="control-group">
								    <label class="control-label" for="p_iva"><spring:message code="seller.p_iva"/></label>
								    <div class="controls">
										<form:input id="p_iva" path="p_iva"/>
										<form:errors path="p_iva"/>
								    </div>
								</div>
								<div class="control-group">
								    <label class="control-label" for="cod_fisc"><spring:message code="seller.cod_fisc"/></label>
								    <div class="controls">
										<form:input id="cod_fisc" path="cod_fisc"/>
										<form:errors path="cod_fisc"/>
								    </div>
								</div>
								<div class="control-group">
								    <label class="control-label" for="company"><spring:message code="seller.company"/></label>
								    <div class="controls">
										<form:input id="company" path="company"/>
										<form:errors path="company"/>
								    </div>
								</div>
							</c:when>
						</c:choose>
						
						<div class="control-group">
						    <label class="control-label" for="url"><spring:message code="seller.url"/></label>
						    <div class="controls">
								<form:input id="url" path="url"/>
								<form:errors path="url"/>
						    </div>
						</div>
						<div class="control-group">
						    <label class="control-label" for="phone"><spring:message code="seller.phone"/></label>
						    <div class="controls">
								<form:input id="phone" path="phone"/>
								<form:errors path="phone"/>
						    </div>
						</div>
						
						<div class="control-group">
						    <div class="controls">
						      <button type="submit" id="submitbutton" class="btn">
						      	<spring:message code="common.submit"/>
						      </button>
							</div>
						</div>
					</div>
					</form:form>
					<c:if test="${requestScope.update eq 'true'}">
					<div class="span4 sellerImages">
						<div class="row-fluid">
							<a class="btn" href="#modalWindow" role="button" data-toggle="modal" onclick="createModalWindow('addImages','selr','${seller.id}',null,null)">Add Images...</a>
						</div>
						<div id="km0Images">
					  	<c:forEach var="image" items="${seller.images}">
					  			<span id="image_${image.id}">
						       		<img src="${pageContext.request.contextPath}/selr/image/${image.id}/${image.name}" alt="${image.name}" />
						       		<a href="#modalWindow" class="icon-edit" role="button" data-toggle="modal" onclick="createModalWindow('updateImage','selr','${seller.id}','image','${image.id}')" ></a>	
						       		<a href="#modalWindow" class="icon-remove"  role="button" data-toggle="modal" onclick="createModalWindow('deleteImage','selr','${seller.id}','image','${image.id}')"></a>
					       		</span>	
				    	</c:forEach>
				    	</div>
			      	</div>	
			   		</c:if>
				</div>
			</div>
			
	    	<div class="span5">
	
				<h5 class="title"><spring:message code="common.map" /></h5>
				<div id="googleMap" style="width:500px;height:380px;"></div>

			</div>			
			
		</div>
	</div>
</div>