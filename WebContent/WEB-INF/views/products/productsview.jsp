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
}

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
}


function setCriteria(){
	/*imposto tutti i valori correnti di sortBy perPage e search*/
	/*valore della select perPage*/
	iDisplayLength = $('#perPage select').find(":selected").val();
	/*valore della search*/
	sSearch = $(".form-search input").val();
	/*valore della sortBy*/
	sortCol = $('#sortBy select').find(":selected").val();
	/*Infine creo la stringa serializzata per l'ajax call*/
	criteria = "iDisplayStart="+iDisplayStart+"&iDisplayLength="+iDisplayLength+"&sortCol="+sortCol+"&sSearch="+sSearch;
	console.log(criteria);
}

function buildItems(data){
	var products = "";
		console.log(data);
		$.each(data.rows,function(i,item){
			products += buildItem(item);
		});	
		
		$('#products').replaceWith('<div id="products" class="row">'+products+'</div>'); 
		$('#products').hide();
		$('#products').fadeIn('slow');
}

function buildItem(item){
	
	var result = 
		'<div class="span3">'+
		'<div class="item">'+
	  <!-- Item image -->
			'<div class="item-image">'+
			  '<a href="single-item.html"><img src="${pageContext.request.contextPath}/products/image/'+item.images[0].id+'/'+item.images[0].name+'" alt="'+item.images[0].name+'" /></a>'+
			'</div>'+
	<!-- Item details -->
			'<div class="item-details">'+
		  <!-- Name -->
		<!-- Use the span tag with the class "ico" and icon link (hot, sale, deal, new) -->
				'<h5><a href="single-item.html">'+item.name+'</a><span class="ico"><img src="" alt="" /></span></h5>'+
				'<div class="clearfix"></div>'+
		<!-- Para. Note more than 2 lines. -->
			'<p>'+item.description+'</p>'+
			'<div class="rateit" data-rateit-resetable="false"></div>'+
			'<hr />'+
			<!-- Price -->
			'<div class="item-price pull-left">'+item.price+'</div>'+
			<!-- Add to cart -->
			'<div class="button pull-right"><a href="#">Add to Cart</a></div>'+
			'<div class="clearfix"></div>'+
			'<div class="clearfix"></div>'+
		'</div></div></div>';
	
	return result;
}

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
							iDisplayStart = ((pageNumber-1)*iDisplayLength);
						}
			        	setCriteria();
        				ajaxCall();	
        				console.log(criteria);
        				console.log("totrecords:"+iTotalRecords);
        				console.log("iDisplayLength:"+iDisplayLength);
        				console.log("iDisplayStart:"+iDisplayStart);
        			}
    });	
}

</script>

<!-- Items - START -->

<div class="items">
  <div class="container">
    <div class="row">

      <!-- Sidebar -->
      <div class="span3 hidden-phone">

        <h5 class="title">Categories</h5>
        <!-- Sidebar navigation -->
          <nav>
            <ul id="nav">
              <!-- Main menu. Use the class "has_sub" to "li" tag if it has submenu. -->
              <li><a href="index.html">Home</a></li>
              <li class="has_sub"><a href="#">Aziende</a>
                <!-- Submenu -->
                <ul>
                              <li><a href="items.html">Azienda 1</a></li>
                              <li><a href="items.html">Azienda 2</a></li>
                              <li><a href="items.html">Azienda 3</a></li>
                              <li><a href="items.html">Azienda 4</a></li>
                              <li><a href="items.html">Azienda 5</a></li>
                </ul>
              </li>
            </ul>
          </nav>
<br />
          <!-- Sidebar items (featured items)-->

          <div class="sidebar-items">

            <h5 class="title">Featured Items</h5>

            <!-- Item #1 -->
            <div class="sitem">
              <!-- Don't forget the class "onethree-left" and "onethree-right" -->
              <div class="onethree-left">
                <!-- Image -->
                <a href="single-item.html"><img src="" alt="" /></a>
              </div>
              <div class="onethree-right">
                <!-- Title -->
                <a href="single-item.html">HTC One V</a>
                <!-- Para -->
                <p>Aenean ullamcorper justo tincidunt justo aliquet.</p>
                <!-- Price -->
                <p class="bold">$199</p>
              </div>
              <div class="clearfix"></div>
            </div>             
          </div>
      </div>
      
      <div class="span9">

        <!-- Breadcrumb -->
        <ul class="breadcrumb">
          <li><a href="index.html">Home</a> <span class="divider">/</span></li>
          <li><a href="items.html">Prodotti</a> <span class="divider">/</span></li>
          <li class="active">km0</li>
        </ul>

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
	                                                <option value="name ASC" selected>Name (A-Z)</option>
	                                                <option value="name DESC">Name (Z-A)</option>
	                                                <option value="price ASC">Price (Low-High)</option>
	                                                <option value="price DESC">Price (High-Low)</option>
	                                                <option value="rating ASC">Ratings</option>
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
           </div>
          </div>
          </div>                          
<!-- Items  - END-->