<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var del = "${requestScope.delete}"; 
		if (del == "true" ) {
			$(":input[type='text']").each(function () { $(this).attr('readonly',true); });				
		}		
	});
	
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

<!-- Address autocompletion scripts-->

<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=false"></script>
       
<script>
var map;

function initialize() {
		
	var chieti = new google.maps.LatLng(42.348395, 14.108963);
	
	/* Input text Autocompletion */
	
/* 	var defaultBounds = new google.maps.LatLngBounds(
  		new google.maps.LatLng(47.100045, 6.348610),
  		new google.maps.LatLng(36.279707,18.977966)); */

	
	var input = (document.getElementById('address_autocompleted'));
	var autocomplete_options = {
			  //bounds: defaultBounds,
			  componentRestrictions: {country: 'it'}
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
	  center: chieti,
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

}


google.maps.event.addDomListener(window, 'load', initialize);
</script>

<div class="items">
	<div class="container">
		<div class="row">


			
			<!-- Main content -->
			
			<div class="span6 side-menu">
				<h5 class="title">
					<c:choose>
			      		<c:when test="${requestScope.delete}">
							<spring:message code="user.delete"/>
			      		</c:when>
			      		<c:when test="${requestScope.create}">
							<spring:message code="user.create"/>
			      		</c:when>
			      		<c:when test="${requestScope.update}">
			      			<spring:message code="user.edit"/>
			   			</c:when>
			      	</c:choose>	
		      	</h5>
				<div class="form">
					<form:form modelAttribute="user" cssClass="form-horizontal" action="${pageContext.request.contextPath}${requestScope.action}" method="POST">
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
					
					<c:choose>
						<c:when test="${requestScope.create}">
							<div class="control-group">
							    <label class="control-label" for="password"><spring:message code="user.password"/></label>
							    <div class="controls">
							    	<form:password id="password" path="password.password"/>
							    	<form:errors path="password.password"/>
							    </div>
							</div>
							<div class="control-group">
							    <label class="control-label" for="confirm_password"><spring:message code="user.confirm_password"/></label>
							    <div class="controls">
							    	<form:password id="confirm_password" path="password.confirm_password"/>
							    	<form:errors path="password.confirm_password"/>
							    </div>
							</div>
						</c:when>
					</c:choose>
					
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
							<form:input id="address_autocompleted" path="address"/>
							<form:errors path="address"/>
					    </div>
					</div>
					
					<div class="control-group">
					    <div class="controls">
					      <button type="submit" class="btn">
					      	<c:choose>
					      		<c:when test="${!requestScope.delete}">
									<spring:message code="common.submit"/>
					      		</c:when>
					      		<c:otherwise>
					      			<spring:message code="common.delete"/>
					      		</c:otherwise>
					      	</c:choose>	
					      	
					      </button>
					    </div>
					</div>
					</form:form>
				</div>
			</div>
			
	    	<div class="span6">
	
				<h5 class="title"><spring:message code="common.map" /></h5>

				<div id="googleMap" style="width:500px;height:380px;"></div>

			</div>
						
		</div>
	</div>
</div>







