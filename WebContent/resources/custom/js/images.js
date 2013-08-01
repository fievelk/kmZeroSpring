
/*Ho dovuto rendere questo script una jsp invece di importarlo come semplice .js perchè necessita di sostituzione variabili(jstl)*/

//Qui si impostano le opzioni per ajaxSubmit (comando di jquery.form: libreria jquery per la gestione dei form)

var pathArray = window.location.pathname.split("/");
var contextPath = window.location.origin+"/"+pathArray[1];

var options = {   // target element(s) to be updated with server response 
        //beforeSubmit:  showRequest,  // pre-submit callback 
        success:       processJson,
        resetForm: true,
		dataType: 'json'/*
	    success: processJson funzione delagata per i dati json*/
};

var options_upload = {
		uploadProgress: upload, /*funzione delegata per il processo di avanzamento dell'upload*/
		dataType: 'json',
	    success: processJson,
	    resetForm: true,
	    forceSync: true    
};

/*doAjaxPost viene chiamata sull'onclick del bottone presente nel form di upload immagini - finestra modale (addImagesModal.jsp)*/

function doAjaxPost(form_id) {
	$(form_id).ajaxSubmit(options);
	return false;
}

//pre-submit callback 
function showRequest(formData, jqForm, options) { 
    var queryString = $.param(formData); 
    alert('About to submit: \n\n' + queryString); 
    return true; 
} 
 

function doAjaxPostUpload(form_id) {
	$(form_id).ajaxSubmit(options_upload);
	return false;
}

function processJson(data)  {
	var imgs = '';
	console.log(data);
	/*dai dati json ritornati dal controller ricreo tutte le immagini e i relativi links per la cancellazione*/
	var prod_id = data.id_product;
	var images = data.images;
	
	$.each(images,function(i,item){
		console.log(item);
		
		var paramsDelete = "'deleteImage','products','"+prod_id+"','image','"+item.id+"'";
		var paramsUpdate = "'updateImage','products','"+prod_id+"','image','"+item.id+"'";
		imgs += '<span id="image_'+item.id+'">'
				+'<img src="'+contextPath+'/products/image/'+item.id+'/'+item.name+'" />'
				+'<a href="#modalWindow" class="icon-edit" role="button" data-toggle="modal" onclick="createModalWindow('+paramsUpdate+')" ></a>'
				+'<a href="#modalWindow" class="icon-remove" role="button" data-toggle="modal" onclick="createModalWindow('+paramsDelete+')" ></a>'
				+'</span>';
	});
	/*non si capisce perch al primo giro del foreach viene stamapato 'undefined', 
	ho cercato di debuggare ma firebug non lo mostra, alla fine ho dovuto stripparlo manualmente dalla stringa*/
	imgs = imgs.replace('undefined','');
	$('div#productImages').replaceWith('<div id="productImages">'+imgs+'</div>'); 
	$('#modalWindow').modal('hide');
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


function createModalWindow(method,owner_kind,owner_id,obj_kind,obj_id){
	var data;
	if(data = doAjaxGetModalWindowContent(method+'_start',owner_kind,owner_id,obj_kind,obj_id)){
		$('#modalWindow').modal('show');
	}else{
		data = 'problem occurred!';
	}
	
}

/*get the right form for...(add,update,delete) image/s*/
function doAjaxGetModalWindowContent(method,owner_kind,owner_id,obj_kind,obj_id){
	
	var url = contextPath+"/"+owner_kind+"/"+owner_id;
	if((obj_kind != null) && (obj_id != null)) url+= "/"+obj_kind+"/"+obj_id;
	url += "/"+method;
	$('div#modalWindow').empty(); /*rimuove i childs*/
	$.ajax({
		type: "POST",
		url: url,
		async: false,
		success: function(data) {
					
					
					$('div#modalWindow').replaceWith(data);				
					
				}
	});		
	return false;
	
}
