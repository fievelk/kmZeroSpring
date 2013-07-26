<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>



<script>
/*Ho dovuto rendere questo script una jsp invece di importarlo come semplice .js perchè necessita di sostituzione variabili(jstl)*/


/*--------AJAX ADD IMAGES - START--------*/

//Qui si impostano le opzioni per ajaxSubmit (comando di jquery.form: libreria jquery per la gestione dei form)

var options = {
		uploadProgress: upload, /*funzione delegata per il processo di avanzamento dell'upload*/
		dataType: 'json',
	    success: processJson /*funzione delagata per i dati json*/
};

/*doAjaxPost viene chiamata sull'onclick del bottone presente nel form di upload immagini - finestra modale (addImagesModal.jsp)*/
function doAjaxPost() {
	$("#fileUpload").ajaxSubmit(options);
	return false;
}

function processJson(data)  {
	var imgs;
	/*dai dati json ritornati dal controller ricreo tutte le immagini e i relativi links per la cancellazione*/
	$.each(data,function(i,item){
		imgs += '<span id="image_'+item.id+'"><img src="${pageContext.request.contextPath}/products/image/'+item.id+'/'+item.name+'">\n<a href="#dialog" class="icon-remove-circle" onclick="dialog('+item.id+','+${id}+')" role="button" data-toggle="modal"></a></span>\n';
	});
	/*non si capisce perch al primo giro del foreach viene stamapato 'undefined', 
	ho cercato di debuggare ma firebug non lo mostra, alla fine ho dovuto stripparlo manualmente dalla stringa*/
	imgs = imgs.replace('undefined','');
	$('div#productImages').replaceWith('<div id="productImages">'+imgs+'</div>'); 
	/*Chiude la finestra modale*/
	$('#addImages').modal('hide');
	/*reset progress bar*/
	$("div.bar").width("0%");
	$('div.bar').text("0%");
	$("div.bar").attr("data-percentage","100");
}

function upload(event, position, total, percentComplete)  {
	$("div.bar").attr("data-percentage",total);
	var percentVal = percentComplete + '%';
	$("div.bar").width(percentVal);
	$('div.bar').text(percentVal); 
}

/*--------AJAX ADD IMAGES - END--------*/
	
/*--------AJAX DELETE IMAGE - START--------*/
	
/*prima viene chiamata questa funzione che fa uscire il dialog di conferma di cancellazione)*/
function dialog(image_id,product_id){
	$('#dialog p').text("sicuro di voler rimuovere questa immagine?");
	var imgsrc = "${pageContext.request.contextPath}/products/image/"+image_id;
	$('#dialog img').attr("src",imgsrc);
	$('#dialog_ok').click(function(){
		/*se viene premuto ok parte la chiamata ajax doAjaxDeleteImg*/
		doAjaxDeleteImg(image_id,product_id);
		$('#dialog').modal('hide')
	});
	$('#dialog_cancel').click(function (){
		$('#dialog_ok').unbind('click'); 
		$('#dialog_cancel').unbind('click'); 
		/*Necessario! Altrimenti rimane legato l'evento click sull'OK anche ad una precedente azione di cancellazione 
		Scenario:
			1-rimuovi immagine (viene fatto il bind dell'ok $('#dialog_ok').click e del cancel $('#dialog_cancel').click)
			2-cancel...rimane il bind dell'ok
			3-rimuovi immagine
			4-ok...viene rimossa sia l'immagine corrente che quella precedente. Perchè al bottone ok era rimasto il bind anche della precedente.
			Quindi con una pressione di 'ok' scateno entrambe le chiamate.
		*/
		$('#dialog').modal('hide')
	});
}


/*chiamata ajax per la cancellazione dell'imagine - chiama ProductsController.deleteImage e ritorna un booleano*/
function doAjaxDeleteImg(image_id,product_id){
	url = "${pageContext.request.contextPath}/products/"+product_id+"/image/"+image_id+"/delete";
	$.ajax({
		type: "POST",
		url: url,
		success: function(data) {
					if(data == true){
						$("#image_"+image_id).remove();
					}
				}
	});		
	return false;
}

/*--------AJAX DELETE IMAGE - END--------*/


</script>