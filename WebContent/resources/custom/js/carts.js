var pathArray = window.location.pathname.split("/");
var contextPath = window.location.origin+"/"+pathArray[1];

$(document).ready(function(){
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/viewcartpaginated",
		success: cartExistJson
	});
});

function cartExistJson(data){
	var exist = data.exist;
	var cartlines = data.cartlines;
	var tot = 0;
	if (cartlines != null){
		$.each(cartlines,function(i,item){
			tot += parseFloat(item.lineTotal);
		});
	}
	$('#modalC').replaceWith('<a id="modalC" href="#modalCart" role="button" data-toggle="modal" onclick="createModalCart()">' + exist + ' Prodotti nel tuo <i class="icon-shopping-cart"></i></a>');
	$('span#tot').replaceWith('<span id="tot" class="bold"> ' + tot.toFixed(2) + ' &euro;</span>');
}


function createModalCart(){
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/viewcartpaginated",
		success: function(data){
			var exist = data.exist;
			if (exist == 0){
				$('table#tablecart').replaceWith('<div id="divcart"><label>Il carrello \u00E8 vuoto</label></div>');
				$('a#checkout').replaceWith('<a id="checkout"></a>');
				$('a#emptycart').replaceWith('<a id="emptycart"></a>');
				$('div#delivery_checkout').replaceWith('<div id="delivery_checkout"></div>');
				$('button#button_checkout').replaceWith('<div id="button_checkout"></div>');
			} else {
				$('div#divcart').replaceWith('<table class="table table-striped tcart" id="tablecart">'
						+ '<thead><tr><th>Name</th><th>Quantity</th><th>Price</th><th>Delete</th></tr></thead>'
						+ '<tbody id="cartlines"></tbody>');
				cartJson(data);
			}
		}
	});
}

function cartJson(data)  {
	var products = '';
	var cartlines = data.cartlines;
	var tot = 0;
	$.each(cartlines,function(i,item){
		products += '<tr id=' + i + '>' +
					+ '<td>' + '' + '</td>'
					+ '<td>' + item.product.name + '</td>'
					+ '<td>' + item.quantity + '</td>'
					+ '<td>' + item.lineTotal + '</td>'
					+ '<td>' + '<a href="#" class="icon-remove" role="button" data-toggle="modal" onclick="deleteCartLine(' + item.id + ',' + i + ',' + data.id + ')"></a>' + '</td>'
					+ '</tr>';
		tot += parseFloat(item.lineTotal); /* trasforma in float perche' @JsonSerialize nel modello passa una stringa, non un float */
	});
	$('tbody#cartlines').replaceWith('<tbody id="cartlines">' + products + '<tr><th></th><th></th><th>Total</th><th id="total"></th></tr></tbody>');
	$('th#total').replaceWith('<th id="total">\u20ac ' + tot.toFixed(2) + '</th>');
	$('a#checkout').replaceWith('<a id="checkout" href="' + contextPath + '/carts/confirmcart_start?id=' + data.id + '" class="btn btn-danger">Vai alla cassa</a>');
	$('a#emptycart').replaceWith('<a id="emptycart" href="#" class="btn" onclick="emptyCart('+ data.id +')">Svuota il Carrello</a>');
}

function checkoutCart(){
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/viewcartpaginated",
		success: checkoutJson
	});
}

function checkoutJson(data)  {
	var products = '';
	var cartlines = data.cartlines;
	var tot = 0;
	$.each(cartlines,function(i,item){
		products += '<tr id=' + i + '>' +
					+ '<td>' + '' + '</td>'
					+ '<td>' + item.product.name + '</td>'
					+ '<td>' + item.quantity + '</td>'
					+ '<td>' + item.lineTotal + '</td>'
					+ '</tr>';
		tot += parseFloat(item.lineTotal); /* trasforma in float perche' @JsonSerialize nel modello passa una stringa, non un float */
	});
	$('tbody#cartlinesconfirmed').replaceWith('<tbody id="cartlines">' + products + '<tr><th></th><th>Total</th><th id="total"></th></tr></tbody>');
	$('th#total').replaceWith('<th id="total">\u20ac ' + tot.toFixed(2) + '</th>');
	$('#totpaypal').replaceWith('<input id="totpaypal" type="hidden" name="amount" value="' + tot.toFixed(2) + '">');
}


function deleteCartLine(id_item, id_tr, id_cart){
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/delete_cartline?idcartline=" + id_item + "&idcart=" + id_cart + "&idanonymous=" + id_tr,
		success: function(data){
			$('tr#'+id_tr).fadeOut('slow', function() {$('tr#'+id_tr).remove(); createModalCart();});
		}
	});
	minimalCart();
}

function existCart(id){
	
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/viewcartpaginated",
		success: function(data){
			var cart_id = data.id;
			var exist = data.exist;
			if (cart_id == 0 && exist == 0){
				// fai partire la finestra modale per l'indirizzo
				$('#modalDialogAddress').modal('show');
				$('#address_autocompletedModal').val('');
				$('#addressDistanceErrorModal').empty();
				$('#successimgModal').remove();
				$('#submitIfValidAddressModal').replaceWith('<button id="submitIfValidAddressModal" type="submit" class="btn" data-dismiss="modal" aria-hidden="true" onclick="validAddress(' + id + ')">Aggiungi al Carrello</button>');
				google.maps.event.addDomListenerOnce($('#modalDialogAddress'), 'shown', executeOnModal());
			} else {
				// L'indirizzo e' gia' stato validato
				addCartLine(id);
			};
		}
	});
};

function validAddress(id){
	var address = $('#address_autocompletedModal').val();
	var quantity = $('#'+id).val();
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/addressvalidated?id=" + id + "&quantity=" + quantity + "&address=" + address,
		success: cartExistJson
	});
};

function addCartLine(id){
	var quantity = $('#'+id).val();
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/create?id=" + id + "&quantity=" + quantity,
		success: cartExistJson
	});
};

function minimalCart(){
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/viewcartpaginated",
		success: cartExistJson
	});
}

function emptyCart(id){
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/emptycart?id=" + id,
		success: function(data){
			createModalCart();
			$('#modalC').replaceWith('<a id="modalC" href="#modalCart" role="button" data-toggle="modal" onclick="createModalCart()">' + 0 + ' Prodotti nel tuo <i class="icon-shopping-cart"></i></a>');
			$('span#tot').replaceWith('<span id="tot" class="bold"> ' + 0.00 + ' &euro;</span>');
		}
	});
}
