<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- AddImage Modal starts -->

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
				<input  name="files" type="file" multiple/>
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