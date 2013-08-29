<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript"> 

      var map = null;
      var marker = null;

var infowindow = new google.maps.InfoWindow(
  { 
    size: new google.maps.Size(150,50)
  });

$(document).ready(function() {
	initialize();
});

function initialize() {
	var wh_addr = "${warehouse}";
	var names = new Array();
	var addresses = new Array();
	var urls = new Array();

	<c:forEach var="seller" items="${sellers}">
		addresses.push("${seller.address}");
		names.push("${seller.company}");
		urls.push("${pageContext.request.contextPath}/sellers/${seller.id}/${seller.company}");
	</c:forEach>
	
	codeAddresses(addresses, function(coords) {
		
			codeAddress(wh_addr, function(wh_coords) {
				var myOptions = {
					    zoom: 10,
					    center: wh_coords,
					    scrollwheel: false,
					    navigationControl: false,
					    mapTypeControl: false,
					    scaleControl: false,
					    draggable: false,
					    disableDefaultUI: true,
					    mapTypeId: google.maps.MapTypeId.ROADMAP
					  }
					  map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
					  createMarker(wh_coords,'WAREHOUSE','<a href="#">WAREHOUSE</a>');
					for(var i = 0; i < coords.length; i++){
						createMarker(coords[i],names[i],'<a href="'+urls[i]+'">'+names[i]+"</a>");
					}
			});
	});
	
}

/*Ottiene le coordinate del Magazzino*/

function codeAddress(address,callback) {

	var geocoder = new google.maps.Geocoder();
    geocoder.geocode( { 'address': address}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
    	  if( typeof callback == 'function' ) {
              callback(results[0].geometry.location);
          }
      } else {
        alert("Geocode was not successful for the following reason: " + status);
        /* throw('No results found: ' + status);*/
      }
    });
  }
  
/*Ottiene le coordinate di tutti i venditori*/

function codeAddresses(addresses, callback) {
    var coords = [];
    for(var i = 0; i < addresses.length; i++) {
        currAddress = addresses[i];
        var geocoder = new google.maps.Geocoder();
        if (geocoder) {
            geocoder.geocode({'address':currAddress}, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    coords.push(results[0].geometry.location);
                    if(coords.length == addresses.length) {
                        if( typeof callback == 'function' ) {
                            callback(coords);
                        }
                    }
                } 
                else {
                    throw('No results found: ' + status);
                }
            });
        }
     }
  }

function createMarker(latlng, name, html) {
    var contentString = html;
    var iconBase = '${pageContext.request.contextPath}/resources/custom/img/';
    var marker;
    console.log(name);
    if(name=="WAREHOUSE"){
    	
	    	marker = new google.maps.Marker({
	        position: latlng,
	        map: map,
	        icon: iconBase+"logo_map.png"
	        });
	    	marker.setZIndex(9999);
    }else{
	    	marker = new google.maps.Marker({
	        position: latlng,
	        map: map,
	        icon: iconBase+"seller2.png"
	        });
	    	marker.setZIndex(Math.floor(Math.random()*1000));
    }
    google.maps.event.addListener(marker, 'click', function() {
        infowindow.setContent(contentString); 
        infowindow.open(map,marker);
        });
    
    return marker;
}

</script> 

<div class="span9">
	<div class="row">
		<div class="span9">
		    <div class="mapbox" id="map_canvas" style="height:400px"></div>
		 </div>
		<c:set var="alternate" value="1"/>
		<c:forEach var="content" items="${seller.contents}">
			<c:choose>
			    <c:when test="${alternate == 1}">
						<div class="span9"><h4 class="title">${content.title}</h4></div>
						<div class="span6">
							${content.description}
						</div>
						<div class="span3">
							<img src="${pageContext.request.contextPath}/selr_content/image/${content.image.id}/${content.image.name}" alt="${content.image.name}" />
						</div>
			       <c:set var="alternate" value="0"/>
			    </c:when>
			    <c:when test="${alternate == 0}">
						<div class="span9"><h4 class="title">${content.title}</h4></div>
						<div class="span3">
							<img src="${pageContext.request.contextPath}/selr_content/image/${content.image.id}/${content.image.name}" alt="${content.image.name}" />
						</div>
						<div class="span6" >
							${content.description}
						</div>
			        <c:set var="alternate" value="1"/>
			    </c:when>
			</c:choose>
		</c:forEach>
	</div>
</div>
<br/><br/>
<div class="recent-posts">
    <div class="row">
    <div class="container">
      <div class="span12">
        <div class="bor"></div>
        <h4 class="title"><spring:message code="product.ourproducts"/></h4>
        <div class="carousel_nav pull-right">
          <!-- Navigation -->
          <a href="#" id="car_prev" class="prev" style="display: inline;"><i class="icon-chevron-left"></i></a>
          <a href="#" id="car_next" class="next" style="display: inline;"><i class="icon-chevron-right"></i></a>
        </div>
        <div class="clearfix"></div>
        <div class="caroufredsel_wrapper" style="display: block; text-align: start; float: none; position: relative; top: auto; right: auto; bottom: auto; left: auto; z-index: auto; width: 940px; height: 255px; margin: 0px; overflow: hidden;">
        <ul class="rps-carousel" style="text-align: left; float: none; position: absolute; top: 0px; right: auto; bottom: auto; left: 0px; margin: 0px; width: 3760px; height: 255px;">
            <!-- Recent items #1 -->
            <!-- Each item should be enclosed inside "li"  tag. -->
           <c:forEach var="product" items="${products}">
						  <li style="width: 180px;">
							<div class="rp-item"> 
						           <div class="rp-image">        
						             <a href="${pageContext.request.contextPath}/products/${product.id}/${product.name}">	
									<img src="${pageContext.request.contextPath}/prod/image/<c:out value="${product.images[0].id}" />/<c:out value="${product.images[0].name}" />" alt="<c:out value="${product.images[0].altName}" />" />
						          	</a>
						       	</div>
								<div class="rp-details">
								  <!-- Title and para -->
								  <h5><a href="${pageContext.request.contextPath}/products/${product.id}/${product.name}">${product.name}<span class="price pull-right">$255</span></a></h5>
								  <div class="clearfix"></div>
								  <p>
								  	<c:if test="${product.description ne null }">
								  		${fn:substring(product.description, 0, 25)}...
								  	</c:if>
								  </p>         
								</div>                
							</div>        
						  </li>
						  </c:forEach>                                                                                                  
        </ul></div>
      </div>
    </div>
  </div>
 </div>
