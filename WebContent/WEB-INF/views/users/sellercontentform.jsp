<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%-- <link href="${pageContext.request.contextPath}/resources/mackart/style/jquery.cleditor.css" rel="stylesheet"> <!-- CLEditor -->
<script src="${pageContext.request.contextPath}/resources/custom/js/jquery.cleditor.min.js"></script> <!-- CLEditor --> --%>

<script>
$(document).ready(function() {
	
	$(".cleditor").cleditor({
		controls: "bold italic underline | undo redo | cut copy paste pastetext | print"
	});
	
	/*--------SETUP READONLY FIELDS IF DELETING - START--------*/
	var del = "${requestScope.delete}"; 
	if (del == "true" ) {
		$(":input[type='text']").each(function () { $(this).attr('readonly','readonly'); });
		$("#description").cleditor()[0].disable("true");
	}		

});


</script>
<!-- Main content -->

<div class="span4">
	<h5 class="title">
		<c:choose>
			<c:when test="${requestScope.delete eq 'true'}">
				<spring:message code="sellercontent.delete"/>
			</c:when>
			<c:when test="${requestScope.create eq 'true'}">
				<spring:message code="sellercontent.create"/>
			</c:when>
			<c:when test="${requestScope.update eq 'true'}">
				<spring:message code="sellercontent.update"/>
			</c:when>
		</c:choose>	
	</h5>
	<div class="form form-small">
		<form:form modelAttribute="sellercontent" cssClass="form-horizontal" action="${pageContext.request.contextPath}${requestScope.action}" method="POST">
		<form:hidden path="id"/>
			<div class="control-group">
			    <label class="control-label" for="name"><spring:message code="sellercontent.title"/></label>
			    <div class="controls">
			    	<form:input id="title" path="title"/>
			    </div>
			</div>				        
			<div class="control-group">
			    <label class="control-label" for="description"><spring:message code="sellercontent.description"/></label>
			    <div class="controls">
			    	<div class="text-area">
                    	<textarea class="cleditor" name="description" id="description">${sellercontent.description}</textarea>
               		</div>
			    </div>
			</div>                     
		<div class="control-group">
		    <div class="controls">
		      <button class="btn" type="submit">
		      	<c:choose>
					<c:when test="${requestScope.delete eq 'true'}">
						<spring:message code="common.delete"/>
					</c:when>
					<c:otherwise>
						<spring:message code="common.submit"/>
					</c:otherwise>
				</c:choose>	
		      </button>
			</div>
		</div>
		</form:form>
			
	</div>
</div>
<c:if test="${requestScope.update eq 'true'}">
	<div class="span4 productImages">
		<div class="row-fluid">
			<a class="btn" href="#modalWindow" role="button" data-toggle="modal" onclick="createModalWindow('addImages','selr_content','${sellercontent.id}',null,null)"><spring:message code="image.add"/></a>
		</div>
		
		<div id="km0Images">
		<c:if test="${image != null}">
			<div>
				<a href="#modalWindow" class="icon-edit" role="button" data-toggle="modal" onclick="createModalWindow('updateImage','selr_content','${sellercontent.id}','image','${image.id}')" ></a>	
	       		<a href="#modalWindow" class="icon-remove"  role="button" data-toggle="modal" onclick="createModalWindow('deleteImage','selr_content','${sellercontent.id}','image','${image.id}')"></a>
			</div>
  			<span id="image_${image.id}">
	       		<img src="${pageContext.request.contextPath}/selr_content/image/${image.id}/${image.name}" alt="${image.name}" />
       		</span>	
     	</c:if>      		
    	</div>

	</div>	
</c:if>
