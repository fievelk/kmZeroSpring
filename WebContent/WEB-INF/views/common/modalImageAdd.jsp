<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- AddImage Modal starts -->

<div id="modalWindow" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-header">
    <button id="modalWindow_dismiss" type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4><spring:message code='image.add_title'/></h4>
    <p><spring:message code="image.add_msg"/></p>
  </div>
  <div class="modal-body">
	<div class="form">
		<form id="fileUpload" name="fileUpload" action="${pageContext.request.contextPath}${action}" method="POST" enctype="multipart/form-data" >
			<input type="hidden" name="ownerId" value="${ownerId}">
			<div>
				<input multiple name="files" type="file" />
			</div>
			<div class="control-group">
			    <div class="controls">
			    	<input id="modalWindow_ok" type="button" class="btn" value="<spring:message code='common.upload'/>" onclick="doAjaxPostUpload('#fileUpload')" /> 
			    	<input id="modalWindow_canc" type="button" class="btn" value="<spring:message code='common.cancel'/>" onclick="$('#modalWindow').modal('hide')" /> 
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