<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
				<h1 class="title">${seller.company}</h1>
				<div class="form form-small">
					<%-- <form:form modelAttribute="seller" cssClass="form-horizontal" action="${pageContext.request.contextPath}${requestScope.action}" method="POST"> --%>
					<input type="hidden" value="${id}"/>
				        <c:forEach items="${seller.contents}" var="contents">
				            <div class="control-group">
							    <h3 class="title">${contents.title}</h3>
							</div>
							<div class="control-group">
							    <h5 class="title">${contents.description}</h5>
							</div>
				        </c:forEach>
					<%-- <div class="control-group">
					    <div class="controls">
					      <button type="submit"><spring:message code="common.submit"/></button>
						</div>
					</div> --%>
					<%-- </form:form> --%>
				</div>
			</div>
		</div>
	</div>
</div>