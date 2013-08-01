<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- Dialog Modal starts -->

<script type="text/javascript">


</script>
<div id="modalWindow" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4><spring:message code="image.remove_title"/></h4>
    <p><spring:message code="image.remove_msg"/></p>
    <div>
    	<img src="${pageContext.request.contextPath}/${owner_kind}/image/${image.id}/${image.name}"/>
    </div>
  </div>
  <div class="modal-body">
	<div class="form">
			<form:form id="deleteImage" modelAttribute="image" action="${pageContext.request.contextPath}${action}" method="POST">
			<div class="span4">
				<form:hidden path="id"/>
				<input type="hidden" name="owner_id" value="${owner_id}">
				
				<div class="control-group">
			    <div class="controls">
			    	<input id="modalWindow_ok" type="button" class="btn" value="<spring:message code="common.ok"/>" onclick="doAjaxPost('#deleteImage')" /> 
			    	<input id="modalWindow_canc" type="button" class="btn" value="<spring:message code='common.cancel'/>" onclick="$('div#modalWindow').modal('hide')" /> 
				</div>
			</div>
			</div>
			</form:form>
	</div> 
  </div>
</div>

<!-- Dialog Modal ends -->