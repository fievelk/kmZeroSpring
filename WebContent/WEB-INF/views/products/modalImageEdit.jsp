<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- Dialog Modal starts -->

<div id="modalWindow" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-header">
    <button id="modalWindow_dismiss" type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4><spring:message code="image.edit_title"/></h4>
    <p><spring:message code="image.edit_msg"/></p>
	<div>
		<img src="${pageContext.request.contextPath}/products/image/${image.id}/${image.name}"/>
	</div>
  </div>
	<div class="modal-body">
		<div class="form">
			<form:form id="imageEdit" modelAttribute="image" action="${pageContext.request.contextPath}/products/image/updateImage" method="POST">
			 
			<div class="span4">
				<form:hidden path="id"/>
				<input type="hidden" name="prod_id" value="${prod_id}">
				<div>
				    <label for="altName"><spring:message code="image.altName"/></label>
				    <div>
				    	<form:input id="altName" path="altName"/>
				    </div>
				</div>
				 
				<div>
				    <label for="position"><spring:message code="image.position"/></label>
				    <div>
				    	<form:input id="position" path="position"/>
				    </div>
				</div>
				<div class="control-group">
			    <div class="controls">
			    	<input id="modalWindow_ok" type="button" class="btn" value="<spring:message code="common.submit"/>" onclick="doAjaxPost('#imageEdit')" /> 
			    	<input id="modalWindow_canc" type="button" class="btn" value="<spring:message code='common.cancel'/>" onclick="$('div#modalWindow').modal('hide')" /> 
				</div>
			</div>
			</div>
			</form:form>
		
	</div> 

  </div>
</div>

<!-- Dialog Modal ends -->