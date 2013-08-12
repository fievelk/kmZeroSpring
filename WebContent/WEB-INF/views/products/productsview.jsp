<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">

/*inizializzazione variabili globali per la paginazione*/
var iDisplayStart = 0; /*primo elemento della pagina ((pageNumber-1)*iDisplayLength)*/
var iTotalRecords = 10;/*numero totale di elementi*/
var iDisplayLength = 10;/*numero di elementi per pagina*/
var sSearch = '';
var sortCol = '';
var criteria = ''; /*stringa serializzata che contiene i parametri per l'ajax call*/

$(document).ready(function() {
		
	setProducts();
	
	$('#perPage select').change(function(){
		iDisplayStart = 0;
		setProducts();
	});
	$('#sortBy select').change(function(){
		iDisplayStart = 0;
		setProducts();
	});
	$(".form-search button").click(function(){
		iDisplayStart = 0;
		setProducts();
	});
	
});

function setProducts(){
	setCriteria();
	ajaxCall();
	paginate();
};

function ajaxCall(){

	url = "${pageContext.request.contextPath}/products/viewProducts";
	$.ajax({
		type: "POST",
		url: url,
		data: criteria,
		async: false,
		success: function(data) {
					iTotalRecords = data.iTotalRecords;
					buildItems(data);
				}
	});		
	return false;
};


function setCriteria(){
	/*imposto tutti i valori correnti di sortBy perPage e search*/
	/*valore della select perPage*/
	iDisplayLength = $('#perPage select').find(":selected").val();
	/*valore della search*/
	sSearch = $(".form-search input").val();
	/*valore della sortBy*/
	sortBy = $('#sortBy select').find(":selected").val();
	sortBy_parts = sortBy.split("-");
	sortCol = sortBy_parts[0];
	sortDir = sortBy_parts[1];
	/*Infine creo la stringa serializzata per l'ajax call*/
	criteria = "iDisplayStart="+iDisplayStart+"&iDisplayLength="+iDisplayLength+"&sortCol="+sortCol+"&sortDir="+sortDir+"&sSearch="+sSearch;
	console.log(criteria);
};

function buildItems(data){
	var products = "";
		console.log(data);
		$.each(data.rows,function(i,item){
			products += buildItem(item);
		});	
		
		$('#products').replaceWith('<div id="products" class="row">'+products+'</div>'); 
		$('#products').hide();
		$('#products').fadeIn('slow');
};

function buildItem(item){
	
	var image;
	if(item.images[0] != null){
		image = '<a href="single-item.html"><img src="${pageContext.request.contextPath}/prod/image/'+item.images[0].id+'/'+item.images[0].name+'" alt="'+item.images[0].name+'" /></a>';
	}else{
		image = '<a href="single-item.html"><img src="${pageContext.request.contextPath}/resources/mackart/img/photos/question.png" alt="undefined" /></a>';
	};
	var result = 
		'<div class="span3">'+
		'<div class="item">'+
// 	  <!-- Item image -->
			'<div class="item-image">'+
			  image+
			'</div>'+
// 	<!-- Item details -->
			'<div class="item-details">'+
// 		  <!-- Name -->
// 		<!-- Use the span tag with the class "ico" and icon link (hot, sale, deal, new) -->
				'<h5><a href="single-item.html">'+item.name+'</a><span class="ico"><img src="" alt="" /></span></h5>'+
				'<div class="clearfix"></div>'+
// 		<!-- Para. Note more than 2 lines. -->
			'<p>'+item.description+'</p>'+
			'<div class="rateit" data-rateit-resetable="false"></div>'+
			'<hr />'+
// 			<!-- Price -->
			'<div class="item-price pull-left">'+item.price+'</div>'+
// 			<!-- Add to cart -->
			'<button value="' + item.id + '" class="button pull-right" onclick="existCart('+ item.id +')">Add to Cart</button>'+
			//'<div class="button pull-right" onclick="existCart('+ item.id +')"><a href="#modalDialogAddress">Add to Cart</a></div>'+
			'<div class="clearfix"></div>'+
			'<div class="clearfix"><label>Scegli una quantit\u00E0: <input type="number" min="1" max="1000" id="' + item.id + '" value="1"/></label></div>'+
		'</div></div></div>';
	
	return result;
};

function paginate() {
	console.log("totrecords:"+iTotalRecords);
	console.log("iDisplayLength:"+iDisplayLength);
	console.log("iDisplayStart:"+iDisplayStart);
		
	$(".pagging").pagination({
        items: iTotalRecords,
        itemsOnPage: iDisplayLength,
        cssStyle: 'light-theme',
        hrefTextPrefix: '#page-',
        onPageClick: function(pageNumber, event){
			        	if(pageNumber == 1){
							iDisplayStart = 0;
						}else{
							console.log("PAGE"+pageNumber);
							console.log("iDisplayLength1:"+iDisplayLength);
							iDisplayStart = ((pageNumber-1)*iDisplayLength);
							console.log("start"+iDisplayStart);
						};
			        	setCriteria();
        				ajaxCall();	
        				console.log(criteria);
        				console.log("totrecords:"+iTotalRecords);
        				console.log("iDisplayLength:"+iDisplayLength);
        				console.log("iDisplayStart:"+iDisplayStart);
        			}
    });	
};

function existCart(id){
	
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/existcart.do",
		success: function(data){
			var cart_id = data.id;
			var exist = data.exist;
			var num_item = exist + 1;
			if (cart_id == 0 && exist == 0){
				// fai partire la finestra modale per l'indirizzo
				$('#modalDialogAddress').modal('show');
				$('#submitIfValidAddressModal').replaceWith('<button id="submitIfValidAddressModal" type="submit" class="btn" data-dismiss="modal" aria-hidden="true" onclick="validAddress(' + id + ')">Add to cart</button>');
				google.maps.event.addDomListenerOnce($('#modalDialogAddress'), 'shown', executeOnModal());
			} else {
				// L'indirizzo è già stato validato
				addCartLine(id);
			};
			$('a#modalC').replaceWith('<a id="modalC" href="#modalCart" role="button" data-toggle="modal" onclick="createModalCart()">' + num_item + ' Item(s) in your <i class="icon-shopping-cart"></i></a>');
		}
	});
};

function validAddress(id){
	var address = $('#address_autocompletedModal').val();
	var quantity = $('#'+id).val();
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/addressvalidated.do?id=" + id + "&q=" + quantity + "&a=" + address,
	});
};


function addCartLine(id){
	var quantity = $('#'+id).val();
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/create.do?id=" + id + "&q=" + quantity,
	});
};

</script>

<!-- Items - START -->
      
      <div class="span9">

<!--         Breadcrumb
        <ul class="breadcrumb">
          <li><a href="index.html">Home</a> <span class="divider">/</span></li>
          <li><a href="items.html">Prodotti</a> <span class="divider">/</span></li>
          <li class="active">km0</li>
        </ul> -->

                            <!-- Title -->
                              <h4 class="pull-left">I prodotti</h4>

<!--Items Per Page -->
                                            <div id="perPage" class="controls pull-right perpage">                               
                                                <select>
	                                                <option value="10" selected>10</option>
	                                                <option value="2">2</option>
	                                                <option value="20">20</option>
	                                                <option value="50">50</option>
	                                                <option value="100">100</option>
                                                </select>  
                                            </div>
                                          <!-- Sorting -->
                                            <div id="sortBy" class="controls pull-right">                               
                                                <select>
	                                                <option value="name-ASC" selected>Name (A-Z)</option>
	                                                <option value="name-DESC">Name (Z-A)</option>
	                                                <option value="price-ASC">Price (Low-High)</option>
	                                                <option value="price-DESC">Price (High-Low)</option>
	                                                <option value="rating-ASC">Ratings</option>
                                                </select>  
                                            </div>
                                            

              <div class="clearfix"></div>
			  <div id="products" class="row">
              </div>
              <div class="row">
                <div id="pagger" class="span9">
                   <!-- Pagination -->
                   <div class="pagging">                             
                   </div>
                </div> 
              </div>
            </div>    
                         
<!-- Items  - END-->


<script src="${pageContext.request.contextPath}/resources/custom/js/kmzGMapsModal.js"></script>

		<div id="modalDialogAddress" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
		  <div class="modal-header">
		    <button id="modalDialogAddress_dismiss" type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h5 class="title">Address Validation test</h5>
		  </div>
		  <div class="modal-body">
				
				<div id="googleMapModal" style="height:200px;"></div>				
					
					<div class="control-group">
					    <label class="control-label" for="address"><spring:message code="user.address" /></label>
					    
					    <div class="controls">
							<input id="address_autocompletedModal"/><br />
					    </div>
					    
					    <p id="addressDistanceErrorModal"></p>
					
						<div class="controls">
					      <button id="submitIfValidAddressModal" type="submit" class="btn" data-dismiss="modal" aria-hidden="true">Add to cart</button>
					    </div>
					
					</div>
					
					<div class="control-group">
					</div>
			</div>
		</div>
