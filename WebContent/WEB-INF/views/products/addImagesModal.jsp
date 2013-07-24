<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- AddImage Modal starts -->

<script type="text/javascript">

function processJson(data)  {
	
	var imgs;
	$.each(data,function(i,item){
		imgs += '<span id="image_'+item.id+'"><img src="${pageContext.request.contextPath}/products/image/'+item.id+'/'+item.name+'">\n<a href="#dialog" class="icon-remove-circle" onclick="dialog('+item.id+','+${id}+')" role="button" data-toggle="modal"></a></span>\n';
	});
	/*non si capisce perchè al primo giro del foreach viene stamapato 'undefined', 
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

var options = {
		
	uploadProgress: upload, 
	dataType: 'json',
    success: processJson
    
};

function doAjaxPost() {

	$("#fileUpload").ajaxSubmit(options);
	return false;
}

</script>
<div id="addImages" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4>Add Image</h4>
  </div>
  <div class="modal-body">
	<div class="form">
		<form id="fileUpload" name="fileUpload" action="${pageContext.request.contextPath}${requestScope.actionModal}" method="POST" enctype="multipart/form-data" >
			<input type="hidden" name="prod_id" value="${id}">
			<div>
				<input  name="files" type="file" multiple class="btn"/>
			</div>
			<div class="control-group">
			    <div class="controls">
			    	<input type="button" class="btn" value="Add" onclick="doAjaxPost()" /> 
				</div>
			</div>
		</form>
		<div class="progress progress-animated">
			<div class="bar bar-success" data-percentage="100" style="width: 0%">0%</div>
		</div>
	</div> 

  </div>
</div>

<!-- AddImage Modal ends -->