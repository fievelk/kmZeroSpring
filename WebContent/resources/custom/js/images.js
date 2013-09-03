var pathArray = window.location.pathname.split("/");
var contextPath = window.location.origin+"/"+pathArray[1];

var options = {   // target element(s) to be updated with server response 
        //beforeSubmit:  showRequest,  // pre-submit callback 
        success: processJson,
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

//update e delete
function doAjaxPost(form_id) {
	$(form_id).ajaxSubmit(options);
	return false;
}

//upload
function doAjaxPostUpload(form_id) {
	$(form_id).ajaxSubmit(options_upload);
	return false;
}

function processJson(data)  {
	if(data.trueData){
		var imgs = '';
		/*dai dati json ritornati dal controller ricreo tutte le immagini e i relativi links per la cancellazione*/
		var prod_id = data.owner_id;
		var images = data.images;
		var owner_kind = data.owner_kind;
		
		$.each(images,function(i,item){
			
			var paramsDelete = "'deleteImage','"+owner_kind+"','"+prod_id+"','"+item.id+"'";
			var paramsUpdate = "'updateImage','"+owner_kind+"','"+prod_id+"','"+item.id+"'";
			imgs += '<div id="km0Image">'
					+'<div>'
					+'<a href="#modalWindow" class="icon-edit" role="button" data-toggle="modal" onclick="createModalWindow('+paramsUpdate+')" ></a>'
					+'<a href="#modalWindow" class="icon-remove" role="button" data-toggle="modal" onclick="createModalWindow('+paramsDelete+')" ></a>'
					+'</div>'
					+'<span id="image_'+item.id+'">'
					+'<img src="'+contextPath+'/'+owner_kind+'/image/'+item.id+'/'+item.name+'" />'
					+'</span>'
					+'</div>';
		});
		/*non si capisce perche' al primo giro del foreach viene stamapato 'undefined', 
		ho cercato di debuggare ma firebug non lo mostra, alla fine ho dovuto stripparlo manualmente dalla stringa*/
		imgs = imgs.replace('undefined','');
		$('div#km0Images').replaceWith('<div id="km0Images">'+imgs+'</div>'); 
		$('#modalWindow').modal('hide');
		/*reset progress bar*/
		$("div.bar").width("0%");
		$('div.bar').text("0%");
		$("div.bar").attr("data-percentage","100");
	}else{
		$(".modal-body").html('Error! <spring:message code="errors.idInjection"/>');
		setTimeout(function(){
			$('#modalWindow').modal('hide');
		}, 2000);
		
	}
}

function upload(event, position, total, percentComplete)  {
	$("div.bar").attr("data-percentage",total);
	var percentVal = percentComplete + '%';
	$("div.bar").width(percentVal);
	$('div.bar').text(percentVal); 
}


function createModalWindow(method,owner_kind,owner_id,image_id){
	var data;
	if(data = doAjaxGetModalWindowContent(method+'_start',owner_kind,owner_id,image_id)){	
		$('#modalWindow').modal('show');
	}else{
		data = 'problem occurred!';
	}
	
}

/*get the right form for...(add,update,delete) image/s*/
function doAjaxGetModalWindowContent(method,owner_kind,owner_id,image_id){

	var url = contextPath+"/"+owner_kind+"/"+owner_id;
	if(image_id != null) url+= "/image/"+image_id;
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

//pre-submit callback 
function showRequest(formData, jqForm, options) { 
    var queryString = $.param(formData); 
    alert('About to submit: \n\n' + queryString); 
    return true; 
} 
