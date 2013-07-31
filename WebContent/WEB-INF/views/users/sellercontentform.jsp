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
});
</script>

<div class="items">
	<div class="container">
		<div class="row">

	    	<div class="span3 side-menu">
	
				<!-- Sidebar navigation -->
				<h5 class="title">Menu</h5>
				<!-- Sidebar navigation -->
				  <nav>
				    <ul id="navi">
				      <li><a href="myaccount.html">Gestione Ordini</a></li>
				      <li><a href="wish-list.html">Storico Ordini</a></li>
				      <li><a href="order-history.html">Gestione Utenti</a></li>
				      <li><a href="edit-profile.html">Gestione Venditori</a></li>
				    </ul>
				  </nav>
			</div>
			
			<!-- Main content -->
			
			<div class="span9">
				<h5 class="title"><spring:message code="seller.content"/></h5>
				<div class="form form-small">
					<form:form modelAttribute="seller" cssClass="form-horizontal" action="${pageContext.request.contextPath}${requestScope.action}" method="POST">
					<form:hidden path="id"/>
					
					<!-- <div class="control-group">
					    <div class="controls"> -->
					    <!-- <div class="text-area"> -->
					    <%-- <c:forEach items="${seller.contents}" var="contents">
				            <div class="control-group">
							    <label class="control-label" for="title">Titolo</label>
							    <div class="controls">
							    	<form:textarea value="${sellerContent.title}" path="contents.title" id="title"/>
							    	<form:errors path="name"/>
							    </div>
							</div>
							<div class="control-group">
							    <label class="control-label" for="description">Descrizione</label>
							    <div class="controls">
							    	<form:textarea value="${sellerContent.description}" path="contents.description" id="cleditor" cssClass="cleditor"/>
							    	<form:errors path="name"/>
							    </div>
							</div>
				        </c:forEach> --%>
				        <c:forEach items="${seller.contents}" var="contents">
				            <div class="control-group">
							    <label class="control-label" for="title">Titolo</label>
							    <div class="controls">
							    	<input type="text" value="${contents.title}" name="title" id="contents.title">
							    </div>
							</div>
							<div class="control-group">
							    <label class="control-label" for="description">Descrizione</label>
							    <div class="controls">
							    	<div class="text-area">
			                        	<textarea class="cleditor" name="input" id="contents.description">${contents.description}</textarea>
			                   		</div>
							    </div>
							</div>
				        </c:forEach>
				        
				        <!-- </div> 
					    </div>
					</div> -->
					
					<!-- <div class="text-area">
                        <textarea class="cleditor" name="input"></textarea>
                   	</div> -->

                      
					<div class="control-group">
					    <div class="controls">
					      <button type="submit"><spring:message code="common.submit"/></button>
						</div>
					</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>