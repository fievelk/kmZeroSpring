$(document).ready(function(){
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/viewcartpaginated",
		success: cartExistJson
	});
});

function cartExistJson(data){
	var exist = data.exist;
	$('#modalC').replaceWith('<a id="modalC" href="#modalCart" role="button" data-toggle="modal" onclick="createModalCart()">' + exist + ' Item(s) in your <i class="icon-shopping-cart"></i></a>');
}


function createModalCart(){
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/viewcartpaginated",
		success: function(data){
			var exist = data.exist;
			$('a#modalC').replaceWith('<a id="modalC" href="#modalCart" role="button" data-toggle="modal" onclick="createModalCart()">' + exist + ' Item(s) in your <i class="icon-shopping-cart"></i></a>');
			if (exist == 0){
				$('table#tablecart').replaceWith('<div id="divcart"><label>Il carrello \u00E8 vuoto</label></div>');
				$('a#checkout').replaceWith('<a id=checkout></a>');
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
	$('a#checkout').replaceWith('<a id="checkout" href="' + contextPath + '/carts/confirmcart_start.do?id=' + data.id + '" class="btn btn-danger">Vai alla cassa</a>');
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
	$('#totpaypal').replaceWith('<input id="totpaypal" type="hidden" name="amount" value="' + tot + '">');
}


function deleteCartLine(id_item, id_tr, id_cart){
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/delete_cartline?idcartline=" + id_item + "&idcart=" + id_cart + "&idanonymous=" + id_tr,
		success: function(data){
			$('tr#'+id_tr).fadeOut('slow', function() {$('tr#'+id_tr).remove(); createModalCart();});
		}
	});
}