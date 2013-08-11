$(document).ready(function(){
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/existcart.do",
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
		url: contextPath+"/carts/viewcartpaginated.do",
		success: cartJson
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
		tot += item.lineTotal;
	});
	$('tbody#cartlines').replaceWith('<tbody id="cartlines">' + products + '<tr><th></th><th>Total</th><th id="total"></th></tr></tbody>');
	$('th#total').replaceWith('<th id="total">\u20ac ' + tot + '</th>');
	$('a#checkout').replaceWith('<a id="checkout" href="' + contextPath + '/carts/confirmcart_start.do?id=' + data.id + '" class="btn btn-danger">Vai alla cassa</a>');
	$('#totpaypal').replaceWith('<input id="totpaypal" type="hidden" name="amount" value="' + tot + '">');
}

function createModalCart2(){
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/viewcartpaginated.do",
		success: cartJson2
	});
}

function cartJson2(data)  {
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
		tot += item.lineTotal;
	});
	$('tbody#cartlines').replaceWith('<tbody id="cartlines">' + products + '<tr><th></th><th></th><th>Total</th><th id="total"></th></tr></tbody>');
	$('th#total').replaceWith('<th id="total">\u20ac ' + tot + '</th>');
	$('#totpaypal').replaceWith('<input id="totpaypal" type="hidden" name="amount" value="' + tot + '">');
}

function deleteCartLine(id_item, id_tr, id_cart){
	$.ajax({
		type: "POST",
		url: contextPath+"/carts/delete_cartline.do?idcl=" + id_item + "&idc=" + id_cart,
		success: function(){
			$('tr#'+id_tr).fadeOut('slow', function() {$('tr#'+id_tr).remove(); createModalCart();});
		}
	});
}