<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">

/*inizializzazione variabili globali per la paginazione*/
var iDisplayStart = 0; /*primo elemento della pagina ((pageNumber-1)*iDisplayLength)*/
var iTotalRecords = 10;/*numero totale di elementi*/
var iDisplayLength = 10;/*numero di elementi per pagina*/
var categoryId = "";
var sellerId = "";
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
	
	$(".form-search input").keypress(function(event) {
		if(event.which == 13) {
			iDisplayStart = 0;
			setProducts();
		}
	});
	
	$("#categ #nav a").click(function(){
		categoryId = $(this).attr("id").replace("cat_","");
		iDisplayStart = 0;
		setProducts();
	});
	
	$("#seller #nav a").click(function(){
		sellerId = $(this).attr("id").replace("seller_","");
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
	criteria = "iDisplayStart="+iDisplayStart+"&iDisplayLength="+iDisplayLength+"&sortCol="+sortCol+"&sortDir="+sortDir+"&sSearch="+sSearch+"&categoryId="+categoryId+"&sellerId="+sellerId;
};

function buildItems(data){
	var products = "";
		$.each(data.rows,function(i,item){
			products += buildItem(item);
		});	
		
		$('#products').replaceWith('<div id="products" class="row">'+products+'</div>'); 
		$('#products').hide();
		$('#products').fadeIn('slow');
};

function buildItem(item){
	
	var image;
	var baseurl = '${pageContext.request.contextPath}';
	var producturl = baseurl+'/products/'+item.id+'/'+item.name;
	var sellerurl = baseurl+'/sellers/'+item.seller.id+'/'+item.seller.company;
	if(item.images[0] != null){
		image = '<a href="'+producturl+'"><img src="${pageContext.request.contextPath}/product/image/'+item.images[0].id+'/'+item.images[0].name+'" alt="'+item.images[0].name+'" /></a>';
	}else{
		image = '<a href="'+producturl+'"><img src="${pageContext.request.contextPath}/resources/mackart/img/photos/question.png" alt="undefined" /></a>';
	};
	var description = (item.description != null) ? (item.description.substring(0,30)+'...') : "no description provided";
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
				'<h6><a href="'+url+'">'+item.name.substring(0,20)+'</a><span class="ico"><img src="" alt="" /></span></h6>'+
				'<div class="clearfix"></div>'+
// 		<!-- Para. Note more than 2 lines. -->
			'<p>'+description+'</p>'+
			'<p><a href="'+sellerurl+'">'+item.seller.company+'</a></p>'+
			'<hr />'+
			'<div class="item-price pull-left">'+item.rating.rating.toFixed(1)+'/5</div>'+
			'<div class="item-price pull-right">\u20ac '+item.price+'/'+item.measure.shortName+'</div>'+			
// 			<!-- Price -->
			
// 			<!-- Add to cart -->
			
			'<div class="clearfix"></div>'+
			'<hr />'+
			'<div id="product_buttons">'+
			<!-- SPINNER -->
			'<div class="fuelux row pull-left"><div class="spinner"><input type="number" min="1" max="1000" id="' + item.id + '" value="1" class="input-mini spinner-input" maxlength="3" /><div class="spinner-buttons btn-group btn-group-vertical"><button type="button" class="btn spinner-up"><i class="icon-chevron-up"></i></button><button type="button" class="btn spinner-down"><i class="icon-chevron-down"></i></button></div></div></div>'+
			<!-- END SPINNER -->
			'<div class="button"><a href="#" id="" onclick="existCart('+ item.id +');return false"><spring:message code="product.addToCart" /></a></div>'+
			'</div>'+
		'</div></div></div>';
	
	return result;
};

function paginate() {
	
	$(".pagging").pagination({
        items: iTotalRecords,
        itemsOnPage: iDisplayLength,
        cssStyle: 'light-theme',
        hrefTextPrefix: '#page-',
        onPageClick: function(pageNumber, event){
					        	if(pageNumber == 1){
									iDisplayStart = 0;
								}else{
									iDisplayStart = ((pageNumber-1)*iDisplayLength);
								}
					        	setCriteria();
								ajaxCall();	
							}
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
        <h4 class="pull-left"><spring:message code="menu.products" /></h4>

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
                           <option value="name-ASC" selected><spring:message code="product.name" /> (A-Z)</option>
                           <option value="name-DESC"><spring:message code="product.name" /> (Z-A)</option>
                           <option value="price-ASC"><spring:message code="cartline.price" /> (Low-High)</option>
                           <option value="price-DESC"><spring:message code="cartline.price" /> (High-Low)</option>
                           <option value="rating-ASC"><spring:message code="product.rating" /></option>
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
		    <h5 class="title"><spring:message code="cart.addressValidation" /></h5>
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
					      <button id="submitIfValidAddressModal" type="submit" class="btn" data-dismiss="modal" aria-hidden="true"><spring:message code="product.addToCart" /></button>
					    </div>
					
					</div>
					
					<div class="control-group">
					</div>
			</div>
		</div>
